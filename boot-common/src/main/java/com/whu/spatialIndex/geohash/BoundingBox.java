package com.whu.spatialIndex.geohash;

public class BoundingBox {
    public double minLon,minLat,maxLon,maxLat;
    BoundingBox(){

    }
    public BoundingBox(double minLongitude, double minLatitude, double maxLongitude, double maxLatitude)
    {
        this.minLon=minLongitude;
        this.minLat=minLatitude;
        this.maxLon=maxLongitude;
        this.maxLat=maxLatitude;
    }

    public Point getCenterPoint(){
        double centerLon=(minLon+maxLon)/2;
        double centerLat=(minLat+maxLat)/2;
        return new Point(centerLon,centerLat);
    }

    public double getLongitudeSize()
    {
        return maxLon-minLon;
    }

    public double getLatitudeSize()
    {
        return maxLat-minLat;
    }

    public boolean isContain(Point p)
    {
        if(p.getLon()>=minLon && p.getLon()<=maxLon && p.getLat()>=minLat && p.getLat()<=maxLat)
        {
            return true;
        }else
        {
            return false;
        }
    }
    public boolean isIntersect(BoundingBox box)
    {
        return !(box.minLon>maxLon || box.maxLon<minLon || box.minLat>maxLat || box.maxLat<minLat);
    }

    public Point getLowerLeft()
    {
        return new Point(minLon,minLat);
    }

    public Point getUpperRight()
    {
        return new Point(maxLon,maxLat);
    }
}
