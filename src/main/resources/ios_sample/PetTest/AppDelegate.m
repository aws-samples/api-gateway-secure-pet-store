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

#import "AppDelegate.h"
#import "DetailViewController.h"
#import "UICKeyChainStore.h"
#import "APIGSessionCredentialsProvider.h"
#import "PETCredentials.h"

@interface AppDelegate ()<UISplitViewControllerDelegate>

@property (strong, nonatomic) UICKeyChainStore *keychain;

@end

NSString *const PETAppService = @"com.amazonaws.apigatewaydemo";
NSString *const PETKeyChainUsernameKey = @"username";
NSString *const PETKeyChainPasswordKey = @"password";

@implementation AppDelegate


- (BOOL)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions {
    // Override point for customization after application launch.
    UISplitViewController *splitViewController = (UISplitViewController *)self.window.rootViewController;
    UINavigationController *navigationController = [splitViewController.viewControllers lastObject];
    navigationController.topViewController.navigationItem.leftBarButtonItem = splitViewController.displayModeButtonItem;
    splitViewController.delegate = self;

    // initialize the keychain access then try and set the default configuration
    // by loading the cached credentials. If we can't find cached credentials the configuration
    // will be null and we can display the login screen from one of the views.
    self.keychain = [UICKeyChainStore keyChainStoreWithService:PETAppService];
    [self setDefaultConfiguration];

    return YES;
}

#pragma mark - Custom methods
- (PETCredentials*)getCachedCredentials {
    if (self.keychain == nil || self.keychain[PETKeyChainUsernameKey] == nil) {
        return nil;
    }

    PETCredentials *creds = [PETCredentials new];
    creds.username = self.keychain[PETKeyChainUsernameKey];
    creds.password = self.keychain[PETKeyChainPasswordKey];

    return creds;
}

- (void)cacheCredentials:(PETCredentials *)creds {
    if (creds == nil) {
        NSLog(@"Trying to cache empty credentials");
        return;
    }

    self.keychain[PETKeyChainUsernameKey] = creds.username;
    self.keychain[PETKeyChainPasswordKey] = creds.password;
}

- (void)setDefaultConfiguration {
    PETCredentials *creds = [self getCachedCredentials];
    if (creds == nil) {
        return;
    }

    APIGSessionCredentialsProvider *provider = [[APIGSessionCredentialsProvider alloc] initWithCredentials:creds];
    AWSServiceConfiguration *config = [[AWSServiceConfiguration alloc] initWithRegion:AWSRegionUSEast1
                                                                  credentialsProvider:provider];
    [AWSServiceManager defaultServiceManager].defaultServiceConfiguration = config;
}

#pragma mark - Split view

- (BOOL)splitViewController:(UISplitViewController *)splitViewController collapseSecondaryViewController:(UIViewController *)secondaryViewController ontoPrimaryViewController:(UIViewController *)primaryViewController {
    /*if ([secondaryViewController isKindOfClass:[UINavigationController class]] && [[(UINavigationController *)secondaryViewController topViewController] isKindOfClass:[DetailViewController class]] && ([(DetailViewController *)[(UINavigationController *)secondaryViewController topViewController] detailItem] == nil)) {
     // Return YES to indicate that we have handled the collapse by doing nothing; the secondary controller will be discarded.
     return YES;
     } else {
     return NO;
     }*/
    return NO;
}

@end
