package com.vavisa.monasabatcom.fragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.companyDetails.Offers;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OfferDetailsFragment extends Fragment implements View.OnClickListener {

    private ProgressBar pb;
    private ScrollView scroll;
    private View fragmentView;
    private SliderLayout offerImagesLayout;
    private TextView offerName;
    private TextView offerPrice;
    private TextView offerDescription;
    private TextView howTooffer;
    private TextView offerTime;
    private TextView requirements;
    private TextView durationOfStay;
    private EditText date;
    private EditText time;
    private EditText extraNotes;
    private CheckBox womenServiceCheck;
    /*    private ListView offerExtrasList;*/
    private Button addToCart;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private ProgressDialog progressDialog;
    private int offerId;
    private Offers offersData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_offer_details, container, false);

            reference();

            if (Common.isConnectToTheInternet(getContext())) {
                requestData();
            } else {
                AlertDialog.Builder error = new AlertDialog.Builder(getContext());
                error.setMessage(R.string.error_connection);
                AlertDialog dialog = error.create();
                dialog.show();
            }

        }

        return fragmentView;
    }


    private void requestData() {
        pb.setVisibility(View.VISIBLE);
        compositeDisposable.add(
                Common.getAPI()
                        .getOfferDetails(offerId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Offers>() {
                                    @Override
                                    public void accept(Offers offers) {
                                        pb.setVisibility(View.GONE);
                                        scroll.setVisibility(View.VISIBLE);
                                        offersData = offers;
                                        bindData();

                                    }
                                }));

    }

    private void bindData() {

        if (Common.isArabic) {
            offerName.setText(offersData.getNameAR());
            offerDescription.setText(offersData.getDescriptionAR());
        } else {
            offerName.setText(offersData.getNameEN());
            offerDescription.setText(offersData.getDescriptionEN());
        }
        offerPrice.setText(offersData.getPrice() + " " + getString(R.string.kd));

        offerTime.setText(offersData.getPreparationTime());
        requirements.setText(offersData.getRequirements());
        durationOfStay.setText(offersData.getStayDuration());

        if (offersData.getWomenService())
            womenServiceCheck.setChecked(true);
        else
            womenServiceCheck.setChecked(false);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart:
                break;
            case R.id.back_button:
                getActivity().onBackPressed();
                break;
        }
    }


    private void reference() {

        Toolbar toolbar = fragmentView.findViewById(R.id.offer_toolbar);
        ImageView backButton = toolbar.findViewById(R.id.back_button);

        pb = fragmentView.findViewById(R.id.pb);
        scroll = fragmentView.findViewById(R.id.scroll);
        offerImagesLayout = fragmentView.findViewById(R.id.offer_images);
        offerName = fragmentView.findViewById(R.id.offer_name);
        offerPrice = fragmentView.findViewById(R.id.offer_price);
        offerDescription = fragmentView.findViewById(R.id.offer_description);
        howTooffer = fragmentView.findViewById(R.id.how_to_serve);
        offerTime = fragmentView.findViewById(R.id.offer_time);
        requirements = fragmentView.findViewById(R.id.requirements);
        durationOfStay = fragmentView.findViewById(R.id.duration_of_stay);
        date = fragmentView.findViewById(R.id.date);
        time = fragmentView.findViewById(R.id.time);
        extraNotes = fragmentView.findViewById(R.id.extra_notes);
        /*serviceExtrasList = fragmentView.findViewById(R.id.extras_list);*/
        womenServiceCheck = fragmentView.findViewById(R.id.women_service_check);
        addToCart = fragmentView.findViewById(R.id.add_to_cart);
        offerId = Integer.parseInt(getArguments().getString("offerId"));

        backButton.setOnClickListener(this);
        addToCart.setOnClickListener(this);

        if (Common.isArabic)
            backButton.setRotation(180);
    }
}
