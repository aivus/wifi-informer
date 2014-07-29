package ua.pp.appdev.wifiinfo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.provider.Settings;
import android.util.Log;

import org.apache.http.conn.util.InetAddressUtils;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Collections;
import java.util.List;

/**
 * Created by:
 * Ilya Antipenko <ilya@antipenko.pp.ua>
 */
public class Notifier {

    private static final int NOTIFICATION_STATE = 1;

    public static void notify(Context context, NetworkInfo info){
        if (info == null) return;

        // Notification manager
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Check connect
        if (info.isConnected()) {
            WifiManager wifiMgr = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInfo = wifiMgr.getConnectionInfo();
            String ssid = wifiInfo.getSSID();
            String ipAddress = getLocalIpv4Address();

            // Open wifi settings intent
            PendingIntent wifiSettingsIntent = PendingIntent.getActivity(context, 0, new Intent(Settings.ACTION_WIFI_SETTINGS), 0);

            int numberOfLevels = 10;
            int level = WifiManager.calculateSignalLevel(wifiInfo.getRssi(), numberOfLevels);

            String title = "SSID: " + ssid;
            String text = "IP: " + ipAddress + " | Signal: " + level + "/" + numberOfLevels;

            Notification notification = new Notification.Builder(context)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setContentIntent(wifiSettingsIntent)
                    .setSmallIcon(R.drawable.ic_action_network_wifi)
                    .setAutoCancel(false)
                    .setOngoing(true)
                    .getNotification();

            notificationManager.notify(NOTIFICATION_STATE, notification);
        } else {
            notificationManager.cancel(NOTIFICATION_STATE);
        }
    }

    private static String getLocalIpv4Address(){
        try {
            String ipv4;
            List<NetworkInterface> nilist = Collections.list(NetworkInterface.getNetworkInterfaces());
            if(nilist.size() > 0){
                for (NetworkInterface ni: nilist){
                    List<InetAddress>  ialist = Collections.list(ni.getInetAddresses());
                    if(ialist.size()>0){
                        for (InetAddress address: ialist){
                            if (!address.isLoopbackAddress() && InetAddressUtils.isIPv4Address(ipv4 = address.getHostAddress())){
                                return ipv4;
                            }
                        }
                    }

                }
            }

        } catch (SocketException ex) {
            Log.e("", ex.toString());
        }
        return "";
    }
}
