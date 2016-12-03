package exmaple.easyshop.main.me;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import exmaple.easyshop.R;
import exmaple.easyshop.commons.ActivityUtils;
import exmaple.easyshop.components.AvatarLoadOptions;
import exmaple.easyshop.main.me.personInfo.PersonActivity;
import exmaple.easyshop.model.CachePreferences;
import exmaple.easyshop.network.EasyshopAPI;
import exmaple.easyshop.user.login.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends Fragment {
    @BindView(R.id.iv_userhead)
    ImageView iv_userhead;
    @BindView(R.id.tv_login)
    TextView tv_login;

    private View view;
    private ActivityUtils activityUtils;

    public MeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityUtils = new ActivityUtils(this);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null){
            view = inflater.inflate(R.layout.fragment_me,container,false);
            ButterKnife.bind(this,view);
        }
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        // TODO: 2016/11/17 判断用户是否登录
        if (CachePreferences.getUser().getName()==null) return;
        if (CachePreferences.getUser().getNick_name() == null){
            tv_login.setText("请输入昵称");
        }else {
            tv_login.setText(CachePreferences.getUser().getNick_name());
        }
        ImageLoader.getInstance()
                .displayImage(EasyshopAPI.IMAGE_URL+CachePreferences.getUser().getHead_img(),iv_userhead, AvatarLoadOptions.build());

    }
    @OnClick({R.id.iv_userhead,R.id.tv_person_info,R.id.tv_login
            , R.id.tv_person_goods, R.id.tv_goods_upload})
    public void onClick(View v){
        // TODO: 2016/11/25 判断用户是否登录，确定跳转的位置
        if (CachePreferences.getUser().getName()==null){
            activityUtils.startActivity(LoginActivity.class);
//            getActivity().finish();
            return;
        }
        switch (v.getId()){
            case R.id.iv_userhead:
            case R.id.tv_login:
            case R.id.tv_person_info:
                activityUtils.startActivity(PersonActivity.class);
                break;
            case R.id.tv_person_goods:
                activityUtils.showToast("我的商品待实现");
                break;
            case R.id.tv_goods_upload:
                activityUtils.showToast("上传商品待实现");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
