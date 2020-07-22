// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.TablesImpl;
import com.azure.data.tables.implementation.models.ResponseFormat;
import com.azure.data.tables.models.Entity;
import com.azure.data.tables.models.QueryParams;
import com.azure.data.tables.models.Table;
import com.azure.data.tables.models.UpdateMode;
import java.net.URL;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Map;

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
    private final TablesImpl tableImplementation;

    TableAsyncClient(TablesImpl tableImplementation, String tableName) {
        this.tableImplementation = tableImplementation;
        this.tableName = tableName;
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
     * @return
     */
    public String getAccountName(){ return null;}

    /**
     * returns Url of this service
     * @return Url
     */
    public String getTableUrl(){ return null;}

    /**
     * returns the version
     * @return the version
     */
    public TablesServiceVersion getApiVersion() { return null;}

    /**
     * creates new table with the name of this client
     * @return a table
     */
    public Mono<Table> create() {
        return null;
    }

    /**
     * creates a new table with the name of this client
     * @return a table
     */
    public Mono<Response<Table>> createWithResponse() {
        return null;
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
            String eTag = response.getHeaders().get("eTag").getValue();
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
        if (updateMode == UpdateMode.MERGE) {
            //merges if exists, inserts if not
            existsEntity(entity, timeout, context).map(exists -> {
                if (exists) {
                    return mergeEntityWithResponse(entity, timeout, context);
                } else {
                    return createEntityWithResponse(entity.getProperties(), timeout, context).then();
                }
            });

        }
        if (updateMode == UpdateMode.REPLACE) {
            //updates if exists, inserts if not
            existsEntity(entity, timeout, context).map(exists -> {
                if (exists) {
                    deleteEntityWithResponse(entity, timeout, context);
                    return createEntityWithResponse(entity.getProperties(), timeout, context).then();
                } else {
                    return createEntityWithResponse(entity.getProperties(), timeout, context).then();
                }
            });
        }
        throw new NullPointerException("UpdateMode cannot be null.");
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
        return updateEntityWithResponse(entity, false, null).flatMap(response -> Mono.justOrEmpty(response.getValue()));
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
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.MERGE) {
            //merges or fails if the entity doesn't exist
            return mergeEntityWithResponse(entity, ifUnchanged, timeout, context);
        }
        if (updateMode == UpdateMode.REPLACE) {
            //replaces or fails if the entity doesn't exist
            deleteEntityWithResponse(entity, ifUnchanged, timeout, context);
            return null;
        }
        throw new NullPointerException("UpdateMode cannot be null.");
    }

    private Mono<Void> mergeEntity(Entity entity, boolean ifUnchanged, Duration timeout, Context context) {
        return mergeEntityWithResponse(entity, ifUnchanged, timeout, context).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    private Mono<Response<Void>> mergeEntityWithResponse(Entity entity, boolean ifUnchanged, Duration timeout, Context context) {
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (ifUnchanged && entity.getETag() == null) {
            monoError(logger, new NullPointerException("eTag cannot be null when 'ifUnchanged' is true"));
        }
        String matchParam = ifUnchanged ? entity.getETag() : "*";

        return tableImplementation.mergeEntityWithResponseAsync(tableName, entity.getPartitionKey(), entity.getRowKey(),
            (int) timeout.getSeconds(), null, matchParam, entity.getProperties(), null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                null);
        });
    }

    private Mono<Boolean> existsEntity(Entity entity, Duration timeout, Context context) {
        return existsEntityWithResponse(entity, timeout, context).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    private Mono<Response<Boolean>> existsEntityWithResponse(Entity entity, Duration timeout, Context context) {
        return tableImplementation.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, entity.getPartitionKey(),
            entity.getRowKey(), (int) timeout.getSeconds(), null, null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                (response.getValue() != null));

        });
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteEntity(Entity entity) {
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return withContext(context -> deleteEntity(entity.getPartitionKey(), entity.getRowKey(), false, entity.getETag()));
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
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return withContext(context -> deleteEntity(entity.getPartitionKey(), entity.getRowKey(), ifUnchanged, entity.getETag()));
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
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return withContext(context -> deleteEntityWithResponse(entity.getPartitionKey(), entity.getRowKey(), ifUnchanged, entity.getETag(), null, context));
    }

    Mono<Response<Void>> deleteEntityWithResponse(Entity entity, boolean ifUnchanged, Duration timeout, Context context) {
        if (entity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return deleteEntityWithResponse(entity.getPartitionKey(), entity.getRowKey(), ifUnchanged, entity.getETag(), timeout, context);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param eTag the eTag for the entity, null if ifUnchanged is false
     * @return void
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Void> deleteEntity(String partitionKey, String rowKey, boolean ifUnchanged, String eTag) {
        return deleteEntityWithResponse(partitionKey, rowKey, ifUnchanged, eTag).flatMap(response -> Mono.justOrEmpty(response.getValue()));
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param eTag the eTag for the entity, null if ifUnchanged is false
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteEntityWithResponse(String partitionKey, String rowKey, boolean ifUnchanged, String eTag) {
        return withContext(context -> deleteEntityWithResponse(partitionKey, rowKey, ifUnchanged, eTag, null, context));
    }

    Mono<Response<Void>> deleteEntityWithResponse(String partitionKey, String rowKey, boolean ifUnchanged, String eTag, Duration timeout, Context context) {
        String matchParam = ifUnchanged ? eTag : "*";
        return tableImplementation.deleteEntityWithResponseAsync(tableName, partitionKey, rowKey,
            matchParam, (int) timeout.getSeconds(), null, null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), null);
        });
    }

    /**
     * Queries and returns entities in the given table using the odata query options
     *
     * @return a paged flux of all the entity which fit this criteria
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<Entity> listEntities() {
        return null;
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
        return withContext(context -> getEntityWithResponse(partitionKey, rowKey, null, context)).flatMap(response -> Mono.justOrEmpty(response.getValue()));
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
        try {
            return tableImplementation.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, partitionKey, rowKey, (int) timeout.getSeconds(),
                null, null, context).handle((response, sink) -> {
                for (Map<String, Object> m : response.getValue().getValue()) {
                    if (m.get("PartitionKey").equals(partitionKey) && m.get("RowKey").equals(rowKey)) {
                        sink.next(new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                            new Entity(m)));
                    }
                }
                sink.error(new NullPointerException("resource not found"));
            });
        } catch (RuntimeException ex) {
            return monoError(logger, ex);
        }
    }
}
