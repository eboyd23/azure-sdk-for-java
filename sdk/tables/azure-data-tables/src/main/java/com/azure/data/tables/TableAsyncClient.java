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
import com.azure.data.tables.implementation.models.TableEntityQueryResponse;
import com.azure.data.tables.implementation.models.TablesQueryEntitiesResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.azure.core.util.FluxUtil.collectBytesInByteBufferStream;
import static com.azure.core.util.FluxUtil.monoError;
import static com.azure.core.util.FluxUtil.withContext;

import java.util.Map;

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
    private final Integer TIMEOUT = 400;

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
     * Queries and returns entities in the given table using the select and filter strings
     *
     * @param tableName the name of the table
     * @return the table
     */
    public PagedFlux<TableEntity> queryEntities(QueryOptions queryOptions) {

        //ContinuationToken continuationToken;
        return new PagedFlux<TableEntity>(
            () -> withContext(context -> queryFistPageEntities(queryOptions, context)),
            continuationToken -> withContext(context -> queryNextPageEntities(context, continuationToken.getNextPartitionKey(), nextRowKey))
        );

    }

    private Mono<PagedResponse<TableEntity>> queryFirstPageEntities(QueryOptions queryOptions, Context context) {
        String requestID = "";
        return impl.queryEntitiesWithResponseAsync(tableName, TIMEOUT, requestID, null, null,
            null, context).flatMap(response -> {
            TableEntityQueryResponse value = response.getValue();
            if (value == null) {
                return Mono.empty();
            }

            final entities = value.getValue().stream()
                .map(e ->  {
                    return new TableEntity(e);
                })
                .collect(Collectors.toList());

            try {
                return Mono.just();
                FeedPage
            }

        })

    }

    private Mono<PagedResponse<TableEntity>> queryNextPageEntities(Context context, String nextPartitionKey, String nextRowKey) {
        String requestID = "";
        try {
            if (nextPartitionKey == null || nextPartitionKey.isEmpty()) {
                return Mono.empty();
            }

            return impl.queryEntitiesWithResponseAsync(tableName, TIMEOUT, requestID, nextPartitionKey, nextRowKey,
                null, context);

        } catch (RuntimeException e) {
            return monoError(logger, e);
        }
    }

    /**
     * Queries and returns entities in the given table using the select and filter strings
     *
     * @param top odata top parameter
     * @param selectString odata select string
     * @param filterString odata filter string
     * @return a paged flux of all the entity which fit this criteria
     */
    public Flux<TableEntity> queryEntities(String rowKey, String partitionKey) {
        QueryOptions queryOptions = new QueryOptions()
            .setFilter("RowKey eq " + rowKey + " and PartitionKey eq " + partitionKey);
        return queryEntities(queryOptions);
    }

    //TODO: createEntities
    /**
     * insert a TableEntity with the given properties and return that TableEntity
     *
     * @param row the RowKey
     * @param partition the PartitionKey
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
    public Mono<TableEntity> createEntity(Map<String, Object> tableEntityProperties) {
        return createEntity(tableEntityProperties, (Integer) null);
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @param timeout time to cancel request
     * @return the created TableEntity
     */
    public Mono<TableEntity> createEntity(Map<String, Object> tableEntityProperties, Integer timeout) {
        return withContext(context -> createEntity(tableEntityProperties, timeout, context));
    }

    public Mono<Response<TableEntity>> createEntityWithResponse(Map<String, Object> tableEntityProperties, Integer timeout) {
        return withContext(context -> createEntityWithResponse(tableEntityProperties, timeout, context));
    }

    private Mono<Response<TableEntity>> createEntityWithResponse(Map<String, Object> tableEntityProperties, Integer timeout, Context context) {
        String requestID = "";

        return impl.insertEntityWithResponseAsync(tableName, timeout, requestID, ResponseFormat.RETURN_CONTENT, tableEntityProperties,
            null, context).map(response -> {
            Map<String, Object> properties = response.getValue();
            String etag = response.getHeaders().get("ETag").getValue();
            TableEntity tableEntity = new TableEntity(properties, etag);
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), tableEntity);
        });
    }

    private Mono<TableEntity> createEntity(Map<String, Object> tableEntityProperties, Context context) {
        return createEntity(tableEntityProperties, null, context);
    }

    private Mono<TableEntity> createEntity(Map<String, Object> tableEntityProperties, Integer timeout, Context context) {
        String requestID = "";

        return impl.insertEntityWithResponseAsync(tableName, timeout, requestID, ResponseFormat.RETURN_CONTENT, tableEntityProperties,
            null, context).map(tablesInsertEntityResponse -> {
            Map<String, Object> properties = tablesInsertEntityResponse.getValue();
            String etag = tablesInsertEntityResponse.getHeaders().get("ETag").getValue();
            return new TableEntity(properties, etag);
        });
    }

    /**
     * insert a TableEntity with the given row and partition key
     * @param row row key
     * @param partition partition key
     * @return the table entity which is inserted
     */
    public Mono<Void> upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
        return upsertEntity(updateMode,tableEntity,ifMatch,(Integer) null);
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @return void
     */
    public Mono<Void> upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout) {
        return withContext(context -> upsertEntity(updateMode, tableEntity, ifMatch, timeout, context));
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @return void
     */
    private Mono<Response<Void>> upsertEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout) {
        return withContext(context -> upsertEntityWithResponse(updateMode, tableEntity, ifMatch, timeout, context));
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @return void
     */
    private Mono<Response<Void>> upsertEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.Merge) {
            //merges if exists, inserts if not
            existsEntity(tableEntity, timeout, context).map(exists -> {
                if (exists) {
                    return mergeEntityWithResponse(tableEntity, ifMatch, timeout, context);
                } else {
                    return createEntityWithResponse(tableEntity.getProperties(), timeout, context).then();
                }
            });

        }
        if (updateMode == UpdateMode.Replace) {
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

    private Mono<Void> upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Context context) {
        return upsertEntity(updateMode, tableEntity, ifMatch, null, context);
    }

    private Mono<Void> upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.Merge) {
            //merges if exists, inserts if not
            existsEntity(tableEntity, timeout, context).map(exists -> {
                if (exists) {
                    return mergeEntity(tableEntity, ifMatch, timeout, context);
                } else {
                    return createEntity(tableEntity.getProperties()).then();
                }
            });

        }
        if (updateMode == UpdateMode.Replace) {
            //updates if exists, inserts if not
            existsEntity(tableEntity, timeout, context).map(exists -> {
                if (exists) {
                    deleteEntity(tableEntity, ifMatch);
                    return createEntity(tableEntity.getProperties()).then();
                } else {
                    return createEntity(tableEntity.getProperties()).then();
                }
            });
        }
        throw new NullPointerException("UpdateMode cannot be null.");
    }

    //TODO update :)

    public Mono<Void> updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
        return updateEntity(updateMode,tableEntity,ifMatch,(Integer) null);
    }

    public Mono<Void> updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout) {
        return withContext(context -> updateEntity(updateMode, tableEntity, ifMatch, timeout, context));
    }

    private Mono<Response<Void>> updateEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout) {
        return withContext(context -> updateEntityWithResponse(updateMode, tableEntity, ifMatch, timeout, context));
    }


    private Mono<Response<Void>> updateEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.Merge) {
            //merges or fails if the entity doesn't exist
            return mergeEntityWithResponse(tableEntity, ifMatch, timeout, context);
        }
        if (updateMode == UpdateMode.Replace) {
            //replaces or fails if the entity doesn't exist
            deleteEntityWithResponse(tableEntity, ifMatch, timeout, context);
            return createEntityWithResponse(tableEntity.getProperties(), timeout, context).then();
        }
        throw new NullPointerException("UpdateMode cannot be null.");
    }

    private Mono<Void> updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Context context) {
        return updateEntity(updateMode, tableEntity, ifMatch, null, context);
    }

    /**
     * insert a new entity into the Table attached to this client
     *
     * @param updateMode type of update
     * @param tableEntity entity to update
     * @param ifMatch true if the user wants to etags to match, false if not
     * @return void
     */
    private Mono<Void> updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Integer timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (updateMode == UpdateMode.Merge) {
            //merges or fails if the entity doesn't exist
            return mergeEntity(tableEntity, ifMatch,timeout,context);
        }
        if (updateMode == UpdateMode.Replace) {
            //replaces or fails if the entity doesn't exist
            deleteEntity(tableEntity, ifMatch);
            return createEntity(tableEntity.getProperties(), timeout, context).then();
        }
    }

    private Mono<Void> mergeEntity(TableEntity tableEntity, boolean ifMatch, int timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (ifMatch && tableEntity.getEtag() == null) {
            monoError(logger, new NullPointerException("etag cannot be null when 'ifMatch' is true"));
        }
        String requestID = "";
        String matchParam = ifMatch ? tableEntity.getEtag() : "*";

        return impl.mergeEntityWithResponseAsync(tableName, tableEntity.getPartitionKey(), tableEntity.getRowKey(),
            timeout, requestID, matchParam, tableEntity.getProperties(), null, context).then();
    }

    private Mono<Response<Void>> mergeEntityWithResponse(TableEntity tableEntity, boolean ifMatch, int timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        if (ifMatch && tableEntity.getEtag() == null) {
            monoError(logger, new NullPointerException("etag cannot be null when 'ifMatch' is true"));
        }
        String requestID = "";
        String matchParam = ifMatch ? tableEntity.getEtag() : "*";

        return impl.mergeEntityWithResponseAsync(tableName, tableEntity.getPartitionKey(), tableEntity.getRowKey(),
            timeout, requestID, matchParam, tableEntity.getProperties(), null, context).map(response -> {
                return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                    null);
            });
    }

    private Mono<Boolean> existsEntity(TableEntity tableEntity, int timeout, Context context) {
        String requestID = "";

        return impl.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, tableEntity.getPartitionKey(),
            tableEntity.getRowKey(), timeout, requestID, null, context).map(response -> {
            return (response.getValue() != null);
        });
    }

    private Mono<Response<Boolean>> existsEntityWithResponse(TableEntity tableEntity, int timeout, Context context) {
        String requestID = "";

        return impl.queryEntitiesWithPartitionAndRowKeyWithResponseAsync(tableName, tableEntity.getPartitionKey(),
            tableEntity.getRowKey(), timeout, requestID, null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(),
                (response.getValue() != null));

        });
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch true if the user wants to etags to match, false if not
     * @return void
     */
    public Mono<Void> deleteEntity(TableEntity tableEntity, boolean ifMatch) {
        return deleteEntity(tableEntity, ifMatch, null);
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch true if the user wants to etags to match, false if not
     * @return void
     */
    public Mono<Void> deleteEntity(TableEntity tableEntity, boolean ifMatch, Integer timeout) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return withContext(context -> deleteEntity(tableEntity.getPartitionKey(), tableEntity.getRowKey(), ifMatch, tableEntity.getEtag(), timeout));
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch true if the user wants to etags to match, false if not
     * @return void
     */
    public Mono<Response<Void>> deleteEntityWithResponse(TableEntity tableEntity, boolean ifMatch, int timeout, Context context) {
        if (tableEntity == null) {
            monoError(logger, new NullPointerException("TableEntity cannot be null"));
        }
        return deleteEntitywithResponse(tableEntity.getPartitionKey(), tableEntity.getRowKey(), ifMatch, tableEntity.getEtag(), timeout, context);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch true if the user wants to etags to match, false if not
     * @param etag the etag for the entity, null if ifMatch is false
     * @return void
     */
    public Mono<Void> deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag) {
        return withContext(context -> deleteEntity(partitionKey, rowKey, ifMatch, etag, null, context));
    }

    /**
     * deletes the given entity
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch true if the user wants to etags to match, false if not
     * @param etag the etag for the entity, null if ifMatch is false
     * @return void
     */
    public Mono<Void> deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag, Integer timeout) {
        return withContext(context -> deleteEntity(partitionKey, rowKey, ifMatch, etag, timeout, context));
    }

    /**
     * deletes the given entity
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch true if the user wants to etags to match, false if not
     * @param etag the etag for the entity, null if ifMatch is false
     * @return void
     */
    public Mono<Response<Void>> deleteEntityWithResponse(String partitionKey, String rowKey, boolean ifMatch, String etag, Integer timeout, Context context) {
        String requestID = "";
        String matchParam = ifMatch ? etag : "*";
        return impl.deleteEntityWithResponseAsync(tableName, partitionKey, rowKey,
            matchParam, timeout, requestID, null, context).map(response -> {
            return new SimpleResponse<>(response.getRequest(), response.getStatusCode(), response.getHeaders(), null);
        });
    }

    private Mono<Void> deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag, Context context) {
        return deleteEntity(partitionKey, rowKey, ifMatch, etag, null, context);
    }

    private Mono<Void> deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag, Integer timeout, Context context) {
        String requestID = "";
        if (ifMatch && etag == null) {
            monoError(logger, new NullPointerException("etag cannot be null when 'ifMatch' is true"));
        }
        String matchParam = ifMatch ? etag : "*";
        return impl.deleteEntityWithResponseAsync(tableName, partitionKey, rowKey,
            matchParam, timeout, requestID, null, context).then();
    }
}
