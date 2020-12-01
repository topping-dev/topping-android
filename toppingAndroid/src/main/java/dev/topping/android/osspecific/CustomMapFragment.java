package dev.topping.android.osspecific;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;

import java.lang.reflect.Field;

import androidx.fragment.app.Fragment;

public class CustomMapFragment extends SupportMapFragment
{
	private int fragmentId = 0;
	private GoogleMap mMap;

	public CustomMapFragment() 
	{
		super();
	}
	
	public void SetFragmentId(int id)
	{
		fragmentId = id;
	}

	@Override
	public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
	    View v = super.onCreateView(arg0, arg1, arg2);
	    initMap();
	    return v;
	}
	
	private void initMap()
	{
		getMapAsync(new OnMapReadyCallback()
		{
			@Override
			public void onMapReady(GoogleMap googleMap)
			{
				mMap = googleMap;
				UiSettings settings = mMap.getUiSettings();
				settings.setAllGesturesEnabled(false);
				settings.setMyLocationButtonEnabled(false);
			}
		});

	    /*getMap().moveCamera(CameraUpdateFactory.newLatLngZoom(mPosFija,16));
	    getMap().addMarker(new MarkerOptions().position(mPosFija).icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));*/
	}
	
	@Override
	public void onDetach() {
	    super.onDetach();

	    try {
	        Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
	        childFragmentManager.setAccessible(true);
	        childFragmentManager.set(this, null);

	    } catch (NoSuchFieldException e) {
	        throw new RuntimeException(e);
	    } catch (IllegalAccessException e) {
	        throw new RuntimeException(e);
	    }
	}

	public GoogleMap getMap()
	{
		return mMap;
	}
}
