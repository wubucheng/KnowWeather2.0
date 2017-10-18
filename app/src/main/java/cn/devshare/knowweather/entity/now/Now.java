package cn.devshare.knowweather.entity.now;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity.now
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 16:12
 */
public class Now {
    private NowCond cond;
    private String fl;
    private String hum;
    private String pcpn;
    private String pres;
    private String tmp;
    private String vis;
    private NowWind wind;

    public void setCond(NowCond cond) {
        this.cond = cond;
    }

    public NowCond getCond() {
        return cond;
    }

    public void setFl(String fl) {
        this.fl = fl;
    }

    public String getFl() {
        return fl;
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

    public void setPres(String pres) {
        this.pres = pres;
    }

    public String getPres() {
        return pres;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getTmp() {
        return tmp;
    }

    public void setVis(String vis) {
        this.vis = vis;
    }

    public String getVis() {
        return vis;
    }

    public void setWind(NowWind wind) {
        this.wind = wind;
    }

    public NowWind getWind() {
        return wind;
    }
}
