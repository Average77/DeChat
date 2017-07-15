package com.isabella.dechat.bean;

import java.util.List;

/**
 * description
 * Created by 张芸艳 on 2017/6/23.
 */

public class FriendListBean {

    /**
     * result_message : success
     * data : [{"area":"江苏省 常州市 天宁区","picWidth":720,"createtime":1500027295921,"picHeight":795,"gender":"男","lng":116.297487,"introduce":"世界一番強いです","imagePath":"http://qhb.2dyt.com/MyInterface/images/e6262791-c526-4a8c-b6cd-161641d9ca05.jpg","userId":3,"yxpassword":"NI79OMMU","relation":0,"password":"698d51a19d8a121ce581499d7b701668","lasttime":1500030645394,"phone":"18739282125","nickname":"ナルー","age":"20","lat":40.039738},{"area":"北京市 北京市 崇文区","picWidth":720,"createtime":1500030226875,"picHeight":1369,"gender":"男","lng":116.390998333333,"introduce":"赛若天仙","imagePath":"http://qhb.2dyt.com/MyInterface/images/a3557334-b94c-441a-943e-558bbb2286e8.jpg","userId":13,"yxpassword":"JaQ7Blw6","relation":0,"password":"e10adc3949ba59abbe56e057f20f883e","lasttime":1500030694519,"phone":"18863894005","nickname":"美的不忍直视","age":"20","lat":39.9054983333333},{"area":"北京市 北京市 海淀区","picWidth":720,"createtime":1500030372677,"picHeight":720,"gender":"女","lng":116.293607,"introduce":"超级超级超级大帅哥","imagePath":"http://qhb.2dyt.com/MyInterface/images/eada132e-8c2f-48f7-924b-f3b97edc4dd0.jpg","userId":14,"yxpassword":"c08IS75Q","relation":0,"password":"e10adc3949ba59abbe56e057f20f883e","lasttime":1500030691579,"phone":"13111111112","nickname":"帅的不可思议","age":"19","lat":40.039176}]
     * result_code : 200
     */

    private String result_message;
    private int result_code;
    private List<FriendListDataBean> data;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }

    public List<FriendListDataBean> getData() {
        return data;
    }

    public void setData(List<FriendListDataBean> data) {
        this.data = data;
    }


}
