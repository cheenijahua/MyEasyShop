package exmaple.easyshop.user.login;

import com.hannesdorfmann.mosby.mvp.MvpView;

/**
 * Created by Administrator on 2016/11/25.
 */

public interface LoginView extends MvpView {
    //显示进度调
    void showPrb();
    //隐藏进度条
    void hidePrb();
    //注册成功
    void loginSuccess();
    //注册失败
    void loginFailed();
    //显示内容
    void showMsg(String msg);
}
