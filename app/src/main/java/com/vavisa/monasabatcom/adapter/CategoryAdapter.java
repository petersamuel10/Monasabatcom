package com.vavisa.monasabatcom.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.CompaniesByCat;
import com.vavisa.monasabatcom.models.Category;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<Category> mCategory;
    private Activity activity;

    public CategoryAdapter(Activity activity) {
        mCategory = new ArrayList<>();
        this.activity = activity;
    }

    @NonNull
    @Override
    public CategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, null);
        return new CategoryAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryAdapter.ViewHolder categoryViewHolder, final int position) {
        categoryViewHolder.bind(mCategory.get(position));

        categoryViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("categoryId", String.valueOf(mCategory.get(position).getId()));
                bundle.putString("catName", (Common.isArabic) ? mCategory.get(position).getNameAR() : mCategory.get(position).getNameEN());
                Fragment companiesByCatFrag = new CompaniesByCat();
                companiesByCatFrag.setArguments(bundle);

                ((MainActivity) activity).pushFragments(Constants.TAB_CATEGORY, companiesByCatFrag, true);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mCategory.size();
    }

    public void addCategory(ArrayList<Category> newCategory) {
        mCategory.addAll(newCategory);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        Category mCategory;

        @BindView(R.id.category_image)
        ImageView category_image;
        @BindView(R.id.category_name)
        TextView category_name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bind(Category category) {
            mCategory = category;
            Glide.with(Common.mActivity).load(category.getPhoto()).placeholder(Common.mActivity.getResources().getDrawable(R.drawable.logo)).into(category_image);
            if (Common.isArabic)
                category_name.setText(category.getNameAR());
            else
                category_name.setText(category.getNameEN());
        }
    }
}
