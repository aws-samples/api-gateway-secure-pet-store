/*
 Copyright 2010-2015 Amazon.com, Inc. or its affiliates. All Rights Reserved.

 Licensed under the Apache License, Version 2.0 (the "License").
 You may not use this file except in compliance with the License.
 A copy of the License is located at

 http://aws.amazon.com/apache2.0

 or in the "license" file accompanying this file. This file is distributed
 on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 express or implied. See the License for the specific language governing
 permissions and limitations under the License.
 */
 

#import <Foundation/Foundation.h>
#import <AWSAPIGateway/AWSAPIGateway.h>

#import "PETLoginUserResponse.h"
#import "PETRegisterUserRequest.h"
#import "PETListPetsResponse.h"
#import "PETCreatePetResponse.h"
#import "PETCreatePetRequest.h"
#import "PETGetPetResponse.h"
#import "PETRegisterUserResponse.h"
#import "PETError.h"

/**
 The service client object.
 */
@interface PETLambdaMicroserviceClient: AWSAPIGatewayClient

/**
 Returns the singleton service client. If the singleton object does not exist, the SDK instantiates the default service client with `defaultServiceConfiguration` from `[AWSServiceManager defaultServiceManager]`. The reference to this object is maintained by the SDK, and you do not need to retain it manually.

 If you want to enable AWS Signature, set the default service configuration in `- application:didFinishLaunchingWithOptions:`
 
 *Swift*

     func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
         let credentialProvider = AWSCognitoCredentialsProvider(regionType: .USEast1, identityPoolId: "YourIdentityPoolId")
         let configuration = AWSServiceConfiguration(region: .USEast1, credentialsProvider: credentialProvider)
         AWSServiceManager.defaultServiceManager().defaultServiceConfiguration = configuration

         return true
     }

 *Objective-C*

     - (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
          AWSCognitoCredentialsProvider *credentialsProvider = [[AWSCognitoCredentialsProvider alloc] initWithRegionType:AWSRegionUSEast1
                                                                                                          identityPoolId:@"YourIdentityPoolId"];
          AWSServiceConfiguration *configuration = [[AWSServiceConfiguration alloc] initWithRegion:AWSRegionUSEast1
                                                                               credentialsProvider:credentialsProvider];
          [AWSServiceManager defaultServiceManager].defaultServiceConfiguration = configuration;

          return YES;
      }

 Then call the following to get the default service client:

 *Swift*

     let serviceClient = PETLambdaMicroserviceClient.defaultClient()

 *Objective-C*

     PETLambdaMicroserviceClient *serviceClient = [PETLambdaMicroserviceClient defaultClient];

 @return The default service client.
 */
+ (instancetype)defaultClient;

/**
 Creates a service client with the given service configuration and registers it for the key.

 If you want to enable AWS Signature, set the default service configuration in `- application:didFinishLaunchingWithOptions:`

 *Swift*

     func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
         let credentialProvider = AWSCognitoCredentialsProvider(regionType: .USEast1, identityPoolId: "YourIdentityPoolId")
         let configuration = AWSServiceConfiguration(region: .USWest2, credentialsProvider: credentialProvider)
         PETLambdaMicroserviceClient.registerClientWithConfiguration(configuration, forKey: "USWest2PETLambdaMicroserviceClient")

         return true
     }

 *Objective-C*

     - (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
         AWSCognitoCredentialsProvider *credentialsProvider = [[AWSCognitoCredentialsProvider alloc] initWithRegionType:AWSRegionUSEast1
                                                                                                         identityPoolId:@"YourIdentityPoolId"];
         AWSServiceConfiguration *configuration = [[AWSServiceConfiguration alloc] initWithRegion:AWSRegionUSWest2
                                                                              credentialsProvider:credentialsProvider];

         [PETLambdaMicroserviceClient registerClientWithConfiguration:configuration forKey:@"USWest2PETLambdaMicroserviceClient"];

         return YES;
     }

 Then call the following to get the service client:

 *Swift*

     let serviceClient = PETLambdaMicroserviceClient(forKey: "USWest2PETLambdaMicroserviceClient")

 *Objective-C*

     PETLambdaMicroserviceClient *serviceClient = [PETLambdaMicroserviceClient clientForKey:@"USWest2PETLambdaMicroserviceClient"];

 @warning After calling this method, do not modify the configuration object. It may cause unspecified behaviors.

 @param configuration A service configuration object.
 @param key           A string to identify the service client.
 */
+ (void)registerClientWithConfiguration:(AWSServiceConfiguration *)configuration forKey:(NSString *)key;

/**
 Retrieves the service client associated with the key. You need to call `+ registerClientWithConfiguration:forKey:` before invoking this method. If `+ registerClientWithConfiguration:forKey:` has not been called in advance or the key does not exist, this method returns `nil`.

 For example, set the default service configuration in `- application:didFinishLaunchingWithOptions:`

 *Swift*

     func application(application: UIApplication, didFinishLaunchingWithOptions launchOptions: [NSObject: AnyObject]?) -> Bool {
         let credentialProvider = AWSCognitoCredentialsProvider(regionType: .USEast1, identityPoolId: "YourIdentityPoolId")
         let configuration = AWSServiceConfiguration(region: .USWest2, credentialsProvider: credentialProvider)
         PETLambdaMicroserviceClient.registerClientWithConfiguration(configuration, forKey: "USWest2PETLambdaMicroserviceClient")

         return true
     }

 *Objective-C*

     - (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
         AWSCognitoCredentialsProvider *credentialsProvider = [[AWSCognitoCredentialsProvider alloc] initWithRegionType:AWSRegionUSEast1
                                                                                                         identityPoolId:@"YourIdentityPoolId"];
         AWSServiceConfiguration *configuration = [[AWSServiceConfiguration alloc] initWithRegion:AWSRegionUSWest2
                                                                              credentialsProvider:credentialsProvider];

         [PETLambdaMicroserviceClient registerClientWithConfiguration:configuration forKey:@"USWest2PETLambdaMicroserviceClient"];

         return YES;
     }

 Then call the following to get the service client:

 *Swift*

     let serviceClient = PETLambdaMicroserviceClient(forKey: "USWest2PETLambdaMicroserviceClient")

 *Objective-C*

     PETLambdaMicroserviceClient *serviceClient = [PETLambdaMicroserviceClient clientForKey:@"USWest2PETLambdaMicroserviceClient"];

 @param key A string to identify the service client.

 @return An instance of the service client.
 */
+ (instancetype)clientForKey:(NSString *)key;

/**
 Removes the service client associated with the key and release it.
 
 @warning Before calling this method, make sure no method is running on this client.
 
 @param key A string to identify the service client.
 */
+ (void)removeClientForKey:(NSString *)key;

/**
 
 
 @param body 
 
 return type: PETLoginUserResponse *
 */
- (AWSTask *)loginPost:(PETRegisterUserRequest *)body;

/**
 
 
 
 return type: PETListPetsResponse *
 */
- (AWSTask *)petsGet;

/**
 
 
 @param body 
 
 return type: PETCreatePetResponse *
 */
- (AWSTask *)petsPost:(PETCreatePetRequest *)body;

/**
 
 
 @param petId 
 
 return type: PETGetPetResponse *
 */
- (AWSTask *)petsPetIdGet:(NSString *)petId;

/**
 
 
 @param body 
 
 return type: PETRegisterUserResponse *
 */
- (AWSTask *)usersPost:(PETRegisterUserRequest *)body;

@end
