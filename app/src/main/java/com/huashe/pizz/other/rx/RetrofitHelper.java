package com.huashe.pizz.other.rx;

import android.support.annotation.NonNull;


import com.huashe.pizz.MyApplication;
import com.huashe.pizz.other.service.LoginService;
import com.huashe.pizz.other.util.Constant;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Author     zkx Kayler
 * Email:     461807588@qq.com
 * Time       2017/6/20 0020
 * Function  Retrofit使用类
 */

public class RetrofitHelper {

    private static OkHttpClient mOkHttpClient;

    static {
        initOkHttpClient();
    }



    public static LoginService getHttpAPITest() {

        return createApi(LoginService.class, Constant.BaseUrl);
    }


    /**
     * 根据传入的baseUrl，和api创建retrofit
     */
    private static <T> T createApi(Class<T> clazz, String baseUrl) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mOkHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(clazz);
    }

    /**
     * 初始化OKHttpClient,设置缓存,设置超时时间,设置打印日志,设置UA拦截器
     */
    private static void initOkHttpClient() {

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        LoggingInterceptor loggingInterceptor=new LoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        if (mOkHttpClient == null) {
            synchronized (RetrofitHelper.class) {
                if (mOkHttpClient == null) {
                    //设置Http缓存
//                    Cache cache = new Cache(new File(savePath), 1024 * 1024 * 10);
                    mOkHttpClient = new OkHttpClient.Builder()
//                            .cache(cache)
                            .addInterceptor(interceptor)
                            .addInterceptor(loggingInterceptor)
//                            .addNetworkInterceptor(new CacheInterceptor())
//                            .addNetworkInterceptor(new StethoInterceptor())暂时不需要 需要时开启
                            .retryOnConnectionFailure(true)
                            .connectTimeout(8, TimeUnit.SECONDS)
                            .writeTimeout(8, TimeUnit.SECONDS)
                            .readTimeout(8, TimeUnit.SECONDS)
                            .addInterceptor(new UserAgentInterceptor())//暂时不需要 需要时开启
                            .build();
                }
            }
        }
    }

    /**
     * 添加UA拦截器，某些请求API需要加上UA才能正常使用
     */
    private static class UserAgentInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {

            Request originalRequest = chain.request();
            Request requestWithUserAgent = originalRequest.newBuilder()
                    .removeHeader("User-Agent")
                    .addHeader("User-Agent", "xxx")
                    .build();
            return chain.proceed(requestWithUserAgent);
        }
    }

    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {

        @Override
        public Response intercept(@NonNull Chain chain) throws IOException {

            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (CommonUtil.isNetworkAvailable(MyApplication.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_NETWORK)
                        .build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
            }
            Response response = chain.proceed(request);
            if (CommonUtil.isNetworkAvailable(MyApplication.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }




    public static class LoggingInterceptor implements Interceptor {
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();

            long t1 = System.nanoTime();
            //Log.v("发送请求: [%s] %s%n%s",
            request.url().toString();

            Response response = chain.proceed(request);

            long t2 = System.nanoTime();
//            System.out.println(String.format("接收响应: [%s] %.1fms%n%s",
//                    response.request().url(), (t2 - t1) / 1e6d, response.headers()));headers

            return response;
        }
    }

//    private static Retrofit retrofit;
//    private static String TOKEN;
//    private static int responseCount(Response response) {
//        int result = 1;
//        while ((response = response.priorResponse()) != null) {
//            result++;
//        }
//        return result;
//    }
//    public static Retrofit getRetrofit( Context mContext) {
//        if (retrofit == null) {
//            //1.处理没有认证  http 401 Not Authorised
//            Authenticator mAuthenticator2 = new Authenticator() {
//                @Override
//                public Request authenticate(Route route, Response response) throws IOException {
//                    if (responseCount(response) >= 2) {
//                        // If both the original call and the call with refreshed token failed,it will probably keep failing, so don't try again.
//                        return null;
//                    }
////                    refreshToken();
//                    return response.request().newBuilder()
//                            .header("Authorization", TOKEN)
//                            .build();
//                }
//            };
//
//            //2. 请求的拦截处理
//            /**
//             * 如果你的 token 是空的，就是还没有请求到 token，比如对于登陆请求，是没有 token 的，
//             * 只有等到登陆之后才有 token，这时候就不进行附着上 token。另外，如果你的请求中已经带有验证 header 了，
//             * 比如你手动设置了一个另外的 token，那么也不需要再附着这一个 token.
//             */
//
//            Interceptor mRequestInterceptor = new Interceptor() {
//                @Override
//                public Response intercept(Chain chain) throws IOException {
//                    Request originalRequest = chain.request();
//
//
//
//                    /**
//                     * TOKEN == null，Login/Register noNeed Token
//                     * noNeedAuth(originalRequest)    refreshToken api request is after log in before log out,but  refreshToken api no need auth
//                     */
//
//                    Request authorisedRequest = originalRequest.newBuilder()
//
//
//                            .header("Connection", "Keep-Alive") //新添加，time-out默认是多少呢？
//                            .header("Authorization", "Bearer "+TOKEN)
//                            .build();
//
//                    Response originalResponse = chain.proceed(authorisedRequest);
//
//                    //把统一拦截的header 打印出来
////                    new MyHttpLoggingInterceptor().logInterceptorHeaders(authorisedRequest);
//
//                    return originalResponse.newBuilder()
//                            .build();
//                }
//            };
//
//            /**
//             * 如果不喜欢系统的Http 的打印方式，可以自己去实现Interceptor 接口
//             *
//             * 但是统一拦截的header 是无法打印的，因为是在请求发出后统一拦截打印的。
//             *
//             */
////            MyHttpLoggingInterceptor loggingInterceptor = new MyHttpLoggingInterceptor();
////            loggingInterceptor.setLevel(MyHttpLoggingInterceptor.Level.BODY);
//
//            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
//            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//
//            OkHttpClient okHttpClient = new OkHttpClient.Builder()
//                    .retryOnConnectionFailure(true)
//                    .connectTimeout(11, TimeUnit.SECONDS)
//                    .readTimeout(11,TimeUnit.SECONDS)
//                    .addNetworkInterceptor(mRequestInterceptor)
//                    .authenticator(mAuthenticator2)
//
//                    .addInterceptor(interceptor)
//
//                    .build();
//
//
//            retrofit = new Retrofit.Builder()
//                    .baseUrl(Constant.BaseUrl)
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create())
//
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                    .build();
//        }
//        return retrofit;
//    }

}
