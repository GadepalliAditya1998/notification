package keka.com.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;
import keka.com.notification.Utilities.NotificationUtilities;
import keka.com.notification.Utilities.Utilities;

/**
 * NotificationPlugin
 */
public class NotificationPlugin implements MethodCallHandler, PluginRegistry.NewIntentListener {
    /**
     * Plugin registration.
     */
    private static Context context;
    public static MethodChannel channel;
    private static Activity activity;

    public static void registerWith(Registrar registrar) {
        NotificationPlugin plugin = new NotificationPlugin(registrar);
        registrar.addNewIntentListener(plugin);
    }

    NotificationPlugin(Registrar registrar) {
        channel = new MethodChannel(registrar.messenger(), "notification");
        channel.setMethodCallHandler(this);
        context = registrar.context();
        activity = registrar.activity();

    }

    @Override
    public void onMethodCall(MethodCall call, Result result) {
        if (call.method.equals("getPlatformVersion")) {
            result.success("Android " + android.os.Build.VERSION.RELEASE);
        } else if (call.method.equals("initialize")) {
            if (activity != null) {
                Intent intent = activity.getIntent();

                if (intent != null && intent.getAction() != null && intent.getAction().equals(NotificationUtilities.DEFAULT_NOTIFICATION_ACTION)) {

                    String data = intent.getStringExtra("notificationData");

                     System.out.println("Data: " + String.valueOf(data));
                    channel.invokeMethod("notify", data);
                }
            }

            initializeNotificationService();

        } else if (call.method.equals("showNotification")) {
            showNotification();
        } else if (call.method.equals("notify1")) {
            Log.d("Notify1", "Method called");
            channel.invokeMethod("notify", null);
        } else {
            result.notImplemented();
        }
    }

    private void initializeNotificationService() {
        if (context == null) return;
        AzureNotificationService notificationService = new AzureNotificationService(context);
        notificationService.initializeNotificationService();
        notificationService.registerDevice();
    }

    private void showNotification() {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(new NotificationChannel("channelId", "channelName", NotificationManager.IMPORTANCE_HIGH));
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "channelId");
        Intent intent = new Intent(context, getMainActivity());
        intent.setAction(NotificationUtilities.DEFAULT_NOTIFICATION_ACTION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Utilities.getRandomValue(), intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = builder.setContentTitle("Title")
                .setContentText("Message")
                .setSmallIcon(android.R.drawable.star_big_on)
                .setContentIntent(pendingIntent)
                .build();

        notificationManager.notify(Utilities.getRandomValue(), notification);
    }

    private Class<?> getMainActivity() {
        String packageName = context.getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        String className = launchIntent.getComponent().getClassName();
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean onNewIntent(Intent intent) {
        Log.d("Intent Listener", intent.getAction());
        if (channel != null && intent != null) {
            if (intent.getAction() != null && intent.getAction().equals(NotificationUtilities.DEFAULT_NOTIFICATION_ACTION)) {

                String data = intent.getStringExtra("notificationData");

                System.out.println("Data: " + String.valueOf(data));
                channel.invokeMethod("notify", data);
            }
            return true;
        }
        return false;
    }
}
