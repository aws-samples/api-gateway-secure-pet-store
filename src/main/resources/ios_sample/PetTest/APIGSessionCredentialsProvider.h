/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

#import <UIKit/UIKit.h>
#import <AWSCore/AWSCore.h>

@class PETCredentials;

@interface APIGSessionCredentialsProvider : NSObject <AWSCredentialsProvider>

/**
 *  Access Key component of credentials.
 */
@property (strong, nonatomic, readonly) NSString *accessKey;

/**
 *  Secret Access Key component of credentials.
 */
@property (strong, nonatomic, readonly) NSString *secretKey;

/**
 *  Session Token component of credentials.
 */
@property (strong, nonatomic, readonly) NSString *sessionKey;

/**
 *  Date at which these credentials will expire.
 */
@property (strong, nonatomic, readonly) NSDate *expiration;

/**
 * Creates a new instance of the APIGSessionCredentialsProvider given a username and 
 * password.
 *
 * @param username The username to login with
 * @param pwd The clear-text password to login with
 * @return instancetype A new APISessionCredentialsProvider object initialized
 *                      with the given credentials
 */
- (instancetype)initWithUsername:(NSString *)username
                     andPassword:(NSString *)pwd;

/**
 * Iinitializes a new APIGSessionCredentialsProvider with the given set of credentials
 *
 * @param creds A populated PETCredentials model object
 * @return instancetype A new APISessionCredentialsProvider object initialized
 *                      with the given credentials
 */
- (instancetype)initWithCredentials:(PETCredentials *)creds;

/**
 * Registers a new user with the credentials given to the constructor of the object.
 * If there was an error during the registration the error property of the AWSTask result
 * will be populated.
 *
 * @return AWSTask a new AWSTask for the registration API call
 */
- (AWSTask *)registerUser;

- (NSString *)getUsername;

@end
