// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.data.tables.models;

import com.azure.core.annotation.Fluent;
import com.azure.data.tables.implementation.EntityHelper;
import com.azure.data.tables.implementation.TableConstants;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * table entity class
 */
@Fluent
public class Entity {
    private final String partitionKey;
    private final String rowKey;
    private final Map<String, Object> properties = new HashMap<>();

    private String eTag;

    static {
        // This is used by classes in different packages to get access to private and package-private methods.
        EntityHelper.setEntityAccessor((entity, name) -> entity.setETag(name));
    }

    /**
     * creates a new TableEntity
     *
     * @param partitionKey the partition key
     * @param rowKey the row key
     */
    public Entity(String partitionKey, String rowKey) {
        this(partitionKey, rowKey, Collections.emptyMap());
    }

    private Entity(String partitionKey, String rowKey, Map<String, Object> properties) {
        this.rowKey = Objects.requireNonNull(rowKey, "'rowKey' cannot be null.");
        this.partitionKey = Objects.requireNonNull(partitionKey, "'partitionKey' cannot be null.");
        Objects.requireNonNull(properties, "'properties' cannot be null.");

        properties.put(TableConstants.PARTITION_KEY, partitionKey);
        properties.put(TableConstants.ROW_KEY, rowKey);
        this.properties.putAll(properties);
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
     *
     * @return the row key for the given entity
     */
    public String getRowKey() {
        return rowKey;
    }

    /**
     * gets the partition key
     *
     * @return the partition key for the given entity
     */
    public String getPartitionKey() {
        return partitionKey;
    }

    /**
     * gets the etag
     *
     * @return the etag for the entity
     */
    public String getETag() {
        return eTag;
    }

    /**
     * Sets the ETag on the Entity.
     *
     * @param eTag ETag to set.
     */
    void setETag(String eTag) {
        this.eTag = eTag;
    }
}
