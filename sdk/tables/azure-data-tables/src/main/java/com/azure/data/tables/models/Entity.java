// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables.models;

import com.azure.core.annotation.Fluent;
import java.util.Map;

/**
 * table entity class
 */
@Fluent
public class Entity {
    private String rowKey;
    private String partitionKey;
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
        properties.put("PartitionKey", partitionKey);
        properties.put("RowKey", rowKey);
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
     * gets the etag
     * @return the etag for the entity
     */
    public String getETag() {
        return etag;
    }

    private Entity setEtag(String etag) {
        this.etag = etag;
        return this;
    }

    /**
     * sets the partition key parameter
     * @param partitionKey the partition key value
     * @return the updated TableEntity
     */
    public Entity setPartitionKey(String partitionKey) {
        this.partitionKey = partitionKey;
        return this;
    }

    /**
     * sets the row key
     * @param rowKey value of row key
     * @return updated tableEntity object
     */
    public Entity setRowKey(String rowKey) {
        this.rowKey = rowKey;
        return this;
    }
}
