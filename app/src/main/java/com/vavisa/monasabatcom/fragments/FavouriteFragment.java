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
import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


public class FavouriteFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.fav_recyclerView)
    RecyclerView fav_recyclerView;
    @BindView(R.id.notFound)
    TextView notFound;
    @BindView(R.id.first)
    ImageView first;
    @BindView(R.id.second)
    ImageView second;
    @BindView(R.id.third)
    ImageView third;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    CompanyAdapter adapter;
    Company2Adapter adapter2;
    Company3Adapter adapter3;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this,view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        if (Paper.book("Monasabatcom").contains("currentUser")) {
            setUpSwipeRefreshLayout();
            setupRecyclerView();
            requestData();
            iconViewColor(first, second, third);

        }
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
            compositeDisposable.add(Common.getAPI().getFavouriteList(Common.currentUser.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Company>>() {
                        @Override
                        public void accept(ArrayList<Company> favourites) throws Exception {
                            if (favourites.size() > 0) {
                                adapter.addCompany(favourites);
                                adapter.notifyDataSetChanged();
                                notFound.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            } else
                                progressDialog.dismiss();
                        }
                    }));
        }else
            errorConnectionMess();
    }
    private void setupRecyclerView() {

        fav_recyclerView.setHasFixedSize(true);
        fav_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(fav_recyclerView.getContext(),R.anim.layout_fall_down);
        fav_recyclerView.setLayoutAnimation(controller);
        adapter = new CompanyAdapter();
        fav_recyclerView.setAdapter(adapter);
    }

    private void requestData2() {
        if(Common.isConnectToTheInternet(getActivity())) {
            progressDialog.show();
            compositeDisposable.add(Common.getAPI().getFavouriteList(Common.currentUser.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Company>>() {
                        @Override
                        public void accept(ArrayList<Company> favourites) throws Exception {
                            if (favourites.size() > 0) {
                                adapter2.addCompany(favourites);
                                adapter2.notifyDataSetChanged();
                                notFound.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            } else
                                progressDialog.dismiss();
                        }
                    }));
        }else
            errorConnectionMess();
    }
    private void setupRecyclerView2() {

        fav_recyclerView.setHasFixedSize(true);
        fav_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(fav_recyclerView.getContext(),R.anim.layout_fall_down);
        fav_recyclerView.setLayoutAnimation(controller);
        adapter2 = new Company2Adapter();
        fav_recyclerView.setAdapter(adapter2);
    }

    private void requestData3() {
        if(Common.isConnectToTheInternet(getActivity())) {
            progressDialog.show();
            compositeDisposable.add(Common.getAPI().getFavouriteList(Common.currentUser.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Company>>() {
                        @Override
                        public void accept(ArrayList<Company> favourites) throws Exception {
                            if (favourites.size() > 0) {
                                adapter3.addCompany(favourites);
                                adapter3.notifyDataSetChanged();
                                notFound.setVisibility(View.GONE);
                                progressDialog.dismiss();
                            } else
                                progressDialog.dismiss();
                        }
                    }));
        }else
            errorConnectionMess();
    }
    private void setupRecyclerView3() {

        fav_recyclerView.setHasFixedSize(true);
        fav_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(fav_recyclerView.getContext(),R.anim.layout_fall_down);
        fav_recyclerView.setLayoutAnimation(controller);
        adapter3 = new Company3Adapter();
        fav_recyclerView.setAdapter(adapter3);
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
