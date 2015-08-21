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

import com.amazonaws.apigatewaydemo.exception.DAOException;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;

/**
 * DynamoDB implementation of the UserDAO interface. This class reads the configuration from the DyanmoDBConfiguration
 * object in the com.amazonaws.apigatewaydemo.configuration package. Credentials to access DynamoDB are retrieved from
 * the Lambda environment.
 * <p/>
 * The table in DynamoDB should be created with an Hash Key called username.
 */
public class DDBUserDAO implements UserDAO {
    private static DDBUserDAO instance = null;

    // credentials for the client come from the environment variables pre-configured by Lambda. These are tied to the
    // Lambda function execution role.
    private static AmazonDynamoDBClient ddbClient = new AmazonDynamoDBClient();

    /**
     * Returns an initialized instance of the DDBUserDAO object. DAO objects should be retrieved through the DAOFactory
     * class
     *
     * @return An initialized instance of the DDBUserDAO object
     */
    public static DDBUserDAO getInstance() {
        if (instance == null) {
            instance = new DDBUserDAO();
        }

        return instance;
    }

    protected DDBUserDAO() {
        // prevents instantiation
    }

    /**
     * Queries DynamoDB to find a user by its Username
     *
     * @param username The username to search for
     * @return A populated User object, null if the user was not found
     * @throws DAOException
     */
    public User getUserByName(String username) throws DAOException {
        if (username == null || username.trim().equals("")) {
            throw new DAOException("Cannot lookup null or empty user");
        }

        return getMapper().load(User.class, username);
    }

    /**
     * Inserts a new row in the DynamoDB users table.
     *
     * @param user The new user information
     * @return The username that was just inserted in DynamoDB
     * @throws DAOException
     */
    public String createUser(User user) throws DAOException {
        if (user.getUsername() == null || user.getUsername().trim().equals("")) {
            throw new DAOException("Cannot create user with empty username");
        }

        if (getUserByName(user.getUsername()) != null) {
            throw new DAOException("Username must be unique");
        }

        getMapper().save(user);

        return user.getUsername();
    }

    /**
     * Returns a DynamoDBMapper object initialized with the default DynamoDB client
     *
     * @return An initialized DynamoDBMapper
     */
    protected DynamoDBMapper getMapper() {
        return new DynamoDBMapper(ddbClient);
    }
}
