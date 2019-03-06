//
//  ModelManager.m
//  Lab4
//
//  Created by Анна Родионова on 14/12/2018.
//  Copyright © 2018 Veirisa. All rights reserved.
//

#import "ModelManager.h"

@implementation ModelManager

+(void)setModel:(NSArray*)model {
    [[NSUserDefaults standardUserDefaults] setObject:model forKey:LIST_MODEL_KEY];
}

+(NSMutableArray*)getModel {
    NSArray* lists = [[NSUserDefaults standardUserDefaults] objectForKey:LIST_MODEL_KEY];
    if (lists == nil) {
        return [[NSMutableArray alloc] init];
    }
    NSMutableArray* mutLists = [[NSMutableArray alloc] init];
    for (NSDictionary* list in lists) {
        NSString* listName = [list objectForKey:LIST_NAME_KEY];
        NSMutableArray* taskNames = [[NSMutableArray alloc] initWithArray:[list objectForKey:TASK_NAMES_KEY]];
        NSMutableArray* taskDones = [[NSMutableArray alloc] initWithArray:[list objectForKey:TASK_DONES_KEY]];
        NSMutableDictionary* mutList = [[NSMutableDictionary alloc]
                                        initWithObjects: [[NSArray alloc] initWithObjects:listName, taskNames, taskDones, nil]
                                        forKeys: [[NSArray alloc] initWithObjects:LIST_NAME_KEY, TASK_NAMES_KEY, TASK_DONES_KEY, nil]];
        [mutLists addObject:mutList];
    }
    return mutLists;
}

@end
