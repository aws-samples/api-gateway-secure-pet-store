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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Abstract class implementing the DemoAction interface. This class is used to declare utility method
 * shared with all Action implementations
 */
public abstract class AbstractDemoAction implements DemoAction {
    /**
     * Returns an initialized Gson object with the default configuration
     * @return An initialized Gson object
     */
    protected Gson getGson() {
        return new GsonBuilder()
                //.enableComplexMapKeySerialization()
                //.serializeNulls()
                //.setDateFormat(DateFormat.LONG)
                //.setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
                .setPrettyPrinting()
                .create();
    }
}
