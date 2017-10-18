package cn.devshare.knowweather.entity.hourlyforecast;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity.hourlyforecast
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 16:09
 */
public class HourlyForecast {
    private HourCond cond;
    private String date;
    private String hum;
    private String pop;
    private String pres;
    private String tmp;
    private HourWind wind;

    public HourCond getCond() {
        return cond;
    }

    public void setCond(HourCond cond) {
        this.cond = cond;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHum() {
        return hum;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getPop() {
        return pop;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPres() {
        return pres;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public HourWind getWind() {
        return wind;
    }

    public void setWind(HourWind wind) {
        this.wind = wind;
    }
}
