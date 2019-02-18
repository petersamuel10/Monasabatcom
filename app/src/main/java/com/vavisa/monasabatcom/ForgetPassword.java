package com.vavisa.monasabatcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vavisa.monasabatcom.Common.Common;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ForgetPassword extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.email_forget)
    EditText email;
    @BindView(R.id.btnForget)
    Button forget;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressDialog progressDialog;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        setContentView(R.layout.activity_forget_password);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        forget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (!TextUtils.isEmpty(email.getText())) {
            progressDialog.show();
            compositeDisposable.add(Common.getAPI().forgotPassword(email.getText().toString(), "ar")
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            if (integer == 1)
                                Toast.makeText(ForgetPassword.this, R.string.send_link, Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(ForgetPassword.this, R.string.no_user_email, Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }));
        }else
            Toast.makeText(ForgetPassword.this, R.string.please_enter_email, Toast.LENGTH_SHORT).show();

    }
}
