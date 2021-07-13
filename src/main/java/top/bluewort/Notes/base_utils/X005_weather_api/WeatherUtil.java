package top.bluewort.Notes.base_utils.X005_weather_api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import top.bluewort.Notes.base_utils.X006_http_request.HttpClientUtil;

import java.io.IOException;
import java.util.Optional;

public class WeatherUtil {
    private String api = "https://www.tianqiapi.com/api/";
    private String appid = "18329817";
    private String appsecret = "baqIYQ7D";
    private String version = "v6";
    private String cityid = "101220109";

    private static Logger logger = LoggerFactory.getLogger(WeatherUtil.class);
    public WeatherDTO getWeather(String city) {
        city = Optional.ofNullable(city).orElse("杭州市").replaceAll("市","");
        WeatherDTO weatherVO = null;
        try {
            String response = "";
            if(StringUtils.isNotEmpty(city)){
                response = HttpClientUtil.sendHttpsGet(api + "?version=" + version + "&appid=" + appid + "&appsecret=" + appsecret + "&city=" + city);
            }else {
                response = HttpClientUtil.sendHttpsGet(api + "?version=" + version + "&appid=" + appid + "&appsecret=" + appsecret + "&cityid=" + cityid);
            }
            JSONObject jsonObject = JSONObject.parseObject(response);
            System.out.println("kjkjlkjkjkjkj"+response);
            weatherVO = JSON.toJavaObject(jsonObject, WeatherDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.warn("当前城市：{}，当前温度：{}",weatherVO.getCity(),weatherVO.getTem());
        return weatherVO;
    }

    /**
     * 天气 ip api查询
     * @param ip
     * @return
     */
    public WeatherDTO getWeatherByIp(String ip) {
        WeatherDTO weatherVO = null;
        try {
            String response = "";
            if(StringUtils.isNotEmpty(ip)){
                response = HttpClientUtil.sendHttpsGet(api + "?version=" + version + "&appid=" + appid + "&appsecret=" + appsecret + "&ip=" + ip);
            }
            JSONObject jsonObject = JSONObject.parseObject(response);
            weatherVO = JSON.toJavaObject(jsonObject, WeatherDTO.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return weatherVO;
    }
}
