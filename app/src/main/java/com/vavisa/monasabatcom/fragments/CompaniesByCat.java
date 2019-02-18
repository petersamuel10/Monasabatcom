package com.vavisa.monasabatcom.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.Company2Adapter;
import com.vavisa.monasabatcom.adapter.Company3Adapter;
import com.vavisa.monasabatcom.adapter.CompanyAdapter;
import com.vavisa.monasabatcom.models.Company;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompaniesByCat extends Fragment implements View.OnClickListener {

    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.category_rec)
    RecyclerView category_recy;
    @BindView(R.id.title)
    TextView title;
    @BindView(R.id.arrow)
    ImageView arrowAr;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @BindView(R.id.first)
    ImageView first;
    @BindView(R.id.second)
    ImageView second;
    @BindView(R.id.third)
    ImageView third;
    @OnClick(R.id.back)
    public void setBack()
    {getActivity().onBackPressed();}

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CompanyAdapter adapter;
    Company2Adapter adapter2;
    Company3Adapter adapter3;
    ProgressDialog progressDialog;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.companies_by_cat, container, false);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        //that use to move between fragments
        Common.mActivity = getActivity();

        ButterKnife.bind(this,view);
        if(Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        title.setText(Common.categoryName);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        setUpSwipeRefreshLayout();
        setupRecyclerView();
        requestData();
        iconViewColor(first,second,third);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.first:
                setupRecyclerView();
                requestData();
                iconViewColor(first,second,third);
                break;

            case R.id.second:
                setupRecyclerView2();
                requestData2();
                iconViewColor(second,first,third);
                break;

            case R.id.third:
                setupRecyclerView3();
                requestData3();
                iconViewColor(third,first,second);
                break;
        }

    }

    public void iconViewColor(ImageView blue, ImageView grey, ImageView grey2) {

        blue.setColorFilter(getResources().getColor(R.color.blue));
        grey.setColorFilter(getResources().getColor(R.color.bottom_nav_false));
        grey2.setColorFilter(getResources().getColor(R.color.bottom_nav_false));

    }

    private void requestData() {
        if(Common.isConnectToTheInternet(getActivity())) {

            progressDialog.show();

            compositeDisposable.add(Common.getAPI().getCompanies(Common.categoryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Company>>() {
                        @Override
                        public void accept(ArrayList<Company> companies) throws Exception {
                            if (companies.size() > 0) {
                                emptyList.setVisibility(View.GONE);
                                adapter.addCompany(companies);
                                adapter.notifyDataSetChanged();
                                progressDialog.dismiss();
                            } else
                                progressDialog.dismiss();
                        }
                    }));
        }else
            errorConnectionMess();
    }
    private void setupRecyclerView() {
        category_recy.setHasFixedSize(true);
        category_recy.setLayoutManager(new GridLayoutManager(getContext(),2));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(category_recy.getContext(),R.anim.layout_fall_down);
        category_recy.setLayoutAnimation(controller);
        adapter = new CompanyAdapter();
        category_recy.setAdapter(adapter);
    }

    private void requestData2() {
        if(Common.isConnectToTheInternet(getActivity())) {

            progressDialog.show();

            compositeDisposable.add(Common.getAPI().getCompanies(Common.categoryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Company>>() {
                        @Override
                        public void accept(ArrayList<Company> companies) throws Exception {
                            if (companies.size() > 0) {
                                emptyList.setVisibility(View.GONE);
                                adapter2.addCompany(companies);
                                adapter2.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }else
                                progressDialog.dismiss();
                        }
                    }));
        }else
            errorConnectionMess();
    }
    private void setupRecyclerView2() {

        category_recy.setHasFixedSize(true);
        category_recy.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(category_recy.getContext(),R.anim.layout_fall_down);
        category_recy.setLayoutAnimation(controller);
        adapter2 = new Company2Adapter();
        category_recy.setAdapter(adapter2);
    }

    private void requestData3() {
        if(Common.isConnectToTheInternet(getActivity())) {

            progressDialog.show();

            compositeDisposable.add(Common.getAPI().getCompanies(Common.categoryId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Company>>() {
                        @Override
                        public void accept(ArrayList<Company> companies) throws Exception {
                            if (companies.size() > 0) {
                                emptyList.setVisibility(View.GONE);
                                adapter3.addCompany(companies);
                                adapter3.notifyDataSetChanged();
                                progressDialog.dismiss();
                            }else
                                progressDialog.dismiss();
                        }
                    }));
        }else
            errorConnectionMess();
    }
    private void setupRecyclerView3() {

        category_recy.setHasFixedSize(true);
        category_recy.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(category_recy.getContext(),R.anim.layout_fall_down);
        category_recy.setLayoutAnimation(controller);
        adapter3 = new Company3Adapter();
        category_recy.setAdapter(adapter3);
    }

    private void setUpSwipeRefreshLayout() {
        sl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (adapter != null) {
                    setupRecyclerView();
                    requestData();
                } else if (adapter2 != null) {
                    setupRecyclerView2();
                    requestData2();
                } else if (adapter3 != null) {
                    setupRecyclerView3();
                    requestData3();
                }

                sl.setRefreshing(false);
            }
        });
    }
    public void errorConnectionMess(){

        AlertDialog.Builder error = new AlertDialog.Builder(getContext());
        error.setMessage(R.string.error_connection);
        AlertDialog dialog = error.create();
        dialog.show();
        sl.setRefreshing(false);
    }


}
