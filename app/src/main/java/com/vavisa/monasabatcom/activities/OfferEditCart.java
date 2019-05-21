package com.vavisa.monasabatcom.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.vavisa.monasabatcom.models.companyDetails.Offers;
import com.vavisa.monasabatcom.models.companyDetails.Photos;
import com.vavisa.monasabatcom.models.companyDetails.WorkingHours;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OfferEditCart extends AppCompatActivity implements View.OnClickListener {

    private ProgressBar pb;
    private NestedScrollView scroll;
    private SliderLayout slider;
    private TextView offerName, offerPayStatus, offerPrice, offerDescription, howToOffer_tag,
            howToOffer, offerTime_tag, offerTime, requirements_tag, requirements, durationOfStay_tag, durationOfStay,
            order_before_tag, order_before;
    private TextView date_ed, time_ed;
    private EditText extraNotes;
    private CheckBox womenServiceCheck;
    /*    private ListView offerExtrasList;*/
    private Button addToCart;

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int offerId, index_cart;
    private Offers offersData;
    private String notes, date, hour;
    private String[] dateList, timeList;
    private int date_item_position = 0, time_item_position = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_edit_cart);
        reference();

        index_cart = getIntent().getExtras().getInt("index");
        offerId = Integer.parseInt(getIntent().getExtras().getString("offerId"));
        date = getIntent().getExtras().getString("searchDate");
        hour = getIntent().getExtras().getString("searchHour");
        notes = getIntent().getExtras().getString("notes");

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
                                        try {
                                            bindData();
                                        } catch (Exception e) {
                                        }

                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        pb.setVisibility(View.GONE);
                                        Common.errorAlert(OfferEditCart.this, getString(R.string.error_occure));
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

        if (offersData.getServiceAndPresentationMethodEN() == null) {
            howToOffer_tag.setVisibility(View.GONE);
            howToOffer.setVisibility(View.GONE);
        } else {

            if (Common.isArabic)
                howToOffer.setText(offersData.getServiceAndPresentationMethodAR());
            else
                howToOffer.setText(offersData.getServiceAndPresentationMethodEN());
        }
        if (offersData.getPreparationTimeEN() == null) {
            offerTime_tag.setVisibility(View.GONE);
            offerTime.setVisibility(View.GONE);
        } else {
            if (Common.isArabic)
                offerTime.setText(offersData.getPreparationTimeAR());
            else
                offerTime.setText(offersData.getPreparationTimeEN());
        }

        if (offersData.getRequirementsEN() == null) {
            requirements_tag.setVisibility(View.GONE);
            requirements.setVisibility(View.GONE);
        } else {
            if (Common.isArabic)
                requirements.setText(offersData.getRequirementsAR());
            else
                requirements.setText(offersData.getRequirementsEN());
        }

        if (offersData.getStayDurationEN() == null) {
            durationOfStay_tag.setVisibility(View.GONE);
            durationOfStay.setVisibility(View.GONE);
        } else {
            if (Common.isArabic)
                durationOfStay.setText(offersData.getStayDurationAR());
            else
                durationOfStay.setText(offersData.getStayDurationEN());
        }


        if (offersData.getDeliveryTime() == null) {
            order_before_tag.setVisibility(View.GONE);
            order_before.setVisibility(View.GONE);
        } else
            order_before.setText(offersData.getDeliveryTime() + getString(R.string.hour));

        if (offersData.getWomenService())
            womenServiceCheck.setVisibility(View.VISIBLE);
        else
            womenServiceCheck.setVisibility(View.GONE);

        switch (offersData.getPaymentTypeId()) {
            case 1:
                offerPayStatus.setVisibility(View.GONE);
                break;
            case 2:
                offerPayStatus.setText(getString(R.string.pay_a_deposit));
                break;
            case 3:
                offerPayStatus.setText(getString(R.string.pay_full_amount_in_advance));
                break;
        }

        setupSlider();
        getDateList();

        date_ed.setText(date);
        date_item_position = new ArrayList<String>(Arrays.asList(dateList)).indexOf(date);
        getTimeList();
        extraNotes.setText(notes);


    }

    private void setupSlider() {
        for (Photos photo : offersData.getPhoto()) {
            TextSliderView textSliderView = new TextSliderView(this);
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
            ArrayList<OfferOrder> offersOrderList = Common.cart.getOffers();
            offersOrderList.set(index_cart, new OfferOrder(offersData.getNameAR(), offersData.getNameEN(), offersData.getPaymentTypeId(),
                    offersData.getDepositPercentage(), offerId, date_ed.getText().toString(), time_ed.getText().toString(),
                    womenServiceCheck.isChecked(), 1, extraNotes.getText().toString(), offersData.getPrice()));

            Common.cart.setOffers(offersOrderList);

            onBackPressed();
        }
    }

    private boolean checkIf_offer_not_exist() {

        for (OfferOrder order : Common.cart.getOffers()) {
            if (order.getOfferIdId() == offersData.getId() &&
                    order.getDate().equals(date_ed.getText().toString()) &&
                    order.getTime().equals(time_ed.getText().toString())) {
                if (index_cart == Common.cart.getOffers().indexOf(order))
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

        Toolbar toolbar = findViewById(R.id.offer_toolbar);
        ImageView backButton = toolbar.findViewById(R.id.back_button);

        pb = findViewById(R.id.pb);
        scroll = findViewById(R.id.scroll);
        slider = findViewById(R.id.offer_images);
        offerName = findViewById(R.id.offer_name);
        offerPayStatus = findViewById(R.id.pay_status);
        offerPrice = findViewById(R.id.offer_price);
        offerDescription = findViewById(R.id.offer_description);
        howToOffer_tag = findViewById(R.id.how_to_serve_tag);
        howToOffer = findViewById(R.id.how_to_serve);
        offerTime_tag = findViewById(R.id.offer_time_tag);
        offerTime = findViewById(R.id.offer_time);
        requirements_tag = findViewById(R.id.requirements_tag);
        requirements = findViewById(R.id.requirements);
        durationOfStay_tag = findViewById(R.id.duration_of_stay_tag);
        durationOfStay = findViewById(R.id.duration_of_stay);
        order_before_tag = findViewById(R.id.order_before_tag);
        order_before = findViewById(R.id.order_before);
        date_ed = findViewById(R.id.date);
        time_ed = findViewById(R.id.time);
        extraNotes = findViewById(R.id.extra_notes);
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
