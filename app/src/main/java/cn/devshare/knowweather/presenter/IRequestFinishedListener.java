package cn.devshare.knowweather.presenter;

import cn.devshare.knowweather.entity.Weather;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.presenter
 * Class describe:网络请求回调接口
 * Author: cheng
 * Create time: 2017/10/21 20:50
 */
public interface IRequestFinishedListener {



    void onRequestWeatherSuccess();

    void onRequestWeatherFail();

    void onRequestBingSuccess(String bingPic);

    void onRequestBingFail();

}
