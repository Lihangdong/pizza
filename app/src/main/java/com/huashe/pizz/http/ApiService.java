package com.huashe.pizz.http;

import com.huashe.pizz.base.BaseResponse;
import com.huashe.pizz.bean.AboutUs.AboutUsBean;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * The interface Api service.
 *
 */


public interface ApiService {
    @POST("getMenu")
    Observable<BaseResponse<List<AboutUsBean>>> getMenu(@QueryMap Map<String, String> params);

    @Streaming //大文件时要加不然会OOM
    @GET
    Call<ResponseBody> downloadFile(@Url String fileUrl);

}
