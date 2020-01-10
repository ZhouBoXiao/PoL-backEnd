package com.whu.spatialIndex.gridIndex;

import com.alibaba.fastjson.JSON;

public class Test {
    public static void main(String[] args) {
        //将中国范围都向外增1：http://www.gaosan.com/gaokao/231977.html
        double xStart=113;//X方向上的起始坐标
        double yStart=29;//Y方向上的起始坐标
        double xEnd=116;
        double yEnd=32;
        double dx=0.05;//
        double dy=0.05;//
        GridIndex gridIndex=new GridIndex();
        gridIndex.createGridIndex(xStart,yStart,xEnd,yEnd,dx,dy);
        String objectToJson = JSON.toJSONString(gridIndex.getArrGrids());
        System.out.println(objectToJson);
        gridIndex.point2GridObject(113.5,29.1);
        gridIndex.point2GridObject(113.4,29.2);
        gridIndex.point2GridObject(113.2,30.4);
        gridIndex.point2GridObject(113.1,30.1);
        String objectToJson1 = JSON.toJSONString(gridIndex.getArrGrids());
        System.out.println(objectToJson1);
        System.out.println(objectToJson.equals(objectToJson1));
//        System.out.println(gridIndex.xNum);
//        System.out.println(gridIndex.yNum);
//        System.out.println(gridIndex.arrGrids.size());
    }
}
