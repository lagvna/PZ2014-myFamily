package com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragments.ArchiveFragment;
import com.fragments.ProductsFragment;
import com.fragments.ShoppingListFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

	public ShoppingListFragment slf;
	public ArchiveFragment af;
	public ProductsFragment pf;

	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 0:
			af = new ArchiveFragment();
			return af;
		case 1:
			slf = new ShoppingListFragment();
			return slf;
		case 2:
			pf = new ProductsFragment();
			return pf;
		}

		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

}