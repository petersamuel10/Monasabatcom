package com.vavisa.monasabatcom;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;
import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.fragments.CategoryFragment;
import com.vavisa.monasabatcom.fragments.CompanyDetails;
import com.vavisa.monasabatcom.fragments.CompanyDetailsFragment;
import com.vavisa.monasabatcom.fragments.FavouriteFragment;
import com.vavisa.monasabatcom.fragments.HomeFragment;
import com.vavisa.monasabatcom.fragments.MyAccountFragment;
import com.vavisa.monasabatcom.fragments.OffersFragment;

import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
    implements BottomNavigationView.OnNavigationItemSelectedListener {

  @BindView(R.id.bottom_navigation)
  BottomNavigationView bottomNavigationView;

  @Override
  protected void attachBaseContext(Context newBase) {
    super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    loadLocal();
    if (Common.booking)
      if (Common.isArabic) {
        CalligraphyConfig.initDefault(
            new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Changa-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build());
      } else {
        CalligraphyConfig.initDefault(
            new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Avenir.otf")
                .setFontAttrId(R.attr.fontPath)
                .build());
      }
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    bottomNavigationView.setOnNavigationItemSelectedListener(this);
    if (Common.booking) {
      // loadFragment(new CompanyDetails());
      loadFragment(new CompanyDetailsFragment());
      Common.booking = false;
    } else {
      loadFragment(new HomeFragment());
    }

    Paper.init(this);
    checkAuthentication();

    final DialogPlus dialogPlus =
        DialogPlus.newDialog(this)
            .setContentHolder(new ViewHolder(R.layout.home_pop_up))
            .setContentBackgroundResource(android.R.color.transparent)
            .setGravity(Gravity.TOP)
            .create();

    View holder = dialogPlus.getHolderView();
    Button searchButton = holder.findViewById(R.id.search_button);
    TextView skipButton = holder.findViewById(R.id.skip_button);

    searchButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {}
        });

    skipButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            dialogPlus.dismiss();
          }
        });

    dialogPlus.show();
  }

  private void checkAuthentication() {
    if (Paper.book("Monasabatcom").contains("currentUser"))
      Common.currentUser = Paper.book("Monasabatcom").read("currentUser");
  }

  private boolean loadFragment(Fragment mFragment) {

    if (mFragment != null) {
      FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
      fragmentTransaction.setCustomAnimations(
          android.R.anim.fade_in,
          android.R.anim.fade_out,
          android.R.anim.fade_in,
          android.R.anim.fade_out);
      fragmentTransaction.replace(R.id.main_fragment, mFragment);
      fragmentTransaction.commit();
      return true;
    }
    return false;
  }

  @Override
  public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
    Fragment fragment = null;
    Common.makeResr = false;
    switch (menuItem.getItemId()) {
      case R.id.main_nav:
        fragment = new HomeFragment();
        break;
      case R.id.category_nav:
        fragment = new CategoryFragment();
        break;
      case R.id.product_nav:
        fragment = new OffersFragment();
        break;
      case R.id.favourite_nav:
        fragment = new FavouriteFragment();
        break;
      case R.id.account_nav:
        fragment = new MyAccountFragment();
        break;
      default:
        break;
    }
    return loadFragment(fragment);
  }

  public void setLanguage(String lang) {
    Locale locale = new Locale(lang);
    Locale.setDefault(locale);
    Configuration config = new Configuration();
    config.locale = locale;
    getBaseContext()
        .getResources()
        .updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    Paper.book("Monasabatcom").write("language", lang);
  }

  public void loadLocal() {
    Paper.init(this);
    String lan = Paper.book("Monasabatcom").read("language");
    if (!TextUtils.isEmpty(lan)) {
      setLanguage(lan);
      if (lan.contentEquals("ar")) Common.isArabic = true;
    } else {
      startActivity(new Intent(MainActivity.this, ChooseLanguage.class));
      finish();
    }
  }
}
