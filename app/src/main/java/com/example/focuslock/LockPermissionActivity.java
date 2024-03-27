package com.example.focuslock;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LockPermissionActivity extends AppCompatActivity {
    private Button btn;
    EditText input;
    Context context; // before onCreate in MainActivity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lock);
        context = getApplicationContext();
        btn = findViewById(R.id.button1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LockPermissionActivity.this,LockActivity.class);
                startActivity(intent);
            }
        });
    }
}