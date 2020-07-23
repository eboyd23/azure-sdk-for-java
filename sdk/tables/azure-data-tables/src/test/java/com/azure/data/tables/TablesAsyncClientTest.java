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
import com.azure.data.tables.implementation.models.ResponseFormat;
import com.azure.data.tables.models.Entity;
import com.azure.data.tables.models.UpdateMode;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

public class TablesAsyncClientTest extends TestBase {
    private String connectionString= null;
    private final ClientLogger logger = new ClientLogger(AuthorizationSamples.class);


    private TableAsyncClient asyncClient;


    private static final String PARTITION_KEY = "PartitionKey";
    private static final String ROW_KEY = "RowKey";
    private static final String TABLE_NAME = "SampleTable";


    @Override
    protected void beforeTest() {
        asyncClient = new TableClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new AddHeadersPolicy(new HttpHeaders().put("Accept",
                OdataMetadataFormat.APPLICATION_JSON_ODATA_MINIMALMETADATA.toString())))
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .tableName(TABLE_NAME)
            .buildAsyncClient();



    }

    @Test
    void createTableAsync() {
        //Act & Assert
        StepVerifier.create(asyncClient.create())
            .assertNext(response -> {
                Assertions.assertEquals(TABLE_NAME, response.getName());

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
    void getEntity() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity = asyncClient.createEntity(tableEntity).block();
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.getEntityWithResponse(tableEntity.getPartitionKey().toString(), tableEntity.getRowKey().toString()))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

    @Test
    void updateEntity() {
        // Arrange
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        Entity tableEntity = new Entity(partitionKeyValue, rowKeyValue);
        tableEntity = asyncClient.createEntity(tableEntity).block();
        tableEntity.addProperties("cost", "5 dollars");
        int expectedStatusCode = 204;

        // Act & Assert
        StepVerifier.create(asyncClient.updateEntityWithResponse(tableEntity, true, UpdateMode.MERGE))
            .assertNext(response -> {
                Assertions.assertEquals(expectedStatusCode, response.getStatusCode());
            })
            .expectComplete()
            .verify();
    }

}
