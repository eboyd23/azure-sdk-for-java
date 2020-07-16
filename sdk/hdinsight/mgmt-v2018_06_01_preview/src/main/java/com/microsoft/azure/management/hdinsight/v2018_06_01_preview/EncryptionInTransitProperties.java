/**
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See License.txt in the project root for
 * license information.
 *
 * Code generated by Microsoft (R) AutoRest Code Generator.
 */

package com.microsoft.azure.management.hdinsight.v2018_06_01_preview;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * The encryption-in-transit properties.
 */
public class EncryptionInTransitProperties {
    /**
     * Indicates whether or not inter cluster node communication is encrypted
     * in transit.
     */
    @JsonProperty(value = "isEncryptionInTransitEnabled")
    private Boolean isEncryptionInTransitEnabled;

    /**
     * Get indicates whether or not inter cluster node communication is encrypted in transit.
     *
     * @return the isEncryptionInTransitEnabled value
     */
    public Boolean isEncryptionInTransitEnabled() {
        return this.isEncryptionInTransitEnabled;
    }

    /**
     * Set indicates whether or not inter cluster node communication is encrypted in transit.
     *
     * @param isEncryptionInTransitEnabled the isEncryptionInTransitEnabled value to set
     * @return the EncryptionInTransitProperties object itself.
     */
    public EncryptionInTransitProperties withIsEncryptionInTransitEnabled(Boolean isEncryptionInTransitEnabled) {
        this.isEncryptionInTransitEnabled = isEncryptionInTransitEnabled;
        return this;
    }

}
