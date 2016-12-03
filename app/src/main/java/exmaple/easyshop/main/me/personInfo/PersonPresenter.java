package exmaple.easyshop.main.me.personInfo;

import com.google.gson.Gson;
import com.hannesdorfmann.mosby.mvp.MvpNullObjectBasePresenter;

import java.io.File;
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

public class PersonPresenter extends MvpNullObjectBasePresenter<PersonView>{
    private Call call;

    @Override
    public void detachView(boolean retainInstance) {
        super.detachView(retainInstance);
        if (call !=null) call.cancel();
    }
    //上传图像
    public void updataAvatar(File file){
        getView().showPrb();
        call = EasyShopClient.getInstance().uploadAvatar(file);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                getView().hidePrb();
                getView().showMsg(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                getView().hidePrb();
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                if (userResult.getCode() !=1){
                    getView().showMsg(userResult.getMassage());
                    return;
                }else {
                    getView().showMsg("未知错误");
                }
                User user = userResult.getData();
                CachePreferences.setUser(user);
                //调用更新图像的方法
                getView().updataAvatar(userResult.getData().getHead_img());
                // TODO: 2016/11/26 环信登录更新图像
            }
        });
    }
}
