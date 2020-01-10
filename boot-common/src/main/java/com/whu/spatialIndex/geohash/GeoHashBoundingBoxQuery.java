package com.whu.spatialIndex.geohash;


import java.util.ArrayList;

public class GeoHashBoundingBoxQuery {
    private double[] dLat=new double[41];
    private double[] dLon=new double[41];
    private ArrayList<GeoHash> adjGeoHashs=new ArrayList<>();
    public GeoHashBoundingBoxQuery()
    {
        for(int i=0;i<41;i++)
        {
            dLat[i]=divideLat(i);
            dLon[i]=divideLon(i);
        }

    }

    private double divideLat(int bits)
    {
        return 180/Math.pow(2,bits/2);
    }
    private double divideLon(int bits)
    {
        return 360/Math.pow(2,(bits+1)/2);
    }

    private int numofBitsForOverlappingGeoHash(BoundingBox box)
    {
        int bits=40;//默认值
        double height=box.getLatitudeSize();
        double width=box.getLongitudeSize();
        while((dLat[bits]<height || dLon[bits]<width)&&bits>0){
            bits--;
        }
        System.out.println("fitting Bits:"+bits);
        return bits;
    }

    //由于刚好分割的位数可能不满足5的倍数 ，无法进行base32转换，只能返回其二进制位
    public  ArrayList<String> getOverLapGeoHashBits(BoundingBox box)
    {
        //adjGeoHashs=new ArrayList<>();
        int fittingBits=numofBitsForOverlappingGeoHash(box);//分割的位数
        Point p=box.getCenterPoint();
        GeoHash centerHash=new GeoHash(p.getLon(),p.getLat(),fittingBits);
        if(hashFits(centerHash,box))
        {
            addSearchHash(centerHash);
        }else
        {
            expandSearch(centerHash,box,fittingBits);
        }
        ArrayList<String> strList=getBinryList();
        return strList;
    }

    private boolean hashFits(GeoHash centerHash,BoundingBox b)
    {
        return (centerHash.contains(b.getLowerLeft()) && centerHash.contains(b.getUpperRight()));
    }

    private void addSearchHash(GeoHash g)
    {
        adjGeoHashs.add(g);
    }

    private void expandSearch(GeoHash centerHash,BoundingBox box,int fittingBits)
    {
        addSearchHash(centerHash);
        ArrayList<GeoHash> hashList=centerHash.getAdjacent(fittingBits);//得到周围8个格子
        for(GeoHash adj: hashList)
        {
            BoundingBox adjBox=adj.getGeoHashBox();
            if(adjBox.isIntersect(box))
            {
                addSearchHash(adj);
            }
        }
    }

    private ArrayList<String> getBinryList()
    {
        ArrayList<String> mergBits=new ArrayList<>();
        for(GeoHash g:adjGeoHashs)
        {
            mergBits.add(g.allBits);
        }
        return mergBits;
    }

    private ArrayList<String> getBase32FromHash()
    {
        ArrayList<String> base32Str=new ArrayList<>();
        for(GeoHash g:adjGeoHashs)
        {
            base32Str.add(g.getBase32FromBits());
        }
        return base32Str;
    }

    //矩形查询2：根据选择的范围以及最终分割的GeoHash块大小找出所有覆盖的块编码
//:该方法失败，内存溢出,不单独使用，结合方法三一起使用
    private void addGeoHashFromMinGeohash(BoundingBox box,int numofBits)
    {
        //int numofBits=40;
        double leftBottomLon=box.minLon;
        double leftBottomLat=box.minLat;
        double rightUpperLon=box.maxLon;
        double rightUpperLat=box.maxLat;
        int xStartSize=(int)((leftBottomLon+180)/dLon[numofBits]);
        int xEndSize=(int)((rightUpperLon+180)/dLon[numofBits]);
        int yStartSize=(int)((leftBottomLat+90)/dLat[numofBits]);
        int yEndSize=(int)((rightUpperLat+90)/dLat[numofBits]);
        double minLon;
        double minLat;
        int xdel=xEndSize-xStartSize,ydel=yEndSize-yStartSize;
        for(int x=0;x<=xdel;x++)
        {
            minLon=leftBottomLon+x*dLon[numofBits];
            for(int y=0;y<=ydel;y++)
            {
                minLat=leftBottomLat+y*dLat[numofBits];
                GeoHash geohash=new GeoHash(minLon,minLat,numofBits);
                adjGeoHashs.add(geohash);
            }
        }
	/*
	while(leftBottomLat<rightUpperLat)
	{
		Point endPoint(rightUpperLon,leftBottomLat);
		while(leftBottomLon<rightUpperLon)
		{
			GeoHash geohash(leftBottomLon,leftBottomLat,numofBits);
		    geoHashList.push_back(geohash);
			if(geohash.contains(endPoint))
			{
				break;
			}else
			{
				leftBottomLon+=dLon[numofBits];
				if(leftBottomLon>=rightUpperLon)
				{
					GeoHash geohash(leftBottomLon,leftBottomLat,numofBits);
		            geoHashList.push_back(geohash);
				}
			}
		}
		leftBottomLon=box.minLon;
		leftBottomLat+=dLat[numofBits];
	}
	*/
    }

    //矩形查询3：分割次数刚好凑成5的倍数，采用增加或减少分割次数
    public ArrayList<String> getBoundingGeoHashBase32(BoundingBox box)
    {
        int fittingBits=numofBitsForOverlappingGeoHash(box);//分割的位数
        int residue=fittingBits%5;
        int intVal=fittingBits/5;
        if(residue <=3)
        {
            Point p=box.getCenterPoint();
            GeoHash centerHash=new GeoHash(p.getLon(),p.getLat(),intVal*5);
            if(hashFits(centerHash,box))
            {
                addSearchHash(centerHash);
            }else
            {
                expandSearch(centerHash,box,intVal*5);
            }
        }else
        {
            int divBits=(intVal+1)*5;
            //然后再逐个遍历，找出覆盖的所有栅格
            addGeoHashFromMinGeohash(box,divBits);
        }
        ArrayList<String> base32StrList=getBase32FromHash();
        return base32StrList;
    }
}
