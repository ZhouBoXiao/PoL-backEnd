package com.whu.service;

import com.alibaba.fastjson.JSONObject;

public interface AdminService {


//    String verify(JSONObject cert, String wallet, String password);
//
    String create(String password);
//
//    String genProof(String position);
//
//    String search(String _json, String wallet, String passWord);
//
    boolean deployContract();

    String queryBySpace(String json);

    String queryByTime(String json);
}

