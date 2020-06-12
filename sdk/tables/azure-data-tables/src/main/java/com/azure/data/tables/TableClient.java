
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

    public TableClient(){ }

    public void createTable(String name){ }

    public void createTableIfNotExist(String name) { }

    public void deleteTable(String name) { }

    public List<String> queryTables(String selectString, String filterString){
        return null;
    }

    public List<TableEntity> queryEntity(String az, String selectString, String filterString){
        return null;
    }

    public TableEntity insertEntity(String tableName, String row, String partition, Map<String, Object> tableEntityProperties){
        return new TableEntity(null, null);
    }
    public TableEntity insertEntity(TableEntity te){
        return te;
    }
    public void deleteEntity(TableEntity tableEntity){ }

    public void updateEntity(TableEntity te){ }
    public TableEntity upsertEntity(TableEntity te){ return new TableEntity(null, null); }
}
