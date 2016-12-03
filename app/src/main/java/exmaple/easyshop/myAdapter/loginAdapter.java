package exmaple.easyshop.myAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import exmaple.easyshop.main.UnLoginFragment;
import exmaple.easyshop.main.me.MeFragment;
import exmaple.easyshop.main.shop.ShopFragment;

/**
 * Created by Administrator on 2016/11/25.
 */

public class loginAdapter extends FragmentStatePagerAdapter {
    public loginAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ShopFragment();
            case 1:
                // TODO: 2016/11/25 跳转环信消息fragment
                return new UnLoginFragment();
            case 2:
                // TODO: 2016/11/25 跳转环信fragment通讯录
                return new UnLoginFragment();
            case 3:
                return new MeFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
