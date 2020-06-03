<<<<<<< HEAD
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

/**
 * class for a table object
 */
public class AzureTable {
    private final String name;
    private TableClient tableClient;
=======
package com.azure.data.tables;

import reactor.core.publisher.Mono;

import java.time.Duration;

public class AzureTable {
    private final String name;
>>>>>>> b945b82e8f... Add examples.

    AzureTable(String name) {
        this.name = name;
    }

<<<<<<< HEAD
    /**
     * returns the name of this table
     *
     * @return table name
     */
=======
>>>>>>> b945b82e8f... Add examples.
    public String getName() {
        return name;
    }

<<<<<<< HEAD
    /**
     * returns the associated table client or null if it doesn't exist
     * @return the associated table client
     */
    public TableClient getClient() {
        return tableClient;
=======
    public Mono<AzureTableEntity> createEntity(String key, Object value) {
        AzureTableEntity azureTableEntity = new AzureTableEntity(key, value);
        System.out.println("Creating entity with key: " +  key + ". Value: " + value);
        return Mono.delay(Duration.ofSeconds(3)).thenReturn(azureTableEntity);
>>>>>>> b945b82e8f... Add examples.
    }
}
