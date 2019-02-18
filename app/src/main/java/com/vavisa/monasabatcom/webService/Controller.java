package com.vavisa.monasabatcom.webService;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Controller {

    final static String BASE_URL = "http://Monasabatcom.vavisa-kw.com/api/app/";
    final static String AUTHORIZATION = "Basic YXBpQE1vbmFzYWJhdGNvbTpBUElATW9uYXNhYmF0Y29tXzEyMzY1NA==";
    private APIInterface apiInterface;

    public Controller() {

        OkHttpClient httpClient = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public  Response intercept(Chain chain) throws IOException {

                Request originalRequest = chain.request();

                Request.Builder builder = originalRequest.newBuilder()
                                          .header("Authorization",
                                                  AUTHORIZATION);

                Request newRequest = builder.build();

                return chain.proceed(newRequest);
            }
        }).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(BASE_URL)
                            .addConverterFactory(GsonConverterFactory.create(gson))
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(httpClient)
                            .build();

        apiInterface = retrofit.create(APIInterface.class);
    }

   public APIInterface getAPI()
    {
        return apiInterface;
    }
}
