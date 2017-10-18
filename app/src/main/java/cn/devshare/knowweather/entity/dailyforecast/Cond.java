package cn.devshare.knowweather.entity.dailyforecast;

import com.google.gson.annotations.SerializedName;

/**
 * ProjectName: KnowWeather
 * PackName：cn.devshare.knowweather.entity
 * Class describe:
 * Author: cheng
 * Create time: 2017/10/18 15:53
 */
public class Cond {
    @SerializedName("code_d")
    private String codeD;
    @SerializedName("code_n")
    private String codeN;
    @SerializedName("txt_d")
    private String txtD;
    @SerializedName("txt_n")
    private String txtN;

    public void setCodeD(String codeD) {
        this.codeD = codeD;
    }

    public String getCodeD() {
        return codeD;
    }

    public void setCodeN(String codeN) {
        this.codeN = codeN;
    }

    public String getCodeN() {
        return codeN;
    }

    public void setTxtD(String txtD) {
        this.txtD = txtD;
    }

    public String getTxtD() {
        return txtD;
    }

    public void setTxtN(String txtN) {
        this.txtN = txtN;
    }

    public String getTxtN() {
        return txtN;
    }
}
