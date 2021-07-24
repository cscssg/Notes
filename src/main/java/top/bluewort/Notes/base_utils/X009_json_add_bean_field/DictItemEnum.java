package top.bluewort.Notes.base_utils.X009_json_add_bean_field;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Iterator;

/**
 * json格式化  字典项自动添加
 */
@Component
public class DictItemEnum {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private final static String[] enums = new String[]{"anesthesia", "carType", "fishTankNum", "addAxy"
            , "warmEquip", "monitorEquip", "recycleWater", "followCar","carLength"};
    private final static String joinStr = "Name";
    private final static int deepLen = 4;

    /**
     * 增加字典项
     *
     * @param oldStr
     * @return
     */
    public Object addDictItem(String oldStr) {
        int deep = 0;
        //1. 判断解析（数组或键值对）
        if (isJsonObject(oldStr)){
            return getJSONObj(JSONObject.parseObject(oldStr, Feature.IgnoreAutoType),deep);
        }
        //2. 解析对象key值
        if (isJsonArray(oldStr)){
            return getJSONOArr(JSONArray.parseArray(oldStr),deep);
        }
        //3. 匹配增加对象key值
        return oldStr;
    }
    private JSONObject getJSONObj(JSONObject jsonObject, int deep){
        ++deep;
        if(deep>=deepLen){
            return jsonObject;
        }
        //1. 判断是否包含jsonarray 类型 包含则递归深入
        Iterator<String> iterator = jsonObject.keySet().iterator();
        while (iterator.hasNext()){
            String key = iterator.next();
            if(childIsJsonArray(jsonObject,key)){
                jsonObject.put(key,getJSONOArr(jsonObject.getJSONArray(key),deep));
            }
        }
        //2. 递归判断key是否存在 存在则进行拼参数
        for(String key:enums) {
            if(jsonObject.containsKey(key)){
                //3. 查询redis 缓存塞入参数值
                 jsonObject.put(key+joinStr,redisTemplate.opsForValue().get("DICTITEM:" + jsonObject.get(key)));
            }
        }
        //4. 返回
        return jsonObject;
    }
    private JSONArray getJSONOArr(JSONArray jsonArray, int deep){
        ++deep;
        if(deep>=deepLen){
            return jsonArray;
        }
        //1. 判断子项是否为jsonobj类型 是的话递归深入
        if(childIsJsonObject(jsonArray)){
            int size = jsonArray.size();
            for(int i = 0;i<size;i++){
                jsonArray.add(i,getJSONObj(jsonArray.getJSONObject(i),deep));
            }
        }
        //2. 返回
        return jsonArray;
    }

    /**
     * 判断子项是否  jsonObject
     *
     * @param jsonArray
     * @return
     */
    private static boolean childIsJsonObject(JSONArray jsonArray) {
        if (ObjectUtils.isEmpty(jsonArray))
            return false;
        try {
            jsonArray.getJSONObject(0);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 判断子项是否jsonarray 对象
     *
     * @param key jsonObject
     * @return
     */
    private static boolean childIsJsonArray(JSONObject jsonObject, String key) {
        if (!jsonObject.containsKey(key))
            return false;
        try {
            jsonObject.getJSONArray(key);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 判断字符串是否可以转化为json对象
     *
     * @param content
     * @return
     */
    private static boolean isJsonObject(String content) {
        // 此处应该注意，不要使用StringUtils.isEmpty(),因为当content为"  "空格字符串时，JSONObject.parseObject可以解析成功，
        // 实际上，这是没有什么意义的。所以content应该是非空白字符串且不为空，判断是否是JSON数组也是相同的情况。
        if (StringUtils.isBlank(content))
            return false;
        try {
            JSONObject jsonStr = JSONObject.parseObject(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 判断字符串是否可以转化为JSON数组
     *
     * @param content
     * @return
     */
    private static boolean isJsonArray(String content) {
        if (StringUtils.isBlank(content))
            return false;
        StringUtils.isEmpty(content);
        try {
            JSONArray jsonStr = JSONArray.parseArray(content);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
