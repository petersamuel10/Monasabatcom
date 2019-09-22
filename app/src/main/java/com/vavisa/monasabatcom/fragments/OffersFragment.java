package com.vavisa.monasabatcom.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.OfferAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;

public class OffersFragment extends Fragment {

    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.offer_recyclerView)
    RecyclerView offer_recyclerView;
    @BindView(R.id.empty_list)
    TextView emptyList;

    private OfferAdapter offerAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressDialog progressDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_offers_fragments, container, false);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        setUpSwipeRefreshLayout();

        setupRecyclerView();
        requestData();

        return view;
    }

    private void requestData() {
        if (Common.isConnectToTheInternet(getContext())) {
            progressDialog.show();
            compositeDisposable.add(Common.getAPI().getOffers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(offers -> {
                        if (offers.size() > 0) {
                            progressDialog.dismiss();
                            emptyList.setVisibility(View.GONE);
                            offerAdapter.addOffer(offers);
                            offer_recyclerView.setVisibility(View.VISIBLE);
                        } else {
                            progressDialog.dismiss();
                            offer_recyclerView.setVisibility(GONE);
                            offerAdapter.notifyDataSetChanged();
                            emptyList.setVisibility(View.VISIBLE);
                        }
                    }, throwable -> {
                        progressDialog.dismiss();
                        Common.errorAlert(getContext(), getString(R.string.error_occure));
                    }));
        } else
            errorConnectionMess();
    }

    private void setupRecyclerView() {

        offer_recyclerView.setHasFixedSize(true);
        offer_recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(offer_recyclerView.getContext(), R.anim.layout_fall_down);
        offer_recyclerView.setLayoutAnimation(controller);
        offerAdapter = new OfferAdapter(getActivity());
        offer_recyclerView.setAdapter(offerAdapter);
    }

    private void setUpSwipeRefreshLayout() {
        sl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sl.setOnRefreshListener(() -> {

            if (offerAdapter != null) {
                setupRecyclerView();
                requestData();
            }
            sl.setRefreshing(false);
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
