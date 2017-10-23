package cn.devshare.knowweather.model;

import android.util.Log;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.devshare.knowweather.db.City;
import cn.devshare.knowweather.db.County;
import cn.devshare.knowweather.db.Provice;
import cn.devshare.knowweather.presenter.IRequestAreaFinishedListener;
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
 * Create time: 2017/10/23 20:29
 */
public class AreaModelImpl implements IAreaModel {
    private List<String> dataList = new ArrayList<>();

    private List<Provice> proviceList;
    private List<City> cityList;
    private List<County> countyList;

    private Provice selectedProvice;
    private City selectedCity;

    @Override
    public void queryProvincesFromDatabase(IRequestAreaFinishedListener iRequestAreaFinishedListener) {
        proviceList = DataSupport.findAll(Provice.class);
        if (proviceList.size() > 0) {
            iRequestAreaFinishedListener.onQueryProvinceSuccess(proviceList);
        } else {
            String url = "http://guolin.tech/api/china";
            queryFromServer(url, "province", iRequestAreaFinishedListener);
        }
    }

    @Override
    public void queryCitiesFromDatabase(Provice selectedProvice, IRequestAreaFinishedListener iRequestAreaFinishedListener) {
        this.selectedProvice = selectedProvice;
        cityList = DataSupport.where("provinceid=?", String.valueOf(selectedProvice.getId())).find(City.class);
        if (cityList.size() > 0) {
            iRequestAreaFinishedListener.onQueryCitiesSuccess(cityList);
        } else {
            int proviceCode = selectedProvice.getProviceCode();
            String url = "http://guolin.tech/api/china/" + proviceCode;
            queryFromServer(url, "city", iRequestAreaFinishedListener);
        }
    }

    @Override
    public void queryCountiesFromDatabase(Provice selectedProvice, City selectedCity, IRequestAreaFinishedListener iRequestAreaFinishedListener) {
        this.selectedCity = selectedCity;
        countyList = DataSupport.where("cityid=?", String.valueOf(selectedCity.getId())).find(County.class);
        if (countyList.size() > 0) {
            iRequestAreaFinishedListener.onQueryCountiesSuccess(countyList);
        } else {
            int proviceCode = selectedProvice.getProviceCode();
            int cityCode = selectedCity.getCityCode();
            String url = "http://guolin.tech/api/china/" + proviceCode + "/" + cityCode;
            queryFromServer(url, "county", iRequestAreaFinishedListener);
        }
    }

    @Override
    public void queryFromServer(String url, final String type, final IRequestAreaFinishedListener iRequestAreaFinishedListener) {
        OkHttpUtil.get(url, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                Log.i("province", responseText);
                boolean result = false;
                if ("province".equals(type)) {
                    result = GsonUtil.handProviceResponse(responseText);
                } else if ("city".equals(type)) {
                    result = GsonUtil.handleCityResponse(responseText, selectedProvice.getId());
                } else if ("county".equals(type)) {
                    GsonUtil.handleCountyResponse(responseText, selectedCity.getId());
                }

                if (result) {
                    if ("province".equals(type)) {
                        queryProvincesFromDatabase(iRequestAreaFinishedListener);
                    } else if ("city".equals(type)) {
                        queryCitiesFromDatabase(selectedProvice, iRequestAreaFinishedListener);
                    } else if ("county".equals(type)) {
                        queryCountiesFromDatabase(selectedProvice, selectedCity, iRequestAreaFinishedListener);
                    }
                }
            }
        });

    }
}

