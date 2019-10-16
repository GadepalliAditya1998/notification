import 'dart:core';

import 'package:jaguar_serializer/jaguar_serializer.dart';

import 'button_action.dart';

part 'model.jser.dart';

class NotificationPayload {
  String title;
  String message;
  String smallIcon;
  String largeIcon;
  String image;
  String textColor;
  List<ButtonAction> actions;
  Map<String, dynamic> customData;
  String channelName;
  String channelId;
  ButtonAction selectedAction;

  NotificationPayload(
      {this.title,
      this.message,
      this.smallIcon,
      this.largeIcon,
      this.image,
      this.textColor,
      this.actions,
      this.customData,
      this.channelName,
      this.channelId,
      this.selectedAction});
}

@GenSerializer()
class NotificationPayloadSerializer extends Serializer<NotificationPayload>
    with _$NotificationPayloadSerializer {}
