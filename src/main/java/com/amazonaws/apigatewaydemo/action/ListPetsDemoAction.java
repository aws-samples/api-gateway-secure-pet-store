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

import com.amazonaws.apigatewaydemo.configuration.DynamoDBConfiguration;
import com.amazonaws.apigatewaydemo.exception.BadRequestException;
import com.amazonaws.apigatewaydemo.exception.InternalErrorException;
import com.amazonaws.apigatewaydemo.model.DAOFactory;
import com.amazonaws.apigatewaydemo.model.action.ListPetsResponse;
import com.amazonaws.apigatewaydemo.model.pet.Pet;
import com.amazonaws.apigatewaydemo.model.pet.PetDAO;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonObject;

import java.util.List;

/**
 * Action to return a list of pets from in the data store
 * <p/>
 * GET to /pets/
 */
public class ListPetsDemoAction extends AbstractDemoAction {
    private static LambdaLogger logger;

    public String handle(JsonObject request, Context lambdaContext) throws BadRequestException, InternalErrorException {
        logger = lambdaContext.getLogger();

        PetDAO dao = DAOFactory.getPetDAO();

        List<Pet> pets = dao.getPets(DynamoDBConfiguration.SCAN_LIMIT);

        ListPetsResponse output = new ListPetsResponse();
        output.setCount(pets.size());
        output.setPageLimit(DynamoDBConfiguration.SCAN_LIMIT);
        output.setPets(pets);

        return getGson().toJson(output);
    }
}
