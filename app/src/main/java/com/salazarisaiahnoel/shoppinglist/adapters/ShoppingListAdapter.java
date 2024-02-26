package com.salazarisaiahnoel.shoppinglist.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.salazarisaiahnoel.shoppinglist.R;

import java.util.List;

public class ShoppingListAdapter extends RecyclerView.Adapter<ShoppingListAdapter.ShoppingListHolder> {

    Context context;
    List<String> name;
    List<String> number;
    List<String> price;
    OnItemClickListener listener;
    OnItemLongClickListener longListener;

    public ShoppingListAdapter(Context context, List<String> name, List<String> number, List<String> price, OnItemClickListener listener, OnItemLongClickListener longListener){
        this.context = context;
        this.name = name;
        this.number = number;
        this.price = price;
        this.listener = listener;
        this.longListener = longListener;
    }

    @NonNull
    @Override
    public ShoppingListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_shpl, parent, false);
        return new ShoppingListHolder(view, listener, longListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListHolder holder, int position) {
        holder.t.setText(name.get(position));
        holder.t1.setText(number.get(position));
        holder.t2.setText(price.get(position));
    }

    @Override
    public int getItemCount() {
        return name.size();
    }

    public interface OnItemClickListener{
        void onItemClick(int position);
    }

    public interface OnItemLongClickListener{
        void onItemLongClick(int position);
    }

    static class ShoppingListHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView t, t1, t2;
        OnItemClickListener listener;
        OnItemLongClickListener longListener;

        public ShoppingListHolder(@NonNull View itemView, OnItemClickListener listener, OnItemLongClickListener longListener) {
            super(itemView);
            t = itemView.findViewById(R.id.shpl_item_name);
            t1 = itemView.findViewById(R.id.shpl_item_number);
            t2 = itemView.findViewById(R.id.shpl_item_price);

            t.setSelected(true);
            t1.setSelected(true);
            t2.setSelected(true);

            this.listener = listener;
            this.longListener = longListener;

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onItemClick(getAdapterPosition());
        }

        @Override
        public boolean onLongClick(View view) {
            longListener.onItemLongClick(getAdapterPosition());
            return true;
        }
    }
}
