
// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClient;
import java.util.List;
import java.util.Map;

/**
 * sync client for table operations
 */
@ServiceClient(
    builder = TableClientBuilder.class)
public class TableClient {
    String tableName;

    public TableClient(String tableName) {
        this.tableName = tableName;
    }

    public List<TableEntity> queryEntity(String az, String selectString, String filterString) {
        return null;
    }

<<<<<<< HEAD
    public void deleteEntity(TableEntity tableEntity){ }

    public void updateEntity(TableEntity te){ }

    public TableEntity upsertEntity(TableEntity te){ return new TableEntity(null, null); }

=======
    public TableEntity insertEntity(String row, String partition, Map<String, Object> tableEntityProperties) {
        return new TableEntity();
    }

    public TableEntity insertEntity(TableEntity tableEntity) {
        return tableEntity;
    }

    public void deleteEntity(TableEntity tableEntity) {
    }

    public void updateEntity(TableEntity te) {
    }

    public void updateAndReplaceEntity(TableEntity tableEntity) {
    }

    public void updateAndMergeEntity(TableEntity tableEntity) {
    }
>>>>>>> 7245126b7a... reformatting
}
