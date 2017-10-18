package cn.devshare.knowweather.entity.suggestion;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 16:13
 */
public class Suggestion {
    private Air air;
    private Comf comf;
    private Cw cw;
    private Drsg drsg;
    private Flu flu;
    private Sport sport;
    private Trav trav;
    private Uv uv;

    public void setAir(Air air) {
        this.air = air;
    }

    public Air getAir() {
        return air;
    }

    public void setComf(Comf comf) {
        this.comf = comf;
    }

    public Comf getComf() {
        return comf;
    }

    public void setCw(Cw cw) {
        this.cw = cw;
    }

    public Cw getCw() {
        return cw;
    }

    public void setDrsg(Drsg drsg) {
        this.drsg = drsg;
    }

    public Drsg getDrsg() {
        return drsg;
    }

    public void setFlu(Flu flu) {
        this.flu = flu;
    }

    public Flu getFlu() {
        return flu;
    }

    public void setSport(Sport sport) {
        this.sport = sport;
    }

    public Sport getSport() {
        return sport;
    }

    public void setTrav(Trav trav) {
        this.trav = trav;
    }

    public Trav getTrav() {
        return trav;
    }

    public void setUv(Uv uv) {
        this.uv = uv;
    }

    public Uv getUv() {
        return uv;
    }

}
