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

import com.amazonaws.apigatewaydemo.exception.BadRequestException;
import com.amazonaws.apigatewaydemo.exception.InternalErrorException;
import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

/**
 * DemoAction defines the methods called by the RequestRouter when it is invoked by a Lambda function. Implementing
 * classes should be able to receive a JsonObject containing the body of the request (taken from the "body" property)
 * of the incoming JSON, and return a String that contains valid json.
 */
public interface DemoAction {
    /**
     * The main handler method for each requests. This method is called by the RequestRouter when invoked by a Lambda
     * function.
     *
     * @param request       Receives a JsonObject containing the body content
     * @param lambdaContext The Lambda context passed by the AWS Lambda environment
     * @return A string containing valid JSON to be returned to the client
     * @throws BadRequestException    This exception should be thrown whenever request parameters are not valid or improperly
     *                                formatted
     * @throws InternalErrorException This exception should be thrown if an error that is independent from user input happens
     */
    String handle(JsonObject request, Context lambdaContext) throws BadRequestException, InternalErrorException;
}
