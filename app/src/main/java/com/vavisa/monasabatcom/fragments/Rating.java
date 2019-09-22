package com.vavisa.monasabatcom.fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class Rating extends Fragment {

    @OnClick(R.id.back_button)
    public void back() {
        getActivity().onBackPressed();
    }

    @BindView(R.id.rating_bar)
    RatingBar ratingBar;
    @BindView(R.id.com_image)
    ImageView com_image_view;
    @BindView(R.id.com_name)
    TextView com_name_txt;
    @BindView(R.id.rate_button)
    Button rating_btn;

    String com_id, com_name, com_image, comment = "";
    float rating_value;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_rating, container, false);
        ButterKnife.bind(this, view);

        addListenerOnRatingBar();

        com_id = getArguments().getString("com_id");
        com_name = getArguments().getString("com_name");
        com_image = getArguments().getString("com_image");
        com_name_txt.setText(com_name);

        Picasso.with(getContext()).load(com_image).placeholder(R.drawable.placeholder).into(com_image_view);
        return view;
    }

    private void addListenerOnRatingBar() {

        //if rating value is changed,
        //display the current rating value in the result (textview) automatically
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            public void onRatingChanged(RatingBar ratingBar, float rating,
                                        boolean fromUser) {

                if (rating == 0) {
                    rating_btn.setAlpha(0.5f);
                    rating_btn.setEnabled(false);
                } else {
                    rating_btn.setAlpha(1.0f);
                    rating_btn.setEnabled(true);
                    rating_value = rating;
                }
            }
        });
    }

    @OnClick(R.id.rate_button)
    public void rateFun() {
        compositeDisposable.add(Common.getAPI().rating(Common.currentUser.getId(), Integer.parseInt(com_id), rating_value, comment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(integer -> {
                    if (integer == 1)
                        showMessage();
                    else
                        Toast.makeText(getContext(), getString(R.string.error_occure), Toast.LENGTH_SHORT).show();
                }, throwable -> Common.errorAlert(getContext(), getString(R.string.error_occure))));
    }

    private void showMessage() {

        final Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_error_alert);
        dialog.setCancelable(false);

        TextView message = dialog.findViewById(R.id.alert_message);
        TextView ok = dialog.findViewById(R.id.ok);

        message.setText(getString(R.string.thanks_rating));

        ok.setOnClickListener(v -> {
            dialog.dismiss();
            getActivity().onBackPressed();
        });

        dialog.show();

    }


}
