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
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.IterableStream;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.AzureTableImpl;
import com.azure.data.tables.implementation.AzureTableImplBuilder;
import com.azure.data.tables.implementation.TablesImpl;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.data.tables.implementation.models.QueryOptions;
import com.azure.data.tables.implementation.models.ResponseFormat;
import com.azure.data.tables.implementation.models.TableEntityQueryResponse;
import com.azure.data.tables.implementation.models.TableProperties;
import com.azure.data.tables.models.Entity;
import com.azure.data.tables.models.QueryParams;
import com.azure.data.tables.models.Table;
import com.azure.data.tables.models.UpdateMode;
import java.net.URI;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import reactor.core.publisher.Mono;

import static com.azure.core.util.FluxUtil.monoError;
import static com.azure.core.util.FluxUtil.withContext;

/**
 * class for the table async client
 */
@ServiceClient(
    builder = TableClientBuilder.class,
    isAsync = true)
public class TableAsyncClient {
    private static final String DELIMITER_CONTINUATION_TOKEN = ";";
    private final ClientLogger logger = new ClientLogger(TableAsyncClient.class);
    private final String tableName;
    private final AzureTableImpl implementation;
    private final TablesImpl tableImplementation;
    private final String accountName;
    private final String tableUrl;
    private final TablesServiceVersion apiVersion;

    TableAsyncClient(String tableName, HttpPipeline pipeline, String url, TablesServiceVersion serviceVersion) {
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
        this.tableImplementation = implementation.getTables();
        this.tableName = tableName;
        this.accountName = null;
        this.tableUrl = null;
        this.apiVersion = null;

    }

    /**
     * returns the table name associated with the client
     *
     * @return table name
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * returns the account for this table
     *
     * @return returns the account name
     */
    public String getAccountName() {
        return this.accountName;
    }

    /**
     * returns Url of this service
     *
     * @return Url
     */
    public String getTableUrl() {
        return this.tableUrl;
    }

    /**
     * returns the version
     *
     * @return the version
     */
    public TablesServiceVersion getApiVersion() {
        return this.apiVersion;
    }

    /**
     * creates new table with the name of this client
     *
     * @return a table
     */
    public Mono<Table> create() {
        return createWithResponse().flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    /**
     * creates a new table with the name of this client
     *
     * @return a table
     */
    public Mono<Response<Table>> createWithResponse() {
        return withContext(context -> createWithResponse(context));
    }

    /**
     * creates a new table with the name of this client
     *
     * @param context the context of the query
     * @return a table
     */
    public Mono<Response<Table>> createWithResponse(Context context) {
        return tableImplementation.createWithResponseAsync(new TableProperties().setTableName(tableName), null,
                ResponseFormat.RETURN_CONTENT, null, context).map(response -> {
                    Table table = response.getValue() == null ? null : new Table(response.getValue().getTableName());
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                        table);
                });
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param entity the entity
     * @return the created TableEntity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Entity> createEntity(Entity entity) {
        return createEntityWithResponse(entity).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param entity the entity
     * @return a mono of the response with the TableEntity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Entity>> createEntityWithResponse(Entity entity) {
        return withContext(context -> createEntityWithResponse(entity, null, context));
    }

    Mono<Response<Entity>> createEntityWithResponse(Entity tableEntity, Duration timeout, Context context) {
        Integer timeoutInt = timeout != null ? (int) timeout.getSeconds() : null;
        return tableImplementation.insertEntityWithResponseAsync(tableName, timeoutInt, null,
                ResponseFormat.RETURN_CONTENT, tableEntity.getProperties(),
                null, context).map(response -> {
                    tableEntity.setEtag(response.getValue().get("odata.etag").toString());
                    tableEntity.setPartitionKey(response.getValue().get("PartitionKey"));
                    tableEntity.setRowKey(response.getValue().get("RowKey"));
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                tableEntity);
                });
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param entity entity to upsert
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> upsertEntity(Entity entity) {
        return upsertEntityWithResponse(entity, null).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param entity entity to upsert
     * @param updateMode type of upsert
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> upsertEntity(Entity entity, UpdateMode updateMode) {
        return upsertEntityWithResponse(entity, updateMode).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param entity entity to upsert
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> upsertEntityWithResponse(Entity entity, UpdateMode updateMode) {
        return withContext(context -> upsertEntityWithResponse(entity, updateMode, null, context));
    }

    Mono<Response<Void>> upsertEntityWithResponse(Entity entity, UpdateMode updateMode, Duration timeout,
        Context context) {
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.REPLACE) {
            return tableImplementation.updateEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(),
                entity.getRowKey().toString(), (int) timeout.getSeconds(), null, "*",
                entity.getProperties(), null, context).map(response -> {
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                    null);
                });
        } else {
            return tableImplementation.mergeEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(),
                entity.getRowKey().toString(), (int) timeout.getSeconds(), null, "*",
                entity.getProperties(), null, context).map(response -> {
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                       null);
                });
        }
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param entity the entity to update
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> updateEntity(Entity entity) {
        //TODO: merge or throw an error if it cannot be found
        return Mono.empty();
    }

    /**
     * updates the entity
     * @param entity the entity to update
     * @param updateMode which type of mode to execute
     * @return void
     */
    public Mono<Void> updateEntity(Entity entity, UpdateMode updateMode) {
        return updateEntity(entity, false, updateMode);
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param entity the entity to update
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> updateEntity(Entity entity, boolean ifUnchanged, UpdateMode updateMode) {
        return updateEntityWithResponse(entity, ifUnchanged, updateMode).flatMap(response ->
            Mono.justOrEmpty(response.getValue()));
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param entity the entity to update
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> updateEntityWithResponse(Entity entity, boolean ifUnchanged, UpdateMode updateMode) {
        return withContext(context -> updateEntityWithResponse(entity, ifUnchanged, updateMode, null, context));
    }

    Mono<Response<Void>> updateEntityWithResponse(Entity entity, boolean ifUnchanged, UpdateMode updateMode,
        Duration timeout, Context context) {
        Integer timeoutInt = timeout == null ? null : (int) timeout.getSeconds();
        if (updateMode == null || updateMode == UpdateMode.MERGE) {
            if (ifUnchanged) {
                return tableImplementation.mergeEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(),
                    entity.getRowKey().toString(), timeoutInt, null, entity.getETag(), entity.getProperties(), null,
                    context).map(response -> {
                        return new SimpleResponse<>(response.getRequest(), response.getStatusCode(),
                            response.getHeaders(), null);
                    });
            } else {
                return getEntity(entity.getPartitionKey().toString(), entity.getRowKey().toString())
                    .flatMap(entityReturned -> {
                        return tableImplementation.mergeEntityWithResponseAsync(tableName,
                        entity.getPartitionKey().toString(), entity.getRowKey().toString(), timeoutInt, null,
                        "*", entity.getProperties(), null, context);
                    }).map(response -> {
                        return new SimpleResponse<>(response.getRequest(), response.getStatusCode(),
                            response.getHeaders(), null);
                    });
            }
        } else {
            if (ifUnchanged) {
                return tableImplementation.updateEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(),
                    entity.getRowKey().toString(), timeoutInt, null, entity.getETag(), entity.getProperties(),
                    null, context).map(response -> {
                        return new SimpleResponse<>(response.getRequest(), response.getStatusCode(),
                            response.getHeaders(), null);
                    });
            } else {
                return getEntity(entity.getPartitionKey().toString(), entity.getRowKey().toString())
                    .flatMap(entityReturned -> {
                        return tableImplementation.updateEntityWithResponseAsync(tableName,
                        entity.getPartitionKey().toString(), entity.getRowKey().toString(),
                        timeoutInt, null, "*", entity.getProperties(), null,
                        context);
                    }).map(response -> {
                        return new SimpleResponse<>(response.getRequest(), response.getStatusCode(),
                            response.getHeaders(), null);
                    });
            }
        }
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteEntity(Entity entity) {
        return deleteEntity(entity, false);
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteEntity(Entity entity, boolean ifUnchanged) {
        return deleteEntityWithResponse(entity, ifUnchanged).then();
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteEntityWithResponse(Entity entity, boolean ifUnchanged) {
        return withContext(context -> deleteEntityWithResponse(entity, ifUnchanged, null, context));
    }

    Mono<Response<Void>> deleteEntityWithResponse(Entity entity, boolean ifUnchanged, Duration timeout,
        Context context) {
        String matchParam = ifUnchanged ? entity.getETag() : "*";
        Integer timeoutInt = timeout == null ? null : (int) timeout.getSeconds();
        context = context == null ? Context.NONE : context;
        return tableImplementation.deleteEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(),
            entity.getRowKey().toString(),
            matchParam, timeoutInt, null, null, context).map(response -> {
                return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                null);
            });
    }

    /**
     * Queries and returns entities in the given table using the odata query options
     *
     * @return a paged flux of all the entity which fit this criteria
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<Entity> listEntities() {
        return listEntities(new QueryParams());
    }

    /**
     * Queries and returns entities in the given table using the odata query options
     *
     * @param queryParams the odata query object
     * @return a paged flux of all the entity which fit this criteria
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<Entity> listEntities(QueryParams queryParams) {
        return new PagedFlux<>(
            () -> withContext(context -> listFirstPageEntities(context, queryParams)),
            token -> withContext(context -> listNextPageEntities(token, context, queryParams)));
    } //802

    PagedFlux<Entity> listTables(QueryParams queryParams, Context context) {

        return new PagedFlux<>(
            () -> listFirstPageEntities(context, queryParams),
            token -> listNextPageEntities(token, context, queryParams));
    } //802

    private Mono<PagedResponse<Entity>> listFirstPageEntities(Context context, QueryParams queryParams) {
        try {
            return listTables(null, null, context, queryParams);
        } catch (RuntimeException e) {
            return monoError(logger, e);
        }
    } //1459

    private Mono<PagedResponse<Entity>> listNextPageEntities(String token, Context context, QueryParams queryParams) {
        if (token == null) {
            return Mono.empty();
        }
        try {
            String[] split = token.split(DELIMITER_CONTINUATION_TOKEN, 2);
            if (split.length != 2) {
                return monoError(logger, new RuntimeException(
                    "Split done incorrectly, must have partition and row key: " + token));
            }
            String nextPartitionKey = split[0];
            String nextRowKey = split[1];
            return listTables(nextPartitionKey, nextRowKey, context, queryParams);
        } catch (RuntimeException e) {
            return monoError(logger, e);
        }
    } //1459

    private Mono<PagedResponse<Entity>> listTables(String nextPartitionKey, String nextRowKey, Context context,
        QueryParams queryParams) {
        QueryOptions queryOptions = new QueryOptions()
            .setFilter(queryParams.getFilter())
            .setTop(queryParams.getTop())
            .setSelect(queryParams.getSelect())
            .setFormat(OdataMetadataFormat.APPLICATION_JSON_ODATA_FULLMETADATA);
        return implementation.getTables().queryEntitiesWithResponseAsync(tableName, null, null,
            nextPartitionKey, nextRowKey, queryOptions, context).flatMap(response -> {
                TableEntityQueryResponse tablesQueryEntityResponse = response.getValue();
                if (tablesQueryEntityResponse == null) {
                    return Mono.empty();
                }
                List<Map<String, Object>> entityResponseValue = tablesQueryEntityResponse.getValue();
                if (entityResponseValue == null) {
                    return Mono.empty();
                }
                final List<Entity> entities = entityResponseValue.stream()
                    .map(valueMap -> {
                        Object value = valueMap.remove("odata.etag");
                        String etag = value != null ? String.valueOf(value) : null;
                        String[] split = valueMap.get("odata.editLink").toString().split("'");
                        Object partitionKey = valueMap.containsKey("PartitionKey") ? valueMap.remove("PartitionKey")
                            : split[1];
                        Object rowKey = valueMap.containsKey("RowKey") ? valueMap.remove("RowKey") : split[3];
                        Entity entity = new Entity(partitionKey, rowKey).setEtag(etag);
                        for (Map.Entry<String, Object> v : valueMap.entrySet()) {
                            if (!v.getKey().contains("odata")) {
                                entity.addProperties(v.getKey(), v.getValue());
                            }
                        }
                        return entity;
                    }).collect(Collectors.toList());

                return Mono.just(new EntityPaged(response, entities,
                response.getDeserializedHeaders().getXMsContinuationNextPartitionKey(),
                response.getDeserializedHeaders().getXMsContinuationNextRowKey()));

            });
    } //1836


    private static class EntityPaged implements PagedResponse<Entity> {
        private final Response<TableEntityQueryResponse> httpResponse;
        private final IterableStream<Entity> entityStream;
        private final String continuationToken;

        EntityPaged(Response<TableEntityQueryResponse> httpResponse, List<Entity> entityList, String nextPartitionKey,
            String nextRowKey) {
            if (nextPartitionKey == null || nextRowKey == null) {
                this.continuationToken = null;
            } else {
                this.continuationToken = String.join(DELIMITER_CONTINUATION_TOKEN, nextPartitionKey, nextRowKey);
            }
            this.httpResponse = httpResponse;
            this.entityStream = IterableStream.of(entityList);
        }

        @Override
        public int getStatusCode() {
            return httpResponse.getStatusCode();
        }

        @Override
        public HttpHeaders getHeaders() {
            return httpResponse.getHeaders();
        }

        @Override
        public HttpRequest getRequest() {
            return httpResponse.getRequest();
        }

        @Override
        public IterableStream<Entity> getElements() {
            return entityStream;
        }

        @Override
        public String getContinuationToken() {
            return continuationToken;
        }

        @Override
        public void close() {
        }
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param partitionKey the partition key of the entity
     * @param rowKey the row key of the entity
     * @return a mono of the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Entity> getEntity(String partitionKey, String rowKey) {
        return getEntity(partitionKey, rowKey).flatMap(entity -> Mono.justOrEmpty(entity));
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param partitionKey the partition key of the entity
     * @param rowKey the row key of the entity
     * @return a mono of the response with the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Entity>> getEntityWithResponse(String partitionKey, String rowKey) {
        return withContext(context -> getEntityWithResponse(partitionKey, rowKey, null, context));
    }

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Entity>> getEntityWithResponse(String partitionKey, String rowKey, Duration timeout,
        Context context) {
        QueryOptions queryOptions = new QueryOptions()
            .setFormat(OdataMetadataFormat.APPLICATION_JSON_ODATA_FULLMETADATA);
        Integer timeoutInt = timeout == null ? null : (int) timeout.getSeconds();
        return tableImplementation.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, partitionKey,
                rowKey, timeoutInt, null, queryOptions, context).handle((response, sink) -> {
                    sink.next(new SimpleResponse<>(response.getRequest(), response.getStatusCode(),
                        response.getHeaders(), new Entity("", "")));
                    sink.error(new NullPointerException("resource not found"));
                    //TODO (t-elboy) fix serialization to correct this
                });
    }
}
