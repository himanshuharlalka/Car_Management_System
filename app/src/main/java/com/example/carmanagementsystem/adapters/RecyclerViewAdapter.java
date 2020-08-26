package com.example.carmanagementsystem.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carmanagementsystem.R;
import com.example.carmanagementsystem.model.Item;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private Context context;
    private List<Item> itemList;

    public RecyclerViewAdapter(Context context, List<Item> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.history_cardview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Item item=itemList.get(position);
        holder.date.setText(item.getDate());
        holder.string2.setText(item.getString2());
        holder.string3.setText(item.getString3());
        holder.string4.setText(item.getString4());
        holder.string5.setText(item.getString5());

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView date;
        public TextView string2;
        public TextView string3;
        public TextView string4;
        public TextView string5;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.text1);
            string2=itemView.findViewById(R.id.text2);
            string3=itemView.findViewById(R.id.text3);
            string4=itemView.findViewById(R.id.text4);
            string5=itemView.findViewById(R.id.text5);

        }

        @Override
        public void onClick(View view) {

        }
    }
}
