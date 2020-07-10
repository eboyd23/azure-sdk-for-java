// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import com.azure.data.tables.implementation.TablesImpl;
import reactor.core.publisher.Mono;

/**
 * async client for account operations
 */
@ServiceClient(
    builder = TableServiceClientBuilder.class,
    isAsync = true)
public class TableServiceAsyncClient {
    private final TablesImpl impl = null;

    TableServiceAsyncClient() {
    }

    /**
     * retrieves the async table client for the provided table or creates one if it doesn't exist
     *
     * @param name the name of the table
     * @return associated TableAsyncClient
     */
    public TableAsyncClient getTableAsyncClient(String name) {
        return null;
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @return the azure table object for the created table
     */
    public Mono<AzureTable> createTable(String name) {
        return null;
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @return a response wth the azure table object for the created table
     */
    public Mono<Response<AzureTable>> createTableWithResponse(String name) {
        return null;
    }

    Mono<Response<AzureTable>> createTableWithResponse(String name, Context context) {
        return null;
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to delete
     * @return mono void
     */
    public Mono<Void> deleteTable(String name) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to delete
     * @return a response
     */
    public Mono<Response<Void>> deleteTableWithResponse(String name) {
        return Mono.empty();
    }

    Mono<Response<Void>> deleteTableWithResponse(String name, Context context) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     * @return mono void
     */
    public Mono<Void> deleteTable(AzureTable azureTable) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to delete
     * @return a response
     */
    public Mono<Response<Void>> deleteTableWithResponse(AzureTable azureTable) {
        return Mono.empty();
    }

    Mono<Response<Void>> deleteTableWithResponse(AzureTable azureTable, Context context) {
        return Mono.empty();
    }

    /**
     * query all the tables under the storage account and returns the tables that fit the query params
     *
     * @param queryOptions the odata query object
     * @return a flux of the tables that met this criteria
     */
    public PagedFlux<AzureTable> queryTables(QueryOptions queryOptions) {
        return null;
    }

    /**
     * query all the tables under the storage account and returns the tables that fit the query params
     *
     * @param queryOptions the odata query object
     * @return a flux of the table responses that met this criteria
     */
    public PagedFlux<Response<AzureTable>> queryTablesWithResponse(QueryOptions queryOptions) {
        return null;
    }

    PagedFlux<Response<AzureTable>> queryTablesWithResponse(QueryOptions queryOptions, Context context) {
        return null;
    }
}
