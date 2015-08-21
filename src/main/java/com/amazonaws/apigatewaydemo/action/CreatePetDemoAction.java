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
package com.amazonaws.apigatewaydemo.action;

import com.amazonaws.apigatewaydemo.configuration.ExceptionMessages;
import com.amazonaws.apigatewaydemo.exception.BadRequestException;
import com.amazonaws.apigatewaydemo.exception.DAOException;
import com.amazonaws.apigatewaydemo.exception.InternalErrorException;
import com.amazonaws.apigatewaydemo.model.DAOFactory;
import com.amazonaws.apigatewaydemo.model.action.CreatePetRequest;
import com.amazonaws.apigatewaydemo.model.action.CreatePetResponse;
import com.amazonaws.apigatewaydemo.model.pet.Pet;
import com.amazonaws.apigatewaydemo.model.pet.PetDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonObject;

/**
 * Action that creates a new Pet in the data store
 * <p/>
 * POST to /pets/
 */
public class CreatePetDemoAction extends AbstractDemoAction {
    private LambdaLogger logger;

    public String handle(JsonObject request, Context lambdaContext) throws BadRequestException, InternalErrorException {
        logger = lambdaContext.getLogger();

        CreatePetRequest input = getGson().fromJson(request, CreatePetRequest.class);

        if (input == null ||
                input.getPetType() == null ||
                input.getPetType().trim().equals("")) {
            throw new BadRequestException(ExceptionMessages.EX_INVALID_INPUT);
        }

        PetDAO dao = DAOFactory.getPetDAO();

        Pet newPet = new Pet();
        newPet.setPetType(input.getPetType());
        newPet.setPetName(input.getPetName());
        newPet.setPetAge(input.getPetAge());

        String petId;

        try {
            petId = dao.createPet(newPet);
        } catch (final DAOException e) {
            logger.log("Error while creating new pet\n" + e.getMessage());
            throw new InternalErrorException(ExceptionMessages.EX_DAO_ERROR);
        }

        if (petId == null || petId.trim().equals("")) {
            logger.log("PetID is null or empty");
            throw new InternalErrorException(ExceptionMessages.EX_DAO_ERROR);
        }

        CreatePetResponse output = new CreatePetResponse();
        output.setPetId(petId);

        return getGson().toJson(output);
    }
}
