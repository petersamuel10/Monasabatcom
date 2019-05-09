package com.vavisa.monasabatcom.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.CartActivity;
import com.vavisa.monasabatcom.adapter.ServiceExtraAdapter;
import com.vavisa.monasabatcom.models.Services;
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

public class ServiceDetailsFragment extends Fragment implements View.OnClickListener {

    private ProgressBar pb;
    private NestedScrollView scrollView;
    private View fragmentView;
    private SliderLayout slider;
    private TextView cart_quantity, serviceName, servicePayStatus, servicePrice,
            serviceDescription, howToServe, serviceTime, requirements, durationOfStay;
    private EditText date_ed, time_ed, extraNotes;
    private RecyclerView serviceExtras_rec;
    private CheckBox womenServiceCheck;
    private Button addToCart;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int serviceId, companyId;
    private String company_name_ar, company_name_en, searchDate, searchHour;
    private Services serviceData;
    private String[] dateList, timeList;
    private int date_item_position = 0, time_item_position = 0;
    private ServiceExtraAdapter extraAdapter;

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

            if (Common.cart.getServices().size() > 0) {
                cart_quantity.setVisibility(View.VISIBLE);
                cart_quantity.setText(String.valueOf(Common.cart.getServices().size()));
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
        pb.setVisibility(View.VISIBLE);
        compositeDisposable.add(
                Common.getAPI()
                        .getServiceDetails(serviceId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<Services>() {
                                    @Override
                                    public void accept(Services servics) {
                                        pb.setVisibility(View.GONE);
                                        scrollView.setVisibility(View.VISIBLE);
                                        serviceData = servics;
                                        try {
                                            bindData();
                                        } catch (Exception e) {
                                        }

                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        pb.setVisibility(View.GONE);
                                        Common.errorAlert(getContext(), getString(R.string.error_occure));
                                    }
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

        howToServe.setText(serviceData.getServiceAndPresentationMethod());
        serviceTime.setText(serviceData.getPreparationTime());
        requirements.setText(serviceData.getRequirements());
        durationOfStay.setText(serviceData.getStayDuration());

        if (serviceData.getWomenService())
            womenServiceCheck.setChecked(true);
        else
            womenServiceCheck.setChecked(false);

        switch (serviceData.getPaymentTypeId()) {
            case 1:
            case 2:
                servicePayStatus.setText(getString(R.string.pay_a_deposit));
                break;
            case 3:
                servicePayStatus.setText(getString(R.string.pay_full_amount_in_advance));
                break;
        }

        extraAdapter = new ServiceExtraAdapter(serviceData.getServiceExtras());
        serviceExtras_rec.setAdapter(extraAdapter);

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
                String time = workingHours.getFromHour() + "-" + workingHours.getToHour();
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
                time_ed.setText(ServiceDetailsFragment.this.timeList[position]);
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void addToCart() {

        ArrayList<ServicesOrder> servicesOrderList = Common.cart.getServices();
        servicesOrderList.add(new ServicesOrder(serviceData.getNameAR(), serviceData.getNameEN(), serviceData.getPaymentTypeId(), serviceId,
                date_ed.getText().toString(), time_ed.getText().toString(), 1, extraNotes.getText().toString(),
                serviceData.getPrice(), extraAdapter.getExtrasOrdersList()));

        Common.cart.setCompany_id(companyId);
        Common.cart.setCompany_name_ar(company_name_ar);
        Common.cart.setCompany_name_en(company_name_en);
        //  Common.cart.setUserId(Common.currentUser.getId());
        Common.cart.setServices(servicesOrderList);
        Common.cart.setPaymentMethodId(serviceData.getPaymentTypeId());

        ((MainActivity) getActivity()).onBackPressed();

    }

    private boolean CheckDateTime() {

        if (date_ed.getText().toString().equals("")) {
            Toast.makeText(getContext(), getString(R.string.select_date_time), Toast.LENGTH_SHORT).show();
            return false;
        } else if (time_ed.getText().toString().equals("")) {
            Toast.makeText(getContext(), getString(R.string.select_date_time), Toast.LENGTH_SHORT).show();
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
                        0.0f, new ArrayList<ServicesOrder>(), new ArrayList<OfferOrder>());
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
        howToServe = fragmentView.findViewById(R.id.how_to_serve);
        serviceTime = fragmentView.findViewById(R.id.service_time);
        requirements = fragmentView.findViewById(R.id.requirements);
        durationOfStay = fragmentView.findViewById(R.id.duration_of_stay);
        date_ed = fragmentView.findViewById(R.id.date);
        time_ed = fragmentView.findViewById(R.id.time);
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
