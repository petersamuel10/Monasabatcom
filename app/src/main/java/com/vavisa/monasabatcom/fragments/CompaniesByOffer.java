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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.CompanyAdapter;
import com.vavisa.monasabatcom.models.Company;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompaniesByOffer extends Fragment implements View.OnClickListener {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.com_by_offer_recy)
    RecyclerView com_offer_recy;
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
    public void setBack() {
        getActivity().onBackPressed();
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CompanyAdapter adapter = new CompanyAdapter();
    ProgressDialog progressDialog;
    private int viewType = 1;
    private int offerId = -1;
    private ArrayList<Company> companyList = new ArrayList<>();

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.companies_by_offer, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        title.setText(getArguments().getString("offerName"));
        offerId = Integer.parseInt(getArguments().getString("offerId"));

        setUpSwipeRefreshLayout();

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        if (Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        requestData();
        iconViewColor(first, second, third);

        return view;
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

            pb.setVisibility(View.VISIBLE);

            compositeDisposable.add(
                    Common.getAPI()
                            .getCompaniesByOffer(offerId)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<ArrayList<Company>>() {
                                        @Override
                                        public void accept(ArrayList<Company> companies) throws Exception {

                                            if (companies.size() > 0) {
                                                emptyList.setVisibility(GONE);
                                                com_offer_recy.setVisibility(View.VISIBLE);
                                                companyList = companies;
                                                setupRecyclerView();
                                                pb.setVisibility(GONE);
                                            } else {
                                                pb.setVisibility(GONE);
                                                com_offer_recy.setVisibility(GONE);
                                                adapter.notifyDataSetChanged();
                                                emptyList.setVisibility(View.VISIBLE);
                                            }
                                        }
                                    }));
        } else errorConnectionMess();
    }

    private void setupRecyclerView() {

        switch (viewType) {
            case 1:
                com_offer_recy.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;

            case 2:
            case 3:
                com_offer_recy.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
        }

        adapter = new CompanyAdapter(getActivity(), companyList, Constants.TAB_OFFERS, viewType, "-1", "-1");
        com_offer_recy.setAdapter(adapter);
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
