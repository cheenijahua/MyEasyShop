package exmaple.easyshop.user.login;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.IOException;

import exmaple.easyshop.model.CachePreferences;
import exmaple.easyshop.model.User;
import exmaple.easyshop.model.UserResult;
import exmaple.easyshop.network.EasyShopClient;
import exmaple.easyshop.network.UICallback;
import okhttp3.Call;

/**
 * Created by Administrator on 2016/11/25.
 */

public class LoginPresenter extends MvpNullObjectBasePresenter<LoginView>{

    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call != null){
            call.cancel();
        }
    }

    public void login(String username, String password){
        //显示progressbar
        getView().showPrb();

        call = EasyShopClient.getInstance().login(username,password);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                //隐藏progressbar
                getView().hidePrb();
                //显示异常信息
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                //根据返回码处理不同的结果
                if(userResult.getCode()==1){
                    //成功提示
                    getView().showMsg("登录成功");
                    //拿到用户实体类
                    User user = userResult.getData();
                    //将用户信息保存到本地
                    CachePreferences.setUser(user);
                    //调用注册成功的方法
                    getView().loginSuccess();
                    // TODO: 2016/11/24 环信未实现
                }else if (userResult.getCode()==2){
                    //隐藏进度条
                    getView().hidePrb();
                    //提示错误信息
                    getView().showMsg(userResult.getMassage());
                    //调用注册失败的方法
                    getView().loginFailed();
                }else {
                    getView().showMsg("未知错误！");
                }
            }
        });
    }
}
