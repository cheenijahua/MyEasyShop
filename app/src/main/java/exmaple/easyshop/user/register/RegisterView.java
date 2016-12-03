package exmaple.easyshop.user.register;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/11/23.
 */

public interface RegisterView extends MvpView{

    void showPrb();

    void hidePrb();
    //注册成功
    void registerSuccess();
    //注册失败
    void registerFailed();
    //显示内容
    void showMsg(String msg);
    //显示错误信息
    void showUserPasswordError(String msg);
}
