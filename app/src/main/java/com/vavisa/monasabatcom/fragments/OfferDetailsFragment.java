package com.vavisa.monasabatcom.fragments;


import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.CartActivity;
import com.vavisa.monasabatcom.models.companyDetails.Offers;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;
import com.vavisa.monasabatcom.models.companyDetails.Photos;
import com.vavisa.monasabatcom.models.companyDetails.WorkingHours;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OfferDetailsFragment extends Fragment implements View.OnClickListener {

    private ProgressDialog progressDialog;
    private NestedScrollView scroll;
    private View fragmentView;
    private SliderLayout slider;
    private TextView cart_quantity, offerName, offerPayStatus, offerPrice, offerDescription, howToOffer_tag,
            howToOffer, offerTime_tag, offerTime, requirements_tag, requirements, durationOfStay_tag, durationOfStay,
            order_before_tag, order_before;
    private TextView date_ed, time_ed;
    private EditText extraNotes;
    private CheckBox womenServiceCheck;
    /*    private ListView offerExtrasList;*/
    private Button addToCart;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int offerId, companyId, quantity_int;
    private Offers offersData;
    private String company_name_ar, company_name_en, searchDate, searchHour;
    private String[] dateList, timeList;
    private int date_item_position = 0, time_item_position = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_offer_details, container, false);
            reference();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setCancelable(false);

            offerId = Integer.parseInt(getArguments().getString("offerId"));
            companyId = Integer.parseInt(getArguments().getString("companyId"));
            company_name_ar = getArguments().getString("company_name_ar");
            company_name_en = getArguments().getString("company_name_en");
            searchDate = getArguments().getString("searchDate");
            searchHour = getArguments().getString("searchHour");

            if (Common.cart.getServices().size() > 0)
                quantity_int += Common.cart.getServices().size();

            if (Common.cart.getOffers().size() > 0)
                quantity_int += Common.cart.getOffers().size();


            if (quantity_int > 0) {
                cart_quantity.setVisibility(View.VISIBLE);
                cart_quantity.setText(String.valueOf(quantity_int));
            } else
                cart_quantity.setVisibility(View.GONE);


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
        progressDialog.show();
        compositeDisposable.add(
                Common.getAPI()
                        .getOfferDetails(offerId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Offers>() {
                                    @Override
                                    public void accept(Offers offers) {
                                        progressDialog.dismiss();
                                        offersData = offers;
                                        try {
                                            bindData();
                                        } catch (Exception e) {
                                        }

                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        progressDialog.dismiss();
                                        Common.errorAlert(getContext(), getString(R.string.error_occure));
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

        if (offersData.getServiceAndPresentationMethodEN() != null) {
            howToOffer_tag.setVisibility(View.VISIBLE);
            howToOffer.setVisibility(View.VISIBLE);
            if (Common.isArabic)
                howToOffer.setText(offersData.getServiceAndPresentationMethodAR());
            else
                howToOffer.setText(offersData.getServiceAndPresentationMethodEN());
        }

        if (offersData.getPreparationTimeEN() != null) {
            offerTime_tag.setVisibility(View.VISIBLE);
            offerTime.setVisibility(View.VISIBLE);
            if (Common.isArabic)
                offerTime.setText(offersData.getPreparationTimeAR());
            else
                offerTime.setText(offersData.getPreparationTimeEN());
        }

        if (offersData.getRequirementsEN() != null) {
            requirements_tag.setVisibility(View.VISIBLE);
            requirements.setVisibility(View.VISIBLE);
            if (Common.isArabic)
                requirements.setText(offersData.getRequirementsAR());
            else
                requirements.setText(offersData.getRequirementsEN());
        }

        if (offersData.getStayDurationEN() != null) {
            durationOfStay_tag.setVisibility(View.VISIBLE);
            durationOfStay.setVisibility(View.VISIBLE);
            if (Common.isArabic)
                durationOfStay.setText(offersData.getStayDurationAR());
            else
                durationOfStay.setText(offersData.getStayDurationEN());
        }

        if (offersData.getDeliveryTime() != null) {
            order_before_tag.setVisibility(View.VISIBLE);
            order_before.setVisibility(View.VISIBLE);
            order_before.setText(offersData.getDeliveryTime() + getString(R.string.hour));
        }


        if (offersData.getWomenService())
            womenServiceCheck.setVisibility(View.VISIBLE);
        else
            womenServiceCheck.setVisibility(View.GONE);

        switch (offersData.getPaymentTypeId()) {
            case 1:
                offerPayStatus.setVisibility(View.GONE);
                break;
            case 2:
                offerPayStatus.setVisibility(View.VISIBLE);
                offerPayStatus.setText(getString(R.string.pay_a_deposit));
                break;
            case 3:
                offerPayStatus.setVisibility(View.VISIBLE);
                offerPayStatus.setText(getString(R.string.pay_full_amount_in_advance));
                break;
        }

        setupSlider();
        getDateList();

        // select date and time came from search home fragment
        if (!searchDate.equals("-1")) {
            date_ed.setText(searchDate);
            date_item_position = new ArrayList<String>(Arrays.asList(dateList)).indexOf(searchDate);
        }
        if (!searchHour.equals("-1")) {
            getTimeList();
        }

    }

    private void setupSlider() {
        for (Photos photo : offersData.getPhoto()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(photo.getPhoto()).empty(R.drawable.placeholder).setScaleType(BaseSliderView.ScaleType.Fit);
            slider.addSlider(textSliderView);
        }

        // slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }

    private void getDateList() {

        if (offersData.getWorkingDaysAndHours() == null)
            dateList = new String[0];
        else {
            dateList = new String[offersData.getWorkingDaysAndHours().size()];
            for (int i = 0; i < offersData.getWorkingDaysAndHours().size(); i++) {

                String date = offersData.getWorkingDaysAndHours().get(i).getDate();
                dateList[i] = date;
            }
        }
    }

    private void getTimeList() {

        if (offersData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours() == null)
            timeList = new String[0];
        else {
            timeList = new String[offersData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours().size()];
            for (int i = 0; i < offersData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours().size(); i++) {

                WorkingHours workingHours = offersData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours().get(i);
                String time = workingHours.getFromHour() + " - " + workingHours.getToHour();
                if (workingHours.getFromHour().equals(searchHour)) {
                    time_item_position = i;
                    time_ed.setText(time);
                }
                timeList[i] = time;
            }
        }
    }

    public void showPopDateMenu() {

        //clear previous time selection
        time_item_position = 0;

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.select_date);

        builder.setSingleChoiceItems(dateList, date_item_position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                date_item_position = position;
                date_ed.setText(dateList[position]);
                time_ed.setText("");
                dialog.dismiss();

            }
        });

        builder.create().show();
    }

    public void showPopTimeMenu() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.select_time);

        builder.setSingleChoiceItems(timeList, time_item_position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                time_item_position = position;
                time_ed.setText(OfferDetailsFragment.this.timeList[position]);
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void addToCart() {

        if (Common.cart.getOffers().size() > 0) {
            if (checkIf_offer_not_exist())
                add_cartData();
        } else
            add_cartData();
    }

    private boolean checkIf_offer_not_exist() {

        for (OfferOrder order : Common.cart.getOffers()) {
            if (order.getOfferIdId() == offersData.getId() &&
                    order.getDate().equals(date_ed.getText().toString()) &&
                    order.getTime().equals(time_ed.getText().toString())) {
                Common.errorAlert(getContext(), getString(R.string.the_offer_is_in_cart_select_different_time));
                return false;
            }
        }
        return true;
    }

    private void add_cartData() {
        ArrayList<OfferOrder> offersOrderList = Common.cart.getOffers();
        offersOrderList.add(new OfferOrder(offersData.getNameAR(), offersData.getNameEN(), offersData.getPaymentTypeId(),
                offersData.getDepositPercentage(), offerId, date_ed.getText().toString(), time_ed.getText().toString(),
                womenServiceCheck.isChecked(), 1, extraNotes.getText().toString(), offersData.getPrice()));

        Common.cart.setCompany_id(companyId);
        Common.cart.setCompany_name_ar(company_name_ar);
        Common.cart.setCompany_name_en(company_name_en);
        Common.cart.setOffers(offersOrderList);
        Common.cart.setPaymentMethodId(offersData.getPaymentTypeId());

        ((MainActivity) getActivity()).onBackPressed();
    }

    private boolean CheckDateTime() {

        if (date_ed.getText().toString().equals("")) {
            Common.errorAlert(getContext(), getString(R.string.select_date_time));
            return false;
        } else if (time_ed.getText().toString().equals("")) {
            Common.errorAlert(getContext(), getString(R.string.select_date_time));
            return false;
        }
        return true;
    }

    private void showErrorCartAlert() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_alert_cart);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);

        TextView ok = dialog.findViewById(R.id.ok);
        TextView cancel = dialog.findViewById(R.id.cancel);

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // clear cart to start new order from another company
                Common.cart = new CartModel(1, -1, -1, -1,
                        0.0f, new ArrayList<ServicesOrder>(), new ArrayList<OfferOrder>(), new ArrayList<PaymentMethod>());
                addToCart();
                dialog.dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart:
                if (CheckDateTime())
                    if (Common.cart.getCompany_id() == -1 || Common.cart.getCompany_id().equals(companyId)) {
                        addToCart();
                    } else
                        showErrorCartAlert();
                break;
            case R.id.back_button:
                getActivity().onBackPressed();
                break;

            case R.id.cart_icon:
                getContext().startActivity(new Intent(getContext(), CartActivity.class));
                break;
            case R.id.date:
                showPopDateMenu();
                break;
            case R.id.time:
                if (TextUtils.isEmpty(date_ed.getText()))
                    Toast.makeText(getContext(), getString(R.string.please_select_date_first), Toast.LENGTH_SHORT).show();
                else {
                    getTimeList();
                    showPopTimeMenu();
                }
                break;
        }
    }

    private void reference() {

        Toolbar toolbar = fragmentView.findViewById(R.id.offer_toolbar);
        ImageView backButton = toolbar.findViewById(R.id.back_button);
        ImageView cart = toolbar.findViewById(R.id.cart_icon);
        cart_quantity = toolbar.findViewById(R.id.cart_quantity);

        scroll = fragmentView.findViewById(R.id.scroll);
        slider = fragmentView.findViewById(R.id.offer_images);
        offerName = fragmentView.findViewById(R.id.offer_name);
        offerPayStatus = fragmentView.findViewById(R.id.pay_status);
        offerPrice = fragmentView.findViewById(R.id.offer_price);
        offerDescription = fragmentView.findViewById(R.id.offer_description);
        howToOffer_tag = fragmentView.findViewById(R.id.how_to_serve_tag);
        howToOffer = fragmentView.findViewById(R.id.how_to_serve);
        offerTime_tag = fragmentView.findViewById(R.id.offer_time_tag);
        offerTime = fragmentView.findViewById(R.id.offer_time);
        requirements_tag = fragmentView.findViewById(R.id.requirements_tag);
        requirements = fragmentView.findViewById(R.id.requirements);
        durationOfStay_tag = fragmentView.findViewById(R.id.duration_of_stay_tag);
        durationOfStay = fragmentView.findViewById(R.id.duration_of_stay);
        order_before_tag = fragmentView.findViewById(R.id.order_before_tag);
        order_before = fragmentView.findViewById(R.id.order_before);
        date_ed = fragmentView.findViewById(R.id.date);
        time_ed = fragmentView.findViewById(R.id.time);
        extraNotes = fragmentView.findViewById(R.id.extra_notes);
        womenServiceCheck = fragmentView.findViewById(R.id.women_service_check);
        addToCart = fragmentView.findViewById(R.id.add_to_cart);

        backButton.setOnClickListener(this);
        cart.setOnClickListener(this);
        addToCart.setOnClickListener(this);
        date_ed.setOnClickListener(this);
        time_ed.setOnClickListener(this);

        if (Common.isArabic)
            backButton.setRotation(180);
    }
}
