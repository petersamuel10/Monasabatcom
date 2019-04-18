package com.vavisa.monasabatcom.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.Company;

import java.util.ArrayList;

public class CompanyAdapter extends RecyclerView.Adapter<CompanyViewHolder> {

  private ArrayList<Company> companies;
  private Context context;
  private int viewType;

  public CompanyAdapter(Context context, ArrayList<Company> companies, int viewType) {
    this.context = context;
    this.companies = companies;
    this.viewType = viewType;
  }

  @NonNull
  @Override
  public CompanyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
    View view = null;
    switch (viewType) {
      case 1:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company, null);
        break;

      case 2:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company2, null);
        break;

      case 3:
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_company3, null);
        break;
    }

    return new CompanyViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull CompanyViewHolder companyViewHolder, int position) {
    companyViewHolder.bind(companies.get(position));
  }

  @Override
  public int getItemCount() {
    return companies.size();
  }

  /*public void addCompany(ArrayList<Company> newCompany)
  {
      companies.addAll(newCompany);
  }*/
}
