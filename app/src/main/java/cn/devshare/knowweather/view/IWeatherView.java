package cn.devshare.knowweather.view;

import android.widget.ImageView;

import cn.devshare.knowweather.entity.Weather;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.view
 * Class describe:View是很薄的一层，只应该有简单的Set/Get的方法，用户输入和设置界面显示的内容，除此就不应该有更多的内容，绝不容许直接访问Model
 * Author: cheng
 * Create time: 2017/10/19 13:23
 */
public interface IWeatherView {


    void showWeater(Weather weater);

    void showBingPic(String bing);

    void refreshPic();

    void closeRefresh(String info);

    void  toast(String info);

}
