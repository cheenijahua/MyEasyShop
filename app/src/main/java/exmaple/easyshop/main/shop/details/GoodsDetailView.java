package exmaple.easyshop.main.shop.details;

import com.hannesdorfmann.mosby.mvp.MvpView;

import java.util.ArrayList;

import exmaple.easyshop.model.GoodsDetail;
import exmaple.easyshop.model.User;

/**
 * Created by Administrator on 2016/12/3.
 */

public interface GoodsDetailView extends MvpView {

    void showProgress();

    void hideProgress();

    /*设置图片路径*/
    void setImageData(ArrayList<String> viewList);

    /*设置商品信息*/
    void setData(GoodsDetail data, User goods_user);

    /*商品不存在了*/
    void showError();

    void showMessage(String msg);

    /*删除商品*/
    void deleteEnd();

}
