// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;

import java.util.List;

<<<<<<< HEAD
/**
 * client for table service
 */
=======
>>>>>>> 044356b812... fixing conflictfile name changes
@ServiceClient(
    builder = TableServiceClientBuilder.class)
public class TableServiceClient {

    TableServiceClient() {
    }

    /**
     * creates the table with the given name.  If a table with the same name already exists, the operation fails.
     *
     * @param name the name of the table to create
<<<<<<< HEAD
     * @return AzureTable of the created table
     */
    public AzureTable createTable(String name) {
        return null;
    }

<<<<<<< HEAD
    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
     * @param name the name of the table to be deleted
     */
    public void deleteTable(String name) {
=======
     */
    public void createTable(String name) {
>>>>>>> a9a61c1401... writing in docs
    }

    /**
     * deletes the given table. Will error if the table doesn't exists or cannot be found with the given name.
     *
<<<<<<< HEAD
     * @param azureTable the table to be deleted
     */
    public void deleteTable(AzureTable azureTable) {
    }

    /**
     * gets a given table by name
     *
     * @param name the name of the table
     * @return associated azure table object
     */
    public AzureTable getTable(String name) {
        return null;
=======
=======
     * @param name
     */
>>>>>>> a9a61c1401... writing in docs
    public void deleteTable(String name) {
>>>>>>> 044356b812... fixing conflictfile name changes
    }

    /**
     * query all the tables under the storage account and return them
     *
<<<<<<< HEAD
     * @param queryOptions the odata query object
     * @return a list of tables that meet the query
     */
    public List<AzureTable> queryTables(QueryOptions queryOptions) {
=======
     * @param filterString the odata filter string
     * @return a list of tables that meet the query
     */
    public List<AzureTable> queryTables(String filterString) {
>>>>>>> a9a61c1401... writing in docs
        return null;
    }

}
