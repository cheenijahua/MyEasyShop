package exmaple.easyshop.model;

/**
 * Created by Administrator on 2016/11/24.
 */

//成功

import com.google.gson.annotations.SerializedName;

/**
 * {
        "code": 1, //结果码
        "msg": "succeed",
        "data": {
        "name": "ytd70aa402693e4333a6318933226d0276", //环信ID
        "uuid": "939913BF5BEB46A29047BE66399BC1A0",   //用户表中主键
        "username": "android"  //用户名
        }
   }
   */
public class UserResult {

    private int code;
    @SerializedName("msg")
    private String massage;

    private User data;

    public int getCode() {
        return code;
    }

    public User getData() {
        return data;
    }

    public String getMassage() {
        return massage;
    }
}
