/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.hanaonazure.v2017_11_03_preview.implementation;

import com.microsoft.azure.arm.resources.models.implementation.GroupableResourceCoreImpl;
import com.microsoft.azure.management.hanaonazure.v2017_11_03_preview.SapMonitor;
import rx.Observable;
import com.microsoft.azure.management.hanaonazure.v2017_11_03_preview.HanaProvisioningStatesEnum;

class SapMonitorImpl extends GroupableResourceCoreImpl<SapMonitor, SapMonitorInner, SapMonitorImpl, HanaOnAzureManager> implements SapMonitor, SapMonitor.Definition, SapMonitor.Update {
    SapMonitorImpl(String name, SapMonitorInner inner, HanaOnAzureManager manager) {
        super(name, inner, manager);
    }

    @Override
    public Observable<SapMonitor> createResourceAsync() {
        SapMonitorsInner client = this.manager().inner().sapMonitors();
        return client.createAsync(this.resourceGroupName(), this.name(), this.inner())
            .map(innerToFluentMap(this));
    }

    @Override
    public Observable<SapMonitor> updateResourceAsync() {
        SapMonitorsInner client = this.manager().inner().sapMonitors();
        return client.createAsync(this.resourceGroupName(), this.name(), this.inner())
            .map(innerToFluentMap(this));
    }

    @Override
    protected Observable<SapMonitorInner> getInnerAsync() {
        SapMonitorsInner client = this.manager().inner().sapMonitors();
        return client.getByResourceGroupAsync(this.resourceGroupName(), this.name());
    }

    @Override
    public boolean isInCreateMode() {
        return this.inner().id() == null;
    }


    @Override
    public Boolean enableCustomerAnalytics() {
        return this.inner().enableCustomerAnalytics();
    }

    @Override
    public String hanaDbCredentialsMsiId() {
        return this.inner().hanaDbCredentialsMsiId();
    }

    @Override
    public String hanaDbName() {
        return this.inner().hanaDbName();
    }

    @Override
    public String hanaDbPassword() {
        return this.inner().hanaDbPassword();
    }

    @Override
    public String hanaDbPasswordKeyVaultUrl() {
        return this.inner().hanaDbPasswordKeyVaultUrl();
    }

    @Override
    public Integer hanaDbSqlPort() {
        return this.inner().hanaDbSqlPort();
    }

    @Override
    public String hanaDbUsername() {
        return this.inner().hanaDbUsername();
    }

    @Override
    public String hanaHostname() {
        return this.inner().hanaHostname();
    }

    @Override
    public String hanaSubnet() {
        return this.inner().hanaSubnet();
    }

    @Override
    public String keyVaultId() {
        return this.inner().keyVaultId();
    }

    @Override
    public String logAnalyticsWorkspaceArmId() {
        return this.inner().logAnalyticsWorkspaceArmId();
    }

    @Override
    public String logAnalyticsWorkspaceId() {
        return this.inner().logAnalyticsWorkspaceId();
    }

    @Override
    public String logAnalyticsWorkspaceSharedKey() {
        return this.inner().logAnalyticsWorkspaceSharedKey();
    }

    @Override
    public String managedResourceGroupName() {
        return this.inner().managedResourceGroupName();
    }

    @Override
    public HanaProvisioningStatesEnum provisioningState() {
        return this.inner().provisioningState();
    }

    @Override
    public SapMonitorImpl withEnableCustomerAnalytics(Boolean enableCustomerAnalytics) {
        this.inner().withEnableCustomerAnalytics(enableCustomerAnalytics);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaDbCredentialsMsiId(String hanaDbCredentialsMsiId) {
        this.inner().withHanaDbCredentialsMsiId(hanaDbCredentialsMsiId);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaDbName(String hanaDbName) {
        this.inner().withHanaDbName(hanaDbName);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaDbPassword(String hanaDbPassword) {
        this.inner().withHanaDbPassword(hanaDbPassword);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaDbPasswordKeyVaultUrl(String hanaDbPasswordKeyVaultUrl) {
        this.inner().withHanaDbPasswordKeyVaultUrl(hanaDbPasswordKeyVaultUrl);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaDbSqlPort(Integer hanaDbSqlPort) {
        this.inner().withHanaDbSqlPort(hanaDbSqlPort);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaDbUsername(String hanaDbUsername) {
        this.inner().withHanaDbUsername(hanaDbUsername);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaHostname(String hanaHostname) {
        this.inner().withHanaHostname(hanaHostname);
        return this;
    }

    @Override
    public SapMonitorImpl withHanaSubnet(String hanaSubnet) {
        this.inner().withHanaSubnet(hanaSubnet);
        return this;
    }

    @Override
    public SapMonitorImpl withKeyVaultId(String keyVaultId) {
        this.inner().withKeyVaultId(keyVaultId);
        return this;
    }

    @Override
    public SapMonitorImpl withLogAnalyticsWorkspaceArmId(String logAnalyticsWorkspaceArmId) {
        this.inner().withLogAnalyticsWorkspaceArmId(logAnalyticsWorkspaceArmId);
        return this;
    }

    @Override
    public SapMonitorImpl withLogAnalyticsWorkspaceId(String logAnalyticsWorkspaceId) {
        this.inner().withLogAnalyticsWorkspaceId(logAnalyticsWorkspaceId);
        return this;
    }

    @Override
    public SapMonitorImpl withLogAnalyticsWorkspaceSharedKey(String logAnalyticsWorkspaceSharedKey) {
        this.inner().withLogAnalyticsWorkspaceSharedKey(logAnalyticsWorkspaceSharedKey);
        return this;
    }

}
