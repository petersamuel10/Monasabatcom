package com.vavisa.monasabatcom.fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Login;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.CompanyAdapter;
import com.vavisa.monasabatcom.models.Company;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;

public class FavouriteFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.loginLN)
    LinearLayout loginLn;
    @BindView(R.id.fav_recyclerView)
    RecyclerView fav_recyclerView;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @BindView(R.id.first)
    ImageView first;
    @BindView(R.id.second)
    ImageView second;
    @BindView(R.id.third)
    ImageView third;

    @OnClick(R.id.loginText)
    public void login() {
        getActivity().startActivity(new Intent(getContext(), Login.class));
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    CompanyAdapter adapter = new CompanyAdapter();
    ProgressDialog progressDialog;
    private ArrayList<Company> companyList = new ArrayList<>();
    private int userId, viewType = 1;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        userId = (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : 0;

        if (userId == 0) {
            loginLn.setVisibility(View.VISIBLE);
        } else {
            loginLn.setVisibility(GONE);
            setUpSwipeRefreshLayout();
            requestData();
            iconViewColor(first, second, third);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                viewType = 1;
                setupRecyclerView();
                iconViewColor(first, second, third);
                break;

            case R.id.second:
                viewType = 2;
                setupRecyclerView();
                iconViewColor(second, first, third);
                break;

            case R.id.third:
                viewType = 3;
                setupRecyclerView();
                iconViewColor(third, first, second);
                break;
        }
    }

    public void iconViewColor(ImageView blue, ImageView grey, ImageView grey2) {

        blue.setColorFilter(getResources().getColor(R.color.blue));
        grey.setColorFilter(getResources().getColor(R.color.bottom_nav_false));
        grey2.setColorFilter(getResources().getColor(R.color.bottom_nav_false));
    }

    private void requestData() {

        if (Common.isConnectToTheInternet(getActivity())) {
           progressDialog.show();
            compositeDisposable.add(
                    Common.getAPI()
                            .getFavouriteList(Common.currentUser.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<ArrayList<Company>>() {
                                        @Override
                                        public void accept(ArrayList<Company> favourites) throws Exception {
                                            if (favourites.size() > 0) {
                                                emptyList.setVisibility(GONE);
                                                fav_recyclerView.setVisibility(View.VISIBLE);
                                                companyList = favourites;
                                                setupRecyclerView();
                                                progressDialog.dismiss();
                                            } else {
                                               progressDialog.dismiss();
                                                fav_recyclerView.setVisibility(GONE);
                                                adapter.notifyDataSetChanged();
                                                emptyList.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                           progressDialog.dismiss();
                                            Common.errorAlert(getContext(), getString(R.string.error_occure));
                                        }
                                    }));
        } else errorConnectionMess();
    }

    private void setupRecyclerView() {

        switch (viewType) {
            case 1:
                fav_recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;

            case 2:
            case 3:
                fav_recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
        }

        adapter = new CompanyAdapter(getActivity(), companyList, Constants.TAB_FAVORITES, viewType, "-1", "-1");
        fav_recyclerView.setAdapter(adapter);
    }

    private void setUpSwipeRefreshLayout() {
        sl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {

                        setupRecyclerView();

                        sl.setRefreshing(false);
                    }
                });
    }

    public void errorConnectionMess() {

        AlertDialog.Builder error = new AlertDialog.Builder(getContext());
        error.setMessage(R.string.error_connection);
        AlertDialog dialog = error.create();
        dialog.show();
        sl.setRefreshing(false);
    }
}
