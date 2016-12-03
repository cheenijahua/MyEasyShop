package exmaple.easyshop.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class GoodsResult {
    private int code;
    @SerializedName("msg")
    private String message;
    private List<GoodsInfo> data;

    public int getCode() {
        return code;
    }

    public List<GoodsInfo> getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }
}
