// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClientBuilder;

<<<<<<< HEAD
/**
 * builds table client
 */
=======
>>>>>>> 044356b812... fixing conflictfile name changes
@ServiceClientBuilder(serviceClients = {TableClient.class, TableAsyncClient.class})
public class TableClientBuilder {
    private String connectionString;
    private String tableName;

<<<<<<< HEAD
    /**
     * Sets the connection string to help build the client
     *
     * @param connectionString the connection string to the storage account
     * @return the TableClientBuilder
     */

=======
>>>>>>> 42b1fba620... stashing changes
    /**
     * Sets the connection string to help build the client
     *
     * @param connectionString the connection string to the storage account
     * @return the TableClientBuilder
     */
    public TableClientBuilder connectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    /**
     * Sets the table name to help build the client
     *
     * @param tableName name of the table for which the client is created for
     * @return the TableClientBuilder
     */
    public TableClientBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a9a61c1401... writing in docs
    /**
     * builds a sync tableClient
     *
     * @return a sync tableClient
     */
<<<<<<< HEAD
    public TableClient buildClient() {
        return new TableClient(null);
    }

    /**
     * builds an async tableClient
     *
     * @return an aysnc tableClient
     */
    public TableAsyncClient buildAsyncClient() {
        return new TableAsyncClient(null);
    }

    TableClientBuilder() {
    }
=======
=======
>>>>>>> a9a61c1401... writing in docs
    public TableClient buildClient() {
        return new TableClient(tableName);
    }

    /**
     * builds an async tableClient
     *
     * @return an aysnc tableClient
     */
    public TableAsyncClient buildAsyncClient() {
        return new TableAsyncClient(tableName);
    }

    TableClientBuilder() {
<<<<<<< HEAD
>>>>>>> 044356b812... fixing conflictfile name changes

    /**
     * gets the connection string
     * @return the connection string
     */
    public String getConnectionString(){
        return this.connectionString;
=======
>>>>>>> 42b1fba620... stashing changes
    }

}
