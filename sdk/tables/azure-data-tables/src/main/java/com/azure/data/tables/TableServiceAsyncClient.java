// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.Host;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.exception.ClientAuthenticationException;
import com.azure.core.exception.HttpResponseException;
import com.azure.core.exception.ResourceExistsException;
import com.azure.core.exception.ResourceModifiedException;
import com.azure.core.exception.ResourceNotFoundException;
import com.azure.core.http.HttpHeader;
import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.IterableStream;
import com.azure.core.util.UrlBuilder;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.AzureTableBuilder;
import com.azure.data.tables.implementation.AzureTableImpl;
import com.azure.data.tables.implementation.TablesImpl;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.data.tables.implementation.models.QueryOptions;
import com.azure.data.tables.implementation.models.ResponseFormat;
import com.azure.data.tables.implementation.models.TableProperties;
import com.azure.data.tables.implementation.models.TableResponse;
import com.azure.data.tables.implementation.models.TableResponseProperties;
import com.azure.data.tables.implementation.models.TableServiceError;
import com.azure.data.tables.implementation.models.TableServiceErrorException;
import com.azure.data.tables.implementation.models.TablesQueryResponse;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import reactor.core.publisher.Mono;
import static com.azure.core.util.FluxUtil.monoError;

import static com.azure.core.util.FluxUtil.pagedFluxError;
import static com.azure.core.util.FluxUtil.withContext;

/**
 * async client for account operations
 */
@ServiceClient(
    builder = TableServiceClientBuilder.class,
    isAsync = true)
public class TableServiceAsyncClient {
    private TablesImpl impl;
    private final ClientLogger logger = new ClientLogger(TableServiceAsyncClient.class);
    private String version;
    private String url;
    private HttpPipeline pipeline;

    TableServiceAsyncClient() {
    }

    TableServiceAsyncClient(HttpPipeline pipeline, String url, TablesServiceVersion tablesServiceVersion) {

        try {
            URI.create(url);
        } catch (IllegalArgumentException ex) {
            throw logger.logExceptionAsError(ex);
        }

        this.pipeline = pipeline;
        this.url = url;
        this.version = tablesServiceVersion.getVersion();

        AzureTableImpl azureTable = new AzureTableBuilder()
            .pipeline(pipeline)
            .version("2019-02-02")
            .buildClient();

        this.impl = new TablesImpl(build());
    }

    TableAsyncClient createTableAsyncClient(String tableName){
        return new TableAsyncClient(impl, tableName);
    }

    private AzureTableImpl build(){
        if (pipeline == null) {
            this.pipeline = new HttpPipelineBuilder().policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy()).build();
        }
        AzureTableImpl client = new AzureTableImpl(pipeline);
        if (url != null) {
            client.setUrl(url);
        }
        if (this.version != null) {
            client.setVersion(this.version);
        } else {
            client.setVersion(TablesServiceVersion.getLatest().getVersion());
        }
        return client;
    }

    /**
     * retrieves the async table client for the provided table or creates one if it doesn't exist
     *
     * @param tableName the tableName of the table
     * @return associated TableAsyncClient
     */
    public TableAsyncClient getTableAsyncClient(String tableName) {
        return new TableAsyncClient(pipeline, url, version, tableName);
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param tableName the name of the table to create
     * @return the azure table object for the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<AzureTable> createTable(String tableName) {
        return createTableWithResponse(tableName).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param tableName the name of the table to create
     * @return a response wth the azure table object for the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<AzureTable>> createTableWithResponse(String tableName) {
        return withContext(context -> createTableWithResponse(tableName, context));
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<AzureTable>> createTableWithResponse(String tableName, Context context) {
        context = context == null ? Context.NONE : context;
        try {
            return impl.createWithResponseAsync(new TableProperties().setTableName(tableName), null, ResponseFormat.RETURN_CONTENT, null, context)
                //.onErrorMap(TableServiceAsyncClient::mapException)
                .onErrorMap(error -> {
                    System.out.print(error);
                    return new Exception();})
                .handle((response, sink) -> {
                    if (response.getValue() == null) {
                        sink.error(new NullPointerException("create call returned null"));
                    }
                    String name = response.getValue().getTableName();
                    sink.next(new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), new AzureTable(name)));
                });
        } catch (RuntimeException ex) {
            return monoError(logger, ex);
        }
    }

    /**
     * Maps an exception from the ATOM APIs to its associated {@link HttpResponseException}.
     *
     * @param exception Exception from the ATOM API.
     *
     * @return The corresponding {@link HttpResponseException} or {@code throwable} if it is not an instance of {@link
     *     TableServiceErrorException}.
     */
    private static Throwable mapException(Throwable exception) {
        if (!(exception instanceof TableServiceErrorException)) {
            return exception;
        }
        final TableServiceErrorException tableError = ((TableServiceErrorException) exception);
        final TableServiceError error = tableError.getValue();
        return new Exception(error.getMessage()); //TODO: fix this because it use to be a switch
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param tableName the name of the table to delete
     * @return mono void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteTable(String tableName) {
        return deleteTableWithResponse(tableName).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param tableName the name of the table to delete
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTableWithResponse(String tableName) {
        return withContext(context -> deleteTableWithResponse(tableName, context));
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Void>> deleteTableWithResponse(String tableName, Context context) {
        context = context == null ? Context.NONE : context;
        return impl.deleteWithResponseAsync(tableName,null, context).map(response -> {
            return new SimpleResponse<>(response, null);
        });
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     * @return mono void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteTable(AzureTable azureTable) {
        return deleteTable(azureTable.getName());
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTableWithResponse(AzureTable azureTable) {
        return deleteTableWithResponse(azureTable.getName());
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Void>> deleteTableWithResponse(AzureTable azureTable, Context context) {
        return deleteTableWithResponse(azureTable.getName(), context);
    }

    /**
     * query all the tables under the storage account and returns the tables that fit the query params
     *
     * @param queryParams the odata query object
     * @return a flux of the tables that met this criteria
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<AzureTable> queryTables(QueryParams queryParams) {

        return new PagedFlux<>(
            () -> withContext(context -> queryTablesFirstPage(context, queryParams)),
            token -> withContext(context -> queryTablesNextPage(token, context, queryParams)));
    } //802

     PagedFlux<AzureTable> queryTables(QueryParams QueryParams, Context context) {

        return new PagedFlux<>(
            () -> queryTablesFirstPage(context, QueryParams),
            token -> queryTablesNextPage(token, context, QueryParams));
    } //802


    private Mono<PagedResponse<AzureTable>> queryTablesFirstPage(Context context, QueryParams queryParams) {
        try {
            return queryTables(null, context, queryParams);
        } catch (RuntimeException e) {
            return monoError(logger, e);
        }
    } //1459

    private Mono<PagedResponse<AzureTable>> queryTablesNextPage( String token, Context context, QueryParams queryParams) {
        try {
            return queryTables(token, context, queryParams);
        } catch (RuntimeException e) {
            return monoError(logger, e);
        }
    } //1459

    private Mono<PagedResponse<AzureTable>> queryTables(String nextTableName, Context context, QueryParams queryParams) {
        QueryOptions queryOptions = queryParams != null ? queryParams.convertToQueryOptions() : new QueryOptions();
        queryOptions.setFormat(OdataMetadataFormat.APPLICATION_JSON_ODATA_MINIMALMETADATA);
        return impl.queryWithResponseAsync(null, nextTableName, queryOptions, context).flatMap(response -> {
            System.out.println("RESPONSE:: ");
            System.out.println(response.getValue().getValue());
            if (response.getValue() == null) {
                return Mono.empty();
            }
            if (response.getValue().getValue() == null) {
                return Mono.empty();
            }
            final List<AzureTable> tables = response.getValue().getValue().stream()
                .map(e -> {
                    AzureTable azureTable = new AzureTable(e.getTableName());
                    return azureTable;
                }).collect(Collectors.toList());
            try {
                HttpHeader token = response.getHeaders().get("x-ms-continuation-NextTableName");
                String continuationToken = token != null ? token.getValue() : null;
                return Mono.just(new FeedPa)
                return Mono.just(extractPage(response, tables, response.getRequest().getUrl()));
            } catch (UnsupportedEncodingException error) {
                return Mono.error(new RuntimeException("Could not parse response into FeedPage<QueueDescription>",
                    error));
            }
        });
    } //1836

    /**
     * Creates a {@link FeedPage} given the elements and a set of response links to get the next link from.
     *
     * @param response the feed response
     * @param entities Entities in the feed.
     * @param currentUrl the url that was returned
     * @param <TResult> Type of Service Bus entities in page.
     *
     * @return A {@link FeedPage} indicating whether this can be continued or not.
     * @throws MalformedURLException if the "next" page link does not contain a well-formed URL.
     */
    private <TResult, TFeed> FeedPage<TResult> extractPage(Response<TFeed> response, List<TResult> entities,
                                                           URL currentUrl) throws UnsupportedEncodingException {

        if (response == null) {
            return new FeedPage<>(response.getStatusCode(), response.getHeaders(), response.getRequest(), entities);
        }

        final String decode = URLDecoder.decode(currentUrl.getQuery(), StandardCharsets.UTF_8.name());
        final Optional<String> nextTable = Arrays.stream(decode.split("\\?"))
            .map(part -> part.split("="))
            .filter(parts -> parts[1].equalsIgnoreCase("$NextTableName"))
            .map(parts -> parts[1])
            .findFirst();

        if (nextTable.isPresent()) {
            return new FeedPage<TResult>(response.getStatusCode(), response.getHeaders(), response.getRequest(), entities,
                nextTable.get());
        } else {
            logger.warning("There should have been a skip parameter for the next page.");
            return new FeedPage<>(response.getStatusCode(), response.getHeaders(), response.getRequest(), entities);
        }
    } //1790

    /**
     * A page of Service Bus entities.
     *
     * @param <T> The entity description from Service Bus.
     */
    private static final class FeedPage<T> implements PagedResponse<T> {
        private final int statusCode;
        private final HttpHeaders header;
        private final HttpRequest request;
        private final IterableStream<T> entries;
        private final String continuationToken;

        /**
         * Creates a page that does not have any more pages.
         *
         * @param entries Items in the page.
         */
        private FeedPage(int statusCode, HttpHeaders header, HttpRequest request, List<T> entries) {
            this.statusCode = statusCode;
            this.header = header;
            this.request = request;
            this.entries = new IterableStream<>(entries);
            this.continuationToken = null;
        }

        /**
         * Creates an instance that has additional pages to fetch.
         *
         * @param entries Items in the page.
         * @param skip Number of elements to "skip".
         */
        private FeedPage(int statusCode, HttpHeaders header, HttpRequest request, List<T> entries, String nextTableName) {
            this.statusCode = statusCode;
            this.header = header;
            this.request = request;
            this.entries = new IterableStream<>(entries);
            this.continuationToken = nextTableName;
        }

        @Override
        public IterableStream<T> getElements() {
            return entries;
        }

        @Override
        public String getContinuationToken() {
            return continuationToken;
        }

        @Override
        public int getStatusCode() {
            return statusCode;
        }

        @Override
        public HttpHeaders getHeaders() {
            return header;
        }

        @Override
        public HttpRequest getRequest() {
            return request;
        }

        @Override
        public void close() {
        }
    }
}
