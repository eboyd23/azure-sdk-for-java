/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.network.v2020_05_01;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * IpGroups associated with azure firewall.
 */
public class AzureFirewallIpGroups {
    /**
     * Resource ID.
     */
    @JsonProperty(value = "id", access = JsonProperty.Access.WRITE_ONLY)
    private String id;

    /**
     * The iteration number.
     */
    @JsonProperty(value = "changeNumber", access = JsonProperty.Access.WRITE_ONLY)
    private String changeNumber;

    /**
     * Get resource ID.
     *
     * @return the id value
     */
    public String id() {
        return this.id;
    }

    /**
     * Get the iteration number.
     *
     * @return the changeNumber value
     */
    public String changeNumber() {
        return this.changeNumber;
    }

}
