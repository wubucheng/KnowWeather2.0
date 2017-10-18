package cn.devshare.knowweather.entity.basic;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:基本天气数据
 * Author: cheng
 * Create time: 2017/10/18 15:34
 */
public class Basic {
    private String city;
    private String cnty;
    private String id;
    private String lat;
    private String lon;
    private Update update;

    public void setCity(String city) {
        this.city = city;
    }

    public String getCity() {
        return city;
    }

    public void setCnty(String cnty) {
        this.cnty = cnty;
    }

    public String getCnty() {
        return cnty;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLat() {
        return lat;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getLon() {
        return lon;
    }

    public void setUpdate(Update update) {
        this.update = update;
    }

    public Update getUpdate() {
        return update;
    }
}
