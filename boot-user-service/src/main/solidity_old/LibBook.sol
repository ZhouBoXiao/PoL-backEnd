pragma solidity ^ 0.4 .2;


import "./SystemContracts/utillib/LibInt.sol";
import "./SystemContracts/utillib/LibString.sol";
import "./SystemContracts/utillib/LibStack.sol";
import "./SystemContracts/utillib/LibJson.sol";


library LibBook {

    using LibInt
    for * ;
    using LibString
    for * ;
    using LibJson
    for * ;
    using LibBook
    for * ;


    struct Book {
        string number;
        string name;
        string price;
        string author;
        string remark;
    }

    function fromJson(Book storage _self, string _json) internal returns(bool succ) {
        _self.reset();

        if (!_json.isJson())
            return false;

        _self.number = _json.jsonRead("number");
        _self.name = _json.jsonRead("name");
        _self.price = _json.jsonRead("price");
        _self.author = _json.jsonRead("author");
        _self.remark = _json.jsonRead("remark");

        return true;
    }


    function toJson(Book storage _self) internal constant returns(string _json) {
        LibStack.push("{");
        LibStack.appendKeyValue("number", _self.number);
        LibStack.appendKeyValue("name", _self.name);
        LibStack.appendKeyValue("price", _self.price);
        LibStack.appendKeyValue("author", _self.author);
        LibStack.appendKeyValue("remark", _self.remark);
        LibStack.append("}");
        _json = LibStack.pop();
    }

    function fromJsonArray(Book[] storage _self, string _json) internal returns(bool succ) {
        _self.length = 0;

        if (!_json.isJson())
            return false;

        while (true) {
            string memory key = "[".concat(_self.length.toString(), "]");
            if (!_json.jsonKeyExists(key))
                break;

            _self.length++;
            _self[_self.length - 1].fromJson(_json.jsonRead(key));
        }

        return true;
    }

    function toJsonArray(Book[] storage _self) internal constant returns(string _json) {
        _json = _json.concat("[");
        for (uint i = 0; i < _self.length; ++i) {
            if (i == 0)
                _json = _json.concat(_self[i].toJson());
            else
                _json = _json.concat(",", _self[i].toJson());
        }
        _json = _json.concat("]");
    }

    function update(Book storage _self, string _json) internal returns(bool succ) {
        if (!_json.isJson())
            return false;

        if (_json.jsonKeyExists("number"))
            _self.number = _json.jsonRead("number");
        if (_json.jsonKeyExists("name"))
            _self.name = _json.jsonRead("name");
        if (_json.jsonKeyExists("price"))
            _self.price = _json.jsonRead("price");
        if (_json.jsonKeyExists("author"))
            _self.author = _json.jsonRead("author");
        if (_json.jsonKeyExists("remark"))
            _self.remark = _json.jsonRead("remark");

        return true;
    }

    function reset(Book storage _self) internal {
        delete _self.number;
        delete _self.name;
        delete _self.price;
        delete _self.author;
        delete _self.remark;
    }


}