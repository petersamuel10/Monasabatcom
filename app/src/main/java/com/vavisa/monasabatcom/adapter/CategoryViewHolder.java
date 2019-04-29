package com.vavisa.monasabatcom.adapter;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.fragments.CompaniesByCat;
import com.vavisa.monasabatcom.models.Category;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryViewHolder extends RecyclerView.ViewHolder {

    Category mCategory;

    @BindView(R.id.category_image)
    ImageView category_image;
    @BindView(R.id.category_name)
    TextView category_name;
    @OnClick(R.id.category_cardView)
    public void selectCategory()
    {
        Common.categoryId = mCategory.getId();
        if (Common.isArabic)
            Common.categoryName = mCategory.getNameAR();
        else
            Common.categoryName =mCategory.getNameEN();
        FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment,new CompaniesByCat()).addToBackStack(null).commit();
    }

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

    public void bind(Category category)
    {
        mCategory = category;
        Glide.with(Common.mActivity).load(category.getPhoto()).placeholder(Common.mActivity.getResources().getDrawable(R.drawable.logo)).into(category_image);
        if (Common.isArabic)
            category_name.setText(category.getNameAR());
        else
            category_name.setText(category.getNameEN());
    }
}
