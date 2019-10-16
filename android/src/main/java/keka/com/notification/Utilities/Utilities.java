package keka.com.notification.Utilities;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import java.util.Calendar;

public class Utilities {

    Context context;
    SharedPreferences sharedPreferences;

    private static final String TAG = "PNS";

    Utilities(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    void saveDeviceToken(String token) {
        if (sharedPreferences != null)
            sharedPreferences.edit().putString("deviceToken", token).apply();
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    void removeDeviceToken() {
        if (sharedPreferences != null)
            sharedPreferences.edit().remove("deviceToken").apply();
    }

    public static Class<?> getMainActivity(Context context) {
        String packageName = context.getPackageName();
        Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(packageName);

        try {
            String className = launchIntent.getComponent().getClassName();
            return Class.forName(className);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getRandomValue() {
        return (int) Math.abs(Calendar.getInstance().getTimeInMillis());
    }


}
