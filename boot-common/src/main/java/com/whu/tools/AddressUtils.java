package com.whu.tools;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

public class AddressUtils {
    public final static void main(String[] args) {
        Map<String, String> map = GetLocationMsg(39.988429, 116.4839);
        System.out.println(JSON.toJSONString(map));
    }

    /**
     *  根据经纬度获取位置信息
     * @param latitude 纬度
     * @param longitude 经度
     * @return
     */
    public static Map<String, String> GetLocationMsg(double latitude, double longitude) {
        String add = "";
        String url = String.format("http://maps.google.cn/maps/api/geocode/json?latlng=%s,%s&language=CN", latitude,
                longitude);
        URL myURL = null;
        URLConnection httpsConn = null;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        try {
            httpsConn = (URLConnection) myURL.openConnection();
            if (httpsConn != null) {
                InputStreamReader insr = new InputStreamReader(httpsConn.getInputStream(), "UTF-8");
                BufferedReader br = new BufferedReader(insr);
                String data = null;
                while ((data = br.readLine()) != null) {
                    add = add + data;
                }
                insr.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(add);

        Map<String, String> map = new HashMap<String, String>();
        JSONObject jsonObject = JSONObject.parseObject(add);
        if (jsonObject.getString("status").equals("OK")) {
            JSONArray jsonArray = JSONArray.parseArray(jsonObject.getString("results"));
            if (jsonArray.size() > 0) {
                JSONObject job = jsonArray.getJSONObject(0);
                JSONArray jar = JSONArray.parseArray(job.getString("address_components"));
                if (jar.size() > 0) {
                    for (int i = 0; i < jar.size(); i++) {
                        JSONObject addjob = jar.getJSONObject(0);
                        String name = addjob.getString("long_name");
                        System.out.println(name);
                        int j = 0;
                        if(job.getString("formatted_address").contains("邮政编码")){
                            j = 1;
                            if(i == jar.size() - 1) {
                                map.put("ZipCode", name);//邮编
                            }
                        }
                        if(i == jar.size() - 4 - j) {
                            map.put("county", name);//县/区
                        }
                        if(i == jar.size() - 3 - j) {
                            map.put("city", name);//市
                        }
                        if(i == jar.size() - 2 - j) {
                            map.put("province", name);//省
                        }
                        if(i == jar.size() - 1 - j) {
                            map.put("country", name);//国
                        }
                    }
                }
            }
        }
        return map;
    }
}