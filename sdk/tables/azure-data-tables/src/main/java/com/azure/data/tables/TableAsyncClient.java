// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;
import com.azure.core.http.rest.PagedFlux;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * class for the table async client
 */
@ServiceClient(
    builder = TableClientBuilder.class,
    isAsync = true)
public class TableAsyncClient {
    String tableName;


    TableAsyncClient(String tableName) {
        this.tableName = tableName;
    }


    /**
     * returns the table associated with this table client
     *
     * @param tableName the name of the table
     * @return the table
     */
    public Mono<AzureTable> getTable(String tableName) {
        return null;
    }

    /**
     * Queries and returns entities in the given table using the select and filter strings
     *
     * @param top          odata top parameter
     * @param selectString odata select string
     * @param filterString odata filter string
     * @return a paged flux of all the entity which fit this criteria
     */
    public PagedFlux<TableEntity> queryEntity(Integer top, String selectString, String filterString) {
        return null;
    }


    /**
     * insert a TableEntity with the given properties and return that TableEntity
     *
     * @param row                   the RowKey
     * @param partition             the PartitionKey
     * @param tableEntityProperties a map of properties for the TableEntity
     * @return the created TableEntity
     */
    public Mono<TableEntity> insertEntity(String row, String partition, Map<String, Object> tableEntityProperties) {
        return null;
    }

    /**
     * insert a new entity into the Table attached to this client
     *
     * @param tableEntity the entity in which to insert
     * @return the inserted TableEntity
     */
    public Mono<TableEntity> insertEntity(TableEntity tableEntity) {
        return null;
    }

    public Mono<Void> updateEntity(TableEntity te){ return  Mono.empty(); }
    public Mono<TableEntity> upsertEntity(TableEntity te){ return null; }

    /**
     * inserts the TableEntity if it doesn't exist or replace it if it does
     *
     * @param tableEntity the TableEntity to insert or replace
     * @return a mono void
     */
    public Mono<Void> insertOrReplaceEntity(TableEntity tableEntity) {
        return Mono.empty();
    }

    /**
     * inserts the TableEntity if it doesn't exist or merges it with the existing entity if it does
     *
     * @param tableEntity the TableEntity to insert or merge
     * @return a mono void
     */
    public Mono<Void> insertOrMergeEntity(TableEntity tableEntity) {
        return Mono.empty();
    }


}
