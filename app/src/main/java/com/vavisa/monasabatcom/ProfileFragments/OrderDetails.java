package com.vavisa.monasabatcom.ProfileFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.Cart.OfferCartAdapter;
import com.vavisa.monasabatcom.adapter.Cart.ServiceCartAdapter;
import com.vavisa.monasabatcom.adapter.profileAdpaters.OfferDetailsAdapter;
import com.vavisa.monasabatcom.adapter.profileAdpaters.ServiceDetailsAdapter;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;
import com.vavisa.monasabatcom.models.profile.OfferDetails;
import com.vavisa.monasabatcom.models.profile.OrderDetailsModel;
import com.vavisa.monasabatcom.models.profile.ServiceDetails;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class OrderDetails extends Fragment {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.scroll)
    NestedScrollView scroll;
    @BindView(R.id.order_num)
    TextView order_num_txt;
    @BindView(R.id.order_from)
    TextView order_from_txt;
    @BindView(R.id.order_status)
    TextView order_status_txt;
    @BindView(R.id.service_rec)
    RecyclerView services_rec;
    @BindView(R.id.offer_rec)
    RecyclerView offers_rec;
    @BindView(R.id.address_name)
    TextView address_name_txt;
    @BindView(R.id.address_phone)
    TextView address_phone_txt;
    @BindView(R.id.fullAddress)
    TextView address_details_txt;
    @BindView(R.id.total)
    TextView total_txt;
    @BindView(R.id.delivery)
    TextView delivery_txt;
    @BindView(R.id.due_amount)
    TextView due_amount_txt;
    @BindView(R.id.paid_amount)
    TextView paid_amount_txt;
    @BindView(R.id.payment_method)
    TextView payment_method_txt;

    @OnClick(R.id.cancel)
    public void back() {
        getActivity().onBackPressed();
    }


    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private Integer order_id;
    private ServiceDetailsAdapter serviceAdapter;
    private OfferDetailsAdapter offerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_order_details, container, false);
        ButterKnife.bind(this, view);
        ImageView arrowAr = view.findViewById(R.id.cancel);
        if (Common.isArabic)
            arrowAr.setRotation(180);

        order_id = Integer.valueOf(getArguments().getString("orderId"));

        requestData();

        return view;
    }


    private void requestData() {
        pb.setVisibility(View.VISIBLE);

        compositeDisposable.add(Common.getAPI().getOrderDetails(order_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<OrderDetailsModel>() {
                    @Override
                    public void accept(OrderDetailsModel orderDetails) throws Exception {
                        pb.setVisibility(View.GONE);
                        scroll.setVisibility(View.VISIBLE);
                        bindData(orderDetails);
                    }
                }));

    }

    private void bindData(OrderDetailsModel order) {

        order_num_txt.setText(order_id.toString());

        if (Common.isArabic) {
            order_from_txt.setText(order.getCompanyNameAR());
            order_status_txt.setText(order.getStatusNameAR());
            payment_method_txt.setText(order.getPaymentMethodNameAR());
        } else {
            order_from_txt.setText(order.getCompanyNameEN());
            order_status_txt.setText(order.getStatusNameEN());
            payment_method_txt.setText(order.getPaymentMethodNameEN());
        }

        if (order.getServices().size() > 0) {
            serviceAdapter = new ServiceDetailsAdapter((ArrayList<ServiceDetails>) order.getServices().clone());
            services_rec.setAdapter(serviceAdapter);
        }
        if (order.getOffers().size() > 0) {
            offerAdapter = new OfferDetailsAdapter((ArrayList<OfferDetails>) order.getOffers().clone());
            offers_rec.setAdapter(offerAdapter);
        }

        address_name_txt.setText(order.getAddress().getName());
        address_phone_txt.setText(order.getAddress().getPhone());
        if (Common.isArabic)
            address_details_txt.setText(order.getAddress().getFullAddressAR());
        else
            address_details_txt.setText(order.getAddress().getFullAddressEN());


        total_txt.setText(order.getTotalAmount()+" "+getString(R.string.kd));
        delivery_txt.setText(order.getDeliveryCost()+" "+getString(R.string.kd));
        due_amount_txt.setText(order.getDueAmount()+" "+getString(R.string.kd));
        paid_amount_txt.setText(order.getPaidAmount()+" "+getString(R.string.kd));

    }
}
