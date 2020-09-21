package com.example.logindemo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signInEmail, signInPassword;
    private TextView signInText;
    private Button signInButton;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Sign In");
        mAuth = FirebaseAuth.getInstance();

        signInEmail = findViewById(R.id.signInEmailEdittext);
        signInPassword = findViewById(R.id.signInPasswordEdittext);
        signInText = findViewById(R.id.signtextView);
        signInButton = findViewById(R.id.btn);
        progressBar = findViewById(R.id.progressbar);

        signInText.setOnClickListener(this);
        signInButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn:
                userLogin();
                break;
            case R.id.signtextView:
                Intent intent = new Intent(getApplicationContext(),SignUp.class);
                startActivity(intent);
                break;
        }

    }

    private void userLogin() {
        String email = signInEmail.getText().toString().trim();
        String password = signInPassword.getText().toString().trim();

        //checking the validity of the email
        if(email.isEmpty())
        {
            signInEmail.setError("Enter an email address");
            signInEmail.requestFocus();
            return;
        }

        if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            signInEmail.setError("Enter a valid email address");
            signInEmail.requestFocus();
            return;
        }

        //checking the validity of the password
        if(password.isEmpty())
        {
            signInPassword.setError("Enter a password");
            signInPassword.requestFocus();
            return;
        }

        if(password.length()<8)
        {
            signInPassword.setError("Minimun length of a password should be 8");
            signInPassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);


        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful())
                        {
                            finish();
                            Intent intent = new Intent(getApplicationContext(),ProfileActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        }else{
                            Toast.makeText(getApplicationContext(),"Login Unsuccessfull", Toast.LENGTH_SHORT).show();
                        }
                    }
                });


    }
}


























