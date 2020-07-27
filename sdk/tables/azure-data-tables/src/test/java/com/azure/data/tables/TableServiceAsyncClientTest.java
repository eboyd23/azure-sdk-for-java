// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.data.tables;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.policy.AddHeadersPolicy;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.test.TestBase;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.data.tables.models.QueryParams;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;


public class TableServiceAsyncClientTest extends TestBase {
    private String connectionString = System.getenv("AZURE_TABLES_CONNECTION_STRING");
    private final ClientLogger logger = new ClientLogger(TableServiceAsyncClientTest.class);

    private TableServiceClient client;
    private TableServiceClient clientEndpoint;
    private TableServiceAsyncClient asyncClient;
    private TableAsyncClient asyncClientTable;

    private static final String PARTITION_KEY = "PartitionKey";
    private static final String ROW_KEY = "RowKey";


    @Override
    protected void beforeTest() {
        client = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildClient();

        asyncClient = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new AddHeadersPolicy(new HttpHeaders().put("Accept",
                OdataMetadataFormat.APPLICATION_JSON_ODATA_MINIMALMETADATA.toString())))
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildAsyncClient();

        StorageConnectionString storageConnectionString
            = StorageConnectionString.create(connectionString, logger);

        clientEndpoint = new TableServiceClientBuilder()
            .endpoint(storageConnectionString.getTableEndpoint().getPrimaryUri())
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildClient();


    }

    @Test
    void createTableAsync() {
        // Arrange
        String tableName = testResourceNamer.randomName("test", 20);

        //Act & Assert
        StepVerifier.create(asyncClient.createTable(tableName))
            .assertNext(response -> {
                Assertions.assertEquals(tableName, response.getName());

            })
            .expectComplete()
            .verify();
    }

    @Test
    void createTableWithResponseAsync() {
        // Arrange
        String tableName = testResourceNamer.randomName("test", 20);
        int expectedStatusCode = 201;

        //Act & Assert
        StepVerifier.create(asyncClient.createTableWithResponse(tableName))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());

            })
            .expectComplete()
            .verify();
    }

    @Test
    void deleteTableAsync() {
        // Arrange
        String tableName = testResourceNamer.randomName("test", 20);
        asyncClient.createTable(tableName).block();

        //Act & Assert
        StepVerifier.create(asyncClient.deleteTable(tableName))
            .expectComplete()
            .verify();
    }

    @Test
    void deleteTableWithResponseAsync() {
        // Arrange
        String tableName = testResourceNamer.randomName("test", 20);
        int expectedStatusCode = 204;
        asyncClient.createTable(tableName).block();

        //Act & Assert
        StepVerifier.create(asyncClient.deleteTableWithResponse(tableName))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    void listTableWithResponseWithParams() {
        // Arrange
        QueryParams queryParams = new QueryParams().setFilter("TableName eq SampleTable");

        //Act & Assert
        StepVerifier.create(asyncClient.listTables(queryParams))
            .assertNext(table -> {
                System.out.print(table);
            })
            .expectComplete()
            .verify();

    }
}
