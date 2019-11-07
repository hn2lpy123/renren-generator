package io.renren.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonUtil {

    private final static Logger log = LoggerFactory.getLogger(JsonUtil.class);

    private static final SerializerFeature[] features = {
            SerializerFeature.DisableCircularReferenceDetect,//打开循环引用检测，JSONField(serialize = false)不循环
            SerializerFeature.WriteMapNullValue, //输出空置字段
            SerializerFeature.WriteNullListAsEmpty,//list字段如果为null，输出为[]，而不是null
            SerializerFeature.WriteNullNumberAsZero,// 数值字段如果为null，输出为0，而不是null
            SerializerFeature.WriteNullBooleanAsFalse,//Boolean字段如果为null，输出为false，而不是null
            SerializerFeature.WriteNullStringAsEmpty//字符类型字段如果为null，输出为""，而不是null
    };
    private static SerializeConfig config;

    static {
        config = new SerializeConfig();
        // JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormatSerializer df = new SimpleDateFormatSerializer(
                "yyyy-MM-dd HH:mm:ss");
        config.put(java.util.Date.class, df);
        config.put(java.sql.Date.class, df);
        config.put(Timestamp.class, df);
    }

    /***
     * 序列化数据
     *
     * @param object
     * @return
     */
    public static String toJSON(Object object) {
        return JSON.toJSONString(object, features);
    }

    /***
     * 反序列化数据
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parse(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /***
     * 反序列化数据
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> T parseNotThrowException(String json, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(json, clazz);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return t;
    }

    /***
     * 反序列化数据
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArray(String json, Class<T> clazz) {
        return JSON.parseArray(json, clazz);
    }

    /***
     * 反序列化数据
     *
     * @param json
     * @param clazz
     * @return
     */
    public static <T> List<T> parseArrayNotThrowException(String json,
                                                          Class<T> clazz) {
        List<T> list = null;
        try {
            list = JSON.parseArray(json, clazz);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return list;
    }

    // 处理结果字符串，转换为map
    @SuppressWarnings("unchecked")
    public static List<Map<String, Object>> res(String result) {
        List<Map<String, Object>> resList = null;
        try {
            resList = JsonUtil.parse(result, List.class);
        } catch (Exception e) {
            log.error("解析返回Json发生异常,原因:" + e.getMessage());
        }
        return resList;
    }


    /**
     * 返回Json字符串里面包含的一个对象
     *
     * @param jsonStr  :{"city":"china","map1":[{"age":"28","name":"yys"}]}
     * @param list_str :map1
     * @param clazz    :Map.class,或者其他对象
     * @param <T>      :Map
     * @return :List<Map>
     */
    public static final <T> List<T> json2childList(String jsonStr, String list_str,
                                                   Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsonStr);
        if (jsonobj == null) {
            return null;
        }
        Object obj = jsonobj.get(list_str);
        if (obj == null) {
            return null;
        }
        if (obj instanceof JSONArray) {
            JSONArray jsonarr = (JSONArray) obj;
            List<T> list = new ArrayList<T>();
            for (int i = 0; i < jsonarr.size(); i++) {
                list.add(jsonarr.getObject(i, clazz));
            }
            return list;
        }
        return null;
    }

    /**
     * 返回Json字符串里面包含的一个对象
     *
     * @param jsonStr :{"department":{"id":"1","name":"生产部"},"password":"admin","username":"admin"}
     * @param obj_str :department
     * @param clazz   :Map.class,或者其他对象
     * @param <T>     :Map
     * @return
     */
    public static final <T> T json2childObj(String jsonStr, String obj_str,
                                            Class<T> clazz) {
        JSONObject jsonobj = JSON.parseObject(jsonStr);
        if (jsonobj == null) {
            return null;
        }

        Object obj = jsonobj.get(obj_str);
        if (obj == null) {
            return null;
        }

        if (obj instanceof JSONObject) {
            return jsonobj.getObject(obj_str, clazz);
        } else {
            log.info(obj.getClass() + "");
        }

        return null;
    }

    /**
     * json 转换成对象
     *
     * @param jsonStr
     * @param clazz
     * @param <T>
     * @return
     */
    public static final <T> T json2obj(String jsonStr, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(jsonStr, clazz);
        } catch (Exception e) {
            log.error("json字符串转换失败！" + jsonStr, e);
        }
        return t;
    }

    /**
     * 对象转换成json字符串(带有格式化输出)
     *
     * @param object       要转换的对象
     * @param prettyFormat 是否格式化json字符串,输出带有换行和缩进的字符串
     * @return 返回一个json 字符串数组
     */
    public static final String obj2json(Object object, boolean prettyFormat) {
        return JSON.toJSONString(object, prettyFormat);
    }

    /**
     * 对象转换成json字符串
     *
     * @param object 要转换的对象
     * @return 返回一个json 字符串数组
     */
    public static final String obj2jsonByFeatures(Object object) {
        return JSON.toJSONString(object, config, features);
    }

    /**
     * 对象转换成json字符串
     *
     * @param object 要转换的对象
     * @return 返回一个json 字符串数组
     */
    public static final String obj2json(Object object) {
        return JSON.toJSONString(object, config);
    }

    /**
     * json 字符串转换成原始的Object对象
     *
     * @param jsonStr
     * @return
     */
    public static final Object json2obj(String jsonStr) {
        return JSON.parse(jsonStr);
    }

    /**
     * json字符串转换成list
     *
     * @param jsonStr    Json字符串
     * @param clazz      要转换的class
     * @param <T>返回的泛型类型
     * @return 返回的<T>泛型类型
     */
    public static <T> List<T> json2List(String jsonStr, Class<T> clazz) {
        List<T> list = new ArrayList<T>();
        try {
            list = JSON.parseArray(jsonStr, clazz);
        } catch (Exception e) {
            log.error("json字符串转List失败！" + jsonStr, e);
        }
        return list;
    }

    /**
     * json字符串转换成Map
     *
     * @param jsonStr Json字符串
     * @return Map
     */
    public static Map<String, Object> json2Map(String jsonStr) {
        try {
            return JSON.parseObject(jsonStr, Map.class);
        } catch (Exception e) {
            log.error("json字符串转换失败！" + jsonStr, e);
        }
        return null;
    }

    /**
     * json 转换成list<map>
     *
     * @param jsonString
     * @return
     */
    public static List<Map<String, Object>> json2ListkeyMap(String jsonString) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = JSON.parseObject(jsonString,
                    new TypeReference<List<Map<String, Object>>>() {
                    });
        } catch (Exception e) {
            log.error("json字符串转map失败", e);
        }
        return list;
    }
}