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

#import "CreatePetViewController.h"
#import "PETLambdaMicroserviceClient.h"
#import "MBProgressHud.h"
#import "MasterViewController.h"
#import "UIViewController+PETViewController.h"

@interface CreatePetViewController ()

@end

@implementation CreatePetViewController

/*
 #pragma mark - Navigation

 // In a storyboard-based application, you will often want to do a little preparation before navigation
 - (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
 // Get the new view controller using [segue destinationViewController].
 // Pass the selected object to the new view controller.
 }
 */

- (IBAction)ageStepped:(id)sender {
    self.petAgeField.text = [NSString stringWithFormat:@"%.f", self.ageStepper.value];
}

- (IBAction)createPet:(id)sender {
    [self showLoader:@"Creating pet"];

    PETLambdaMicroserviceClient *client = [PETLambdaMicroserviceClient defaultClient];
    [AWSLogger defaultLogger].logLevel = AWSLogLevelVerbose;

    PETCreatePetRequest *request = [PETCreatePetRequest new];
    request.petName = self.petNameField.text;
    request.petType = self.petTypeField.text;
    request.petAge = @(self.ageStepper.value);

    [[client petsPost:request] continueWithBlock:^id(AWSTask *task) {
        [self hideLoader];

        if (task.error) {
            NSDictionary *errorBody = [task.error.userInfo objectForKey:AWSAPIGatewayErrorHTTPBodyKey];
            NSString *errorMessage = [errorBody objectForKey:@"message"];

            [self showErrorMessage:errorMessage withTitle:@"Error while retrieving pet"];
        } else if (task.exception) {
            // Handle the exception.
        } else {
            //PETCreatePetResponse *resp = task.result;
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.masterController loadPets];
                [self dismissViewControllerAnimated:YES completion:^{

                }];
            });
        }
        return nil;
    }];
}

- (IBAction)cancel:(id)sender {
    [self dismissViewControllerAnimated:YES completion:^{
        
    }];
}

@end
