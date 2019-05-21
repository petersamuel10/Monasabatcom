package com.vavisa.monasabatcom.ProfileFragments;


import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.PageData;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class Terms_conditions extends Fragment {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.wb_terms)
    WebView webView_terms;
    @BindView(R.id.arrow)
    ImageView arrowAr;

    @OnClick(R.id.back)
    public void setBack() {
        getActivity().onBackPressed();
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_terms_conditions, container, false);
        ButterKnife.bind(this, view);

        if (Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        getData();

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Common.isArabic)
            setLanguage("ar");
        else
            setLanguage("en");
    }

    private void getData() {
        if (Common.isConnectToTheInternet(getActivity())) {
            pb.setVisibility(View.VISIBLE);
            compositeDisposable.add(Common.getAPI().getPages(1)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<PageData>() {
                        @Override
                        public void accept(PageData pageData) throws Exception {
                            bindData(pageData);
                            pb.setVisibility(View.GONE);
                        }
                    }));
        } else
            errorConnectionMess();
    }

    private void bindData(PageData pageData) {
        if (Common.isArabic) {
            String head = "<head><style type='text/css'>@font-face {font-family: 'arial';src: url('file:///android_asset/AvenirLTStd-Book.otf');}body {font-family: 'verdana';text-align: justify;background-color:#00000000;}</style></head>";
            String myHtmlString = "<html>" + head +
                    "<body style=\"font-family: arial\">" + pageData.getContentAR() + "</body></html>";
            webView_terms.loadDataWithBaseURL("", myHtmlString, "text/html", "utf-8", "");
        } else {
            String head = "<head><style type='text/css'>@font-face {font-family: 'arial';src: url('file:///android_asset/AvenirLTStd-Book.otf');}body {font-family: 'verdana';text-align: justify;background-color:#00000000;}</style></head>";
            String myHtmlString = "<html>" + head +
                    "<body style=\"font-family: arial\">" + pageData.getContentEN() + "</body></html>";
            webView_terms.loadDataWithBaseURL("", myHtmlString, "text/html", "utf-8", "");
        }
    }

    public void errorConnectionMess() {

        AlertDialog.Builder error = new AlertDialog.Builder(getActivity());
        error.setMessage(R.string.error_connection);
        AlertDialog dialog = error.create();
        dialog.show();
    }

    public void setLanguage(String lang)
    {
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale= locale;
        getContext().getResources().updateConfiguration(config,getContext().getResources().getDisplayMetrics());
    }
}
