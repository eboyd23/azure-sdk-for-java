
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import java.util.List;
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
     * Queries and returns entities in the given table using the select and filter strings
     *
     * @param queryOptions the odata query object
     * @return a list of the tables that fit the query
     */
    public PagedIterable<TableEntity> queryEntity(QueryOptions queryOptions) {
        return new PagedIterable<TableEntity>(client.queryEntities(queryOptions));
    }

    /**
     * Queries and returns entities in the given table with the given rowKey and ParitionKey
     *
     * @param rowKey the given row key
     * @param partitionKey the given partition key
     * @return a list of the tables that fit the row and partition key
     */
    public PagedIterable<TableEntity> queryEntitiesWithPartitionAndRowKey(String rowKey, String partitionKey) {
        return new PagedIterable<TableEntity>(client.queryEntities(rowKey, partitionKey));
    }
    public PagedIterable<TableEntity> queryEntitiesWithPartitionAndRowKey(String rowKey, String partitionKey, Context context) {
        return new PagedIterable<TableEntity>(client.queryEntities(rowKey, partitionKey));
    }


    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
    public  TableEntity createEntity(Map<String, Object> tableEntityProperties) {
        return client.createEntity(tableEntityProperties).block();
    }

    /**
     * based on Mode it either inserts or merges if exists or inserts or merges if exists
     *
     * @param updateMode type of upsert
     * @param tableEntity entity to upsert
     */
    public Void upsertEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
        return client.upsertEntity(updateMode, tableEntity, ifMatch).block();
    }

    /**
     * based on Mode it either updates or fails if it does exists or replaces or fails if it does exists
     *
     * @param updateMode type of update
     * @param tableEntity entity to update
     */
    public Void updateEntity(UpdateMode updateMode, TableEntity tableEntity, boolean ifMatch) {
        return client.updateEntity(updateMode, tableEntity, ifMatch).block();
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     */
    public void deleteEntity(TableEntity tableEntity, boolean ifMatch) {
        client.deleteEntity(tableEntity, ifMatch);
    }

    /**
     * deletes the given entity
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     */
    public void deleteEntity(String partitionKey, String rowKey, boolean ifMatch, String etag) {
        client.deleteEntity(partitionKey, rowKey, ifMatch, etag).block();
    }

    /**
     * returns the table name associated with the client
     *
     * @return table name
     */
    public String getTableName() {
        return this.tableName;
    }


}
