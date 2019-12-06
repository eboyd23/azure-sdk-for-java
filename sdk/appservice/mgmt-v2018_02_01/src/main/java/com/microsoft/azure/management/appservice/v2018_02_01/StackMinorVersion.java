/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.appservice.v2018_02_01;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Application stack minor version.
 */
public class StackMinorVersion {
    /**
     * Application stack minor version (display only).
     */
    @JsonProperty(value = "displayVersion")
    private String displayVersion;

    /**
     * Application stack minor version (runtime only).
     */
    @JsonProperty(value = "runtimeVersion")
    private String runtimeVersion;

    /**
     * &lt;code&gt;true&lt;/code&gt; if this is the default minor version;
     * otherwise, &lt;code&gt;false&lt;/code&gt;.
     */
    @JsonProperty(value = "isDefault")
    private Boolean isDefault;

    /**
     * &lt;code&gt;true&lt;/code&gt; if this supports Remote Debugging,
     * otherwise &lt;code&gt;false&lt;/code&gt;.
     */
    @JsonProperty(value = "isRemoteDebuggingEnabled")
    private Boolean isRemoteDebuggingEnabled;

    /**
     * Get application stack minor version (display only).
     *
     * @return the displayVersion value
     */
    public String displayVersion() {
        return this.displayVersion;
    }

    /**
     * Set application stack minor version (display only).
     *
     * @param displayVersion the displayVersion value to set
     * @return the StackMinorVersion object itself.
     */
    public StackMinorVersion withDisplayVersion(String displayVersion) {
        this.displayVersion = displayVersion;
        return this;
    }

    /**
     * Get application stack minor version (runtime only).
     *
     * @return the runtimeVersion value
     */
    public String runtimeVersion() {
        return this.runtimeVersion;
    }

    /**
     * Set application stack minor version (runtime only).
     *
     * @param runtimeVersion the runtimeVersion value to set
     * @return the StackMinorVersion object itself.
     */
    public StackMinorVersion withRuntimeVersion(String runtimeVersion) {
        this.runtimeVersion = runtimeVersion;
        return this;
    }

    /**
     * Get &lt;code&gt;true&lt;/code&gt; if this is the default minor version; otherwise, &lt;code&gt;false&lt;/code&gt;.
     *
     * @return the isDefault value
     */
    public Boolean isDefault() {
        return this.isDefault;
    }

    /**
     * Set &lt;code&gt;true&lt;/code&gt; if this is the default minor version; otherwise, &lt;code&gt;false&lt;/code&gt;.
     *
     * @param isDefault the isDefault value to set
     * @return the StackMinorVersion object itself.
     */
    public StackMinorVersion withIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
        return this;
    }

    /**
     * Get &lt;code&gt;true&lt;/code&gt; if this supports Remote Debugging, otherwise &lt;code&gt;false&lt;/code&gt;.
     *
     * @return the isRemoteDebuggingEnabled value
     */
    public Boolean isRemoteDebuggingEnabled() {
        return this.isRemoteDebuggingEnabled;
    }

    /**
     * Set &lt;code&gt;true&lt;/code&gt; if this supports Remote Debugging, otherwise &lt;code&gt;false&lt;/code&gt;.
     *
     * @param isRemoteDebuggingEnabled the isRemoteDebuggingEnabled value to set
     * @return the StackMinorVersion object itself.
     */
    public StackMinorVersion withIsRemoteDebuggingEnabled(Boolean isRemoteDebuggingEnabled) {
        this.isRemoteDebuggingEnabled = isRemoteDebuggingEnabled;
        return this;
    }

}