package com.whu.spatialIndex.geohash;

import java.util.ArrayList;

public class GeoHash {
    public String allBits="";
    private int precision;
    private BoundingBox box=new BoundingBox();
    public static char[] base32Table;
    static{
        base32Table=new char[]{'0','1','2','3','4','5','6','7','8','9','b','c','d','e','f','g',
                'h','j','k','m','n','p','q','r','s','t','u','v','w','x','y','z'};// Base32编码表
    }
    public GeoHash(double lon, double lat)
    {
        this.precision=45;//默认设置总的二进制串长度为40位
        setMergeBits(lon,lat,precision);
    }
    public GeoHash(double lon,double lat,int prec)
    {
        this.precision=prec;
        setMergeBits(lon,lat,prec);
    }
    public GeoHash(){
        this.precision=45;
    }

    /**
     * 静态内部类单例模式
     * @returnb
     */
    public static GeoHash getInstance(){
        return Inner.instance;
    }



    private static class Inner {
        private static final GeoHash instance = new GeoHash();
    }
    //第二种方法，根据总的精度直接得到混合后的二进制码
    public void setMergeBits(double lon,double lat,int desiredPrecision)
    {
        double lonRange[]={-180,180};
        double latRange[]={-90,90};
        boolean isEvenBit=true;
        int significantBits=0;
        while(significantBits<desiredPrecision)
        {
            if(isEvenBit)
            {
                divideRangeEncode(lon,lonRange);
            }else
            {
                divideRangeEncode(lat,latRange);
            }
            isEvenBit=!isEvenBit;
            significantBits++;
        }
        this.box.minLon=lonRange[0];
        this.box.maxLon=lonRange[1];
        this.box.minLat=latRange[0];
        this.box.maxLat=latRange[1];
    }
    private void divideRangeEncode(double data,double range[])
    {
        double mid=(range[0]+range[1])/2;
        if(data>mid)
        {
            allBits +='1';
            range[0]=mid;
        }else
        {
            allBits +='0';
            range[1]=mid;
        }
    }
    //将混合后的二进制码转换为base32字符串
    public String getBase32FromBits()
    {
        String encodeStr="";
        int intVal=precision/5;//转化为多少个base32字符
        if (allBits.equals(""))
            return encodeStr;
        char[] bitsArr=allBits.toCharArray();
        int bin2dec;	/* 将binHash中的每5位二进制码编为1个十进制数 */
        for (int k = 0; k < intVal; k++)
        {
            bin2dec = 0;
            for (int i = k * 5; i < (k + 1) * 5; ++i)
            {
                bin2dec = bin2dec * 2 + (bitsArr[i] == '1' ? 1 : 0);
            }
            encodeStr += base32Table[bin2dec];
        }
        return encodeStr;
    }
    public BoundingBox getGeoHashBox()
    {
        return box;
    }

    public String getBinaryHashs()
    {
        return allBits;
    }

    public boolean contains(Point p)
    {
        return box.isContain(p);
    }

    public ArrayList<GeoHash> getAdjacent(int fittingBits)
    {
        //得到周围8个邻居的GeoHash对象
        ArrayList<GeoHash> neighbors=new ArrayList<>();
        double lonSize=box.maxLon-box.minLon;
        double latSize=box.maxLat-box.minLat;
        double midLon=(box.maxLon+box.minLon)/2;
        double leftLon=midLon-lonSize;
        double rightLon=midLon+lonSize;
        double midLat=(box.maxLat+box.minLat)/2;
        double bottomLat=midLat-latSize;
        double upperLat=midLat+latSize;
        //上
        neighbors.add(new GeoHash(midLon,upperLat,fittingBits));
        //下
        neighbors.add(new GeoHash(midLon,bottomLat,fittingBits));
        //左
        neighbors.add(new GeoHash(leftLon,midLat,fittingBits));
        //右
        neighbors.add(new GeoHash(rightLon,midLat,fittingBits));
        //左上
        neighbors.add(new GeoHash(leftLon,upperLat,fittingBits));
        //右上
        neighbors.add(new GeoHash(rightLon,upperLat,fittingBits));
        //左下
        neighbors.add(new GeoHash(leftLon,bottomLat,fittingBits));
        //右下
        neighbors.add(new GeoHash(rightLon,bottomLat,fittingBits));
        return neighbors;
    }

}

