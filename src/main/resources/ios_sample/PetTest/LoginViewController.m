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

#import "LoginViewController.h"
#import "PETLambdaMicroserviceClient.h"
#import "APIGSessionCredentialsProvider.h"
#import "PETCredentials.h"
#import "UIViewController+PETViewController.h"
#import "AppDelegate.h"

@interface LoginViewController ()

@end

@implementation LoginViewController

- (IBAction)loginAction:(id)sender {
    [self showLoader:@"Logging in"];
    [[self getAppDelegate] cacheCredentials:[self getCredentials]];
    [[self getAppDelegate] setDefaultConfiguration];
    [self hideLoader];
    [self dismissViewControllerAnimated:YES completion:nil];
}

- (IBAction)registerAction:(id)sender {
    APIGSessionCredentialsProvider *provider = [[APIGSessionCredentialsProvider alloc] initWithCredentials:[self getCredentials]];
    [self showLoader:@"Registration in progress"];
    [[provider registerUser] continueWithBlock:^id(AWSTask *task) {
        [self hideLoader];

        if (task.error) {
            NSDictionary *errorBody = [task.error.userInfo objectForKey:AWSAPIGatewayErrorHTTPBodyKey];
            NSString *errorMessage = [errorBody objectForKey:@"message"];

            [self showErrorMessage:errorMessage withTitle:@"Error during registration"];
        } else {
            [[self getAppDelegate] cacheCredentials:[self getCredentials]];
            [[self getAppDelegate] setDefaultConfiguration];

            [self dismissViewControllerAnimated:YES completion:nil];
        }
        return nil;
    }];
}

/**
 * Returns an initialized PETCredentials object from the form fields
 */
- (PETCredentials *)getCredentials {
    PETCredentials *creds = [PETCredentials new];
    creds.username = self.usernameField.text;
    creds.password = self.passwordField.text;

    return creds;
}

- (AppDelegate *)getAppDelegate {
    return (AppDelegate *)[[UIApplication sharedApplication] delegate];
}

/*
 #pragma mark - Navigation

 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

@end
