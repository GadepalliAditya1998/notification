#import "NotificationPlugin.h"
#import <notification/notification-Swift.h>

@implementation NotificationPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftNotificationPlugin registerWithRegistrar:registrar];
}
@end
