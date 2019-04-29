package com.vavisa.monasabatcom.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.FilterActivity;
import com.vavisa.monasabatcom.adapter.CompanyAdapter;
import com.vavisa.monasabatcom.models.City;
import com.vavisa.monasabatcom.models.Company;
import com.vavisa.monasabatcom.models.SearchHour;
import com.vavisa.monasabatcom.utility.KeyboardUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static com.vavisa.monasabatcom.constants.Constants.RESULT_OK;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private static final String TAG = "HomeFragment";

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.home_recyclerView)
    RecyclerView companyListView;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @BindView(R.id.first)
    ImageView first;
    @BindView(R.id.second)
    ImageView second;
    @BindView(R.id.third)
    ImageView third;

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CompanyAdapter adapter;
    ProgressDialog progressDialog;
    private View fragmentView;
    private ConstraintLayout searchLayout;
    private ImageButton clearText;
    private TextView cancelSearch, search_ed;
    private EditText searchText;
    private ArrayList<Company> companyList = new ArrayList<>();
    private int viewType = 1;

    List<City> allCitiesList = new ArrayList<>();
    List<SearchHour> searchHoursList = new ArrayList<>();
    ArrayList<String> cityNameList,hoursList;

    MaterialSpinner whereSpinner,whenSpinner;
    TextView date_txt;

    String searchDate = "-1",searchHour = "-1",cityName;
    int cityId = -1,pageNo = 1,categoryId = -1;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.fragment_home_fragments, container, false);

            Toolbar toolbar = fragmentView.findViewById(R.id.toolBar);
            ImageButton searchButton = toolbar.findViewById(R.id.search_bar);
            ImageButton filterButton = toolbar.findViewById(R.id.filter);
            searchLayout = fragmentView.findViewById(R.id.search_layout);
            clearText = searchLayout.findViewById(R.id.clear_text);
            cancelSearch = searchLayout.findViewById(R.id.cancel_button);
            searchText = searchLayout.findViewById(R.id.search_text);
            search_ed = fragmentView.findViewById(R.id.search_ed);

            clearText.setOnClickListener(this);
            cancelSearch.setOnClickListener(this);
            searchButton.setOnClickListener(this);
            filterButton.setOnClickListener(this);
            search_ed.setOnClickListener(this);

            getCities();
            getTime();

        }

/*    // that use to move between fragments
    Common.mActivity = getActivity();*/

        ButterKnife.bind(this, fragmentView);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setCancelable(false);

        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        setUpSwipeRefreshLayout();
        requestData();
        iconViewColor(first, second, third);

        searchText.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if (s.toString().trim().length() > 0) {
                            clearText.setVisibility(View.VISIBLE);
                        } else {
                            clearText.setVisibility(GONE);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });

        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first:
                viewType = 1;
                setupRecyclerView();
                iconViewColor(first, second, third);
                break;

            case R.id.second:
                viewType = 2;
                setupRecyclerView();
                iconViewColor(second, first, third);
                break;

            case R.id.third:
                viewType = 3;
                setupRecyclerView();
                iconViewColor(third, first, second);
                break;

            case R.id.search_bar:
                searchLayout.setVisibility(View.VISIBLE);
                searchText.requestFocus();
                KeyboardUtility.showKeyboard(getActivity(), searchText);
                break;

            case R.id.filter:
                startActivityForResult(new Intent(getActivity(), FilterActivity.class), RESULT_OK);
                break;

            case R.id.clear_text:
                searchText.setText("");
                clearText.setVisibility(GONE);
                break;

            case R.id.cancel_button:
                searchLayout.setVisibility(GONE);
                searchText.clearFocus();
                searchText.setText("");
                clearText.setVisibility(GONE);
                KeyboardUtility.hideKeyboardFrom(getActivity(), searchText);
                break;

            case R.id.search_ed:
                showPopDialog();
                break;
        }
    }

    private void getCities() {
        if (Common.isConnectToTheInternet(getActivity())) {

            cityNameList = new ArrayList<>();
            compositeDisposable.add(Common.getAPI().getCities(0)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<City>>() {
                        @Override
                        public void accept(ArrayList<City> cities) throws Exception {
                            for (City city : cities) {
                                allCitiesList.add(city);
                                if(Common.isArabic)
                                    cityNameList.add(city.getNameAR());
                                else
                                    cityNameList.add(city.getNameEN());
                            }

                        }
                    }));


        } else errorConnectionMess();
    }

    private void getTime() {
        if (Common.isConnectToTheInternet(getActivity())) {
            hoursList = new ArrayList<>();

            compositeDisposable.add(Common.getAPI().getSearchHours()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<ArrayList<SearchHour>>() {
                        @Override
                        public void accept(ArrayList<SearchHour> searchHourList) throws Exception {
                            for (SearchHour searchHour : searchHourList) {
                                searchHoursList.add(searchHour);
                                hoursList.add(searchHour.getHour());
                            }
                        }
                    }));

        } else errorConnectionMess();
    }

    private void showPopDialog() {

       final Dialog dialog = new Dialog(getActivity(), R.style.MyDialog);
        dialog.setContentView(R.layout.home_pop_up);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;


        Button searchButton = dialog.findViewById(R.id.search_button);
        TextView skipButton = dialog.findViewById(R.id.skip_button);

        whereSpinner = dialog.findViewById(R.id.where);
        date_txt = dialog.findViewById(R.id.when);
        whenSpinner = dialog.findViewById(R.id.time);

       /* // get for first time before selected
        City s = allCitiesList.get(0);
        cityId = s.getId();
        if (Common.isArabic)
            cityName = s.getNameAR();
        else
            cityName = s.getNameEN();

        SearchHour h = searchHoursList.get(0);
        searchHour = h.getHour();*/


        date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { selectDate(); }});

        whereSpinner.setItems(cityNameList);
        whenSpinner.setItems(hoursList);

        whereSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {

                City s = allCitiesList.get(position);
                cityId = s.getId();
                if (Common.isArabic)
                    cityName = s.getNameAR();
                else
                    cityName = s.getNameEN();
            }
        });

        whenSpinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {
            @Override
            public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
                SearchHour s = searchHoursList.get(position);
                searchHour = s.getHour();
            }
        });

        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                       search_ed.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if(searchDate.equals("-1"))
                            search_ed.setText(cityName+" - "+ searchHour);
                        else
                            search_ed.setText(cityName+" - "+ searchDate + " - "+searchHour);
                        requestData();
                        dialog.dismiss();
                    }
                });

        skipButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

        dialog.show();

    }

    public void selectDate() {
        Locale.setDefault(Locale.ENGLISH);
        Calendar calendar = Calendar.getInstance();
        int mYear = calendar.get(Calendar.YEAR);
        int mMonth = calendar.get(Calendar.MONTH);
        int mDay = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = null;

        datePickerDialog = new DatePickerDialog(getActivity(),
                new DatePickerDialog.OnDateSetListener() {

                    @SuppressLint("DefaultLocale")
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        searchDate = String.format("%02d/%02d/%d", dayOfMonth, (monthOfYear + 1), year);
                        date_txt.setText(searchDate);

                    }
                }, mYear, mMonth, mDay);


        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());

        datePickerDialog.show();


    }

    public void iconViewColor(ImageView blue, ImageView grey, ImageView grey2) {

        blue.setColorFilter(getResources().getColor(R.color.blue));
        grey.setColorFilter(getResources().getColor(R.color.bottom_nav_false));
        grey2.setColorFilter(getResources().getColor(R.color.bottom_nav_false));
    }

    private void requestData() {
        if (Common.isConnectToTheInternet(getActivity())) {
            pb.setVisibility(View.VISIBLE);
            compositeDisposable.add(
                    Common.getAPI()
                            .getFeaturedCompanies(categoryId, cityId, searchDate, searchHour, 0, pageNo, 10)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<ArrayList<Company>>() {
                                        @Override
                                        public void accept(ArrayList<Company> companies) throws Exception {
                                            if (companies.size() > 0) {
                                                emptyList.setVisibility(GONE);
                                                companyList = companies;
                                                setupRecyclerView();
                                                pb.setVisibility(GONE);
                                            } else progressDialog.dismiss();
                                        }
                                    }));

        } else errorConnectionMess();
    }

    private void setupRecyclerView() {

        switch (viewType) {
            case 1:
                companyListView.setLayoutManager(new GridLayoutManager(getContext(), 2));
                break;

            case 2:
            case 3:
                companyListView.setLayoutManager(new LinearLayoutManager(getActivity()));
                break;
        }

        adapter = new CompanyAdapter(getActivity(), companyList, viewType);
        companyListView.setAdapter(adapter);


        // companyListView.setHasFixedSize(true);

    /*LayoutAnimationController controller =
        AnimationUtils.loadLayoutAnimation(companyListView.getContext(), R.anim.layout_fall_down);
    companyListView.setLayoutAnimation(controller);*/
    }

    private void setUpSwipeRefreshLayout() {
        sl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        requestData();
                        sl.setRefreshing(false);
                    }
                });
    }

    public void errorConnectionMess() {

        AlertDialog.Builder error = new AlertDialog.Builder(getContext());
        error.setMessage(R.string.error_connection);
        AlertDialog dialog = error.create();
        dialog.show();
        pb.setVisibility(GONE);
        sl.setRefreshing(false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            String filterName = data.getStringExtra("filter");
            Log.i(TAG, "Received result " + filterName);
            // apply filter here
        }
    }
}
