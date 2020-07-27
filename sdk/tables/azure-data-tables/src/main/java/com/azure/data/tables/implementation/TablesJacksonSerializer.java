// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
package com.azure.data.tables.implementation;

import com.azure.core.http.HttpHeaders;
import com.azure.core.util.logging.ClientLogger;
import com.azure.core.util.serializer.CollectionFormat;
import com.azure.core.util.serializer.JacksonAdapter;
import com.azure.core.util.serializer.SerializerAdapter;
import com.azure.core.util.serializer.SerializerEncoding;
import com.azure.data.tables.implementation.models.TableEntityQueryResponse;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Serializer for Tables responses.
 */
public class TablesJacksonSerializer implements SerializerAdapter {
    private final JacksonAdapter jacksonAdapter = new JacksonAdapter();
    private final ClientLogger logger = new ClientLogger(TablesJacksonSerializer.class);

    @Override
    public String serialize(Object object, SerializerEncoding serializerEncoding) throws IOException {
        return jacksonAdapter.serialize(object, serializerEncoding);
    }

    @Override
    public String serializeRaw(Object object) {
        return jacksonAdapter.serializeRaw(object);
    }

    @Override
    public String serializeList(List<?> list, CollectionFormat format) {
        return jacksonAdapter.serializeList(list, format);
    }

    @Override
    public <U> U deserialize(String value, Type type, SerializerEncoding serializerEncoding) throws IOException {
        if (type != TableEntityQueryResponse.class) {
            return jacksonAdapter.deserialize(value, type, serializerEncoding);
        }

        Map<String, String> d = jacksonAdapter.deserialize(value, Object.class, serializerEncoding);

        return null;
    }

    @Override
    public <U> U deserialize(HttpHeaders httpHeaders, Type type) throws IOException {
        return jacksonAdapter.deserialize(httpHeaders, type);
    }
}
