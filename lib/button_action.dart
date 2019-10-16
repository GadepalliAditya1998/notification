import 'package:jaguar_serializer/jaguar_serializer.dart';
part 'button_action.jser.dart';

class ButtonAction {
  int actionId;
  String actionName;

  ButtonAction({this.actionId, this.actionName});
}

@GenSerializer()
class ButtonActionSerializer extends Serializer<ButtonAction>
    with _$ButtonActionSerializer {}
