package com.mobile.absoluke.travellie;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yul Lucia on 11/09/2017.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    String[] listFragment = {"FragmentLocation", "FragmentPosts", "FragmentPhotos", "FragmentAbout"};
    public PagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                FragmentLocation fragmentLocation = new FragmentLocation();

                return fragmentLocation;

            case 1:
                FragmentPosts fragmentPosts = new FragmentPosts();

                return fragmentPosts;

            case 2:
                FragmentPhotos fragmentPhotos = new FragmentPhotos();

                return fragmentPhotos;

            case 3:
                FragmentAbout fragmentAbout = new FragmentAbout();

                return fragmentAbout;
        }
        return null;
    }

    @Override
    public int getCount() {
        return listFragment.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
//        switch (position) {
//            case 0:
//                title = "Location";
//                break;
//            case 1:
//                title = "Posts";
//                break;
//            case 2:
//                title = "Photos";
//                break;
//            case 3:
//                title = "About";
//                break;
//        }
//        return title;
        return null;
    }
}
