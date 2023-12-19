package com.example.grocergrid.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocergrid.R;
import com.example.grocergrid.models.OrderAdminModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class OrderAdminAdapter extends RecyclerView.Adapter<OrderAdminAdapter.ViewHolder> {

    Context context;
    List<OrderAdminModel> orderAdminModelList;

    FirebaseFirestore firestore;
    FirebaseAuth auth;

    public OrderAdminAdapter(Context context, List<OrderAdminModel> orderAdminModelList) {
        this.context = context;
        this.orderAdminModelList = orderAdminModelList;
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_order, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.userName.setText(orderAdminModelList.get(position).getName());
        holder.userPhone.setText(orderAdminModelList.get(position).getPhone());
        holder.usercurrentEmail.setText(orderAdminModelList.get(position).getEmail());



        holder.name.setText(orderAdminModelList.get(position).getProductName());
        holder.price.setText(orderAdminModelList.get(position).getProductPrice());
        holder.date.setText(orderAdminModelList.get(position).getDate());
        holder.time.setText(orderAdminModelList.get(position).getTime());
        holder.quantity.setText(orderAdminModelList.get(position).getTotalQuantity());
        holder.totalPrice.setText(String.valueOf(orderAdminModelList.get(position).getTotalPrice()));

        holder.confrimItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Order Confirmed", Toast.LENGTH_SHORT).show();

            }
        });
        holder.rejectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(context,"Order Rejected", Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public int getItemCount() {
        return orderAdminModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name,price,date,time,quantity,totalPrice,userName, userPhone,usercurrentEmail;
        Button confrimItem,rejectItem;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            userName = itemView.findViewById(R.id.customer_name);
            userPhone = itemView.findViewById(R.id.cusphone_number);
            usercurrentEmail = itemView.findViewById(R.id.cusemail_address);


            name = itemView.findViewById(R.id.adminproduct_name);
            price = itemView.findViewById(R.id.adminproduct_price);
            totalPrice = itemView.findViewById(R.id.admintotal_price);
            quantity = itemView.findViewById(R.id.admintotal_quantity);
            date = itemView.findViewById(R.id.admincurrent_date);
            time = itemView.findViewById(R.id.admincurrent_time);
            confrimItem = itemView.findViewById(R.id.confirmorder);
            rejectItem = itemView.findViewById(R.id.rejectorder);
        }
    }
}
