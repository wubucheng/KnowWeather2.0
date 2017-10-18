package cn.devshare.knowweather.entity.basic;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 15:45
 */
public class Update {
    private String loc;
    private String utc;

    public void setLoc(String loc) {
        this.loc = loc;
    }

    public String getLoc() {
        return loc;
    }

    public void setUtc(String utc) {
        this.utc = utc;
    }

    public String getUtc() {
        return utc;
    }
}
