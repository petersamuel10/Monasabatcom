package com.vavisa.monasabatcom.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.FilterModel;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;

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

        if (Common.isConnectToTheInternet(getBaseContext())) {
            requestData();
        } else {
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
                        FilterModel firstFilter = new FilterModel(-1, "الكل", "All");
                        filters.addAll(filterModel);
                        filters.add(0, firstFilter);

                        filterList.setAdapter(new FilterAdapter());
                    }
                }));
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
        public void onBindViewHolder(@NonNull FilterViewHolder filterViewHolder, final int i) {
            filterViewHolder.bindData(filters.get(i));
            if (Common.filter_position == i)
                filterViewHolder.ic_true.setVisibility(View.VISIBLE);

            final int adapterPosition = filterViewHolder.getAdapterPosition();

            filterViewHolder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            Common.filter_position = i;
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

    private class FilterViewHolder extends RecyclerView.ViewHolder {

        private TextView filterName;
        private ImageView ic_true;

        public FilterViewHolder(@NonNull View itemView) {
            super(itemView);
            filterName = itemView.findViewById(R.id.filter_name);
            ic_true = itemView.findViewById(R.id.select_true);
        }

        public void bindData(FilterModel filter) {
            if (Common.isArabic)
                filterName.setText(filter.getNameAR());
            else
                filterName.setText(filter.getNameEN());
        }
    }
}
