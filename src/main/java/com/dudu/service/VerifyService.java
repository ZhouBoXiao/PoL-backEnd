package com.dudu.service;

import com.alibaba.fastjson.JSONObject;

public interface VerifyService {

    String verify(JSONObject cert);

    String create(String password);

    String genProof(String position);

    String createContract(String json);

    String search(String _json);
}

