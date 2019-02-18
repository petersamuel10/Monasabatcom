package com.vavisa.monasabatcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

public class Register extends AppCompatActivity {

    private User newUser;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    String deviceToken;
    ProgressDialog progressDialog;

    @BindView(R.id.fullName_reg)
    EditText full_name;
    @BindView(R.id.email_reg)
    EditText email;
    @BindView(R.id.mobile_reg)
    EditText mobile;
    @BindView(R.id.password_reg)
    EditText password;
    @BindView(R.id.confirm_password_reg)
    EditText confirm_password;
    @BindView(R.id.btnRegister)
    Button register;
    @BindView(R.id.have_account)
    TextView have_an_account;
    @OnClick(R.id.have_account)
    public void onclick()
    {
        startActivity(new Intent(this,Login.class));
    }
    @OnClick(R.id.btnRegister)
    public void registerClick()
    {
        if(Common.isConnectToTheInternet(getBaseContext())) {

            newUser = new User(full_name.getText().toString(),
                    email.getText().toString(),
                    mobile.getText().toString(),
                    password.getText().toString(),
                    deviceToken);

            if (validateRegister(newUser, confirm_password.getText().toString())) {
                register();
            }
        }else
        {
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
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        deviceToken = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

    }

    public boolean validateRegister(User user,String confirm){

        if(user.getName() == null || user.getName().trim().length()<3 ){
            Toast.makeText(this,R.string.invalid_name, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getPassword() == null || user.getPassword().trim().length() == 0){
            Toast.makeText(this, R.string.error_invalid_password, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(confirm==null || !user.getPassword().contentEquals(confirm))
        {
            Toast.makeText(this, R.string.not_match, Toast.LENGTH_SHORT).show();
            return false;
        }

        if(user.getEmail()==null || !android.util.Patterns.EMAIL_ADDRESS.matcher(user.getEmail()).matches())
        {
            Toast.makeText(this,R.string.error_invalid_email, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(user.getMobile() == null)
        {
            Toast.makeText(this,R.string.mobile_missing, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void register() {
        progressDialog.show();
        compositeDisposable.add(Common.getAPI().register(newUser)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Integer>() {
                                    @Override
                                    public void accept(Integer integer) throws Exception {
                                        if(integer>0) {
                                            Common.currentUser = newUser;
                                            Common.currentUser.setId(integer);
                                            requestProfileInfo();
                                            if(Common.booking)
                                                startActivity(new Intent(getBaseContext(),MainActivity.class));
                                            else
                                                startActivity(new Intent(Register.this, MainActivity.class));
                                            progressDialog.dismiss();
                                            finish();

                                          //  progressDialog.dismiss();
                                          //  startActivity(new Intent(Register.this, Login.class));
                                        }
                                         else if(integer==-5){
                                            progressDialog.dismiss();
                                            Toast.makeText(Register.this,getResources().getString(R.string.email_reg_before) , Toast.LENGTH_SHORT).show();}
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
