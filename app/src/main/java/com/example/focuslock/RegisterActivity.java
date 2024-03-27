package com.example.focuslock;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity implements ConnectionReceiver.ReceiverListener{
    private FirebaseAuth auth;
    private EditText signUpEmail,signUpPassword,signUpConfirmPassword,username;
    private Button signUpButton;
    private  TextView alreadyHaveAccount;
    private DatabaseReference mRootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

//        TextView btn=findViewById(R.id.alreadyHaveAccount);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        auth=FirebaseAuth.getInstance();

        signUpEmail=findViewById(R.id.inputEmail);
        username=findViewById(R.id.inputUsername);
        signUpPassword=findViewById(R.id.inputPassword);
        signUpConfirmPassword=findViewById(R.id.inputConformPassword);
        signUpButton=findViewById(R.id.btnRegister);
        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernametext=username.getText().toString().trim();
                String userEmail=signUpEmail.getText().toString().trim();
                String userPassword=signUpPassword.getText().toString().trim();
                String userConfirmPassword=signUpConfirmPassword.getText().toString().trim();

                if(userEmail.isEmpty())
                {
                    signUpEmail.setError("Please provide email id");
                }
                if(userPassword.isEmpty()||userConfirmPassword.isEmpty())
                {
                    signUpPassword.setError("please Enter the password");

                }
                if(!userPassword.equals(userConfirmPassword))
                {
                    signUpConfirmPassword.setError("Password did not match!");
                }
                else {
//                    auth.createUserWithEmailAndPassword(userEmail,userConfirmPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                        @Override
//                        public void onComplete(@NonNull Task<AuthResult> task) {
//                            if(task.isSuccessful())
//                            {
//                                Toast.makeText(RegisterActivity.this,"Successfully registerd",Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
//                            }
//                            else {
//                                Toast.makeText(RegisterActivity.this,"Register Failed"+task.getException(),Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                    });

                    auth.createUserWithEmailAndPassword(userEmail,userConfirmPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {

                            HashMap<String , Object> map = new HashMap<>();
                            map.put("name" , usernametext);
                            map.put("email", userEmail);
                            map.put("id" , auth.getCurrentUser().getUid());

                            mRootRef.child("Users").child(auth.getCurrentUser().getUid()).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
//                                        pd.dismiss();
                                        Toast.makeText(RegisterActivity.this, "Update the profile " +
                                                "for better expereince", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterActivity.this , HostActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(intent);
                                        finish();
                                    }
                                }
                            });

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
//                            pd.dismiss();
                            Toast.makeText(RegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });

                }
                }

        });
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            }
        });
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
        Snackbar snackbar = Snackbar.make(findViewById(R.id.btnRegister), message, Snackbar.LENGTH_LONG);

        // initialize view
        View view = snackbar.getView();

        // Assign variable
//        TextView textView = view.findViewById(R.id.S);

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
