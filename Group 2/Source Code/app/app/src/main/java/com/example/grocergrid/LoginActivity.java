package com.example.grocergrid;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.grocergrid.adminpanel.ButtonAdminActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText loginEmailEditText;
    private EditText loginPasswordEditText;
    private Button loginButton;
    private TextView forgotPasswordTextView;
    private TextView signUpRedirectTextView;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    private static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_KEY_IS_LOGGED_IN = "isLoggedIn";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        auth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progress_bar);
        loginEmailEditText = findViewById(R.id.login_email);
        loginPasswordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        forgotPasswordTextView = findViewById(R.id.forgot_password);
        signUpRedirectTextView = findViewById(R.id.signUpRedirectText);

        signUpRedirectTextView.setOnClickListener(this);
        loginButton.setOnClickListener(this);
        forgotPasswordTextView.setOnClickListener(this);

        if (isUserLoggedIn()) {
            openBottomMenuu();
        }
    }

    private boolean isUserLoggedIn() {
        SharedPreferences prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        return prefs.getBoolean(PREF_KEY_IS_LOGGED_IN, false);
    }

    private void setLoggedInFlag(boolean isLoggedIn) {
        SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
        editor.putBoolean(PREF_KEY_IS_LOGGED_IN, isLoggedIn);
        editor.apply();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.login_button) {
            loginUser();
        } else if (view.getId() == R.id.signUpRedirectText) {
            redirectToSignUp();
        } else if (view.getId() == R.id.forgot_password) {
            handleForgotPassword();
        }
    }

    private void handleForgotPassword() {
        Intent intent = new Intent(this, ForgotdialogActivity.class);
        startActivity(intent);
    }

    private void redirectToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    private void loginUser() {
        String email = loginEmailEditText.getText().toString();
        String password = loginPasswordEditText.getText().toString();

        if (email.isEmpty()) {
            loginEmailEditText.setError("Enter an email address");
            loginEmailEditText.requestFocus();
            return;

        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            loginEmailEditText.setError("Enter a valid email address");
            loginEmailEditText.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            loginPasswordEditText.setError("Enter a password");
            loginPasswordEditText.requestFocus();
            return;
        }
        if (password.length() < 8) {
            loginPasswordEditText.setError("Minimum range of a password should be 8");
            loginPasswordEditText.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {
                    String adminEmail = "farahreefa.14@gmail.com";
                    if (email.equals(adminEmail)) {
                        // Email matches the admin email, navigate to ImageButtonAdminActivity
                        Intent adminIntent = new Intent(LoginActivity.this, ButtonAdminActivity.class);
                        adminIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(adminIntent);
                        finish();
                    } else {
                        // Email doesn't match admin email, navigate to MainActivity
                        Toast.makeText(LoginActivity.this, "Logging in", Toast.LENGTH_SHORT).show();
                        openBottomMenuu();
                        setLoggedInFlag(true);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "Login Unsuccessful", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    private void openBottomMenuu() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
