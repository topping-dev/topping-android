package dev.topping.android;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAdapter.CreateNdefMessageCallback;
import android.nfc.NfcEvent;
import android.os.Build;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

public class LuaNFC implements LuaInterface, CreateNdefMessageCallback
{
	LuaContext context;
	LuaTranslator ltTagRead;
	private NfcAdapter mNfcAdapter;
	private PendingIntent mPendingIntent;
	private List<NdefRecord> recordList = new ArrayList<NdefRecord>();
	private final String HASH_TAG = "alng://";

	private NfcAdapter GetAdapter()
	{
		if(mNfcAdapter == null)
			mNfcAdapter = NfcAdapter.getDefaultAdapter(context.GetContext());
		return mNfcAdapter;
	}

	public void CreateMessage()
	{

	}

	public void CreateCommonRecord(int type, byte[] payload)
	{
		String val = HASH_TAG;

		val += type + "/";

		byte[] hash = val.getBytes(Charset.forName("UTF-16LE")); //UTF-16LE because windows phone sux
		byte[] combined = new byte[hash.length + payload.length];

		System.arraycopy(hash, 0, combined, 0, hash.length);
		System.arraycopy(payload, 0, combined, hash.length,payload.length);
		NdefRecord rec = new NdefRecord(NdefRecord.TNF_ABSOLUTE_URI, combined, new byte[0], new byte[0]);
		recordList.add(rec);
	}

	/*
	 * (Ignore)
	 */
	@Override
	public NdefMessage createNdefMessage(NfcEvent event)
	{
		NdefMessage msg = new NdefMessage((NdefRecord[]) recordList.toArray());
		return msg;
	}

	@LuaFunction(manual = false, methodName = "IsAvailable")
	public boolean IsAvailable()
	{
		//No nfc before 9
		if(Build.VERSION.SDK_INT < Build.VERSION_CODES.GINGERBREAD)
			return false;
		return GetAdapter() != null;
	}

	/**
	 * Sets combo changed listener
	 * @param lt +fun(context: LuaContext, tagdata: userdata):void
	 */
	@LuaFunction(manual = false, methodName = "SetOnTagReadListener", arguments = { LuaTranslator.class })
	public void SetOnTagReadListener(final LuaTranslator lt)
	{
		ltTagRead = lt;
		mPendingIntent = PendingIntent.getActivity(LuaForm.Companion.GetActiveForm(), 0, new Intent(LuaForm.Companion.GetActiveForm(), getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP), 0);
		LuaForm.Companion.GetActiveForm().SetNfc(GetAdapter(), mPendingIntent);
	}

	@Override
	public String GetId()
	{
		return "LuaNFC";
	}
}
