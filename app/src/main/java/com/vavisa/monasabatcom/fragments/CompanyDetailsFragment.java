package com.vavisa.monasabatcom.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.adapter.services_offersAdapters.ServicesAdapter;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;
import com.vavisa.monasabatcom.models.companyDetails.Photos;

import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static com.vavisa.monasabatcom.utility.ListSizeUtility.setListViewHeightBasedOnChildren;

public class CompanyDetailsFragment extends Fragment implements View.OnClickListener {

  private View fragmentView;
  private SliderLayout slider;
  private TextView companyName;
  private ImageView rating;
  private ImageView share;
  private ImageView favorite;
  private RatingBar ratingBar;
  private TextView ratingCount;
  private TextView workingTime;
  private TextView companyDescription;
  private ListView serviceListView;
  private CompositeDisposable compositeDisposable = new CompositeDisposable();
  private ProgressDialog progressDialog;
  private int userId;
  private CompanyDetailsModel companyDetails;
  private ServicesAdapter servicesAdapter;

  @Nullable
  @Override
  public View onCreateView(
      @NonNull LayoutInflater inflater,
      @Nullable ViewGroup container,
      @Nullable Bundle savedInstanceState) {

    if (fragmentView == null) {
      fragmentView = inflater.inflate(R.layout.fragment_company_details, container, false);

      Toolbar toolbar = fragmentView.findViewById(R.id.toolBar);
      ImageView backButton = toolbar.findViewById(R.id.back_button);

      slider = fragmentView.findViewById(R.id.slider);
      companyName = fragmentView.findViewById(R.id.company_name);
      rating = fragmentView.findViewById(R.id.ic_rating);
      share = fragmentView.findViewById(R.id.ic_share);
      favorite = fragmentView.findViewById(R.id.ic_fav);
      ratingBar = fragmentView.findViewById(R.id.rating);
      ratingCount = fragmentView.findViewById(R.id.rating_count);
      workingTime = fragmentView.findViewById(R.id.working_time);
      companyDescription = fragmentView.findViewById(R.id.about);
      serviceListView = fragmentView.findViewById(R.id.service_list);

      progressDialog = new ProgressDialog(getActivity());

      backButton.setOnClickListener(this);
      rating.setOnClickListener(this);
      share.setOnClickListener(this);
      favorite.setOnClickListener(this);
    }

    if (Common.isConnectToTheInternet(getContext())) {
      requestData();
    } else {
      AlertDialog.Builder error = new AlertDialog.Builder(getContext());
      error.setMessage(R.string.error_connection);
      AlertDialog dialog = error.create();
      dialog.show();
    }

    userId =
        (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : 0;

    return fragmentView;
  }

  private void requestData() {
    progressDialog.show();
    compositeDisposable.add(
        Common.getAPI()
            .getCompanyDetails(Common.companyId,userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                new Consumer<CompanyDetailsModel>() {
                  @Override
                  public void accept(CompanyDetailsModel companyDetailsModel) {
                    progressDialog.dismiss();
                    companyDetails = companyDetailsModel;
                    bindData();
                  }
                }));
  }

  private void bindData() {
    setupSlider();
    if (Common.isArabic) {
      companyName.setText(companyDetails.getNameAR());
      companyDescription.setText(companyDetails.getAboutAR());
    } else {
      companyName.setText(companyDetails.getNameEN());
      companyDescription.setText(companyDetails.getAboutEN());
    }

    ratingBar.setRating(companyDetails.getRating());
    ratingCount.setText("(" + companyDetails.getRatingCount() + ")");
    workingTime.setText(
        getResources().getString(R.string.from)
            + " "
            + companyDetails.getWorkingFromTime()
            + " "
            + getResources().getString(R.string.to)
            + " "
            + companyDetails.getWorkingToTime());

    servicesAdapter =
        new ServicesAdapter(
            getActivity(), android.R.layout.simple_list_item_1, companyDetails.getServices());
    serviceListView.setAdapter(servicesAdapter);
    setListViewHeightBasedOnChildren(serviceListView);
  }

  private void setupSlider() {
    for (Photos photo : companyDetails.getPhoto()) {
      TextSliderView textSliderView = new TextSliderView(getContext());
      textSliderView.image(photo.getPhoto()).setScaleType(BaseSliderView.ScaleType.Fit);
      slider.addSlider(textSliderView);
    }

    // slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
    slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
  }

  @Override
  public void onClick(View v) {

    switch (v.getId()) {
      case R.id.back_button:
        getActivity().onBackPressed();
        break;

      case R.id.ic_rating:
        break;

      case R.id.ic_fav:
        break;

      case R.id.ic_share:
        break;
    }
  }
}
