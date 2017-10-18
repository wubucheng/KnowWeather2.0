package cn.devshare.knowweather.entity.now;

import java.util.Date;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity.now
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 16:12
 */
public class NowWind {

    private String deg;
    private String dir;
    private String sc;
    private String spd;

    public void setDeg(String deg) {
        this.deg = deg;
    }

    public String getDeg() {
        return deg;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getDir() {
        return dir;
    }

    public void setSc(String sc) {
        this.sc = sc;
    }

    public String getSc() {
        return sc;
    }

    public void setSpd(String spd) {
        this.spd = spd;
    }

    public String getSpd() {
        return spd;
    }

}
