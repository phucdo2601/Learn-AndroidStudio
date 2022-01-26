package com.phucdn.learnuploadimagetoserverb1.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.phucdn.learnuploadimagetoserverb1.model.User;
import com.phucdn.learnuploadimagetoserverb1.utils.Constant;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    //link api https://accountservermanagement.herokuapp.com/api/accounts

    public static final String DOMAIN ="https://accountservermanagement.herokuapp.com/api/";

    Gson gson = new GsonBuilder()
            .setDateFormat("yyyy MM dd HH:mm:ss").create();

    ApiService apiService = new Retrofit.Builder()
            .baseUrl(DOMAIN)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiService.class);

    //call api
    //khai bao multipart boi vi co gui file len server
    @Multipart
    @POST("accounts")
    Call<User> registerAccount(@Part(Constant.KEY_USERNAME) RequestBody username,
                               @Part(Constant.KEY_PASSWORD) RequestBody password,
                               @Part MultipartBody.Part avt);
}
