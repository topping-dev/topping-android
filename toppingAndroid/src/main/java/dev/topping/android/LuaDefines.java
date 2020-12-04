package dev.topping.android;

import android.content.Context;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Defines
 */
@LuaClass(className = "LuaDefines")
public class LuaDefines implements LuaInterface
{
	public static String TAG = "LuaDefines";

	//TODO:Add lua date
	@LuaFunction(manual = false, methodName = "GetHumanReadableDate", self = LuaDefines.class, arguments = { Integer.class })
	public static String GetHumanReadableDate(Integer value)
	{
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(System.currentTimeMillis());
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		int hour = cal.get(Calendar.HOUR);
		int minute = cal.get(Calendar.MINUTE);
		int second = cal.get(Calendar.SECOND);
		return day + "." + month + "." + year /*+ " " + hour + ":" + month + ":" + second*/;
	}

	@LuaFunction(manual = false, methodName = "RegisterAndConnectWifi", self = LuaDefines.class, arguments = { LuaContext.class, String.class, String.class })
	public static void RegisterAndConnectWifi(LuaContext lc, String ssid, String password)
	{
		connectToAP(lc.GetContext(), ssid, password);
	}

	public static void connectToAP(Context ctx, String ssid, String passkey)
	{
		Log.i(TAG, "* connectToAP");

		WifiConfiguration wifiConfiguration = new WifiConfiguration();

		WifiManager wifiManager = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);
		List<ScanResult> scanResultList = wifiManager.getScanResults();

		String networkSSID = ssid;
		String networkPass = passkey;

		Log.d(TAG, "# password " + networkPass);

		for (ScanResult result : scanResultList) {
			if (result.SSID.equals(networkSSID)) {

				String securityMode = getScanResultSecurity(result);

				if (securityMode.equalsIgnoreCase("OPEN")) {

					wifiConfiguration.SSID = "\"" + networkSSID + "\"";
					wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
					int res = wifiManager.addNetwork(wifiConfiguration);
					Log.d(TAG, "# add Network returned " + res);

					boolean b = wifiManager.enableNetwork(res, true);
					Log.d(TAG, "# enableNetwork returned " + b);

					wifiManager.setWifiEnabled(true);

				} else if (securityMode.equalsIgnoreCase("WEP")) {

					wifiConfiguration.SSID = "\"" + networkSSID + "\"";
					wifiConfiguration.wepKeys[0] = "\"" + networkPass + "\"";
					wifiConfiguration.wepTxKeyIndex = 0;
					wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
					wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
					int res = wifiManager.addNetwork(wifiConfiguration);
					Log.d(TAG, "### 1 ### add Network returned " + res);

					boolean b = wifiManager.enableNetwork(res, true);
					Log.d(TAG, "# enableNetwork returned " + b);

					wifiManager.setWifiEnabled(true);
				}

				wifiConfiguration.SSID = "\"" + networkSSID + "\"";
				wifiConfiguration.preSharedKey = "\"" + networkPass + "\"";
				wifiConfiguration.hiddenSSID = true;
				wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
				wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
				wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
				wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
				wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
				wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
				wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
				wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);

				int res = wifiManager.addNetwork(wifiConfiguration);
				Log.d(TAG, "### 2 ### add Network returned " + res);

				wifiManager.enableNetwork(res, true);

				boolean changeHappen = wifiManager.saveConfiguration();

				if(res != -1 && changeHappen){
					Log.d(TAG, "### Change happen");

					//AppStaticVar.connectedSsidName = networkSSID;

				}else{
					Log.d(TAG, "*** Change NOT happen");
				}

				wifiManager.setWifiEnabled(true);
			}
		}
	}

	public static String getScanResultSecurity(ScanResult scanResult) {
		Log.i(TAG, "* getScanResultSecurity");

		final String cap = scanResult.capabilities;
		final String[] securityModes = { "WEP", "PSK", "EAP" };

		for (int i = securityModes.length - 1; i >= 0; i--) {
			if (cap.contains(securityModes[i])) {
				return securityModes[i];
			}
		}

		return "OPEN";
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaDefines";
	}
}
