//package com.example.cargas.adapter;
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentPagerAdapter;
//import android.view.ViewGroup;
//
//public class MyPagerAdapter extends FragmentPagerAdapter {
//
//	public MyPagerAdapter(FragmentManager fm) {
//
//		super(fm);
//	}
//
//	private final String[] titles = { "1", "2", "3" };
//
//	@Override
//	public CharSequence getPageTitle(int position) {
//		return titles[position];
//	}
//
//	@Override
//	public int getCount() {
//		return titles.length;
//	}
//
//	@Override
//	public Fragment getItem(int position) {
//		return list.get(position);
//	}
//
//	public Object instantiateItem(ViewGroup container, int position) {
//		tagList.add(makeFragmentName(container.getId(),
//				(int) getItemId(position)));
//		return super.instantiateItem(container, position);
//	}
//
//	public void update(int item) {
//		Fragment fragment = fm.findFragmentByTag(tagList.get(item));
//		if (fragment != null) {
//			switch (item) {
//			case 0:
//
//				break;
//			case 1:
//				((QueryFragment) fragment).update();
//				break;
//			case 2:
//
//				break;
//			default:
//				break;
//			}
//		}
//	}
//
//}
