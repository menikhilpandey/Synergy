package com.example.alihasan.synergytwo.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.alihasan.synergytwo.Assignments.BusinessActivity;
import com.example.alihasan.synergytwo.R;
import com.example.alihasan.synergytwo.api.model.Debtor;

import java.util.ArrayList;

public class DebtorAdapter extends RecyclerView.Adapter<DebtorAdapter.MyViewHolder> {

    Context context;
    ArrayList<Debtor> debtorList = new ArrayList<Debtor>();
    static String userName;

    public DebtorAdapter(Context context, ArrayList<Debtor> debtorList, String userName) {
        this.context = context;
        this.debtorList = debtorList;
        this.userName = userName;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.agent_card,viewGroup,false);
        MyViewHolder recycViewHolder = new MyViewHolder(view,context,debtorList);
        return recycViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Debtor debtor = debtorList.get(position);

        holder.caseNo.setText(debtor.getCaseNo());
        holder.applName.setText(debtor.getApplName());
        holder.altTele.setText(debtor.getAltTele());
        holder.address.setText(debtor.getAddress());
        holder.typeCase.setText(debtor.getTypeCase());
    }

    @Override
    public int getItemCount() {
        return debtorList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView caseNo, applName, altTele, address, typeCase;
        Context context;
        ArrayList<Debtor> data = new ArrayList<Debtor>();

        public MyViewHolder(View itemView, Context context, ArrayList<Debtor> data) {
            super(itemView);
            this.data = data;
            this.context = context;

            caseNo = itemView.findViewById(R.id.caseNo);
            applName = itemView.findViewById(R.id.applName);
            altTele = itemView.findViewById(R.id.altTele);
            address = itemView.findViewById(R.id.address);
            typeCase = itemView.findViewById(R.id.caseType);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(typeCase.getText().toString().equals("BUSINESS")) {
                Intent intent = new Intent(this.context, BusinessActivity.class);
                intent.putExtra("CASENO",caseNo.getText().toString());
                intent.putExtra("USERNAME",userName);
                context.startActivity(intent);
            }
        }
    }
}
