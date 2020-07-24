package com.azure.data.tables;

import com.azure.core.http.HttpHeaders;
import com.azure.core.http.policy.AddHeadersPolicy;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.test.TestBase;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.data.tables.models.Table;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TableServiceClientTest extends TestBase {
    private String connectionString= System.getenv("AZURE_TABLES_CONNECTION_STRING");
    private final ClientLogger logger = new ClientLogger(TableServiceAsyncClientTest.class);
    private TableServiceClient client;


    @Override
    protected void beforeTest() {
        client = new TableServiceClientBuilder()
            .connectionString(connectionString)
            .addPolicy(new AddHeadersPolicy(new HttpHeaders().put("Accept",
                OdataMetadataFormat.APPLICATION_JSON_ODATA_MINIMALMETADATA.toString())))
            .addPolicy(new HttpLoggingPolicy(new HttpLogOptions().setLogLevel(HttpLogDetailLevel.BODY_AND_HEADERS)))
            .buildClient();
    }

    @Test
    void createTable() {
        // Arrange
        String tableName = testResourceNamer.randomName("test", 20);

        //Act
        Table table = client.createTable(tableName);

        //Assert
        assertEquals(table.getName(), tableName);
    }

    @Test
    void deleteTableAsync() {
        // Arrange
        String tableName = testResourceNamer.randomName("test", 20);

        //Act & Assert
    }

    @Test
    void deleteTableWithResponseAsync() {
        // Arrange
        String tableName = testResourceNamer.randomName("test", 20);
        int expectedStatusCode = 204;

    }
}
