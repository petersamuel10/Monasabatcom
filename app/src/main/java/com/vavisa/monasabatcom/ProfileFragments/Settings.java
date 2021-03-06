package com.vavisa.monasabatcom.ProfileFragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Settings extends Fragment {

    @BindView(R.id.switchBtn)
    SwitchCompat chk_notice;
    @BindView(R.id.arrow)
    ImageView arrowAr;

    @OnClick(R.id.back)
    public void setBack() {
        getActivity().onBackPressed();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_settings, container, false);

        ButterKnife.bind(this, view);
        if (Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
        boolean isEnabled = status.getPermissionStatus().getEnabled();
        boolean isSubscribed = status.getSubscriptionStatus().getSubscribed();
        if (isSubscribed)
            chk_notice.setChecked(true);
        else
            chk_notice.setChecked(false);

        return view;
    }

    @OnClick(R.id.switchBtn)
    public void onChecked() {
        if (chk_notice.isChecked()) {
            OneSignal.setSubscription(true);
        } else {
            OneSignal.setSubscription(false);
        }
    }

}
