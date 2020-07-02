
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;

import com.azure.data.tables.implementation.TablesImpl;
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

    TableClient(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Queries and returns entities in the given table using the select and filter strings
     *
     * @param queryOptions the odata query object
     * @return a list of the tables that fit the query
     */
    public List<TableEntity> queryEntity(QueryOptions queryOptions) {
        return null;
    }

    public TableEntity upsertEntity(TableEntity te){ return new TableEntity(null, null); }


    /**
     * insert a TableEntity with the given properties and return that TableEntity. Property map must include
     * rowKey and partitionKey
     *
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
    public TableEntity insertEntity(String row, String partition, Map<String, Object> tableEntityProperties) {
        return null;
    }

    public void upsertEntity( UpdateMode updateMode, TableEntity tableEntity){
        if (updateMode.equals(UpdateMode.Merge)){
            //insert or merge if exists
        }
        if (updateMode.equals(UpdateMode.Replace)){
            //insert or replace if exists
        }
    }

    public void updateEntity( UpdateMode updateMode, TableEntity tableEntity) {
        if (updateMode.equals(UpdateMode.Merge)){
            //update if exists, fails if entity does not exist
        }
        if (updateMode.equals(UpdateMode.Replace)){
            //replaces if exists, fails if entity does not exist
        }
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     */
    public void deleteEntity(TableEntity tableEntity) {
    }

    /**
     * deletes the given entity
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     */
    public void deleteEntity(String partitionKey, String rowKey) {
    }

    /**
     * returns the table name associated with the client
     * @return table name
     */
    public String getTableName(){
        return this.tableName;
    }


}
