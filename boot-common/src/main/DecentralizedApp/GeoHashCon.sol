pragma solidity ^0.4.2;

import "./SystemContracts/utillib/Strings.sol";
import "./SystemContracts/utillib/LibString.sol";

// library D {
//     struct Label {
//         bytes data;
//         uint64 length;
//     }
//     struct Edge {
//         string node;
//         Label label;
//     }
//     struct Node {
//         Edge[32] children;
//     }
// }


// library Utils {
//     /// Returns a label containing the longest common prefix of `check` and `label`
//     /// and a label consisting of the remaining part of `label`.
//     function splitCommonPrefix(D.Label memory label, D.Label memory check) internal constant returns (D.Label memory prefix, D.Label memory labelSuffix) {
//         return splitAt(label, commonPrefix(check, label));
//     }
//     /// Splits the label at the given position and returns prefix and suffix,
//     /// i.e. prefix.length == pos and prefix.data . suffix.data == l.data.
//     function splitAt(D.Label memory l, uint pos) internal constant returns (D.Label memory prefix, D.Label memory suffix) {
//         if (!(pos <= l.length && pos <= 64 )){
//             throw;
//         }
//         prefix.length = pos;
//         if (pos == 0) {
//             prefix.data = bytes8(0);
//         } else {
//             prefix.data = l.data & ~bytes8((uint64(1) << (64 - pos)) - 1);
//         }
//         suffix.length = l.length - pos;
//         suffix.data = l.data << pos;
//     }
//     /// Returns the length of the longest common prefix of the two labels.
//     function commonPrefix(D.Label memory a, D.Label memory b) internal constant returns (uint prefix) {
//         uint length = a.length < b.length ? a.length : b.length;
//         // TODO: This could actually use a "highestBitSet" helper
//         uint diff = uint64(a.data ^ b.data);
//         uint mask = 1 << 63;
//         for (; prefix < length; prefix++)
//         {
//             if ((mask & diff) != 0)
//                 break;
//             diff += diff;
//         }
//     }
//     /// Returns the result of removing a prefix of length `prefix` bits from the
//     /// given label (i.e. shifting its data to the left).
//     function removePrefix(D.Label memory l, uint prefix) internal constant returns (D.Label memory r) {
//         //require(prefix <= l.length, "Bad lenght");
//         if (!prefix <= l.length){
//             throw;
//         }
//         r.length = l.length - prefix;
//         r.data = l.data << prefix;
//     }
//     /// Removes the first bit from a label and returns the bit and a
//     /// label containing the rest of the label (i.e. shifted to the left).
//     // function chopFirstBit(D.Label memory l) internal constant returns (uint firstBit, D.Label memory tail) {
//     //     // require(l.length > 0, "Empty element");
//     //     if (!l.length > 0){
//     //         throw;
//     //     }
//     //     return (uint(l.data >> 63), D.Label(l.data << 1, l.length - 1));
//     // }

//     function getIndex(bytes8 data) internal constant returns(uint){

//         if (uint(data) == 48){  //0
//             return 0;
//         }else if (uint(data) == 49){  //1
//             return 1;
//         }else if (uint(data) == 50){  //2
//             return 2;
//         }else if (uint(data) == 51){  //3
//             return 3;
//         }else if (uint(data) == 52){  //4
//             return 4;
//         }else if (uint(data) == 53){  //5
//             return 5;
//         }else if (uint(data) == 54){  //6
//             return 6;
//         }else if (uint(data) == 55){  //7
//             return 7;
//         }else if (uint(data) == 56){  //8
//             return 8;
//         }else if (uint(data) == 57){  //9
//             return 9;
//         }else if (uint(data) == 98){  //b
//             return 10;
//         }else if (uint(data) == 99){  //c
//             return 11;
//         }else if (uint(data) == 100){ //d
//             return 12;
//         }else if (uint(data) == 101){ //e
//             return 13;
//         }else if (uint(data) == 102){ //f
//             return 14;
//         }else if (uint(data) == 103){ //g
//             return 15;
//         }else if (uint(data) == 104){ //h
//             return 16;
//         }else if (uint(data) == 106){ //j
//             return 17;
//         }else if (uint(data) == 107){ //k
//             return 18;
//         }else if (uint(data) == 109){ //m
//             return 19;
//         }else if (uint(data) == 110){ //n
//             return 20;
//         }else if (uint(data) == 112){ //p
//             return 21;
//         }else if (uint(data) == 113){ //q
//             return 22;
//         }else if (uint(data) == 114){ //r
//             return 23;
//         }else if (uint(data) == 115){ //s
//             return 24;
//         }else if (uint(data) == 116){ //t
//             return 25;
//         }else if (uint(data) == 117){ //u
//             return 26;
//         }else if (uint(data) == 118){ //v
//             return 27;
//         }else if (uint(data) == 119){ //w
//             return 28;
//         }else if (uint(data) == 120){ //x
//             return 29;
//         }else if (uint(data) == 121){ //y
//             return 30;
//         }else if (uint(data) == 122){ //z
//             return 31;
//         }
//     }

//     function chopFirstChar(D.Label memory l) internal constant returns (uint firstBit, D.Label memory tail) {
//         if (!l.length > 0){
//             throw;
//         }
//         return (getIndex(l.data >> 56), D.Label(l.data << 8, l.length - 8));
//     }

//     /// Returns the first bit set in the bitfield, where the 0th bit
//     /// is the least significant.
//     /// Throws if bitfield is zero.
//     /// More efficient the smaller the result is.
//     function lowestBitSet(uint bitfield) internal constant returns (uint bit) {
//         // require(bitfield != 0, "Bad bitfield");
//         if (!bitfield != 0){
//             throw;
//         }
//         bytes8 bitfieldBytes = bytes8(bitfield);
//         // First, find the lowest byte set
//         uint byteSet = 0;
//         for (; byteSet < 8; byteSet++) {
//             if (bitfieldBytes[7 - byteSet] != 0)
//                 break;
//         }
//         uint64 singleByte = uint64(uint8(bitfieldBytes[7 - byteSet]));
//         uint64 mask = 1;
//         for (bit = 0; bit < 64; bit ++) {
//             if ((singleByte & mask) != 0)
//                 return 8 * byteSet + bit;
//             mask += mask;
//         }
//         // assert(false);
//         return 0;
//     }
//     /// Returns the value of the `bit`th bit inside `bitfield`, where
//     /// the least significant is the 0th bit.
//     function bitSet(uint64 bitfield, uint64 bit) internal constant returns (uint64) {
//         return (bitfield & (uint64(1) << bit)) != 0 ? 1 : 0;
//     }
// }

contract GeoHashCon{

    using Strings for *;
    using LibString for *;
    // // Mapping of hash of key to value
    // mapping (bytes8 => string) values;
    // // Particia tree nodes (hash to decoded contents)
    // mapping (bytes8 => D.Node) nodes;
    // // The current root hash, keccak256(node(path_M('')), path_M(''))
    // bytes8 public root;
    // D.Edge rootEdge;

    mapping (string => string) geo;


    struct Data {
        mapping(string => bool) flags;
        string[] paths;
    }


    // struct Node {
    //     string[] paths;
    // }
    mapping(string => Data) index;

    // // TODO also return the proof
    // function insert(bytes8 memory key, string memory value) public {
    //     D.Label memory k = D.Label(key, 64);
    //     // bytes32 valueHash = keccak256(value);
    //     values[key].push(value);
    //     // keys.push(key);
    //     D.Edge memory e;
    //     if (rootEdge.node == 0 && rootEdge.label.length == 0)
    //     {
    //         // Empty Trie
    //         e.label = k;
    //         e.node = value;
    //     }
    //     else
    //     {
    //         e = insertAtEdge(rootEdge, k, value);
    //     }
    //     //root = edgeHash(e);//??
    //     rootEdge = e;
    // }

    // function getNode(bytes32 hash) public constant returns (uint, bytes32, bytes32, uint, bytes32, bytes32) {
    //     D.Node memory n = nodes[hash];
    //     return (
    //         n.children[0].label.length, n.children[0].label.data, n.children[0].node,
    //         n.children[1].label.length, n.children[1].label.data, n.children[1].node
    //     );
    // }

    // function getRootEdge() public constant returns (uint64, bytes8, bytes8) {
    //     return (rootEdge.label.length, rootEdge.label.data, rootEdge.node);
    // }


    // function insertAtNode(bytes32 nodeHash, D.Label memory key, bytes32 value) internal returns (bytes32) {
    //     // require(key.length > 1, "Bad key");
    //     D.Node memory n = tree.nodes[nodeHash];
    //     (uint256 head, D.Label memory tail) = Utils.chopFirstBit(key);
    //     n.children[head] = _insertAtEdge(tree, n.children[head], tail, value);
    //     return _replaceNode(tree, nodeHash, n);
    // }

    // function insertAtEdge(D.Edge memory e, D.Label memory key, string memory value) internal returns (D.Edge memory) {
    //     // require(key.length >= e.label.length, "Key lenght mismatch label lenght");
    //     // if (!key.length >= e.label.length){
    //     //     throw;
    //     // }
    //     (D.Label memory prefix, D.Label memory suffix) = Utils.splitCommonPrefix(key, e.label);
    //     string newNodeHash;
    //     if (suffix.length == 0) {
    //         // Full match with the key, update operation
    //         // newNodeHash = value;
    //         values[key.data] = LibString.concat(values[key.data], "," , value);
    //     } else if (prefix.length >= e.label.length) {
    //         // Partial match, just follow the path
    //         newNodeHash = insertAtNode(e.node, suffix, value);
    //     } else {
    //         // Mismatch, so let us create a new branch node.
    //         (uint256 head, D.Label memory tail) = Utils.chopFirstChar(suffix);
    //         D.Node memory branchNode;
    //         branchNode.children[head] = D.Edge(value, tail);
    //         branchNode.children[1 - head] = D.Edge(e.node, Utils.removePrefix(e.label, prefix.length + 1));
    //         newNodeHash = insertNode(branchNode);
    //     }
    //     return D.Edge(newNodeHash, prefix);
    // }

    // function insertNode(D.Node memory n) internal returns (bytes32 newHash) {
    //     bytes32 h = hash(n);
    //     nodes[h].children[0] = n.children[0];
    //     nodes[h].children[1] = n.children[1];
    //     return h;
    // }

    // function insert1(string part) public constant returns (string _json){

    //     var s = part.toSlice();
    //     var delim = ",".toSlice();
    //     var parts = new string[](s.count(delim) + 1);
    //     for(uint i = 0; i < parts.length - 1; i++) {
    //         parts[i] = s.split(delim).toString();
    //         _json = _json.concat(  parts[i], "," );
    //     }

    // }

    // function insert2(string geohash, string cert) public constant returns(string _json){


    //     string memory tmp;
    //     for (uint i = 0 ; i <bytes(geohash).length ; i++){
    //         tmp = geohash.substr(0, i+1);
    //         _json = _json.concat( tmp, "," );
    //     }

    // }

    // function contains(string value) returns (bool)
    // {
    //     return self.flags[value];
    // }

    // string _json1;

    function insert(string geohash, string cert) public {

        geo[geohash] = geo[geohash].concat( cert, "," );
        string memory tmp="";
        string memory cur="";
        for (uint i = 0 ; i <bytes(geohash).length; i++){
            if(i==0){
                tmp = geohash.substr(0, i+1);
                // index[tmp] = index[tmp].concat( geohash, "," );
                // index[tmp].push();
            }else{
                cur = geohash.substr(0, i+1);
                if (!index[tmp].flags[cur]){
                    index[tmp].flags[cur] = true;
                    index[tmp].paths.push(cur);
                }
                tmp = cur; //上一个前缀
            }
        }


    }
    function print(string part) public constant returns(string _json){

        uint len = index[part].paths.length;
        for (uint i = 0; i< len;i++ ){

            _json = _json.concat(index[part].paths[i], ",");
        }


    }

    function Query(string part)  public constant returns(string _json){

        uint len = index[part].paths.length;

        for (uint i = 0; i< len;i++ ){

            string pre = index[part].paths[i];
            if (bytes(pre).length == 9){
                _json = _json.concat(pre, ",");

            }
            else{
                _json = _json.concat(Query(pre));
            }

        }
        return _json;
    }
    function BoundingBoxQuery(string prefixs) public constant returns(string _json){
        //得到geohash的前缀列表
        // string prefixs = exCall("boundingBox", location);
        _json = "";

        if (bytes(prefixs).length == 9){
            return geo[prefixs];
        }
        var geohashes = Query(prefixs);
        var s = geohashes.toSlice();
        var delim = ",".toSlice();
        var parts = new string[](s.count(delim) + 1);
        for(uint i = 0; i < parts.length - 1; i++) {
            parts[i] = s.split(delim).toString();

            _json = _json.concat(geo[parts[i]]);
        }

    }


    // function qe(string part) public constant returns(string){
    //     _json1 = "";
    //     Query(part);
    //     return _json1;
    // }
    // function query(string part)  public constant returns(string _json){
    //     _json="";
    //     if(bytes(index[part]).length!=0){
    //         var s = index[part].toSlice();
    //         var delim = ",".toSlice();
    //         var parts = new string[](s.count(delim) + 1);
    //         if (s.count(delim) > 0){
    //             for(uint i = 0; i < parts.length - 1; i++) {
    //                 parts[i] = s.split(delim).toString();
    //                 _json = _json.concat(  geo[parts[i]]);
    //             }
    //         }
    //     }

    // }

    // function BoundingBoxQuery(string prefixs) public constant returns(string _json){
    //     //得到geohash的前缀列表
    //     // string prefixs = exCall("boundingBox", location);
    //     _json = "";
    //     var s = prefixs.toSlice();
    //     var delim = ",".toSlice();
    //     var parts = new string[](s.count(delim) + 1);
    //     for(uint i = 0; i < parts.length; i++) {
    //         parts[i] = s.split(delim).toString();

    //         _json = _json.concat(Query(parts[i]));
    //     }

    // }


}