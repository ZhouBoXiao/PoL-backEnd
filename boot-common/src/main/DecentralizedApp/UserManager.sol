pragma solidity ^0.4.2;
import "./SystemContracts/utillib/LibString.sol";


contract UserManager{
    using LibString for * ;
    struct itmap {
    	//username ->
    	mapping(string => IndexValue) data;
    	KeyFlag[] keys;
    	uint size;
    }
    struct IndexValue {
    	uint keyIndex;
    	string value;  //contract address
    	string addr;  // user address
    }
    struct KeyFlag {
    	string key; // user address
    	bool deleted;
    }
    itmap self;
    string userList="";
    string contractList="";

    function insert(string key, string value, string addr) returns(bool replaced) {
    	uint keyIndex = self.data[key].keyIndex;
    	self.data[key].value = value;
    	self.data[key].addr = addr;
    	userList = userList.concat(addr, ",");  //user address list
    	contractList = contractList.concat(value, ","); // contract address list
    	if (keyIndex > 0){

    		return true;
    	}
    	else {
    		keyIndex = self.keys.length++;
    		self.data[key].keyIndex = keyIndex + 1;
    		self.keys[keyIndex].key = key;
    		self.size++;
    		return false;
    	}
    }

    function remove(string key) returns(bool success) {
    	uint keyIndex = self.data[key].keyIndex;
    	if (keyIndex == 0)
    		return false;
    	delete self.data[key];
    	self.keys[keyIndex - 1].deleted = true;
    	self.size--;
    }

    function getContractByUsername(string key) public constant returns(string value) {

        if (self.data[key].keyIndex >= 0) {
            value = self.data[key].value;
        }
        else{
            value = "";
        }
    }

    function contains(string key) returns(bool) {
    	return self.data[key].keyIndex > 0;
    }

    function iterate_start() returns(uint keyIndex) {
    	return iterate_next( uint(-1));
    }

    function iterate_valid( uint keyIndex) returns(bool) {
    	return keyIndex < self.keys.length;
    }

    function iterate_next(uint keyIndex) returns(uint r_keyIndex) {
    	keyIndex++;
    	while (keyIndex < self.keys.length && self.keys[keyIndex].deleted)
    		keyIndex++;
    	return keyIndex;
    }

    function iterate_get(uint keyIndex) returns(string key, string value, string addr) {
    	key = self.keys[keyIndex].key;
    	value = self.data[key].value;
    	addr = self.data[key].addr;
    }

    function getUserList() public constant returns(string) {
        return userList;
    }

    function getConAddressList() public constant returns(string ) {
        return contractList;
    }
}
