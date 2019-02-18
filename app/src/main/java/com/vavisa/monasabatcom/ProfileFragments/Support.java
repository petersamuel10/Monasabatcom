package com.vavisa.monasabatcom.ProfileFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.models.PageData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * A simple {@link Fragment} subclass.
 */
public class Support extends Fragment {

    @BindView(R.id.wb_terms)
    WebView webView_terms;
    @BindView(R.id.arrow)
    ImageView arrowAr;
    @OnClick(R.id.back)
    public void setBack()
    {getActivity().onBackPressed();}

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressDialog progressDialog;


    public Support() {
        // Required empty public constructor
    }

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
        View view = inflater.inflate(R.layout.profile_support, container, false);
        ButterKnife.bind(this,view);
        progressDialog = new ProgressDialog(getActivity());
        if(Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        getData();

        return view;
    }

    private void getData() {
        if(Common.isConnectToTheInternet(getActivity())) {
            progressDialog.show();

            compositeDisposable.add(Common.getAPI().getPages(2)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<PageData>() {
                        @Override
                        public void accept(PageData pageData) throws Exception {
                            bindData(pageData);
                            progressDialog.dismiss();
                        }
                    }));
        }else
            errorConnectionMess();
    }

    private void bindData(PageData pageData) {
        if(Common.isArabic){
            String head = "<head><style type='text/css'>@font-face {font-family: 'arial';src: url('file:///android_asset/AvenirLTStd-Book.otf');}body {font-family: 'verdana';text-align: justify;background-color:#00000000;}</style></head>";
            String myHtmlString = "<html>" + head +
                    "<body style=\"font-family: arial\">" + pageData.getContentAR() + "</body></html>";
            webView_terms.loadDataWithBaseURL("", myHtmlString, "text/html", "utf-8", "");
        }else {
            String head = "<head><style type='text/css'>@font-face {font-family: 'arial';src: url('file:///android_asset/AvenirLTStd-Book.otf');}body {font-family: 'verdana';text-align: justify;background-color:#00000000;}</style></head>";
            String myHtmlString = "<html>" + head +
                    "<body style=\"font-family: arial\">" + pageData.getContentEN() + "</body></html>";
            webView_terms.loadDataWithBaseURL("", myHtmlString, "text/html", "utf-8", "");
        }
    }

    public void errorConnectionMess(){

        AlertDialog.Builder error = new AlertDialog.Builder(getActivity());
        error.setMessage(R.string.error_connection);
        AlertDialog dialog = error.create();
        dialog.show();
    }

}
