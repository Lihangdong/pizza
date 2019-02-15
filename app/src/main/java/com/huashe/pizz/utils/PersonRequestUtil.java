package com.huashe.pizz.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.huashe.pizz.bean.PersonalCenter.UpdateBean;
import com.huashe.pizz.bean.PersonalCenter.UserInfoBean;
import com.huashe.pizz.other.rx.RetrofitHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;


/**
 * Created by Jamil
 * Time  2019/1/22
 * Description
 */
public class PersonRequestUtil {
    static PersonRequestUtil personRequestUtil;

    static public PersonRequestUtil getInstance() {
        if (personRequestUtil == null) {
            personRequestUtil = new PersonRequestUtil();
        }
        return personRequestUtil;
    }

    //个人中心 个人信息请求
    public void Request4PersonalInfo(String userid, final Calback4Personal calback4Personal) {
        // bytesToHexString(CryptoUtil.aesEncrypt(key.getBytes()

        RetrofitHelper.getHttpAPITest()
                .getUserInfo(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object Object) {
                        System.out.println("aaaaaaaaaaaaaaa"+Object.toString());
                        Gson gson = new Gson();
                        String jsonObject = gson.toJson(Object);
                        UserInfoBean userInfoBean = gson.fromJson(jsonObject, UserInfoBean.class);
                        calback4Personal.success(userInfoBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error--->", "onError: detail > " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    //个人中心 客户喜好列表
    public void Request4KeHuLikeList(String userid, final Calback4Personal calback4Personal) {
        RetrofitHelper.getHttpAPITest()
                .getCustomLike(userid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object Object) {
                        Gson gson = new Gson();
                        String jsonObject = gson.toJson(Object);
                        System.out.println("getCustomLike   "+jsonObject);
                        try {
                            JSONObject jsonObject1 = new JSONObject(jsonObject);
                            calback4Personal.success(jsonObject1);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error--->", "onError: detail > " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    //客户喜好的产品信息
    public void Request4KeHuLikeProductInfo(String likeid, final Calback4Personal calback4Personal) {
        RetrofitHelper.getHttpAPITest()
                .getProductById(likeid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object Object) {
                        Gson gson = new Gson();
                        String jsonObject = gson.toJson(Object);
                        calback4Personal.success(jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error--->", "onError: detail > " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


    //个人中心 保存客户喜好
    public void saveCustomLikeNetRequest(Map<String, String> map, final Calback4Personal calback4Personal) {
        // bytesToHexString(CryptoUtil.aesEncrypt(key.getBytes()

        RetrofitHelper.getHttpAPITest()
                .setCustomLike(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object Object) {
                        Gson gson = new Gson();
                        String jsonObject = gson.toJson(Object);
                        calback4Personal.success(jsonObject);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error--保存客户喜好->", "onError: detail > " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void UpdateNetRequest(String version, final Calback4Personal calback4Personal) {
        RetrofitHelper.getHttpAPITest()
                .getPack(version)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(Object Object) {
                        Gson gson = new Gson();
                        String jsonObject = gson.toJson(Object);
                        calback4Personal.success(gson.fromJson(jsonObject, UpdateBean.class));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("error--版本更新->", "onError: detail > " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }





    public void upload(String mobile, MultipartBody.Part imgs,final Calback4Personal calback4Personal) {
        RetrofitHelper.getHttpAPITest()
                .upload(mobile,imgs)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Object>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {
                        calback4Personal.success(o);
                    }

                    @Override
                    public void onError(Throwable e) {
                     Log.i("图片上传异常",e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }



}
