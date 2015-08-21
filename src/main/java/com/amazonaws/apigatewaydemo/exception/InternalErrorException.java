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
package com.amazonaws.apigatewaydemo.exception;

/**
 * This exception is thrown whenever an internal error occurs, for example a DAO error if the data store is not accessible.
 * The exception sets a default pattern in the string "INT_ERROR: .*" that can be easily matched from the API Gateway for
 * mapping.
 */
public class InternalErrorException extends Exception {
    private static final String PREFIX = "INT_ERROR: ";

    public InternalErrorException(String s, Exception e) {
        super(PREFIX + s, e);
    }

    public InternalErrorException(String s) {
        super(PREFIX + s);
    }
}
