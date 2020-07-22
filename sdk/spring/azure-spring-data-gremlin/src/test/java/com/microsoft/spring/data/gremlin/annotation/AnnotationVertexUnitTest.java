// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.microsoft.spring.data.gremlin.annotation;

import com.microsoft.spring.data.gremlin.common.GremlinUtils;
import com.microsoft.spring.data.gremlin.common.domain.Library;
import com.microsoft.spring.data.gremlin.common.domain.Person;
import com.microsoft.spring.data.gremlin.common.TestConstants;
import com.microsoft.spring.data.gremlin.conversion.source.GremlinSource;
import com.microsoft.spring.data.gremlin.conversion.source.GremlinSourceVertex;
import org.junit.Assert;
import org.junit.Test;

public class AnnotationVertexUnitTest {

    @Test
    public void testAnnotationVertexDefaultLabel() {
        final GremlinSource<Library> source = GremlinUtils.toGremlinSource(Library.class);

        Assert.assertTrue(source instanceof GremlinSourceVertex);
        Assert.assertNotNull(source.getLabel());
        Assert.assertEquals(source.getLabel(), Library.class.getSimpleName());
    }

    @Test
    public void testAnnotationVertexSpecifiedLabel() {
        final GremlinSource<Person> source = GremlinUtils.toGremlinSource(Person.class);

        Assert.assertTrue(source instanceof GremlinSourceVertex);
        Assert.assertNotNull(source.getLabel());
        Assert.assertEquals(source.getLabel(), TestConstants.VERTEX_PERSON_LABEL);
    }
}
