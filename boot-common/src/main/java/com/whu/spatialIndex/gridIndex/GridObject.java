package com.whu.spatialIndex.gridIndex;

import java.util.ArrayList;

public class GridObject {
    private int axGrid;//表示第几行元素
    private int ayGrid;//表示第几列元素
    ArrayList<Point> gridPoints;
    //public int pSize;
    //public long bSize;
    public GridObject(){
        this.axGrid=0;
        this.ayGrid=0;
        this.gridPoints=new ArrayList<Point>();
        //this.pSize = 0;
        //this.bSize = 0;
    }
    public GridObject(int axGrid,int ayGrid){
        this.axGrid=axGrid;
        this.ayGrid=ayGrid;
        this.gridPoints=new ArrayList<Point>();
        //this.pSize = 0;
        //this.bSize = 0;
    }
//    public String toString(){
//        return this.xGrid+","+this.ayGrid+","+this.pAL.size()+","+this.bSize;
//    }


    public int getAxGrid() {
        return axGrid;
    }

    public void setAxGrid(int axGrid) {
        this.axGrid = axGrid;
    }

    public int getAyGrid() {
        return ayGrid;
    }

    public void setAyGrid(int ayGrid) {
        this.ayGrid = ayGrid;
    }

    public ArrayList<Point> getGridPoints() {
        return gridPoints;
    }

    public void setGridPoints(ArrayList<Point> gridPoints) {
        this.gridPoints = gridPoints;
    }
}
