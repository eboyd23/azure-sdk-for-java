// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;
<<<<<<< HEAD
<<<<<<< HEAD
import com.azure.core.http.rest.PagedFlux;
import reactor.core.publisher.Mono;

/**
 * async client for account operations
 */
=======
import reactor.core.publisher.Flux;
=======
import com.azure.core.http.rest.PagedFlux;
>>>>>>> a9a61c1401... writing in docs
import reactor.core.publisher.Mono;

>>>>>>> 044356b812... fixing conflictfile name changes
@ServiceClient(
    builder = TableServiceClientBuilder.class,
    isAsync = true)
public class TableServiceAsyncClient {

    TableServiceAsyncClient() {
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @return a table client connected to the given table
     */
<<<<<<< HEAD
    public Mono<AzureTable> createTable(String name) {
=======
    public Mono<TableAsyncClient> createTable(String name) {
>>>>>>> a9a61c1401... writing in docs
        return null;
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
<<<<<<< HEAD
     * @param name the name of the table to delete
     * @return mono void
     */
    public Mono<Void> deleteTable(String name) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     * @return mono void
     */
    public Mono<Void> deleteTable(AzureTable azureTable) {
=======
     * @param name the name of the table to create
     * @return mono void
     */
    public Mono<Void> deleteTable(String name) {
>>>>>>> a9a61c1401... writing in docs
        return Mono.empty();
    }

    /**
<<<<<<< HEAD
     * retrieves the table client for the provided table or creates one if it doesn't exist
     *
     * @param name the name of the table
     * @return associated TableClient
     */
    public Mono<TableClient> getTableClient(String name) {
        return null;
    }

    /**
     * query all the tables under the storage account and return them
     *
     * @param queryOptions the odata query object
     * @return a flux of the tables that met this criteria
     */
    public PagedFlux<AzureTable> queryTables(QueryOptions queryOptions) {
        return null;
    }
=======
     * query all the tables under the storage account and return them
     *
     * @param filterString the odata filter string
     * @return a flux of the tables that met this criteria
     */
    public PagedFlux<AzureTable> queryTables(String filterString) {
        return null;
    }

>>>>>>> a9a61c1401... writing in docs

    /**
     * gets the client for this table
     *
     * @param tableName the table to get the client from
     * @return the table client
     */
    public TableAsyncClient getClient(String tableName) {
        return null;
    }
}
