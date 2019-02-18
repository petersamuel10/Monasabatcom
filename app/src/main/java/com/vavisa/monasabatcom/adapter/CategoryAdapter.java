package com.vavisa.monasabatcom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.Category;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryViewHolder> {

    ArrayList<Category> mCategory;
    public static Context mContext;

    public CategoryAdapter() {
        mCategory = new ArrayList<>();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,null);
        mContext = parent.getContext();

        return new CategoryViewHolder(view) ;
    }



    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder categoryViewHolder, int position) {
        categoryViewHolder.bind(mCategory.get(position));

    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public void addCategory(ArrayList<Category> newCategory) {
        mCategory.addAll(newCategory);
    }
}
