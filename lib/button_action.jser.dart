// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'button_action.dart';

// **************************************************************************
// JaguarSerializerGenerator
// **************************************************************************

abstract class _$ButtonActionSerializer implements Serializer<ButtonAction> {
  @override
  Map<String, dynamic> toMap(ButtonAction model) {
    if (model == null) return null;
    Map<String, dynamic> ret = <String, dynamic>{};
    setMapValue(ret, 'actionId', model.actionId);
    setMapValue(ret, 'actionName', model.actionName);
    return ret;
  }

  @override
  ButtonAction fromMap(Map map) {
    if (map == null) return null;
    final obj = ButtonAction();
    obj.actionId = map['actionId'] as int;
    obj.actionName = map['actionName'] as String;
    return obj;
  }
}
