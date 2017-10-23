package cn.devshare.knowweather.presenter;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import cn.devshare.knowweather.entity.Weather;
import cn.devshare.knowweather.model.IWeatherModel;
import cn.devshare.knowweather.model.WeatherModelImpl;
import cn.devshare.knowweather.view.IWeatherView;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.presenter
 * Class describe:Presenter层
 * Author: cheng
 * Create time: 2017/10/19 15:04
 */
public class WeatherPresenter  implements  IWeatherPresenter,IRequestFinishedListener {

    IWeatherModel weatherModel;
    IWeatherView weatherView;
    Weather weather;

    public WeatherPresenter(IWeatherView iWeatherView){
        this.weatherModel=new WeatherModelImpl();
        this.weatherView=iWeatherView;
    }

    public WeatherPresenter(){

    }
    @Override
    public void requestWeather(String weatherId, Context context) {
        SharedPreferences preferences= PreferenceManager.getDefaultSharedPreferences(context);
        String weatherString=preferences.getString("weather",null);
        String bingPic=preferences.getString("bing_pic",null);

        if(weatherString!=null){
            //从SharedPreferences读取到数据
            weather= weatherModel.handleWeather(weatherString);
            weatherId=weather.getBasic().getCity();
            Log.i("the preDDDD we ",weather.getBasic().toString());
            weatherView.showWeater(weather);
        }else {
            //请求网络数据
            weatherModel.requestWeather(weatherId,context,this);
        }

        if(bingPic!=null){
            weatherView.showBingPic(bingPic);
        }else{
            weatherModel.requestBingPic(context,this);

        }

    }

    public void onSuccess(Weather weather){
        this.weatherView.showWeater(weather);
        this.weatherView.closeRefresh("刷新成功");
    }
    @Override
    public void onRequestWeatherSuccess() {
        Weather weather=weatherModel.getWeather();
        onSuccess(weather);
    }

    @Override
    public void onRequestWeatherFail() {
        weatherView.toast("获取天气失败");
    }

    @Override
    public void onRequestBingSuccess(String bingPic) {
        weatherView.showBingPic(bingPic);
    }

    @Override
    public void onRequestBingFail() {

    }

    public void www(Weather weather){
        weatherView.showWeater(weather);
    }
}
