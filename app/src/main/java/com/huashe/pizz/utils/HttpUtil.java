package com.huashe.pizz.utils;

import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtil {

    // 正式更新URL  https://www.bcelement.com.cn/jeeplus/
    public static final String BASE_URL = "https://www.bcelement.com.cn/jeeplus/bookcase/";
    private static final String TAG = "HttpUtil";


    // 获得Get请求对象request
    public static HttpGet getHttpGet(String url) {
        HttpGet request = new HttpGet(url);
        return request;
    }


    // 获得Post请求对象request
    public static HttpPost getHttpPost(String url) {
        HttpPost request = new HttpPost(url);
        return request;
    }


    // 根据请求获得响应对象response
    public static HttpResponse getHttpResponse(HttpGet request) throws ClientProtocolException, IOException {
        HttpResponse response = new DefaultHttpClient().execute(request);
        return response;
    }


    // 根据请求获得响应对象response
    public static HttpResponse getHttpResponse(HttpPost request) throws ClientProtocolException, IOException {
        HttpResponse response = new DefaultHttpClient().execute(request);
        return response;
    }


    // 发送Post请求，获得响应查询结果
    public static String queryStringForPost(String url) {
        HttpPost request = HttpUtil.getHttpPost(url);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 10000);
        HttpConnectionParams.setSoTimeout(httpParameters, 10000);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);


        Log.i(TAG, url);
        String result = null;
        try {
            // HttpUtil.getHttpResponse(request);//原方法，没做网络超时处理
            HttpResponse response = httpClient.execute(request);// 新方案，做了网络超时设置
            // 判断是否请求成功
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获得响应
                result = EntityUtils.toString(response.getEntity());
                return result;
            } else {
                Log.i(TAG, response.getStatusLine().getStatusCode() + "");
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        }
        return null;
    }


    // 获得响应查询结果
    public static String queryStringForPost(HttpPost request) {
        String result = null;
        try {
            // 获得响应对象
            HttpResponse response = HttpUtil.getHttpResponse(request);
            // 判断是否请求成功
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获得响应
                result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        }
        return null;
    }


    // 发送Get请求，获得响应查询结果
    public static String queryStringForGet(String url) {
        // 获得HttpGet对象
        HttpGet request = HttpUtil.getHttpGet(url);
        String result = null;
        try {
            // 获得响应对象
            HttpResponse response = HttpUtil.getHttpResponse(request);
            // 判断是否请求成功
            if (response.getStatusLine().getStatusCode() == 200) {
                // 获得响应
                result = EntityUtils.toString(response.getEntity());
                return result;
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        } catch (IOException e) {
            e.printStackTrace();
            result = "网络异常！";
            return result;
        }
        return null;
    }


    /**
     * @param url 发送请求的URL
     * @return 服务器响应字符串
     * @throws Exception
     */
    public static String getRequest(String url) throws Exception {
        // 创建HttpGet对象。
        HttpGet get = new HttpGet(url);
        HttpParams httpParameters = new BasicHttpParams();
        HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
        HttpConnectionParams.setSoTimeout(httpParameters, 5000);
        DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
        // 发送GET请求
        HttpResponse httpResponse = httpClient.execute(get);
        // 如果服务器成功地返回响应
        if (httpResponse.getStatusLine().getStatusCode() == 200) {
            // 获取服务器响应字符串
            String result = EntityUtils.toString(httpResponse.getEntity());
            return result;
        }
        return null;
    }


    /**
     * 请求远程服务器，并封装参数信息
     *
     * @param url
     * @param rawParams
     * @return
     * @throws Exception
     */
    public static String postRequest(String url, Map<String, String> rawParams) throws Exception {
        String result = null;
            // 创建HttpPost对象。
            HttpPost post = new HttpPost(url);
            // 如果传递参数个数比较多的话可以对传递的参数进行封装
            List<NameValuePair> params = new ArrayList<>();
            for (String key : rawParams.keySet()) {
                // 封装请求参数
                params.add(new BasicNameValuePair(key, rawParams.get(key)));
            }
            Log.i(TAG, "params-------------------> " + params);
            // 设置请求参数
            post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
            HttpParams httpParameters = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(httpParameters, 3000);
            HttpConnectionParams.setSoTimeout(httpParameters, 15000);
            DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
            // 发送POST请求
            HttpResponse httpResponse = httpClient.execute(post);
            // 如果服务器成功地返回响应
            result = null;
            if (httpResponse.getStatusLine().getStatusCode() == 200) {
                // 获取服务器响应字符串
                result = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
    //            ToastUtil.showToast(result);
                Log.i(TAG, "result-------->" + result);
            }else {
                Log.i(TAG, "result-------->网络故障，请稍后再试" + result);
            }
        return result;
    }
}
