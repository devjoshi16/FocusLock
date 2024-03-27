package com.example.focuslock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class ClientActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private Button submitButton;
    private DatabaseReference mRootRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.client_page);
        Intent intent = getIntent();
        String str = intent.getStringExtra("lecture");
        submitButton = findViewById(R.id.submit_data);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore db=FirebaseFirestore.getInstance();
                HashMap<String , Object> map = new HashMap<>();
//                map.put("name" , "dev");
//                map.put("email", "dev");
//                map.put("id" , auth.getCurrentUser().getUid());
                map.put("city","navsari");
                db.collection("user").document("userinfo").set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
//                db.collection("user").document("userinfo").set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(ClientActivity.this,"Vales added",Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ClientActivity.this,"failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ClientActivity.this,e.getMessage(),Toast.LENGTH_LONG).show();

                    }
                });
            }
        });

    }
}
