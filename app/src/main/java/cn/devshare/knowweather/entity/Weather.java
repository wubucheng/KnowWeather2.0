package cn.devshare.knowweather.entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import cn.devshare.knowweather.entity.aqi.Aqi;
import cn.devshare.knowweather.entity.basic.Basic;
import cn.devshare.knowweather.entity.dailyforecast.DailyForecast;
import cn.devshare.knowweather.entity.hourlyforecast.HourlyForecast;
import cn.devshare.knowweather.entity.now.Now;
import cn.devshare.knowweather.entity.suggestion.Suggestion;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 16:17
 */
public class Weather {
    private Aqi aqi;
    private Basic basic;
    @SerializedName("daily_forecast")
    private List<DailyForecast> dailyForecast;
    @SerializedName("hourly_forecast")
    private List<HourlyForecast> hourlyForecast;
    private Now now;
    private String status;
    private Suggestion suggestion;

    public Aqi getAqi() {
        return aqi;
    }

    public void setAqi(Aqi aqi) {
        this.aqi = aqi;
    }

    public Basic getBasic() {
        return basic;
    }

    public void setBasic(Basic basic) {
        this.basic = basic;
    }

    public List<DailyForecast> getDailyForecast() {
        return dailyForecast;
    }

    public void setDailyForecast(List<DailyForecast> dailyForecast) {
        this.dailyForecast = dailyForecast;
    }

    public List<HourlyForecast> getHourlyForecast() {
        return hourlyForecast;
    }

    public void setHourlyForecast(List<HourlyForecast> hourlyForecast) {
        this.hourlyForecast = hourlyForecast;
    }

    public Now getNow() {
        return now;
    }

    public void setNow(Now now) {
        this.now = now;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Suggestion getSuggestion() {
        return suggestion;
    }

    public void setSuggestion(Suggestion suggestion) {
        this.suggestion = suggestion;
    }
}
