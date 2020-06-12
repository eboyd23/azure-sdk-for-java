
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.data.tables.TableClient;

/**
 * class for a table object
 */
public class AzureTable {
    private final String name;
    private TableClient tableClient;

    /**
     * returns the name of this table
     *
     * @return table name
     */
    public String getName() {
        return name;
    }

    /**
     * returns the associated table client or null if it doesn't exist
     * @return the associated table client
     */
    public TableClient getClient() {
        return tableClient;

    }

    public AzureTable(String name) {
        this.name = name;
    }
}
