// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.util.Configuration;
import com.azure.core.util.CoreUtils;
import java.util.List;
import java.util.Objects;

/**
 * builds table client
 */
@ServiceClientBuilder(serviceClients = {TableClient.class, TableAsyncClient.class})
public class TableClientBuilder {
    private String connectionString;
    private String tableName;
    private final List<HttpPipelinePolicy> policies = null;
    private Configuration configuration;
    private TokenCredential tokenCredential;
    private HttpClient httpClient;
    private HttpLogOptions httpLogOptions;
    private HttpPipeline pipeline;
    private TablesServiceVersion serviceVersion;

    /**
     * Sets the connection string to help build the client
     *
     * @param connectionString the connection string to the storage account
     * @return the TableClientBuilder
     */
    public TableClientBuilder connectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    /**
     * Sets the table name to help build the client
     *
     * @param tableName name of the table for which the client is created for
     * @return the TableClientBuilder
     */
    public TableClientBuilder tableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    /**
     * builds a sync tableClient
     *
     * @return a sync tableClient
     */
    public TableClient buildClient() {
        return new TableClient(tableName, null);
    }

    /**
     * builds an async tableClient
     *
     * @return an aysnc tableClient
     */
    public TableAsyncClient buildAsyncClient() {
        return new TableAsyncClient(pipeline, "", serviceVersion.getVersion() ,tableName);
    }

    /**
     * table client builder constructor
     */
    public TableClientBuilder() {
    }

    /**
     * gets the connection string
     * @return the connection string
     */
    public String getConnectionString(){
        return this.connectionString;
    }


    /**
     * Adds a policy to the set of existing policies that are executed after required policies.
     *
     * @param policy The retry policy for service requests.
     * @return The updated TableClientBuilder object.
     * @throws NullPointerException If {@code policy} is {@code null}.
     */
    public TableClientBuilder addPolicy(HttpPipelinePolicy policy) {
        Objects.requireNonNull(policy);
        policies.add(policy);
        return this;
    }

    /**
     * Sets the configuration store that is used during construction of the service client.
     *
     * The default configuration store is a clone of the {@link Configuration#getGlobalConfiguration() global
     * configuration store}, use {@link Configuration#NONE} to bypass using configuration settings during construction.
     *
     * @param configuration The configuration store used to
     * @return The updated TableClientBuilder object.
     */
    public TableClientBuilder configuration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * Sets the {@link TokenCredential} used to authenticate HTTP requests.
     *
     * @param tokenCredential TokenCredential used to authenticate HTTP requests.
     * @return The updated TableClientBuilder object.
     * @throws NullPointerException If {@code credential} is {@code null}.
     */
    public TableClientBuilder credential(TokenCredential tokenCredential) {
        // token credential can not be null value
        Objects.requireNonNull(tokenCredential);
        this.tokenCredential = tokenCredential;
        return this;
    }

    /**
     * Sets the endpoint for the Azure Storage Table instance that the client will interact with.
     *
     * @param endpoint The URL of the Azure Storage Table instance to send service requests to and receive responses
     * from.
     * @return the updated TableClientBuilder object
     * @throws IllegalArgumentException If {@code endpoint} isn't a proper URL
     */
    public TableClientBuilder endpoint(String endpoint) {
        return this;
    }

    /**
     * Sets the HTTP client to use for sending and receiving requests to and from the service.
     *
     * @param client The HTTP client to use for requests.
     * @return The updated TableClientBuilder object.
     */
    public TableClientBuilder httpClient(HttpClient client) {
        this.httpClient = client;
        return this;
    }

    /**
     * Sets the logging configuration for HTTP requests and responses.
     *
     * <p> If logLevel is not provided, default value of {@link HttpLogDetailLevel#NONE} is set.</p>
     *
     * @param logOptions The logging configuration to use when sending and receiving HTTP requests/responses.
     * @return The updated TableClientBuilder object.
     */
    public TableClientBuilder httpLogOptions(HttpLogOptions logOptions) {
        httpLogOptions = logOptions;
        return this;
    }

    /**
     * Sets the HTTP pipeline to use for the service client.
     *
     * @param pipeline The HTTP pipeline to use for sending service requests and receiving responses.
     * @return The updated TableClientBuilder object.
     */
    public TableClientBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * Sets the {@link TablesServiceVersion} that is used when making API requests.

     * @param version {@link TablesServiceVersion} of the service to be used when making requests.
     * @return The updated TableClientBuilder object.
     */
    public TableClientBuilder serviceVersion(TablesServiceVersion version) {
        this.serviceVersion = version;
        return this;
    }

}
