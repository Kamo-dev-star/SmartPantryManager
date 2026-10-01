package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PantryAdapter extends RecyclerView.Adapter<PantryAdapter.PantryViewHolder> {

    private final List<PantryItem> pantryItems;
    private final OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(PantryItem item);
    }

    public PantryAdapter(List<PantryItem> pantryItems, OnItemClickListener listener) {
        this.pantryItems = pantryItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PantryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pantry, parent, false);

        return new PantryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PantryViewHolder holder, int position) {

        PantryItem item = pantryItems.get(position);

        String name = item.getName().toLowerCase();

        if (name.contains("tomato")) {
            holder.iconText.setText("🍅");
        } else if (name.contains("onion")) {
            holder.iconText.setText("🧅");
        } else if (name.contains("garlic")) {
            holder.iconText.setText("🧄");
        } else if (name.contains("milk")) {
            holder.iconText.setText("🥛");
        } else if (name.contains("bread")) {
            holder.iconText.setText("🍞");
        } else if (name.contains("chicken")) {
            holder.iconText.setText("🍗");
        } else if (name.contains("egg")) {
            holder.iconText.setText("🥚");
        } else if (name.contains("cheese")) {
            holder.iconText.setText("🧀");
        } else if (name.contains("pasta")) {
            holder.iconText.setText("🍝");
        } else if (name.contains("rice")) {
            holder.iconText.setText("🍚");
        } else if (name.contains("apple")) {
            holder.iconText.setText("🍎");
        } else if (name.contains("banana")) {
            holder.iconText.setText("🍌");
        } else {
            holder.iconText.setText("🥫");
        }

        holder.nameText.setText(item.getName());

        holder.quantityText.setText(
                item.getQuantity() + " " + item.getUnit()
        );

        if (item.getExpiryDate() != null &&
                !item.getExpiryDate().trim().isEmpty()) {

            holder.expiryText.setText(
                    "Expires: " + item.getExpiryDate()
            );

            holder.expiryText.setVisibility(View.VISIBLE);

        } else {
            holder.expiryText.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(
                v -> listener.onItemClick(item)
        );
    }

    @Override
    public int getItemCount() {
        return pantryItems.size();
    }

    public static class PantryViewHolder extends RecyclerView.ViewHolder {

        TextView iconText;
        TextView nameText;
        TextView quantityText;
        TextView expiryText;

        public PantryViewHolder(@NonNull View itemView) {
            super(itemView);

            iconText = itemView.findViewById(R.id.txtItemIcon);
            nameText = itemView.findViewById(R.id.txtItemName);
            quantityText = itemView.findViewById(R.id.txtItemQuantity);
            expiryText = itemView.findViewById(R.id.txtItemExpiry);
        }
    }
}