package com.qlphong.adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.qlphong.view.PhongFragment;
import com.qlphong.view.TaiSanFragment;

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 1:
                TaiSanFragment taiSanFragment = new TaiSanFragment();
                return taiSanFragment;
            default:
                PhongFragment phongFragment = new PhongFragment();
                return phongFragment;
        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 1:
                return "Tài sản";
            default:
                return "Phòng";
        }
    }
}
