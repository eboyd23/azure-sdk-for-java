// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import reactor.core.publisher.Mono;

/**
 * sync client for table operations
 */
@ServiceClient(
    builder = TableClientBuilder.class)
public class TableClient {
    final String tableName;
    final TableAsyncClient client;

    TableClient(String tableName, TableAsyncClient client) {
        this.tableName = tableName;
        this.client = client;
    }

    /**
     * returns the table name associated with the client*
     *
     * @return table name
     */
    public String getTableName() {
        return this.tableName;
    }

    /**
     * Queries and returns entities in the given table using the odata QueryOptions
     *
     * @param queryOptions the odata query object
     * @return a list of the tables that fit the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public List<TableEntity> queryEntities(QueryOptions queryOptions) {
        return null;
    }

    /**
     * Queries and returns entities in the given table using the odata QueryOptions
     *
     * @param queryOptions the odata query object
     * @param timeout max time for query to execute before erroring out
     * @return a list of the tables that fit the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public List<TableEntity> queryEntities(QueryOptions queryOptions, Duration timeout) {
        return null;
    }

    /**
     * Queries and returns entities in the given table using the odata QueryOptions
     *
     * @param queryOptions the odata query object
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a list of the tables that fit the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public List<TableEntity> queryEntities(QueryOptions queryOptions, Duration timeout, Context context) {
        return null;
    }

    /**
     * Queries and returns entities in the given table using the odata QueryOptions
     *
     * @param queryOptions the odata query object
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a list responses with the tables that fit the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public List<Response<TableEntity>> queryEntitiesWithResponse(QueryOptions queryOptions, Duration timeout, Context context) {
        return null;
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param rowKey the row key of the entity
     * @param partitionKey the partition key of the entity
     * @param ifMatch if the etag of the entity must match the found entity or not
     * @param etag the etag, only required if the ifMatch param is true
     * @return the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TableEntity getEntity(String rowKey, String partitionKey, Boolean ifMatch, String etag) {
        return client.getEntity(rowKey, partitionKey, ifMatch, etag).block();
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
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TableEntity getEntity(String rowKey, String partitionKey, Boolean ifMatch, String etag, Duration timeout) {
        return client.getEntity(rowKey, partitionKey, ifMatch, etag, timeout).block();
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param rowKey the row key of the entity
     * @param partitionKey the partition key of the entity
     * @param ifMatch if the etag of the entity must match the found entity or not
     * @param etag the etag, only required if the ifMatch param is true
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a mono of the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TableEntity getEntity(String rowKey, String partitionKey, Boolean ifMatch, String etag, Duration timeout, Context context) {
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
     * @param context the context of the query
     * @return a mono of the response with the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<TableEntity> getEntityWithResponse(String rowKey, String partitionKey, Boolean ifMatch, String etag, Duration timeout, Context context) {
        return getEntityWithResponse(rowKey, partitionKey, ifMatch, etag, timeout, context);
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TableEntity createEntity(Map<String, Object> tableEntityProperties) {
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
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TableEntity createEntity(Map<String, Object> tableEntityProperties, Duration timeout) {
        return null;
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return the created TableEntity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public TableEntity createEntity(Map<String, Object> tableEntityProperties, Duration timeout, Context context) {
        return null;
    }

    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return the created TableEntity in a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<TableEntity> createEntityWithResponse(Map<String, Object> tableEntityProperties, Duration timeout, Context context) {
        return client.createEntityWithResponse(tableEntityProperties, timeout, context).block();
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout) {
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> upsertEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        return client.upsertEntityWithResponse(updateMode, tableEntity, ifMatch, timeout, context).block();
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param tableEntity the entity to update
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
        client.updateEntity(updateMode, tableEntity, ifMatch).block();
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param tableEntity the entity to update
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        client.updateEntity(updateMode, tableEntity, ifMatch, timeout).block();
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param tableEntity the entity to update
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {

    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param tableEntity the entity to update
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> updateEntityWithResponse(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        return updateEntityWithResponse(updateMode, tableEntity, ifMatch, timeout, context);
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(TableEntity tableEntity, boolean ifMatch) {
        deleteEntity(tableEntity, ifMatch, null);
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(TableEntity tableEntity, boolean ifMatch, Duration timeout) {
        client.deleteEntity(tableEntity, ifMatch, timeout).block();
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        deleteEntityWithResponse(tableEntity, ifMatch, timeout, context).getValue();
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> deleteEntityWithResponse(TableEntity tableEntity, boolean ifMatch, Duration timeout, Context context) {
        return client.deleteEntityWithResponse(tableEntity, ifMatch, timeout, context).block();
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param etag the etag for the entity, null if ifMatch is false
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag) {
        client.deleteEntity(partitionKey, rowKey, ifMatch, etag);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param etag the etag for the entity, null if ifMatch is false
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag, Duration timeout) {
        client.deleteEntity(partitionKey, rowKey, ifMatch, etag, timeout);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param etag the etag for the entity, null if ifMatch is false
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag, Duration timeout, Context context) {
        deleteEntityWithResponse(partitionKey, rowKey, ifMatch, etag, timeout, context).getValue();
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifMatch if the etag of the entity must match the entity in the service or not
     * @param etag the etag for the entity, null if ifMatch is false
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> deleteEntityWithResponse(String partitionKey, String rowKey, boolean ifMatch, String etag, Duration timeout, Context context) {
        return client.deleteEntityWithResponse(partitionKey, rowKey,ifMatch, etag, timeout, context).block();
    }

}
