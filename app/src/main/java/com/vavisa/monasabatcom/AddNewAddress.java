package com.vavisa.monasabatcom;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.models.profile.Address;
import com.vavisa.monasabatcom.models.profile.City;
import com.vavisa.monasabatcom.models.profile.Governorate;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddNewAddress extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {

    @BindView(R.id.address_name)
    EditText address_name;
    @BindView(R.id.address_phone)
    EditText address_phone;
    @BindView(R.id.govenorate)
    Spinner governorate;
    @BindView(R.id.city)
    Spinner city;
    @BindView(R.id.address_block)
    EditText address_block;
    @BindView(R.id.address_street)
    EditText address_street;
    @BindView(R.id.address_building)
    EditText address_building;
    @BindView(R.id.address_floor)
    EditText address_floor;
    @BindView(R.id.address_apartment)
    EditText address_apartment;
    @BindView(R.id.save_address)
    Button save_address;
    @OnClick(R.id.cancel)
    public void getBack(){finish();}

    List<String> governorateList = new ArrayList<>() ;
    List<String> citiesList;
    List<City> allCitiesList;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    String name,phone,block,street,building,floor,apartment;

    int userId,cityId;
    Address address,editAddress;
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
        setContentView(R.layout.activity_add_new_address);

        ButterKnife.bind(this);
        progressDialog = new ProgressDialog(getBaseContext());
        progressDialog.setCancelable(false);

        if(Common.isConnectToTheInternet(this))
        {
            getGovernorates();
            getAllCities();
        }
        else
            errorConnectionMess();

        governorate.setOnItemSelectedListener(this);

        save_address.setOnClickListener(this);
    }


    private void getGovernorates() {
        io.reactivex.Observable<ArrayList<Governorate>> govList  = Common.getAPI().getGovernorates().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());

        govList.subscribe(new Observer<ArrayList<Governorate>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(ArrayList<Governorate> governorates) {
                for (Governorate governorate:governorates) {
                    if(Common.isArabic)
                        governorateList.add(governorate.getNameAR());
                    else
                        governorateList.add(governorate.getNameEN());
                }

                ArrayAdapter<String> gov_adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, governorateList);
                gov_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                gov_adapter.notifyDataSetChanged();
                governorate.setAdapter(gov_adapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

                if (Common.isEditAddress) {
                    editAddress = new Address();
                    editAddress = Common.address;
                   // Common.isEditAddress = false;
                    setEditAddressData();
                }else
                {

                }


               // Toast.makeText(AddNewAddress.this, "ccccc"+String.valueOf(governorateList.size()), Toast.LENGTH_SHORT).show();
               // Toast.makeText(AddNewAddress.this, "gigikgikglk", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAllCities() {

        allCitiesList = new ArrayList<>();

        compositeDisposable.add(Common.getAPI().getCities(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<City>>() {
                    @Override
                    public void accept(ArrayList<City> cities) throws Exception {
                        for (City city:cities) {
                            allCitiesList.add(city);

                        }
                    }
                }));

    }

    private void setEditAddressData() {

        address_name.setText(editAddress.getName());
        address_phone.setText(editAddress.getPhone());

        int governorateId = editAddress.getGovernorateId();
        governorate.setSelection(governorateId-1);
        //get cities for this governorate and select this city in spinner
        getCities(governorateId,true);

        address_block.setText(editAddress.getBlock());
        address_street.setText(editAddress.getStreet());
        address_building.setText(editAddress.getBuilding());
        if(!TextUtils.isEmpty(editAddress.getFloor()))
        {
            address_floor.setText(editAddress.getFloor());
            address_apartment.setText(editAddress.getApartment());
        }

        save_address.setText(getResources().getString(R.string.edit));
    }

    private void getCities(int governorateId, final boolean isEdit) {

        citiesList = new ArrayList<>();

        compositeDisposable.add(Common.getAPI().getCities(governorateId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<City>>() {
                    @Override
                    public void accept(ArrayList<City> cities) throws Exception {
                        for (City city:cities) {
                            if(Common.isArabic)
                                citiesList.add(city.getNameAR());
                            else
                                citiesList.add(city.getNameEN());
                        }
                        ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(getBaseContext(),android.R.layout.simple_spinner_item, citiesList);
                        city_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        city_adapter.notifyDataSetChanged();
                        city.setAdapter(city_adapter);

                        int index = 0;
                        if(isEdit){
                            if(Common.isArabic)
                                index = citiesList.indexOf(editAddress.getCityNameAR());
                            else
                                index = citiesList.indexOf(editAddress.getCityNameEN());

                            city.setSelection(index);
                        }

                    }
                }));
    }

    @Override
    public void onClick(View v) {


        userId = Common.currentUser.getId();
        name = address_name.getText().toString();
        phone = address_phone.getText().toString();
        block = address_block.getText().toString();
        street = address_street.getText().toString();
        building = address_building.getText().toString();
        floor = address_floor.getText().toString();
        apartment = address_apartment.getText().toString();

        getCityId();

        if (validation(name, phone, cityId, block, street, building)) {
            if (!TextUtils.isEmpty(floor) && !TextUtils.isEmpty(apartment))
                address = new Address(userId, name, phone, cityId, block, street, building, floor, apartment);
            else if (!TextUtils.isEmpty(floor))
                address = new Address(userId, name, phone, cityId, block, street, building, floor, null);
            else if (!TextUtils.isEmpty(apartment))
                address = new Address(userId, name, phone, cityId, block, street, building, null, apartment);
            else
                address = new Address(userId, name, phone, cityId, block, street, building);

            if (save_address.getText().equals(getString(R.string.add_address)))
                add_address();
            else
                edit_address();

        }

    }

    public void add_address(){
        compositeDisposable.add(Common.getAPI().addAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer > 0) {
                            Toast.makeText(AddNewAddress.this, R.string.add_address_successfully, Toast.LENGTH_LONG).show();
                            address_name.setText("");
                            address_phone.setText("");
                            address_block.setText("");
                            address_street.setText("");
                            address_building.setText("");
                            address_floor.setText("");
                            address_apartment.setText("");
                            governorate.setSelection(0);

                        } else
                            Toast.makeText(AddNewAddress.this, getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                            finish();
                    }
                }));
    }

    public void edit_address(){

        address.setAddressId(editAddress.getId());

        compositeDisposable.add(Common.getAPI().editAddress(address)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        if (integer > 0) {
                            Toast.makeText(AddNewAddress.this, R.string.update_address_successfully, Toast.LENGTH_LONG).show();
                            address_name.setText("");
                            address_phone.setText("");
                            address_block.setText("");
                            address_street.setText("");
                            address_building.setText("");
                            address_floor.setText("");
                            address_apartment.setText("");
                            governorate.setSelection(0);

                        } else
                            Toast.makeText(AddNewAddress.this, getResources().getString(R.string.error_connection), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }));
    }

    private boolean validation(String name, String phone, int cityId, String block, String street, String building) {

        if(TextUtils.isEmpty(name) ||TextUtils.isEmpty(phone) || TextUtils.isEmpty(block) || TextUtils.isEmpty(street)||TextUtils.isEmpty(building))
        {
            Toast.makeText(this, R.string.missing_required_data, Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // for governerate listener to change spinner of cities
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        //to prevent call this method when run and change the index of city to select from api
        if(!Common.isEditAddress)
            getCities(position+1,false);
        else
            Common.isEditAddress = false;
    }

    private void getCityId() {
        String cityName = city.getSelectedItem().toString();

        for (City city:allCitiesList) {
            if(Common.isArabic){
                if(city.getNameAR().equals(cityName)) {
                    cityId = city.getId();
                    break;
                }

            }else {
                if(city.getNameEN().equals(cityName)) {
                    cityId = city.getId();
                    break;
                }
            }
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void errorConnectionMess(){

        AlertDialog.Builder error = new AlertDialog.Builder(this);
        error.setMessage(R.string.error_connection);
        AlertDialog dialog = error.create();
        dialog.show();
    }

}