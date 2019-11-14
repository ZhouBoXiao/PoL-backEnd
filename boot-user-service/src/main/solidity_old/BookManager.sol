pragma solidity ^ 0.4.2;


import "./LibBook.sol";
import "./SystemContracts/sysbase/OwnerNamed.sol";
import "./SystemContracts/utillib/LibLog.sol";

contract BookManager is OwnerNamed {

    using LibBook
    for * ;
    using LibString
    for * ;
    using LibInt
    for * ;
    using LibLog
    for * ;

    event Notify(uint _errno, string _info);

    LibBook.Book[] bookList;
    mapping(string => uint) keyMap;


    //定义错误信息
    enum ErrorNo {
        NO_ERROR,
        BAD_PARAMETER,
        NUMBER_EMPTY,
        BOOK_NOT_EXISTS,
        NUMBER_ALREADY_EXISTS,
        ACCOUNT_ALREDY_EXISTS,
        NO_PERMISSION
    }

    // 构造函数，在合约发布时会被触发调用
    function BookManager() {
        LibLog.log("deploy BookModule....");

        //把合约注册到JUICE链上, 参数必须和BookModule.sol中的保持一致
        register("BookModule", "0.0.1.0", "BookManager", "0.0.1.0");
    }


    function add(string _number, string _name, string _price, string _author, string _remark) public returns(uint) {
        LibLog.log("BookManager into add..");

        if (_number.equals("")) {
            LibLog.log("Invalid number.", "BookManager");
            errno = 15200 + uint(ErrorNo.NUMBER_EMPTY);
            Notify(errno, "图书编号为空，插入失败.");
            return errno;
        }

        if (keyMap[_number] == 0) {
            if (bookList.length > 0) {
                if (_number.equals(bookList[0].number)) {
                    LibLog.log("number aready exists", "BookManager");
                    errno = 15200 + uint(ErrorNo.NUMBER_ALREADY_EXISTS);
                    Notify(errno, "图书编号已存在，插入失败.");
                    return errno;
                }
            }
        } else {
            LibLog.log("number aready exists", "BookManager");
            errno = 15200 + uint(ErrorNo.NUMBER_ALREADY_EXISTS);
            Notify(errno, "图书编号已存在，插入失败.");
            return errno;
        }

        uint idx = bookList.length;
        bookList.push(LibBook.Book(_number, _name, _price, _author, _remark));

        keyMap[_number] = idx;

        errno = uint(ErrorNo.NO_ERROR);

        LibLog.log("add a book success", "BookManager");
        Notify(errno, "add a book success");
        return errno;
    }

    function deleteByNumber(string _number) public returns(uint) {
        LibLog.log("into delete..", "BookManager");

        //顾客列表不为空
        if (bookList.length > 0) {
            if (keyMap[_number] == 0) {
                //_number不存在，或者是数组第一个元素
                if (!_number.equals(bookList[0].number)) {
                    LibLog.log("book not exists: ", _number);
                    errno = 15200 + uint(ErrorNo.BOOK_NOT_EXISTS);
                    Notify(errno, "图书编号不存在，删除失败.");
                    return;
                }
            }
        } else {
            LibLog.log("book list is empty: ", _number);
            errno = 15200 + uint(ErrorNo.BOOK_NOT_EXISTS);
            Notify(errno, "图书列表为空，删除失败.");
            return;
        }

        //数组总长度
        uint len = bookList.length;

        //此用户在数组中的序号
        uint idx = keyMap[_number];

        if (idx >= len) return;
        for (uint i = idx; i < len - 1; i++) {
            //从待删除的数组element开始，把后一个element移动到前一个位置
            bookList[i] = bookList[i + 1];
            //同时修改keyMap中，对应key的在数组中的序号
            keyMap[bookList[i].number] = i;
        }
        //删除数组最后一个元素（和倒数第二个重复了）
        delete bookList[len - 1];
        //删除mapping中元素，实际上是设置value为0
        delete keyMap[_number];

        //数组总长度-1
        bookList.length--;


        LibLog.log("delete user success.", "BookManager");
        errno = uint(ErrorNo.NO_ERROR);

        Notify(errno, "删除图书成功.");
    }

    function listAll() constant public returns(string _json) {
        uint len = 0;
        uint counter = 0;
        len = LibStack.push("");
        for (uint i = 0; i < bookList.length; i++) {
            if (counter > 0) {
                len = LibStack.append(",");
            }
            len = LibStack.append(bookList[i].toJson());
            counter++;
        }
        len = itemsStackPush(LibStack.popex(len), counter);
        _json = LibStack.popex(len);
    }

    function itemsStackPush(string _items, uint _total) constant private returns(uint len) {
        len = 0;
        len = LibStack.push("{");
        len = LibStack.appendKeyValue("result", uint(0));
        len = LibStack.appendKeyValue("total", _total);
        len = LibStack.append(",\"data\":[");
        len = LibStack.append(_items);
        len = LibStack.append("]");
        len = LibStack.append("}");
        return len;
    }
}
