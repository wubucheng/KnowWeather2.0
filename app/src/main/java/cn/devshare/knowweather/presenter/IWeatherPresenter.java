package cn.devshare.knowweather.presenter;

import android.content.BroadcastReceiver;
import android.content.Context;

import cn.devshare.knowweather.entity.Weather;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.presenter
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/19 15:56
 */
public interface IWeatherPresenter  {

    void requestWeather(String weatherId,Context context);

}
