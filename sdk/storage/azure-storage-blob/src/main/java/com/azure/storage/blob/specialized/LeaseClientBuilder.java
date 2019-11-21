// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.storage.blob.specialized;

import com.azure.core.annotation.ServiceClientBuilder;
import com.azure.core.http.HttpPipeline;
import com.azure.storage.blob.BlobAsyncClient;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.ContainerAsyncClient;
import com.azure.storage.blob.ContainerClient;

import java.net.URL;
import java.util.Objects;
import java.util.UUID;

/**
 * This class provides a fluent builder API to help aid the configuration and instantiation of Storage Lease
 * clients. Lease clients are able to interact with both container and blob clients and act as a supplement client. A
 * new instance of {@link LeaseClient} and {@link LeaseAsyncClient} are constructed every time
 * {@link #buildClient() buildClient} and {@link #buildAsyncClient() buildAsyncClient} are called
 * respectively.
 *
 * <p>When a client is instantiated and a {@link #leaseId(String) leaseId} hasn't been set a {@link UUID} will be used
 * as the lease identifier.</p>
 *
 * <p><strong>Instantiating LeaseClients</strong></p>
 *
 * {@codesnippet com.azure.storage.blob.specialized.LeaseClientBuilder.syncInstantiationWithBlobAndLeaseId}
 *
 * {@codesnippet com.azure.storage.blob.specialized.LeaseClientBuilder.syncInstantiationWithContainerAndLeaseId}
 *
 * <p><strong>Instantiating LeaseAsyncClients</strong></p>
 *
 * {@codesnippet com.azure.storage.blob.specialized.LeaseClientBuilder.asyncInstantiationWithBlobAndLeaseId}
 *
 * {@codesnippet com.azure.storage.blob.specialized.LeaseClientBuilder.asyncInstantiationWithContainerAndLeaseId}
 *
 * @see LeaseClient
 * @see LeaseAsyncClient
 */
@ServiceClientBuilder(serviceClients = { LeaseClient.class, LeaseAsyncClient.class })
public final class LeaseClientBuilder {
    private HttpPipeline pipeline;
    private URL url;
    private String leaseId;
    private boolean isBlob;

    /**
     * Creates a {@link LeaseClient} based on the configurations set in the builder.
     *
     * @return a {@link LeaseClient} based on the configurations in this builder.
     */
    public LeaseClient buildClient() {
        return new LeaseClient(buildAsyncClient());
    }

    /**
     * Creates a {@link LeaseAsyncClient} based on the configurations set in the builder.
     *
     * @return a {@link LeaseAsyncClient} based on the configurations in this builder.
     */
    public LeaseAsyncClient buildAsyncClient() {
        return new LeaseAsyncClient(pipeline, url, getLeaseId(), isBlob);
    }

    /**
     * Configures the builder based on the passed {@link BlobClient}. This will set the {@link HttpPipeline} and
     * {@link URL} that are used to interact with the service.
     *
     * @param blobClient BlobClient used to configure the builder.
     * @return the updated LeaseClientBuilder object
     * @throws NullPointerException If {@code blobClient} is {@code null}.
     */
    public LeaseClientBuilder blobClient(BlobClient blobClient) {
        Objects.requireNonNull(blobClient);
        this.pipeline = blobClient.getHttpPipeline();
        this.url = blobClient.getBlobUrl();
        this.isBlob = true;
        return this;
    }

    /**
     * Configures the builder based on the passed {@link BlobAsyncClient}. This will set the {@link HttpPipeline} and
     * {@link URL} that are used to interact with the service.
     *
     * @param blobAsyncClient BlobAsyncClient used to configure the builder.
     * @return the updated LeaseClientBuilder object
     * @throws NullPointerException If {@code blobAsyncClient} is {@code null}.
     */
    public LeaseClientBuilder blobAsyncClient(BlobAsyncClient blobAsyncClient) {
        Objects.requireNonNull(blobAsyncClient);
        this.pipeline = blobAsyncClient.getHttpPipeline();
        this.url = blobAsyncClient.getBlobUrl();
        this.isBlob = true;
        return this;
    }

    /**
     * Configures the builder based on the passed {@link ContainerClient}. This will set the {@link HttpPipeline} and
     * {@link URL} that are used to interact with the service.
     *
     * @param containerClient ContainerClient used to configure the builder.
     * @return the updated LeaseClientBuilder object
     * @throws NullPointerException If {@code containerClient} is {@code null}.
     */
    public LeaseClientBuilder containerClient(ContainerClient containerClient) {
        Objects.requireNonNull(containerClient);
        this.pipeline = containerClient.getHttpPipeline();
        this.url = containerClient.getContainerUrl();
        this.isBlob = false;
        return this;
    }

    /**
     * Configures the builder based on the passed {@link ContainerAsyncClient}. This will set the {@link HttpPipeline}
     * and {@link URL} that are used to interact with the service.
     *
     * @param containerAsyncClient ContainerAsyncClient used to configure the builder.
     * @return the updated LeaseClientBuilder object
     * @throws NullPointerException If {@code containerAsyncClient} is {@code null}.
     */
    public LeaseClientBuilder containerAsyncClient(ContainerAsyncClient containerAsyncClient) {
        Objects.requireNonNull(containerAsyncClient);
        this.pipeline = containerAsyncClient.getHttpPipeline();
        this.url = containerAsyncClient.getContainerUrl();
        this.isBlob = false;
        return this;
    }

    /**
     * Sets the identifier for the lease.
     *
     * <p>If a lease ID isn't set then a {@link UUID} will be used.</p>
     *
     * @param leaseId Identifier for the lease.
     * @return the updated LeaseClientBuilder object
     */
    public LeaseClientBuilder leaseId(String leaseId) {
        this.leaseId = leaseId;
        return this;
    }

    private String getLeaseId() {
        return (leaseId == null) ? UUID.randomUUID().toString() : leaseId;
    }
}