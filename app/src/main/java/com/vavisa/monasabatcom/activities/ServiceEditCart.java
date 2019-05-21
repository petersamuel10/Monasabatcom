package com.vavisa.monasabatcom.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
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
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.services_offersAdapters.ServiceExtraAdapter;
import com.vavisa.monasabatcom.models.ServiceExtras;
import com.vavisa.monasabatcom.models.Services;
import com.vavisa.monasabatcom.models.companyDetails.Photos;
import com.vavisa.monasabatcom.models.companyDetails.WorkingHours;
import com.vavisa.monasabatcom.models.orderModels.ExtrasOrder;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ServiceEditCart extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar pb;
    private NestedScrollView scrollView;
    private View extra_border_view;
    private TextView service_extras_tag;
    private SliderLayout slider;
    private TextView serviceName, servicePayStatus, servicePrice, serviceDescription, howToServe_tag,
            howToServe, serviceTime_tag, serviceTime, requirements_tag, requirements, durationOfStay_tag, durationOfStay,
            order_before_tag, order_before;
    private EditText extraNotes;
    private TextView date_ed, time_ed;
    private RecyclerView serviceExtras_rec;
    private CheckBox womenServiceCheck;
    private Button addToCart;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int serviceId, index_cart;
    private String date, hour, notes;
    private Services serviceData;
    private String[] dateList, timeList;
    private int date_item_position = 0, time_item_position = 0;
    private ServiceExtraAdapter extraAdapter;
    private ServicesOrder service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_edit_cart);
        reference();

        index_cart = getIntent().getExtras().getInt("index");
        service = getIntent().getExtras().getParcelable("service_order");
        serviceId = service.getServiceId();
        date = service.getDate();
        hour = service.getTime();
        notes = service.getNote();

        if (Common.isConnectToTheInternet(this)) {
            requestData();
        } else {
            AlertDialog.Builder error = new AlertDialog.Builder(this);
            error.setMessage(R.string.error_connection);
            AlertDialog dialog = error.create();
            dialog.show();
        }
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
                                        Common.errorAlert(ServiceEditCart.this, getString(R.string.error_occure));
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

        if (serviceData.getServiceAndPresentationMethodEN() == null) {
            howToServe_tag.setVisibility(View.GONE);
            howToServe.setVisibility(View.GONE);
        } else {

            if (Common.isArabic)
                howToServe.setText(serviceData.getServiceAndPresentationMethodAR());
            else
                howToServe.setText(serviceData.getServiceAndPresentationMethodEN());
        }
        if (serviceData.getPreparationTime() == null) {
            serviceTime_tag.setVisibility(View.GONE);
            serviceTime.setVisibility(View.GONE);
        } else
            serviceTime.setText(serviceData.getPreparationTime());
        if (serviceData.getRequirements() == null) {
            requirements_tag.setVisibility(View.GONE);
            requirements.setVisibility(View.GONE);
        } else
            requirements.setText(serviceData.getRequirements());
        if (serviceData.getStayDuration() == null) {
            durationOfStay_tag.setVisibility(View.GONE);
            durationOfStay.setVisibility(View.GONE);
        } else
            durationOfStay.setText(serviceData.getStayDuration());

        if (serviceData.getDeliveryTime() == null) {
            order_before_tag.setVisibility(View.GONE);
            order_before.setVisibility(View.GONE);
        } else
            order_before.setText(serviceData.getDeliveryTime() + getString(R.string.hour));

        if (serviceData.getWomenService())
            womenServiceCheck.setVisibility(View.VISIBLE);
        else
            womenServiceCheck.setVisibility(View.GONE);

        switch (serviceData.getPaymentTypeId()) {
            case 1:
                servicePayStatus.setVisibility(View.GONE);
                break;
            case 2:
                servicePayStatus.setText(getString(R.string.pay_a_deposit));
                break;
            case 3:
                servicePayStatus.setText(getString(R.string.pay_full_amount_in_advance));
                break;
        }

        if (serviceData.getServiceExtras().size() > 0) {

            for (ExtrasOrder extraOrder :service.getExtrasOrder()) {
                for(ServiceExtras extraService : serviceData.getServiceExtras()){
                    if(extraOrder.getExtraId() == extraService.getId()){
                        int index = serviceData.getServiceExtras().indexOf(extraService);
                        serviceData.getServiceExtras().get(index).setQuantity(extraOrder.getQuantity());
                    }
                }
            }
            extraAdapter = new ServiceExtraAdapter(serviceData.getServiceExtras());
            serviceExtras_rec.setAdapter(extraAdapter);
        } else {
            extra_border_view.setVisibility(View.GONE);
            service_extras_tag.setVisibility(View.GONE);
        }


        setupSlider();
        getDateList();

        date_ed.setText(date);
        date_item_position = new ArrayList<String>(Arrays.asList(dateList)).indexOf(date);
        getTimeList();

        extraNotes.setText(notes);


    }

    private void setupSlider() {
        for (Photos photo : serviceData.getPhoto()) {
            TextSliderView textSliderView = new TextSliderView(this);
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
                if (time.equals(hour)) {
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

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
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


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.select_time);

        builder.setSingleChoiceItems(timeList, time_item_position, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {
                time_item_position = position;
                time_ed.setText(timeList[position]);
                dialog.dismiss();
            }
        });

        builder.create().show();
    }

    private void addToCart() {

        if (checkIf_offer_not_exist()) {

            ArrayList<ExtrasOrder> extrasOrderList = new ArrayList<>();
            if (serviceData.getServiceExtras().size() > 0)
                extrasOrderList.addAll(extraAdapter.getExtrasOrdersList());

            ArrayList<ServicesOrder> servicesOrderList = Common.cart.getServices();
            servicesOrderList.set(index_cart,new ServicesOrder(serviceData.getNameAR(), serviceData.getNameEN(), serviceData.getPaymentTypeId(),
                    serviceData.getDepositPercentage(), serviceId, date_ed.getText().toString(), time_ed.getText().toString(),
                    womenServiceCheck.isChecked(), 1, extraNotes.getText().toString(),
                    serviceData.getPrice(), extrasOrderList));

            Common.cart.setServices(servicesOrderList);

            onBackPressed();
        }

    }

    private boolean checkIf_offer_not_exist() {

        for (ServicesOrder order : Common.cart.getServices()) {
            if (order.getServiceId() == serviceData.getId() &&
                    order.getDate().equals(date_ed.getText().toString()) &&
                    order.getTime().equals(time_ed.getText().toString())) {
                if (index_cart == Common.cart.getServices().indexOf(order))
                    onBackPressed();
                else {
                    Common.errorAlert(this, getString(R.string.the_offer_is_in_cart_select_different_time));
                    return false;
                }
            }
        }
        return true;
    }

    private boolean CheckDateTime() {

        if (date_ed.getText().toString().equals("")) {
            Common.errorAlert(this, getString(R.string.select_date_time));
            return false;
        } else if (time_ed.getText().toString().equals("")) {
            Common.errorAlert(this, getString(R.string.select_date_time));
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_to_cart:
                if (CheckDateTime())
                    addToCart();
                break;
            case R.id.back_button:
                onBackPressed();
                break;
            case R.id.date:
                showPopDateMenu();
                break;
            case R.id.time:
                if (TextUtils.isEmpty(date_ed.getText()))
                    Toast.makeText(this, getString(R.string.please_select_date_first), Toast.LENGTH_SHORT).show();
                else {
                    getTimeList();
                    showPopTimeMenu();
                }
                break;
        }
    }

    private void reference() {

        Toolbar toolbar = findViewById(R.id.service_toolbar);
        ImageView backButton = toolbar.findViewById(R.id.back_button);

        pb = findViewById(R.id.pb);
        scrollView = findViewById(R.id.scroll);
        slider = findViewById(R.id.service_images);
        serviceName = findViewById(R.id.service_name);
        servicePayStatus = findViewById(R.id.pay_status);
        servicePrice = findViewById(R.id.service_price);
        serviceDescription = findViewById(R.id.service_description);
        howToServe_tag = findViewById(R.id.how_to_serve_tag);
        howToServe = findViewById(R.id.how_to_serve);
        serviceTime_tag = findViewById(R.id.service_time_tag);
        serviceTime = findViewById(R.id.service_time);
        requirements_tag = findViewById(R.id.requirements_tag);
        requirements = findViewById(R.id.requirements);
        durationOfStay_tag = findViewById(R.id.duration_of_stay_tag);
        durationOfStay = findViewById(R.id.duration_of_stay);
        order_before_tag = findViewById(R.id.order_before_tag);
        order_before = findViewById(R.id.order_before);
        date_ed = findViewById(R.id.date);
        time_ed = findViewById(R.id.time);
        extra_border_view = findViewById(R.id.view5);
        service_extras_tag = findViewById(R.id.service_extras_tag);
        extraNotes = findViewById(R.id.extra_notes);
        serviceExtras_rec = findViewById(R.id.extras_list_rec);
        womenServiceCheck = findViewById(R.id.women_service_check);
        addToCart = findViewById(R.id.add_to_cart);

        backButton.setOnClickListener(this);
        addToCart.setOnClickListener(this);
        date_ed.setOnClickListener(this);
        time_ed.setOnClickListener(this);

        if (Common.isArabic)
            backButton.setRotation(180);

    }
}
