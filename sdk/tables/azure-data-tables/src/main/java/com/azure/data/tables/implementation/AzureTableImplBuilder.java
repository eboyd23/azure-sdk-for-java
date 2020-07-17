// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.data.tables.implementation;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.http.HttpPipeline;
import com.azure.core.http.HttpPipelineBuilder;
import com.azure.core.http.policy.CookiePolicy;
import com.azure.core.http.policy.RetryPolicy;
import com.azure.core.http.policy.UserAgentPolicy;

/** A builder for creating a new instance of the AzureTable type. */
@ServiceClientBuilder(serviceClients = {AzureTableImpl.class})
public final class AzureTableImplBuilder {
    /*
     * The URL of the service account or table that is the target of the
     * desired operation.
     */
    private String url;

    /**
     * Sets The URL of the service account or table that is the target of the desired operation.
     *
     * @param url the url value.
     * @return the AzureTableImplBuilder.
     */
    public AzureTableImplBuilder url(String url) {
        this.url = url;
        return this;
    }

    /*
     * The HTTP pipeline to send requests through
     */
    private HttpPipeline pipeline;

    /**
     * Sets The HTTP pipeline to send requests through.
     *
     * @param pipeline the pipeline value.
     * @return the AzureTableImplBuilder.
     */
    public AzureTableImplBuilder pipeline(HttpPipeline pipeline) {
        this.pipeline = pipeline;
        return this;
    }

    /**
     * The version for Tables service.
     */
    private String version;

    /**
     * Sets the version for Tables service.
     *
     * @param version the service value.
     * @return the AzureTableImplBuilder.
     */
    public AzureTableImplBuilder version(String version) {
        this.version = version;
        return this;
    }

    /**
     * Builds an instance of AzureTableImpl with the provided parameters.
     *
     * @return an instance of AzureTableImpl.
     */
    public AzureTableImpl buildClient() {
        if (pipeline == null) {
            this.pipeline =
                    new HttpPipelineBuilder()
                            .policies(new UserAgentPolicy(), new RetryPolicy(), new CookiePolicy())
                            .build();
        }
        AzureTableImpl client = new AzureTableImpl(pipeline, url, version);
        return client;
    }
}
