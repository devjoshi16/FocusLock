package com.example.focuslock;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.NetworkInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

public class WiFiDirectBroadcastReceiver extends BroadcastReceiver {
    private WifiP2pManager mManager;
    private WifiP2pManager.Channel mChannel;
//    private ConnectionActivity mActivity;
    private MainActivity mActivity;

    public WiFiDirectBroadcastReceiver(WifiP2pManager mManager, WifiP2pManager.Channel mChannel, MainActivity activity) {
        this.mManager = mManager;
        this.mChannel = mChannel;
        this.mActivity = activity;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION.equals(action)) {
            int state = intent.getIntExtra(WifiP2pManager.EXTRA_WIFI_STATE, WifiP2pManager.WIFI_P2P_STATE_DISABLED);
            if (state == WifiP2pManager.WIFI_P2P_STATE_ENABLED)
                Toast.makeText(context, "Wifi is ON", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(context, "Wifi is OFF", Toast.LENGTH_SHORT).show();

        } else if (WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION.equals(action)) {
            if (mManager != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(mActivity, new String[]{
                                Manifest.permission.ACCESS_FINE_LOCATION
                        }, 10);
                        mManager.requestPeers(mChannel, (WifiP2pManager.PeerListListener) mActivity.peerListListener);
                    }
                }
            }
            } else if (WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION.equals(action)) {
                if (mManager == null)
                    return;
                NetworkInfo networkInfo = intent.getParcelableExtra(WifiP2pManager.EXTRA_NETWORK_INFO);
                if (networkInfo.isConnectedOrConnecting())
                    mManager.requestConnectionInfo(mChannel, mActivity.connectionInfoListener);
//            else
//                mActivity.connectionStatus.setText("Device Disconnected");
            } else if (WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION.equals(action)) {
                //
            }

    }
}
