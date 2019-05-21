package com.vavisa.monasabatcom.webService;

import com.google.gson.JsonElement;
import com.vavisa.monasabatcom.models.Category;
import com.vavisa.monasabatcom.models.Company;
import com.vavisa.monasabatcom.models.FilterModel;
import com.vavisa.monasabatcom.models.Offer;
import com.vavisa.monasabatcom.models.PageData;
import com.vavisa.monasabatcom.models.PaymentUrl;
import com.vavisa.monasabatcom.models.SearchHour;
import com.vavisa.monasabatcom.models.Services;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;
import com.vavisa.monasabatcom.models.companyDetails.Offers;
import com.vavisa.monasabatcom.models.orderModels.OrderModel;
import com.vavisa.monasabatcom.models.orderResult.AddOrderResult;
import com.vavisa.monasabatcom.models.profile.Address;
import com.vavisa.monasabatcom.models.profile.AppointmentModel;
import com.vavisa.monasabatcom.models.profile.City;
import com.vavisa.monasabatcom.models.profile.Governorate;
import com.vavisa.monasabatcom.models.profile.OrderDetailsModel;
import com.vavisa.monasabatcom.models.profile.User;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface APIInterface {

    @POST("GetCategories/")
    Observable<ArrayList<Category>> getCategory();

    @POST("Register/")
    Observable<Integer> register(@Body User user);

    @POST("Login/")
    Observable<Integer> login(@Body User user);

    @FormUrlEncoded
    @POST("GetProfile/")
    Observable<User> getProfile(@Field("UserId") Integer userId, @Field("Password") String Password);

    @POST("GetOfferTypes/")
    Observable<ArrayList<Offer>> getOffers();

    @FormUrlEncoded
    @POST("GetUserFavorites/")
    Observable<ArrayList<Company>> getFavouriteList(@Field("UserId") Integer userId);

    @FormUrlEncoded
    @POST("GetCompaniesByOffer/")
    Observable<ArrayList<Company>> getCompaniesByOffer(@Field("TypeId") Integer userId);

    @FormUrlEncoded
    @POST("Editprofile/")
    Observable<Integer> editProfile(@Field("UserId") Integer userId, @Field("Name") String name, @Field("Email") String email, @Field("Mobile") String mobile);

    @FormUrlEncoded
    @POST("GetUserAddresses/")
    Observable<ArrayList<Address>> getUserAddresses(@Field("UserId") Integer userId);

    @POST("GetGovernorates/")
    Observable<ArrayList<Governorate>> getGovernorates();

    @FormUrlEncoded
    @POST("GetCities/")
    Observable<ArrayList<City>> getCities(@Field("GovernorateId") Integer governorateId);

    @POST("GetSearchHours")
    Observable<ArrayList<SearchHour>> getSearchHours();

    @POST("AddAddress/")
    Observable<Integer> addAddress(@Body Address address);

    @POST("EditAddress/")
    Observable<Integer> editAddress(@Body Address address);

    @FormUrlEncoded
    @POST("DeleteAddress/")
    Observable<Integer> deleteAddress(@Field("AddressId") Integer addressId);

    @FormUrlEncoded
    @POST("ChangePassword/")
    Observable<Integer> changePassword(@Field("UserId") Integer userId, @Field("OldPassword") String oldPassword, @Field("Password") String password);

    @FormUrlEncoded
    @POST("GetUserOrders")
    Observable<ArrayList<AppointmentModel>> getUserOrders(@Field("UserId") Integer userId);

    @FormUrlEncoded
    @POST("ForgotPassword/")
    Observable<Integer> forgotPassword(@Field("Email") String email, @Field("Language") String lan);


    @FormUrlEncoded
    @POST("GetCompanies/")
    Observable<ArrayList<Company>> getCompanies(@Field("Keyword") String keyword, @Field("CategoryId") Integer CategoryId,@Field("FilterId") Integer FilterId,
                                                @Field("CityId") Integer CityId, @Field("SearchDate") String SearchDate, @Field("SearchHour") String SearchHour,
                                                @Field("IsFeatured") Integer IsFeatured, @Field("PageNo") Integer PageNo, @Field("PageSize") Integer PageSize);

    @FormUrlEncoded
    @POST("GetCompanyDetails")
    Observable<CompanyDetailsModel> getCompanyDetails(@Field("CompanyId") Integer companyId, @Field("SearchDate") String SearchDate,
                                                      @Field("SearchHour") String SearchHour, @Field("UserId") Integer userId);

    @FormUrlEncoded
    @POST("GetServiceDetails")
    Observable<Services> getServiceDetails(@Field("ServiceId") Integer ServiceId);

    @FormUrlEncoded
    @POST("GetOfferDetails")
    Observable<Offers> getOfferDetails(@Field("OfferId") Integer OfferId);

    @FormUrlEncoded
    @POST("Rating/")
    Observable<Integer> rating(@Field("UserId") Integer userId,@Field("CompanyId") Integer companyId,
                               @Field("Rate") Float rating, @Field("Comment") String comment);

    @FormUrlEncoded
    @POST("MarkAsFavorite/")
    Observable<Integer> markAsFavorite(@Field("UserId") Integer userId, @Field("CompanyId") Integer companyId);

    @POST("AddOrder")
    Observable<AddOrderResult> addOrder(@Body OrderModel orderModel);

    @FormUrlEncoded
    @POST("GetOrderDetails/")
    Observable<OrderDetailsModel> getOrderDetails(@Field("OrderId") Integer orderId);

    @POST("GetFilters")
    Observable<ArrayList<FilterModel>> getFilters();

    @FormUrlEncoded
    @POST("GetPages/")
    Observable<PageData> getPages(@Field("PageId") Integer pageId);

    @FormUrlEncoded
    @POST("PayOrder")
    Observable<PaymentUrl> getPayment(@Field("OrderId") Integer Orderid, @Field("PaidAmount") float paidAmount);

    @POST("GetSplashScreen")
    Observable<JsonElement> getSplashScreen();

    @FormUrlEncoded
    @POST("CheckAndGetDeliveryCost")
    Observable<Float> checkAndGetDeliveryCost(@Field("CompanyId") Integer companyId, @Field("AddressId") Integer addressId);


}
