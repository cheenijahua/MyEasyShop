package exmaple.easyshop.main.me.personInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.EditText;

import com.google.gson.Gson;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import exmaple.easyshop.R;
import exmaple.easyshop.commons.ActivityUtils;
import exmaple.easyshop.commons.RegexUtils;
import exmaple.easyshop.model.CachePreferences;
import exmaple.easyshop.model.User;
import exmaple.easyshop.model.UserResult;
import exmaple.easyshop.network.EasyShopClient;
import exmaple.easyshop.network.UICallback;
import okhttp3.Call;

public class NickNameActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_nickname)
    EditText etNickname;

    private ActivityUtils activityUtils;
    String str_nickname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nick_name);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_save)
    public void onClick() {
        str_nickname = etNickname.getText().toString();
        if (RegexUtils.verifyNickname(str_nickname) != RegexUtils.VERIFY_SUCCESS){
            String msg = getString(R.string.nickname_rules);
            activityUtils.showToast(msg );
            return;
        }
        init();
    }

    private void init() {
        //从本地拿到用户类
        User user = CachePreferences.getUser();
        //要修改的昵称
        user.setNick_name(str_nickname);
        Call  call = EasyShopClient.getInstance().updatedUser(user);
        call.enqueue(new UICallback() {
            @Override
            public void onFailureUI(Call call, IOException e) {
                activityUtils.showToast(e.getMessage());
            }

            @Override
            public void onResponseUI(Call call, String body) {
                UserResult userResult = new Gson().fromJson(body,UserResult.class);
                if (userResult.getCode()!= 1){
                    activityUtils.showToast(userResult.getMassage());
                    return;
                }
                CachePreferences.setUser(userResult.getData());
                activityUtils.showToast("修改成功");
                //返回键的返回方法
                onBackPressed();
            }
        });
    }
}
