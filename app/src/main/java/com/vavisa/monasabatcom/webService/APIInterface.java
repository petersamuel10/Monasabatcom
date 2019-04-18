package com.vavisa.monasabatcom.webService;

import com.vavisa.monasabatcom.models.Address;
import com.vavisa.monasabatcom.models.Appointment;
import com.vavisa.monasabatcom.models.Category;
import com.vavisa.monasabatcom.models.City;
import com.vavisa.monasabatcom.models.Company;
import com.vavisa.monasabatcom.models.Governorate;
import com.vavisa.monasabatcom.models.Offer;
import com.vavisa.monasabatcom.models.OrderDetails;
import com.vavisa.monasabatcom.models.PageData;
import com.vavisa.monasabatcom.models.PaymentUrl;
import com.vavisa.monasabatcom.models.User;
import com.vavisa.monasabatcom.models.companyDetails.CompanyDetailsModel;
import com.vavisa.monasabatcom.models.makeAppointment.MakeAppointment;

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
    Observable<Integer>register(@Body User user);

    @POST("Login/")
    Observable<Integer>login(@Body User user);

    @FormUrlEncoded
    @POST("GetProfile/")
    Observable<User>getProfile(@Field("UserId") Integer userId , @Field("Password") String Password);

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
    Observable<Integer> editProfile(@Field("UserId") Integer userId,@Field("Name") String name,@Field("Email") String email,@Field("Mobile") String mobile);

    @FormUrlEncoded
    @POST("GetUserAddresses/")
    Observable<ArrayList<Address>> getUserAddresses(@Field("UserId") Integer userId);

    @POST("GetGovernorates/")
    Observable<ArrayList<Governorate>> getGovernorates();

    @FormUrlEncoded
    @POST("GetCities/")
    Observable<ArrayList<City>> getCities(@Field("GovernorateId") Integer governorateId);

    @POST("AddAddress/")
    Observable<Integer> addAddress(@Body Address address);

    @POST("EditAddress/")
    Observable<Integer> editAddress(@Body Address address);

    @FormUrlEncoded
    @POST("DeleteAddress/")
    Observable<Integer> deleteAddress(@Field("AddressId")Integer addressId);

    @FormUrlEncoded
    @POST("ChangePassword/")
    Observable<Integer> changePassword(@Field("UserId") Integer userId,@Field("OldPassword") String oldPassword,@Field("Password") String password);

    @FormUrlEncoded
    @POST("GetUserOrders/")
    Observable<ArrayList<Appointment>> getUserOrders(@Field("UserId") Integer userId);

    @FormUrlEncoded
    @POST("ForgotPassword/")
    Observable<Integer> forgotPassword(@Field("Email") String email,@Field("Language") String lan);

    @POST("GetFeaturedCompanies/")
    Observable<ArrayList<Company>> getFeaturedCompanies();

    @FormUrlEncoded
    @POST("GetCompanies/")
    Observable<ArrayList<Company>> getCompanies(@Field("CategoryId") Integer categoryId);

    @FormUrlEncoded
    @POST("GetCompanyDetails/")
    Observable<CompanyDetailsModel> getCompanyDetails(@Field("CompanyId") Integer companyId,@Field("UserId") Integer userId);

    @FormUrlEncoded
    @POST("Rating/")
    Observable<Integer> rating(@Field("CompanyId") Integer companyId,@Field("UserId") Integer userId,@Field("Rate") Integer rating);

    @FormUrlEncoded
    @POST("MarkAsFavorite/")
    Observable<Integer> markAsFavorite(@Field("UserId") Integer userId,@Field("CompanyId") Integer companyId);

    @POST("AddOrder/")
    Observable<Integer> addOrder(@Body MakeAppointment makeAppointment);

    @FormUrlEncoded
    @POST("GetOrderDetails/")
    Observable<OrderDetails> getOrderDetails(@Field("OrderId")Integer orderId);

    @FormUrlEncoded
    @POST("CheckAppointmentDate/")
    Observable<Integer> checkAppointmentDate(@Field("CompanyId")Integer orderId,@Field("AppointmentDate") String AppointmentDate);


    @FormUrlEncoded
    @POST("GetPages/")
    Observable<PageData> getPages(@Field("PageId")Integer pageId);

    @FormUrlEncoded
    @POST("Payorder/")
    Observable<PaymentUrl> getPayment (@Field("Orderid") Integer Orderid);

}
