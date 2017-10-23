package cn.devshare.knowweather.model;


import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.Provice;
import cn.devshare.knowweather.presenter.IRequestAreaFinishedListener;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.model
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/23 20:25
 */
public interface IAreaModel {

    void queryProvincesFromDatabase(IRequestAreaFinishedListener iRequestAreaFinishedListener);

    void queryCitiesFromDatabase(Provice selectedProvice,IRequestAreaFinishedListener iRequestAreaFinishedListener);

    void queryCountiesFromDatabase(Provice selectedProvice, City selectCity, IRequestAreaFinishedListener iRequestAreaFinishedListener);

    void queryFromServer(String url, final String type,IRequestAreaFinishedListener iRequestAreaFinishedListener);
}
