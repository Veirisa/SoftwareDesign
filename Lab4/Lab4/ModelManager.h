//
//  ModelManager.h
//  Lab4
//
//  Created by Анна Родионова on 14/12/2018.
//  Copyright © 2018 Veirisa. All rights reserved.
//

#import "Constants.h"
#import <Foundation/Foundation.h>

NS_ASSUME_NONNULL_BEGIN

@interface ModelManager : NSObject

+(void)setModel:(NSArray*)model;
+(NSMutableArray*)getModel;

@end

NS_ASSUME_NONNULL_END
