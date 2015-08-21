/*
 * Copyright 2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"). You may not use this file except in compliance
 * with the License. A copy of the License is located at
 *
 * http://aws.amazon.com/apache2.0/
 *
 * or in the "license" file accompanying this file. This file is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */
package com.amazonaws.apigatewaydemo.configuration;

/**
 * Configuration parameters for the DynamoDB DAO objects
 */
public class DynamoDBConfiguration {
    // TODO: Specify the name of the Users table in DynamoDB
    public static final String USERS_TABLE_NAME = "users";
    // TODO: Specify the name of the Pet table in DynamoDB
    public static final String PET_TABLE_NAME = "pets";

    public static final int SCAN_LIMIT = 50;
}
