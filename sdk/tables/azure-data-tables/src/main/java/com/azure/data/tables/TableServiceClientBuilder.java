// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.credential.TokenCredential;
import com.azure.core.http.HttpClient;
import com.azure.core.http.HttpHeaders;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.AddDatePolicy;
import com.azure.core.http.policy.AddHeadersFromContextPolicy;
import com.azure.core.http.policy.AddHeadersPolicy;
import com.azure.core.http.policy.BearerTokenAuthenticationPolicy;
import com.azure.core.http.policy.HttpLogDetailLevel;
import com.azure.core.http.policy.HttpLogOptions;
import com.azure.core.http.policy.HttpLoggingPolicy;
import com.azure.core.http.policy.HttpPipelinePolicy;
import com.azure.core.http.policy.HttpPolicyProviders;
import com.azure.core.http.policy.RequestIdPolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;
import com.azure.core.util.logging.ClientLogger;
import com.azure.data.tables.implementation.models.OdataMetadataFormat;
import com.azure.storage.common.implementation.connectionstring.StorageAuthenticationSettings;
import com.azure.storage.common.implementation.connectionstring.StorageConnectionString;
import com.azure.storage.common.implementation.credentials.SasTokenCredential;
import java.util.ArrayList;
import java.util.List;
import com.azure.core.util.Configuration;
import java.util.Objects;

/**
 * builds the table service clients
 */
@ServiceClientBuilder(serviceClients = {TableServiceClient.class, TableServiceAsyncClient.class})
public class TableServiceClientBuilder {
    private final ClientLogger logger = new ClientLogger(TableServiceClientBuilder.class);
    private final List<HttpPipelinePolicy> policies;
    private String connectionString;
    private Configuration configuration;
    private TablesSharedKeyCredential tablesSharedKeyCredential;
    private String endpoint;
    private HttpClient httpClient;
    private HttpLogOptions httpLogOptions;
    private TablesServiceVersion version;
    private TokenCredential tokenCredential;
    private HttpPipeline httpPipeline;
    private SasTokenCredential sasTokenCredential;
    private HttpPipeline pipeline;

    /**
     * constructor
     */
    public TableServiceClientBuilder() {
        policies = new ArrayList<>();
        httpLogOptions = new HttpLogOptions();
    }

    /**
     * Sets the connection string to help build the client
     *
     * @param connectionString the connection string to the storage account
     * @return the TableServiceClientBuilder
     */
    public TableServiceClientBuilder connectionString(String connectionString) {
        this.connectionString = connectionString;
        return this;
    }

    /**
     * builds a sync TableServiceClient
     *
     * @return a sync TableServiceClient
     */
    public TableServiceClient buildClient() {
        //return new TableServiceClient(buildAsyncClient());
        return null;
    }



    /**
     * builds an async TableServiceAsyncClient
     *
     * @return TableServiceAsyncClient an aysnc TableServiceAsyncClient
     */
    public TableServiceAsyncClient buildAsyncClient() {
        return null;
    }
////        BuilderHelper.httpsValidation(customerProvidedKey, "customer provided key", endpoint, logger);
//
//        boolean anonymousAccess = false;
//
//        if (Objects.isNull(tablesSharedKeyCredential) && Objects.isNull(tokenCredential)
//            && Objects.isNull(sasTokenCredential)) {
//            anonymousAccess = true;
//        }
//
//        if (Objects.nonNull(customerProvidedKey) && Objects.nonNull(encryptionScope)) {
//            throw logger.logExceptionAsError(new IllegalArgumentException("Customer provided key and encryption "
//                + "scope cannot both be set"));
//        }
//
//        TablesServiceVersion serviceVersion = version != null ? version : TablesServiceVersion.getLatest();
//
//        HttpPipeline pipeline = (httpPipeline != null) ? httpPipeline : BuilderHelper.buildPipeline(
//            storageSharedKeyCredential, tokenCredential, sasTokenCredential, endpoint, retryOptions, logOptions,
//            httpClient, additionalPolicies, configuration, logger);
//
//        return new BlobServiceAsyncClient(pipeline, endpoint, serviceVersion, accountName, customerProvidedKey,
//            encryptionScope, blobContainerEncryptionScope, anonymousAccess);
//    }
//    public TableServiceAsyncClient buildAsyncClient() {
//        // Global Env configuration store
//        Configuration buildConfiguration =
//            (configuration == null) ? Configuration.getGlobalConfiguration().clone() : configuration;
//
//        // Service version
//        TablesServiceVersion serviceVersion =
//            version != null ? version : TablesServiceVersion.getLatest();
//
//        // Endpoint
//        String buildEndpoint = endpoint;
//        if (tokenCredential == null) {
//            buildEndpoint = getEndpoint();
//        }
//        // endpoint cannot be null, which is required in request authentication
//        Objects.requireNonNull(buildEndpoint, "'Endpoint' is required and can not be null.");
//
//        // if http pipeline is already defined
//        if (pipeline != null) {
//            return new TableServiceAsyncClient(buildEndpoint, pipeline, serviceVersion);
//        }
//
//        // Closest to API goes first, closest to wire goes last.
//        final List<HttpPipelinePolicy> policies = new ArrayList<>();
//
//        String clientName = properties.getOrDefault(SDK_NAME, "UnknownName");
//        String clientVersion = properties.getOrDefault(SDK_VERSION, "UnknownVersion");
//
//        policies.add(new UserAgentPolicy(httpLogOptions.getApplicationId(), clientName, clientVersion,
//            buildConfiguration));
//        policies.add(new RequestIdPolicy());
//        policies.add(new AddHeadersFromContextPolicy());
//        policies.add(new AddHeadersPolicy(headers));
//
//        HttpPolicyProviders.addBeforeRetryPolicies(policies);
//
//        policies.add(retryPolicy == null ? DEFAULT_RETRY_POLICY : retryPolicy);
//
//        policies.add(new AddDatePolicy());
//
//        if (tokenCredential != null) {
//            // User token based policy
//            policies.add(
//                new BearerTokenAuthenticationPolicy(tokenCredential, String.format("%s/.default", buildEndpoint)));
//        } else if (credential != null) {
//            // Use credential based policy
//            policies.add(new ConfigurationCredentialsPolicy(credential));
//        } else {
//            // Throw exception that credential and tokenCredential cannot be null
//            throw logger.logExceptionAsError(
//                new IllegalArgumentException("Missing credential information while building a client."));
//        }
//
//        policies.addAll(this.policies);
//        HttpPolicyProviders.addAfterRetryPolicies(policies);
//        policies.add(new HttpLoggingPolicy(httpLogOptions));
//
//        // customized pipeline
//        HttpPipeline pipeline = new HttpPipelineBuilder()
//            .policies(policies.toArray(new HttpPipelinePolicy[0]))
//            .httpClient(httpClient)
//            .build();
//
//        return new ConfigurationAsyncClient(buildEndpoint, pipeline, serviceVersion);
//
//
//    }
//
//    public BlobServiceClientBuilder customerProvidedKey(CustomerProvidedKey customerProvidedKey) {
//        if (customerProvidedKey == null) {
//            this.customerProvidedKey = null;
//        } else {
//            this.customerProvidedKey = new CpkInfo()
//                .setEncryptionKey(customerProvidedKey.getKey())
//                .setEncryptionKeySha256(customerProvidedKey.getKeySha256())
//                .setEncryptionAlgorithm(customerProvidedKey.getEncryptionAlgorithm());
//        }
//
//        return this;
//    }

    /**
     * gets the connection string
     * @return the connection string
     */
    public String getConnectionString() {
        return this.connectionString;
    }

    /**
     * Sets the configuration object used to retrieve environment configuration values during building of the client.
     *
     * @param configuration Configuration store used to retrieve environment configurations.
     * @return the updated TableServiceClientBuilder object
     */
    public TableServiceClientBuilder configuration(Configuration configuration) {
        this.configuration = configuration;
        return this;
    }

    /**
     * update credential
     * @param credential the tables shared key credential
     * @return the updated TableServiceClient builder
     * @throws NullPointerException If {@code credential} is {@code null}.
     */
    public TableServiceClientBuilder credential(TokenCredential credential) {
        this.tablesSharedKeyCredential = Objects.requireNonNull(tablesSharedKeyCredential, "credential cannot"
            + "be null");
        return this;
    }

    /**
     * Sets the table service endpoint
     *
     * @param endpoint URL of the service
     * @return the updated TableServiceClientBuilder object
     */
    public TableServiceClientBuilder endpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    private String getEndpoint() {
        if (endpoint != null) {
            return endpoint;
        } else if (tablesSharedKeyCredential != null) {
            //return tablesSharedKeyCredential.getCanonicalizedResource();
        } else {
            return null;
        }
        return null;
    }

    /**
     * Sets the {@link HttpClient} to use for sending a receiving requests to and from the service.
     *
     * @param httpClient HttpClient to use for requests.
     * @return the updated TableServiceClientBuilder object
     */
    public TableServiceClientBuilder httpClient(HttpClient httpClient) {
        if (this.httpClient != null && httpClient == null) {
            logger.error("'httpClient' is being set to 'null' when it was previously configured.");
        }
        this.httpClient = httpClient;
        return this;
    }

    /**
     * Sets the {@link HttpLogOptions} for service requests.
     *
     * @param logOptions The logging configuration to use when sending and receiving HTTP requests/responses.
     * @return the updated TableServiceClientBuilder object
     * @throws NullPointerException If {@code logOptions} is {@code null}.
     */
    public TableServiceClientBuilder httpLogOptions(HttpLogOptions logOptions) {
        //this.logOptions = Objects.requireNonNull(logOptions, "'logOptions' cannot be null.");
        return this;
    }

    /**
     * Adds a pipeline policy to apply on each request sent. The policy will be added after the retry policy. If
     * the method is called multiple times, all policies will be added and their order preserved.
     *
     * @param pipelinePolicy a pipeline policy
     * @return the updated TableServiceClientBuilder object
     * @throws NullPointerException If {@code pipelinePolicy} is {@code null}.
     */
    public TableServiceClientBuilder addPolicy(HttpPipelinePolicy pipelinePolicy) {
        //this.additionalPolicies.add(Objects.requireNonNull(pipelinePolicy, "'pipelinePolicy' cannot be null"));
        return this;
    }

    /**
     * Sets the TablesServiceVersion that is used when making API requests.
     * <p>
     * If a service version is not provided, the service version that will be used will be the latest known service
     * version based on the version of the client library being used. If no service version is specified, updating to a
     * newer version of the client library will have the result of potentially moving to a newer service version.
     * <p>
     * Targeting a specific service version may also mean that the service will return an error for newer APIs.
     *
     * @param version {@link TablesServiceVersion} of the service to be used when making requests.
     * @return the updated TableServiceClientBuilder object
     */
    public TableServiceClientBuilder serviceVersion(TablesServiceVersion version) {
        this.version = version;
        return this;
    }

    /**
     * Sets the HTTP pipeline to use for the service client.
     *
     * @param pipeline The HTTP pipeline to use for sending service requests and receiving responses.
     * @return The updated TableServiceClientBuilder object.
     */
    public TableServiceClientBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

}
