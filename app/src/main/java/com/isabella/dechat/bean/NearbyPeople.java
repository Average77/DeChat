package com.isabella.dechat.bean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/6/22.
 */

public class NearbyPeople {

    /**
     * result_code : 200
     * pageCount : 共10条记录
     * data : [{"createtime":"Jun 22, 2017 10:12:33 AM","phone":"123","area":"1212","nickname":"12121231","userId":3,"introduce":"1212","gender":"a","password":"456"},{"createtime":"Jun 22, 2017 5:33:07 PM","phone":"15313095207","area":"å\u008c\u0097äº¬","nickname":"LZM","userId":4,"introduce":"æ\u0088\u0091å°±æ\u0098¯æ\u0088\u0091ä¸\u008dä¸\u0080æ ·ç\u009a\u0084ç\u0083\u009fç\u0081«","password":"123"},{"createtime":"Jun 22, 2017 6:45:41 PM","phone":"1212","area":"å\u008c\u0097äº¬","nickname":"Average","userId":5,"introduce":"æ\u0088\u0091å°±æ\u0098¯æ\u0088\u0091ä¸\u008dä¸\u0080æ ·ç\u009a\u0084ç\u0083\u009fç\u0081«","password":"123"},{"createtime":"Jun 22, 2017 6:58:43 PM","phone":"1313","area":"å\u008c\u0097äº¬","nickname":"Average","userId":6,"introduce":"æ\u0088\u0091å°±æ\u0098¯æ\u0088\u0091ä¸\u008dä¸\u0080æ ·ç\u009a\u0084ç\u0083\u009fç\u0081«","password":"123"},{"createtime":"Jun 22, 2017 7:19:13 PM","phone":"1414","area":"beijing","nickname":"Average","userId":7,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 7:23:06 PM","phone":"1515","area":"beijing","nickname":"Average","userId":8,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 7:33:28 PM","phone":"1616","area":"beijing","nickname":"Average","userId":9,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 7:42:45 PM","phone":"1717","area":"145","nickname":"545","userId":10,"introduce":"121","password":"666"},{"createtime":"Jun 22, 2017 9:27:11 PM","phone":"1818","area":"beijing","nickname":"Average","userId":11,"introduce":"aaa","password":"123"},{"createtime":"Jun 22, 2017 9:39:28 PM","phone":"1919","area":"beijing","nickname":"Average","userId":12,"introduce":"aaa","password":"123"}]
     */

    private int result_code;
    private String pageCount;
    private List<NearbyDataBean> data;
    private String result_message;

    public int getResult_code() {
        return result_code;
    }

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public String getPageCount() {
        return pageCount;
    }

    public void setPageCount(String pageCount) {
        this.pageCount = pageCount;
    }

    public List<NearbyDataBean> getData() {
        return data;
    }

    public void setData(List<NearbyDataBean> data) {
        this.data = data;
    }



    @Override
    public String toString() {
        return "NearbyPeople{" +
                "result_code=" + result_code +
                ", pageCount='" + pageCount + '\'' +
                ", data=" + data +
                ", result_message='" + result_message + '\'' +
                '}';
    }
}
