package cn.devshare.knowweather.presenter;

import java.util.List;

import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.County;
import cn.devshare.knowweather.db.Provice;
import cn.devshare.knowweather.model.AreaModelImpl;
import cn.devshare.knowweather.model.IAreaModel;
import cn.devshare.knowweather.view.IAreaView;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.presenter
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/23 20:34
 */
public class AreaPresenter implements IAreaPresenter, IRequestAreaFinishedListener {
    private IAreaModel iAreaModel;
    private IAreaView iAreaView;

    public AreaPresenter(IAreaView iAreaView){
        iAreaModel=new AreaModelImpl();
        this.iAreaView=iAreaView;
    }
    @Override
    public void queryProvinces() {
        iAreaView.showProgressDialog();
        iAreaModel.queryProvincesFromDatabase(this);
    }

    @Override
    public void queryCities(Provice selectedCity) {
        iAreaView.showProgressDialog();
        iAreaModel.queryCitiesFromDatabase(selectedCity, this);
    }

    @Override
    public void queryCounties(Provice selectProvice, City selectCity) {
        iAreaView.showProgressDialog();
        iAreaModel.queryCountiesFromDatabase(selectProvice, selectCity, this);
    }

    @Override
    public void onQueryProvinceSuccess(List<Provice> provices) {
        iAreaView.closeProgressDialog();
        iAreaView.showProvince(provices);
    }

    @Override
    public void onQueryCitiesSuccess(List<City> cities) {
        iAreaView.closeProgressDialog();
        iAreaView.showCities(cities);
    }

    @Override
    public void onQueryCountiesSuccess(List<County> counties) {
        iAreaView.closeProgressDialog();
        iAreaView.showCounties(counties);
    }

    @Override
    public void onReqeustAreaSuccess() {

    }

    @Override
    public void onRequestAreaFail() {

    }
}
