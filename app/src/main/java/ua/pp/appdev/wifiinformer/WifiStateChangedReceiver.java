package ua.pp.appdev.wifiinformer;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;

/**
 * Created by:
 * Ilya Antipenko <ilya@antipenko.pp.ua>
 */
public class WifiStateChangedReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
        Notifier.notify(context, info);
    }
}
