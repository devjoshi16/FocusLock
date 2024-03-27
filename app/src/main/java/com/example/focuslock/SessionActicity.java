package com.example.focuslock;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class SessionActicity extends AppCompatActivity {


    LinearLayout layout_header, dbms, operating_system, dsa, computer_networks, system_design;
    private FirebaseFirestore db;
    private FirebaseAuth auth;
    private DatabaseReference mRootRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        Intent intent = getIntent();
        String str = intent.getStringExtra("type");
        layout_header = findViewById(R.id.layout_header);
        dbms = findViewById(R.id.dbms);
        operating_system = findViewById(R.id.operating_system);
        dsa = findViewById(R.id.dsa);
        computer_networks = findViewById(R.id.computer_networks);
        system_design = findViewById(R.id.system_design);
        mRootRef = FirebaseDatabase.getInstance().getReference();
        auth = FirebaseAuth.getInstance();

        ScrollView scrollView = findViewById(R.id.scroll);
        ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) layout_header.getLayoutParams();
        int inith = params.height;

        scrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY <= 300) {
                    if (oldScrollY - scrollY > 0) {
                        params.height = (300 - scrollY) / 5 + inith;
                        layout_header.setLayoutParams(params);
                    } else {
                        if (params.height != inith) {
                            params.height = inith + 60 - scrollY / 5;
                            layout_header.setLayoutParams(params);
                        }
                    }
                }
            }
        });

        dbms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (str.equals("host")) {
                    intent = new Intent(SessionActicity.this, MainActivity.class);
                } else {
                    intent = new Intent(SessionActicity.this, ConnectionActivity.class);
                }
                intent.putExtra("lecture", "Dbms");
                startActivity(intent);
            }
        });
        operating_system.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (str.equals("host")) {
                    intent = new Intent(SessionActicity.this, MainActivity.class);
                } else {
                    intent = new Intent(SessionActicity.this, ConnectionActivity.class);
                }
                intent.putExtra("lecture", "Operating System");
                startActivity(intent);

            }
        });
        dsa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (str.equals("host")) {
                    intent = new Intent(SessionActicity.this, MainActivity.class);
                } else {
                    intent = new Intent(SessionActicity.this, ConnectionActivity.class);
                }
                intent.putExtra("lecture", "DSA");
                startActivity(intent);
//                addDataToFirestore("DSA", "10:00", "10:30");

            }
        });
        computer_networks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (str.equals("host")) {
                    intent = new Intent(SessionActicity.this, MainActivity.class);
                } else {
                    intent = new Intent(SessionActicity.this, ConnectionActivity.class);
                }
                intent.putExtra("lecture", "Computer Networks");
                startActivity(intent);

            }
        });
        system_design.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (str.equals("host")) {
                    intent = new Intent(SessionActicity.this, MainActivity.class);
                } else {
                    intent = new Intent(SessionActicity.this, ConnectionActivity.class);
                }
                intent.putExtra("lecture", "System Design");
                startActivity(intent);
            }
        });


    }

    private void addDataToFirestore(String lecturename, String start, String end) {

        db = FirebaseFirestore.getInstance();
        HashMap<String, Object> map = new HashMap<>();
        map.put("lecture", lecturename);
        map.put("start Time:", start);
        map.put("end Time:", end);
//        db.collection("user").document("userinfo").set(map)
//        dbCourses.document("xyz").set(map)
        db = FirebaseFirestore.getInstance();
        db.collection("lecture").document(lecturename).collection("11-01-23").document("student-name").set(map).addOnCompleteListener(new OnCompleteListener<Void>() {
            //                db.collection("user").document("userinfo").set(map, SetOptions.merge()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(SessionActicity.this, "Vales added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SessionActicity.this, "failes", Toast.LENGTH_SHORT).show();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SessionActicity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }
}