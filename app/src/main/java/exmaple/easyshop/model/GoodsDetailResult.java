package exmaple.easyshop.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Administrator on 2016/12/3.
 */
@SuppressWarnings("unused")
public class GoodsDetailResult {
    private int code;
    @SerializedName("msg")
    private String message;
    private GoodsDetail datas;
    /*发布者的信息*/
    private User user;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public GoodsDetail getDatas() {
        return datas;
    }

    public User getUser() {
        return user;
    }

}



