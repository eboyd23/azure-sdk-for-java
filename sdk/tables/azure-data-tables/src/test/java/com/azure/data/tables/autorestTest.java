package com.azure.data.tables;

import com.azure.core.http.*;
import com.azure.core.http.policy.*;

import com.azure.core.util.Context;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.AzureTableImpl;
import com.azure.data.tables.implementation.AzureTableImplBuilder;

import com.azure.data.tables.implementation.TablesImpl;
import com.azure.data.tables.implementation.models.QueryOptions;
import com.azure.data.tables.implementation.models.ResponseFormat;
import com.azure.data.tables.implementation.models.TableProperties;
import com.azure.storage.common.StorageSharedKeyCredential;
import com.azure.storage.common.implementation.connectionstring.StorageAuthenticationSettings;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;

import com.azure.storage.common.policy.StorageSharedKeyCredentialPolicy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;


import java.util.ArrayList;
import java.util.List;

public class autorestTest {


    @Test
    void createAndUpdateTableFixed() {
        final String connectionString = System.getenv("azure_tables_connection_string");
        final List<HttpPipelinePolicy> policies = new ArrayList<>();

        StorageConnectionString storageConnectionString
            = StorageConnectionString.create(connectionString, new ClientLogger("tables"));

        StorageAuthenticationSettings authSettings = storageConnectionString.getStorageAuthSettings();
        StorageSharedKeyCredential sharedKeyCredential = new StorageSharedKeyCredential(authSettings.getAccount().getName(),
            authSettings.getAccount().getAccessKey());

        //storagesharedkey object and the storage auth object
        policies.add(new AddDatePolicy());
        policies.add(new StorageSharedKeyCredentialPolicy(sharedKeyCredential));
        //HttpLoggingPolicy()

        final HttpPipeline pipeline = new HttpPipelineBuilder()
            .httpClient(null)
            .policies(policies.toArray(new HttpPipelinePolicy[0]))
            .build();

        AzureTableImplBuilder azureTableImplBuilder = new AzureTableImplBuilder();
        AzureTableImpl azureTable = azureTableImplBuilder
            .pipeline(pipeline)
            .url("https://telboytrial.table.core.windows.net/")
            .buildClient();

        try{
            TablesImpl tables = azureTable.getTables();

            StepVerifier.create(tables.deleteWithResponseAsync("ebTable","ID23",Context.NONE))
                .assertNext(response -> {
                    System.out.println(response);
                    Assertions.assertEquals(200, response.getStatusCode());
                })
                .expectComplete()
                .verify();
        } catch (Exception e){
            System.out.print(e);
        }

    }

    @Test
    void TablesAuth() {
        final String connectionString = System.getenv("azure_tables_connection_string");
        final List<HttpPipelinePolicy> policies = new ArrayList<>();

        StorageConnectionString storageConnectionString
            = StorageConnectionString.create(connectionString, new ClientLogger("tables"));
        System.out.println(storageConnectionString);

        StorageAuthenticationSettings authSettings = storageConnectionString.getStorageAuthSettings();

        TablesSharedKeyCredential sharedKeyCredential = new TablesSharedKeyCredential(authSettings.getAccount().getName(),
            authSettings.getAccount().getAccessKey());

        //storagesharedkey object and the storage auth object
        policies.add(new AddDatePolicy());
        policies.add(new TablesSharedKeyCredentialPolicy(sharedKeyCredential));
        //HttpLoggingPolicy()

        final HttpPipeline pipeline = new HttpPipelineBuilder()
            .httpClient(null)
            .policies(policies.toArray(new HttpPipelinePolicy[0]))
            .build();

        AzureTableImplBuilder azureTableImplBuilder = new AzureTableImplBuilder();
        AzureTableImpl azureTable = azureTableImplBuilder
            .pipeline(pipeline)
            .url("https://telboytrial.table.core.windows.net/")
            .buildClient();

        try{
            TablesImpl tables = azureTable.getTables();

            StepVerifier.create(tables.createWithResponseAsync(new TableProperties().setTableName("ebTable"),
                "ID23",
                ResponseFormat.RETURN_CONTENT,
                null,null))
                .assertNext(response -> {
                    System.out.println(response);
                    Assertions.assertEquals(200, response.getStatusCode());
                })
                .expectComplete()
                .verify();
        } catch (Exception e){
            System.out.print(e);
        }

//        try{
//            TablesImpl tables = azureTable.getTables();
//
//            StepVerifier.create(tables.deleteWithResponseAsync("ebTable","ID23",Context.NONE))
//                .assertNext(response -> {
//                    System.out.println(response);
//                    Assertions.assertEquals(200, response.getStatusCode());
//                })
//                .expectComplete()
//                .verify();
//        } catch (Exception e){
//            System.out.print(e);
//        }

    }









//    @Test
//    void createTableTest() {
//        StringToSign = VERB + "\n" +
//            Content-MD5 + "\n" +
//            Content-Type + "\n" +
//            Date + "\n" +
//            CanonicalizedResource;
//
//    }
//
//
//    @Test
//    void createAndUpdateTable() throws InterruptedException {
//        TablesImpl ti = new TablesImpl( new AzureTableImpl());
//        Mono<TablesCreateResponse> c = ti.createWithResponseAsync( new TableProperties(), "requestId", RETURN_CONTENT,
//            null , new Context("key", "value"));
//    }

}
