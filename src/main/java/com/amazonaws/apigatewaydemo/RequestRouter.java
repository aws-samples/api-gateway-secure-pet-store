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
package com.amazonaws.apigatewaydemo;

import com.amazonaws.apigatewaydemo.action.DemoAction;
import com.amazonaws.apigatewaydemo.exception.BadRequestException;
import com.amazonaws.apigatewaydemo.exception.InternalErrorException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This class contains the main event handler for the Lambda function.
 */
public class RequestRouter {
    /**
     * The main Lambda function handler. Receives the request as an input stream, parses the json and looks for the
     * "action" property to decide where to route the request. The "body" property of the incoming request is passed
     * to the DemoAction implementation as a request body.
     *
     * @param request  The InputStream for the incoming event. This should contain an "action" and "body" properties. The
     *                 action property should contain the namespaced name of the class that should handle the invocation.
     *                 The class should implement the DemoAction interface. The body property should contain the full
     *                 request body for the action class.
     * @param response An OutputStream where the response returned by the action class is written
     * @param context  The Lambda Context object
     * @throws IOException            This is thrown when an error occurs while reading the incoming request or writing to the response
     *                                OutputStream.
     * @throws BadRequestException    This Exception is thrown whenever parameters are missing from the request or the action
     *                                class can't be found
     * @throws InternalErrorException This Exception is thrown when an internal error occurs, for example when the database
     *                                is not accessible
     */
    public static void lambdaHandler(InputStream request, OutputStream response, Context context) throws IOException, BadRequestException, InternalErrorException {
        LambdaLogger logger = context.getLogger();

        JsonParser parser = new JsonParser();
        JsonObject inputObj = parser.parse(IOUtils.toString(request)).getAsJsonObject();

        if (inputObj == null || inputObj.get("action") == null || inputObj.get("action").getAsString().trim().equals("")) {
            logger.log("Invald inputObj, could not find action parameter");
            throw new BadRequestException("Could not find action value in request");
        }

        String actionClass = inputObj.get("action").getAsString();
        DemoAction action;

        try {
            action = DemoAction.class.cast(Class.forName(actionClass).newInstance());
        } catch (final InstantiationException e) {
            logger.log("Error while instantiating action class\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        } catch (final IllegalAccessException e) {
            logger.log("Illegal access while instantiating action class\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        } catch (final ClassNotFoundException e) {
            logger.log("Action class could not be found\n" + e.getMessage());
            throw new InternalErrorException(e.getMessage());
        }

        if (action == null) {
            logger.log("Action class is null");
            throw new BadRequestException("Invalid action class");
        }

        JsonObject body = null;
        if (inputObj.get("body") != null) {
            body = inputObj.get("body").getAsJsonObject();
        }

        String output = action.handle(body, context);

        IOUtils.write(output, response);
    }
}
