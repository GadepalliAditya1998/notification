import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:notification/notification.dart';

void main() {
  const MethodChannel channel = MethodChannel('notification');

  setUp(() {
    channel.setMockMethodCallHandler((MethodCall methodCall) async {
      return '42';
    });
  });

  tearDown(() {
    channel.setMockMethodCallHandler(null);
  });

  test('getPlatformVersion', () async {
    NotificationPlugin notificationPlugin = NotificationPlugin();
    expect(await notificationPlugin.platformVersion, '42');
  });
}
