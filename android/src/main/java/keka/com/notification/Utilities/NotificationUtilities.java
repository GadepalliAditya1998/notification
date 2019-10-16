package keka.com.notification.Utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.gson.Gson;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;

import keka.com.notification.models.ButtonAction;
import keka.com.notification.models.NotificationModel;


public class NotificationUtilities {

    public static String DEFAULT_NOTIFICATION_CHANNEL_ID = "channelId";
    public static String DEFAULT_NOTIFICATION_CHANNEL_NAME = "channelName";
    public static String DEFAULT_NOTIFICATION_ACTION = "NOTIFICATION_DEFAULT_CLICK";

    public NotificationManager notificationManager;
    private Context context;

    public NotificationUtilities(Context context) {
        this.context = context;
        notificationManager = getNotificationManager(context);
    }

    public NotificationManager getNotificationManager(Context context) {
        return (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationChannel createNotificationChannel(String channelid, String channelName, int notificationImportance) {
        NotificationChannel notificationChannel = new NotificationChannel(channelid, channelName, notificationImportance);
        return notificationChannel;
    }

    public NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, NotificationModel notificationModel) {

        Gson gson = new Gson();

        String notificationData = gson.toJson(notificationModel);

        //System.out.println("NotificationData: " + notificationData);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);

        notificationBuilder.setContentTitle(notificationModel.title);
        notificationBuilder.setContentText(notificationModel.message);
        notificationBuilder.setColor(Color.parseColor(getDefaultIfNull(notificationModel.textColor, "#000000")));
        notificationBuilder.setSmallIcon(getDrawableIdFromString(context, getDefaultIfNull(notificationModel.smallIcon, "launch")));
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setChannelId(getDefaultIfNull(notificationModel.channelId, DEFAULT_NOTIFICATION_CHANNEL_ID));

        if (notificationModel.largeIcon != null) {
            notificationBuilder.setLargeIcon(getBitmapFromUrl(notificationModel.largeIcon));
        }

        if (notificationModel.image != null) {
            notificationBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                    .bigPicture(getBitmapFromUrl(notificationModel.image)));
        }

        if (notificationModel.actions != null) {

            for (ButtonAction action : notificationModel.actions) {
                Intent intent = new Intent(context, Utilities.getMainActivity(context));
                intent.setAction(DEFAULT_NOTIFICATION_ACTION);
                notificationModel.selectedAction = new ButtonAction(action.actionId, action.actionName);
                String data = gson.toJson(notificationModel);
                //System.out.println("serialized data: " + data);
                intent.putExtra("notificationData", data);
                notificationBuilder.addAction(0, action.actionName, PendingIntent.getActivity(context, (int) Calendar.getInstance().getTimeInMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT));
            }
        }

        //System.out.println("NotificationData " + notificationData);
        notificationBuilder.setContentIntent(getPendingIntent(context, (int) Math.abs(Calendar.getInstance().getTimeInMillis()), notificationData));

        return notificationBuilder;
    }

    public Notification buildNotification(String channelId, String channelName, NotificationModel notificationModel) {
        return buildNotification(getNotificationBuilder(context, channelId, notificationModel));
    }

    public Notification buildNotification(NotificationCompat.Builder builder) {
        Notification notification = builder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
        return notification;
    }

    public int getDrawableIdFromString(Context context, String drawableName) {
        return context.getResources().getIdentifier(drawableName, "drawable", context.getPackageName());
    }

    public static <T> T getDefaultIfNull(T toBeChecked, T defaultValue) {
        return (toBeChecked == null) ? defaultValue : toBeChecked;
    }

    private Bitmap getBitmapFromUrl(String url) {
        try {
            Bitmap resultBitMap = null;
            URL imageURL = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) imageURL.openConnection();

            httpURLConnection.setRequestMethod("GET");
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == 200) {
                resultBitMap = BitmapFactory.decodeStream(httpURLConnection.getInputStream());
            }
            httpURLConnection.disconnect();
            return resultBitMap;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Intent getNotificationIntent(Context context, String action) {
        Intent notificationIntent = new Intent(context, Utilities.getMainActivity(context));
        notificationIntent.setAction(getDefaultIfNull(action, DEFAULT_NOTIFICATION_ACTION));
        return notificationIntent;
    }

    public PendingIntent getPendingIntent(Context context, int requestCode, @Nullable String intentExtra) {
        Intent intent = getNotificationIntent(context, null);
        intent.putExtra("notificationData", intentExtra);
        //System.out.println("Inside PI method " + intentExtra);
        return PendingIntent.getActivity(context, requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void showNotification(int notificationId, Notification notification) {
        if (notificationManager != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                notificationManager.createNotificationChannel(createNotificationChannel(DEFAULT_NOTIFICATION_CHANNEL_ID, DEFAULT_NOTIFICATION_CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH));
            notificationManager.notify(notificationId, notification);
        }
    }
}
