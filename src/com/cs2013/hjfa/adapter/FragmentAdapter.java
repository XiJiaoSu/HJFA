package com.cs2013.hjfa.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

/**
 * 主界面中的viewpager的适配器
 * 
 * 
 */
public class FragmentAdapter<T extends Fragment> extends FragmentPagerAdapter {

	private List<T> mFragments = null;

	public FragmentAdapter(FragmentManager fm, List<T> list) {
		super(fm);
		this.mFragments = list;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragments != null ? mFragments.get(position) : null;
	}

	@Override
	public int getCount() {
		return mFragments != null ? mFragments.size() : 0;
	}
	

}
