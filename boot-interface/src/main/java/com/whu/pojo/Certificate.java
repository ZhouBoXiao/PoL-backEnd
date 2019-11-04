package com.whu.pojo;

import com.alibaba.fastjson.JSONArray;
import lombok.Data;

@Data
public class Certificate {
    String id;
    String type;
    String prover;
    String issuanceDate;
    JSONArray location;
    JSONArray witnesses;
}
