package cn.devshare.knowweather.db;

import org.litepal.crud.DataSupport;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.db
 * Class describe:省份实体
 * Author: cheng
 * Create time: 2017/10/17 20:28
 */
public class Provice extends DataSupport {

    private int id;
    private String proviceName;
    private int proviceCode;

    public Provice(String proviceName,int proviceCode){
        this.proviceName=proviceName;
        this.proviceCode=proviceCode;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProviceName() {
        return proviceName;
    }

    public void setProviceName(String proviceName) {
        this.proviceName = proviceName;
    }

    public int getProviceCode() {
        return proviceCode;
    }

    public void setProviceCode(int proviceCode) {
        this.proviceCode = proviceCode;
    }
}
