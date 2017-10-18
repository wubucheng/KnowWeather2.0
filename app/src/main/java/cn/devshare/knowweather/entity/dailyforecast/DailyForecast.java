package cn.devshare.knowweather.entity.dailyforecast;

import java.util.Date;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 15:56
 */
public class DailyForecast {
    private Astro astro;
    private Cond cond;
    private String date;
    private String hum;
    private String pcpn;
    private String pop;
    private String pres;
    private Tmp tmp;
    private String uv;
    private String vis;
    private Wind wind;

    public void setAstro(Astro astro) {
        this.astro = astro;
    }

    public Astro getAstro() {
        return astro;
    }

    public void setCond(Cond cond) {
        this.cond = cond;
    }

    public Cond getCond() {
        return cond;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setHum(String hum) {
        this.hum = hum;
    }

    public String getHum() {
        return hum;
    }

    public void setPcpn(String pcpn) {
        this.pcpn = pcpn;
    }

    public String getPcpn() {
        return pcpn;
    }

    public void setPop(String pop) {
        this.pop = pop;
    }

    public String getPop() {
        return pop;
    }

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getPres() {
        return pres;
    }

    public void setTmp(Tmp tmp) {
        this.tmp = tmp;
    }

    public Tmp getTmp() {
        return tmp;
    }

    public void setUv(String uv) {
        this.uv = uv;
    }

    public String getUv() {
        return uv;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getVis() {
        return vis;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Wind getWind() {
        return wind;
    }

}
