<<<<<<< HEAD

=======
>>>>>>> 044356b812... fixing conflictfile name changes
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;
<<<<<<< HEAD
import java.util.List;
import java.util.Map;

/**
 * sync client for table operations
 */
=======

import java.util.List;
import java.util.Map;

>>>>>>> 044356b812... fixing conflictfile name changes
@ServiceClient(
    builder = TableClientBuilder.class)
public class TableClient {
    String tableName;

    TableClient(String tableName) {
        this.tableName = tableName;
    }

    /**
     * Queries and returns entities in the given table using the select and filter strings
     *
     * @param selectString odata select string
     * @param filterString odata filter string
     * @return a list of the tables that fit the query
     */
    public List<TableEntity> queryEntity(String selectString, String filterString) {
        return null;
    }

<<<<<<< HEAD
<<<<<<< HEAD
    public void deleteEntity(TableEntity tableEntity){ }

    public void updateEntity(TableEntity te){ }

    public TableEntity upsertEntity(TableEntity te){ return new TableEntity(null, null); }

=======
=======
    /**
     * insert a TableEntity with the given properties and return that TableEntity
     *
     * @param row                   the RowKey
     * @param partition             the PartitionKey
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
>>>>>>> a9a61c1401... writing in docs
    public TableEntity insertEntity(String row, String partition, Map<String, Object> tableEntityProperties) {
        return new TableEntity();
    }

    /**
     * insert a new entity into the Table attached to this client
     *
     * @param tableEntity the entity in which to insert
     * @return the inserted TableEntity
     */
    public TableEntity insertEntity(TableEntity tableEntity) {
        return tableEntity;
    }

    /**
     * deletes the given entity
     *
     * @param tableEntity entity to delete
     */
    public void deleteEntity(TableEntity tableEntity) {
    }

    /**
     * merges the given entity with the entity which exists on the storage account
     *
     * @param tableEntity the entity with which to merge
     */
    public void mergeEntity(TableEntity tableEntity) {
    }

    /**
     * updates the provided TableEntity
     *
     * @param tableEntity the TableEntity to update
     */
    public void updateEntity(TableEntity tableEntity) {
    }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param tableEntity the TableEntity to insert or replace
     */
    public void insertOrReplaceEntity(TableEntity tableEntity) {
    }

    /**
     * inserts the TableEntity if it doesn't exist or merges it with the existing entity if it does
     *
     * @param tableEntity the TableEntity to insert or merge
     */
    public void insertOrMergeEntity(TableEntity tableEntity) {
    }
>>>>>>> 7245126b7a... reformatting
}
