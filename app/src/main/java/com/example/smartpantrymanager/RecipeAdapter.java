package com.example.smartpantrymanager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<RecipeItem> recipes;
    private final OnRecipeClickListener listener;

    public interface OnRecipeClickListener {
        void onRecipeClick(RecipeItem recipe);
    }

    public RecipeAdapter(List<RecipeItem> recipes,
                         OnRecipeClickListener listener) {
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recipe, parent, false);

        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull RecipeViewHolder holder, int position) {

        RecipeItem recipe = recipes.get(position);

        String name = recipe.getName().toLowerCase();

        if (name.contains("tomato") || name.contains("pasta")) {
            holder.txtRecipeIcon.setText("🍝");
        } else if (name.contains("chicken")) {
            holder.txtRecipeIcon.setText("🍗");
        } else if (name.contains("omelette") || name.contains("egg")) {
            holder.txtRecipeIcon.setText("🍳");
        } else if (name.contains("sandwich")) {
            holder.txtRecipeIcon.setText("🥪");
        } else if (name.contains("rice")) {
            holder.txtRecipeIcon.setText("🍚");
        } else if (name.contains("salad")) {
            holder.txtRecipeIcon.setText("🥗");
        } else if (name.contains("bread")) {
            holder.txtRecipeIcon.setText("🍞");
        } else if (name.contains("pancake") || name.contains("french toast")) {
            holder.txtRecipeIcon.setText("🥞");
        } else if (name.contains("soup")) {
            holder.txtRecipeIcon.setText("🍲");
        } else if (name.contains("burger")) {
            holder.txtRecipeIcon.setText("🍔");
        } else {
            holder.txtRecipeIcon.setText("🍽️");
        }

        holder.txtRecipeName.setText(recipe.getName());
        holder.txtRecipeInstructions.setText(recipe.getInstructions());
        holder.txtRecipeName.setText(recipe.getName());
        holder.txtRecipeInstructions.setText(recipe.getInstructions());

        holder.itemView.setOnClickListener(v ->
                listener.onRecipeClick(recipe));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateRecipes(List<RecipeItem> newRecipes) {
        recipes.clear();
        recipes.addAll(newRecipes);
        notifyDataSetChanged();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView txtRecipeIcon;
        TextView txtRecipeName;
        TextView txtRecipeInstructions;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);

            txtRecipeIcon = itemView.findViewById(R.id.txtRecipeIcon);

            txtRecipeName =
                    itemView.findViewById(R.id.txtRecipeItemName);

            txtRecipeInstructions =
                    itemView.findViewById(R.id.txtRecipeItemDescription);

        }
    }
}