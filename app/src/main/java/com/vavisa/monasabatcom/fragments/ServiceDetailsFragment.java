package com.vavisa.monasabatcom.fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.CartActivity;
import com.vavisa.monasabatcom.adapter.services_offersAdapters.ServiceExtraAdapter;
import com.vavisa.monasabatcom.models.Services;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;
import com.vavisa.monasabatcom.models.companyDetails.Photos;
import com.vavisa.monasabatcom.models.companyDetails.WorkingHours;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.ExtrasOrder;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class ServiceDetailsFragment extends Fragment implements View.OnClickListener {

    private ProgressBar pb;
    private NestedScrollView scrollView;
    private View fragmentView, extra_border_view;
    private TextView service_extras_tag;
    private SliderLayout slider;
    private TextView cart_quantity, serviceName, servicePayStatus, servicePrice, serviceDescription, howToServe_tag,
            howToServe, serviceTime_tag, serviceTime, requirements_tag, requirements, durationOfStay_tag, durationOfStay,
            order_before_tag, order_before;
    private EditText extraNotes;
    private TextView date_ed, time_ed;
    private RecyclerView serviceExtras_rec;
    private CheckBox womenServiceCheck;
    private Button addToCart;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int serviceId, companyId, quantity_int = 0;
    private String company_name_ar, company_name_en, searchDate, searchHour;
    private Services serviceData;
    private String[] dateList, timeList;
    private int date_item_position = 0, time_item_position = 0;
    private ServiceExtraAdapter extraAdapter;

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_service_details, container, false);
            reference();

            serviceId = Integer.parseInt(getArguments().getString("serviceId"));
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

            extraNotes.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    v.setFocusable(true);
                    v.setFocusableInTouchMode(true);
                    return false;
                }
            });

        }

        return fragmentView;
    }

    private void requestData() {
        pb.setVisibility(View.VISIBLE);
        compositeDisposable.add(
                Common.getAPI()
                        .getServiceDetails(serviceId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                servics -> {
                                    pb.setVisibility(View.GONE);
                                    scrollView.setVisibility(View.VISIBLE);
                                    serviceData = servics;
                                    try {
                                        bindData();
                                    } catch (Exception e) {
                                    }

                                }, throwable -> {
                                    pb.setVisibility(View.GONE);
                                    Common.errorAlert(getContext(), getString(R.string.error_occure));
                                }));

    }

    private void bindData() {

        if (Common.isArabic) {
            serviceName.setText(serviceData.getNameAR());
            serviceDescription.setText(serviceData.getDescriptionAR());
        } else {
            serviceName.setText(serviceData.getNameEN());
            serviceDescription.setText(serviceData.getDescriptionEN());
        }
        servicePrice.setText(serviceData.getPrice() + " " + getString(R.string.kd));

        if (serviceData.getServiceAndPresentationMethodEN() != null) {
            howToServe_tag.setVisibility(View.VISIBLE);
            howToServe.setVisibility(View.VISIBLE);
            if (Common.isArabic)
                howToServe.setText(serviceData.getServiceAndPresentationMethodAR());
            else
                howToServe.setText(serviceData.getServiceAndPresentationMethodEN());
        }
        if (serviceData.getPreparationTime() != null) {
            serviceTime_tag.setVisibility(View.VISIBLE);
            serviceTime.setVisibility(View.VISIBLE);
            serviceTime.setText(serviceData.getPreparationTime());
        }

        if (serviceData.getRequirements() != null) {
            requirements_tag.setVisibility(View.VISIBLE);
            requirements.setVisibility(View.VISIBLE);
            requirements.setText(serviceData.getRequirements());
        }

        if (serviceData.getStayDuration() != null) {
            durationOfStay_tag.setVisibility(View.VISIBLE);
            durationOfStay.setVisibility(View.VISIBLE);
            durationOfStay.setText(serviceData.getStayDuration());
        }


        if (serviceData.getDeliveryTime() != null) {
            order_before_tag.setVisibility(View.VISIBLE);
            order_before.setVisibility(View.VISIBLE);
            order_before.setText(serviceData.getDeliveryTime() + getString(R.string.hour));
        }


        if (serviceData.getWomenService())
            womenServiceCheck.setVisibility(View.VISIBLE);
        else
            womenServiceCheck.setVisibility(View.GONE);

        switch (serviceData.getPaymentTypeId()) {
            case 1:
                servicePayStatus.setVisibility(View.GONE);
                break;
            case 2:
                servicePayStatus.setVisibility(View.VISIBLE);
                servicePayStatus.setText(getString(R.string.pay_a_deposit));
                break;
            case 3:
                servicePayStatus.setVisibility(View.VISIBLE);
                servicePayStatus.setText(getString(R.string.pay_full_amount_in_advance));
                break;
        }

        if (serviceData.getServiceExtras().size() > 0) {
            extra_border_view.setVisibility(View.VISIBLE);
            service_extras_tag.setVisibility(View.VISIBLE);
            extraAdapter = new ServiceExtraAdapter(serviceData.getServiceExtras());
            serviceExtras_rec.setAdapter(extraAdapter);
        } else {
            extra_border_view.setVisibility(View.GONE);
            service_extras_tag.setVisibility(View.GONE);
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
        for (Photos photo : serviceData.getPhoto()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(photo.getPhoto()).empty(R.drawable.placeholder).setScaleType(BaseSliderView.ScaleType.Fit);
            slider.addSlider(textSliderView);
        }

        // slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }

    private void getDateList() {

        if (serviceData.getWorkingDaysAndHours() == null)
            dateList = new String[0];
        else {
            dateList = new String[serviceData.getWorkingDaysAndHours().size()];
            for (int i = 0; i < serviceData.getWorkingDaysAndHours().size(); i++) {

                String date = serviceData.getWorkingDaysAndHours().get(i).getDate();
                dateList[i] = date;
            }
        }
    }

    private void getTimeList() {

        if (serviceData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours() == null)
            timeList = new String[0];
        else {
            timeList = new String[serviceData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours().size()];
            for (int i = 0; i < serviceData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours().size(); i++) {

                WorkingHours workingHours = serviceData.getWorkingDaysAndHours().get(date_item_position).getWorkingHours().get(i);
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

        builder.setSingleChoiceItems(dateList, date_item_position, (dialog, position) -> {
            date_item_position = position;
            date_ed.setText(dateList[position]);
            time_ed.setText("");
            dialog.dismiss();

        });

        builder.create().show();
    }

    public void showPopTimeMenu() {


        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.select_time);

        builder.setSingleChoiceItems(timeList, time_item_position, (dialog, position) -> {
            time_item_position = position;
            time_ed.setText(ServiceDetailsFragment.this.timeList[position]);
            dialog.dismiss();
        });

        builder.create().show();
    }

    private void addToCart() {

        ArrayList<ExtrasOrder> extrasOrderList = new ArrayList<>();
        if (serviceData.getServiceExtras().size() > 0)
            extrasOrderList.addAll(extraAdapter.getExtrasOrdersList());

        ArrayList<ServicesOrder> servicesOrderList = Common.cart.getServices();
        servicesOrderList.add(new ServicesOrder(serviceData.getNameAR(), serviceData.getNameEN(), serviceData.getPaymentTypeId(),
                serviceData.getDepositPercentage(), serviceId, date_ed.getText().toString(), time_ed.getText().toString(),
                womenServiceCheck.isChecked(), 1, extraNotes.getText().toString(),
                serviceData.getPrice(), extrasOrderList));

        Common.cart.setCompany_id(companyId);
        Common.cart.setCompany_name_ar(company_name_ar);
        Common.cart.setCompany_name_en(company_name_en);
        Common.cart.setServices(servicesOrderList);
        Common.cart.setPaymentMethodId(serviceData.getPaymentTypeId());

        getActivity().onBackPressed();

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

        Toolbar toolbar = fragmentView.findViewById(R.id.service_toolbar);
        ImageView backButton = toolbar.findViewById(R.id.back_button);
        ImageView cart = toolbar.findViewById(R.id.cart_icon);
        cart_quantity = toolbar.findViewById(R.id.cart_quantity);


        pb = fragmentView.findViewById(R.id.pb);
        scrollView = fragmentView.findViewById(R.id.scroll);
        slider = fragmentView.findViewById(R.id.service_images);
        serviceName = fragmentView.findViewById(R.id.service_name);
        servicePayStatus = fragmentView.findViewById(R.id.pay_status);
        servicePrice = fragmentView.findViewById(R.id.service_price);
        serviceDescription = fragmentView.findViewById(R.id.service_description);
        howToServe_tag = fragmentView.findViewById(R.id.how_to_serve_tag);
        howToServe = fragmentView.findViewById(R.id.how_to_serve);
        serviceTime_tag = fragmentView.findViewById(R.id.service_time_tag);
        serviceTime = fragmentView.findViewById(R.id.service_time);
        requirements_tag = fragmentView.findViewById(R.id.requirements_tag);
        requirements = fragmentView.findViewById(R.id.requirements);
        durationOfStay_tag = fragmentView.findViewById(R.id.duration_of_stay_tag);
        durationOfStay = fragmentView.findViewById(R.id.duration_of_stay);
        order_before_tag = fragmentView.findViewById(R.id.order_before_tag);
        order_before = fragmentView.findViewById(R.id.order_before);
        date_ed = fragmentView.findViewById(R.id.date);
        time_ed = fragmentView.findViewById(R.id.time);
        extra_border_view = fragmentView.findViewById(R.id.view5);
        service_extras_tag = fragmentView.findViewById(R.id.service_extras_tag);
        extraNotes = fragmentView.findViewById(R.id.extra_notes);
        serviceExtras_rec = fragmentView.findViewById(R.id.extras_list_rec);
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
