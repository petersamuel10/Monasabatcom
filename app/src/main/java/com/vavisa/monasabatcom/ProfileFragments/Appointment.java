package com.vavisa.monasabatcom.ProfileFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.profileAdpaters.AppointmentAdapter;
import com.vavisa.monasabatcom.models.profile.AppointmentModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Appointment extends Fragment {

    @BindView(R.id.appointment_rec)
    RecyclerView appointment_rec;
    @BindView(R.id.appointment_progress)
    ProgressBar progressBar;
    @BindView(R.id.arrow)
    ImageView arrowAr;
    @OnClick(R.id.back)
    public void setBack()
    {getActivity().onBackPressed();}

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    AppointmentAdapter adapter;
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.profile_appointment, container, false);

        ButterKnife.bind(this,view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        if(Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        setupRecyclerView();
        if(Common.isConnectToTheInternet(getContext())){
            requestData();
        } else
        {
            AlertDialog.Builder error = new AlertDialog.Builder(getContext());
            error.setMessage(R.string.error_connection);
            AlertDialog dialog = error.create();
            dialog.show();
        }

    return view;
    }

    private void requestData() {
        progressDialog.show();
        compositeDisposable.add(Common.getAPI().getUserOrders(Common.currentUser.getId())
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<ArrayList<AppointmentModel>>() {
                                    @Override
                                    public void accept(ArrayList<AppointmentModel> appointments) throws Exception {
                                        adapter.addAppointment(appointments);
                                        adapter.notifyDataSetChanged();
                                        progressDialog.dismiss();
                                    }
                                }));
    }

    private void setupRecyclerView() {

        appointment_rec.setHasFixedSize(true);
        appointment_rec.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new AppointmentAdapter(getActivity());
        LayoutAnimationController controller = AnimationUtils.loadLayoutAnimation(appointment_rec.getContext(),R.anim.layout_fall_down);
        appointment_rec.setLayoutAnimation(controller);
        appointment_rec.setAdapter(adapter);
    }

}
