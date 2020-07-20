// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.HttpHeader;
import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.IterableStream;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.AzureTableImpl;
import com.azure.data.tables.implementation.AzureTableImplBuilder;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.data.tables.implementation.models.QueryOptions;
import com.azure.data.tables.implementation.models.ResponseFormat;
import com.azure.data.tables.implementation.models.TableProperties;
import reactor.core.publisher.Mono;

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

import static com.azure.core.util.FluxUtil.monoError;
import static com.azure.core.util.FluxUtil.withContext;

/**
 * async client for account operations
 */
@ServiceClient(builder = TableServiceClientBuilder.class, isAsync = true)
public class TableServiceAsyncClient {
    private final ClientLogger logger = new ClientLogger(TableServiceAsyncClient.class);
    private final AzureTableImpl implementation;

    TableServiceAsyncClient(HttpPipeline pipeline, String url, TablesServiceVersion serviceVersion) {
        try {
            final URI uri = URI.create(url);
            logger.verbose("Table Service URI: {}", uri);
        } catch (IllegalArgumentException ex) {
            throw logger.logExceptionAsError(ex);
        }

        this.implementation = new AzureTableImplBuilder()
            .url(url)
            .pipeline(pipeline)
            .version(serviceVersion.getVersion())
            .buildClient();
    }

    /**
     * retrieves the async table client for the provided table or creates one if it doesn't exist
     *
     * @param tableName the tableName of the table
     *
     * @return associated TableAsyncClient
     */
    public TableAsyncClient getTableAsyncClient(String tableName) {
        return new TableAsyncClient(implementation.getTables(), tableName);
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param tableName the name of the table to create
     *
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
     *
     * @return a response wth the azure table object for the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<AzureTable>> createTableWithResponse(String tableName) {
        return withContext(context -> createTableWithResponse(tableName, context));
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<AzureTable>> createTableWithResponse(String tableName, Context context) {
        context = context == null ? Context.NONE : context;
        final TableProperties properties = new TableProperties().setTableName(tableName);

        try {
            return implementation.getTables().createWithResponseAsync(properties,
                null,
                ResponseFormat.RETURN_CONTENT, null, context)
                .map(response -> {
                    final AzureTable table = new AzureTable(response.getValue().getTableName());

                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(),
                        response.getHeaders(), table);
                });
        } catch (RuntimeException ex) {
            return monoError(logger, ex);
        }
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param tableName the name of the table to delete
     *
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
     *
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTableWithResponse(String tableName) {
        return withContext(context -> deleteTableWithResponse(tableName, context));
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Void>> deleteTableWithResponse(String tableName, Context context) {
        context = context == null ? Context.NONE : context;
        return implementation.getTables().deleteWithResponseAsync(tableName, null, context).map(response -> {
            return new SimpleResponse<>(response, null);
        });
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     *
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
     *
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
     *
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

    private Mono<PagedResponse<AzureTable>> queryTablesNextPage(String token, Context context, QueryParams queryParams) {
        try {
            return queryTables(token, context, queryParams);
        } catch (RuntimeException e) {
            return monoError(logger, e);
        }
    } //1459

    private Mono<PagedResponse<AzureTable>> queryTables(String nextTableName, Context context, QueryParams queryParams) {
        QueryOptions queryOptions = queryParams != null ? queryParams.convertToQueryOptions() : new QueryOptions();
        queryOptions.setFormat(OdataMetadataFormat.APPLICATION_JSON_ODATA_MINIMALMETADATA);
        return implementation.getTables().queryWithResponseAsync(null, nextTableName, queryOptions, context).flatMap(response -> {
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
