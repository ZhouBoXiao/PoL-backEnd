package com.whu.web.controller;


import java.util.List;
import io.swagger.annotations.*;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.languages.SpringCodegen", date = "2019-12-16T02:02:32.083Z")

@Api(value = "user", description = "the user API")
public interface UserApi {

    @RequestMapping(value = "/verify",  method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "验证证书", notes="验证证书",  httpMethod = "POST", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "证书", required = true, paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    Map<String, Object> verify(@RequestParam("json") String json);

    @RequestMapping(value = "/genProof",method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "生成证明" , notes="生成证明", httpMethod = "POST",produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "position", value = "位置" ,required=true, paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    Map<String, Object> genProof( @RequestParam("position") String position);

    @RequestMapping(value = "/search" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "查询某个用户的证书", notes="查询某个用户的证书", httpMethod = "POST", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "json", value = "查询", required = true, paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })
    Map<String, Object> search(@RequestParam("json") String json);

    @RequestMapping(value = "/deployContract" ,method = RequestMethod.POST, produces="application/json;charset=UTF-8")
    @ResponseBody
    @ApiOperation(value = "部署智能合约", notes="部署智能合约", httpMethod = "POST", produces="application/json")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "wallet", value = "钱包", required = true, paramType="form"),
            @ApiImplicitParam(name = "username", value = "用户名", required = true, paramType="form")
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful operation") })

    Map<String, Object> deployContract(
            @NotNull @ApiParam(value = "钱包" ,required=true ) @Valid @RequestParam("wallet") String wallet,
            @NotNull @ApiParam(value = "用户名" ,required=true ) @Valid @RequestParam("username") String username);
}
