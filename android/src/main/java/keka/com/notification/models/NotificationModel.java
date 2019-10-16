package keka.com.notification.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.util.Map;

public class NotificationModel {
    @SerializedName("title")
    public String title;
    @SerializedName("message")
    public String message;
    @SerializedName("smallIcon")
    public String smallIcon;
    @SerializedName("largeIcon")
    public String largeIcon;
    @SerializedName("image")
    public String image;
    @SerializedName("textColor")
    public String textColor;
    @SerializedName("actions")
    public List<ButtonAction> actions;
    @SerializedName("customData")
    public Map<String, String> customData;
    @SerializedName("channelName")
    public String channelName;
    @SerializedName("channelId")
    public String channelId;
    @SerializedName("selectedAction")
    public ButtonAction selectedAction;

    public NotificationModel() {

    }

    public NotificationModel(String title, String message, String smallIcon, String largeIcon, String image, String textColor, List<ButtonAction> actions, Map<String, String> customData, String channelName, String channelId, ButtonAction selectedAction) {
        this.title = title;
        this.message = message;
        this.smallIcon = smallIcon;
        this.largeIcon = largeIcon;
        this.image = image;
        this.textColor = textColor;
        this.actions = actions;
        this.customData = customData;
        this.channelName = channelName;
        this.channelId = channelId;
        this.selectedAction = selectedAction;
    }
}
