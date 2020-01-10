pragma solidity ^0.4.2;

// import "./ExCall.sol";
// import "./SystemContracts/utillib/LibString.sol";
// import "./SystemContracts/utillib/JsmnSolLib.sol";

contract GridIndexCon {

    // using LibString for *;

//     string xStart;//X方向上的起始坐标
// 	string yStart;//Y方向上的起始坐标
// 	string dx;//X方向上的间隔
// 	string dy;//Y方向上的间隔
// 	uint64 xNum;//分割的列数
// 	uint64 yNum;//分割的行数

// 	struct Point{
// 	    string X;
//         string Y;
// 	}

// 	struct GridObject{

// 	    uint64 xGrid;//所在行
//     	uint64 yGrid;//所在列
//     	Point[] gridPoints;//存放每一个格网中的所有点
// 	}

// 	struct Parent {
//         mapping(uint => GridObject) arrGrids;
//         uint Size;
//     }
// 	//GridObject[] arrGrids;//存放分割的所有格网
// 	Parent parent;
	string all;
// 	string str = "gridPoints";
	function set(string json){

	    all = json;
	}
	/*function add(string point, string xy) public constant returns(int){


	    int pos1 = all.indexOf(xy);
	    int pos2 = all.indexOf(str, uint(pos1));

	    all.sp

	    return pos2;
	}*/

	function get() constant returns(string){
	    return all;

	}

	/*function stringToBytes32(string memory source) returns (bytes32 result) {
        assembly {
            result := mload(add(source, 32))
        }
    }

    function bytesToUint(bytes memory b) public constant returns (uint256){

        uint256 number;
        for(uint i= 0; i<b.length; i++){
            number = number + uint8(b[i])*(2**(8*(b.length-(i+1))));
        }
        return number;
    }*/

    // function stringToUint(string s) constant returns (uint64 result) {
    //     bytes memory b = bytes(s);
    //     uint64 i;
    //     result = 0;
    //     for (i = 0; i < b.length; i++) {
    //         uint64 c = uint64(b[i]);
    //         if (c >= 48 && c <= 57) {
    //             result = result * 10 + (c - 48);
    //         }
    //     }
    // }

// 	function createGridIndex(string startX,string startY,string endX,string endY,string _dx,string _dy) public  {
//     	xStart=startX;
//     	yStart=startY;
//     	dx=_dx;
//     	dy=_dy;
//         xNum = stringToUint(exCall("Double", endX.concat(",", startX, ",".concat(_dx))));
//         yNum = stringToUint(exCall("Double", endY.concat(",", startY, ",".concat(_dy))));
//         uint counter=0;
//         parent.Size = 0;

//     	for(uint64 x=0;x<xNum;x++)
//     	{
//     		for(uint64 y=0;y<yNum;y++)
//     		{

//                 GridObject storage go;
//                 go.xGrid = x ;
//                 go.yGrid = y ;
//     // 			arrGrids.push(go);
//     			parent.arrGrids[parent.Size] = go;
//     			parent.Size++;

//     		}

//     	}

//     }
    /*//将点存入格网对象数组中
    function point2GridObject(string lon,string lat){
        //首先根据经纬度找到对应的格网
        uint64 xSize=stringToUint(exCall("Double", lon.concat(",", xStart, ",".concat(dx))));
        uint64 ySize=stringToUint(exCall("Double", lat.concat(",", yStart, ",".concat(dy))));
        Point memory p = Point({X:lon,Y:lat});
    	parent.arrGrids[xSize*yNum+ySize].gridPoints.push(p);

    }

// 	vector<Point> getPointsFromRegion(double,double,double,double);//矩形范围查询

	//获取格网中在查询范围内的点
    function getPointsFromGrid(GridObject storage grid,string xQueryStart,string yQueryStart,string xQueryEnd,string yQueryEnd) internal returns (string _json, uint64 counter)
    {
        _json = "";
        // uint64 counter = 0;
        counter = 0;
        string memory tmp;
    	for(uint64 i=0;i<grid.gridPoints.length;i++)
    	{
    		Point storage p=grid.gridPoints[i];
    		if((p.X.compare(xQueryStart) >= 0) && (p.X.compare(xQueryEnd) <= 0) &&( p.Y.compare(yQueryStart) >= 0) && (p.Y.compare(yQueryEnd) <= 0 ))
    		{
    			if (counter > 0) {
                    _json = _json.concat(",");
                }
                _json = _json.concat("{\"X\":");
                _json = _json.concat(p.X);
                _json = _json.concat(",\"Y\":");
                _json = _json.concat(p.Y);
                _json = _json.concat("}");
                counter++;
    		}
    	}

    }*/


    // function uint64ToString(uint64 v) constant returns (string str) {
    //     uint maxlength = 100;
    //     bytes memory reversed = new bytes(maxlength);
    //     uint64 i = 0;
    //     while (v != 0) {
    //         uint remainder = v % 10;
    //         v = v / 10;
    //         reversed[i++] = byte(48 + remainder);
    //     }
    //     bytes memory s = new bytes(i + 1);
    //     for (uint64 j = 0; j <= i; j++) {
    //         s[j] = reversed[i - j];
    //     }
    //     str = string(s);
    // }

    // function itemskPush(string _items, uint64 _total) constant private returns(string _json) {

    //     _json = _json.concat("{\"total\":");
    //     //len = LibStack.appendKeyValue("result", "true");
    //     _json = _json.concat(uint64ToString(_total));
    //     _json = _json.concat(",\"data\":[");
    //     _json = _json.concat(_items);
    //     _json = _json.concat("]}");

    // }
}
