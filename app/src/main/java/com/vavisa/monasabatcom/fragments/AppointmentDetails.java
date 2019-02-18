package com.vavisa.monasabatcom.fragments;


import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.CompletePayment;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.OrderDetails;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;
import com.vavisa.monasabatcom.models.companyDetails.OfferServices;
import com.vavisa.monasabatcom.models.companyDetails.Offers;
import com.vavisa.monasabatcom.models.companyDetails.ServiceExtras;
import com.vavisa.monasabatcom.models.companyDetails.Services;
import com.vavisa.monasabatcom.models.makeAppointment.ExtrasOrder;
import com.vavisa.monasabatcom.models.makeAppointment.OfferOrder;
import com.vavisa.monasabatcom.models.makeAppointment.ServicesOrder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class AppointmentDetails extends Fragment {

    @BindView(R.id.com_name)
    TextView companyName;
    @BindView(R.id.services)
    LinearLayout servicesLayout;
    @BindView(R.id.offers)
    LinearLayout offersLayout;
    @BindView(R.id.com_na_txt)
    TextView com_nam_txt;
    @BindView(R.id.service_txt)
    TextView service_txt;
    @BindView(R.id.offer_txt)
    TextView offerTxt;
    @BindView(R.id.date_txt)
    TextView date_txt;
    @BindView(R.id.time_txt)
    TextView time_txt;
    @BindView(R.id.address_txt)
    TextView address_txt;
    @BindView(R.id.totalAmount_txt)
    TextView totalAmount_txt;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.address_name)
    TextView address_name;
    @BindView(R.id.address_phone)
    TextView address_phone;
    @BindView(R.id.fullAddress)
    TextView fullAddress;
    @BindView(R.id.totalAmount)
    TextView totalAmount;
    @OnClick(R.id.back)
    public void setBack(){
        getActivity().onBackPressed();
        Common.makeResr = true;
    }
    @OnClick(R.id.btnReservation)
    public void completeReservation(){

        makeOrder();
       // getActivity().startActivity(new Intent(getContext(),CompletePayment.class));
       // getActivity().finish();

    }

    ProgressDialog progressDialog;
    private CompanyDetailsModel comDetails;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Integer order_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.fragment_appointment_details, container, false);
      progressDialog = new ProgressDialog(getActivity());
      progressDialog.setCancelable(false);
      Button resrvation = view.findViewById(R.id.btnReservation);
      ImageView arrowAr = view.findViewById(R.id.arrow);

        if (Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

      if(Common.showReserBtn)
          resrvation.setVisibility(View.VISIBLE);

      ButterKnife.bind(this,view);

      if(Common.newOrder) {
          bindNewOrderDetails();
          Common.newOrder = false;
      }
      else
          requestData();
      return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Common.showReserBtn = false;
    }

    private void requestData() {
        progressDialog.show();
        compositeDisposable.add(Common.getAPI().getOrderDetails(Common.orderId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderDetails>() {
                    @Override
                    public void accept(OrderDetails orderDetails) throws Exception {
                        bindData(orderDetails);
                        com_nam_txt.setVisibility(View.VISIBLE);
                        service_txt.setVisibility(View.VISIBLE);
                        date_txt.setVisibility(View.VISIBLE);
                        time_txt.setVisibility(View.VISIBLE);
                        address_txt.setVisibility(View.VISIBLE);
                        totalAmount_txt.setVisibility(View.VISIBLE);
                        progressDialog.dismiss();
                    }
                }));

    }

    private void bindData(OrderDetails orderDetails) {

        RelativeLayout.LayoutParams linear_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        if(Common.isArabic) {
            companyName.setText(orderDetails.getCompanyNameAR());
            fullAddress.setText(orderDetails.getAddress().getFullAddressAR());

            for (Services services:orderDetails.getServices()) {
                setServices(servicesLayout, services.getNameAR(), services.getPrice(), true);

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);


                for (ServiceExtras serviceExtra : services.getServiceExtras()) {
                    setServicesExtra(extraLayout, serviceExtra.getNameAR(), serviceExtra.getPrice());
                }
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                serviceSetElegantNumberAndNote(extraLayout, servicesLayout, count_note, services.getQty(), services.getNote());
            }

            if (orderDetails.getOffers().size() > 0) {
                for (Offers offer : orderDetails.getOffers()) {
                    setServices(offersLayout, offer.getNameAR(), offer.getPrice(), true);

                    LinearLayout extraLayout = new LinearLayout(getActivity());
                    extraLayout.setLayoutParams(linear_description);
                    extraLayout.setOrientation(LinearLayout.VERTICAL);


                    for (OfferServices offerServices : offer.getOfferServices()) {
                        setServicesExtra(extraLayout, offerServices.getNameAR(), offerServices.getPrice());
                    }
                    LinearLayout count = new LinearLayout(getActivity());
                    count.setLayoutParams(linear_description);
                    serviceSetElegantNumberAndNote(extraLayout, offersLayout, count, offer.getQty(), null);
                }
            } else
                offerTxt.setVisibility(View.GONE);

        }else{
            companyName.setText(orderDetails.getCompanyNameEN());
            fullAddress.setText(orderDetails.getAddress().getFullAddressEN());

            for (Services services:orderDetails.getServices()) {
                setServices(servicesLayout, services.getNameEN(), services.getPrice(), true);

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);


                for (ServiceExtras serviceExtra : services.getServiceExtras()) {
                    setServicesExtra(extraLayout, serviceExtra.getNameEN(), serviceExtra.getPrice());
                }
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                serviceSetElegantNumberAndNote(extraLayout, servicesLayout, count_note, services.getQty(), services.getNote());
            }

            if (orderDetails.getOffers().size() > 0) {
                for (Offers offer : orderDetails.getOffers()) {
                    setServices(offersLayout, offer.getNameEN(), offer.getPrice(), true);

                    LinearLayout extraLayout = new LinearLayout(getActivity());
                    extraLayout.setLayoutParams(linear_description);
                    extraLayout.setOrientation(LinearLayout.VERTICAL);


                    for (OfferServices offerServices : offer.getOfferServices()) {
                        setServicesExtra(extraLayout, offerServices.getNameEN(), offerServices.getPrice());
                    }
                    LinearLayout count = new LinearLayout(getActivity());
                    count.setLayoutParams(linear_description);
                    serviceSetElegantNumberAndNote(extraLayout, offersLayout, count, offer.getQty(), null);
                }
            } else
                offerTxt.setVisibility(View.GONE);
        }

        date.setText(orderDetails.getAppointmentDate());
        time.setText(getResources().getString(R.string.from)+" "+orderDetails.getStartTime()+" "+getResources().getString(R.string.to)+" "+orderDetails.getEndTime());
        address_name.setText(orderDetails.getAddress().getName());
        address_phone.setText(orderDetails.getAddress().getPhone());


        totalAmount.setText(orderDetails.getTotalAmount()+" "+getResources().getString(R.string.kd));
    }

    private void bindNewOrderDetails() {

        RelativeLayout.LayoutParams linear_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        com_nam_txt.setVisibility(View.VISIBLE);
        service_txt.setVisibility(View.VISIBLE);
        date_txt.setVisibility(View.VISIBLE);
        time_txt.setVisibility(View.VISIBLE);
        address_txt.setVisibility(View.VISIBLE);
        totalAmount_txt.setVisibility(View.VISIBLE);


        if (Common.isArabic) {
            companyName.setText(Common.companyDetails.getNameAR());
            fullAddress.setText(Common.address.getFullAddressAR());

            for (ServicesOrder services : Common.appointment.getServicesOrder()) {
                setServices(servicesLayout, services.get_sNameAR(), services.getPrice(), true);

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);


                for (ExtrasOrder serviceExtra : services.getExtrasOrder()) {
                    setServicesExtra(extraLayout, serviceExtra.get_xNameAR(), serviceExtra.getPrice());
                }
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                serviceSetElegantNumberAndNote(extraLayout, servicesLayout, count_note, services.getQuantity(), services.getNote());
            }

            if (Common.appointment.getOfferOrder().size() > 0) {
                for (OfferOrder offer : Common.appointment.getOfferOrder()) {
                    setServices(offersLayout, offer.get_fNameAR(), offer.getPrice(), true);

                    LinearLayout extraLayout = new LinearLayout(getActivity());
                    extraLayout.setLayoutParams(linear_description);
                    extraLayout.setOrientation(LinearLayout.VERTICAL);


                    for (OfferServices offerServices : offer.getOfferServices()) {
                        setServicesExtra(extraLayout, offerServices.get_fsNameAR(), offerServices.getPrice());
                    }
                    LinearLayout count = new LinearLayout(getActivity());
                    count.setLayoutParams(linear_description);
                    serviceSetElegantNumberAndNote(extraLayout, offersLayout, count, offer.getQuantity(), null);
                }
            } else
                offerTxt.setVisibility(View.GONE);

        } else {
            companyName.setText(Common.companyDetails.getNameEN());
            fullAddress.setText(Common.address.getFullAddressEN());

            for (ServicesOrder services : Common.appointment.getServicesOrder()) {
                setServices(servicesLayout, services.get_sNameEN(), services.getPrice(), true);

                LinearLayout extraLayout = new LinearLayout(getActivity());
                extraLayout.setLayoutParams(linear_description);
                extraLayout.setOrientation(LinearLayout.VERTICAL);


                for (ExtrasOrder serviceExtra : services.getExtrasOrder()) {
                    setServicesExtra(extraLayout, serviceExtra.get_xNameEN(), serviceExtra.getPrice());
                }
                LinearLayout count_note = new LinearLayout(getActivity());
                count_note.setLayoutParams(linear_description);
                serviceSetElegantNumberAndNote(extraLayout, servicesLayout, count_note, services.getQuantity(), services.getNote());
            }

            if(Common.appointment.getOfferOrder().size()>0) {
                for (OfferOrder offer : Common.appointment.getOfferOrder()) {
                    setServices(offersLayout, offer.get_fNameEN(), offer.getPrice(), true);

                    LinearLayout extraLayout = new LinearLayout(getActivity());
                    extraLayout.setLayoutParams(linear_description);
                    extraLayout.setOrientation(LinearLayout.VERTICAL);


                    for (OfferServices offerServices : offer.getOfferServices()) {
                        setServicesExtra(extraLayout, offerServices.get_fsNameEN(), offerServices.getPrice());
                    }
                    LinearLayout count = new LinearLayout(getActivity());
                    count.setLayoutParams(linear_description);
                    serviceSetElegantNumberAndNote(extraLayout, offersLayout, count, offer.getQuantity(), null);
                }
            }else
                offerTxt.setVisibility(View.GONE);
        }

        date.setText(Common.date);
        time.setText(Common.time);
        address_name.setText(Common.address.getName());
        address_phone.setText(Common.address.getPhone());

        totalAmount.setText(Common.totalAmount+" "+getResources().getString(R.string.kd));


    }


    private void makeOrder() {

        progressDialog.show();

        final io.reactivex.Observable<Integer> orderId  = Common.getAPI().addOrder(Common.appointment).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        orderId.subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Integer integer) {

                order_id = integer;
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                progressDialog.dismiss();

               // Toast.makeText(getContext(), "//"+ String.valueOf(Common.orderId), Toast.LENGTH_SHORT).show();

                if (order_id == -1) {
                    Toast.makeText(getContext(), R.string.error_occure, Toast.LENGTH_SHORT).show();
                }
                else if (order_id == -2) {
                    Toast.makeText(getContext(), R.string.missing_required_data, Toast.LENGTH_SHORT).show();
                }
                else if (order_id == -3) {
                    Toast.makeText(getContext(), R.string.not_valide, Toast.LENGTH_SHORT).show();
                }
                else if (order_id == -4) {
                    Toast.makeText(getContext(), R.string.appointment_not_avaliable, Toast.LENGTH_SHORT).show();
                }
                else if (order_id == -5) {
                    Toast.makeText(getContext(), R.string.appointment_not_avaliable, Toast.LENGTH_SHORT).show();
                }
                else
                    Common.orderId = order_id;

                if(Common.orderId>0) {
                    getActivity().startActivity(new Intent(getContext(), CompletePayment.class));
                    getActivity().finish();
                }else
                    Toast.makeText(getContext(), R.string.error_occure, Toast.LENGTH_SHORT).show();



            }
        });
                //Common.getAPI().getGovernorates().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

/*
        compositeDisposable.add(Common.getAPI().addOrder(Common.appointment)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            Common.orderId = integer;
                         //   Toast.makeText(getContext(), "//"+String.valueOf(Common.orderId), Toast.LENGTH_SHORT).show();
                        }
                    }));

*/


    }


    private void setServices(LinearLayout layout, String name, Float priceValue, boolean main){

        RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        //layout for service and offers
        RelativeLayout namePriceLayout = new RelativeLayout(getActivity());
        namePriceLayout.setLayoutParams(layout_description);
        namePriceLayout.setPaddingRelative(4,10,6,18);
        namePriceLayout.setBackground(getResources().getDrawable(R.drawable.border));

        TextView _name = setNameText(name,main);
        TextView price = setPriceText(priceValue,main);

        namePriceLayout.addView(_name);
        namePriceLayout.addView(price);

        // servicePrice.put(service_id,price);

        layout.addView(namePriceLayout);
    }

    private void setServicesExtra(LinearLayout extraLayout, String name, Float priceValue){


        RelativeLayout.LayoutParams layout_description = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        // layout_description.addRule(RelativeLayout.CENTER_VERTICAL);

        //layout for service and offers
        RelativeLayout namePriceLayout = new RelativeLayout(getActivity());
        namePriceLayout.setLayoutParams(layout_description);
        namePriceLayout.setPaddingRelative(100,2,50,2);

        TextView _xname = setNameText(name,false);
        TextView extraPrice = setPriceText(priceValue,false);


        namePriceLayout.addView(_xname);
        namePriceLayout.addView(extraPrice);

        extraLayout.addView(namePriceLayout);

    }

    private void serviceSetElegantNumberAndNote(LinearLayout extraLayout, LinearLayout layout, LinearLayout CountNoteLayout, Integer count, String _note)
    {

        layout.addView(extraLayout);

        RelativeLayout.LayoutParams linearStart = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        RelativeLayout.LayoutParams linearEnd = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);

        linearStart.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.CENTER_VERTICAL);
        linearEnd.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.CENTER_VERTICAL);

        LinearLayout detailsLinearLayout = new LinearLayout(getActivity());
        detailsLinearLayout.setLayoutParams(linearStart);

        detailsLinearLayout.setOrientation(LinearLayout.VERTICAL);

        RelativeLayout details = new RelativeLayout(getActivity());
        details.setLayoutParams(linearStart);

        //quantity
        TextView quantity = new TextView(getActivity());
        quantity.setText(getResources().getString(R.string.quantity));
        quantity.setTextColor(getResources().getColor(R.color.black));
        quantity.setTypeface(null,Typeface.BOLD);
        quantity.setLayoutParams(linearStart);
        quantity.setGravity(Gravity.CENTER_VERTICAL);
        quantity.setPaddingRelative(30,30,0,20);

        TextView countNumber = setNameText(String.valueOf(count),true);
        countNumber.setPaddingRelative(20,30,30,20);
        countNumber.setLayoutParams(linearEnd);

        details.addView(quantity);
        details.addView(countNumber);

        detailsLinearLayout.addView(details);

        if(_note!=null||!TextUtils.isEmpty(_note)) {
            TextView noteTxt = new TextView(getActivity());
            noteTxt.setText(getResources().getString(R.string.note));
            noteTxt.setTextColor(getResources().getColor(R.color.black));
            noteTxt.setLayoutParams(linearStart);
            noteTxt.setTypeface(null,Typeface.BOLD);
            noteTxt.setPadding(20, 0, 20, 0);
            //noteTxt.setGravity(Gravity.END);
            // noteTxt.setId(id);

            TextView note = setNameText(_note, false);
            linearStart.setMargins(8, 0, 8, 12);
            note.setLayoutParams(linearStart);
            note.setBackground(getResources().getDrawable(R.drawable.border));
            note.setPaddingRelative(20, 30, 0, 10);
            note.setTextColor(getResources().getColor(R.color.emailFontColor));
            note.setLines(3);


            detailsLinearLayout.addView(noteTxt);
            detailsLinearLayout.addView(note);
        }


        CountNoteLayout.addView(detailsLinearLayout);
        layout.addView(CountNoteLayout);

    }

    private TextView setNameText(String _name,boolean main) {

        TextView name = new TextView(getActivity());
        name.setText(_name);
        name.setTextColor(getResources().getColor(R.color.black));
        RelativeLayout.LayoutParams nameLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        nameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_START,RelativeLayout.CENTER_VERTICAL);
        name.setLayoutParams(nameLayoutParams);
        if (main) {
            name.setTypeface(null, Typeface.BOLD);
            name.setTextSize(14);
        }

        name.setGravity(Gravity.CENTER_VERTICAL);
        name.setPaddingRelative(20,20,4,20);

        return name;
    }

    private TextView setPriceText(Float priceValue, boolean main) {

        TextView price = new TextView(getActivity());
        price.setText(String.valueOf(priceValue)+getResources().getString(R.string.kd));
        price.setTextColor(getResources().getColor(R.color.black));
        RelativeLayout.LayoutParams nameLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        nameLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END,RelativeLayout.CENTER_VERTICAL);
        price.setLayoutParams(nameLayoutParams);
        if (main) {
            price.setTypeface(null, Typeface.BOLD);
            price.setTextSize(14);
        }
        price.setGravity(Gravity.CENTER_VERTICAL);
        price.setPaddingRelative(20,12,0,0);

        return price;
    }

}
