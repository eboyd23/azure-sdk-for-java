// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.data.tables;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.policy.AddHeadersPolicy;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.test.TestBase;
import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.data.tables.implementation.models.QueryOptions;
import com.azure.data.tables.models.Entity;
import com.azure.data.tables.models.QueryParams;
import com.azure.data.tables.models.UpdateMode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class TablesAsyncClientTest extends TestBase {
    private String connectionString = System.getenv("AZURE_TABLES_CONNECTION_STRING");
    private final ClientLogger logger = new ClientLogger(TableServiceAsyncClientTest.class);
    private final String sas = System.getenv("AZURE_TABLES_SAS");
    private String tableName = null;


    private TableAsyncClient asyncClient;


    private static final String PARTITION_KEY = "PartitionKey";
    private static final String ROW_KEY = "RowKey";


    @Override
    protected void beforeTest() {
        tableName = testResourceNamer.randomName("tableName", 20);
        asyncClient = new TableClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new AddHeadersPolicy(new HttpHeaders().put("Accept",
                OdataMetadataFormat.APPLICATION_JSON_ODATA_FULLMETADATA.toString())))
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .addPolicy(interceptorManager.getRecordPolicy())
            .tableName(tableName)
            .buildAsyncClient();

        asyncClient.create().block();

    }

    @Test
    void createTableAsync() {
        //Arrange
        String tableName2 = testResourceNamer.randomName("tableName", 20);
        TableAsyncClient asyncClient2 = new TableClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new AddHeadersPolicy(new HttpHeaders().put("Accept",
                OdataMetadataFormat.APPLICATION_JSON_ODATA_FULLMETADATA.toString())))
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .addPolicy(interceptorManager.getRecordPolicy())
            .tableName(tableName2)
            .buildAsyncClient();

        //Act & Assert
        StepVerifier.create(asyncClient2.create())
            .assertNext(response -> {
                Assertions.assertEquals(tableName2, response.getName());

            })
            .expectComplete()
            .verify();
    }

    @Test
    void createEntityAsync() {
        //Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        //Act & Assert
        StepVerifier.create(asyncClient.createEntity(tableEntity))
            .assertNext(response -> {
                Assertions.assertEquals(response.getPartitionKey(), partitionKeyValue);
                Assertions.assertEquals(response.getRowKey(), rowKeyValue);
                Assertions.assertNotNull(response.getETag());

            })
            .expectComplete()
            .verify();
    }

    @Test
    void createEntityWithResponse() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity entity = new Entity(partitionKeyValue, rowKeyValue);
        int expectedStatusCode = 201;

        // Act & Assert
        StepVerifier.create(asyncClient.createEntityWithResponse(entity))
            .assertNext(response -> {
                Assertions.assertEquals(response.getValue().getPartitionKey(), partitionKeyValue);
                Assertions.assertEquals(response.getValue().getRowKey(), rowKeyValue);
                Assertions.assertNotNull(response.getValue().getETag());
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void deleteEntityAsync() {
        //Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        asyncClient.createEntity(tableEntity).block();

        //Act & Assert
        StepVerifier.create(asyncClient.deleteEntity(tableEntity))
            .expectComplete()
            .verify();
    }

    @Test
    void deleteEntityWithResponse() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        asyncClient.createEntity(tableEntity).block();
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.deleteEntityWithResponse(tableEntity, false))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void deleteEntityWithResponseMatchEtag() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity = asyncClient.createEntity(tableEntity).block();
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.deleteEntityWithResponse(tableEntity, true))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void getEntityWithResponseTHIS() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity = asyncClient.createEntity(tableEntity).block();
        int expectedStatusCode = 200;
        System.out.println("HERE " + tableEntity.getETag());

        // Act & Assert
        StepVerifier.create(asyncClient.getEntityWithResponse(tableEntity.getPartitionKey().toString(), tableEntity.getRowKey().toString()))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
                System.out.println("ODATA " + response.getHeaders().get("etag"));
            })
            .expectComplete()
            .verify();
    }

    /**
     * expect to see both propertyA and propertyB since the UpdateMode is MERGE
     */
    @Test
    void updateEntityWithResponseMerge() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("ApartitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("ArowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity.addProperties("propertyA", "valueA");
        tableEntity = asyncClient.createEntity(tableEntity).block();
        tableEntity.getProperties().remove("propertyA");
        tableEntity.addProperties("propertyB", "valueB");
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.updateEntityWithResponse(tableEntity, true, UpdateMode.MERGE))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    /**
     * expect to see both propertyA and propertyB since the UpdateMode is MERGE
     */
    @Test
    void updateEntityWithResponseMergeUnconditional() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("ApartitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("ArowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity.addProperties("propertyA", "valueA");
        tableEntity = asyncClient.createEntity(tableEntity).block();
        tableEntity.getProperties().remove("propertyA");
        tableEntity.addProperties("propertyB", "valueB");
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.updateEntityWithResponse(tableEntity, false, UpdateMode.MERGE))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    /**
     * expect to see only propertyB since the UpdateMode is REPLACE
     */
    @Test
    void updateEntityWithResponseReplace() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("ApartitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("ArowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity.addProperties("propertyA", "valueA");
        tableEntity = asyncClient.createEntity(tableEntity).block();
        tableEntity.getProperties().remove("propertyA");
        tableEntity.addProperties("propertyB", "valueB");
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.updateEntityWithResponse(tableEntity, true, UpdateMode.REPLACE))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    /**
     * expect to see only propertyB since the UpdateMode is REPLACE
     */
    @Test
    void updateEntityWithResponseReplaceUnconditional() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("ApartitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("ArowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity.addProperties("propertyA", "valueA");
        tableEntity = asyncClient.createEntity(tableEntity).block();
        tableEntity.getProperties().remove("propertyA");
        tableEntity.addProperties("propertyB", "valueB");
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.updateEntityWithResponse(tableEntity, false, UpdateMode.REPLACE))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void listEntityWithFilter() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity entity = new Entity(partitionKeyValue, rowKeyValue);
        QueryParams queryParams1 = new QueryParams().setFilter("PartitionKey eq '" + entity.getPartitionKey() + "'");
        asyncClient.createEntity(entity).block();

        // Act & Assert
        StepVerifier.create(asyncClient.listEntities(queryParams1))
            .assertNext(returnEntity -> {
                Assertions.assertEquals(partitionKeyValue, returnEntity.getPartitionKey());
                Assertions.assertEquals(entity.getRowKey(), returnEntity.getRowKey());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void listEntityWithSelect() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity entity = new Entity(partitionKeyValue, rowKeyValue)
            .addProperties("propertyC", "valueC")
            .addProperties("propertyD", "valueD");
        QueryParams queryParams = new QueryParams()
            .setFilter("PartitionKey eq '" + entity.getPartitionKey() + "'")
            .setSelect("propertyC");
        asyncClient.createEntity(entity).block();

        // Act & Assert
        StepVerifier.create(asyncClient.listEntities(queryParams))
            .assertNext(returnEntity -> {
                Assertions.assertEquals(entity.getRowKey(), returnEntity.getRowKey());
                Assertions.assertEquals(entity.getPartitionKey(), returnEntity.getPartitionKey());
                Assertions.assertEquals("valueC", returnEntity.getProperties().get("propertyC"));
                Assertions.assertEquals(3, returnEntity.getProperties().size());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void listEntityWithTop() {
        // Arrange
        QueryParams queryParams = new QueryParams()
            .setTop(1);

        // Act & Assert
        StepVerifier.create(asyncClient.listEntities(queryParams))
            .expectNextCount(2);
    }

}
