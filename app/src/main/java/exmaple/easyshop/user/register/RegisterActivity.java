package exmaple.easyshop.user.register;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.hannesdorfmann.mosby.mvp.MvpActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import exmaple.easyshop.main.MainActivity;
import exmaple.easyshop.R;
import exmaple.easyshop.commons.ActivityUtils;
import exmaple.easyshop.commons.RegexUtils;
import exmaple.easyshop.components.AlertDialogFragment;
import exmaple.easyshop.components.ProgressDialogFragment;

public class RegisterActivity extends MvpActivity<RegisterView,RegisterPresenter> implements RegisterView{

    @BindView(R.id.reg_toolbar)
    Toolbar toolbar;
    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_pwdAgain)
    EditText et_pwdAgain;
    @BindView(R.id.btn_register)
    Button btn_register;

    private String username,password,pwd_again;
    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        initView();
    }

    @NonNull
    @Override
    public RegisterPresenter createPresenter() {
        return new RegisterPresenter();
    }

    private void initView() {
        et_username.addTextChangedListener(textWatcher);
        et_pwd.addTextChangedListener(textWatcher);
        et_pwdAgain.addTextChangedListener(textWatcher);
        setSupportActionBar(toolbar);

        //添加返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }

    private final TextWatcher textWatcher = new TextWatcher() {
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

        /**
         * 最终内容
         * @param editable
         */
        @Override
        public void afterTextChanged(Editable editable) {
            username = et_username.getText().toString();
            password = et_pwd.getText().toString();
            pwd_again = et_pwdAgain.getText().toString();
            //判断用户名密码是否为空
            boolean canLogin =! (TextUtils.isEmpty(username) || TextUtils.isEmpty(password));
            btn_register.setEnabled(canLogin);
        }
    };

    @OnClick(R.id.btn_register)
    public void onClick(){
        if (RegexUtils.verifyUsername(username) != RegexUtils.VERIFY_SUCCESS){
            String msg = getString(R.string.username_rules);
            showUserPasswordError(msg);
            return;
        }
        if (RegexUtils.verifyPassword(password) != RegexUtils.VERIFY_SUCCESS){
            String msg = getString(R.string.password_rules);
            showUserPasswordError(msg);
            return;
        }
        if (!TextUtils.equals(password,pwd_again)) {
            String msg = getString(R.string.username_equal_pwd);
            showUserPasswordError(msg);
            return;
        }
        presenter.register(username,password);
    }

    @Override
    public void showPrb() {
        //隐藏软键盘
        activityUtils.hideSoftKeyboard();
        if(dialogFragment ==null){
            dialogFragment = new ProgressDialogFragment();
        }
        if(dialogFragment.isVisible()) return;
        dialogFragment.show(getSupportFragmentManager(),"progress_dialog_fragment");
    }

    @Override
    public void hidePrb() {
        dialogFragment.dismiss();
    }

    @Override
    public void registerSuccess() {
        //成功跳转
        activityUtils.startActivity(MainActivity.class);
        finish();
    }

    @Override
    public void registerFailed() {
        et_username.setText("");
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void showUserPasswordError(String msg) {
        AlertDialogFragment fragment = AlertDialogFragment.newInstance(msg);
        fragment.show(getSupportFragmentManager(),getString(R.string.username_pwd_rule));
    }
}
