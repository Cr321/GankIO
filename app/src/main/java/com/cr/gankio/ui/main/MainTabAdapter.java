package com.cr.gankio.ui.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cr.gankio.Constants;
import com.cr.gankio.ui.fragments.GankNewsFragment;

import java.util.Arrays;
import java.util.List;

/**
 * @author RUI CAI
 * @date 2018/1/22
 */

public class MainTabAdapter extends FragmentPagerAdapter {

    private final List<String> mFragmentTitleList = Arrays.asList(
            Constants.TYPE_ANDROID,
            Constants.TYPE_IOS,
            Constants.TYPE_VIDEO,
            Constants.TYPE_WELFARE,
            Constants.TYPE_EXPAND,
            Constants.TYPE_FRONT_END,
            Constants.TYPE_XIA,
            Constants.TYPE_APP);

    public MainTabAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return GankNewsFragment.newInstance(mFragmentTitleList.get(position));
    }

    @Override
    public int getCount() {
        return mFragmentTitleList.size();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}