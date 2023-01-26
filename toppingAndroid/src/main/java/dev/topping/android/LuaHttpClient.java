package dev.topping.android;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.osspecific.utils.MultipartUtility;

/**
 * Class that handles HTTP POST and GET requests
 */
@LuaClass(className = "LuaHttpClient")
public class LuaHttpClient implements LuaInterface
{
	public URLConnection httpClient;
	public String tag;
	public LuaTranslator onComplete = null;
	public LuaTranslator onFail = null;
	public String contentType;

	private static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		return s.hasNext() ? s.next() : "";
	}

	private static byte[] convertStreamToByteArray(java.io.InputStream is) {
		int nRead;
		byte[] data = new byte[16384];
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		try {
			while ((nRead = is.read(data, 0, data.length)) != -1) {
				buffer.write(data, 0, nRead);
			}
			buffer.flush();
			return buffer.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new byte[0];
	}

	/**
	 * Creates LuaHttpClient Object From Lua.
	 * @param tag
	 * @return LuaHttpClient
	 */
	@LuaFunction(manual = false, methodName = "create", self = LuaHttpClient.class, arguments = { String.class })
	public static LuaHttpClient create(String tag)
	{
		LuaHttpClient lhc = new LuaHttpClient();
		lhc.httpClient = ToppingEngine.getInstance().getHttpClient(tag).getConnection();
		lhc.tag = tag;
		return lhc;
	}

	/**
	 * Sets the content type of the connection
	 * @param type type
	 */
	@LuaFunction(manual = false, methodName = "setContentType", arguments = { String.class })
	public void setContentType(String type)
	{
		contentType = type;
	}

	/**
	 * Start Form data.
	 * This is used to create multipart form data. After this use AppendPostData or AppendImageData.
	 * To end form use EndForm.
	 * return LuaObjectStore
	 */
	@LuaFunction(manual = false, methodName = "startForm", arguments = { })
	public LuaObjectStore startForm()
	{
		LuaObjectStore los = new LuaObjectStore();
		try
		{
			MultipartUtility mu = new MultipartUtility(httpClient);
			los.obj = mu;
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return los;
	}

	/**
	 * Add data to form.
	 * @param formData Form data created by StartForm.
	 * @param name id of the data.
	 * @param value value of the data.
	 */
	@LuaFunction(manual = false, methodName = "appendPostData", arguments = { Object.class, String.class, String.class })
	public void appendPostData(Object formData, String name, String value)
	{
		LuaObjectStore los = (LuaObjectStore)formData;
		@SuppressWarnings("unchecked")
		MultipartUtility mu = (MultipartUtility) los.obj;
		try
		{
			mu.addFormField(name, value);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Add file to form.
	 * @param formData Form data created by StartForm.
	 * @param name id of the data.
	 * @param file data of the file.
	 */
	@LuaFunction(manual = false, methodName = "appendFileData", arguments = { Object.class, String.class, String.class })
	public void appendFileData(Object formData, String name, Object file)
	{
		LuaObjectStore los = (LuaObjectStore)formData;
		@SuppressWarnings("unchecked")
		MultipartUtility mu = (MultipartUtility) los.obj;
		byte[] data = (byte[])file;
		try
		{
			mu.addFilePart(name, "upload.png", data);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Finishes the form data created.
	 * @param formData Form data created by StartForm.
	 */
	@LuaFunction(manual = false, methodName = "endForm", arguments = { Object.class })
	public void endForm(Object formData)
	{
	}

	/**
	 * Start asynchronous load of form.
	 * @param url url to send.
	 * @param formData Form data finished by EndForm.
	 * @param tag tag that is used to identify connection.
	 */
	@LuaFunction(manual = false, methodName = "startAsyncLoadForm", arguments = { String.class, Object.class, String.class })
	public void startAsyncLoadForm(String url, Object formData, String tag)
	{
		LuaObjectStore los = (LuaObjectStore)formData;
		@SuppressWarnings("unchecked")
		MultipartUtility mu = (MultipartUtility) los.obj;

		MultipartUtility.ByteArrayWriter parts = mu.getMultipart();

		URLConnection connection = null;
		try
		{
			connection = new URL(url).openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if(connection == null)
			return;

		if(contentType != null && contentType.compareTo("") != 0)
			connection.setRequestProperty("Content-Type", contentType);

		try
		{
			((HttpURLConnection)connection).setRequestMethod("POST");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		DataOutputStream dos = null;
		try
		{
			dos = (DataOutputStream) httpClient.getOutputStream();
			parts.flush();
			dos.writeBytes(parts.toString());
			dos.flush();

			parts.close();
			dos.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}


		AsyncTask<Object, String, String> helper = new AsyncTask<Object, String, String>()
		{
			CharSequence errorText = null;
			String resultString = "";
			@Override
			protected String doInBackground(Object... params)
			{
				URLConnection httpclient = (URLConnection) params[1];
				try
				{
					InputStream in = new BufferedInputStream(httpclient.getInputStream());
					resultString = convertStreamToString(in);
				}
				catch (Exception e)
				{
					Log.e("LuaHttpClient", "Error downloading list." + e.getMessage());
					errorText = "Error";
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result)
			{
				if(errorText != null)
					onFail.callIn(resultString, errorText);
				else
					onComplete.callIn(resultString);
			}
		};
		try
		{
			helper.execute(this, httpClient);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	/**
	 * Start asynchronous load.
	 * @param url url to send.
	 * @param data post data string.
	 * @param tag tag that is used to identify connection.
	 */
	@LuaFunction(manual = false, methodName = "startAsyncLoad", arguments = { String.class, String.class, String.class })
	public void startAsyncLoad(String url, String data, String tag)
	{
		URLConnection connection = null;
		try
		{
			connection = new URL(url).openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if(connection == null)
			return;

		if(contentType != null && contentType.compareTo("") != 0)
			connection.setRequestProperty("Content-Type", contentType);

		try
		{
			((HttpURLConnection)connection).setRequestMethod("POST");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		String[] arr = data.split("#");
		StringBuilder sbPostData = new StringBuilder();
		try
		{
			for(String s : arr)
			{
				String[] arrIn = s.split("=");
				sbPostData.append(arrIn[0]).append("=").append(URLEncoder.encode(arrIn[1], "UTF-8"));
			}
		}
		catch (Exception e)
		{
			sbPostData = new StringBuilder();
			sbPostData.append(data);
		}

		AsyncTask<Object, String, String> helper = new AsyncTask<Object, String, String>()
		{
			CharSequence errorText = null;
			String resultString = "";
			@Override
			protected String doInBackground(Object... params)
			{
				URLConnection httpclient = (URLConnection) params[1];
				StringBuilder postData = (StringBuilder) params[2];
				try
				{
					DataOutputStream wr = new DataOutputStream( httpclient.getOutputStream());
					wr.write(postData.toString().getBytes("UTF-8"));
					InputStream in = new BufferedInputStream(httpclient.getInputStream());
					resultString = convertStreamToString(in);
				}
				catch (Exception e)
				{
					Log.e("LuaHttpClient", "Error downloading list." + e.getMessage());
					errorText = "Error";
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result)
			{
				if(errorText != null && onFail != null)
					onFail.callIn(resultString, errorText);
				else if(errorText == null && onComplete != null)
					onComplete.callIn(resultString);
			}
		};
		try
		{
			helper.execute(this, httpClient, sbPostData);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	/**
	 * Start asynchronous load.
	 * @param url url to send.
	 * @param tag tag that is used to identify connection.
	 */
	@LuaFunction(manual = false, methodName = "startAsyncLoadGet", arguments = { String.class, String.class })
	public void startAsyncLoadGet(String url, String tag)
	{
		URLConnection connection = null;
		try
		{
			connection = new URL(url).openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if(connection == null)
			return;

		if(contentType != null && contentType.compareTo("") != 0)
			connection.setRequestProperty("Content-Type", contentType);

		try
		{
			((HttpURLConnection)connection).setRequestMethod("GET");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		AsyncTask<Object, String, String> helper = new AsyncTask<Object, String, String>()
		{
			CharSequence errorText = null;
			String resultString = "";
			@Override
			protected String doInBackground(Object... params)
			{
				URLConnection httpclient = (URLConnection) params[1];
				StringBuilder postData = (StringBuilder) params[2];
				try
				{
					InputStream in = new BufferedInputStream(httpclient.getInputStream());
					resultString = convertStreamToString(in);
				}
				catch (Exception e)
				{
					Log.e("LuaHttpClient", "Error downloading list." + e.getMessage());
					errorText = "Error";
				}
				return null;
			}

			@Override
			protected void onPostExecute(String result)
			{
				if(errorText != null)
					onFail.callIn(resultString, errorText);
				else
					onComplete.callIn(resultString);
			}
		};
		try
		{
			helper.execute(this, httpClient);
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}
	}

	/**
	 * Start synchronous load.
	 * @param url url to send.
	 * @param data post data string.
	 * @return String value of returned data.
	 */
	@LuaFunction(manual = false, methodName = "startLoad", arguments = { String.class, String.class })
	public String startLoad(String url, String data)
	{
		URLConnection connection = null;
		try
		{
			connection = new URL(url).openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if(connection == null)
			return "";

		if(contentType != null && contentType.compareTo("") != 0)
			connection.setRequestProperty("Content-Type", contentType);

		String[] arr = data.split("#");
		StringBuilder sbPostData = new StringBuilder();
		try
		{
			for(String s : arr)
			{
				String[] arrIn = s.split("=");
				sbPostData.append(arrIn[0]).append("=").append(URLEncoder.encode(arrIn[1], "UTF-8"));
			}
		}
		catch (Exception e)
		{
			sbPostData = new StringBuilder();
			sbPostData.append(data);
		}

		CharSequence errorText = null;
		String resultString = "";

		try
		{
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.write(sbPostData.toString().getBytes("UTF-8"));
			InputStream in = new BufferedInputStream(connection.getInputStream());
			resultString = convertStreamToString(in);
		}
		catch (Exception e)
		{
			Log.e("LuaHttpClient", "Error downloading list." + e.getMessage());
			errorText = "Hata, daha sonra tekrar deneyin!";
		}

		if(errorText != null)
			return errorText.toString();
		else
			return resultString;
	}

	/**
	 * Start synchronous load.
	 * @param url url to send.
	 * @return String value of returned data.
	 */
	@LuaFunction(manual = false, methodName = "startLoadGet", arguments = { String.class })
	public String startLoadGet(String url)
	{
		URLConnection connection = null;
		try
		{
			connection = new URL(url).openConnection();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if(connection == null)
			return "";

		if(contentType != null && contentType.compareTo("") != 0)
			connection.setRequestProperty("Content-Type", contentType);

		CharSequence errorText = null;
		String resultString = "";

		try
		{
			InputStream in = new BufferedInputStream(connection.getInputStream());
			resultString = convertStreamToString(in);
		}
		catch (Exception e)
		{
			Log.e("LuaHttpClient", "Error downloading list." + e.getMessage());
			errorText = "Hata, daha sonra tekrar deneyin!";
		}

		if(errorText != null)
			return errorText.toString();
		else
			return resultString;
	}

	/**
	 * Set timeout of connection
	 * @param timeout timeout value seconds
	 */
	@LuaFunction(manual = false, methodName = "setTimeout", arguments = { Integer.class })
	public void setTimeout(Integer timeout)
	{
		Integer timeoutMs = new Integer(timeout * 1000);
		httpClient.setReadTimeout(timeoutMs);
		httpClient.setConnectTimeout(timeoutMs);
	}

	/**
	 * Sets combo changed listener
	 * @param lt +fun(client: LuaHttpClient, context: LuaContext, resultString: string):void
	 */
	@LuaFunction(manual = false, methodName = "setOnFinishListener", arguments = { LuaTranslator.class })
	public void setOnFinishListener(final LuaTranslator lt)
	{
		onComplete = lt;
	}

	/**
	 * Sets combo changed listener
	 * @param lt +fun(client: LuaHttpClient, context: LuaContext, resultString: string, error: string):void
	 */
	@LuaFunction(manual = false, methodName = "setOnFailListener", arguments = { LuaTranslator.class })
	public void setOnFailListener(final LuaTranslator lt)
	{
		onFail = lt;
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaHttpClient";
	}

}
