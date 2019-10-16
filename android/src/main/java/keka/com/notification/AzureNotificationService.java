package keka.com.notification;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import keka.com.notification.interfaces.INotificationService;
import keka.com.notification.services.RegistrationService;

public class AzureNotificationService implements INotificationService {

    Context context;

    private String HUB_NAME;
    private String CONNECTION_STRING;

    private String ACTION = "ACTION_REGISTER_DEVICE";
    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public AzureNotificationService(Context context) {
        this.context = context;
    }

    @Override
    public void initializeNotificationService() {
        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = appInfo.metaData;
            HUB_NAME = bundle.getString("azureHubName");
            CONNECTION_STRING = bundle.getString("azureHubConnectionString");

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void registerDevice() {
        if (checkPlayServices()) {
            Intent intent = new Intent(context, RegistrationService.class);
            intent.setAction(ACTION);
            intent.putExtra("hubName", HUB_NAME);
            intent.putExtra("hubConnectionString", CONNECTION_STRING);
            context.startService(intent);
        }
    }

    @Override
    public void unRegisterDevice() {

    }

    private boolean checkPlayServices() {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                Toast.makeText(context, "Your Google PlayServices are out of Date, Please update it", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Your Device doesn't support Google PlayServices", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        return true;
    }
}
