package com.vavisa.monasabatcom.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Login;
import com.vavisa.monasabatcom.MainActivity;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.CartActivity;
import com.vavisa.monasabatcom.adapter.services_offersAdapters.OffersAdapter;
import com.vavisa.monasabatcom.adapter.services_offersAdapters.ServicesAdapter;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;
import com.vavisa.monasabatcom.models.companyDetails.Photos;
import com.vavisa.monasabatcom.utility.Constants;

import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static com.vavisa.monasabatcom.utility.ListSizeUtility.setListViewHeightBasedOnChildren;

public class CompanyDetailsFragment extends Fragment implements View.OnClickListener {

    private ProgressBar pb;
    private ScrollView scrollView;
    private View fragmentView, service_border_view, offer_border_view;
    private SliderLayout slider;
    private TextView companyName, service_tag, offer_tag;
    private ImageView rating, share, favorite;
    private RatingBar ratingBar;
    private TextView cart_quantity, ratingCount, workingTime, companyDescription;
    private ListView serviceListView, offersListView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private int userId;
    private CompanyDetailsModel companyDetails;
    private ServicesAdapter servicesAdapter;
    private OffersAdapter offersAdapter;

    private int company_id;
    private String tab_tag, searchDate, searchHour;


    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_company_details, container, false);
            reference();

            userId =
                    (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : 0;

            company_id = Integer.parseInt(getArguments().getString("companyId"));
            tab_tag = getArguments().getString("tag");
            searchDate = getArguments().getString("searchDate");
            searchHour = getArguments().getString("searchHour");

        }

        if (Common.isConnectToTheInternet(getContext())) {
            requestData();
        } else {
            AlertDialog.Builder error = new AlertDialog.Builder(getContext());
            error.setMessage(R.string.error_connection);
            AlertDialog dialog = error.create();
            dialog.show();
        }


        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();

        int quantity_int = 0;

        if (Common.cart.getServices().size() > 0)
            quantity_int += Common.cart.getServices().size();

        if (Common.cart.getOffers().size() > 0)
            quantity_int += Common.cart.getOffers().size();


        if (quantity_int > 0) {
            cart_quantity.setVisibility(View.VISIBLE);
            cart_quantity.setText(String.valueOf(quantity_int));
        } else
            cart_quantity.setVisibility(GONE);
    }

    private void requestData() {
        pb.setVisibility(View.VISIBLE);
        compositeDisposable.add(
                Common.getAPI()
                        .getCompanyDetails(company_id, searchDate, searchHour, userId)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                new Consumer<CompanyDetailsModel>() {
                                    @Override
                                    public void accept(CompanyDetailsModel companyDetailsModel) {
                                        pb.setVisibility(GONE);
                                        scrollView.setVisibility(View.VISIBLE);
                                        companyDetails = companyDetailsModel;
                                        try {
                                            bindData();
                                        } catch (Exception e) {
                                        }
                                    }
                                }, new Consumer<Throwable>() {
                                    @Override
                                    public void accept(Throwable throwable) throws Exception {
                                        pb.setVisibility(GONE);
                                        Common.errorAlert(getContext(), getString(R.string.error_occure));
                                    }
                                }));
    }

    private void bindData() {
        setupSlider();
        if (Common.isArabic) {
            companyName.setText(companyDetails.getNameAR());
            companyDescription.setText(Html.fromHtml(companyDetails.getAboutAR()));
        } else {
            companyName.setText(companyDetails.getNameEN());
            companyDescription.setText(Html.fromHtml(companyDetails.getAboutEN()));
        }

        ratingBar.setRating(companyDetails.getRating());
        ratingCount.setText("(" + companyDetails.getRatingCount() + ")");
        workingTime.setText(
                getString(R.string.from)
                        + " "
                        + companyDetails.getWorkingFromTime()
                        + " "
                        + getString(R.string.to)
                        + " "
                        + companyDetails.getWorkingToTime());

        if (companyDetails.getServices().size() == 0) {

            service_border_view.setVisibility(GONE);
            service_tag.setVisibility(GONE);
            serviceListView.setVisibility(GONE);
        } else {

            // send company id used when send order
            servicesAdapter =
                    new ServicesAdapter(
                            getActivity(), android.R.layout.simple_list_item_1,
                            companyDetails.getServices(), String.valueOf(company_id),
                            companyDetails.getNameAR(), companyDetails.getNameEN(),
                            searchDate, searchHour, tab_tag);
            serviceListView.setAdapter(servicesAdapter);
            setListViewHeightBasedOnChildren(serviceListView);
        }


        if (companyDetails.getOffers().size() == 0) {

            offer_border_view.setVisibility(GONE);
            offer_tag.setVisibility(GONE);
            offersListView.setVisibility(GONE);
        } else {
            offersAdapter =
                    new OffersAdapter(getActivity(), android.R.layout.simple_list_item_1,
                            companyDetails.getOffers(), String.valueOf(company_id),
                            companyDetails.getNameAR(), companyDetails.getNameEN(),
                            searchDate, searchHour, tab_tag);
            offersListView.setAdapter(offersAdapter);
            setListViewHeightBasedOnChildren(offersListView);
        }

        Common.cart.setPaymentMethod(companyDetails.getPaymentMethod());
        Common.cart.setTerms_ar(companyDetails.getTermsAndConditionsAR());
        Common.cart.setTerms_en(companyDetails.getTermsAndConditionsEn());
    }

    private void setupSlider() {
        for (Photos photo : companyDetails.getPhoto()) {
            TextSliderView textSliderView = new TextSliderView(getContext());
            textSliderView.image(photo.getPhoto()).empty(R.drawable.placeholder).setScaleType(BaseSliderView.ScaleType.Fit);
            slider.addSlider(textSliderView);
        }

        // slider.setPresetTransformer(SliderLayout.Transformer.Background2Foreground);
        slider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
    }

    private void setShare() {

        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, companyName.getText().toString().trim());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    private void setFavourite() {
        userId =
                (Paper.book("Monasabatcom").contains("currentUser")) ? Common.currentUser.getId() : 0;
        if (userId != 0) {
            if (companyDetails.getFavourite()) {
                favorite.setImageDrawable(
                        getResources().getDrawable(R.drawable.ic_favorite_border_black_24dp));
                companyDetails.setFavourite(false);
            } else {
                favorite.setImageDrawable(
                        getResources().getDrawable(R.drawable.ic_favorite_black_24dp));
                companyDetails.setFavourite(true);
            }
            compositeDisposable.add(
                    Common.getAPI()
                            .markAsFavorite(userId, companyDetails.getId())
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<Integer>() {
                                        @Override
                                        public void accept(Integer integer) throws Exception {
                                            if (integer > 0) {

                                            }
                                        }
                                    }));
        } else {
            getActivity().startActivity(new Intent(getContext(), Login.class));
        }
    }

    private void showRatingDialog() {

        if (Paper.book("Monasabatcom").contains("currentUser")) {
            Fragment rating = new Rating();
            Bundle bundle = new Bundle();
            bundle.putString("com_id", String.valueOf(companyDetails.getId()));
            bundle.putString("com_name", (Common.isArabic) ? companyDetails.getNameAR() : companyDetails.getNameEN());
            bundle.putString("com_image", companyDetails.getLogo());

            rating.setArguments(bundle);

            ((MainActivity) getActivity()).pushFragments(Constants.TAB_HOME, rating, true);
        } else
            getActivity().startActivity(new Intent(getContext(), Login.class));


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.back_button:
                getActivity().onBackPressed();
                break;
            case R.id.cart_icon:
                getContext().startActivity(new Intent(getContext(), CartActivity.class));
                break;
            case R.id.ic_rating:
                showRatingDialog();
                break;
            case R.id.ic_fav:
                setFavourite();
                break;
            case R.id.ic_share:
                setShare();
                break;
        }
    }

    private void reference() {
        Toolbar toolbar = fragmentView.findViewById(R.id.toolBar);
        ImageView backButton = toolbar.findViewById(R.id.back_button);
        ImageView cart = toolbar.findViewById(R.id.cart_icon);
        cart_quantity = toolbar.findViewById(R.id.cart_quantity);

        pb = fragmentView.findViewById(R.id.pb);
        scrollView = fragmentView.findViewById(R.id.scroll);
        slider = fragmentView.findViewById(R.id.slider);
        companyName = fragmentView.findViewById(R.id.company_name);
        rating = fragmentView.findViewById(R.id.ic_rating);
        share = fragmentView.findViewById(R.id.ic_share);
        favorite = fragmentView.findViewById(R.id.ic_fav);
        ratingBar = fragmentView.findViewById(R.id.rating);
        ratingCount = fragmentView.findViewById(R.id.rating_count);
        workingTime = fragmentView.findViewById(R.id.working_time);
        companyDescription = fragmentView.findViewById(R.id.about);
        service_tag = fragmentView.findViewById(R.id.services_tag);
        service_border_view = fragmentView.findViewById(R.id.view2);
        serviceListView = fragmentView.findViewById(R.id.service_list);
        offer_border_view = fragmentView.findViewById(R.id.view4);
        offer_tag = fragmentView.findViewById(R.id.offers_tag);
        offersListView = fragmentView.findViewById(R.id.offers_list);

        backButton.setOnClickListener(this);
        cart.setOnClickListener(this);
        rating.setOnClickListener(this);
        share.setOnClickListener(this);
        favorite.setOnClickListener(this);

        if (Common.isArabic)
            backButton.setRotation(180);

    }
}
