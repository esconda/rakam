package org.rakam.aws.dynamodb.metastore;

import io.airlift.configuration.Config;

import javax.validation.constraints.NotNull;

public class DynamodbMetastoreConfig
{
    private String tableName = "metastore";

    @Config("metastore.adapter.dynamodb.table")
    public DynamodbMetastoreConfig setTableName(String tableName) {
        this.tableName = tableName;
        return this;
    }

    @NotNull
    public String getTableName()
    {
        return tableName;
    }
}
