// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables.models;

import com.azure.core.annotation.Fluent;
import java.util.HashMap;
import java.util.Map;

/**
 * table entity class
 */
@Fluent
public class Entity {
    private Object rowKey;
    private Object partitionKey;
    private Map<String, Object> properties;
    private String etag;
    //tableName
    //etag

    /**
     * creates a new TableEntity
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     */
    public Entity(Object partitionKey, Object rowKey) {
        properties = new HashMap<>();
        setPartitionKey(partitionKey);
        setRowKey(rowKey);
        properties.put("PartitionKey", partitionKey);
        properties.put("RowKey", rowKey);
    }

    public Entity(Map<String, Object> properties) {
        this.properties = properties;
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
     * add a property to an entity
     * @param key the key of the value
     * @param value the value to add
     * @return the updated Entity
     */
    public Entity addProperties(String key, Object value) { return this;}

    /**
     * gets the row key
     * @return the row key for the given entity
     */
    public Object getRowKey() {
        return rowKey;
    }

    /**
     * gets the partition key
     * @return the partition key for the given entity
     */
    public Object getPartitionKey() {
        return partitionKey;
    }

    /**
     * gets the etag
     * @return the etag for the entity
     */
    public String getETag() {
        return etag;
    }

    public Entity setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * sets the partition key parameter
     * @param partitionKey the partition key value
     * @return the updated TableEntity
     */
    public Entity setPartitionKey(Object partitionKey) {
        this.partitionKey = partitionKey;
        return this;
    }

    /**
     * sets the row key
     * @param rowKey value of row key
     * @return updated tableEntity object
     */
    public Entity setRowKey(Object rowKey) {
        this.rowKey = rowKey;
        return this;
    }
}
