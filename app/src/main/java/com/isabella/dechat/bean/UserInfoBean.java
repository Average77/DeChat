package com.isabella.dechat.bean;



public class UserInfoBean {

    /**
     * result_message : success
     * data : {"area":"河南省 焦作市 孟州市","lasttime":1499851060564,"createtime":1499830426052,"gender":"男","introduce":"死肥宅就是我。","imagePath":"http://qhb.2dyt.com/MyInterface/images/8618fc75-4efa-4f17-a777-aad915fe249b.jpg","nickname":"Reinhardt","userId":21,"photolist":[{"picWidth":720,"timer":1499842558230,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/e2335022-7880-4a24-a987-4bba7abea51f.jpg","albumId":5,"userId":21},{"picWidth":720,"timer":1499842685715,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/533919ea-541b-45fb-971e-9e8af5c87c42.jpg","albumId":6,"userId":21},{"picWidth":720,"timer":1499842719930,"picHeight":639,"imagePath":"http://qhb.2dyt.com/MyInterface/images/9d0f042d-267e-4850-b7d4-aee8a4ec65c5.jpg","albumId":7,"userId":21},{"picWidth":720,"timer":1499842733598,"picHeight":604,"imagePath":"http://qhb.2dyt.com/MyInterface/images/b94d09a0-00b9-4ae3-bf82-6efc26a3e8b5.jpg","albumId":8,"userId":21},{"picWidth":720,"timer":1499842742076,"picHeight":720,"imagePath":"http://qhb.2dyt.com/MyInterface/images/582dcdc1-3721-4b74-9204-eb171cade580.jpg","albumId":9,"userId":21}]}
     * result_code : 200
     */

    private String result_message;
    private UserInfoDataBean data;
    private int result_code;

    public String getResult_message() {
        return result_message;
    }

    public void setResult_message(String result_message) {
        this.result_message = result_message;
    }

    public UserInfoDataBean getData() {
        return data;
    }

    public void setData(UserInfoDataBean data) {
        this.data = data;
    }

    public int getResult_code() {
        return result_code;
    }

    public void setResult_code(int result_code) {
        this.result_code = result_code;
    }


}
