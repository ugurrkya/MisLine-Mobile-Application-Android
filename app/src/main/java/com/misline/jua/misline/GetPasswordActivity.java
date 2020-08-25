package com.misline.jua.misline;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class GetPasswordActivity extends AppCompatActivity {


    EditText send_email;
    Button btn_get;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_password);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Get Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        send_email = (EditText) findViewById(R.id.send_email);
        btn_get = (Button)findViewById(R.id.btn_get);

        firebaseAuth = FirebaseAuth.getInstance();

        btn_get.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = send_email.getText().toString();
                if(email.equals("")){
                    Toast.makeText(GetPasswordActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }else{
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(GetPasswordActivity.this, "Please check your email", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(GetPasswordActivity.this,LoginActivity.class));
                            }else{
                                String error = task.getException().getMessage();
                                Toast.makeText(GetPasswordActivity.this,  error, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
