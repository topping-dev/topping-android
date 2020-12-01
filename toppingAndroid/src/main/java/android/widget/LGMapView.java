package android.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.util.Log;

import dev.topping.android.osspecific.utils.FragmentTracker;
import dev.topping.android.LuaColor;
import dev.topping.android.LuaForm;
import dev.topping.android.LuaMapCircle;
import dev.topping.android.LuaMapImage;
import dev.topping.android.LuaMapMarker;
import dev.topping.android.LuaMapPolygon;
import dev.topping.android.LuaPoint;
import dev.topping.android.LuaResource;
import dev.topping.android.LuaStream;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.osspecific.CustomMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * MapView
 */
@LuaClass(className = "LGMapView")
public class LGMapView extends LGView implements LuaInterface
{
	private String apiKey;
	private int viewIdToSet = 0;
	private CustomMapFragment mapFragment;

	/**
	 * Creates LGMapView Object From Lua.
	 * @param lc
	 * @param apikey
	 * @return LGMapView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class, String.class }, self = LGMapView.class)
	public static LGMapView Create(LuaContext lc, String apikey)
	{
		LGMapView mapView = new LGMapView(lc.GetContext());
		mapView.SetApiKey(apikey);
		return mapView;
	}

	/**
	 * (Ignore)
	 */
	private void SetApiKey(String apikey)
	{
		apiKey = apikey;
	}

	/**
	 * (Ignore)
	 */
	public LGMapView(Context context)
	{
		super(context);

	}

	/**
	 * (Ignore)
	 */
	public LGMapView(Context context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGMapView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGMapView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = new FrameLayout(context);
		CommonSetup(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = new FrameLayout(context, attrs);
		CommonSetup(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = new FrameLayout(context, attrs, defStyle);
		CommonSetup(context);
	}

	/**
	 * (Ignore)
	 */
	public void CommonSetup(Context context)
	{

	}

	/**
	 * (Ignore) 
	 */
	@Override
	public void onCreate()
	{
		super.onCreate();
		viewIdToSet = view.getId();//ViewIdGenerator.generateViewId();
		//view.setId(viewIdToSet);
		mapFragment = new CustomMapFragment();
		mapFragment.SetFragmentId(viewIdToSet);
		FragmentTracker ft = FragmentTracker.GetInstance(LuaForm.GetActiveForm().getSupportFragmentManager());
		ft.ReplaceFragment(viewIdToSet, mapFragment, false);
	}

	/**
	 * Adds circle to map based on parameters
	 * @param geoLoc LuaPoint
	 * @param radius
	 * @param strokeColor LuaColor
	 * @param fillColor LuaColor
	 * @return LuaMapCircle
	 */
	@LuaFunction(manual = false, methodName = "AddCircle", arguments = { LuaPoint.class, Double.class, LuaColor.class, LuaColor.class })
	public LuaMapCircle AddCircle(LuaPoint geoLoc, double radius, LuaColor strokeColor, LuaColor fillColor)
	{
		LuaMapCircle lmc = new LuaMapCircle(mapFragment.getMap().addCircle(new CircleOptions()
				.center(new LatLng(geoLoc.x, geoLoc.y))
				.radius(radius)
				.strokeColor(strokeColor.GetColorValue())
				.fillColor(fillColor.GetColorValue())));
		return lmc;
	}

	/**
	 * Adds marker to map
	 * @param geoLoc LuaPoint
	 * @param path path of the icon (can be null)
	 * @param icon filename of to icon (can be null)
	 * @return LuaMapMarker
	 */
	@LuaFunction(manual = false, methodName = "AddMarker", arguments = { LuaPoint.class, String.class, String.class })
	public LuaMapMarker AddMarker(LuaPoint geoLoc, String path, String icon)
	{
		MarkerOptions mo = new MarkerOptions();
		mo.position(new LatLng(geoLoc.x, geoLoc.y));
		if(icon != null)
		{
			LuaStream ls = LuaResource.GetResource(path, icon);
			if(ls != null && ls.GetStream() != null)
			{
				Bitmap bmp = BitmapFactory.decodeStream((InputStream) ls.GetStreamInternal());
				BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bmp);
				mo.icon(bd);
			}
		}
		LuaMapMarker lmm = new LuaMapMarker(mapFragment.getMap().addMarker(mo));
		return lmm;
	}

	/**
	 * Adds marker to map with extended options
	 * @param geoLoc LuaPoint
	 * @param path path of the icon (can be null)
	 * @param icon filename of to icon (can be null)
	 * @param anchor LuaPoint
	 * @return LuaMapMarker
	 */
	@LuaFunction(manual = false, methodName = "AddMarkerEx", arguments = { LuaPoint.class, String.class, String.class, LuaPoint.class })
	public LuaMapMarker AddMarkerEx(LuaPoint geoLoc, String path, String icon, LuaPoint anchor)
	{
		MarkerOptions mo = new MarkerOptions();
		mo.position(new LatLng(geoLoc.x, geoLoc.y))
				.anchor(anchor.x, anchor.y);
		if(icon != null)
		{
			LuaStream ls = LuaResource.GetResource(path, icon);
			if(ls != null && ls.GetStream() != null)
			{
				Bitmap bmp = BitmapFactory.decodeStream((InputStream) ls.GetStreamInternal());
				BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bmp);
				mo.icon(bd);
			}
		}
		LuaMapMarker lmm = new LuaMapMarker(mapFragment.getMap().addMarker(mo));
		return lmm;
	}

	/**
	 * Adds image to map
	 * @param geoLoc LuaPoint
	 * @param path path of the icon (can be null)
	 * @param icon filename of to icon (can be null)
	 * @param width
	 * @return LuaMapImage
	 */
	public LuaMapImage AddImage(LuaPoint geoPoint, String path, String icon, float width)
	{
		GroundOverlayOptions goo = new GroundOverlayOptions();
		goo.position(new LatLng(geoPoint.x, geoPoint.y), width);
		if(icon != null)
		{
			LuaStream ls = LuaResource.GetResource(path, icon);
			if(ls != null && ls.GetStream() != null)
			{
				Bitmap bmp = BitmapFactory.decodeStream((InputStream) ls.GetStreamInternal());
				BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bmp);
				goo.image(bd);
			}
		}
		else
			Log.e("LGMapView", "Image is needed for LuaMapImage");

		LuaMapImage lmi = new LuaMapImage(mapFragment.getMap().addGroundOverlay(goo));
		return lmi;
	}

	/**
	 * Adds marker to map with extended options
	 * @param points
	 * @param strokeColor LuaColor
	 * @param fillColor LuaColor
	 * @return LuaMapPolygon
	 */
	public LuaMapPolygon AddPolygon(HashMap<Integer, LuaPoint> points, LuaColor strokeColor, LuaColor fillColor)
	{
		PolygonOptions po = new PolygonOptions();
		for(Map.Entry<Integer, LuaPoint> kvp : points.entrySet())
		{
			po.add(new LatLng(kvp.getValue().x, kvp.getValue().y));
		}
		po.strokeColor(strokeColor.GetColorValue());
		po.fillColor(fillColor.GetColorValue());

		LuaMapPolygon lmp = new LuaMapPolygon(mapFragment.getMap().addPolygon(po));
		return lmp;
	}
}
