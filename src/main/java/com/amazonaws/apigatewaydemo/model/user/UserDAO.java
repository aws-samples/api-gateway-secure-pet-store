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

/**
 * This interface defines the methods required for an implementation of the UserDAO object
 */
public interface UserDAO {
    /**
     * Find a user by its unique username
     *
     * @param username The username to search for
     * @return A populated User object if the user was found, null otherwise
     * @throws DAOException
     */
    User getUserByName(String username) throws DAOException;

    /**
     * Creates a new user in the data store
     *
     * @param user The new user information
     * @return The username of the user that was created
     * @throws DAOException
     */
    String createUser(User user) throws DAOException;
}
