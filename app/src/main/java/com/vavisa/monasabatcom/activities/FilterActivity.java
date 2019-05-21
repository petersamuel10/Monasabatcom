package com.vavisa.monasabatcom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.FilterModel;
import com.vavisa.monasabatcom.models.profile.User;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class FilterActivity extends AppCompatActivity {

    ProgressBar pb;
    RecyclerView filterList;
    private ArrayList<FilterModel> filters = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);

        pb = findViewById(R.id.pb);
        filterList = findViewById(R.id.filter_list);

        if(Common.isConnectToTheInternet(getBaseContext())) {
            requestData();
        }else
        {
            AlertDialog.Builder error = new AlertDialog.Builder(this);
            error.setMessage(R.string.error_connection);
            AlertDialog dialog = error.create();
            dialog.show();

        }


        filterList.setLayoutManager(new LinearLayoutManager(this));
        filterList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    private void requestData() {

        pb.setVisibility(View.VISIBLE);
        compositeDisposable.add(Common.getAPI().getFilters()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<FilterModel>>() {
                    @Override
                    public void accept(ArrayList<FilterModel> filterModel) throws Exception {
                        pb.setVisibility(View.GONE);

                        // first one is to all
                        FilterModel firstFilter = new FilterModel(-1,"الكل","All");
                        filters.addAll(filterModel);
                        filters.add(0,firstFilter);

                        filterList.setAdapter(new FilterAdapter());
                    }
                }));
    }

    private class FilterViewHolder extends RecyclerView.ViewHolder {

        private TextView filterName;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            filterName = itemView.findViewById(R.id.filter_name);
        }

        public void bindData(FilterModel filter) {
            if(Common.isArabic)
                filterName.setText(filter.getNameAR());
            else
                filterName.setText(filter.getNameEN());
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
                            intent.putExtra("filter", String.valueOf(filters.get(adapterPosition).getId()));
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
