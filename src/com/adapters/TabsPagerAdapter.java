package com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragments.ArchiveFragment;
import com.fragments.ProductsFragment;
import com.fragments.ShoppingListFragment;


/**
 * PageAdapter uzywany do wyswietlania fragmentow 
 * w aktywnosci TaskActivity
 * @author kwachu
 *
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
	/**
	 * pole przechowujące fragment shopping list
	 */
	public ShoppingListFragment slf;
	/**
	 * pole przechowujące fragment archive
	 */
	public ArchiveFragment af;
	/**
	 * pole przechowujące fragment products
	 */
	public ProductsFragment pf;
	/**
	 * Główny adapter klasy
	 * @param fm fragmentManager
	 */
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
