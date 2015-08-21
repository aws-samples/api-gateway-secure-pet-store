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
package com.amazonaws.apigatewaydemo.provider;

/**
 * Factory object to create providers
 */
public class ProviderFactory {
    /**
     * List of available credentials providers
     */
    public enum CredentialsProviderType {
        Cognito
    }

    /**
     * Gets the default CredentialsProvider implementation
     *
     * @return An initialized CognitoCredentialsProvider
     */
    public static CredentialsProvider getCredentialsProvider() {
        return getCredentialsProvider(CredentialsProviderType.Cognito);
    }

    /**
     * Returns the request CredentialsProvider implementation
     *
     * @param type The implementation type
     * @return An initialized CredentialsProvider object
     */
    public static CredentialsProvider getCredentialsProvider(CredentialsProviderType type) {
        CredentialsProvider provider = null;
        switch (type) {
            case Cognito:
                provider = CognitoCredentialsProvider.getInstance();
                break;
        }

        return provider;
    }
}
