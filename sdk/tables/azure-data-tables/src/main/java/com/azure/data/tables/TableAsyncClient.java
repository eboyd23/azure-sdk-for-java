// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.AzureTableImpl;
import com.azure.data.tables.implementation.AzureTableImplBuilder;
import com.azure.data.tables.implementation.TablesImpl;
import com.azure.data.tables.implementation.models.ResponseFormat;
import com.azure.data.tables.implementation.models.TableProperties;
import com.azure.data.tables.models.Entity;
import com.azure.data.tables.models.QueryParams;
import com.azure.data.tables.models.Table;
import com.azure.data.tables.models.UpdateMode;
import java.net.URI;
import java.time.Duration;
import java.util.Map;
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
     * @return
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
     * @return a table
     */
    public Mono<Response<Table>> createWithResponse(Context context) {
        return tableImplementation.createWithResponseAsync(new TableProperties().setTableName(tableName), null,
            ResponseFormat.RETURN_CONTENT, null, context).map(response -> {
            Table table = response.getValue() == null ? null : new Table(response.getValue().getTableName());
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), table);
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

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Entity>> createEntityWithResponse(Entity tableEntity, Duration timeout, Context context) {
        Integer timeoutInt = timeout != null ? (int) timeout.getSeconds() : null;
        return tableImplementation.insertEntityWithResponseAsync(tableName, timeoutInt, null, ResponseFormat.RETURN_CONTENT, tableEntity.getProperties(),
            null, context).map(response -> {
            tableEntity.setEtag(response.getValue().get("odata.etag").toString());
            tableEntity.setPartitionKey(response.getValue().get("PartitionKey"));
            tableEntity.setRowKey(response.getValue().get("RowKey"));
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), tableEntity);
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

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Void>> upsertEntityWithResponse(Entity entity, UpdateMode updateMode, Duration timeout, Context context) {
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.REPLACE) {
            return tableImplementation.updateEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(), entity.getRowKey().toString(),
                (int) timeout.getSeconds(), null, "*", entity.getProperties(), null,
                context).map(response -> {
                return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                    null);
            });
        } else {
            return tableImplementation.mergeEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(), entity.getRowKey().toString(),
                (int) timeout.getSeconds(), null, "*", entity.getProperties(), null,
                context).map(response -> {
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
        return updateEntityWithResponse(entity, ifUnchanged, updateMode).flatMap(response -> Mono.justOrEmpty(response.getValue()));
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

    @ServiceMethod(returns = ReturnType.SINGLE)
    Mono<Response<Void>> updateEntityWithResponse(Entity entity, boolean ifUnchanged, UpdateMode updateMode, Duration timeout, Context context) {
        Integer timeoutInt = timeout == null ? null : (int) timeout.getSeconds();
        if (updateMode == null || updateMode == UpdateMode.MERGE) {
            if (ifUnchanged) {
                return tableImplementation.mergeEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(), entity.getRowKey().toString(),
                    timeoutInt, null, entity.getETag(), entity.getProperties(), null,
                    context).map(response -> {
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                        null);
                });
            } else {
                return getEntity(entity.getPartitionKey().toString(), entity.getRowKey().toString()).flatMap(entityReturned -> {
                    return tableImplementation.mergeEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(), entity.getRowKey().toString(),
                        (int) timeout.getSeconds(), null, "*", entity.getProperties(), null,
                        context);
                }).map(response -> {
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                        null);
                });
            }
        } else {
            if (ifUnchanged) {
                return tableImplementation.updateEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(), entity.getRowKey().toString(),
                    (int) timeout.getSeconds(), null, entity.getETag(), entity.getProperties(), null,
                    context).map(response -> {
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                        null);
                });
            } else {
                return getEntity(entity.getPartitionKey().toString(), entity.getRowKey().toString()).flatMap(entityReturned -> {
                    return tableImplementation.updateEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(), entity.getRowKey().toString(),
                        (int) timeout.getSeconds(), null, "*", entity.getProperties(), null,
                        context);
                }).map(response -> {
                    return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                        null);
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

    Mono<Response<Void>> deleteEntityWithResponse(Entity entity, boolean ifUnchanged, Duration timeout, Context context) {
        String matchParam = ifUnchanged ? entity.getETag() : "*";
        Integer timeoutInt = timeout == null ? null : (int) timeout.getSeconds();
        context = context == null ? Context.NONE : context;
        return tableImplementation.deleteEntityWithResponseAsync(tableName, entity.getPartitionKey().toString(), entity.getRowKey().toString(),
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
        return listEntities(null);
    }

    /**
     * Queries and returns entities in the given table using the odata query options
     *
     * @param queryOptions the odata query object
     * @return a paged flux of all the entity which fit this criteria
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<Entity> listEntities(QueryParams queryOptions) {
        return null;
    }

    @ServiceMethod(returns = ReturnType.COLLECTION)
    private Mono<PagedResponse<Entity>> listFirstPageEntities(QueryParams queryOptions, Context context) {
        return null;
    }

    @ServiceMethod(returns = ReturnType.COLLECTION)
    private Mono<PagedResponse<Entity>> listNextPageEntities(Context context, String nextPartitionKey, String nextRowKey) {
        return null;
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
    Mono<Response<Entity>> getEntityWithResponse(String partitionKey, String rowKey, Duration timeout, Context context) {
        Integer timeoutInt = timeout == null ? null : (int) timeout.getSeconds();
        return tableImplementation.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, partitionKey, rowKey, timeoutInt,
            null, null, context).handle((response, sink) -> {
            for (Map<String, Object> m : response.getValue().getValue()) {
                if (m.get("PartitionKey").equals(partitionKey) && m.get("RowKey").equals(rowKey)) {
                    sink.next(new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                        new Entity(m)));
                }
            }
            sink.error(new NullPointerException("resource not found")); //TODO fix this
        });
    }
}
