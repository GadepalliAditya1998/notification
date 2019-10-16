import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:notification/notification.dart';
import 'package:notification/model.dart';

NotificationPlugin notificationPlugin;

void OnNotificationClick(NotificationPayload payload) {
  print("Opened: " + payload.title);

  if (payload.selectedAction.actionId == 1)
    print("Like tapped");
  else if (payload.selectedAction.actionId == 2)
    print("Share tapped");
  else if(payload.selectedAction.actionId == 3)
    print("Ignore Action Tapped");
  else
    print("No Action Tapped");
}

void main() {
  notificationPlugin = NotificationPlugin();

  notificationPlugin.initialize();
  notificationPlugin.setOnNotificationClickHandler(OnNotificationClick);
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    try {
      platformVersion = await notificationPlugin.platformVersion;
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: RaisedButton(
            onPressed: () {
              notificationPlugin.showNotification();
            },
            child: Text("Show Notification"),
          ),
        ),
      ),
    );
  }
}
