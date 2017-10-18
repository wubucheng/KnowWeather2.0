package cn.devshare.knowweather.db;

import org.litepal.crud.DataSupport;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.db
 * Class describe:城市实体
 * Author: cheng
 * Create time: 2017/10/17 20:34
 */
public class City extends DataSupport {

    private int id;
    private String cityName;
    private int cityCode;
    private int provinceId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
