package com.mobile.absoluke.travellie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Yul Lucia on 11/09/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentAbout fragmentAbout = new FragmentAbout();

                return fragmentAbout;

            case 1:
                FragmentLocation fragmentLocation = new FragmentLocation();

                return fragmentLocation;

            case 2:
                FragmentPhotos fragmentPhotos = new FragmentPhotos();

                return fragmentPhotos;

            case 3:
                FragmentPosts fragmentPosts = new FragmentPosts();

                return fragmentPosts;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position) {
//        String title = "";
//        switch (position) {
//            case 0:
//                title = "About";
//                break;
//            case 1:
//                title = "Location";
//                break;
//            case 2:
//                title = "Photos";
//                break;
//            case 3:
//                title = "Posts";
//                break;
//        }
//        return title;
        return null;
    }
}
