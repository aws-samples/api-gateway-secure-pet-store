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
package com.amazonaws.apigatewaydemo.model.user;

import com.amazonaws.apigatewaydemo.configuration.DynamoDBConfiguration;
import com.amazonaws.services.dynamodbv2.datamodeling.*;

import java.nio.ByteBuffer;

/**
 * User object model - this class is annotated to be used with the DynamoDB object mapper
 */
@DynamoDBTable(tableName = DynamoDBConfiguration.USERS_TABLE_NAME)
public class User {
    private String username;
    private ByteBuffer password;
    private ByteBuffer salt;
    private UserIdentity identity;

    public User() {

    }

    @DynamoDBHashKey(attributeName = "username")
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @DynamoDBAttribute(attributeName = "password")
    public ByteBuffer getPassword() {
        return password;
    }

    public void setPassword(ByteBuffer password) {
        this.password = password;
    }

    @DynamoDBAttribute(attributeName = "passwordSalt")
    public ByteBuffer getSalt() {
        return salt;
    }

    public void setSalt(ByteBuffer salt) {
        this.salt = salt;
    }

    @DynamoDBAttribute(attributeName = "identityId")
    public String getCognitoIdentityId() {
        if (this.identity == null) {
            return null;
        }
        return this.identity.getIdentityId();
    }

    public void setCognitoIdentityId(String cognitoIdentityId) {
        if (this.identity == null) {
            this.identity = new UserIdentity();
        }
        this.identity.setIdentityId(cognitoIdentityId);
    }

    @DynamoDBIgnore
    public byte[] getPasswordBytes() {
        return password.array();
    }

    @DynamoDBIgnore
    public byte[] getSaltBytes() {
        return salt.array();
    }

    @DynamoDBIgnore
    public UserIdentity getIdentity() {
        return identity;
    }

    public void setIdentity(UserIdentity identity) {
        this.identity = identity;
    }
}
