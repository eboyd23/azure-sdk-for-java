// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.data.tables;

import com.azure.core.http.HttpClient;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.test.TestBase;
import com.azure.data.tables.models.QueryParams;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

/**
 * Tests methods for {@link TableServiceAsyncClient}.
 */
public class TableServiceAsyncClientTest extends TestBase {
    private TableServiceAsyncClient asyncClient;

    @Override
    protected void beforeTest() {
        final String connectionString = TestUtils.getConnectionString(interceptorManager.isPlaybackMode());
        final TableServiceClientBuilder builder = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .httpLogOptions(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS));

        if (interceptorManager.isPlaybackMode()) {
            builder.httpClient(interceptorManager.getPlaybackClient());
        } else {
            builder.httpClient(HttpClient.createDefault())
                .addPolicy(interceptorManager.getRecordPolicy())
                .addPolicy(new RetryPolicy());
        }

        asyncClient = builder.buildAsyncClient();
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

    @Disabled("TODO: Not working at the moment.")
    @Test
    void listTableWithResponseWithParams() {
        // Arrange
        QueryParams queryParams = new QueryParams().setFilter("TableName eq SampleTable");

        // Act & Assert
        StepVerifier.create(asyncClient.listTables(queryParams))
            .assertNext(table -> {
                System.out.print(table);
            })
            .expectComplete()
            .verify();

    }
}
