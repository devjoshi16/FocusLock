package com.example.focuslock;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.material.snackbar.Snackbar;


public class LoginActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener {
    private FirebaseAuth auth;
    private EditText loginEmail,loginPassword;
    private Button loginButton;
    private ImageButton imgbtn_eye;
    private  TextView signUpRedirectText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView btn=findViewById(R.id.textViewSignUp);
        auth=FirebaseAuth.getInstance();
        loginEmail= findViewById(R.id.inputEmail);
        loginPassword=findViewById(R.id.inputPassword);
        loginButton=findViewById(R.id.btnlogin);
        imgbtn_eye=findViewById(R.id.eyebtn);
        checkConnection();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email=loginEmail.getText().toString().trim();
                String password=loginPassword.getText().toString().trim();

                if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    if(!password.isEmpty())
                    {
                        auth.signInWithEmailAndPassword(email,password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = auth.getCurrentUser();
                                            Toast.makeText(LoginActivity.this, user.getEmail(), Toast.LENGTH_SHORT).show();
                                            startActivity(new Intent(LoginActivity.this, HostActivity.class));
                                            finish();
                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }


                                    }
                                });
//                        auth.signInWithEmailAndPassword(email, password)
//                                .addOnCompleteListener(LoginActivity.this,new OnCompleteListener<AuthResult>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<AuthResult> task) {
//                                        if (task.isSuccessful()) {
//                                            // Sign in success, update UI with the signed-in user's information
//                                            Log.d(TAG, "signInWithEmail:success");
//                                            FirebaseUser user = auth.getCurrentUser();
//                                        } else {
//                                            // If sign in fails, display a message to the user.
//                                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                                    Toast.LENGTH_SHORT).show();
//
//                                        }
//                                    }
//                                });
                    }
                    else {
                        loginButton.setError("password cannot be empty");
                    }

                } else if (email.isEmpty()) {
                    loginEmail.setError("Email cannot be empty");
                }else{
                    loginEmail.setError("please enter valid email");
                }
            }
        });
        imgbtn_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(view.getId()==R.id.eyebtn){

                        if(loginPassword.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
//                          ((ImageView(view)).setImageResource(R.drawable.hide_password);
                            //Show Password
                            loginPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        }
                        else{
//                          ((ImageView)(view)).setImageResource(R.drawable.show_password);
                            //Hide Password
                            loginPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        }
                }
            }
    });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            startActivity(new Intent(LoginActivity.this , HostActivity.class));
            finish();
        }
    }

    private void checkConnection() {

        // initialize intent filter
        IntentFilter intentFilter = new IntentFilter();

        // add action
        intentFilter.addAction("android.new.conn.CONNECTIVITY_CHANGE");

        // register receiver
        registerReceiver(new ConnectionReceiver(), intentFilter);

        // Initialize listener
        ConnectionReceiver.Listener = this;

        // Initialize connectivity manager
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // Initialize network info
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        // get connection status
        boolean isConnected = networkInfo != null && networkInfo.isConnectedOrConnecting();

        // display snack bar
        showSnackBar(isConnected);
    }

    private void showSnackBar(boolean isConnected) {

        // initialize color and message
        String message;
        int color;

        // check condition
        if (isConnected) {

            // when internet is connected
            // set message
            message = "Connected to Internet";

            // set text color
            color = Color.WHITE;

        } else {

            // when internet
            // is disconnected
            // set message
            message = "Not Connected to Internet";

            // set text color
            color = Color.RED;
        }

        // initialize snack bar
        Snackbar snackbar = Snackbar.make(findViewById(R.id.btnlogin), message, Snackbar.LENGTH_LONG);

        // initialize view
        View view = snackbar.getView();

        // Assign variable
//        TextView textView = view.findViewById(R.id.snackbar_text);

        // set text color
//        textView.setTextColor(color);

        // show snack bar
        snackbar.show();
    }

    @Override
    public void onNetworkChange(boolean isConnected) {
        // display snack bar
        showSnackBar(isConnected);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // call method
        checkConnection();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // call method
        checkConnection();
    }
}
