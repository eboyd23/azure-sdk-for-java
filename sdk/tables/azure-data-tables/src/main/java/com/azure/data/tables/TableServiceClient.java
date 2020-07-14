// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceClient;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.Response;
import com.azure.core.util.Context;
import reactor.core.publisher.Mono;

/**
 * client for table service
 */
@ServiceClient(
    builder = TableServiceClientBuilder.class)
public class TableServiceClient {
    private final TableServiceAsyncClient client;

    TableServiceClient(TableServiceAsyncClient client) {
        this.client = client;
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @return AzureTable of the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public AzureTable createTable(String name) {
        return client.createTable(name).block();
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @param context the context of the query
     * @return AzureTable of the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public AzureTable createTable(String name, Context context) {
        return null;
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
     * @param context the context of the query
     * @return response with azureTable of the created table
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Response<AzureTable> createTableWithResponse(String name, Context context) {
        return null;
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to be deleted
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteTable(String name) {
        client.deleteTable(name).block();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to be deleted
     * @param context the context of the query
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteTable(String name, Context context) {
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to be deleted
     * @param context the context of the query
     * @return response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTableWithResponse(String name, Context context) {
        return Mono.empty();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to be deleted
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteTable(AzureTable azureTable) {
        client.deleteTable(azureTable.getName()).block();
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to be deleted
     * @param context the context of the query
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public void deleteTable(AzureTable azureTable, Context context) {
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param azureTable the table to be deleted
     * @param context the context of the query
     * @return response
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<Response<Void>> deleteTableWithResponse(AzureTable azureTable, Context context) {
        return Mono.empty();
    }

    /**
     * gets a given table by name
     *
     * @param name the name of the table
     * @return associated azure table object
     */
    public AzureTable getTable(String name) {
        return null;
    }

    /**
     * gets the Table Client for the given table
     *
     * @param name the name of the table
     * @return the Table Client for the table
     */
    public TableClient getTableClient(String name) {
        return null;
    }

    /**
     * query all the tables under the storage account given the query options and returns the ones that fit the
     * criteria
     *
     * @param queryOptions the odata query object
     * @return a list of tables that meet the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<AzureTable> queryTables(QueryParams queryOptions) {
        return null;
    }

    /**
     * query all the tables under the storage account given the query options and returns the ones that fit the
     * criteria
     *
     * @param queryOptions the odata query object
     * @param context context of the query
     * @return a list of tables that meet the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<AzureTable> queryTables(QueryParams queryOptions, Context context) {
        return null;
    }

    /**
     * query all the tables under the storage account given the query options and returns the ones that fit the
     * criteria
     *
     * @param queryOptions the odata query object
     * @param context context of the query
     * @return a response of a list of tables that meet the query
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    Response<PagedIterable<AzureTable>> queryTablesWithResponse(QueryParams queryOptions, Context context) {
        return null;
    }

}
