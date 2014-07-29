package ua.pp.appdev.wifiinfo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by:
 * Ilya Antipenko <ilya@antipenko.pp.ua>
 */
public class WifiStateChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Notifier.notify(context);
    }
}
