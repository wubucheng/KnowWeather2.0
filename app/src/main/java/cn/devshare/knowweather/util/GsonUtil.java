package cn.devshare.knowweather.util;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.County;
import cn.devshare.knowweather.db.Provice;
import cn.devshare.knowweather.entity.Weather;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.util
 * Class describe:gson解析数据
 * Author: cheng
 * Create time: 2017/10/17 20:45
 */
public class GsonUtil {

    public static boolean handProviceResponse(String response) {
        Log.i("handle: ", "province");
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray provinceArray = new JSONArray(response);
                for (int i = 0; i < provinceArray.length(); i++) {
                    JSONObject provinceObject = provinceArray.getJSONObject(i);
                    Provice provice = new Provice(provinceObject.getString("name"), provinceObject.getInt("id"));
                    provice.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }

    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {

            try {
                JSONArray cityArray = new JSONArray(response);
                for (int i = 0; i < cityArray.length(); i++) {
                    JSONObject cityObject = cityArray.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityObject.getString("name"));
                    city.setCityCode(cityObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }

                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray countyArray = new JSONArray(response);
                for (int i = 0; i < countyArray.length(); i++) {
                    JSONObject countyObject = countyArray.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    Log.i("the county is",county.getCountyName());
                    Log.i("the weatherI is",county.getWeatherId());
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
        return false;
    }


   public static Weather handleWeatherResponse(String response){
       try {
           JSONObject weatherObject=new JSONObject(response);
           JSONArray weaterArray=weatherObject.getJSONArray("HeWeather5");
           String weatherContent=weaterArray.getJSONObject(0).toString();
           return new Gson().fromJson(weatherContent,Weather.class);
       } catch (JSONException e) {
           e.printStackTrace();
       }
        return null;
   }

}
