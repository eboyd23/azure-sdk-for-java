/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.storage.v2019_04_01;

import com.microsoft.azure.management.storage.v2019_04_01.implementation.SkuInner;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.microsoft.rest.serializer.JsonFlatten;

/**
 * The parameters used when creating a storage account.
 */
@JsonFlatten
public class StorageAccountCreateParameters {
    /**
     * Required. Gets or sets the SKU name.
     */
    @JsonProperty(value = "sku", required = true)
    private SkuInner sku;

    /**
     * Required. Indicates the type of storage account. Possible values
     * include: 'Storage', 'StorageV2', 'BlobStorage', 'FileStorage',
     * 'BlockBlobStorage'.
     */
    @JsonProperty(value = "kind", required = true)
    private Kind kind;

    /**
     * Required. Gets or sets the location of the resource. This will be one of
     * the supported and registered Azure Geo Regions (e.g. West US, East US,
     * Southeast Asia, etc.). The geo region of a resource cannot be changed
     * once it is created, but if an identical geo region is specified on
     * update, the request will succeed.
     */
    @JsonProperty(value = "location", required = true)
    private String location;

    /**
     * Gets or sets a list of key value pairs that describe the resource. These
     * tags can be used for viewing and grouping this resource (across resource
     * groups). A maximum of 15 tags can be provided for a resource. Each tag
     * must have a key with a length no greater than 128 characters and a value
     * with a length no greater than 256 characters.
     */
    @JsonProperty(value = "tags")
    private Map<String, String> tags;

    /**
     * The identity of the resource.
     */
    @JsonProperty(value = "identity")
    private Identity identity;

    /**
     * User domain assigned to the storage account. Name is the CNAME source.
     * Only one custom domain is supported per storage account at this time. To
     * clear the existing custom domain, use an empty string for the custom
     * domain name property.
     */
    @JsonProperty(value = "properties.customDomain")
    private CustomDomain customDomain;

    /**
     * Not applicable. Azure Storage encryption is enabled for all storage
     * accounts and cannot be disabled.
     */
    @JsonProperty(value = "properties.encryption")
    private Encryption encryption;

    /**
     * Network rule set.
     */
    @JsonProperty(value = "properties.networkAcls")
    private NetworkRuleSet networkRuleSet;

    /**
     * Required for storage accounts where kind = BlobStorage. The access tier
     * used for billing. Possible values include: 'Hot', 'Cool'.
     */
    @JsonProperty(value = "properties.accessTier")
    private AccessTier accessTier;

    /**
     * Provides the identity based authentication settings for Azure Files.
     */
    @JsonProperty(value = "properties.azureFilesIdentityBasedAuthentication")
    private AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication;

    /**
     * Allows https traffic only to storage service if sets to true. The
     * default value is true since API version 2019-04-01.
     */
    @JsonProperty(value = "properties.supportsHttpsTrafficOnly")
    private Boolean enableHttpsTrafficOnly;

    /**
     * Account HierarchicalNamespace enabled if sets to true.
     */
    @JsonProperty(value = "properties.isHnsEnabled")
    private Boolean isHnsEnabled;

    /**
     * Allow large file shares if sets to Enabled. It cannot be disabled once
     * it is enabled. Possible values include: 'Disabled', 'Enabled'.
     */
    @JsonProperty(value = "properties.largeFileSharesState")
    private LargeFileSharesState largeFileSharesState;

    /**
     * Get required. Gets or sets the SKU name.
     *
     * @return the sku value
     */
    public SkuInner sku() {
        return this.sku;
    }

    /**
     * Set required. Gets or sets the SKU name.
     *
     * @param sku the sku value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withSku(SkuInner sku) {
        this.sku = sku;
        return this;
    }

    /**
     * Get required. Indicates the type of storage account. Possible values include: 'Storage', 'StorageV2', 'BlobStorage', 'FileStorage', 'BlockBlobStorage'.
     *
     * @return the kind value
     */
    public Kind kind() {
        return this.kind;
    }

    /**
     * Set required. Indicates the type of storage account. Possible values include: 'Storage', 'StorageV2', 'BlobStorage', 'FileStorage', 'BlockBlobStorage'.
     *
     * @param kind the kind value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withKind(Kind kind) {
        this.kind = kind;
        return this;
    }

    /**
     * Get required. Gets or sets the location of the resource. This will be one of the supported and registered Azure Geo Regions (e.g. West US, East US, Southeast Asia, etc.). The geo region of a resource cannot be changed once it is created, but if an identical geo region is specified on update, the request will succeed.
     *
     * @return the location value
     */
    public String location() {
        return this.location;
    }

    /**
     * Set required. Gets or sets the location of the resource. This will be one of the supported and registered Azure Geo Regions (e.g. West US, East US, Southeast Asia, etc.). The geo region of a resource cannot be changed once it is created, but if an identical geo region is specified on update, the request will succeed.
     *
     * @param location the location value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withLocation(String location) {
        this.location = location;
        return this;
    }

    /**
     * Get gets or sets a list of key value pairs that describe the resource. These tags can be used for viewing and grouping this resource (across resource groups). A maximum of 15 tags can be provided for a resource. Each tag must have a key with a length no greater than 128 characters and a value with a length no greater than 256 characters.
     *
     * @return the tags value
     */
    public Map<String, String> tags() {
        return this.tags;
    }

    /**
     * Set gets or sets a list of key value pairs that describe the resource. These tags can be used for viewing and grouping this resource (across resource groups). A maximum of 15 tags can be provided for a resource. Each tag must have a key with a length no greater than 128 characters and a value with a length no greater than 256 characters.
     *
     * @param tags the tags value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withTags(Map<String, String> tags) {
        this.tags = tags;
        return this;
    }

    /**
     * Get the identity of the resource.
     *
     * @return the identity value
     */
    public Identity identity() {
        return this.identity;
    }

    /**
     * Set the identity of the resource.
     *
     * @param identity the identity value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withIdentity(Identity identity) {
        this.identity = identity;
        return this;
    }

    /**
     * Get user domain assigned to the storage account. Name is the CNAME source. Only one custom domain is supported per storage account at this time. To clear the existing custom domain, use an empty string for the custom domain name property.
     *
     * @return the customDomain value
     */
    public CustomDomain customDomain() {
        return this.customDomain;
    }

    /**
     * Set user domain assigned to the storage account. Name is the CNAME source. Only one custom domain is supported per storage account at this time. To clear the existing custom domain, use an empty string for the custom domain name property.
     *
     * @param customDomain the customDomain value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withCustomDomain(CustomDomain customDomain) {
        this.customDomain = customDomain;
        return this;
    }

    /**
     * Get not applicable. Azure Storage encryption is enabled for all storage accounts and cannot be disabled.
     *
     * @return the encryption value
     */
    public Encryption encryption() {
        return this.encryption;
    }

    /**
     * Set not applicable. Azure Storage encryption is enabled for all storage accounts and cannot be disabled.
     *
     * @param encryption the encryption value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withEncryption(Encryption encryption) {
        this.encryption = encryption;
        return this;
    }

    /**
     * Get network rule set.
     *
     * @return the networkRuleSet value
     */
    public NetworkRuleSet networkRuleSet() {
        return this.networkRuleSet;
    }

    /**
     * Set network rule set.
     *
     * @param networkRuleSet the networkRuleSet value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withNetworkRuleSet(NetworkRuleSet networkRuleSet) {
        this.networkRuleSet = networkRuleSet;
        return this;
    }

    /**
     * Get required for storage accounts where kind = BlobStorage. The access tier used for billing. Possible values include: 'Hot', 'Cool'.
     *
     * @return the accessTier value
     */
    public AccessTier accessTier() {
        return this.accessTier;
    }

    /**
     * Set required for storage accounts where kind = BlobStorage. The access tier used for billing. Possible values include: 'Hot', 'Cool'.
     *
     * @param accessTier the accessTier value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withAccessTier(AccessTier accessTier) {
        this.accessTier = accessTier;
        return this;
    }

    /**
     * Get provides the identity based authentication settings for Azure Files.
     *
     * @return the azureFilesIdentityBasedAuthentication value
     */
    public AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication() {
        return this.azureFilesIdentityBasedAuthentication;
    }

    /**
     * Set provides the identity based authentication settings for Azure Files.
     *
     * @param azureFilesIdentityBasedAuthentication the azureFilesIdentityBasedAuthentication value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withAzureFilesIdentityBasedAuthentication(AzureFilesIdentityBasedAuthentication azureFilesIdentityBasedAuthentication) {
        this.azureFilesIdentityBasedAuthentication = azureFilesIdentityBasedAuthentication;
        return this;
    }

    /**
     * Get allows https traffic only to storage service if sets to true. The default value is true since API version 2019-04-01.
     *
     * @return the enableHttpsTrafficOnly value
     */
    public Boolean enableHttpsTrafficOnly() {
        return this.enableHttpsTrafficOnly;
    }

    /**
     * Set allows https traffic only to storage service if sets to true. The default value is true since API version 2019-04-01.
     *
     * @param enableHttpsTrafficOnly the enableHttpsTrafficOnly value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withEnableHttpsTrafficOnly(Boolean enableHttpsTrafficOnly) {
        this.enableHttpsTrafficOnly = enableHttpsTrafficOnly;
        return this;
    }

    /**
     * Get account HierarchicalNamespace enabled if sets to true.
     *
     * @return the isHnsEnabled value
     */
    public Boolean isHnsEnabled() {
        return this.isHnsEnabled;
    }

    /**
     * Set account HierarchicalNamespace enabled if sets to true.
     *
     * @param isHnsEnabled the isHnsEnabled value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withIsHnsEnabled(Boolean isHnsEnabled) {
        this.isHnsEnabled = isHnsEnabled;
        return this;
    }

    /**
     * Get allow large file shares if sets to Enabled. It cannot be disabled once it is enabled. Possible values include: 'Disabled', 'Enabled'.
     *
     * @return the largeFileSharesState value
     */
    public LargeFileSharesState largeFileSharesState() {
        return this.largeFileSharesState;
    }

    /**
     * Set allow large file shares if sets to Enabled. It cannot be disabled once it is enabled. Possible values include: 'Disabled', 'Enabled'.
     *
     * @param largeFileSharesState the largeFileSharesState value to set
     * @return the StorageAccountCreateParameters object itself.
     */
    public StorageAccountCreateParameters withLargeFileSharesState(LargeFileSharesState largeFileSharesState) {
        this.largeFileSharesState = largeFileSharesState;
        return this;
    }

}