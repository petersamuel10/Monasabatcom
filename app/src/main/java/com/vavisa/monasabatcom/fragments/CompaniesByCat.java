package com.vavisa.monasabatcom.fragments;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.FilterActivity;
import com.vavisa.monasabatcom.adapter.CompanyAdapter;
import com.vavisa.monasabatcom.models.profile.City;
import com.vavisa.monasabatcom.models.Company;
import com.vavisa.monasabatcom.models.SearchHour;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static com.vavisa.monasabatcom.utility.Constants.RESULT_OK;

public class CompaniesByCat extends Fragment implements View.OnClickListener {

    @BindView(R.id.pb)
    ProgressBar pb;
    @BindView(R.id.sl)
    SwipeRefreshLayout sl;
    @BindView(R.id.category_rec)
    RecyclerView companyListView;
    @BindView(R.id.empty_list)
    TextView emptyList;
    @BindView(R.id.first)
    ImageView first;
    @BindView(R.id.second)
    ImageView second;
    @BindView(R.id.third)
    ImageView third;
    @BindView(R.id.arrow)
    ImageView back;

    @OnClick(R.id.back)
    public void back() {
        getActivity().onBackPressed();
    }

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    CompanyAdapter adapter = new CompanyAdapter();
    private View fragmentView;
    private TextView title, search_ed;
    private ArrayList<Company> companyList = new ArrayList<>();
    private int viewType = 1;

    List<City> allCitiesList = new ArrayList<>();
    ArrayList<SearchHour> searchHoursList_ = new ArrayList<>();
    ArrayList<String> cityNameList, hoursList;

    private String[] timeList;
    private String[] cityList;
    private int cityItemPosition = 0;
    private int timeItemPosition = 0;
    Boolean isEmpty;

    TextView city_txt, date_txt, time_txt;
    String searchDate = "-1", searchHour = "-1", cityName = "";
    int cityId = -1, pageNo = 1, categoryId = -1;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentView == null) {
            fragmentView = inflater.inflate(R.layout.companies_by_cat, container, false);
            ButterKnife.bind(this, fragmentView);
            reference();

            title.setText(getArguments().getString("catName"));
            categoryId = Integer.parseInt(getArguments().getString("categoryId"));
            getCities();
            getTime();

            companyListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                    if (llm.findLastCompletelyVisibleItemPosition() == companyList.size() - 1) {
                        if (!isEmpty) {
                            pageNo++;
                            pb.setVisibility(View.VISIBLE);
                            requestData();
                        }
                    }
                }
            });

        }

        setUpSwipeRefreshLayout();
        requestData();


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

            case R.id.filter:
                startActivityForResult(new Intent(getActivity(), FilterActivity.class), RESULT_OK);
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

                            if (cities == null)
                                cityList = new String[0];
                            else {
                                allCitiesList.addAll(cities);
                                cityList = new String[cities.size()];
                                for (int i = 0; i < cities.size(); i++) {
                                    if (Common.isArabic)
                                        cityList[i] = cities.get(i).getNameAR();
                                    else
                                        cityList[i] = cities.get(i).getNameEN();
                                }
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

                            if (searchHourList == null)
                                timeList = new String[0];
                            else {
                                searchHoursList_.addAll(searchHourList);
                                timeList = new String[searchHourList.size()];
                                for (int i = 0; i < searchHourList.size(); i++) {
                                    timeList[i] = searchHourList.get(i).getHour();
                                }
                            }
                        }
                    }));

        } else errorConnectionMess();
    }

    private void showPopDialog() {

        final Dialog dialog = new Dialog(getActivity(), R.style.MyDialog);
        dialog.setContentView(R.layout.home_pop_up);
        dialog.getWindow().getAttributes().windowAnimations = R.style.PauseDialogAnimation;

        // for dialog
        Button searchButton = dialog.findViewById(R.id.search_button);
        TextView skipButton = dialog.findViewById(R.id.skip_button);
        ImageView del_city = dialog.findViewById(R.id.del_city);
        ImageView del_date = dialog.findViewById(R.id.del_date);
        ImageView del_time = dialog.findViewById(R.id.del_time);

        city_txt = dialog.findViewById(R.id.where);
        date_txt = dialog.findViewById(R.id.when);
        time_txt = dialog.findViewById(R.id.time);

        del_city.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                city_txt.setText("");
                cityId = -1;
                cityName = "";
            }
        });
        del_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_txt.setText("");
                searchDate = "-1";
            }
        });
        del_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                time_txt.setText("");
                searchHour = "-1";
            }
        });

        city_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCityList();
            }
        });
        date_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectDate();
            }
        });
        time_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimeList();
            }
        });


        searchButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String search_title = "";
                        search_ed.setTextColor(getResources().getColor(R.color.colorPrimary));
                        if (!cityName.equals("")) search_title += cityName;
                        if (!searchDate.equals("-1"))
                            search_title += ((search_title.equals("")) ? "" : " - ") + searchDate;
                        if (!searchHour.equals("-1"))
                            search_title += ((search_title.equals("")) ? "" : " - ") + searchHour;
                        search_ed.setText(search_title);

                        requestData();
                        dialog.dismiss();
                    }
                });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        if (!cityName.equals("")) city_txt.setText(cityName);
        if (!searchHour.equals("-1")) time_txt.setText(searchHour);
        if (!searchDate.equals("-1")) date_txt.setText(searchDate);


        dialog.show();

    }

    public void showCityList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setSingleChoiceItems(cityList, cityItemPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {

                cityItemPosition = position;
                dialog.dismiss();
                cityId = allCitiesList.get(position).getId();
                cityName = cityList[position];
                city_txt.setText(cityName);

            }
        });

        builder.create().show();
    }

    public void showTimeList() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setSingleChoiceItems(timeList, timeItemPosition, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int position) {

                timeItemPosition = position;
                dialog.dismiss();
                searchHour = timeList[position];
                time_txt.setText(searchHour);
            }
        });

        builder.create().show();
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
                            .getCompanies("", categoryId, -1, cityId, searchDate, searchHour, 0, pageNo, 10)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    new Consumer<ArrayList<Company>>() {
                                        @Override
                                        public void accept(ArrayList<Company> companies) throws Exception {
                                            if (companies.size() > 0) {
                                                isEmpty = false;
                                                emptyList.setVisibility(GONE);
                                                companyListView.setVisibility(View.VISIBLE);
                                                if(pageNo == 1)
                                                    companyList.clear();
                                                companyList.addAll(companies);
                                            } else if (companyList.isEmpty()) {
                                                companyListView.setVisibility(GONE);
                                                adapter.notifyDataSetChanged();
                                                emptyList.setVisibility(View.VISIBLE);
                                                pb.setVisibility(GONE);
                                            }else if (companies.isEmpty()) {
                                                isEmpty = true;
                                                pb.setVisibility(GONE);
                                            }
                                        }
                                    }, new Consumer<Throwable>() {
                                        @Override
                                        public void accept(Throwable throwable) throws Exception {
                                            pb.setVisibility(GONE);
                                            Common.errorAlert(getContext(), getString(R.string.error_occure));
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

        adapter = new CompanyAdapter(getActivity(), companyList, Constants.TAB_CATEGORY, viewType, searchDate, searchHour);
        companyListView.setAdapter(adapter);
    }

    private void setUpSwipeRefreshLayout() {
        sl.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        sl.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        pageNo = 1;
                        companyList = new ArrayList<>();
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

    private void reference() {

        Toolbar toolbar = fragmentView.findViewById(R.id.toolBar);
        title = toolbar.findViewById(R.id.title_cat);
        search_ed = fragmentView.findViewById(R.id.search_ed);
        if (Common.isArabic)
            back.setRotation(180);

        search_ed.setOnClickListener(this);
        first.setOnClickListener(this);
        second.setOnClickListener(this);
        third.setOnClickListener(this);

        iconViewColor(first, second, third);

    }
}
