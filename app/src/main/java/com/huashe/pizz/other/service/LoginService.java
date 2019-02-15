package com.huashe.pizz.other.service;

import java.io.File;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

/**
 * Created by Jamil
 * Time  2019/1/17
 * Description
 */
public interface LoginService {



    //今日头条
    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("userLogin")
    Observable<Object> getNewsDetails(@Query("name") String name,
                                      @Query("password") String password,
                                      @Query("key") String key);




    //    @POST("getUserInfo")
//    Observable<Object> getUserInfo(@QueryMap Map<String ,String> map);
    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("getUserInfo")
    Observable<Object> getUserInfo(@Query("userid") String userid);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("getCustomLike")//客户喜好信息列表
    Observable<Object> getCustomLike(@Query("userid") String userid);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("getProductById") //客户信号产品信息接口
    Observable<Object> getProductById(@Query("likeid") String likeid);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("setCustomLike")// 保存客户喜好接口
    Observable<Object> setCustomLike(@QueryMap Map<String,String> map);


    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("getPack") //版本更新接口
    Observable<Object> getPack(@Query("version") String version);




    @Multipart
    @POST("uploadPhoto") //图片上传
    Observable<Object> upload(@Query("mobile") String name,@Part MultipartBody.Part file);

    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("alterpwd")//修改密码&重置密码
    Observable<Object> alterpwd(@QueryMap Map<String,String> map);



    @Headers({"Content-type:application/json;charset=utf-8","Accept:application/json"})
    @POST("/pizza/downLoadUpdate/download")
    Observable<Object> downLoadupdate();

}
