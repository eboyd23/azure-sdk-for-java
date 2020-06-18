// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.


import java.util.Map;

/**
 * table entity class
 */
public class TableEntity {
<<<<<<< HEAD
    private String rowKey;
    private String partitionKey;
    private Map<String, Object> properties;
=======
    Map<String, Object> properties;

    TableEntity() {
>>>>>>> a9a61c1401... writing in docs

    /**
     * creates a new TableEntity
     *
     * @param rowKey rowKey
     * @param partitionKey partitionKey
     * @param properties map of properties of the entity
     */
    TableEntity(String rowKey, String partitionKey, Map<String, Object> properties) {
        this.rowKey = rowKey;
        this.partitionKey = partitionKey;
        this.properties = properties;
    }

    /**
     * creates a new TableEntity
     *
<<<<<<< HEAD
     * @param rowKey rowKey
     * @param partitionKey partitionKey
     */
    TableEntity(String rowKey, String partitionKey) {
        this.rowKey = rowKey;
        this.partitionKey = partitionKey;
=======
     * @param table      table which the TableEntity exists in
     * @param row        rowKey
     * @param partition  partitionKey
     * @param properties map of properties of the entity
     */
    TableEntity(String table, String row, String partition, Map<String, Object> properties) {
        this.properties = properties;
>>>>>>> a9a61c1401... writing in docs
    }

    /**
     * returns a map of properties
     *
     * @return map of properties of thsi entity
     */
    public Map<String, Object> getProperties() {
        return properties;
    }

    /**
<<<<<<< HEAD
     * gets the row key
     * @return the row key for the given entity
     */
    public String getRowKey() {
        return rowKey;
    }

    /**
     * gets the partition key
     * @return the partition key for the given entity
     */
    public String getPartitionKey() {
        return partitionKey;
    }


    /**
     * adds a new property to this entity's property map
     *
     * @param key the key of the property
=======
     * adds a new property to this entity's property map
     *
     * @param key   the key of the property
>>>>>>> a9a61c1401... writing in docs
     * @param value the value of the property
     */
    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    /**
     * set the properties
     * @param properties properties to set to this entity
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}
