package top.bluewort.Notes.base_utils.X005_weather_api;

import lombok.Data;

@Data
public class WeatherDTO {


    /**
     * 当前城市ID
     */
    private String cityid;
    /**
     * 天气情况
     */
    private String wea;
    /**
     * 湿度
     */
    private String humidity;
    /**
     * 当前温度
     */
    private String tem;
    /**
     * 风向(早/晚)
     */
    private String win;
    /**
     * 风速
     */
    private String win_speed;

    private String city;
    private String cityEn;

    public String getCityid() {
        return cityid;
    }

    public void setCityid(String cityid) {
        this.cityid = cityid;
    }

    public String getWea() {
        return wea;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getTem() {
        return tem;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityEn() {
        return cityEn;
    }

    public void setCityEn(String cityEn) {
        this.cityEn = cityEn;
    }
}
