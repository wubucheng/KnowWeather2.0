package cn.devshare.knowweather.presenter;

import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.Provice;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.presenter
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/23 20:28
 */
public interface IAreaPresenter {
    void queryProvinces();

    void queryCities(Provice selectedProvice);

    void queryCounties(Provice selected,City selectCity);
}
