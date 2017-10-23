package cn.devshare.knowweather.util;

import java.io.IOException;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.util
 * Class describe: 网络请求类
 * Author: cheng
 * Create time: 2017/10/17 20:42
 */
public class OkHttpUtil {

    //get请求数据
    public static void get(String url, Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request=new Request.Builder().url(url).build();
        client.newCall(request).enqueue(callback);
    }



}
