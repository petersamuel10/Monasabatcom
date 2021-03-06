package com.vavisa.monasabatcom.ProfileFragments;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ChangePassword extends Fragment implements View.OnClickListener {

    @BindView(R.id.old_password)
    EditText oldPasswordEt;
    @BindView(R.id.new_password)
    EditText newPasswordEt;
    @BindView(R.id.confirm_new_password)
    EditText confirm_passwordEt;
    @BindView(R.id.change_password)
    Button changeBtn;
    @BindView(R.id.arrow)
    ImageView arrowAr;

    @OnClick(R.id.back)
    public void setBack() {
        getActivity().onBackPressed();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    ProgressDialog progressDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_change_password, container, false);

        ButterKnife.bind(this, view);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        if (Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        changeBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {

        if (Common.isConnectToTheInternet(getContext())) {
            String oldPassword, newPassword, confirm;
            oldPassword = oldPasswordEt.getText().toString();
            newPassword = newPasswordEt.getText().toString();
            confirm = confirm_passwordEt.getText().toString();

            if (validatePassword(oldPassword, newPassword, confirm)) {
                changePassword(oldPassword, newPassword);
            }
        } else
            Common.errorAlert(getContext(), getString(R.string.error_connection));
    }

    private void changePassword(String oldPassword, String newPassword) {
        progressDialog.show();
        compositeDisposable.add(Common.getAPI().changePassword(Common.currentUser.getId(), oldPassword, newPassword)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        progressDialog.dismiss();

                        if (integer > 0) {
                            Toast.makeText(getContext(), R.string.change_password_successfully, Toast.LENGTH_SHORT).show();
                            getActivity().onBackPressed();
                        } else if (integer == -4) {
                            Common.errorAlert(getContext(), getString(R.string.incorrect_password));
                            oldPasswordEt.setText("");
                        } else if (integer == -3)
                            Common.errorAlert(getContext(), getString(R.string.no_user_email));
                        else
                            Common.errorAlert(getContext(), getString(R.string.error_occure));
                    }
                }));
    }

    private boolean validatePassword(String oldPassword, String password, String confirm) {

        if (oldPassword == null || oldPassword.trim().length() == 0) {
            Toast.makeText(getContext(), R.string.error_invalid_password, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (password == null || password.trim().length() == 0) {
            Toast.makeText(getContext(), R.string.error_invalid_password, Toast.LENGTH_SHORT).show();
            return false;
        }

        if (confirm == null || !password.contentEquals(confirm)) {
            Toast.makeText(getContext(), R.string.not_match, Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
