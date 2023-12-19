package com.example.grocergrid.adminpanel;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.grocergrid.LoginActivity;
import com.example.grocergrid.R;
import com.example.grocergrid.adapters.MyCartAdapter;
import com.example.grocergrid.adapters.OrderAdminAdapter;
import com.example.grocergrid.models.MyCartModel;
import com.example.grocergrid.models.OrderAdminModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderAdminActivity extends AppCompatActivity {


    FirebaseFirestore db;
    FirebaseAuth auth;
    RecyclerView recyclerView;
    OrderAdminAdapter orderAdminAdapter;
    Button confirmbtn,rejectbtn;
    List<OrderAdminModel> orderAdminModelList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_admin);


        db = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        recyclerView = findViewById(R.id.adminRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        orderAdminModelList = new ArrayList<>();
        orderAdminAdapter = new OrderAdminAdapter(this, orderAdminModelList);
        recyclerView.setAdapter(orderAdminAdapter);










        //  user's email
        String userEmail = auth.getCurrentUser().getEmail();

        //  user's email as the document id
        db.collection("AddToCart").document(userEmail)
                .collection("CurrentUser").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (DocumentSnapshot documentSnapshot : task.getResult().getDocuments()) {
                                String documentId = documentSnapshot.getId();

                                OrderAdminModel orderAdminModel = documentSnapshot.toObject(OrderAdminModel.class);

                                orderAdminModel.setDocumentId(documentId);
                                orderAdminModelList.add(orderAdminModel);
                                orderAdminAdapter.notifyDataSetChanged();
                            }


                        }
                    }
                });







    }
}