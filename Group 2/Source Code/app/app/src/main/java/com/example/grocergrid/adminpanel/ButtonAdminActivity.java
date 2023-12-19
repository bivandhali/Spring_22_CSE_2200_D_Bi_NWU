package com.example.grocergrid.adminpanel;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.grocergrid.LoginActivity;

import com.example.grocergrid.R;

public class ButtonAdminActivity extends AppCompatActivity {
    private Button addImgesButton;
    private Button logoutButton;
    private Button orderButton;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_button_admin);
        progressBar = findViewById(R.id.progress_bar);
        addImgesButton=findViewById(R.id.addImagesButton);
        orderButton=findViewById(R.id.orderButton);
        logoutButton=findViewById(R.id.logoutButton);


        addImgesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonAdminActivity.this, AdminPanelActivity.class);
                startActivity(intent);
            }
        });

        orderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ButtonAdminActivity.this, OrderAdminActivity.class);
                startActivity(intent);
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Show the progress bar
                progressBar.setVisibility(View.VISIBLE);

                // Simulate a delay before switching to the LoginActivity

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // Hide the progress bar
                                progressBar.setVisibility(View.GONE);

                                Intent intent = new Intent(ButtonAdminActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }
                        },
                        2000 // Delay in milliseconds (2 seconds in this case)
                );
            }
        });
    }
}



