package cn.devshare.knowweather.entity.dailyforecast;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 15:53
 */
public class Astro {
    private String mr;
    private String ms;
    private String sr;
    private String ss;
    public void setMr(String mr) {
        this.mr = mr;
    }
    public String getMr() {
        return mr;
    }

    public void setMs(String ms) {
        this.ms = ms;
    }
    public String getMs() {
        return ms;
    }

    public void setSr(String sr) {
        this.sr = sr;
    }
    public String getSr() {
        return sr;
    }

    public void setSs(String ss) {
        this.ss = ss;
    }
    public String getSs() {
        return ss;
    }
}
