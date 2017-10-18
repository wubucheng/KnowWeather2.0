package cn.devshare.knowweather.entity.hourlyforecast;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity.hourlyforecast
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 16:08
 */
public class HourCond {
    private String code;
    private String txt;

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTxt() {
        return txt;
    }
}
