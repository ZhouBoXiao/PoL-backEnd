pragma solidity ^0.4.2;

library IterableMapping {

    struct itmap {
        uint size;
        mapping(address => IndexValue) data;
        KeyFlag []keys;
    }

    // key值的列表
    struct KeyFlag {
        address key;
        bool deleted;
    }

    // value
    struct IndexValue {
        uint KeyIndex;
        address value;
    }


    // 插入数据
    function insert(itmap storage self, address key, address value) returns(bool replaced) {
        uint keyIdx = self.data[key].KeyIndex;
        self.data[key].value = value;
        if (keyIdx > 0) {
            return true;
        }else {
            keyIdx = self.keys.length++;
            self.data[key].KeyIndex = keyIdx + 1;
            self.keys[keyIdx].key = key;
            self.size++;
            return false;
        }
    }

    // 删除数据(逻辑删除)
    function remove(itmap storage self, address key) returns(bool) {
        uint keyIdx = self.data[key].KeyIndex;
        if (keyIdx == 0) {
            return false;
        } else {
            delete self.data[key]; //逻辑删除
            self.keys[keyIdx - 1].deleted = true;
            self.size --;
            return true;
        }
    }

    function getByKey(itmap storage self, address key) returns(address) {
        address value = 0x0;
        if (self.data[key].KeyIndex > 0) {
            value = self.data[key].value;
        }
        return value;
    }

    // 获取数据
    function iterate_get(itmap storage self, uint KeyIdx) returns(address key, address value) {
        key = self.keys[KeyIdx].key;
        value = self.data[key].value;
    }

    // 包含
    function iterate_contains(itmap storage self, address key) returns(bool) {
        return self.data[key].KeyIndex > 0;
    }

    // 下一个索引
    function iterate_next(itmap storage self, uint _keyIndex) returns(uint r_keyIndex) {

        _keyIndex++;
        while(_keyIndex < self.keys.length && self.keys[_keyIndex].deleted) {
            _keyIndex++;
        }
        return _keyIndex;
    }

    // 开始
    function iterate_start(itmap storage self) returns(uint keyIndex) {
        return iterate_next(self, uint(-1));
    }

    // 是否有效
    function iterate_valid(itmap storage self, uint keyIndex) returns(bool) {
        return keyIndex < self.keys.length;
    }
}