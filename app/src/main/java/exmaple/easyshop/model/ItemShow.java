package exmaple.easyshop.model;

/**
 * Created by Administrator on 2016/11/25.
 */

public class ItemShow {
    //
    private String item_title;

    private String item_content;

    public ItemShow(String item_title,String item_content) {
        this.item_content = item_content;
        this.item_title = item_title;
    }

    public String getItem_content() {
        return item_content;
    }

    public String getItem_title() {
        return item_title;
    }
}
