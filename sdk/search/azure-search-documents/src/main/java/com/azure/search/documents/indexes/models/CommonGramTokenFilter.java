// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.search.documents.indexes.models;

import com.azure.core.annotation.Fluent;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import java.util.List;

/**
 * Construct bigrams for frequently occurring terms while indexing. Single
 * terms are still indexed too, with bigrams overlaid. This token filter is
 * implemented using Apache Lucene.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "@odata.type")
@JsonTypeName("#Microsoft.Azure.Search.CommonGramTokenFilter")
@Fluent
public final class CommonGramTokenFilter extends TokenFilter {
    /*
     * The set of common words.
     */
    @JsonProperty(value = "commonWords", required = true)
    private List<String> commonWords;

    /*
     * A value indicating whether common words matching will be case
     * insensitive. Default is false.
     */
    @JsonProperty(value = "ignoreCase")
    private Boolean ignoreCase;

    /*
     * A value that indicates whether the token filter is in query mode. When
     * in query mode, the token filter generates bigrams and then removes
     * common words and single terms followed by a common word. Default is
     * false.
     */
    @JsonProperty(value = "queryMode")
    private Boolean useQueryMode;

    /**
     * Get the commonWords property: The set of common words.
     *
     * @return the commonWords value.
     */
    public List<String> getCommonWords() {
        return this.commonWords;
    }

    /**
     * Set the commonWords property: The set of common words.
     *
     * @param commonWords the commonWords value to set.
     * @return the CommonGramTokenFilter object itself.
     */
    public CommonGramTokenFilter setCommonWords(List<String> commonWords) {
        this.commonWords = commonWords;
        return this;
    }

    /**
     * Get the ignoreCase property: A value indicating whether common words
     * matching will be case insensitive. Default is false.
     *
     * @return the ignoreCase value.
     */
    public Boolean isIgnoreCase() {
        return this.ignoreCase;
    }

    /**
     * Set the ignoreCase property: A value indicating whether common words
     * matching will be case insensitive. Default is false.
     *
     * @param ignoreCase the ignoreCase value to set.
     * @return the CommonGramTokenFilter object itself.
     */
    public CommonGramTokenFilter setIgnoreCase(Boolean ignoreCase) {
        this.ignoreCase = ignoreCase;
        return this;
    }

    /**
     * Get the useQueryMode property: A value that indicates whether the token
     * filter is in query mode. When in query mode, the token filter generates
     * bigrams and then removes common words and single terms followed by a
     * common word. Default is false.
     *
     * @return the useQueryMode value.
     */
    public Boolean isUseQueryMode() {
        return this.useQueryMode;
    }

    /**
     * Set the useQueryMode property: A value that indicates whether the token
     * filter is in query mode. When in query mode, the token filter generates
     * bigrams and then removes common words and single terms followed by a
     * common word. Default is false.
     *
     * @param useQueryMode the useQueryMode value to set.
     * @return the CommonGramTokenFilter object itself.
     */
    public CommonGramTokenFilter setUseQueryMode(Boolean useQueryMode) {
        this.useQueryMode = useQueryMode;
        return this;
    }
}
