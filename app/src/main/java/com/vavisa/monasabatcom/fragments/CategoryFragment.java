package com.vavisa.monasabatcom.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.CategoryAdapter;
import com.vavisa.monasabatcom.models.Category;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;


public class CategoryFragment extends Fragment {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.category_recyclerView)
    RecyclerView home_recyclerView;


    private CategoryAdapter homeAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view =inflater.inflate(R.layout.fragment_category, container, false);

      ButterKnife.bind(this,view);

        setUpSwipeRefreshLayout();

        //setup recycler
        setupRecyclerView();
        //get Data
        requestData();

      return view;
    }

    private void requestData() {
        if(Common.isConnectToTheInternet(getActivity())) {
           pb.setVisibility(View.VISIBLE);
            compositeDisposable.add(Common.getAPI().getCategory()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<Category>>() {
                        @Override
                        public void accept(ArrayList<Category> categories) throws Exception {
                            homeAdapter.addCategory(categories);
                            homeAdapter.notifyDataSetChanged();
                            pb.setVisibility(View.GONE);
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            pb.setVisibility(GONE);
                            Common.errorAlert(getContext(),getString(R.string.error_occure));
                        }
                    }));
        }else
            errorConnectionMess();
    }
    private void setupRecyclerView() {

        home_recyclerView.setHasFixedSize(false);
        home_recyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(home_recyclerView.getContext(),R.anim.layout_fall_down);
        home_recyclerView.setLayoutAnimation(controller);
        homeAdapter = new CategoryAdapter(getActivity());
        home_recyclerView.setAdapter(homeAdapter);
    }

    private void setUpSwipeRefreshLayout() {
        sl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                if (homeAdapter != null) {
                    setupRecyclerView();
                    requestData();
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
