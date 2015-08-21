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
 * This exception is thrown whenever this service is not authorize to communicate with another AWS service, it should not
 * be exposed to Lambda or returned to the client. When this exception is caught we should throw an InternalErrorException
 */
public class AuthorizationException extends Exception {
    public AuthorizationException(String s, Exception e) {
        super(s, e);
    }

    public AuthorizationException(String s) {
        super(s);
    }
}
