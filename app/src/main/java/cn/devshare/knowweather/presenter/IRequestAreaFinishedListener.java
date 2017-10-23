package cn.devshare.knowweather.presenter;

import java.util.List;

import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.County;
import cn.devshare.knowweather.db.Provice;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.presenter
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/23 20:33
 */
public interface IRequestAreaFinishedListener {

    void onQueryProvinceSuccess(List<Provice> provices);

    void onQueryCitiesSuccess(List<City>cities);

    void onQueryCountiesSuccess(List<County>counties);

    void onReqeustAreaSuccess();

    void onRequestAreaFail();

}
