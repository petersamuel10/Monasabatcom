package com.vavisa.monasabatcom;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;

import com.vavisa.monasabatcom.Common.Common;
import com.vavisa.monasabatcom.fragments.CategoryFragment;
import com.vavisa.monasabatcom.fragments.FavouriteFragment;
import com.vavisa.monasabatcom.fragments.HomeFragment;
import com.vavisa.monasabatcom.fragments.MyAccountFragment;
import com.vavisa.monasabatcom.fragments.OffersFragment;
import com.vavisa.monasabatcom.models.companyDetails.PaymentMethod;
import com.vavisa.monasabatcom.models.orderModels.CartModel;
import com.vavisa.monasabatcom.models.orderModels.OfferOrder;
import com.vavisa.monasabatcom.models.orderModels.ServicesOrder;
import com.vavisa.monasabatcom.utility.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Stack;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.paperdb.Paper;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    private HashMap<String, Stack<Fragment>> mStack;
    private String mCurrentTab;
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
        // init activity and cart when app start
        Common.mActivity = this;

        mStack = new HashMap<String, Stack<Fragment>>();
        mStack.put(Constants.TAB_HOME, new Stack<Fragment>());
        mStack.put(Constants.TAB_CATEGORY, new Stack<Fragment>());
        mStack.put(Constants.TAB_OFFERS, new Stack<Fragment>());
        mStack.put(Constants.TAB_FAVORITES, new Stack<Fragment>());
        mStack.put(Constants.TAB_PROFILE, new Stack<Fragment>());

        bottomNavigationView.setSelectedItemId(R.id.main_nav);

        Paper.init(this);
        checkAuthentication();
    }

    private void checkAuthentication() {
        if (Paper.book("Monasabatcom").contains("currentUser"))
            Common.currentUser = Paper.book("Monasabatcom").read("currentUser");
    }

    private void selectTab(String tabId) {

        // if press twice in the same tab
        if (mCurrentTab == tabId) {

            Fragment fragment = mStack.get(mCurrentTab).elementAt(0);
            mStack.get(tabId).clear();
            pushFragments(tabId, fragment, true);

        } else {
            mCurrentTab = tabId;

            if (mStack.get(tabId).size() == 0) {

                /*
                 *    First time this tab is selected. So add first fragment of that tab.
                 *    Dont need animation, so that argument is false.
                 *    We are adding a new fragment which is not present in stack. So add to stack is true.
                 */

                switch (tabId) {
                    case "home":
                        pushFragments(tabId, new HomeFragment(), true);
                        break;
                    case "category":
                        pushFragments(tabId, new CategoryFragment(), true);
                        break;
                    case "offers":
                        pushFragments(tabId, new OffersFragment(), true);
                        break;
                    case "favorites":
                        pushFragments(tabId, new FavouriteFragment(), true);
                        break;
                    case "profile":
                        pushFragments(tabId, new MyAccountFragment(), true);
                        break;
                }

            } else {

                /*
                 *    We are switching tabs, and target tab is already has atleast one fragment.
                 *    No need of animation, no need of stack pushing. Just show the target fragment
                 */
                pushFragments(tabId, mStack.get(tabId).lastElement(), false);
            }
        }
    }

    public void pushFragments(String tag, Fragment fragment, boolean shouldAdd) {
        if (shouldAdd)
            mStack.get(tag).push(fragment);

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_fragment, fragment);
        ft.commit();
    }

    private void popFragments() {
        /*
         *    Select the second last fragment in current tab's stack..
         *    which will be shown after the fragment transaction given below
         */

        Fragment fragment = mStack.get(mCurrentTab).elementAt(mStack.get(mCurrentTab).size() - 2);

        /*pop current fragment from stack.. */
        mStack.get(mCurrentTab).pop();

        /* We have the target fragment in hand.. Just show it.. Show a standard navigation animation*/

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        ft.replace(R.id.main_fragment, fragment);
        ft.commit();

    }

    @Override
    public void onBackPressed() {

        if (mStack.get(mCurrentTab).size() == 1) {
            if (mCurrentTab == Constants.TAB_HOME) {
                // We are already showing first fragment of current tab, so when back pressed, we will finish this activity..
                finish();
                return;
            } else {
                bottomNavigationView.setSelectedItemId(R.id.home);
            }
        } else
            // Goto previous fragment in navigation stack of this tab
            popFragments();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.main_nav:
                selectTab(Constants.TAB_HOME);
                return true;
            case R.id.category_nav:
                selectTab(Constants.TAB_CATEGORY);
                return true;
            case R.id.product_nav:
                selectTab(Constants.TAB_OFFERS);
                return true;
            case R.id.favourite_nav:
                selectTab(Constants.TAB_FAVORITES);
                return true;
            case R.id.account_nav:
                selectTab(Constants.TAB_PROFILE);
                return true;
        }
        return false;
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
