package com.task05.model;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

// TODO: Change name
@DynamoDBTable(tableName = "Events")
@Builder
@Setter
@Getter
@ToString
public class Event
{
    @DynamoDBHashKey private String id;
    @DynamoDBAttribute private int principalId;
    @DynamoDBAttribute private String createdAt;
    @DynamoDBAttribute private Map<String, String> body;
}

