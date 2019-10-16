import 'dart:async';
import 'dart:convert';
import 'dart:io';

import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:notification/model.dart';

class NotificationPlugin {
  MethodChannel _channel;
  Function(NotificationPayload) onNotificationClick;

  static final NotificationPlugin _plugin = NotificationPlugin.private(
    MethodChannel('notification'),
  );

  factory NotificationPlugin() => _plugin;

  NotificationPlugin.private(this._channel) {
    _channel.setMethodCallHandler(handler);
  }

  void initialize() {
    _channel.invokeMethod("initialize");
  }

  Future<String> get platformVersion async {
    final String version = await _channel.invokeMethod('getPlatformVersion');
    return version;
  }

  void showNotification() {
    _channel.invokeMethod('showNotification');
  }

  void setOnNotificationClickHandler(
      Function(NotificationPayload) notificationClickHandler) {
    onNotificationClick = notificationClickHandler;
  }

  Future<dynamic> handler(MethodCall call) {
    print("Handled called");
    if (call.method == "notify") {
      print("Notify Method called");
      print("Args:" + call.arguments.toString());
      String data = call.arguments as String;

      if (data != null) {
        Map<String, dynamic> map = jsonDecode(data);

        print("Map :" + map.toString());

        if (onNotificationClick != null) {
          onNotificationClick(NotificationPayloadSerializer().fromMap(map));
        }
      }
    }
    return null;
  }
}
