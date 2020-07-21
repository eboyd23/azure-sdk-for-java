// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.data.tables.models.Entity;
import com.azure.data.tables.models.UpdateMode;
import java.time.Duration;
import java.util.Map;

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
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Entity createEntity(Map<String, Object> tableEntityProperties) {
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
    public Entity createEntity(Map<String, Object> tableEntityProperties, Duration timeout) {
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
    public Response<Entity> createEntityWithResponse(Map<String, Object> tableEntityProperties, Duration timeout, Context context) {
        return client.createEntityWithResponse(tableEntityProperties, timeout, context).block();
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param entity entity to upsert
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(Entity entity) {
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param entity entity to upsert
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(Entity entity, UpdateMode updateMode) {
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param entity entity to upsert
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(Entity entity, boolean ifUnchanged) {
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param entity entity to upsert
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(Entity entity, boolean ifUnchanged, UpdateMode updateMode) {
    }


    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param entity entity to upsert
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void upsertEntity(Entity entity, boolean ifUnchanged, UpdateMode updateMode, Duration timeout) {
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param entity entity to upsert
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> upsertEntityWithResponse(Entity entity, boolean ifUnchanged, UpdateMode updateMode, Duration timeout, Context context) {
        return client.upsertEntityWithResponse(entity, ifUnchanged, updateMode, timeout, context).block();
    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param entity the entity to update
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(Entity entity) {

    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param entity the entity to update
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(Entity entity, UpdateMode updateMode) {

    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param entity the entity to update
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(Entity entity, boolean ifUnchanged) {

    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param entity the entity to update
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(Entity entity, boolean ifUnchanged, UpdateMode updateMode) {

    }

    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param entity the entity to update
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void updateEntity(Entity entity, boolean ifUnchanged, UpdateMode updateMode, Duration timeout) {
    }


    /**
     * if UpdateMode is MERGE, merges or fails if the entity doesn't exist. If UpdateMode is REPLACE replaces or
     * fails if the entity doesn't exist
     *
     * @param updateMode which type of update to execute
     * @param entity the entity to update
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> updateEntityWithResponse(Entity entity, boolean ifUnchanged, UpdateMode updateMode, Duration timeout, Context context) {
        return updateEntityWithResponse(entity, ifUnchanged, updateMode, timeout, context);
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(Entity entity) {
        deleteEntity(entity);
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(Entity entity, boolean ifUnchanged) {
        deleteEntity(entity, ifUnchanged, null);
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(Entity entity, boolean ifUnchanged, Duration timeout) {
    }

    /**
     * deletes the given entity
     *
     * @param entity entity to delete
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> deleteEntityWithResponse(Entity entity, boolean ifUnchanged, Duration timeout, Context context) {
        return client.deleteEntityWithResponse(entity, ifUnchanged, timeout, context).block();
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(String partitionKey, String rowKey) {
        client.deleteEntity(partitionKey, rowKey, false, null);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param eTag the eTag for the entity, null if ifUnchanged is false
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(String partitionKey, String rowKey, boolean ifUnchanged, String eTag) {
        client.deleteEntity(partitionKey, rowKey, ifUnchanged, eTag);
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param eTag the eTag for the entity, null if ifUnchanged is false
     * @param timeout max time for query to execute before erroring out
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteEntity(String partitionKey, String rowKey, boolean ifUnchanged, String eTag, Duration timeout) {
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     * @param ifUnchanged if the eTag of the entity must match the entity in the service or not
     * @param eTag the eTag for the entity, null if ifUnchanged is false
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Void> deleteEntityWithResponse(String partitionKey, String rowKey, boolean ifUnchanged, String eTag, Duration timeout, Context context) {
        return client.deleteEntityWithResponse(partitionKey, rowKey, ifUnchanged, eTag, timeout, context).block();
    }

    /**
     * Queries and returns entities in the given table using the odata QueryOptions
     *
     * @param queryOptions the odata query object
     * @return a list of the tables that fit the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<Entity> listEntities(QueryParams queryOptions) {
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
    public PagedIterable<Entity> listEntities(QueryParams queryOptions, Duration timeout) {
        return null;
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param partitionKey the partition key of the entity
     * @param rowKey the row key of the entity
     * @return the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Entity getEntity(String partitionKey, String rowKey) {
        return client.getEntity(partitionKey, rowKey).block();
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param partitionKey the partition key of the entity
     * @param rowKey the row key of the entity
     * @param timeout max time for query to execute before erroring out
     * @return a mono of the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Entity getEntity(String partitionKey, String rowKey, Duration timeout) {
        return null;
    }

    /**
     * gets the entity which fits the given criteria
     *
     * @param partitionKey the partition key of the entity
     * @param rowKey the row key of the entity
     * @param timeout max time for query to execute before erroring out
     * @param context the context of the query
     * @return a mono of the response with the table entity
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<Entity> getEntityWithResponse(String partitionKey, String rowKey, Duration timeout, Context context) {
        return getEntityWithResponse(partitionKey, rowKey, timeout, context);
    }

}
