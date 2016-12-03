package exmaple.easyshop.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import exmaple.easyshop.R;
import exmaple.easyshop.commons.ActivityUtils;

public class WellcomeActivity extends AppCompatActivity {

    @BindView(R.id.wellcome_img)
    ImageView wellcome_img;

    private AlphaAnimation alpha;
    private ActivityUtils activityUtils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        ButterKnife.bind(this);
        activityUtils = new ActivityUtils(this);
        initView();
    }

    private void initView() {
        alpha = new AlphaAnimation(0.1f,1.0f);
        alpha.setDuration(3000);
        wellcome_img.startAnimation(alpha);
        alpha.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                activityUtils.startActivity(MainActivity.class);
                finish();
            }
            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
