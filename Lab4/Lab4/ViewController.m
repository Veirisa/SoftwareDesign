//
//  ViewController.m
//  Lab4
//
//  Created by Анна Родионова on 12/12/2018.
//  Copyright © 2018 Veirisa. All rights reserved.
//

#import "ModelManager.h"
#import "ViewController.h"

@interface ViewController ()
@property (weak, nonatomic) IBOutlet UIButton *addButton;
@property (weak, nonatomic) IBOutlet UIButton *removeBotton;

@property (weak, nonatomic) IBOutlet UITableView *listTableView;

@property (strong, nonatomic) NSMutableArray* model;


@end

@implementation ViewController

- (void)viewDidLoad {
    [super viewDidLoad];
    
    /*
    NSString* listName0 = @"LIST0";
    NSMutableArray* taskNames0 = [[NSMutableArray alloc] initWithObjects:@"Task00", @"Task01", nil];
    NSMutableArray* taskDones0 = [[NSMutableArray alloc] initWithObjects:@NO, @NO, nil];
    NSMutableDictionary* list0 = [[NSMutableDictionary alloc]
                          initWithObjects: [[NSArray alloc] initWithObjects:listName0, taskNames0, taskDones0, nil]
                           forKeys: [[NSArray alloc] initWithObjects:LIST_NAME_KEY, TASK_NAMES_KEY, TASK_DONES_KEY, nil]];
    
    NSString* listName1 = @"LIST1";
    NSMutableArray* taskNames1 = [[NSMutableArray alloc] initWithObjects:@"Task10", @"Task11", nil];
    NSMutableArray* taskDones1 = [[NSMutableArray alloc] initWithObjects:@NO, @NO, nil];
    NSMutableDictionary* list1 = [[NSMutableDictionary alloc]
                           initWithObjects: [[NSArray alloc] initWithObjects:listName1, taskNames1, taskDones1, nil]
                           forKeys: [[NSArray alloc] initWithObjects:LIST_NAME_KEY, TASK_NAMES_KEY, TASK_DONES_KEY, nil]];
    
    NSMutableArray* curModel = [[NSMutableArray alloc] initWithObjects:list0, list1, nil];
    
    [ModelManager setModel:curModel];
    */
    
    self.model = [ModelManager getModel];
}


-(IBAction)changeLists:(UIButton*)sender {
    
    UIAlertController* alert;
    if (sender == self.addButton) {
        alert = [UIAlertController alertControllerWithTitle:@"List name:" message:NULL preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction* addAction = [UIAlertAction actionWithTitle:@"Add" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            NSString* newListName = alert.textFields.firstObject.text;
            if ([newListName isEqualToString:@""]) {
                return;
            }
            for (NSDictionary* list in self.model) {
                if ([[list objectForKey:LIST_NAME_KEY] isEqualToString:newListName]) {
                    return;
                }
            }
            NSMutableDictionary* newList = [[NSMutableDictionary alloc]
                                            initWithObjects: [[NSArray alloc] initWithObjects:newListName, [[NSMutableArray alloc] init], [[NSMutableArray alloc] init], nil]
                                            forKeys: [[NSArray alloc] initWithObjects:LIST_NAME_KEY, TASK_NAMES_KEY, TASK_DONES_KEY, nil]];
            [self.model addObject:newList];
            [ModelManager setModel:self.model];
            [self.listTableView reloadData];
        }];
        [alert addAction:addAction];
    }
    if (sender == self.removeBotton) {
        alert = [UIAlertController alertControllerWithTitle:@"List name:" message:NULL preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction* addAction = [UIAlertAction actionWithTitle:@"Remove" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            NSString* delListName = alert.textFields.firstObject.text;
            if ([delListName isEqualToString:@""]) {
                return;
            }
            for (NSDictionary* list in self.model) {
                if ([[list objectForKey:LIST_NAME_KEY] isEqualToString:delListName]) {
                    [self.model removeObjectIdenticalTo:list];
                    break;
                }
            }
            [ModelManager setModel:self.model];
            [self.listTableView reloadData];
        }];
        [alert addAction:addAction];
    }
    UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:nil];
    [alert addAction:cancelAction];
    [alert addTextFieldWithConfigurationHandler:nil];
    [self presentViewController:alert animated:YES completion:nil];
}

- (NSInteger)numberOfSectionsInTableView:(UITableView *)tableView {
    return [self.model count];
}

- (NSInteger)tableView:(UITableView *)tableView numberOfRowsInSection:(NSInteger)section {
    return [[self.model[section] objectForKey:TASK_NAMES_KEY] count] + 1;
}

- (NSString *)tableView:(UITableView *)tableView titleForHeaderInSection:(NSInteger)section {
    return [self.model[section] objectForKey:LIST_NAME_KEY];
}

- (UITableViewCell *)tableView:(UITableView *)tableView cellForRowAtIndexPath:(NSIndexPath *)indexPath {

    NSArray* taskNames = [self.model[indexPath.section] objectForKey:TASK_NAMES_KEY];
    NSArray* taskDones = [self.model[indexPath.section] objectForKey:TASK_DONES_KEY];
    if (indexPath.row == [taskNames count]) {
        UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"AddCell"];
        cell.selectionStyle = UITableViewCellSelectionStyleNone;
        return cell;
    }
    UITableViewCell *cell = [tableView dequeueReusableCellWithIdentifier:@"ListCell"];
    [cell.textLabel setText: taskNames[indexPath.row]];
    cell.selectionStyle = UITableViewCellSelectionStyleNone;
    UIImageView* doneView = [cell viewWithTag:IMAGE_VIEW_TAG];
    if ([taskDones[indexPath.row] isEqual:@NO]) {
        [doneView setImage:[UIImage imageNamed:@"NotDone.png"]];
    } else {
        [doneView setImage:[UIImage imageNamed:@"Done.png"]];
    }
    return cell;
}

- (void)tableView:(UITableView *)tableView didSelectRowAtIndexPath:(NSIndexPath *)indexPath {
    
    UITableViewCell *cell = [tableView cellForRowAtIndexPath:indexPath];
    NSMutableArray* taskNames = [self.model[indexPath.section] objectForKey:TASK_NAMES_KEY];
    NSMutableArray* taskDones = [self.model[indexPath.section] objectForKey:TASK_DONES_KEY];
    if (indexPath.row == [taskDones count]) {
        UIAlertController* alert = [UIAlertController alertControllerWithTitle:@"Task name:" message:NULL preferredStyle:UIAlertControllerStyleAlert];
        UIAlertAction* addAction = [UIAlertAction actionWithTitle:@"Add" style:UIAlertActionStyleDefault handler:^(UIAlertAction * _Nonnull action) {
            NSString* newTaskName = alert.textFields.firstObject.text;
            if ([newTaskName isEqualToString:@""]) {
                return;
            }
            for (NSString* name in taskNames) {
                if ([name isEqualToString:newTaskName]) {
                    return;
                }
            }
            [taskNames addObject:newTaskName];
            [taskDones addObject:@NO];
            [ModelManager setModel:self.model];
            [self.listTableView reloadData];
        }];
        [alert addAction:addAction];
        UIAlertAction *cancelAction = [UIAlertAction actionWithTitle:@"Cancel" style:UIAlertActionStyleCancel handler:nil];
        [alert addAction:cancelAction];
        [alert addTextFieldWithConfigurationHandler:nil];
        [self presentViewController:alert animated:YES completion:nil];
        return;
    }
    
    UIImageView* doneView = [cell viewWithTag:IMAGE_VIEW_TAG];
    if ([taskDones[indexPath.row] isEqual:@YES]) {
        [doneView setImage:[UIImage imageNamed:@"NotDone.png"]];
        [taskDones setObject:@NO atIndexedSubscript:indexPath.row];
    } else {
        [doneView  setImage:[UIImage imageNamed:@"Done.png"]];
        [taskDones setObject:@YES atIndexedSubscript:indexPath.row];
    }
    [ModelManager setModel:self.model];
    [self.listTableView reloadData];
}

@end
