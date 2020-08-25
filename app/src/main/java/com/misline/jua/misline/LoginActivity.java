package com.misline.jua.misline;

import android.app.ProgressDialog;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {


    EditText email,password;
    Button btn_login;
    TextView get_password;

    FirebaseAuth auth;
    FirebaseAuth.AuthStateListener mAuthListener;

    ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Login");


        auth = FirebaseAuth.getInstance();

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);
        password.setTransformationMethod(new PasswordTransformationMethod());
        btn_login =(Button) findViewById(R.id.btn_login);
        get_password = (TextView) findViewById(R.id.get_password);

        get_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, GetPasswordActivity.class));
            }
        });


        mProgressDialog = new ProgressDialog(this);

        //firebase


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                //check users

                FirebaseUser user = firebaseAuth.getCurrentUser();

                if( user!=null)
                {
                    Intent moveToHome = new Intent(LoginActivity.this, MainActivity.class);
                    moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(moveToHome);
                }

            }
        };
        auth.addAuthStateListener(mAuthListener);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mProgressDialog.setTitle("Logging the user");
                mProgressDialog.setMessage("Please wait...");
                mProgressDialog.show();
                loginUser();

            }
        });

    }
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = auth.getCurrentUser();
        auth.addAuthStateListener(mAuthListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        auth.removeAuthStateListener(mAuthListener);
    }

    private void loginUser() {


        String userEmail, userPassword;

        userEmail = email.getText().toString().trim();
        userPassword = password.getText().toString().trim();


        if (!TextUtils.isEmpty(userEmail) && !TextUtils.isEmpty(userPassword))
        {

            auth.signInWithEmailAndPassword(userEmail,userPassword).addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        mProgressDialog.dismiss();
                        FirebaseUser user = auth.getCurrentUser();
                        Intent moveToHome = new Intent(LoginActivity.this, MainActivity.class);
                        moveToHome.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(moveToHome);
                    }else
                    {
                        Toast.makeText(LoginActivity.this, "Unable to logging user", Toast.LENGTH_LONG).show();
                        mProgressDialog.dismiss();
                    }
                }
            });
        }else
        {
            Toast.makeText(LoginActivity.this, "Please enter user email and password", Toast.LENGTH_LONG).show();
            mProgressDialog.dismiss();
        }

    }
}
