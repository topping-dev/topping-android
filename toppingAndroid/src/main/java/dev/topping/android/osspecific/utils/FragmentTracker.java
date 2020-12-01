package dev.topping.android.osspecific.utils;

import java.util.UUID;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentManager.OnBackStackChangedListener;
import androidx.fragment.app.FragmentTransaction;

public class FragmentTracker implements OnBackStackChangedListener
{
	private static FragmentTracker mInstance = null;
	
	public static FragmentTracker GetInstance(FragmentManager fm)
	{
		if(mInstance == null)
		{
			mInstance = new FragmentTracker();
			fm.addOnBackStackChangedListener(mInstance);
		}
		
		mInstance.SetFragmentManager(fm);
		
		return mInstance;
	}
	
	private FragmentManager fragmentManager;
	private String foremostFragmentName = "";
		
	private FragmentTracker()
	{
		
	}
	
	public void SetFragmentManager(FragmentManager fm)
	{
		fragmentManager = fm;
	}
	
	public void AddFragment(Fragment fragment)
	{
		String uuid = UUID.randomUUID().toString();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.add(android.R.id.content, fragment, uuid);
		fragmentTransaction.addToBackStack(uuid);
		fragmentTransaction.commit();
	}
	
	public void AddFragment(Fragment fragment, Fragment toRemove)
	{
		String uuid = UUID.randomUUID().toString();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.remove(toRemove);
		fragmentTransaction.add(android.R.id.content, fragment, uuid);
		fragmentTransaction.addToBackStack(uuid);
		fragmentTransaction.commit();
	}
	
	public void AddFragments(int parent, Fragment[] arr)
	{
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		for(Fragment f : arr)
		{
			String uuid = UUID.randomUUID().toString();
			fragmentTransaction.add((parent == 0) ? android.R.id.content : parent, f, uuid);
			fragmentTransaction.addToBackStack(uuid);
		}
		fragmentTransaction.commit();
	}
	
	public void RemoveFragment(Fragment fragment)
	{
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.remove(fragment);
		fragmentTransaction.commit();
	}
	
	public void ReplaceFragment(Fragment fragment)
	{
		ReplaceFragment(fragment, true);
	}
	
	public void ReplaceFragment(Fragment fragment, boolean addToBackStack)
	{
		String uuid = UUID.randomUUID().toString();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace(android.R.id.content, fragment, uuid);
		if(addToBackStack)
			fragmentTransaction.addToBackStack(uuid);
		fragmentTransaction.commit();
	}
	
	public void ReplaceFragment(int parent, Fragment fragment, boolean addToBackStack)
	{
		String uuid = UUID.randomUUID().toString();
		FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
		fragmentTransaction.replace((parent == 0) ? android.R.id.content : parent, fragment, uuid);
		if(addToBackStack)
			fragmentTransaction.addToBackStack(uuid);
		fragmentTransaction.commit();
	}
	
	public Fragment GetVisibleFragment()
	{
		if(foremostFragmentName == "")
			return null;
		return fragmentManager.findFragmentByTag(foremostFragmentName);
	}
	
	public boolean IsBaseFragment()
	{
		return foremostFragmentName == "";
	}

	@Override
	public void onBackStackChanged()
	{
		if(fragmentManager.getBackStackEntryCount() > 0)
		{
			FragmentManager.BackStackEntry bse = (FragmentManager.BackStackEntry) fragmentManager.getBackStackEntryAt(fragmentManager.getBackStackEntryCount() - 1);
			foremostFragmentName = bse.getName();
		}
		else
		{
			foremostFragmentName = "";
		}
			
	}
}
