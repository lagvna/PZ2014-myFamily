package com.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.fragments.ChartFragment;
import com.fragments.NewExpenseFragment;
import com.fragments.SummaryFragment;
/**
 * Klasa adapter do zarządzania listą zakładek w wydatkach
 * @author KMACIAZE
 */
public class TabsPagerAdapterExpenses extends FragmentPagerAdapter {
	/**
	 * pole przechowujące fragment summary
	 */
	public SummaryFragment sf;
	/**
	 * pole przechowujące fragment chart
	 */
	public ChartFragment cf;
	/**
	 * pole przechowujące fragment new expense
	 */
	public NewExpenseFragment nef;
	/**
	 * Główny konstruktor klasy
	 * @param fm fragment manager
	 */
	public TabsPagerAdapterExpenses(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
		case 1:
			sf = new SummaryFragment();
			return sf;
		case 2:
			cf = new ChartFragment();
			return cf;
		case 0:
			nef = new NewExpenseFragment();
			return nef;
		}

		return null;
	}

	@Override
	public int getCount() {
		return 3;
	}

}