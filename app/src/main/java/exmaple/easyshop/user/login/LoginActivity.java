package exmaple.easyshop.user.login;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import exmaple.easyshop.main.MainActivity;
import exmaple.easyshop.R;
import exmaple.easyshop.commons.ActivityUtils;
import exmaple.easyshop.components.ProgressDialogFragment;
import exmaple.easyshop.user.register.RegisterActivity;

public class LoginActivity extends MvpActivity<LoginView,LoginPresenter> implements LoginView {

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.btn_login)
    Button btn_login;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    private ActivityUtils activityUtils;
    private String username;
    private String password;
    private ProgressDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        init();
    }

    @NonNull
    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenter();
    }

    private void init() {
        et_username.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private TextWatcher textWatcher = new TextWatcher() {
        /**
         *
         * @param charSequence 表示改变之前的内容
         * @param start 通常start和count组合，可以charSequence在中读取本次改变字段中被改变的内容
         * @param count
         * @param after 而after表示改变后新的内容的数量
         */
        @Override
        public void beforeTextChanged(CharSequence charSequence, int start, int count, int after) {

        }

        /**
         *
         * @param charSequence 表示改变之后的内容
         * @param start  通常start和count组合，可以在s中读取本次改变字段中新的内容
         * @param before  而before表示被改变的内容的数量
         * @param count
         */

        @Override
        public void onTextChanged(CharSequence charSequence, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            username = et_username.getText().toString();
            password = et_pwd.getText().toString();
            //判断用户名和密码是否为空

            boolean canLogin = !(TextUtils.isEmpty(username)||TextUtils.isEmpty(password));
            btn_login.setEnabled(canLogin);
        }
    };

    @OnClick({R.id.btn_login,R.id.tv_register})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.btn_login:
                // TODO: 2016/11/17 0017 执行登录的网络请求
               getPresenter().login(username,password);
                break;
            case R.id.tv_register:
                activityUtils.startActivity(RegisterActivity.class);
                break;
        }
    }

    @Override
    public void showPrb() {
        activityUtils.hideSoftKeyboard();
        if (dialogFragment == null)  dialogFragment = new ProgressDialogFragment();
        if (dialogFragment.isVisible()) return;
        dialogFragment.show(getSupportFragmentManager(),"progress_dialog_fragment");
    }

    @Override
    public void hidePrb() {
        dialogFragment.dismiss();
    }

    @Override
    public void loginSuccess() {
        activityUtils.startActivity(MainActivity.class);
        this.finish();
    }

    @Override
    public void loginFailed() {
        et_username.setText("");
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }
}
