// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.Response;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.TablesImpl;
import com.azure.data.tables.implementation.models.ResponseFormat;
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
    private final TablesImpl impl = null;

    TableAsyncClient(String tableName) {
        this.tableName = tableName;
    }

    /**
     * returns the table name associated with the client
     *
     * @return table name
     */
    public Mono<String> getTableName() {
        return Mono.empty();
    }

    /**
     * Queries and returns entities in the given table using the odata query options
     *
     * @param queryOptions the odata query object
     * @return a paged flux of all the entity which fit this criteria
     */
    public PagedFlux<TableEntity> queryEntities(QueryOptions queryOptions) {
        return null;
    }

    /**
     * Queries and returns entities in the given table using the odata query options
     *
     * @param queryOptions the odata query object
     * @param timeout max time for query to execute before erroring out
     * @return a paged flux of all the entity which fit this criteria
     */
    public PagedFlux<TableEntity> queryEntities(QueryOptions queryOptions, Duration timeout) {
        return null;
    }

    /**
     * Queries and returns entities in the given table using the odata query options
     *
     * @param queryOptions the odata query object
     * @param timeout max time for query to execute before erroring out
     * @return a paged flux of responses of all the entity which fit this criteria
     */
    public PagedFlux<Response<TableEntity>> queryEntitiesWithResponse(QueryOptions queryOptions, Duration timeout) {
        return null;
    }

    private PagedFlux<Response<TableEntity>> queryEntitiesWithResponse(QueryOptions queryOptions, Duration timeout, Context context) {
        return null;
    }

    private Mono<PagedResponse<TableEntity>> queryFirstPageEntities(QueryOptions queryOptions, Context context) {
        return null;
    }

    private Mono<PagedResponse<TableEntity>> queryNextPageEntities(Context context, String nextPartitionKey, String nextRowKey) {
        return null;
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param rowKey the row key of the entity
     * @param partitionKey the partition key of the entity
     * @param ifMatch if the etag of the entity must match the found entity or not
     * @param etag the etag, only required if the ifMatch param is true
     * @return a mono of the table entity
     */
    public Mono<TableEntity> getEntity(String rowKey, String partitionKey, Boolean ifMatch, String etag) {
        return null;
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param rowKey the row key of the entity
     * @param partitionKey the partition key of the entity
     * @param ifMatch if the etag of the entity must match the found entity or not
     * @param etag the etag, only required if the ifMatch param is true
     * @param timeout max time for query to execute before erroring out
     * @return a mono of the table entity
     */
    public Mono<TableEntity> getEntity(String rowKey, String partitionKey, Boolean ifMatch, String etag, Duration timeout) {
        return null;
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param rowKey the row key of the entity
     * @param partitionKey the partition key of the entity
     * @param ifMatch if the etag of the entity must match the found entity or not
     * @param etag the etag, only required if the ifMatch param is true
     * @param timeout max time for query to execute before erroring out
     * @return a mono of the response with the table entity
     */
    public Mono<Response<TableEntity>> getEntityWithResponse(String rowKey, String partitionKey, Boolean ifMatch, String etag, Duration timeout) {
        return null;
    }

    Mono<Response<TableEntity>> getEntityWithResponse(String rowKey, String partitionKey, Boolean ifMatch, String etag, Duration timeout, Context context) {
        return null;
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
    public Mono<TableEntity> createEntity(Map<String, Object> tableEntityProperties) {
        return createEntity(tableEntityProperties, (Duration) null);
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @param timeout max time for query to execute before erroring out
     * @return the created TableEntity
     */
    public Mono<TableEntity> createEntity(Map<String, Object> tableEntityProperties, Duration timeout) {
        return null;
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @param timeout max time for query to execute before erroring out
     * @return a mono of the response with the TableEntity
     */
    public Mono<Response<TableEntity>> createEntityWithResponse(Map<String, Object> tableEntityProperties, Duration timeout) {
        return withContext(context -> createEntityWithResponse(tableEntityProperties, timeout, context));
    }

    Mono<Response<TableEntity>> createEntityWithResponse(Map<String, Object> tableEntityProperties, Duration timeout, Context context) {
        String requestID = "";

        return impl.insertEntityWithResponseAsync(tableName, timeout.toMillisPart(), requestID, ResponseFormat.RETURN_CONTENT, tableEntityProperties,
            null, context).map(response -> {
            Map<String, Object> properties = response.getValue();
            String etag = response.getHeaders().get("ETag").getValue();
            TableEntity tableEntity = new TableEntity(properties, etag);
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), tableEntity);
        });
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @return void
     */
    public Mono<Void> upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
        return upsertEntity(updateMode, tableEntity, ifMatch, (Duration) null);
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @return void
     */
    public Mono<Void> upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        return null;
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @return a response
     */
    public Mono<Response<Void>> upsertEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        return withContext(context -> upsertEntityWithResponse(updateMode, tableEntity, ifMatch, timeout, context));
    }

    Mono<Response<Void>> upsertEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.MERGE) {
            //merges if exists, inserts if not
            existsEntity(tableEntity, timeout, context).map(exists -> {
                if (exists) {
                    return mergeEntityWithResponse(tableEntity, ifMatch, timeout, context);
                } else {
                    return createEntityWithResponse(tableEntity.getProperties(), timeout, context).then();
                }
            });

        }
        if (updateMode == UpdateMode.REPLACE) {
            //updates if exists, inserts if not
            existsEntity(tableEntity, timeout, context).map(exists -> {
                if (exists) {
                    deleteEntityWithResponse(tableEntity, ifMatch, timeout, context);
                    return createEntityWithResponse(tableEntity.getProperties(), timeout, context).then();
                } else {
                    return createEntityWithResponse(tableEntity.getProperties(), timeout, context).then();
                }
            });
        }
        throw new NullPointerException("UpdateMode cannot be null.");
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param tableEntity the entity to update
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @return void
     */
    public Mono<Void> updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
        return updateEntity(updateMode, tableEntity, ifMatch, (Duration) null);
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param tableEntity the entity to update
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @return void
     */
    public Mono<Void> updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        return null;
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param tableEntity the entity to update
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @return a response
     */
    public Mono<Response<Void>> updateEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        return withContext(context -> updateEntityWithResponse(updateMode, tableEntity, ifMatch, timeout, context));
    }

    Mono<Response<Void>> updateEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.MERGE) {
            //merges or fails if the entity doesn't exist
            return mergeEntityWithResponse(tableEntity, ifMatch, timeout, context);
        }
        if (updateMode == UpdateMode.REPLACE) {
            //replaces or fails if the entity doesn't exist
            deleteEntityWithResponse(tableEntity, ifMatch, timeout, context);
            return null;
        }
        throw new NullPointerException("UpdateMode cannot be null.");
    }

    private Mono<Void> mergeEntity(TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (ifMatch && tableEntity.getEtag() == null) {
            monoError(logger, new NullPointerException("etag cannot be null when 'ifMatch' is true"));
        }
        String requestID = "";
        String matchParam = ifMatch ? tableEntity.getEtag() : "*";

        return impl.mergeEntityWithResponseAsync(tableName, tableEntity.getPartitionKey(), tableEntity.getRowKey(),
            timeout.toMillisPart(), requestID, matchParam, tableEntity.getProperties(), null, context).then();
    }

    private Mono<Response<Void>> mergeEntityWithResponse(TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (ifMatch && tableEntity.getEtag() == null) {
            monoError(logger, new NullPointerException("etag cannot be null when 'ifMatch' is true"));
        }
        String requestID = "";
        String matchParam = ifMatch ? tableEntity.getEtag() : "*";

        return impl.mergeEntityWithResponseAsync(tableName, tableEntity.getPartitionKey(), tableEntity.getRowKey(),
            timeout.toMillisPart(), requestID, matchParam, tableEntity.getProperties(), null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                null);
        });
    }

    private Mono<Boolean> existsEntity(TableEntity tableEntity, Duration timeout, Context context) {
        String requestID = "";

        return impl.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, tableEntity.getPartitionKey(),
            tableEntity.getRowKey(), timeout.toMillisPart(), requestID, null, context).map(response -> {
            return (response.getValue() != null);
        });
    }

    private Mono<Response<Boolean>> existsEntityWithResponse(TableEntity tableEntity, Duration timeout, Context context) {
        String requestID = "";

        return impl.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, tableEntity.getPartitionKey(),
            tableEntity.getRowKey(), timeout.toMillisPart(), requestID, null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                (response.getValue() != null));

        });
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @return void
     */
    public Mono<Void> deleteEntity(TableEntity tableEntity, boolean ifMatch) {
        return deleteEntity(tableEntity, ifMatch, null);
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @return void
     */
    public Mono<Void> deleteEntity(TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return withContext(context -> deleteEntity(tableEntity.getPartitionKey(), tableEntity.getRowKey(), ifMatch, tableEntity.getEtag(), timeout));
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @return a response
     */
    public Mono<Response<Void>> deleteEntityWithResponse(TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return withContext(context -> deleteEntityWithResponse(tableEntity.getPartitionKey(), tableEntity.getRowKey(), ifMatch, tableEntity.getEtag(), timeout, context));
    }

    Mono<Response<Void>> deleteEntityWithResponse(TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return deleteEntityWithResponse(tableEntity.getPartitionKey(), tableEntity.getRowKey(), ifMatch, tableEntity.getEtag(), timeout, context);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param etag the etag for the entity, null if ifMatch is false
     * @return void
     */
    public Mono<Void> deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag) {
        return deleteEntity(partitionKey, rowKey, ifMatch, etag, null);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param etag the etag for the entity, null if ifMatch is false
     * @param timeout max time for query to execute before erroring out
     * @return void
     */
    public Mono<Void> deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag, Duration timeout) {
        return null;
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param etag the etag for the entity, null if ifMatch is false
     * @param timeout max time for query to execute before erroring out
     * @return a response
     */
    public Mono<Response<Void>> deleteEntityWithResponse(String partitionKey, String rowKey, boolean ifMatch, String etag, Duration timeout) {
        String requestID = "";
        String matchParam = ifMatch ? etag : "*";
        return withContext(context -> impl.deleteEntityWithResponseAsync(tableName, partitionKey, rowKey,
            matchParam, timeout.toMillisPart(), requestID, null, context)).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), null);
        });
    }

    Mono<Response<Void>> deleteEntityWithResponse(String partitionKey, String rowKey, boolean ifMatch, String etag, Duration timeout, Context context) {
        String requestID = "";
        String matchParam = ifMatch ? etag : "*";
        return impl.deleteEntityWithResponseAsync(tableName, partitionKey, rowKey,
            matchParam, timeout.toMillisPart(), requestID, null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), null);
        });
    }

}
