package com.whu.service;

import com.alibaba.fastjson.JSONObject;

public interface UserService {

    String verify(JSONObject cert);

//    String create(String password);


    String genProof(String position);

    String search(String _json);

    String deployContract(String _json, String username);
}

