package cn.devshare.knowweather.model;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.IOException;

import cn.devshare.knowweather.WeatherActivity;
import cn.devshare.knowweather.config.Api;
import cn.devshare.knowweather.entity.Weather;
import cn.devshare.knowweather.presenter.IRequestFinishedListener;
import cn.devshare.knowweather.presenter.WeatherPresenter;
import cn.devshare.knowweather.util.GsonUtil;
import cn.devshare.knowweather.util.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.model
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/19 13:15
 */
public class WeatherModelImpl implements IWeatherModel {
    private Weather weather;
    String bingPic;
    public boolean isSuccess = false;


    WeatherPresenter weatherPresenter;

    public WeatherModelImpl() {
        weatherPresenter=new WeatherPresenter();
    }



    @Override
    public void requestWeather(final String weatherId, final Context context, final IRequestFinishedListener requestFinishedListener) {
        String url = Api.WeatherBaseUrl + weatherId + "&key=" + Api.WeatherKey;
        OkHttpUtil.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                requestFinishedListener.onRequestWeatherFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resposeText = response.body().string();
                Log.i("the weather info", resposeText.toString());

                weather = GsonUtil.handleWeatherResponse(resposeText);

                if (weather != null && "ok".equals(weather.getStatus())) {
                    SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                    editor.putString("weather", resposeText);
                    editor.apply();
                    weatherPresenter.www(weather);
                   // requestFinishedListener.onRequestWeatherSuccess();
                }
            }
        });
    }

    @Override
    public Weather getWeather(){
        return weather;
    }

    @Override
    public Weather handleWeather(String response) {
        return GsonUtil.handleWeatherResponse(response);
    }

    @Override
    public void requestBingPic(final Context context,final IRequestFinishedListener requestFinishedListener) {
        OkHttpUtil.get(Api.BingUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                requestFinishedListener.onRequestBingFail();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                bingPic = response.body().string();
                SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
                editor.putString("bing_pic", bingPic);
                editor.apply();
                requestFinishedListener.onRequestBingSuccess(bingPic);
            }
        });

    }


}
