// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.exception.HttpResponseException;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.models.TableServiceErrorException;
<<<<<<< HEAD
import java.util.List;
=======
import java.util.HashMap;
import java.util.List;
import java.util.Map;
>>>>>>> codeSnippetsTables

/**
 * sync code snippets for the table service
 */
public class TableServiceClientCodeSnippets {
<<<<<<< HEAD

    /**
     * all the functionality of the Tables SDK written sync
     */
    public static void methods() {
        ClientLogger logger = new ClientLogger("TableServiceClientCodeSnippets");

        //create a tableServiceClient
=======
    private final ClientLogger logger = new ClientLogger("TableServiceClientCodeSnippets");

    /**
     * create table code snippet
     */
    public void createTable() {
>>>>>>> codeSnippetsTables
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
            .connectionString("connectionString")
            .buildClient();

<<<<<<< HEAD
        //create TableClient
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        //create a table
=======
>>>>>>> codeSnippetsTables
        try {
            AzureTable officeSuppliesTable = tableServiceClient.createTable("OfficeSupplies");
        } catch (TableServiceErrorException e) {
            logger.error("Create Table Unsuccessful. Error: " + e);
        }
<<<<<<< HEAD

        //delete  table
=======
    }

    /**
     * delete table code snippet
     */
    public void deleteTable() {
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
            .connectionString("connectionString")
            .buildClient();

>>>>>>> codeSnippetsTables
        try {
            tableServiceClient.deleteTable("OfficeSupplies");
        } catch (TableServiceErrorException e) {
            logger.error("Delete Table Unsuccessful. Error: " + e);
        }
<<<<<<< HEAD

        //query tables
=======
    }

    /**
     * query table code snippet
     */
    public void queryTables() {
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
            .connectionString("connectionString")
            .buildClient();

>>>>>>> codeSnippetsTables
        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setFilter("TableName eq OfficeSupplies");

        try {
            List<AzureTable> responseTables = tableServiceClient.queryTables(queryOptions);
        } catch (HttpResponseException e) {
            logger.error("Table Query Unsuccessful. Error: " + e);
        }
<<<<<<< HEAD

        //insert entity
        String row = "crayola markers";
        String partitionKey = "markers";
        TableEntity tableEntity = new TableEntity(row, partitionKey, null);
        try {
            tableEntity = tableClient.insertEntity(tableEntity);
        } catch (HttpResponseException e) {
            logger.error("Insert Entity Unsuccessful. Error: " + e);
        }

        //update entity
        tableEntity.addProperty("Seller", "Crayola");
        try {
            tableClient.updateEntity(tableEntity);
        } catch (HttpResponseException e) {
            logger.error("Update Entity Unsuccessful. Error: " + e);
        }

        //upsert entity (where it is an insert or replace)
        tableEntity.addProperty("Price", "5");
        try {
            tableClient.insertOrReplaceEntity(tableEntity);
        } catch (HttpResponseException e) {
            logger.error("Upsert Entity Unsuccessful. Error: " + e);
        }

        //upsert entity (where it is an insert or merge)
        tableEntity.addProperty("Price", "5");
        try {
            tableClient.insertOrMergeEntity(tableEntity);
        } catch (HttpResponseException e) {
            logger.error("Upsert Entity Unsuccessful. Error: " + e);
        }

        //delete entity
        try {
            tableClient.deleteEntity(tableEntity);
        } catch (HttpResponseException e) {
            logger.error("Delete Entity Unsuccessful. Error: " + e);
        }

        //query a table
        queryOptions.setFilter("Product eq markers");
        queryOptions.setSelect("Seller, Price");
        try {
            List<TableEntity> list = tableClient.queryEntity(queryOptions);
=======
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
        } catch (HttpResponseException e) {
            logger.error("Insert Entity Unsuccessful. Error: " + e);
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

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setFilter("RowKey eq crayolaMarkers");
        List<TableEntity> tableEntities = null;
        try {
            tableEntities = tableClient.queryEntity(queryOptions);
        } catch (HttpResponseException e) {
            logger.error("Query Table Entities Unsuccessful. Error: " + e);
        }
        if (tableEntities != null) {
            TableEntity tableEntity = tableEntities.get(0);
            tableEntity.addProperty("Seller", "Crayola");
            try {
                tableClient.updateEntity(UpdateMode.Replace, tableEntity);
            } catch (HttpResponseException e) {
                logger.error("Update Entity Unsuccessful. Error: " + e);
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

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setFilter("RowKey eq crayolaMarkers");
        List<TableEntity> tableEntities = null;
        try {
            tableEntities = tableClient.queryEntity(queryOptions);
        } catch (HttpResponseException e) {
            logger.error("Query Table Entities Unsuccessful. Error: " + e);
        }
        if (tableEntities != null) {
            TableEntity tableEntity = tableEntities.get(0);
            tableEntity.addProperty("Price", "5");
            try {
                tableClient.upsertEntity(UpdateMode.Replace, tableEntity);
            } catch (HttpResponseException e) {
                logger.error("Upsert Entity Unsuccessful. Error: " + e);
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

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setFilter("RowKey eq crayolaMarkers");
        List<TableEntity> tableEntities = null;
        try {
            tableEntities = tableClient.queryEntity(queryOptions);
        } catch (HttpResponseException e) {
            logger.error("Query Table Entities Unsuccessful. Error: " + e);
        }
        if (tableEntities != null) {
            TableEntity tableEntity = tableEntities.get(0);
            try {
                tableClient.deleteEntity(tableEntity);
            } catch (HttpResponseException e) {
                logger.error("Delete Entity Unsuccessful. Error: " + e);
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

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setFilter("Product eq markers");
        queryOptions.setSelect("Seller, Price");
        try {
            List<TableEntity> tableEntities = tableClient.queryEntity(queryOptions);
>>>>>>> codeSnippetsTables
        } catch (HttpResponseException e) {
            logger.error("Query Table Entities Unsuccessful. Error: " + e);
        }
    }

<<<<<<< HEAD
=======
    /**
     * check to see if a table entity exists
     */
    public void entityExists() {
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .buildClient();

        QueryOptions queryOptions = new QueryOptions();
        queryOptions.setFilter("TableName eq OfficeSupplies");
        try {
            List<TableEntity> responseTables =
                tableClient.queryEntitiesWithPartitionAndRowKey("crayolaMarkers", "markers");
            if (responseTables != null && responseTables.get(0) != null) {
                logger.info("Entity with the rowKey = crayolaMarkers and partitionKey = markers exists.");
            }
        } catch (HttpResponseException e) {
            logger.error("Table Query Unsuccessful. Error: " + e);
        }

    }
>>>>>>> codeSnippetsTables
}
