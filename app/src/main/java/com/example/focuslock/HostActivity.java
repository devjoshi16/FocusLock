package com.example.focuslock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HostActivity extends AppCompatActivity {
    private Button host, join, logout;
    TextView username;
    EditText input;
    WifiManager wifiManager;
    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    IntentFilter mIntentFilter;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_host);
        host = findViewById(R.id.btnhost);
        join = findViewById(R.id.btnjoin);
        logout = findViewById(R.id.logout);
        username = findViewById(R.id.userid);
        firebaseAuth = FirebaseAuth.getInstance();
        if(firebaseAuth.getCurrentUser()!=null) {
            String str = firebaseAuth.getCurrentUser().getEmail();
            username.setText(str);
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        initialWork();
        AlertDialog.Builder builder = new AlertDialog.Builder(HostActivity.this);
        builder.setMessage("Enter faculty password:");
        builder.setTitle("Alert !");
        input = new EditText(this);
        builder.setView(input);
        builder.setCancelable(false);
        builder.setPositiveButton("Submit", (DialogInterface.OnClickListener) (dialog, which) -> {
            if (input.getText().toString().equals("1234")) {
                Intent intent=new Intent(HostActivity.this, SessionActicity.class);
                intent.putExtra("type","host");
                startActivity(intent);
                finish();
            } else {
                dialog.dismiss();
                Toast.makeText(this, "incorrect password", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Cancel", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.cancel();
        });
        host.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Create the Alert dialog
                AlertDialog alertDialog = builder.create();
                // Show the Alert Dialog box
                alertDialog.show();
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    // if build version is less than Q try the old traditional method
                    if (!wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(true);
                    } else {
                        wifiManager.setWifiEnabled(false);
                    }
                } else {
                    // if it is Android Q and above go for the newer way
                    // NOTE: You can also use this code for less than android Q also
                    Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                    startActivityForResult(panelIntent, 1);
                }
            }
        });
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HostActivity.this, SessionActicity.class);
                intent.putExtra("type","join");
                startActivity(intent);
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                    // if build version is less than Q try the old traditional method
                    if (!wifiManager.isWifiEnabled()) {
                        wifiManager.setWifiEnabled(true);
                    } else {
                        wifiManager.setWifiEnabled(false);
                    }
                } else {
                    // if it is Android Q and above go for the newer way
                    // NOTE: You can also use this code for less than android Q also
                    Intent panelIntent = new Intent(Settings.Panel.ACTION_WIFI);
                    startActivityForResult(panelIntent, 1);


                }

            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                startActivity(new Intent(HostActivity.this, RegisterActivity.class));


                FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
                    @Override
                    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                        if (firebaseAuth.getCurrentUser() == null) {
                            startActivity(new Intent(HostActivity.this, LoginActivity.class));

                        } else {
                            Toast.makeText(HostActivity.this, "failed signput", Toast.LENGTH_SHORT).show();
                        }
                    }
                };

                firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.addAuthStateListener(authStateListener);
                firebaseAuth.signOut();
            }

        });
    }

    public void initialWork() {
        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);
    }

}
