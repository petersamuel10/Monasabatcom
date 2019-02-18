package com.vavisa.monasabatcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.models.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Login extends AppCompatActivity {

    private User user;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    String deviceToken;
    ProgressDialog progressDialog;

    @BindView(R.id.email_log)
    EditText email;
    @BindView(R.id.password_log)
    EditText password;
    @BindView(R.id.btnLogin)
    Button loginBtn;
    @OnClick(R.id.forgetPassword)
    public void forgetPassword(){startActivity(new Intent(this,ForgetPassword.class));}
    @OnClick(R.id.notRegister)
    public void registerPage(){
        startActivity(new Intent(this,Register.class));
    }
    @OnClick(R.id.btnLogin)
    public void loginClick()
    {
        if (Common.isConnectToTheInternet(getBaseContext())) {
            if(!TextUtils.isEmpty(email.getText())&&!TextUtils.isEmpty(password.getText())) {
                user = new User(email.getText().toString(),
                        password.getText().toString(), deviceToken);

                login(user);
            }else
                Toast.makeText(Login.this, getResources().getString(R.string.enter_email_password), Toast.LENGTH_SHORT).show();

        }else{
            AlertDialog.Builder error = new AlertDialog.Builder(this);
            error.setMessage(R.string.error_connection);
            AlertDialog dialog = error.create();
            dialog.show();

        }

    }

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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        //save userId
        Paper.init(this);

        deviceToken = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

    }

    private void login(final User user) {

        progressDialog.show();
            compositeDisposable.add(Common.getAPI().login(user)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(Integer integer) throws Exception {
                            if (integer > 0) {
                                Common.currentUser = user;
                                Common.currentUser.setId(integer);
                                requestProfileInfo();
                                if(Common.booking) {
                                    Common.booking =false;
                                    onBackPressed();
                                }
                                else
                                    startActivity(new Intent(Login.this, MainActivity.class));
                                progressDialog.dismiss();
                                finish();
                            } else if(integer==-1){
                                progressDialog.dismiss();
                                password.setText("");
                                Toast.makeText(Login.this, getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();}
                                else if(integer==-2){
                                progressDialog.dismiss();
                                password.setText("");
                                Toast.makeText(Login.this, getResources().getString(R.string.error_incorrect_password), Toast.LENGTH_SHORT).show();
                            }else
                            {
                                progressDialog.dismiss();
                                password.setText("");
                                email.setText("");
                                Toast.makeText(Login.this, getResources().getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show();
                            }

                        }
                    }));

    }

    public void requestProfileInfo() {

        compositeDisposable.add(Common.getAPI().getProfile(Common.currentUser.getId(),Common.currentUser.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<User>() {
                    @Override
                    public void accept(User user) throws Exception {
                        Common.currentUser.setName(user.getName());
                        Common.currentUser.setMobile(user.getMobile());
                        Paper.book("Monasabatcom").write("currentUser",Common.currentUser);
                    }
                }));
    }

}
