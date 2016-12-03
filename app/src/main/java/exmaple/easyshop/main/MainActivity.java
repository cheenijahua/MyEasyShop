package exmaple.easyshop.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import exmaple.easyshop.EasyShopApplication;
import exmaple.easyshop.R;
import exmaple.easyshop.model.CachePreferences;
import exmaple.easyshop.myAdapter.loginAdapter;
import exmaple.easyshop.myAdapter.unLoginAdapter;

public class MainActivity extends AppCompatActivity {

    private Unbinder unbinder;

    @BindViews({R.id.tv_shop,R.id.tv_msg,R.id.tv_mail_list,R.id.tv_me})
    TextView[] textViews;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.main_title)
    TextView tv_title;
    @BindView(R.id.viewpager)
    ViewPager pager;
    EasyShopApplication app;
    boolean flag = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        unbinder = ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");



        init();
    }

    private void init() {
        //设置默认的fragment为市场
        textViews[0].setSelected(true);
        //判断用户是否登录，从而选择不当的适配器

        FragmentManager manager= getSupportFragmentManager();
        if(CachePreferences.getUser().getName() == null){
            //加载未登录适配器adapter
            pager.setAdapter(new unLoginAdapter(manager));
            pager.setCurrentItem(0);
        }else {
            //加载已经登录adapter
            pager.setAdapter(new loginAdapter(manager));
            pager.setCurrentItem(0);
        }



        //viewpager添加滑动事件
        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                //textview选择处理
                for(TextView textView : textViews){
                    textView.setSelected(false);
                }
                //pager滑动时，设置toolbar的title，
                tv_title.setText(textViews[position].getText());
                textViews[position].setSelected(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @OnClick({R.id.tv_shop,R.id.tv_msg,R.id.tv_mail_list,R.id.tv_me})
    public void onClick(TextView textView){
        for (int i = 0;i<textViews.length;i++){
            textViews[0].setSelected(false);
            textViews[i].setTag(i);
        }
        textView.setSelected(true);
        pager.setCurrentItem((Integer) textView.getTag(),false);
        //设置toolbar的titile
        tv_title.setText(textViews[(int) textView.getTag()].getText());
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (!flag){
            flag = true;
            Toast.makeText(this, "再恩一次退出", Toast.LENGTH_SHORT).show();
            pager.postDelayed(new Runnable() {
                @Override
                public void run() {
                    flag = false;
                }
            },2000);
        }
        else {
            finish();

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }
}
