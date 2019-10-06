package com.dudu.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.battcn.swagger.properties.ApiDataType;
import com.battcn.swagger.properties.ApiParamType;
import com.dudu.domain.LearnResouce;
import com.dudu.jni.NativeLib;
import com.dudu.service.LearnService;
import com.dudu.service.VerifyService;
import com.dudu.tools.ServletUtil;
import com.dudu.tools.StringUtil;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/PoL")
@Api(tags = "1.1", description = "后台", value = "后台")
public class VerifyController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private VerifyService verifyService;


    @RequestMapping("/uuid")
    @ResponseBody
    public Map<String, Object> greeting() {
        Map<String, Object> result = new HashMap<>();
        String uuid = NativeLib.generateUUID();
        result.put("generateUUID",uuid);
        return result;
    }

    @RequestMapping(value = "/verify",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "验证证书")
    @ApiImplicitParam(name = "json", value = "证书", dataType = ApiDataType.STRING, paramType = ApiParamType.FORM)
    public Map<String, Object> verify(@RequestParam String json){
        String res = null;
        Map<String, Object> jo = new HashMap<>();
        logger.info("json: " + json );
//        String walletfile = "/home/lmars/PoL/PoL-Juice/ju-ethereum/data/keys/826b383b-96be-6651-dd23-619231977052.json";
//        String walletfile = request.getParameter("walletfile");
//        String password = request.getParameter("password");
//        String password = "12345678";

        JSONObject cert = JSON.parseObject(json);

        res = verifyService.verify(cert);

        if(res == null || "".equals(res)) {
            jo.put("result", false);
            jo.put("message", "证书验证失败!");
        }else{
            jo.put("message", JSON.parseObject(res));
            jo.put("result", true);
        }
        return jo;
    }
    /**
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/generateWallet",method = RequestMethod.GET)
    public void generateWallet(HttpServletRequest request , HttpServletResponse response){
        JSONObject result=new JSONObject();
        String password = request.getParameter("password");
        if(StringUtil.isNull(password)){
            result.put("message", "不能为空!");
            result.put("result", false);
            ServletUtil.createSuccessResponse(200, result, response);
            return;
        }
        logger.info("password:  "+password);
        String fileName = verifyService.create(password);
        if(!StringUtil.isNull(fileName)) {
            result.put("message", fileName);
            result.put("result", true);
        }else{
            result.put("result", false);
            result.put("message", "钱包创建失败!");
        }
        ServletUtil.createSuccessResponse(200, result, response);
    }



    @RequestMapping(value = "/genProof" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Map<String, Object> genProof(String position){

        String res = null;
        Map<String, Object> result = new HashMap<>();
        logger.info("position:  "+position);
        if(StringUtil.isNull(position)){
            result.put("message", "不能为空!");
            result.put("result", false);
            return result;
        }

        res = verifyService.genProof(position);
        if(res == null || "".equals(res)) {
            result.put("result", false);
            result.put("message", "证明生成失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        return result;
    }

    @RequestMapping(value = "/search" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ApiOperation(value = "查询证书")
    @ApiImplicitParam(name = "json", value = "查询", dataType = ApiDataType.STRING, paramType = ApiParamType.FORM)
    public Map<String, Object> search(String json){
        Map<String, Object> result = new HashMap<>();

        String res = verifyService.search(json);
        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "查询失败!");
        }
        else {
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        return result;
    }

    @RequestMapping(value = "/createContract" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    public Map<String, Object> createContract(String json){
        String res = null;
        Map<String, Object> result = new HashMap<>();
        logger.info("position:  "+json);
        if(StringUtil.isNull(json)){
            result.put("message", "不能为空!");
            result.put("result", false);
            return result;
        }

        res = verifyService.createContract(json);
        if("".equals(res) || res==null){
            result.put("result", false);
            result.put("message", "部署合约失败!");
        }else{
            result.put("result", true);
            result.put("message", JSON.parseObject(res));
        }
        return result;
    }


    /**
     *
     * @param request
     * @param response
     */
//    @RequestMapping(value = "/update",method = RequestMethod.POST)
//    public void updateLearn(HttpServletRequest request , HttpServletResponse response){
//        JSONObject result=new JSONObject();
//        String id = request.getParameter("id");
//        LearnResouce learnResouce=learnService.queryLearnResouceById(Long.valueOf(id));
//        String author = request.getParameter("author");
//        String title = request.getParameter("title");
//        String url = request.getParameter("url");
//        if(StringUtil.isNull(author)){
//            result.put("message","作者不能为空!");
//            result.put("flag",false);
//            ServletUtil.createSuccessResponse(200, result, response);
//            return;
//        }
//        if(StringUtil.isNull(title)){
//            result.put("message","教程名称不能为空!");
//            result.put("flag",false);
//            ServletUtil.createSuccessResponse(200, result, response);
//            return;
//        }
//        if(StringUtil.isNull(url)){
//            result.put("message","地址不能为空!");
//            result.put("flag",false);
//            ServletUtil.createSuccessResponse(200, result, response);
//            return;
//        }
//        learnResouce.setAuthor(author);
//        learnResouce.setTitle(title);
//        learnResouce.setUrl(url);
//        int index=learnService.update(learnResouce);
//        System.out.println("修改结果="+index);
//        if(index>0){
//            result.put("message","教程信息修改成功!");
//            result.put("flag",true);
//        }else{
//            result.put("message","教程信息修改失败!");
//            result.put("flag",false);
//        }
//        ServletUtil.createSuccessResponse(200, result, response);
//    }
    /**
     * 删除教程
     * @param request
     * @param response
     */
//    @RequestMapping(value="/delete",method = RequestMethod.POST)
//    @ResponseBody
//    public void deleteUser(HttpServletRequest request , HttpServletResponse response){
//        String ids = request.getParameter("ids");
//        System.out.println("ids==="+ids);
//        JSONObject result = new JSONObject();
//        //删除操作
//        int index = learnService.deleteByIds(ids.split(","));
//        if(index>0){
//            result.put("message","教程信息删除成功!");
//            result.put("flag",true);
//        }else{
//            result.put("message","教程信息删除失败!");
//            result.put("flag",false);
//        }
//        ServletUtil.createSuccessResponse(200, result, response);
//    }
}