package cn.devshare.knowweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;


import java.io.IOException;

import cn.devshare.knowweather.config.Api;
import cn.devshare.knowweather.entity.Weather;
import cn.devshare.knowweather.util.GsonUtil;
import cn.devshare.knowweather.util.OkHttpUtil;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.service
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/19 9:04
 */
public class AutoUpdateService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        updateWeather();
        updateBingpIC();

        AlarmManager alarmManager= (AlarmManager) getSystemService(ALARM_SERVICE);
        int anhour=8*60*60*1000;
        long triggerAtTime=SystemClock.elapsedRealtime()+anhour;
        Intent i=new Intent(this,AutoUpdateService.class);
        PendingIntent pendingIntent=PendingIntent.getService(this,0,i,0);
        alarmManager.cancel(pendingIntent);
        alarmManager.set(AlarmManager.ELAPSED_REALTIME,triggerAtTime,pendingIntent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void updateBingpIC() {
        OkHttpUtil.get(Api.BingUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic=response.body().string();
                SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                editor.putString("bing_pic",bingPic);
                editor.apply();
            }
        });
    }

    private void updateWeather() {
        SharedPreferences sharedPreferences=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this);
        final String weatherString=sharedPreferences.getString("weather",null);
        if(weatherString!=null){
            final Weather weather=GsonUtil.handleWeatherResponse(weatherString);
            String city=weather.getBasic().getCity();
            String url=Api.WeatherBaseUrl+city+"&key="+Api.WeatherKey;
            OkHttpUtil.get(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String weatherText=response.body().string();
                    Weather weather=GsonUtil.handleWeatherResponse(weatherText);
                    if(weather!=null&&"ok".equals(weather.getStatus())){
                        SharedPreferences.Editor editor=PreferenceManager.getDefaultSharedPreferences(AutoUpdateService.this).edit();
                        editor.putString("weather",weatherText);
                        editor.apply();

                    }
                }
            });

        }
    }
}
