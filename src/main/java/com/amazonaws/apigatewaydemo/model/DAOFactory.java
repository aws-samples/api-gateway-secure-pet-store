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
package com.amazonaws.apigatewaydemo.model;

import com.amazonaws.apigatewaydemo.model.pet.DDBPetDAO;
import com.amazonaws.apigatewaydemo.model.pet.PetDAO;
import com.amazonaws.apigatewaydemo.model.user.DDBUserDAO;
import com.amazonaws.apigatewaydemo.model.user.UserDAO;

/**
 * The DAO Factory object to abstract the implementation of DAO interfaces.
 */
public class DAOFactory {
    /**
     * Contains the implementations of the DAO objects. By default we only have a DynamoDB implementation
     */
    public enum DAOType {
        DynamoDB
    }

    /**
     * Returns the default UserDAO object
     *
     * @return The default implementation of the UserDAO object - by default this is the DynamoDB implementation
     */
    public static UserDAO getUserDAO() {
        return getUserDAO(DAOType.DynamoDB);
    }

    /**
     * Returns a UserDAO implementation
     *
     * @param daoType A value from the DAOType enum
     * @return The corresponding UserDAO implementation
     */
    public static UserDAO getUserDAO(DAOType daoType) {
        UserDAO dao = null;
        switch (daoType) {
            case DynamoDB:
                dao = DDBUserDAO.getInstance();
                break;
        }

        return dao;
    }

    /**
     * Returns the default PetDAO implementation
     *
     * @return The DynamoDB PetDAO implementation
     */
    public static PetDAO getPetDAO() {
        return getPetDAO(DAOType.DynamoDB);
    }

    /**
     * Returns a PetDAO implementation
     *
     * @param daoType The implementation type
     * @return The requested DAO implementation
     */
    public static PetDAO getPetDAO(DAOType daoType) {
        PetDAO dao = null;
        switch (daoType) {
            case DynamoDB:
                dao = DDBPetDAO.getInstance();
                break;
        }

        return dao;
    }
}
