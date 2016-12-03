package exmaple.easyshop.main.me.personInfo;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.hannesdorfmann.mosby.mvp.MvpActivity;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.pkmmte.view.CircularImageView;

import org.hybridsquad.android.library.CropHandler;
import org.hybridsquad.android.library.CropHelper;
import org.hybridsquad.android.library.CropParams;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import exmaple.easyshop.R;
import exmaple.easyshop.commons.ActivityUtils;
import exmaple.easyshop.components.AvatarLoadOptions;
import exmaple.easyshop.components.PicWindow;
import exmaple.easyshop.components.ProgressDialogFragment;
import exmaple.easyshop.main.MainActivity;
import exmaple.easyshop.model.CachePreferences;
import exmaple.easyshop.model.ItemShow;
import exmaple.easyshop.model.User;
import exmaple.easyshop.myAdapter.PersonAdapter;
import exmaple.easyshop.network.EasyshopAPI;

public class PersonActivity extends MvpActivity<PersonView, PersonPresenter> implements PersonView {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.iv_user_head)
    CircularImageView ivUserHead;
    @BindView(R.id.listView)
    ListView listView;


    private ActivityUtils activityUtils;
    private ProgressDialogFragment dialogFragment;
    private List<ItemShow> list = new ArrayList<>();
    private PersonAdapter adapter;
    private PicWindow picWindow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        adapter = new PersonAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(onItemClickListener);


        // TODO: 2016/11/25  获取用户头像
        updataAvatar(CachePreferences.getUser().getHead_img());

    }

    //方便修改改为昵称，回来更改数据
    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        init();
        adapter.notifyDataSetChanged();
    }

    private void init() {
        User user = CachePreferences.getUser();
        list.add(new ItemShow(getResources().getString(R.string.username),user.getName()));
        list.add(new ItemShow(getResources().getString(R.string.nickname),user.getNick_name()));
        list.add(new ItemShow(getResources().getString(R.string.hx_id),user.getHx_Id()));

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
    @NonNull
    @Override
    public PersonPresenter createPresenter() {
        return new PersonPresenter();
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            switch (i){
                case 0:
                    activityUtils.showToast(getResources().getString(R.string.username_update));
                    break;
                case 1:
                    activityUtils.startActivity(NickNameActivity.class);
                    break;
                case 2:
                    activityUtils.showToast(getResources().getString(R.string.id_update));
                    break;
            }
        }
    };


    @Override
    public void showPrb() {
        if(dialogFragment == null) dialogFragment = new ProgressDialogFragment();
        if (dialogFragment.isVisible()) return;
        dialogFragment.show(getSupportFragmentManager(),"progress_dialog_fragment");
    }


    @Override
    public void hidePrb() {
        dialogFragment.dismiss();
    }

    @Override
    public void showMsg(String msg) {
        activityUtils.showToast(msg);
    }

    @Override
    public void updataAvatar(String url) {
        //设置头像
        ImageLoader.getInstance()
                .displayImage(EasyshopAPI.IMAGE_URL+url,ivUserHead, AvatarLoadOptions.build());
    }

    @OnClick({R.id.iv_user_head, R.id.btn_login_out})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_user_head:
                //    图片上传待实现
                if (picWindow == null){
                    picWindow = new PicWindow(this,listener);
                }
                if (picWindow.isShowing()){
                    picWindow.dismiss();
                    return;
                }
                picWindow.show();
                break;
            case R.id.btn_login_out:
                // TODO: 2016/11/25 环信的退出登录
                CachePreferences.clearAllData();
                Intent intent = new Intent(this, MainActivity.class);
                //清除所有旧的activity
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                break;
        }
    }

    //图片选择窗的自定义监听
    private PicWindow.Listener listener = new PicWindow.Listener() {
        /**
         * 从相册中选择
         * 清空剪裁的缓存
         */
        @Override
        public void toGallery() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCropFromGalleryIntent(cropHandler.getCropParams());
            startActivityForResult(intent,CropHelper.REQUEST_CROP);
        }

        /**
         * 从相机中选择
         */

        @Override
        public void toCamera() {
            CropHelper.clearCachedCropFile(cropHandler.getCropParams().uri);
            Intent intent = CropHelper.buildCaptureIntent(cropHandler.getCropParams().uri);
            startActivityForResult(intent,CropHelper.REQUEST_CAMERA);

        }
    };

    private CropHandler cropHandler = new CropHandler() {
        @Override
        public void onPhotoCropped(Uri uri) {
            //通过uri拿到图片文件
            File file = new File(uri.getPath());
            //物业类上传图像
            presenter.updataAvatar(file);
        }

        @Override
        public void onCropCancel() {
        }

        @Override
        public void onCropFailed(String message) {
        }

        @Override
        public CropParams getCropParams() {
            CropParams cropParams = new CropParams();
            cropParams.aspectX = 400;
            cropParams.aspectY = 400;
            return cropParams;
        }

        @Override
        public Activity getContext() {
            return PersonActivity.this;
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //帮助我们处理结果
        CropHelper.handleResult(cropHandler,requestCode, resultCode, data);
    }
}
