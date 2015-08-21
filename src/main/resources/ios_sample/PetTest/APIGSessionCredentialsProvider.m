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

#import "APIGSessionCredentialsProvider.h"
#import "PETLambdaMicroserviceClient.h"
#import "PETCredentials.h"

NSString *const APIGClientConfigurationKey = @"APIGCredentialsProvider";

@interface APIGSessionCredentialsProvider()

@property (strong, nonatomic) PETCredentials *credentials;

@end


@implementation APIGSessionCredentialsProvider
#pragma mark - Constructors
- (instancetype)initWithUsername:(NSString *)username
                     andPassword:(NSString *)pwd {
    if (self = [super init]) {
        _credentials = [PETCredentials new];
        _credentials.username = username;
        _credentials.password = pwd;

        [self setUpConfig];
    }

    return self;
}

- (instancetype)initWithCredentials:(PETCredentials *)creds {
    if (self = [super init]) {
        _credentials = creds;

        [self setUpConfig];
    }

    return self;
}

#pragma mark - AWSCredentialsProvider methods

- (AWSTask *)refresh {
    PETLambdaMicroserviceClient *client = [PETLambdaMicroserviceClient clientForKey:APIGClientConfigurationKey];
    PETRegisterUserRequest *req = [PETRegisterUserRequest new];
    req.username = self.credentials.username;
    req.password = self.credentials.password;
    return [[client loginPost:req] continueWithBlock:^id(AWSTask *task) {
        PETLoginUserResponse *resp = task.result;

        PETLoginUserResponse_credentials *credentials = resp.credentials;

        _accessKey = credentials.accessKey;
        _secretKey = credentials.secretKey;
        _sessionKey = credentials.sessionToken;
        _expiration = [NSDate dateWithTimeIntervalSince1970:[credentials.expiration doubleValue] / 1000];

        return nil;
    }];
}

#pragma mark - Custom and private methods

- (AWSTask *)registerUser {
    PETRegisterUserRequest *req = [PETRegisterUserRequest new];
    req.username = self.credentials.username;
    req.password = self.credentials.password;

    PETLambdaMicroserviceClient *client = [PETLambdaMicroserviceClient clientForKey:APIGClientConfigurationKey];

    return [client usersPost:req];
}

- (NSString *)getUsername {
    return self.credentials.username;
}

- (void)setUpConfig {
    AWSServiceConfiguration *defaultConfig = [[AWSServiceConfiguration alloc] initWithRegion:AWSRegionUSEast1
                                                                         credentialsProvider:nil];
    [PETLambdaMicroserviceClient registerClientWithConfiguration:defaultConfig
                                                          forKey:APIGClientConfigurationKey];
}


@end
