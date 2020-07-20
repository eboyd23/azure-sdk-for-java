// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.data.tables;

import com.azure.core.util.ExpandableStringEnum;

/**
 * Defines values for TableErrorCode.
 */
public class TableErrorCode extends ExpandableStringEnum<TableErrorCode> {

    /**
     * Static value ResourceAlreadyExists for TableErrorCode.
     */
    public static final TableErrorCode RESOURCE_ALREADY_EXISTS = fromString("ResourceAlreadyExists");

    /**
     * Static value ResourceNotFound for TableErrorCode.
     */
    public static final TableErrorCode RESOURCE_NOT_FOUND = fromString("ResourceNotFound");

    public static TableErrorCode fromString(String name) {
        return fromString(name, TableErrorCode.class);
    }

}
