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
package com.amazonaws.apigatewaydemo.model.pet;

import com.amazonaws.apigatewaydemo.exception.DAOException;

import java.util.List;

/**
 * This interface defines the methods required for an implementation of the PetDAO object
 */
public interface PetDAO {
    /**
     * Creates a new pet in the data store
     *
     * @param pet The pet object to be created
     * @return The generated petId
     * @throws DAOException Whenever an error occurs while accessing the data store
     */
    String createPet(Pet pet) throws DAOException;

    /**
     * Retrieves a Pet object by its id
     *
     * @param petId The petId to look for
     * @return An initialized and populated Pet object. If the pet couldn't be found return null
     * @throws DAOException Whenever a data store access error occurs
     */
    Pet getPetById(String petId) throws DAOException;

    List<Pet> getPets(int limit);
}
