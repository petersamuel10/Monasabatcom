package com.vavisa.monasabatcom.Common;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.FragmentActivity;

import com.vavisa.monasabatcom.models.Address;
import com.vavisa.monasabatcom.models.Offer;
import com.vavisa.monasabatcom.models.User;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;
import com.vavisa.monasabatcom.models.companyDetails.Services;
import com.vavisa.monasabatcom.models.makeAppointment.MakeAppointment;
import com.vavisa.monasabatcom.webService.APIInterface;
import com.vavisa.monasabatcom.webService.Controller;
import com.vavisa.monasabatcom.webService.Payment;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class Common {

    public static User currentUser;
    public static Integer offerId;
    public static Integer categoryId;
    public static Integer companyId;
    public static String categoryName;
    public static String offerName;
    public static boolean isArabic = false;
    public static FragmentActivity mActivity;
    public static CompanyDetailsModel companyDetails;

    public static Boolean isEditAddress = false;
    public static MakeAppointment appointment;
    public static Boolean makeResr = false;
    public static Boolean showReserBtn = false;
    public static Boolean newOrder = false;
    public static Boolean booking  = false;
    public static Integer orderId = 0;
    public static Float totalAmount;
    public static String date;
    public static String time;
    public static Address address;
    public static List<Services> services;
    public static List<Offer> offers;


    public static APIInterface getAPI ()
    {
        return new Controller().getAPI();
    }

    public static APIInterface getPayment ()
    {
        return new Payment().getAPI();
    }

    public static Boolean isConnectToTheInternet (Context context) {

        boolean connected = false;
        final ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            connected = true;
        } else if (netInfo != null && netInfo.isConnected()
                && cm.getActiveNetworkInfo().isAvailable()) {
            connected = true;
        } else if (netInfo != null && netInfo.isConnected()) {
            try {
                URL url = new URL("http://www.google.com");
                HttpURLConnection urlc = (HttpURLConnection) url
                        .openConnection();
                urlc.setConnectTimeout(3000);
                urlc.connect();
                if (urlc.getResponseCode() == 200) {
                    connected = true;
                }
            } catch (MalformedURLException e1) {
                e1.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (cm != null) {
           /* final NetworkInfo[] netInfoAll = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfoAll) {
                System.out.println("get network type :::" + ni.getTypeName());
                if ((ni.getTypeName().equalsIgnoreCase("WIFI") || ni
                        .getTypeName().equalsIgnoreCase("MOBILE"))
                        && ni.isConnected() && ni.isAvailable()) {
                    connected = true;
                    if (connected) {
                        break;
                    }
                }
            }*/
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null && activeNetwork.isConnected() && activeNetwork.isAvailable()) { // connected to the internet
                if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                    // connected to wifi
                    connected = true;
                } else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                    // connected to the mobile provider's data plan
                    connected = true;
                }
            } else {
                // not connected to the internet
                connected = false;
            }
        }
        return connected;

    }
}



