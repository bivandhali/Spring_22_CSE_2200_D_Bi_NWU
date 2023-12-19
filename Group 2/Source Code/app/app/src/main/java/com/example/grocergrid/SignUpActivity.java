package com.example.grocergrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;


import com.example.grocergrid.models.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.FirebaseDatabase;


public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText signupEmailEditText, signupNameEditText, signupPasswordEditText, signupPhoneEditText, signupConfirmPasswordEditText;
    private Button signupButton;
    private TextView loginRedirectTextView;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseDatabase database;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        auth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();

        progressBar=findViewById(R.id.progress_bar);
        signupEmailEditText = findViewById(R.id.signup_email);
        signupPasswordEditText = findViewById(R.id.signup_password);
        signupPhoneEditText = findViewById(R.id.signup_number);
        signupNameEditText = findViewById(R.id.signup_name);
        signupConfirmPasswordEditText = findViewById(R.id.signup_confirmpassword);
        signupButton = findViewById(R.id.signup_button);
        loginRedirectTextView = findViewById(R.id.loginRedirectText);

        loginRedirectTextView.setOnClickListener(this);
        signupButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.signup_button) {
            signUp();
        } else if (view.getId() == R.id.loginRedirectText) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
    }

    private void signUp() {

        String name = signupNameEditText.getText().toString().trim();
        String email = signupEmailEditText.getText().toString().trim();
        String password = signupPasswordEditText.getText().toString().trim();
        String confirmPassword = signupConfirmPasswordEditText.getText().toString().trim();
        String phone = signupPhoneEditText.getText().toString().trim();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || phone.isEmpty()) {
            Toast.makeText(this, "Fields are empty! Please fill up the blanks.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email.isEmpty()) {
            signupEmailEditText.setError("Enter an email address");
            signupEmailEditText.requestFocus();
            return;

        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signupEmailEditText.setError("Enter a valid email address");
            signupEmailEditText.requestFocus();
        } else {
            Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);// Proceed to the next page or perform other actions
        }
        if (password.isEmpty()) {

            signupPasswordEditText.setError("Enter a password");
            signupPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 8) {
            signupPasswordEditText.setError("Minimum range of a password should be 8");
            signupPasswordEditText.requestFocus();
            return;
        }
        if (!password.equals(confirmPassword)) {
            signupConfirmPasswordEditText.setError("The password doesn't match. Write it correctly.");
            signupConfirmPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    UserModel userModel=new UserModel(name,password,email,phone);
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).setValue(userModel);

                    Toast.makeText(SignUpActivity.this, "SignUp Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    if(task.getException()instanceof FirebaseAuthUserCollisionException)
                        Toast.makeText(SignUpActivity.this, "SignUp Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
}