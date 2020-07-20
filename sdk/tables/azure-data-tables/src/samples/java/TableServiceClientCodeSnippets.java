// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.exception.HttpResponseException;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.util.logging.ClientLogger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * sync code snippets for the table service
 */
public class TableServiceClientCodeSnippets {
    private final ClientLogger logger = new ClientLogger("TableServiceClientCodeSnippets");

    /**
     * create table code snippet
     */
    public void createTable() {
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
            .connectionString("connectionString")
            .buildClient();
        try {
            AzureTable officeSuppliesTable = tableServiceClient.createTable("OfficeSupplies");
        } catch (TableStorageException e) {
            if (e.getErrorCode() == TableErrorCode.TABLE_ALREADY_EXISTS) {
                System.err.println("Create Table Unsuccessful. Table already exists.");
            } else if (e.getErrorCode() == TableErrorCode.INVALID_TABLE_NAME) {
                System.err.println("Create Table Unsuccessful. Table name invalid");
            } else {
                System.err.println("Create Table Unsuccessful. " + e);
            }
        }
    }

    /**
     * delete table code snippet
     */
    public void deleteTable() {
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
            .connectionString("connectionString")
            .buildClient();

        try {
            tableServiceClient.deleteTable("OfficeSupplies");
        } catch (TableStorageException e) {
            if (e.getErrorCode() == TableErrorCode.TABLE_NOT_FOUND) {
                System.err.println("Delete Table Unsuccessful. Table not found.");
            } else {
                System.err.println("Delete Table Unsuccessful. Error: " + e);
            }
        }
    }

    /**
     * query table code snippet
     */
    public void queryTables() {
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
            .connectionString("connectionString")
            .buildClient();

        QueryParams queryParams = new QueryParams().setFilter("TableName eq OfficeSupplies");

        try {
            PagedIterable<AzureTable> responseTables = tableServiceClient.queryTables(queryParams);
        } catch (TableStorageException e) {
            System.err.println("Table Query Unsuccessful. Error: " + e);
        }
    }

    /**
     * insert entity code snippet
     */
    private void insertEntity() {
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        Map<String, Object> properties = new HashMap<>();
        properties.put("RowKey", "crayolaMarkers");
        properties.put("PartitionKey", "markers");
        try {
            TableEntity tableEntity = tableClient.createEntity(properties);
        } catch (TableStorageException e) {
            if (e.getErrorCode() == TableErrorCode.ENTITY_ALREADY_EXISTS) {
                System.err.println("Create Entity Unsuccessful. Entity already exists.");
            } else if (e.getErrorCode() == TableErrorCode.INVALID_PK_OR_RK_NAME) {
                System.err.println("Create Table Unsuccessful. Row key or Partition key is unvalid.");
            } else {
                System.err.println("Create Entity Unsuccessful. " + e);
            }
        }
    }

    /**
     * update entity code snippet
     */
    private void updateEntity() {
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        String rowKey = "crayolaMarkers";
        String partitionKey = "markers";
        TableEntity tableEntity = null;
        try {
            tableEntity = tableClient.getEntity(rowKey, partitionKey);
            tableClient.updateEntity(UpdateMode.REPLACE, tableEntity,true);
        } catch (TableStorageException e) {
            if (e.getErrorCode() == TableErrorCode.ENTITY_NOT_FOUND) {
                System.err.println("Cannot find entity. Update unsuccessful");
            } else {
                System.err.println("Update Entity Unsuccessful. Error: " + e);
            }
        }
    }

    /**
     * upsert entity code snippet
     */
    private void upsertEntity() {
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        String rowKey = "crayolaMarkers";
        String partitionKey = "markers";
        TableEntity tableEntity = null;
        try {
            tableEntity = tableClient.getEntity(rowKey, partitionKey);
            tableClient.upsertEntity(UpdateMode.REPLACE, tableEntity, true);
        } catch (TableStorageException e) {
            if (e.getErrorCode() == TableErrorCode.ENTITY_NOT_FOUND) {
                System.err.println("Cannot find entity. Upsert unsuccessful");
            } else {
                System.err.println("Upsert Entity Unsuccessful. Error: " + e);
            }
        }
    }

    /**
     * delete entity code snippet
     */
    private void deleteEntity() {
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        String rowKey = "crayolaMarkers";
        String partitionKey = "markers";
        TableEntity tableEntity = null;
        try {
            tableEntity = tableClient.getEntity(rowKey, partitionKey);
            tableClient.deleteEntity(tableEntity, true);
        } catch (TableStorageException e) {
            if (e.getErrorCode() == TableErrorCode.ENTITY_NOT_FOUND) {
                System.err.println("Delete Entity Unsuccessful. Entity not found.");
            } else {
                System.err.println("Delete Entity Unsuccessful. Error: " + e);
            }
        }
    }

    /**
     * query entity code snippet
     */
    private void queryEntity() {
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        QueryParams queryParams = new QueryParams()
            .setFilter("Product eq markers")
            .setSelect("Seller, Price");
        try {
            List<TableEntity> tableEntities = tableClient.queryEntities(queryParams);
        } catch (TableStorageException e) {
            System.err.println("Query Table Entities Unsuccessful. Error: " + e);
        }
    }

    /**
     * check to see if a table entity exists
     */
    public void entityExists() {
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        String rowKey = "crayolaMarkers";
        String partitionKey = "markers";
        try {
            TableEntity tableEntity = tableClient.getEntity(rowKey, partitionKey);
        } catch (TableStorageException e) {
            System.err.println("Get Entity Unsuccessful. Entity may not exist: " + e);
        }
    }
}
