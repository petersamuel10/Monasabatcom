package com.vavisa.monasabatcom.fragments;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Login;
import com.vavisa.monasabatcom.ProfileFragments.Addresses;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;
import com.vavisa.monasabatcom.models.companyDetails.OfferServices;
import com.vavisa.monasabatcom.models.companyDetails.Offers;
import com.vavisa.monasabatcom.models.companyDetails.Photos;
import com.vavisa.monasabatcom.models.companyDetails.ServiceExtras;
import com.vavisa.monasabatcom.models.companyDetails.Services;
import com.vavisa.monasabatcom.models.makeAppointment.ExtrasOrder;
import com.vavisa.monasabatcom.models.makeAppointment.MakeAppointment;
import com.vavisa.monasabatcom.models.makeAppointment.OfferOrder;
import com.vavisa.monasabatcom.models.makeAppointment.ServicesOrder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyDetails extends Fragment implements View.OnClickListener,RatingDialogListener, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.arrow)
    ImageView arrowAr;
    @BindView(R.id.com_nameTitle)
    TextView com_name_title;
    @BindView(R.id.slider)
    SliderLayout slider;
    @BindView(R.id.com_name)
    TextView com_name;
    @BindView(R.id.rating_ic)
    ImageView rating_ic;
    @BindView(R.id.share_ic)
    ImageView share_ic;
    @BindView(R.id.fav_ic)
    ImageView fav_ic;
    @BindView(R.id.com_rating)
    RatingBar ratingBar;
    @BindView(R.id.com_ratingCount)
    TextView com_ratingCount;
    @BindView(R.id.working_time)
    TextView workingTime;
    @BindView(R.id.com_about)
    TextView com_about;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.services)
    LinearLayout serviceLayout;
    @BindView(R.id.offer_Linear)
    LinearLayout offer_linear;
    @BindView(R.id.offer)
    LinearLayout offerLayout;
    @BindView(R.id.btnReserva)
    Button btnAppointment;

    @OnClick(R.id.btnReserva)
    public void makeAppoint() {
        userId = (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : null;
        if(userId==null) {
            Common.booking = true;
            getActivity().startActivity(new Intent(getContext(), Login.class));
        }
        else
            makeAppointment();
    }

    @OnClick(R.id.back)
    public void setBack() {
        getActivity().onBackPressed();
    }

    ProgressDialog progressDialog;
    Calendar calendar;
    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    Integer userId;
    List<Services> services = new ArrayList<>();
    List<Offers> offers = new ArrayList<>();
    HashMap<String, String> photosList;

    private CompanyDetailsModel comDetails;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    View v ;


    List<CheckBox> serviceCB = new ArrayList<>();
    HashMap<Integer, List<android.widget.CheckBox>> serviceExtraCB = new HashMap<>();
    // HashMap<Integer,List<TextView>> serviceExtraPrice = new HashMap<>();
    HashMap<Integer, ElegantNumberButton> serviceCount = new HashMap<>();
    HashMap<Integer, EditText> serviceNote = new HashMap<>();
    //  HashMap<Integer,TextView> servicePrice = new HashMap<>();

    HashMap<Integer, LinearLayout> serviceExtraLinear = new HashMap<>();
    HashMap<Integer, LinearLayout> serviceCNLinear = new HashMap<>();


    List<CheckBox> offerCB = new ArrayList<>();
    // HashMap<Integer, android.widget.CheckBox> offerCB = new HashMap<Integer, android.widget.CheckBox>();
    HashMap<Integer, List<CheckBox>> offerExtraCB = new HashMap<>();
    HashMap<Integer, ElegantNumberButton> offerCount = new HashMap<>();

    HashMap<Integer, LinearLayout> offerCNLinear = new HashMap<>();
    HashMap<Integer, LinearLayout> offerExtraLinear = new HashMap<>();


    //  HashMap<Integer,EditText> offerNote = new HashMap<>();
    //  HashMap<Integer,LinearLayout> offerDLinear = new HashMap<>();
    //  HashMap<Integer,TextView> offerPrice = new HashMap<>();
    //  HashMap<Integer,TextView> offerExtraPrice = new HashMap<>();


    ArrayList<ServicesOrder> servicesOrderList;
    ArrayList<ExtrasOrder> extrasOrderList;
    ArrayList<OfferOrder> offerOrderList;
    ArrayList<OfferServices> offerExtraOrderList;

    ServicesOrder servicesOrder;
    ExtrasOrder extrasOrder;
    OfferOrder offerOrder;
    OfferServices offerServices;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.company_details, container, false);

        v = getActivity().findViewById(android.R.id.content);
        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);
        date.setOnClickListener(this);
        time.setOnClickListener(this);
        fav_ic.setOnClickListener(this);
        share_ic.setOnClickListener(this);
        rating_ic.setOnClickListener(this);

        userId = (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : null;

        if (Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        if (Common.isConnectToTheInternet(getContext())) {
            requestData();
        } else {
            AlertDialog.Builder error = new AlertDialog.Builder(getContext());
            error.setMessage(R.string.error_connection);
            AlertDialog dialog = error.create();
            dialog.show();
            getActivity().onBackPressed();
        }

        return view;
    }

    private void requestData() {
        progressDialog.show();
        compositeDisposable.add(Common.getAPI().getCompanyDetails(Common.companyId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CompanyDetailsModel>() {
                    @Override
                    public void accept(CompanyDetailsModel companyDetailsModel) throws Exception {
                        comDetails = companyDetailsModel;
                        services = companyDetailsModel.getServices();
                        offers = companyDetailsModel.getOffers();
                        setupSlider();
                        bindData();
                        progressDialog.dismiss();
                    }
                }));
    }

    private void bindData() {

        if (comDetails.getFavourite())
            fav_ic.setImageResource(R.drawable.ic_favorite_black_24dp);

        ratingBar.setRating(comDetails.getRating());
        com_ratingCount.setText("(" + comDetails.getRatingCount() + ")");
        workingTime.setText(getResources().getString(R.string.from) + " " + comDetails.getWorkingFromTime() + " " + getResources().getString(R.string.to) + " " + comDetails.getWorkingToTime());


        if (comDetails.getOffers().size() == 0)
            offer_linear.setVisibility(View.GONE);

        RelativeLayout.LayoutParams linear_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        if (Common.isArabic) {

            com_name_title.setText(comDetails.getNameAR());
            com_name.setText(comDetails.getNameAR());
            com_about.setText(comDetails.getAboutAR());

            for (Services service : comDetails.getServices()) {
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                serviceCNLinear.put(service.getId(), count_note);
                setServices(serviceLayout, service.getNameAR(), service.getPrice(), service.getId());

                // Toast.makeText(getActivity(), "//"+String.valueOf(service.getId()), Toast.LENGTH_SHORT).show();

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);
                serviceExtraLinear.put(service.getId(), extraLayout);

                List<CheckBox> sxCBList = new ArrayList<>();
                List<TextView> sxPriceList = new ArrayList<>();

                for (ServiceExtras serviceExtra : service.getServiceExtras()) {
                    setServicesExtra(sxCBList, sxPriceList, extraLayout, serviceExtra.getNameAR(), serviceExtra.getPrice(), serviceExtra.getId());
                }
                serviceExtraCB.put(service.getId(), sxCBList);
                //serviceExtraPrice.put(service.getId(),extraPriceList);
                serviceSetElegantNumberAndNote(extraLayout, serviceLayout, count_note, service.getId());

            }

            for (Offers offer : comDetails.getOffers()) {
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                offerCNLinear.put(offer.getId(), count_note);
                setOffers(offerLayout, offer.getNameAR(), offer.getPrice(), offer.getId());

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);
                offerExtraLinear.put(offer.getId(), extraLayout);

                List<CheckBox> oxCBList = new ArrayList<>();
                List<TextView> oxPriceList = new ArrayList<>();

                for (OfferServices offerServices : offer.getOfferServices()) {

                    setOffersExtra(oxCBList, oxPriceList, extraLayout, offerServices.getNameAR(), offerServices.getPrice(), offerServices.getId());
                }
                offerExtraCB.put(offer.getId(), oxCBList);
                //  offerLayout.addView(extraLayout);
                offerSetElegantNumberAndNote(extraLayout, offerLayout, count_note, offer.getId());
            }

        }
        //////////////////////////English

        else
        {

            com_name_title.setText(comDetails.getNameEN());
            com_name.setText(comDetails.getNameEN());
            com_about.setText(comDetails.getAboutEN());

            for (Services service : comDetails.getServices()) {
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                serviceCNLinear.put(service.getId(), count_note);
                setServices(serviceLayout, service.getNameEN(), service.getPrice(), service.getId());

                // Toast.makeText(getActivity(), "//"+String.valueOf(service.getId()), Toast.LENGTH_SHORT).show();

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);
                serviceExtraLinear.put(service.getId(), extraLayout);

                List<CheckBox> sxCBList = new ArrayList<>();
                List<TextView> sxPriceList = new ArrayList<>();

                for (ServiceExtras serviceExtra : service.getServiceExtras()) {
                    setServicesExtra(sxCBList, sxPriceList, extraLayout, serviceExtra.getNameEN(), serviceExtra.getPrice(), serviceExtra.getId());
                }
                serviceExtraCB.put(service.getId(), sxCBList);
                //serviceExtraPrice.put(service.getId(),extraPriceList);
                serviceSetElegantNumberAndNote(extraLayout, serviceLayout, count_note, service.getId());

            }

            for (Offers offer : comDetails.getOffers()) {
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                offerCNLinear.put(offer.getId(), count_note);
                setOffers(offerLayout, offer.getNameEN(), offer.getPrice(), offer.getId());

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);
                offerExtraLinear.put(offer.getId(), extraLayout);

                List<CheckBox> oxCBList = new ArrayList<>();
                List<TextView> oxPriceList = new ArrayList<>();

                for (OfferServices offerServices : offer.getOfferServices()) {

                    setOffersExtra(oxCBList, oxPriceList, extraLayout, offerServices.getNameEN(), offerServices.getPrice(), offerServices.getId());
                }
                offerExtraCB.put(offer.getId(), oxCBList);
                //  offerLayout.addView(extraLayout);
                offerSetElegantNumberAndNote(extraLayout, offerLayout, count_note, offer.getId());
            }
        }

    }

    private void setupSlider() {

        photosList = new HashMap<>();

        for (Photos photo : comDetails.getPhoto()) {
            photosList.put(photo.getPhoto(), photo.getPhoto());
        }

        for (String name : photosList.keySet()) {

            TextSliderView textSliderView = new TextSliderView(getContext());

            textSliderView.image(photosList.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            slider.addSlider(textSliderView);
        }


        slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        // slider.setCustomAnimation(new DescriptionAnimation());
        // slider.setDuration(4000);
    }

    @Override
    public void onStop() {
        super.onStop();
        slider.stopAutoCycle();

    }

    @Override
    public void onClick(View v) {
        /*
        calendar   = Calendar.getInstance(Locale.ENGLISH);
        int mDay   = calendar.get(Calendar.DAY_OF_MONTH);
        int mMonth = calendar.get(Calendar.MONTH);
        int mYear  = calendar.get(Calendar.YEAR);
        int mHour   = calendar.get(Calendar.HOUR_OF_DAY);
        int mMinute = calendar.get(Calendar.MINUTE);
*/
        switch (v.getId()) {

            case R.id.fav_ic:
                setFavourite();
                break;

            case R.id.share_ic:
                setShare();
                break;
            case R.id.rating_ic:
                showRatingDialog();
                break;
            case R.id.date:
                selectDate();
                break;

            case R.id.time:
                if (TextUtils.isEmpty(date.getText()))
                    Toast.makeText(getActivity(), getResources().getString(R.string.select_date), Toast.LENGTH_SHORT).show();
                else
                    selectTime();
/*
                    timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            time.setText(String.format("%02d:%02d", hourOfDay, minute));
                        }}, mHour, mMinute, true);
                    timePickerDialog.show();
                    */

                break;
        }
    }

    public void selectDate() {
        Locale.setDefault(Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        String stringDate = String.format("%02d/%02d/%d", dayOfMonth, (monthOfYear + 1), year);
                        date.setText(stringDate);
                        time.setText("");
                        Common.date = stringDate;

                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();

        /*
        datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                date.setText(dayOfMonth+"/"+(month+1)+"/"+year);
            }
        },mYear,mMonth,mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
        */

    }

    public void selectTime() {
        Locale.setDefault(Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        // String  stringTime;

        com.wdullaer.materialdatetimepicker.time.TimePickerDialog tpd = com.wdullaer.materialdatetimepicker.time.TimePickerDialog.newInstance(
                new com.wdullaer.materialdatetimepicker.time.TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(com.wdullaer.materialdatetimepicker.time.TimePickerDialog view, int hourOfDay, int minute, int second) {

                        Calendar datetime = Calendar.getInstance();
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                        String formattedDate = df.format(c.getTime());

                        if (formattedDate.equalsIgnoreCase(date.getText().toString().trim())) {
                            datetime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                            datetime.set(Calendar.MINUTE, minute);

                            if (datetime.getTimeInMillis() >= c.getTimeInMillis()) {

                                time.setText(String.format("%02d:%02d", hourOfDay, minute));
                                Common.time = String.format("%02d:%02d", hourOfDay, minute);
                            } else {
                                //it's before current'
                                Snackbar.make(v, getResources().getString(R.string.invalid_time), Snackbar.LENGTH_LONG).show();
                            }
                        } else {
                            time.setText(String.format("%02d:%02d", hourOfDay, minute));
                            Common.time = String.format("%02d:%02d", hourOfDay, minute);
                        }
                    }
                },
                hour,
                minute,
                true);

        tpd.show(getActivity().getFragmentManager(), "Time");

    }

    private void setShare() {
        userId = (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : null;
        if(userId==null) {
            Common.booking = true;
            getActivity().startActivity(new Intent(getContext(), Login.class));
        }
        else
        {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, com_name.getText().toString().trim());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);
        }
    }

    private void setFavourite() {
        userId = (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : null;
        if (userId != null) {
            compositeDisposable.add(Common.getAPI().markAsFavorite(userId, comDetails.getId())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            if (integer > 0) {
                                if (comDetails.getFavourite()) {
                                    fav_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                                    comDetails.setFavourite(false);
                                } else {
                                    fav_ic.setImageDrawable(getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                                    comDetails.setFavourite(true);
                                }
                            }
                        }
                    }));
        } else {
            Common.booking = true;
            getActivity().startActivity(new Intent(getContext(), Login.class));
        }
    }

    private void showRatingDialog() {
        userId = (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : null;
        if (userId != null) {
            new AppRatingDialog.Builder()
                    .setPositiveButtonText(R.string.rating_now)
                    .setNegativeButtonText(R.string.cancel)
                    .setDefaultRating(0)
                    .setCommentInputEnabled(false)
                    .setTitle(R.string.rating)
                    .setStarColor(R.color.orange)
                    .setTitleTextColor(R.color.colorPrimary)
                    .setWindowAnimation(R.style.RatingDialogFadeAnim)
                    .create(getActivity())
                    .setTargetFragment(this, 1)
                    .show();

        } else {
            Common.booking = true;
            getActivity().startActivity(new Intent(getContext(), Login.class));
        }
    }

    @Override
    public void onPositiveButtonClicked(int value, String comment) {

        compositeDisposable.add(Common.getAPI().rating(comDetails.getId(), userId, value)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer > 0) {
                            AlertDialog.Builder rating_success = new AlertDialog.Builder(getContext());
                            rating_success.setMessage(R.string.thanks_rating)
                                    .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            requestData();
                                        }
                                    });
                            AlertDialog dialog = rating_success.create();
                            dialog.show();
                        } else {
                            AlertDialog.Builder error = new AlertDialog.Builder(getContext());
                            error.setMessage(R.string.error_connection);
                            AlertDialog dialog = error.create();
                            dialog.show();
                        }
                    }
                }));
    }

    @Override
    public void onNegativeButtonClicked() { }

    @Override
    public void onNeutralButtonClicked() { }

    @Override
    public void onCheckedChanged(CompoundButton CB, boolean isChecked) {
        int id = CB.getId();
        //   Toast.makeText(getActivity(), "///"+String.valueOf(id), Toast.LENGTH_SHORT).show();

        if (isChecked) {

            if (CB.getTag().equals("serviceCB")) {
                serviceExtraLinear.get(id).setVisibility(View.VISIBLE);
                serviceCNLinear.get(id).setVisibility(View.VISIBLE);
                for (Services service:services) {
                    if(id==service.getId())
                        service.setSelected(true);
                }
            }

            if(CB.getTag().equals("serviceExtraCB"))
            {
                for (Services service : services) {
                    for (ServiceExtras serviceExtras : service.getServiceExtras()) {
                        if(id == serviceExtras.getId())
                            serviceExtras.setSelected(true);
                    }
                }
            }


            if (CB.getTag().equals("offerCB")) {
                offerExtraLinear.get(id).setVisibility(View.VISIBLE);
                offerCNLinear.get(id).setVisibility(View.VISIBLE);
                for (Offers  offer:offers) {
                    if(id==offer.getId())
                        offer.setSelected(true);
                }
            }

            if(CB.getTag().equals("offerExtraCB"))
            {
                for (Offers offer : offers) {
                    for (OfferServices offerServices : offer.getOfferServices()) {
                        if(id == offerServices.getId())
                            offerServices.setSelected(true);
                    }
                }
            }

        } else {

            if (CB.getTag().equals("serviceCB")) {
                serviceExtraLinear.get(id).setVisibility(View.GONE);
                serviceCNLinear.get(id).setVisibility(View.GONE);
                for (Services service:services) {
                    if(id==service.getId()) {
                        service.setSelected(false);
                        for (ServiceExtras serviceExtra:service.getServiceExtras()) {
                            serviceExtra.setSelected(false);
                        }
                        //unchecked sub
                        List<CheckBox> cbList = serviceExtraCB.get(id);
                        for (CheckBox cb:cbList) {
                            cb.setChecked(false);
                        }
                        ElegantNumberButton numberButton = serviceCount.get(id);
                        numberButton.setNumber("1");
                        EditText ed = serviceNote.get(id);
                        ed.setText("");
                    }
                }
            }

            if(CB.getTag().equals("serviceExtraCB"))
            {
                for (Services service : services) {
                    for (ServiceExtras serviceExtras : service.getServiceExtras()) {
                        if(id == serviceExtras.getId())
                            serviceExtras.setSelected(false);
                    }
                }
            }

            if (CB.getTag().equals("offerCB")) {
                offerExtraLinear.get(id).setVisibility(View.GONE);
                offerCNLinear.get(id).setVisibility(View.GONE);
                for (Offers  offer:offers) {
                    if(id==offer.getId()) {
                        offer.setSelected(false);
                        //unchecked sub
                        List<CheckBox> cbList = offerExtraCB.get(id);
                        for (CheckBox cb:cbList) {
                            cb.setChecked(false);
                        }
                        ElegantNumberButton numberButton = offerCount.get(id);
                        numberButton.setNumber("1");
                        //  EditText ed = serviceNote.get(id);
                        //  ed.setText("
                    }
                }
            }

            if(CB.getTag().equals("offerExtraCB"))
            {
                for (Offers offer : offers) {
                    for (OfferServices offerServices : offer.getOfferServices()) {
                        if(id == offerServices.getId())
                            offerServices.setSelected(false);
                    }
                }
            }
        }
    }

    private void makeAppointment() {

        // final ArrayList<ServicesOrder> servicesOrders = new ArrayList<>();
        //  final ArrayList<OfferOrder> offerOrders = new ArrayList<>();

        servicesOrderList = new ArrayList<>();
        offerOrderList = new ArrayList<>();
        Float total = 0.0f;


        for (Services s : services) {

            if (s.isSelected()) {

                Float serviceTotal = 0.0f;

                ElegantNumberButton nb = serviceCount.get(s.getId());
                //Toast.makeText(getActivity(), "nnn"+String.valueOf(serviceCount.get(s.getId()).getNumber()), Toast.LENGTH_SHORT).show();
                EditText note = serviceNote.get(s.getId());

                extrasOrderList = new ArrayList<>();
                servicesOrder = new ServicesOrder();

                servicesOrder.set_sNameAR(s.getNameAR());
                servicesOrder.set_sNameEN(s.getNameEN());
                servicesOrder.setServiceId(s.getId());
                servicesOrder.setPrice(s.getPrice());
                servicesOrder.setQuantity(Integer.parseInt(nb.getNumber()));
                servicesOrder.setNote(note.getText().toString());

                serviceTotal+= servicesOrder.getPrice();

                for (ServiceExtras sx : s.getServiceExtras()) {
                    if (sx.isSelected()) {
                        extrasOrder = new ExtrasOrder();

                        extrasOrder.set_xNameAR(sx.getNameAR());
                        extrasOrder.set_xNameEN(sx.getNameEN());
                        extrasOrder.setExtraId(sx.getId());
                        extrasOrder.setPrice(sx.getPrice());
                        serviceTotal+=extrasOrder.getPrice();

                        extrasOrderList.add(extrasOrder);
                    }
                }
                servicesOrder.setExtrasOrder(extrasOrderList);
                servicesOrderList.add(servicesOrder);

                total+= serviceTotal*servicesOrder.getQuantity();
            }

        }


        // offers
        for (Offers f : offers)
        {
            if (f.isSelected()) {

                Float offerTotal = 0.0f;

                ElegantNumberButton nb = offerCount.get(f.getId());
                // Toast.makeText(getActivity(), "//"+String.valueOf(offerCount.size()), Toast.LENGTH_SHORT).show();
                //  Toast.makeText(getActivity(), "//"+String.valueOf(offerCount.get(f.getId()).getNumber()), Toast.LENGTH_SHORT).show();
                // EditText note = offerNote.get(s.getId());

                offerExtraOrderList = new ArrayList<>();
                offerOrder = new OfferOrder();

                offerOrder.set_fNameAR(f.getNameAR());
                offerOrder.set_fNameEN(f.getNameEN());
                offerOrder.setOfferIdId(f.getId());
                offerOrder.setPrice(f.getPrice());
                offerOrder.setQuantity(Integer.parseInt(nb.getNumber()));
                offerOrder.setNote("");

                offerTotal += offerOrder.getPrice();

                //offer Services
                for (OfferServices fs : f.getOfferServices()) {
                    if (fs.isSelected()) {
                        offerServices = new OfferServices();

                        offerServices.set_fsNameAR(fs.getNameAR());
                        offerServices.set_fsNameEN(fs.getNameEN());
                        offerServices.setId(fs.getId());
                        offerServices.setPrice(fs.getPrice());
                        offerTotal += offerServices.getPrice();

                        offerExtraOrderList.add(offerServices);
                    }
                }
                offerOrder.setOfferServices(offerExtraOrderList);
                offerOrderList.add(offerOrder);

                total += offerTotal * offerOrder.getQuantity();
            }

        }
        Common.totalAmount = total;

        //  Toast.makeText(getActivity(), "<<"+String.valueOf(total), Toast.LENGTH_SHORT).show();

        completeOrder();
    }

    private void completeOrder() {

        final String appointmentDate = String.format("%s %s", date.getText().toString(), time.getText().toString());

        if (servicesOrderList.size() < 1)
            Snackbar.make(v, getResources().getString(R.string.select_service), Snackbar.LENGTH_LONG).show();
        else {

            if (TextUtils.isEmpty(date.getText().toString()) || TextUtils.isEmpty(time.getText().toString()))
                Snackbar.make(v, getResources().getString(R.string.select_date_time), Snackbar.LENGTH_LONG).show();

            else {


                compositeDisposable.add(Common.getAPI().checkAppointmentDate(comDetails.getId(), appointmentDate)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Integer>() {
                            @Override
                            public void accept(Integer integer) throws Exception {
                                if (integer > 0) {
                                    saveData();
                                } else
                                    Snackbar.make(v, getResources().getString(R.string.appointment_not_avaliable), Snackbar.LENGTH_LONG).show();
                            }
                        }));
            }
        }
    }

    private void saveData() {

        final String appointmentDate = String.format("%s %s", date.getText().toString(), time.getText().toString());

        Common.appointment = new MakeAppointment();
        Common.companyDetails = comDetails;
        Common.appointment.setCompanyId(comDetails.getId());
        Common.appointment.setAppointmentDate(appointmentDate);
        Common.appointment.setUserId(Common.currentUser.getId());
        // Common.appointment.setAddressId(Common.addressId);
        Common.appointment.setPaymentMethodId(1);
        Common.appointment.setServicesOrder(servicesOrderList);
        Common.appointment.setOfferOrder(offerOrderList);

        Common.makeResr =true;
        Fragment fragment = new Addresses();
        FragmentManager fragmentManager = Common.mActivity.getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }

    private void setServices(LinearLayout layout, String name, Float priceValue, Integer service_id){

        RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //layout for service and offers
        RelativeLayout namePriceLayout = new RelativeLayout(getActivity());
        namePriceLayout.setLayoutParams(layout_description);
        namePriceLayout.setPaddingRelative(4,20,6,20);
        namePriceLayout.setBackground(getResources().getDrawable(R.drawable.border_services));

        android.widget.CheckBox cb = setCheckBox(name,service_id);

        TextView price = setPriceText(priceValue);

        cb.setTag("serviceCB");
        cb.setHint(String.valueOf(priceValue));
        price.setTag("servicePrice");

        namePriceLayout.addView(cb);
        namePriceLayout.addView(price);

        serviceCB.add(cb);
        // servicePrice.put(service_id,price);

        layout.addView(namePriceLayout);
    }

    private void setOffers(LinearLayout layout, String name, Float priceValue, Integer offer_id){


        RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //layout for service and offers
        RelativeLayout namePriceLayout = new RelativeLayout(getActivity());
        namePriceLayout.setLayoutParams(layout_description);
        namePriceLayout.setPaddingRelative(4,20,6,20);
        namePriceLayout.setBackground(getResources().getDrawable(R.drawable.border_services));

        android.widget.CheckBox cb = setCheckBox(name,offer_id);

        TextView price = setPriceText(priceValue);

        cb.setTag("offerCB");
        cb.setHint(String.valueOf(priceValue));
        price.setTag("offerPrice");

        namePriceLayout.addView(cb);
        namePriceLayout.addView(price);

        // id to prevent conflict with service id
        offerCB.add(cb);
        //  offerPrice.put(offer_id,price);

        layout.addView(namePriceLayout);
    }

    private void setServicesExtra(List<CheckBox> serviceExtraList, List<TextView> sxPriceList, LinearLayout extraLayout, String name, Float priceValue,
                                  Integer serviceExtraId){


        RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        // layout_description.addRule(RelativeLayout.CENTER_VERTICAL);

        //layout for service and offers
        RelativeLayout namePriceLayout = new RelativeLayout(getActivity());
        namePriceLayout.setLayoutParams(layout_description);
        namePriceLayout.setPaddingRelative(100,2,50,2);

        android.widget.CheckBox extraCB = setCheckBox(name,serviceExtraId);
        TextView extraPrice = setPriceText(priceValue);

        extraCB.setHint(String.valueOf(priceValue));
        extraCB.setTag("serviceExtraCB");
        extraPrice.setTag("serviceExtraPrice");

        namePriceLayout.addView(extraCB);
        namePriceLayout.addView(extraPrice);

        serviceExtraList.add(extraCB);
        sxPriceList.add(extraPrice);

        //  serviceExtraCB.put(service_id,extraCB);

        extraLayout.addView(namePriceLayout);

        extraLayout.setVisibility(LinearLayout.GONE);

    }

    private void setOffersExtra(List<CheckBox> offerExtraList, List<TextView> oxPriceList,LinearLayout extraLayout, String name, Float priceValue,
                                Integer offerExtraId){


        RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        // layout_description.addRule(RelativeLayout.CENTER_VERTICAL);

        //layout for service and offers
        RelativeLayout namePriceLayout = new RelativeLayout(getActivity());
        namePriceLayout.setLayoutParams(layout_description);
        namePriceLayout.setPaddingRelative(100,2,50,2);

        android.widget.CheckBox extraCB = setCheckBox(name,offerExtraId);
        TextView extraPrice = setPriceText(priceValue);

        extraCB.setHint(String.valueOf(priceValue));
        extraCB.setTag("offerExtraCB");
        extraPrice.setTag("offerExtraPrice");

        namePriceLayout.addView(extraCB);
        namePriceLayout.addView(extraPrice);


        offerExtraList.add(extraCB);
        oxPriceList.add(extraPrice);


        extraLayout.addView(namePriceLayout);

        extraLayout.setVisibility(LinearLayout.GONE);

    }

    private void serviceSetElegantNumberAndNote(LinearLayout extraLayout, LinearLayout layout, LinearLayout CountNoteLayout, Integer service_id)
    {

        layout.addView(extraLayout);

        RelativeLayout.LayoutParams linear = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout detailsLinearLayout = new LinearLayout(getActivity());
        detailsLinearLayout.setLayoutParams(linear);

        detailsLinearLayout.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout details = new RelativeLayout(getActivity());
        details.setLayoutParams(linear);

        //quantity
        TextView quantity = new TextView(getActivity());
        quantity.setText(getResources().getString(R.string.quantity));
        quantity.setTextColor(getResources().getColor(R.color.black));
        RelativeLayout.LayoutParams priceLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        priceLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.CENTER_VERTICAL);
        quantity.setLayoutParams(priceLayoutParams);
        quantity.setGravity(Gravity.CENTER_VERTICAL);
        quantity.setPaddingRelative(0,50,0,0);

        ElegantNumberButton numberButton = new ElegantNumberButton (getActivity());
        numberButton.setRange(1,50);
        numberButton.setNumber("1");

        RelativeLayout.LayoutParams quantityParams = new RelativeLayout.LayoutParams(300,150);
        quantityParams.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.CENTER_VERTICAL);
        numberButton.setPaddingRelative(0,50,0,0);
        numberButton.setLayoutParams(quantityParams);
        // numberButton.setId(id);
        details.addView(quantity);
        details.addView(numberButton);


        TextView noteTxt = new TextView(getActivity());
        noteTxt.setText(getResources().getString(R.string.note));
        noteTxt.setTextColor(getResources().getColor(R.color.black));
        noteTxt.setLayoutParams(priceLayoutParams);
        noteTxt.setGravity(Gravity.END);
        // noteTxt.setId(id);
        noteTxt.setPaddingRelative(20,30,0,0);

        EditText note = new EditText(getActivity());
        note.setPadding(2,2,2,2);
        note.setBackground(getResources().getDrawable(R.drawable.border));
        linear.setMargins(8,8,8,8);
        note.setLayoutParams(linear);
        note.setLines(4);

        detailsLinearLayout.addView(details);
        detailsLinearLayout.addView(noteTxt);
        detailsLinearLayout.addView(note);


        serviceCount.put(service_id,numberButton);
        serviceNote.put(service_id,note);


        CountNoteLayout.addView(detailsLinearLayout);
        CountNoteLayout.setVisibility(LinearLayout.GONE);
        layout.addView(CountNoteLayout);

    }

    private void offerSetElegantNumberAndNote(LinearLayout extraLayout, LinearLayout layout, LinearLayout CountNoteLayout, Integer offer_id)
    {

        layout.addView(extraLayout);

        RelativeLayout.LayoutParams linear = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        LinearLayout detailsLinearLayout = new LinearLayout(getActivity());
        detailsLinearLayout.setLayoutParams(linear);

        detailsLinearLayout.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout details = new RelativeLayout(getActivity());
        details.setLayoutParams(linear);

        //quantity
        TextView quantity = new TextView(getActivity());
        quantity.setText(getResources().getString(R.string.quantity));
        quantity.setTextColor(getResources().getColor(R.color.black));
        RelativeLayout.LayoutParams priceLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        priceLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.CENTER_VERTICAL);
        quantity.setLayoutParams(priceLayoutParams);
        quantity.setGravity(Gravity.CENTER_VERTICAL);
        quantity.setPaddingRelative(0,50,0,0);

        ElegantNumberButton numberButton = new ElegantNumberButton (getActivity());
        numberButton.setRange(1,50);
        numberButton.setNumber("1");

        RelativeLayout.LayoutParams quantityParams = new RelativeLayout.LayoutParams(300,150);
        quantityParams.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.CENTER_VERTICAL);
        numberButton.setPaddingRelative(0,50,0,0);
        numberButton.setLayoutParams(quantityParams);
        // numberButton.setId(id);
        details.addView(quantity);
        details.addView(numberButton);

/*
        TextView noteTxt = new TextView(getActivity());
        noteTxt.setText(getResources().getString(R.string.note));
        noteTxt.setTextColor(getResources().getColor(R.color.black));
        noteTxt.setLayoutParams(priceLayoutParams);
        noteTxt.setGravity(Gravity.END);
        // noteTxt.setId(id);
        noteTxt.setPaddingRelative(20,30,0,0);
        EditText note = new EditText(getActivity());
        note.setPadding(2,2,2,2);
        note.setBackground(getResources().getDrawable(R.drawable.border));
        note.setLines(4);
        */

        detailsLinearLayout.addView(details);
        //  detailsLinearLayout.addView(noteTxt);
        //  detailsLinearLayout.addView(note);


        offerCount.put(offer_id,numberButton);
        //   serviceNote.put(service_offer_id,note);


        CountNoteLayout.addView(detailsLinearLayout);
        CountNoteLayout.setVisibility(LinearLayout.GONE);
        layout.addView(CountNoteLayout);

    }

    private android.widget.CheckBox setCheckBox(String name, int id) {

        CheckBox cb = new CheckBox(getActivity());
        RelativeLayout.LayoutParams nameLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        nameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.CENTER_VERTICAL);
        cb.setLayoutParams(nameLayoutParams);
        cb.setTextColor(getResources().getColor(R.color.black));
        cb.setText(name);
        cb.setOnCheckedChangeListener(this);

        cb.setId(id);

        return cb;
    }

    private TextView setPriceText(Float priceValue) {

        TextView price = new TextView(getActivity());
        price.setText(String.valueOf(priceValue)+getResources().getString(R.string.kd));
        price.setTextColor(getResources().getColor(R.color.black));
        RelativeLayout.LayoutParams nameLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        nameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.CENTER_VERTICAL);
        price.setLayoutParams(nameLayoutParams);
        price.setGravity(Gravity.CENTER_VERTICAL);
        price.setPaddingRelative(20,12,0,0);

        return price;
    }



}