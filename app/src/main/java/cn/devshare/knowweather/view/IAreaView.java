package cn.devshare.knowweather.view;

import java.util.List;

import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.County;
import cn.devshare.knowweather.db.Provice;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.view
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/23 20:44
 */
public interface IAreaView {

    void queryProvinces();

    void queryCities();

    void queryCounties();

    void showProvince(List<Provice> provices);

    void showCities(List<City> cities);

    void showCounties(List<County> counties);

    void showProgressDialog();

    void closeProgressDialog();
}
