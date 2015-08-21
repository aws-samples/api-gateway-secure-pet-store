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
 * This exception should be thrown whenever requests fail validation. The exception sets a default pattern in the string
 * "BAD_REQ: .*" that can be easily matched from the API Gateway for mapping.
 */
public class BadRequestException extends Exception {
    private static final String PREFIX = "BAD_REQ: ";
    public BadRequestException(String s, Exception e) {
        super(PREFIX + s, e);
    }

    public BadRequestException(String s) {
        super(PREFIX + s);
    }
}
