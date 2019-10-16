package keka.com.notification.services;

import android.app.Notification;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import org.json.JSONObject;
import java.util.Map;

import keka.com.notification.Utilities.NotificationUtilities;
import keka.com.notification.Utilities.Utilities;
import keka.com.notification.models.NotificationModel;


public class FirebaseCloudMessagingService extends FirebaseMessagingService {
    public FirebaseCloudMessagingService() {
        super();
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {

        try {

            Map<String, String> data = remoteMessage.getData();

            String json = data.get("data");

            if (json == null) return;

            System.out.println("JSON DATA " + json);

            NotificationModel notificationModel;

            JSONObject jsonObject = new JSONObject(json);

            Gson gson = new Gson();
            notificationModel = gson.fromJson(jsonObject.toString(), NotificationModel.class);

            NotificationUtilities notificationUtilities = new NotificationUtilities(this);
            Notification notification = notificationUtilities.buildNotification(null, null, notificationModel);
            notificationUtilities.showNotification(Utilities.getRandomValue(), notification);

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onNewToken(@NonNull String s) {

        Log.d("fcm Token", s);
        super.onNewToken(s);
    }
}



