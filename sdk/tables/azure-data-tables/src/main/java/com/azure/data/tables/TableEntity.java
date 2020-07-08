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
    private String rowKey;
    private String partitionKey;
    private Map<String, Object> properties;
    private String etag;
    //tableName
    //etag

    /**
     * creates a new TableEntity
     *
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
     * @param rowKey rowKey
     * @param partitionKey partitionKey
     */
    TableEntity(String rowKey, String partitionKey) {
        this.rowKey = rowKey;
        this.partitionKey = partitionKey;
    }

    /**
     * creates a new TableEntity
     *
     * @param properties map of properties of the entity
     * @param etag the etag of the entity
     */
    public TableEntity(Map<String, Object> properties, String etag) {
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
     * @param value the value of the property
     */
    public void addProperty(String key, Object value) {
        properties.put(key, value);
    }

    /**
     * set the properties
     *
     * @param properties properties to set to this entity
     */
    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }

    /**
     * gets the etag
     * @return the etag for the entity
     */
    public String getEtag() {
        return etag;
    }

    /**
     * sets the etag for the entity
     * @param etag
     */
    public void setEtag(String etag) {
        this.etag = etag;
    }
}
