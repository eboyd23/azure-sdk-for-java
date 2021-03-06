// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.

package com.azure.management.sql.models;

import com.azure.core.annotation.BodyParam;
import com.azure.core.annotation.ExpectedResponses;
import com.azure.core.annotation.Get;
import com.azure.core.annotation.Headers;
import com.azure.core.annotation.Host;
import com.azure.core.annotation.HostParam;
import com.azure.core.annotation.PathParam;
import com.azure.core.annotation.Put;
import com.azure.core.annotation.QueryParam;
import com.azure.core.annotation.ReturnType;
import com.azure.core.annotation.ServiceInterface;
import com.azure.core.annotation.ServiceMethod;
import com.azure.core.annotation.UnexpectedResponseExceptionType;
import com.azure.core.http.rest.PagedFlux;
import com.azure.core.http.rest.PagedIterable;
import com.azure.core.http.rest.PagedResponse;
import com.azure.core.http.rest.PagedResponseBase;
import com.azure.core.http.rest.RestProxy;
import com.azure.core.http.rest.SimpleResponse;
import com.azure.core.management.exception.ManagementException;
import com.azure.core.util.Context;
import com.azure.core.util.FluxUtil;
import com.azure.core.util.logging.ClientLogger;
import reactor.core.publisher.Mono;

/** An instance of this class provides access to all the operations defined in ManagedDatabaseSecurityAlertPolicies. */
public final class ManagedDatabaseSecurityAlertPoliciesInner {
    private final ClientLogger logger = new ClientLogger(ManagedDatabaseSecurityAlertPoliciesInner.class);

    /** The proxy service used to perform REST calls. */
    private final ManagedDatabaseSecurityAlertPoliciesService service;

    /** The service client containing this operation class. */
    private final SqlManagementClientImpl client;

    /**
     * Initializes an instance of ManagedDatabaseSecurityAlertPoliciesInner.
     *
     * @param client the instance of the service client containing this operation class.
     */
    ManagedDatabaseSecurityAlertPoliciesInner(SqlManagementClientImpl client) {
        this.service =
            RestProxy
                .create(
                    ManagedDatabaseSecurityAlertPoliciesService.class,
                    client.getHttpPipeline(),
                    client.getSerializerAdapter());
        this.client = client;
    }

    /**
     * The interface defining all the services for SqlManagementClientManagedDatabaseSecurityAlertPolicies to be used by
     * the proxy service to perform REST calls.
     */
    @Host("{$host}")
    @ServiceInterface(name = "SqlManagementClientM")
    private interface ManagedDatabaseSecurityAlertPoliciesService {
        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @Get(
            "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql"
                + "/managedInstances/{managedInstanceName}/databases/{databaseName}/securityAlertPolicies"
                + "/{securityAlertPolicyName}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner>> get(
            @HostParam("$host") String host,
            @PathParam("resourceGroupName") String resourceGroupName,
            @PathParam("managedInstanceName") String managedInstanceName,
            @PathParam("databaseName") String databaseName,
            @PathParam("securityAlertPolicyName") String securityAlertPolicyName,
            @PathParam("subscriptionId") String subscriptionId,
            @QueryParam("api-version") String apiVersion,
            Context context);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @Put(
            "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql"
                + "/managedInstances/{managedInstanceName}/databases/{databaseName}/securityAlertPolicies"
                + "/{securityAlertPolicyName}")
        @ExpectedResponses({200, 201})
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner>> createOrUpdate(
            @HostParam("$host") String host,
            @PathParam("resourceGroupName") String resourceGroupName,
            @PathParam("managedInstanceName") String managedInstanceName,
            @PathParam("databaseName") String databaseName,
            @PathParam("securityAlertPolicyName") String securityAlertPolicyName,
            @PathParam("subscriptionId") String subscriptionId,
            @QueryParam("api-version") String apiVersion,
            @BodyParam("application/json") ManagedDatabaseSecurityAlertPolicyInner parameters,
            Context context);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @Get(
            "/subscriptions/{subscriptionId}/resourceGroups/{resourceGroupName}/providers/Microsoft.Sql"
                + "/managedInstances/{managedInstanceName}/databases/{databaseName}/securityAlertPolicies")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyListResultInner>> listByDatabase(
            @HostParam("$host") String host,
            @PathParam("resourceGroupName") String resourceGroupName,
            @PathParam("managedInstanceName") String managedInstanceName,
            @PathParam("databaseName") String databaseName,
            @PathParam("subscriptionId") String subscriptionId,
            @QueryParam("api-version") String apiVersion,
            Context context);

        @Headers({"Accept: application/json", "Content-Type: application/json"})
        @Get("{nextLink}")
        @ExpectedResponses({200})
        @UnexpectedResponseExceptionType(ManagementException.class)
        Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyListResultInner>> listByDatabaseNext(
            @PathParam(value = "nextLink", encoded = true) String nextLink, Context context);
    }

    /**
     * Gets a managed database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database's security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner>> getWithResponseAsync(
        String resourceGroupName, String managedInstanceName, String databaseName) {
        if (this.client.getHost() == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (resourceGroupName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
        }
        if (managedInstanceName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null."));
        }
        if (databaseName == null) {
            return Mono.error(new IllegalArgumentException("Parameter databaseName is required and cannot be null."));
        }
        if (this.client.getSubscriptionId() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getSubscriptionId() is required and cannot be null."));
        }
        final String securityAlertPolicyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return FluxUtil
            .withContext(
                context ->
                    service
                        .get(
                            this.client.getHost(),
                            resourceGroupName,
                            managedInstanceName,
                            databaseName,
                            securityAlertPolicyName,
                            this.client.getSubscriptionId(),
                            apiVersion,
                            context))
            .subscriberContext(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext())));
    }

    /**
     * Gets a managed database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database's security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner>> getWithResponseAsync(
        String resourceGroupName, String managedInstanceName, String databaseName, Context context) {
        if (this.client.getHost() == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (resourceGroupName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
        }
        if (managedInstanceName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null."));
        }
        if (databaseName == null) {
            return Mono.error(new IllegalArgumentException("Parameter databaseName is required and cannot be null."));
        }
        if (this.client.getSubscriptionId() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getSubscriptionId() is required and cannot be null."));
        }
        final String securityAlertPolicyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return service
            .get(
                this.client.getHost(),
                resourceGroupName,
                managedInstanceName,
                databaseName,
                securityAlertPolicyName,
                this.client.getSubscriptionId(),
                apiVersion,
                context);
    }

    /**
     * Gets a managed database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database's security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ManagedDatabaseSecurityAlertPolicyInner> getAsync(
        String resourceGroupName, String managedInstanceName, String databaseName) {
        return getWithResponseAsync(resourceGroupName, managedInstanceName, databaseName)
            .flatMap(
                (SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    /**
     * Gets a managed database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database's security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ManagedDatabaseSecurityAlertPolicyInner get(
        String resourceGroupName, String managedInstanceName, String databaseName) {
        return getAsync(resourceGroupName, managedInstanceName, databaseName).block();
    }

    /**
     * Creates or updates a database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @param parameters A managed database security alert policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner>> createOrUpdateWithResponseAsync(
        String resourceGroupName,
        String managedInstanceName,
        String databaseName,
        ManagedDatabaseSecurityAlertPolicyInner parameters) {
        if (this.client.getHost() == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (resourceGroupName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
        }
        if (managedInstanceName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null."));
        }
        if (databaseName == null) {
            return Mono.error(new IllegalArgumentException("Parameter databaseName is required and cannot be null."));
        }
        if (this.client.getSubscriptionId() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getSubscriptionId() is required and cannot be null."));
        }
        if (parameters == null) {
            return Mono.error(new IllegalArgumentException("Parameter parameters is required and cannot be null."));
        } else {
            parameters.validate();
        }
        final String securityAlertPolicyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return FluxUtil
            .withContext(
                context ->
                    service
                        .createOrUpdate(
                            this.client.getHost(),
                            resourceGroupName,
                            managedInstanceName,
                            databaseName,
                            securityAlertPolicyName,
                            this.client.getSubscriptionId(),
                            apiVersion,
                            parameters,
                            context))
            .subscriberContext(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext())));
    }

    /**
     * Creates or updates a database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @param parameters A managed database security alert policy.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner>> createOrUpdateWithResponseAsync(
        String resourceGroupName,
        String managedInstanceName,
        String databaseName,
        ManagedDatabaseSecurityAlertPolicyInner parameters,
        Context context) {
        if (this.client.getHost() == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (resourceGroupName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
        }
        if (managedInstanceName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null."));
        }
        if (databaseName == null) {
            return Mono.error(new IllegalArgumentException("Parameter databaseName is required and cannot be null."));
        }
        if (this.client.getSubscriptionId() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getSubscriptionId() is required and cannot be null."));
        }
        if (parameters == null) {
            return Mono.error(new IllegalArgumentException("Parameter parameters is required and cannot be null."));
        } else {
            parameters.validate();
        }
        final String securityAlertPolicyName = "default";
        final String apiVersion = "2017-03-01-preview";
        return service
            .createOrUpdate(
                this.client.getHost(),
                resourceGroupName,
                managedInstanceName,
                databaseName,
                securityAlertPolicyName,
                this.client.getSubscriptionId(),
                apiVersion,
                parameters,
                context);
    }

    /**
     * Creates or updates a database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @param parameters A managed database security alert policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<ManagedDatabaseSecurityAlertPolicyInner> createOrUpdateAsync(
        String resourceGroupName,
        String managedInstanceName,
        String databaseName,
        ManagedDatabaseSecurityAlertPolicyInner parameters) {
        return createOrUpdateWithResponseAsync(resourceGroupName, managedInstanceName, databaseName, parameters)
            .flatMap(
                (SimpleResponse<ManagedDatabaseSecurityAlertPolicyInner> res) -> {
                    if (res.getValue() != null) {
                        return Mono.just(res.getValue());
                    } else {
                        return Mono.empty();
                    }
                });
    }

    /**
     * Creates or updates a database's security alert policy.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policy is defined.
     * @param parameters A managed database security alert policy.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a managed database security alert policy.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public ManagedDatabaseSecurityAlertPolicyInner createOrUpdate(
        String resourceGroupName,
        String managedInstanceName,
        String databaseName,
        ManagedDatabaseSecurityAlertPolicyInner parameters) {
        return createOrUpdateAsync(resourceGroupName, managedInstanceName, databaseName, parameters).block();
    }

    /**
     * Gets a list of managed database's security alert policies.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policies are defined.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of managed database's security alert policies.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ManagedDatabaseSecurityAlertPolicyInner>> listByDatabaseSinglePageAsync(
        String resourceGroupName, String managedInstanceName, String databaseName) {
        if (this.client.getHost() == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (resourceGroupName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
        }
        if (managedInstanceName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null."));
        }
        if (databaseName == null) {
            return Mono.error(new IllegalArgumentException("Parameter databaseName is required and cannot be null."));
        }
        if (this.client.getSubscriptionId() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getSubscriptionId() is required and cannot be null."));
        }
        final String apiVersion = "2017-03-01-preview";
        return FluxUtil
            .withContext(
                context ->
                    service
                        .listByDatabase(
                            this.client.getHost(),
                            resourceGroupName,
                            managedInstanceName,
                            databaseName,
                            this.client.getSubscriptionId(),
                            apiVersion,
                            context))
            .<PagedResponse<ManagedDatabaseSecurityAlertPolicyInner>>map(
                res ->
                    new PagedResponseBase<>(
                        res.getRequest(),
                        res.getStatusCode(),
                        res.getHeaders(),
                        res.getValue().value(),
                        res.getValue().nextLink(),
                        null))
            .subscriberContext(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext())));
    }

    /**
     * Gets a list of managed database's security alert policies.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policies are defined.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of managed database's security alert policies.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ManagedDatabaseSecurityAlertPolicyInner>> listByDatabaseSinglePageAsync(
        String resourceGroupName, String managedInstanceName, String databaseName, Context context) {
        if (this.client.getHost() == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter this.client.getHost() is required and cannot be null."));
        }
        if (resourceGroupName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter resourceGroupName is required and cannot be null."));
        }
        if (managedInstanceName == null) {
            return Mono
                .error(new IllegalArgumentException("Parameter managedInstanceName is required and cannot be null."));
        }
        if (databaseName == null) {
            return Mono.error(new IllegalArgumentException("Parameter databaseName is required and cannot be null."));
        }
        if (this.client.getSubscriptionId() == null) {
            return Mono
                .error(
                    new IllegalArgumentException(
                        "Parameter this.client.getSubscriptionId() is required and cannot be null."));
        }
        final String apiVersion = "2017-03-01-preview";
        return service
            .listByDatabase(
                this.client.getHost(),
                resourceGroupName,
                managedInstanceName,
                databaseName,
                this.client.getSubscriptionId(),
                apiVersion,
                context)
            .map(
                res ->
                    new PagedResponseBase<>(
                        res.getRequest(),
                        res.getStatusCode(),
                        res.getHeaders(),
                        res.getValue().value(),
                        res.getValue().nextLink(),
                        null));
    }

    /**
     * Gets a list of managed database's security alert policies.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policies are defined.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of managed database's security alert policies.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ManagedDatabaseSecurityAlertPolicyInner> listByDatabaseAsync(
        String resourceGroupName, String managedInstanceName, String databaseName) {
        return new PagedFlux<>(
            () -> listByDatabaseSinglePageAsync(resourceGroupName, managedInstanceName, databaseName),
            nextLink -> listByDatabaseNextSinglePageAsync(nextLink));
    }

    /**
     * Gets a list of managed database's security alert policies.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policies are defined.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of managed database's security alert policies.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedFlux<ManagedDatabaseSecurityAlertPolicyInner> listByDatabaseAsync(
        String resourceGroupName, String managedInstanceName, String databaseName, Context context) {
        return new PagedFlux<>(
            () -> listByDatabaseSinglePageAsync(resourceGroupName, managedInstanceName, databaseName, context),
            nextLink -> listByDatabaseNextSinglePageAsync(nextLink));
    }

    /**
     * Gets a list of managed database's security alert policies.
     *
     * @param resourceGroupName The name of the resource group that contains the resource. You can obtain this value
     *     from the Azure Resource Manager API or the portal.
     * @param managedInstanceName The name of the managed instance.
     * @param databaseName The name of the managed database for which the security alert policies are defined.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of managed database's security alert policies.
     */
    @ServiceMethod(returns = ReturnType.COLLECTION)
    public PagedIterable<ManagedDatabaseSecurityAlertPolicyInner> listByDatabase(
        String resourceGroupName, String managedInstanceName, String databaseName) {
        return new PagedIterable<>(listByDatabaseAsync(resourceGroupName, managedInstanceName, databaseName));
    }

    /**
     * Get the next page of items.
     *
     * @param nextLink The nextLink parameter.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of the managed database's security alert policies.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ManagedDatabaseSecurityAlertPolicyInner>> listByDatabaseNextSinglePageAsync(
        String nextLink) {
        if (nextLink == null) {
            return Mono.error(new IllegalArgumentException("Parameter nextLink is required and cannot be null."));
        }
        return FluxUtil
            .withContext(context -> service.listByDatabaseNext(nextLink, context))
            .<PagedResponse<ManagedDatabaseSecurityAlertPolicyInner>>map(
                res ->
                    new PagedResponseBase<>(
                        res.getRequest(),
                        res.getStatusCode(),
                        res.getHeaders(),
                        res.getValue().value(),
                        res.getValue().nextLink(),
                        null))
            .subscriberContext(context -> context.putAll(FluxUtil.toReactorContext(this.client.getContext())));
    }

    /**
     * Get the next page of items.
     *
     * @param nextLink The nextLink parameter.
     * @param context The context to associate with this operation.
     * @throws IllegalArgumentException thrown if parameters fail the validation.
     * @throws ManagementException thrown if the request is rejected by server.
     * @throws RuntimeException all other wrapped checked exceptions if the request fails to be sent.
     * @return a list of the managed database's security alert policies.
     */
    @ServiceMethod(returns = ReturnType.SINGLE)
    public Mono<PagedResponse<ManagedDatabaseSecurityAlertPolicyInner>> listByDatabaseNextSinglePageAsync(
        String nextLink, Context context) {
        if (nextLink == null) {
            return Mono.error(new IllegalArgumentException("Parameter nextLink is required and cannot be null."));
        }
        return service
            .listByDatabaseNext(nextLink, context)
            .map(
                res ->
                    new PagedResponseBase<>(
                        res.getRequest(),
                        res.getStatusCode(),
                        res.getHeaders(),
                        res.getValue().value(),
                        res.getValue().nextLink(),
                        null));
    }
}
