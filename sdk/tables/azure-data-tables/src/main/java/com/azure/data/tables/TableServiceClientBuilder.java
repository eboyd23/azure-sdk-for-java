// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClientBuilder;

<<<<<<< HEAD
/**
 * builds the table service clients
 */
@ServiceClientBuilder(serviceClients = {TableServiceClient.class, TableServiceAsyncClient.class})
public class TableServiceClientBuilder {

    private String connectionString;
    
=======
>>>>>>> a9a61c1401... writing in docs
    /**
     * Sets the connection string to help build the client
     *
     * @param connectionString the connection string to the storage account
     * @return the TableServiceClientBuilder
     */
    public TableServiceClientBuilder connectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

<<<<<<< HEAD
<<<<<<< HEAD
=======
>>>>>>> a9a61c1401... writing in docs
    /**
     * builds a sync TableServiceClient
     *
     * @return a sync TableServiceClient
     */
<<<<<<< HEAD
=======
>>>>>>> 044356b812... fixing conflictfile name changes
=======
>>>>>>> a9a61c1401... writing in docs
    public TableServiceClient buildClient() {
        return new TableServiceClient();
    }

<<<<<<< HEAD
<<<<<<< HEAD
    /**
     * builds an async TableServiceAsyncClient
     *
     * @return TableServiceAsyncClient an aysnc TableServiceAsyncClient
     */
=======
>>>>>>> 044356b812... fixing conflictfile name changes
=======
    /**
     * builds an async TableServiceAsyncClient
     *
     * @return an aysnc TableServiceAsyncClient
     */
>>>>>>> a9a61c1401... writing in docs
    public TableServiceAsyncClient buildAsyncClient() {
        return new TableServiceAsyncClient();
    }

<<<<<<< HEAD
    /**
     * constructor
     */
=======
>>>>>>> 044356b812... fixing conflictfile name changes
    public TableServiceClientBuilder() {

    }

    /**
     * gets the connection string
     * @return the connection string
     */
    public String getConnectionString(){
        return this.connectionString;
    }

}
