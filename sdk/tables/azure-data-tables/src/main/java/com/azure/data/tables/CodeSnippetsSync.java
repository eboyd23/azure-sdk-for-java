package com.azure.data.tables;

import com.azure.core.exception.HttpResponseException;
import com.azure.data.tables.implementation.models.TableServiceErrorException;

import java.util.HashMap;
import java.util.List;

public class CodeSnippetsSync {

    public static void methods() {

        //create a tableServiceClient
        TableServiceClient tableServiceClient = new TableServiceClientBuilder()
            .connectionString("connectionString")
            .build();

        //create TableClient
        TableClient tableClient = new TableClientBuilder()
            .connectionString("connectionString")
            .tableName("OfficeSupplies")
            .build();


        //create a table
        try {
            tableServiceClient.createTable("OfficeSupplies");
        } catch (TableServiceErrorException e) {
            //use azure core errors? based on
            System.out.println("Create Table Unsuccessful. Error: " + e);
        }

        //delete  table
        try {
            tableServiceClient.deleteTable("OfficeSupplies");
        } catch (TableServiceErrorException e) {
            System.out.println("Delete Table Unsuccessful. Error: " + e);
        }

        //query tables
        String selectString = "$selectString= TableName eq 'OfficeSupplies'";

        try {
            //TODO: create Table class TableName is the odata feild
            List<AzureTable> responseTables = tableServiceClient.queryTables(selectString);
        } catch (HttpResponseException e) {
            System.out.println("Table Query Unsuccessful. Error: " + e);
        }


        //insert entity
        String tableName = "OfficeSupplies";
        String row = "crayola markers";
        String partitionKey = "markers";
        HashMap<String, Object> tableEntityProperties = new HashMap<>();
        TableEntity tableEntity = new TableEntity(tableName, row, partitionKey, tableEntityProperties);
        try {
            tableEntity = tableClient.insertEntity(tableEntity);
        } catch (HttpResponseException e) {
            System.out.println("Insert Entity Unsuccessful. Error: " + e);
        }


        //update entity
        tableEntity.addProperty("Seller", "Crayola");
        try {
            tableClient.updateEntity(tableEntity);
        } catch (HttpResponseException e) {
            System.out.println("Update Entity Unsuccessful. Error: " + e);
        }


        //upsert entity (where it is an update and replace)
        tableEntity.addProperty("Price", "5");
        try {
            tableClient.updateAndReplaceEntity(tableEntity);
        } catch (HttpResponseException e) {
            System.out.println("Upsert Entity Unsuccessful. Error: " + e);
        }

        //upsert entity (where it is an update and replace)
        tableEntity.addProperty("Price", "5");
        try {
            tableClient.updateAndMergeEntity(tableEntity);
        } catch (HttpResponseException e) {
            System.out.println("Upsert Entity Unsuccessful. Error: " + e);
        }


        //delete entity
        try {
            tableClient.deleteEntity(tableEntity);
        } catch (HttpResponseException e) {
            System.out.println("Delete Entity Unsuccessful. Error: " + e);
        }


        //query a table
        String filterString2 = "$filter = Product eq 'markers'";
        String selectString2 = "$select = Seller eq 'crayola'";
        try {
            List<TableEntity> list = tableClient.queryEntity(tableName, filterString2, selectString2);
        } catch (HttpResponseException e) {
            System.out.println("Query Table Entities Unsuccessful. Error: " + e);
        }
    }

}
