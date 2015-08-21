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
 * Static list of error messages when exceptions are thrown
 */
public class ExceptionMessages {
    public static final String EX_INVALID_INPUT = "Invalid input parameters";
    public static final String EX_PWD_SALT = "Cannot generate password salt";
    public static final String EX_PWD_ENCRYPT = "Failed to encrypt password";
    public static final String EX_PWD_SAVE = "Failed to save password";
    public static final String EX_NO_COGNITO_IDENTITY = "Cannot retrieve Cognito identity";
    public static final String EX_DAO_ERROR = "Error loading user";
}
