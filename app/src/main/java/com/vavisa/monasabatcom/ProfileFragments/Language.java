package com.vavisa.monasabatcom.ProfileFragments;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;

/**
 * A simple {@link Fragment} subclass.
 */
public class Language extends Fragment implements View.OnClickListener {

    @BindView(R.id.english)
    RelativeLayout english;
    @BindView(R.id.true_english)
    ImageView true_english;
    @BindView(R.id.arabic)
    RelativeLayout arabic;
    @BindView(R.id.true_arabic)
    ImageView true_arabic;
    @BindView(R.id.back)
    LinearLayout back;
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
        View view = inflater.inflate(R.layout.profile_language, container, false);

        ButterKnife.bind(this, view);

        Paper.init(getContext());

        if(Common.isArabic) {
            true_arabic.setVisibility(View.VISIBLE);
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));
        }
        else
            true_english.setVisibility(View.VISIBLE);

        english.setOnClickListener(this);
        arabic.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.english:
                true_english.setVisibility(View.VISIBLE);
                true_arabic.setVisibility(View.GONE);
                Paper.book("Monasabatcom").write("language","en");
                Common.isArabic =false;
                break;

            case R.id.arabic:
                true_arabic.setVisibility(View.VISIBLE);
                true_english.setVisibility(View.GONE);
                Paper.book("Monasabatcom").write("language","ar");
                Common.isArabic =true;
                break;
        }

        Intent i = getContext().getPackageManager()
                .getLaunchIntentForPackage( getContext().getPackageName() );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().finish();
        startActivity(i);

    }

}

















    /*
    private static Locale sLocale;

    public static void setLocale(Locale locale) {
        sLocale = locale;
        if(sLocale != null) {
            Locale.setDefault(sLocale);
        }
    }



    public static void updateConfig(ContextThemeWrapper wrapper) {
        if(sLocale != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Configuration configuration = new Configuration();
            configuration.setLocale(sLocale);
            wrapper.applyOverrideConfiguration(configuration);
        }
    }

    public static void updateConfig(Application app, Configuration configuration) {
        if(sLocale != null && Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
            //Wrapping the configuration to avoid Activity endless loop
            Configuration config = new Configuration(configuration);
            config.locale = sLocale;
            Resources res = app.getBaseContext().getResources();
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
    }
*/

