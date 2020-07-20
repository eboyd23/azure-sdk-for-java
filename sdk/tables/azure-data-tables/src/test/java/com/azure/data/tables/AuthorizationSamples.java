package com.azure.data.tables;

import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.test.TestBase;
import com.azure.core.util.logging.ClientLogger;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.util.HashMap;
import java.util.Map;


public class AuthorizationSamples extends TestBase {
    private String connectionString = null;
    private String endpoint = null;
    private TablesSharedKeyCredential tablesSharedKeyCredential = null;
    private final ClientLogger logger = new ClientLogger(AuthorizationSamples.class);

    private TableServiceClient client;
    private TableServiceClient clientEndpoint;
    private TableServiceAsyncClient asyncClient;
    private TableAsyncClient asyncClientTable;

    private static final String PARTITION_KEY = "PartitionKey";
    private static final String ROW_KEY = "RowKey";

    @Test
    void withConnectionString() {
        // BEGIN: com.azure.data.tables.TablesServiceClient.buildClient
        TableServiceClient client = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .buildClient();
        // END: com.azure.data.tables.TablesServiceClient.buildClient
    }

    @Test
    public void azureTablesCredentialAndEndpoint() {
        // BEGIN: com.azure.storage.blob.specialized.BlobClientBase.Builder.endpoint#String
        StorageConnectionString storageConnectionString
            = StorageConnectionString.create(connectionString, logger);
        TableServiceClient client = new TableServiceClientBuilder()
            .endpoint(storageConnectionString.getTableEndpoint().getPrimaryUri())
            .credential(tablesSharedKeyCredential)
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildClient();
        // END: com.azure.storage.blob.specialized.BlobClientBase.Builder.endpoint#String
        client.createTable("tableName");
    }


    @Override
    protected void beforeTest() {
        client = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildClient();

        asyncClient = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildAsyncClient();

        StorageConnectionString storageConnectionString
            = StorageConnectionString.create(connectionString, logger);

        clientEndpoint = new TableServiceClientBuilder()
            .endpoint(storageConnectionString.getTableEndpoint().getPrimaryUri())
            .credential(tablesSharedKeyCredential)
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildClient();


    }

    @Test
    void createTableSync() {
        client.createTable("hello");
    }

    @Test
    void createTableSyncEndpoint() {
        clientEndpoint.createTable("hello");
    }

    @Test
    void deleteTableSync() {
        client.deleteTable("hello2");
    }

    @Test
    void deleteTableAsync() {
        asyncClient.deleteTable("hello");
    }

    @Test
    void createTableAsync() {
        asyncClient.queryTables(null).subscribe(r -> {
            r.getName();
        });
    }

    @Test
    void queryTableAsync() {
        StepVerifier.create(asyncClient.queryTables(null))
            .assertNext(response -> {
                System.out.print("Response");
                System.out.print(response);
            })
            .expectComplete()
            .verify();
    }

    @Test
    void createTableAsyncWithResponse() {
        asyncClient.createTable("hello");
    }

    @Test
    void insertEntity() {
        asyncClientTable = asyncClient.getTableAsyncClient("hello");
        Map<String, Object> properties = new HashMap<>();
        String partitionKeyValue = testResourceNamer.randomName("partitionKey", 20);
        String rowKeyValue = testResourceNamer.randomName("rowKey", 20);
        properties.put(PARTITION_KEY, partitionKeyValue);
        properties.put(ROW_KEY, rowKeyValue);

        TableEntity tableEntity = asyncClientTable.createEntity(properties).block();
    }

    @Test
    void queryEntity() {
        asyncClientTable = asyncClient.getTableAsyncClient("hello");
        QueryParams queryParams = new QueryParams().setSelect("PartitionKey");
        asyncClientTable.queryEntities(queryParams).subscribe(r -> {
            r.getPartitionKey();
        });
    }
}
