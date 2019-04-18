package com.vavisa.monasabatcom.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.vavisa.monasabatcom.R;

public class ServiceDetailsFragment extends Fragment implements View.OnClickListener {

  private View fragmentView;
  private SliderLayout serviceImagesLayout;
  private TextView serviceName;
  private TextView servicePrice;
  private TextView serviceDescription;
  private TextView howToServe;
  private TextView serviceTime;
  private TextView requirements;
  private TextView durationOfStay;
  private EditText date;
  private EditText time;
  private EditText extraNotes;
  private ListView serviceExtrasList;
  private CheckBox womenServiceCheck;
  private Button addToCart;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (fragmentView == null) {
      fragmentView = inflater.inflate(R.layout.fragment_service_details, container, false);

      serviceImagesLayout = fragmentView.findViewById(R.id.service_images);
      serviceName = fragmentView.findViewById(R.id.service_name);
      servicePrice = fragmentView.findViewById(R.id.service_price);
      serviceDescription = fragmentView.findViewById(R.id.service_description);
      howToServe = fragmentView.findViewById(R.id.how_to_serve);
      serviceTime = fragmentView.findViewById(R.id.service_time);
      requirements = fragmentView.findViewById(R.id.requirements);
      durationOfStay = fragmentView.findViewById(R.id.duration_of_stay);
      date = fragmentView.findViewById(R.id.date);
      time = fragmentView.findViewById(R.id.time);
      extraNotes = fragmentView.findViewById(R.id.extra_notes);
      serviceExtrasList = fragmentView.findViewById(R.id.extras_list);
      womenServiceCheck = fragmentView.findViewById(R.id.women_service_check);
      addToCart = fragmentView.findViewById(R.id.add_to_cart);

      addToCart.setOnClickListener(this);
    }

    return fragmentView;
  }

  @Override
  public void onClick(View v) {
    switch (v.getId()) {
      case R.id.add_to_cart:
        break;
    }
  }
}
