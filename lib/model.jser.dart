// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'model.dart';

// **************************************************************************
// JaguarSerializerGenerator
// **************************************************************************

abstract class _$NotificationPayloadSerializer
    implements Serializer<NotificationPayload> {
  Serializer<ButtonAction> __buttonActionSerializer;
  Serializer<ButtonAction> get _buttonActionSerializer =>
      __buttonActionSerializer ??= ButtonActionSerializer();
  @override
  Map<String, dynamic> toMap(NotificationPayload model) {
    if (model == null) return null;
    Map<String, dynamic> ret = <String, dynamic>{};
    setMapValue(ret, 'title', model.title);
    setMapValue(ret, 'message', model.message);
    setMapValue(ret, 'smallIcon', model.smallIcon);
    setMapValue(ret, 'largeIcon', model.largeIcon);
    setMapValue(ret, 'image', model.image);
    setMapValue(ret, 'textColor', model.textColor);
    setMapValue(
        ret,
        'actions',
        codeIterable(model.actions,
            (val) => _buttonActionSerializer.toMap(val as ButtonAction)));
    setMapValue(ret, 'customData',
        codeMap(model.customData, (val) => passProcessor.serialize(val)));
    setMapValue(ret, 'channelName', model.channelName);
    setMapValue(ret, 'channelId', model.channelId);
    setMapValue(ret, 'selectedAction',
        _buttonActionSerializer.toMap(model.selectedAction));
    return ret;
  }

  @override
  NotificationPayload fromMap(Map map) {
    if (map == null) return null;
    final obj = NotificationPayload();
    obj.title = map['title'] as String;
    obj.message = map['message'] as String;
    obj.smallIcon = map['smallIcon'] as String;
    obj.largeIcon = map['largeIcon'] as String;
    obj.image = map['image'] as String;
    obj.textColor = map['textColor'] as String;
    obj.actions = codeIterable<ButtonAction>(map['actions'] as Iterable,
        (val) => _buttonActionSerializer.fromMap(val as Map));
    obj.customData = codeMap<dynamic>(
        map['customData'] as Map, (val) => passProcessor.deserialize(val));
    obj.channelName = map['channelName'] as String;
    obj.channelId = map['channelId'] as String;
    obj.selectedAction =
        _buttonActionSerializer.fromMap(map['selectedAction'] as Map);
    return obj;
  }
}
