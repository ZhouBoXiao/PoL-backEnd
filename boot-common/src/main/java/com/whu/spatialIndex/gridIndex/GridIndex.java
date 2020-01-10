package com.whu.spatialIndex.gridIndex;

import java.util.ArrayList;

public class GridIndex {
    private double xStart;//X方向上的起始坐标
    private double yStart;//Y方向上的起始坐标
    private double dx;//X方向上的间隔
    private double dy;//y方向上的间隔
    private int xNum;
    private int yNum;
    private ArrayList<GridObject> arrGrids=null;//存储每一个格网对象


    public static GridIndex getInstance(){
        return Inner.instance;
    }

    private static class Inner {
        private static final GridIndex instance = new GridIndex();
    }

    //生成格网索引
    public void createGridIndex(double xStart,double yStart,double xEnd,double yEnd,double dx,double dy){
        this.xStart=xStart;
        this.yStart=yStart;
        this.dx=dx;
        this.dy=dy;
        this.xNum=(int)((xEnd-xStart)/dx);
        this.yNum=(int)((yEnd-yStart)/dy);
        arrGrids=new ArrayList<GridObject>();
        for(int x=0;x<xNum;x++){
            for(int y=0;y<yNum;y++){
                GridObject go=new GridObject(x,y);
                arrGrids.add(go);
            }//内层for循环
        }//外层for循环
    }
    //根据点经纬度，将其存入相应的格网对象中
    public void point2GridObject(double lon,double lat){
        //首先根据经纬度找到对应的格网
        int xSize=(int)((lon-xStart)/dx);
        int ySize=(int)((lat-yStart)/dy);
        GridObject grid=arrGrids.get(xSize*yNum+ySize);
        Point p=new Point(lon,lat);
        grid.gridPoints.add(p);
    }
    //根据空间查询的区域，找出空间内包含的所有点
    public ArrayList<Point> getPointsFromRegion(double xQueryStart,double yQueryStart,double xQueryEnd,double yQueryEnd){
        ArrayList<Point> arrPoints=new ArrayList<Point>();
        int xStartSize=(int)((xQueryStart-xStart)/dx);//最左列
        int xEndSize=(int)((xQueryEnd-xStart)/dx);//最右列
        int yStartSize=(int)((yQueryStart-yStart)/dy);//最下行
        int yEndSize=(int)((yQueryEnd-yStart)/dy);//最上行
        /*当查询范围中有完整的网格时，分为两种情况，首先对于与查询边界相交的网格，将这些网格中的点分别取出，然后返回符合查询条件的点，然后是对于完整的网格，直接返回其中所有的点*/
        if(((xEndSize-xStartSize)>1)&&((yEndSize-yStartSize)>1)){
            /*=================首先对于查询边界相交的网格进行处理，分为四种情况，用四个for循环分别进行处理================*/
            /*====================处理位于下面一行的网格中的点======================*/
            for(int x=xStartSize;x<=xEndSize;x++){
                GridObject grid=arrGrids.get(x*yNum+yStartSize);
                ArrayList<Point> pBottom= getPointsFromGrid(grid,xQueryStart,yQueryStart,xQueryEnd,yQueryEnd);
                arrPoints.addAll(pBottom);
            }
            /*====================处理位于上面一行的网格中的点======================*/
            for(int x=xStartSize;x<=xEndSize;x++){
                GridObject grid=arrGrids.get(x*yNum+yEndSize);
                ArrayList<Point> pTop= getPointsFromGrid(grid,xQueryStart,yQueryStart,xQueryEnd,yQueryEnd);
                arrPoints.addAll(pTop);
            }
            /*====================处理位于左面一行的网格中的点======================*/
            for(int y=yStartSize+1;y<yEndSize;y++){
                GridObject grid=arrGrids.get(xStartSize*yNum+y);
                ArrayList<Point> pLeft= getPointsFromGrid(grid,xQueryStart,yQueryStart,xQueryEnd,yQueryEnd);
                arrPoints.addAll(pLeft);
            }
            /*====================处理位于右面一行的网格中点=======================*/
            for(int y=yStartSize+1;y<yEndSize;y++){
                GridObject grid=arrGrids.get(xEndSize*yNum+y);
                ArrayList<Point> pRight= getPointsFromGrid(grid,xQueryStart,yQueryStart,xQueryEnd,yQueryEnd);
                arrPoints.addAll(pRight);
            }
            /*========然后对完全处于查询范围中的网格进行处理，将这些网格中的点直接返回，添加到查询结果数组中=======*/
            for(int x=yStartSize+1;x<xEndSize;x++){
                for(int y=yStartSize+1;y<yEndSize;y++){
                    GridObject grid=arrGrids.get(x*yNum+y);
                    ArrayList<Point> pAllGrid= grid.gridPoints;
                    arrPoints.addAll(pAllGrid);
                }
            }
            /*===================================================================*/
        }
        /*==========================================================================================*/

        /*==========当查询范围中没有完整的网格时，将与查询范围相交的每个网格都取出，并对每个网格中的点进行比较，返回符合查询条件的点=============*/
        else if(((xEndSize-xStartSize)==1)||((yEndSize-yStartSize)==1)){
            //pQueryResult.clear();//将结果数组清空
            for(int x=xStartSize;x<=xEndSize;x++){
                for(int y=yStartSize;y<=yEndSize;y++){
                    GridObject grid=arrGrids.get(x*yNum+y);
                    ArrayList<Point> pGrid= getPointsFromGrid(grid,xQueryStart,yQueryStart,xQueryEnd,yQueryEnd);
                    arrPoints.addAll(pGrid);
                }
            }
        }
        /*==========================================================================================*/

        /*====================当查询范围在一个网格内时，将这个网格中的点取出，并过滤出符合查询条件的点===========================*/
        else if(((xEndSize-xStartSize)==0)&&((yEndSize-yStartSize)==0)){
            GridObject grid=arrGrids.get(xStartSize*yNum+yEndSize);
            ArrayList<Point> pGrid= getPointsFromGrid(grid,xQueryStart,yQueryStart,xQueryEnd,yQueryEnd);
            arrPoints.addAll(pGrid);
        }
        return arrPoints;
    }
    //判断格网中的点是否在查询范围内
    private ArrayList<Point> getPointsFromGrid(GridObject grid, double xQueryStart, double yQueryStart, double xQueryEnd, double yQueryEnd){
        ArrayList<Point> points=new ArrayList<Point>();
        ArrayList<Point> gridPoints=grid.gridPoints;
        for(Point p:gridPoints){
            if(p.getX()>=xQueryStart && p.getX()<=xQueryEnd && p.getY()>=yQueryStart && p.getY()<=xQueryEnd){
                points.add(p);
            }
        }
        return points;
    }

    public double getxStart() {
        return xStart;
    }

    public void setxStart(double xStart) {
        this.xStart = xStart;
    }

    public double getyStart() {
        return yStart;
    }

    public void setyStart(double yStart) {
        this.yStart = yStart;
    }

    public double getDx() {
        return dx;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public double getDy() {
        return dy;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }

    public int getxNum() {
        return xNum;
    }

    public void setxNum(int xNum) {
        this.xNum = xNum;
    }

    public int getyNum() {
        return yNum;
    }

    public void setyNum(int yNum) {
        this.yNum = yNum;
    }

    public ArrayList<GridObject> getArrGrids() {
        return arrGrids;
    }

    public void setArrGrids(ArrayList<GridObject> arrGrids) {
        this.arrGrids = arrGrids;
    }
}
