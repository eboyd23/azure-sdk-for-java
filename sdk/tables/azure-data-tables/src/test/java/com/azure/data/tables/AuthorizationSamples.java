package com.azure.data.tables;

import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpHeaders;
import com.azure.core.http.policy.AddDatePolicy;
import com.azure.core.http.policy.AddHeadersPolicy;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.test.TestBase;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.data.tables.implementation.models.TableProperties;
import com.azure.data.tables.models.Entity;
import com.azure.data.tables.models.QueryParams;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import com.ctc.wstx.shaded.msv_core.verifier.regexp.Token;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;


public class AuthorizationSamples extends TestBase {
    private String connectionString= null;
    private final ClientLogger logger = new ClientLogger(AuthorizationSamples.class);

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



}
