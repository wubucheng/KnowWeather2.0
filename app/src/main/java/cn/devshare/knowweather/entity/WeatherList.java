package cn.devshare.knowweather.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 16:19
 */
public class WeatherList {
    @SerializedName("HeWeather5")
    private List<Weather> weather;

    public void setHeweather(List<Weather> weather) {
        this.weather = weather;
    }

    public List<Weather> getHeweather() {
        return weather;
    }
}
