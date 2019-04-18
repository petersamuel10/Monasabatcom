package com.vavisa.monasabatcom.fragments;

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
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.R;
import com.vavisa.monasabatcom.activities.FilterActivity;
import com.vavisa.monasabatcom.adapter.CompanyAdapter;
import com.vavisa.monasabatcom.models.Company;
import com.vavisa.monasabatcom.utility.KeyboardUtility;

import java.util.ArrayList;

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
  private TextView cancelSearch;
  private EditText searchText;
  private ArrayList<Company> companyList = new ArrayList<>();
  private int viewType = 1;

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

      clearText.setOnClickListener(this);
      cancelSearch.setOnClickListener(this);
      searchButton.setOnClickListener(this);
      filterButton.setOnClickListener(this);
    }

    // that use to move between fragments
    Common.mActivity = getActivity();

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
          public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

          @Override
          public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.toString().trim().length() > 0) {
              clearText.setVisibility(View.VISIBLE);
            } else {
              clearText.setVisibility(GONE);
            }
          }

          @Override
          public void afterTextChanged(Editable s) {}
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
    }
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
              .getFeaturedCompanies()
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
