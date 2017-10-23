package cn.devshare.knowweather.model;

import android.content.Context;

import cn.devshare.knowweather.entity.Weather;
import cn.devshare.knowweather.presenter.IRequestFinishedListener;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.model
 * Class describe:modle用来放数据的处理（比如网络请求，缓存等）,类似于jpa
 * Author: cheng
 * Create time: 2017/10/19 13:14
 */
public interface IWeatherModel {


    void requestWeather(String weatherId, Context context, IRequestFinishedListener iRequestFinishedListener);

    Weather handleWeather(String response);

    void requestBingPic(Context context,IRequestFinishedListener iRequestFinishedListener);

    Weather getWeather();
}
