package com.vavisa.monasabatcom.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.constants.Constants;

import java.util.ArrayList;
import java.util.List;

public class FilterActivity extends AppCompatActivity {

  private List<String> filters = new ArrayList<>();

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_filter);

    RecyclerView filterList = findViewById(R.id.filter_list);
    filterList.setLayoutManager(new LinearLayoutManager(this));

    filterList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    filters.add("Men");
    filters.add("Women");
    filters.add("Children");

    filterList.setAdapter(new FilterAdapter());
  }

  private class FilterViewHolder extends RecyclerView.ViewHolder {

    private TextView filterName;

    public FilterViewHolder(@NonNull View itemView) {
      super(itemView);
      filterName = itemView.findViewById(R.id.filter_name);
    }

    public void bindData(String name) {
      filterName.setText(name);
    }
  }

  private class FilterAdapter extends RecyclerView.Adapter<FilterViewHolder> {

    @NonNull
    @Override
    public FilterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
      View v =
          LayoutInflater.from(viewGroup.getContext())
              .inflate(R.layout.filter_list_item, viewGroup, false);
      return new FilterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull FilterViewHolder filterViewHolder, int i) {
      filterViewHolder.bindData(filters.get(i));

      final int adapterPosition = filterViewHolder.getAdapterPosition();

      filterViewHolder.itemView.setOnClickListener(
          new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              Intent intent = new Intent();
              intent.putExtra("filter", filters.get(adapterPosition));
              setResult(Constants.RESULT_OK, intent);
              finish();
            }
          });
    }

    @Override
    public int getItemCount() {
      return filters.size();
    }
  }
}
