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

#import "MasterViewController.h"
#import "DetailViewController.h"
#import "APIGSessionCredentialsProvider.h"
#import "CreatePetViewController.h"
#import "UIViewController+PETViewController.h"
#import "PETLambdaMicroserviceClient.h"

@interface MasterViewController ()

@property NSMutableArray *objects;
@end

@implementation MasterViewController

- (void)awakeFromNib {
    [super awakeFromNib];
    if ([[UIDevice currentDevice] userInterfaceIdiom] == UIUserInterfaceIdiomPad) {
        self.clearsSelectionOnViewWillAppear = NO;
        self.preferredContentSize = CGSizeMake(320.0, 600.0);
    }
}

- (void)viewDidAppear:(BOOL)animated {
    [super viewDidAppear:animated];

    // If the application delegate couldn't find any cached credentials, and therefore didn't set any default
    // configuration (see setDefaultConfiguration is AppDelegate), we first show the login window.
    if (AWSServiceManager.defaultServiceManager.defaultServiceConfiguration == NULL) {
        [self showLoginWindow];
    } else { // otherwise we start loading the list of pets
        [self loadPets];
    }
}

- (void)viewDidLoad {
    [super viewDidLoad];

    // set the preferred display mode to always show both views. This should happen since we are forcing
    // the application to run in landscape mode
    self.splitViewController.preferredDisplayMode = UISplitViewControllerDisplayModeAllVisible;

    UIBarButtonItem *addButton = [[UIBarButtonItem alloc] initWithBarButtonSystemItem:UIBarButtonSystemItemAdd target:self action:@selector(insertNewObject:)];
    self.navigationItem.rightBarButtonItem = addButton;
    self.detailViewController = (DetailViewController *)[[self.splitViewController.viewControllers lastObject] topViewController];

}

- (void)insertNewObject:(id)sender {
    NSLog(@"insert");
    [self performSegueWithIdentifier:@"createPet" sender:self];
}

#pragma mark - Segues

- (void)prepareForSegue:(UIStoryboardSegue *)segue sender:(id)sender {
    if ([[segue identifier] isEqualToString:@"showDetail"]) {
        DetailViewController *controller = (DetailViewController *)[[segue destinationViewController] topViewController];
        PETLambdaMicroserviceClient *client = [PETLambdaMicroserviceClient defaultClient];
        [controller showLoader:@"Loading pet"];
        NSIndexPath *indexPath = [self.tableView indexPathForSelectedRow];
        PETListPetsResponse_pets_item *curPet = self.objects[indexPath.row];
        [[client petsPetIdGet:curPet.petId] continueWithBlock:^id(AWSTask *task) {
            [controller hideLoader];
            if (task.error) {
                NSDictionary *errorBody = [task.error.userInfo objectForKey:AWSAPIGatewayErrorHTTPBodyKey];
                NSString *errorMessage = [errorBody objectForKey:@"message"];

                [self showErrorMessage:errorMessage withTitle:@"Error while retrieving pet"];
            } else {
                PETGetPetResponse *resp = task.result;
                dispatch_async(dispatch_get_main_queue(), ^{
                    [controller setDetailItem:resp];
                });
            }
            return nil;
        }];

        controller.navigationItem.leftBarButtonItem = self.splitViewController.displayModeButtonItem;
        controller.navigationItem.leftItemsSupplementBackButton = YES;
    } else if ([[segue identifier] isEqualToString:@"createPet"]) {
        CreatePetViewController *controller = (CreatePetViewController *)[segue destinationViewController];
        controller.masterController = self;
    }
}

#pragma mark - Custom methods
- (void)showLoginWindow {
    [self performSegueWithIdentifier:@"login-segue" sender:self];
}

- (void)loadPets {
    [self showLoader:@"Loading pet list"];

    PETLambdaMicroserviceClient *client = [PETLambdaMicroserviceClient defaultClient];
    [[client petsGet] continueWithBlock:^id(AWSTask *task) {
        if (task.error) {
            NSDictionary *errorBody = [task.error.userInfo objectForKey:AWSAPIGatewayErrorHTTPBodyKey];
            NSString *errorMessage = [errorBody objectForKey:@"message"];

            [self showErrorMessage:errorMessage withTitle:@"Error while retrieving the pet list"];
        } else {
            PETListPetsResponse *pets = task.result;
            self.objects = [NSMutableArray arrayWithArray:pets.pets];
            [self hideLoader];
            dispatch_async(dispatch_get_main_queue(), ^{
                [self.tableView reloadData];
            });
        }

        return nil;
    }];
}

#pragma mark - Table View

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return 1;
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return self.objects.count;
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"Cell" forIndexPath:indexPath];
    PETListPetsResponse_pets_item *object = self.objects[indexPath.row];
    cell.textLabel.text = object.petName;
    return cell;
}

- (BOOL)tableView:(UITableView *)tableView canEditRowAtIndexPath:(NSIndexPath *)indexPath {
    // Return NO if you do not want the specified item to be editable.
    return YES;
}

- (void)tableView:(UITableView *)tableView commitEditingStyle:(UITableViewCellEditingStyle)editingStyle forRowAtIndexPath:(NSIndexPath *)indexPath {
    if (editingStyle == UITableViewCellEditingStyleDelete) {
        [self.objects removeObjectAtIndex:indexPath.row];
        [tableView deleteRowsAtIndexPaths:@[indexPath] withRowAnimation:UITableViewRowAnimationFade];
    } else if (editingStyle == UITableViewCellEditingStyleInsert) {
        // Create a new instance of the appropriate class, insert it into the array, and add a new row to the table view.
    }
}

@end
