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

#import <objc/runtime.h>

#import "UIViewController+PETViewController.h"
#import "MBProgressHud.h"
#import "AppDelegate.h"

@implementation UIViewController (PETViewController)

@dynamic progressHud;

- (void)showErrorMessage:(NSString *)message
               withTitle:(NSString *)title {
    [self showMessage:message
            withTitle:title
               ofType:TSMessageNotificationTypeError];
}

- (void)showInfoMessage:(NSString *)message
              withTitle:(NSString *)title {
    [self showMessage:message
            withTitle:title
               ofType:TSMessageNotificationTypeMessage];
}

- (void)showMessage:(NSString *)message
          withTitle:(NSString *)title
             ofType:(TSMessageNotificationType)type {
    [TSMessage setDefaultViewController:self];
    [TSMessage showNotificationWithTitle:title
                                subtitle:message
                                    type:type];
}

- (void)showLoader:(NSString*)message {
    dispatch_async(dispatch_get_main_queue(), ^{
        MBProgressHUD *hud = [MBProgressHUD showHUDAddedTo:self.view
                                                  animated:YES];
        if (message != nil)
            hud.labelText = message;
        
        objc_setAssociatedObject(self, @selector(progressHud), hud, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
    });
}

- (void)hideLoader {
    dispatch_async(dispatch_get_main_queue(), ^{
        MBProgressHUD *hud = (MBProgressHUD *)objc_getAssociatedObject(self, @selector(progressHud));
        [hud hide:YES];
    });
}

@end
