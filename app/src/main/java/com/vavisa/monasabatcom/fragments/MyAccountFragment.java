package com.vavisa.monasabatcom.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.Login;
import com.vavisa.monasabatcom.ProfileFragments.Addresses;
import com.vavisa.monasabatcom.ProfileFragments.Appointment;
import com.vavisa.monasabatcom.ProfileFragments.ChangePassword;
import com.vavisa.monasabatcom.ProfileFragments.Language;
import com.vavisa.monasabatcom.ProfileFragments.ProfileDetails;
import com.vavisa.monasabatcom.ProfileFragments.Settings;
import com.vavisa.monasabatcom.ProfileFragments.Support;
import com.vavisa.monasabatcom.ProfileFragments.Terms_conditions;
import com.vavisa.monasabatcom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

public class MyAccountFragment extends Fragment implements View.OnClickListener {

    @BindView(R.id.userInfoWidget)
    LinearLayout userInfo;
    @BindView(R.id.userName)
    TextView user_name;
    @BindView(R.id.emailTxt)
    TextView email;
    @BindView(R.id.appointment)
    LinearLayout appointment;
    @BindView(R.id.profile_details)
    LinearLayout profileDetails;
    @BindView(R.id.addresses)
    LinearLayout addresses;
    @BindView(R.id.language)
    LinearLayout language;
    @BindView(R.id.setting)
    LinearLayout settings;
    @BindView(R.id.conditions)
    LinearLayout conditions;
    @BindView(R.id.support)
    LinearLayout support;
    @BindView(R.id.change_pass)
    LinearLayout change_password;
    @BindView(R.id.login_logout)
    TextView login_logout;
    @BindView(R.id.l1)
    ImageView l1;
    @BindView(R.id.l2)
    ImageView l2;
    @BindView(R.id.l3)
    ImageView l3;
    @BindView(R.id.l4)
    ImageView l4;
    @BindView(R.id.l5)
    ImageView l5;
    @BindView(R.id.l6)
    ImageView l6;
    @BindView(R.id.l7)
    ImageView l7;
    @BindView(R.id.l8)
    ImageView l8;
    @BindView(R.id.l9)
    ImageView l9;
    @BindView(R.id.ic_login)
    ImageView loginBtn;

    @Override
    public void onAttachFragment(Fragment childFragment) {
        super.onAttachFragment(childFragment);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(Common.isArabic)
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Changa-Regular.ttf")
                    .setFontAttrId(R.attr.fontPath).build());
        }else
        {
            CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                    .setDefaultFontPath("fonts/Avenir.otf")
                    .setFontAttrId(R.attr.fontPath).build());
        }
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_account, container, false);

        ButterKnife.bind(this, view);
        //check if there is current user
        if (Paper.book("Monasabatcom").contains("currentUser")) {
            bindData();
            userInfo.setVisibility(View.VISIBLE);
            change_password.setVisibility(View.VISIBLE);
            support.setVisibility(View.VISIBLE);
            login_logout.setText(R.string.logout);
            loginBtn.setImageDrawable(getResources().getDrawable(R.drawable.ic_logout));

            //login_logout.setTextColor(getResources().getColor(R.color.colorAccent));
        }

        if(Common.isArabic)
            changeArrow();


        profileDetails.setOnClickListener(this);
        appointment.setOnClickListener(this);
        addresses.setOnClickListener(this);
        language.setOnClickListener(this);
        settings.setOnClickListener(this);
        conditions.setOnClickListener(this);
        support.setOnClickListener(this);
        change_password.setOnClickListener(this);
        login_logout.setOnClickListener(this);
        return view;
    }

    private void changeArrow() {
        l1.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l2.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l3.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l4.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l5.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l6.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l7.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l8.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
        l9.setImageDrawable(getResources().getDrawable(R.drawable.ic_arrow_left_black_24dp));
    }

    private void bindData() {
        user_name.setText(Common.currentUser.getName());
        email.setText(Common.currentUser.getEmail());
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.appointment:
                selectOption(new Appointment());
                break;

            case R.id.profile_details:
                selectOption(new ProfileDetails());
                break;

            case R.id.addresses:
                selectOption(new Addresses());
                break;

            case R.id.setting:
                selectOption(new Settings());
                break;

            case R.id.language:
                selectOption(new Language());
                break;

            case R.id.conditions:
                selectOption(new Terms_conditions());
                break;

            case R.id.support:
                selectOption(new Support());
                break;

            case R.id.change_pass:
                selectOption(new ChangePassword());
                break;

            case R.id.login_logout: {
                if (login_logout.getText() == getString(R.string.logout)) {
                    Paper.book("Monasabatcom").delete("currentUser");
                    userInfo.setVisibility(View.GONE);
                    change_password.setVisibility(View.GONE);
                    support.setVisibility(View.GONE);
                    login_logout.setText(R.string.login);
                   // login_logout.setTextColor(getResources().getColor(R.color.colorPrimary));
                   } else {
                          startActivity(new Intent(getContext(), Login.class));}
                 }
                 break;
        }
    }

    public void selectOption(Fragment  fragment)
    {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_fragment, fragment).addToBackStack(null).commit();
    }

}