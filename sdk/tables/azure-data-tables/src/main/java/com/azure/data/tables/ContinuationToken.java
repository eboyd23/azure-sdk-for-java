package com.azure.data.tables;

public class ContinuationToken {
    String nextPartitionKey;

    public String getNextPartitionKey() {
        return nextPartitionKey;
    }

    public String getNextRowKey() {
        return nextRowKey;
    }

    String nextRowKey;
    public ContinuationToken (String nextPartitionKey, String nextRowKey) {
        this.nextPartitionKey = nextPartitionKey;
        this.nextRowKey = nextRowKey;
    }
}
