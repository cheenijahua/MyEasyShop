package exmaple.easyshop.myAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import exmaple.easyshop.main.UnLoginFragment;
import exmaple.easyshop.main.me.MeFragment;
import exmaple.easyshop.main.shop.ShopFragment;

/**
 * Created by Administrator on 2016/11/17.
 */

public class unLoginAdapter extends FragmentStatePagerAdapter {

    public unLoginAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ShopFragment();
            case 1:
                return new UnLoginFragment();
            case 2:
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
