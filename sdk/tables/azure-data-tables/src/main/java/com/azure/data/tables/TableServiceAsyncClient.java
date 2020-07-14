// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpRequest;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.util.Context;
import com.azure.core.util.IterableStream;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.TablesImpl;
import java.net.MalformedURLException;
import java.util.List;
import reactor.core.publisher.Mono;

import static com.azure.core.util.FluxUtil.withContext;

/**
 * async client for account operations
 */
@ServiceClient(
    builder = TableServiceClientBuilder.class,
    isAsync = true)
public class TableServiceAsyncClient {
    private TablesImpl impl = null;
    private final ClientLogger logger = new ClientLogger(TableServiceAsyncClient.class);
    private String apiVersion;
    private String serviceEndpoint;

    TableServiceAsyncClient() {
    }

    TableServiceAsyncClient(String serviceEndpoint, HttpPipeline pipeline, TablesServiceVersion version) {
        this.impl = RestProxy.create(TablesImpl.class, pipeline);
        this.serviceEndpoint = serviceEndpoint;
        apiVersion = version.getVersion();
    }

    /**
     * retrieves the async table client for the provided table or creates one if it doesn't exist
     *
     * @param name the name of the table
     * @return associated TableAsyncClient
     */
    public TableAsyncClient getTableAsyncClient(String name) {
        return null;
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @return the azure table object for the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<AzureTable> createTable(String name) {
        return null;
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @return a response wth the azure table object for the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<AzureTable>> createTableWithResponse(String name) {
        return null;
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<AzureTable>> createTableWithResponse(String name, Context context) {
        return null;
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to delete
     * @return mono void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteTable(String name) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to delete
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTableWithResponse(String name) {
        return Mono.empty();
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Void>> deleteTableWithResponse(String name, Context context) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     * @return mono void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteTable(AzureTable azureTable) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTableWithResponse(AzureTable azureTable) {
        return Mono.empty();
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Void>> deleteTableWithResponse(AzureTable azureTable, Context context) {
        return Mono.empty();
    }

    /**
     * query all the tables under the storage account and returns the tables that fit the query params
     *
     * @param queryOptions the odata query object
     * @return a flux of the tables that met this criteria
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<AzureTable> queryTables(QueryParams queryOptions) {
        return null;
    }
////        return new PagedFlux<>(
////            () -> withContext(context -> queryTablesFirstPage(context, queryOptions)),
////            token -> withContext(context -> queryTablesNextPage(token, context)));
//    } //802
//
//    /**
//     * query all the tables under the storage account and returns the tables that fit the query params
//     *
//     * @param queryOptions the odata query object
//     * @return a flux of the table responses that met this criteria
//     */
//    public PagedFlux<Response<AzureTable>> queryTablesWithResponse(QueryOptions queryOptions) {
//        return null;
//    }
//
//    PagedFlux<Response<AzureTable>> queryTablesWithResponse(QueryOptions queryOptions, Context context) {
//        return null;
//    }
//
//    private Mono<PagedResponse<AzureTable>> queryTablesFirstPage(Context context, QueryOptions queryOptions) {
//        try {
//            return queryTables(null, context, queryOptions);
//        } catch (RuntimeException e) {
//            return monoError(logger, e);
//        }
//    } //1459

//    private Mono<PagedResponse<AzureTable>> queryTables(String nextTableName, Context context, QueryOptions queryOptions) {
//        return impl.queryWithResponseAsync("id", nextTableName, (com.azure.data.tables.implementation.models.QueryOptions) null, context)
//            .flatMap(response -> {
//                if (response.getValue() == null) {
//                    Mono.error(new NullPointerException("value is null"));
//                }
//                final Response<List<TableResponseProperties>> feedResponse = new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
//                    response.getValue().getValue());
//
//                final List<TableResponseProperties> feed = feedResponse.getValue();
//
//                final List<AzureTable> tables = feed.stream()
//                    .map(e -> {
//                        final String tableName = e.getTableName();
//                        final AzureTable table = new AzureTable(tableName);
//                        return table;
//                    })
//                    .collect(Collectors.toList());
//                try {
//                    return Mono.just(extractPage(feedResponse, tables, feed.getLink()));
//                } catch (MalformedURLException | UnsupportedEncodingException error) {
//                    return Mono.error(new RuntimeException("Could not parse response into FeedPage<QueueDescription>",
//                        error));
//                }
//            });
//    } //1836

    /**
     * Creates a {@link FeedPage} given the elements and a set of response links to get the next link from.
     *
     * @param entities Entities in the feed.
     * @param responseLinks Links returned from the feed.
     * @param <TResult> Type of Service Bus entities in page.
     *
     * @return A {@link FeedPage} indicating whether this can be continued or not.
     * @throws MalformedURLException if the "next" page link does not contain a well-formed URL.
     */
//    private <TResult, TFeed> FeedPage<TResult> extractPage(Response<TFeed> response, List<TResult> entities,
//                                                           List<ResponseLink> responseLinks)
//        throws MalformedURLException, UnsupportedEncodingException {
//        final Optional<ResponseLink> nextLink = responseLinks.stream()
//            .filter(link -> link.getRel().equalsIgnoreCase("next"))
//            .findFirst();
//
//        if (!nextLink.isPresent()) {
//            return new FeedPage<>(response.getStatusCode(), response.getHeaders(), response.getRequest(), entities);
//        }
//
//        final URL url = new URL(nextLink.get().getHref());
//        final String decode = URLDecoder.decode(url.getQuery(), StandardCharsets.UTF_8.name());
//        final Optional<Integer> skipParameter = Arrays.stream(decode.split("&amp;|&"))
//            .map(part -> part.split("=", 2))
//            .filter(parts -> parts[0].equalsIgnoreCase("$skip") && parts.length == 2)
//            .map(parts -> Integer.valueOf(parts[1]))
//            .findFirst();
//
//        if (skipParameter.isPresent()) {
//            return new FeedPage<>(response.getStatusCode(), response.getHeaders(), response.getRequest(), entities,
//                skipParameter.get());
//        } else {
//            logger.warning("There should have been a skip parameter for the next page.");
//            return new FeedPage<>(response.getStatusCode(), response.getHeaders(), response.getRequest(), entities);
//        }
//    } //1790

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
        private FeedPage(int statusCode, HttpHeaders header, HttpRequest request, List<T> entries, int skip) {
            this.statusCode = statusCode;
            this.header = header;
            this.request = request;
            this.entries = new IterableStream<>(entries);
            this.continuationToken = String.valueOf(skip);
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
