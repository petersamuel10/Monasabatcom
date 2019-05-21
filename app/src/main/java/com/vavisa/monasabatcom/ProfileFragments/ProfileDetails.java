package com.vavisa.monasabatcom.ProfileFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.paperdb.Paper;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class ProfileDetails extends Fragment implements View.OnClickListener {

    @BindView(R.id.fullName_ed)
    EditText edtName;
    @BindView(R.id.email_ed)
    EditText edtEmail;
    @BindView(R.id.mobile_ed)
    EditText edtMobile;
    @BindView(R.id.update)
    Button updateBtn;
    @BindView(R.id.arrow)
    ImageView arrowAr;

    @OnClick(R.id.back)
    public void setBack() {
        getActivity().onBackPressed();
    }

    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    String name, email, mobile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.profile_details, container, false);

        ButterKnife.bind(this, view);
        if (Common.isArabic)
            arrowAr.setImageDrawable(getResources().getDrawable(R.drawable.arrow_right_white_24dp));

        edtName.setText(Common.currentUser.getName());
        edtEmail.setText(Common.currentUser.getEmail());
        edtMobile.setText(Common.currentUser.getMobile());

        updateBtn.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        name = edtName.getText().toString();
        email = edtEmail.getText().toString();
        mobile = edtMobile.getText().toString();

        if (validate(name, email)) {
            if (Common.isConnectToTheInternet(getContext())) {
                update(Common.currentUser.getId(), name, email, mobile);
            } else {
                AlertDialog.Builder error = new AlertDialog.Builder(getContext());
                error.setMessage(R.string.error_connection);
                AlertDialog dialog = error.create();
                dialog.show();
            }
        }
    }

    private void updateProfileView(String name, String email, String mobile) {
        Common.currentUser.setName(name);
        Common.currentUser.setEmail(email);
        Common.currentUser.setMobile(mobile);
        Paper.book("Monasabatcom").delete("currentUser");
        Paper.book("Monasabatcom").write("currentUser", Common.currentUser);
    }

    private void update(Integer id, final String name, final String email, final String mobile) {
        compositeDisposable.add(Common.getAPI().editProfile(id, name, email, mobile)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer > 0) {
                            updateProfileView(name, email, mobile);
                            getActivity().onBackPressed();
                        } else if (integer == -5)
                            Common.errorAlert(getContext(),getString(R.string.user_not_exist));
                        else if (integer == -5)
                            Common.errorAlert(getContext(),getString(R.string.email_reg_before));
                    }
                }));
    }

    private boolean validate(String name, String email) {
        if (name == null || name.trim().length() < 3) {
            Toast.makeText(getContext(), getResources().getString(R.string.enter_user_name), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (email == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(getContext(), getResources().getString(R.string.error_invalid_email), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
