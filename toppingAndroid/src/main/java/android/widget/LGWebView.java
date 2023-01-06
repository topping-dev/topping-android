package android.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import dev.topping.android.LuaTranslator;
import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;

/**
 * Button
 */
@LuaClass(className = "LGWebView")
public class LGWebView extends LGTextView implements LuaInterface
{
	private LuaTranslator ltRequestAction;

	/**
	 * Creates LGWebView Object From Lua.
	 * @param lc
	 * @return LGWebView
	 */
	@LuaFunction(manual = false, methodName = "Create", arguments = { LuaContext.class }, self = LGWebView.class)
	public static LGWebView Create(LuaContext lc)
	{
		return new LGWebView(lc);
	}

	/**
	 * (Ignore)
	 */
	public LGWebView(LuaContext context)
	{
		super(context);
	}

	/**
	 * (Ignore)
	 */
	public LGWebView(LuaContext context, String luaId)
	{
		super(context, luaId);
	}

	/**
	 * (Ignore)
	 */
	public LGWebView(LuaContext context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public LGWebView(LuaContext context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context)
	{
		view = lc.GetLayoutInflater().createView(context, "WebView");
		if(view == null)
			view = new WebView(context);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs)
	{
		view = lc.GetLayoutInflater().createView(context, "WebView", attrs);
		if(view == null)
			view = new WebView(context, attrs);
	}

	/**
	 * (Ignore)
	 */
	public void Setup(Context context, AttributeSet attrs, int defStyle)
	{
		view = lc.GetLayoutInflater().createView(context, "WebView", attrs);
		if(view == null)
			view = new WebView(context, attrs, defStyle);
	}

	/**
	 * (Ignore)
	 */
	@Override
	public void AfterSetup(Context context) {
		super.AfterSetup(context);
		((WebView)view).setWebViewClient(new WebViewClient()
		{
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
				if(ltRequestAction == null)
					return super.shouldOverrideUrlLoading(view, request);
				else {
					return (boolean) ltRequestAction.CallIn(request.getUrl().toString());
				}
			}
		});
		((WebView)view).setWebChromeClient(new WebChromeClient());
	}

	/**
	 * Set webview configuration
	 * @param enableJavascript
	 * @param enableDomStorage
	 */
	@LuaFunction(manual = false, methodName = "SetConfiguration", arguments = { Boolean.class, Boolean.class })
	public void SetConfiguration(boolean enableJavascript, boolean enableDomStorage) {
		((WebView)view).getSettings().setJavaScriptEnabled(enableJavascript);
		((WebView)view).getSettings().setDomStorageEnabled(enableDomStorage);
	}

	/**
	 * Load url
	 * @param url
	 */
	@LuaFunction(manual = false, methodName = "Load", arguments = { String.class })
	public void Load(String url) {
		((WebView)view).loadUrl(url);
	}

	/**
	 * Load data
	 * @param data
	 * @param mimeType
	 * @param encoding
	 * @param baseUrl
	 */
	@LuaFunction(manual = false, methodName = "LoadData", arguments = { String.class, String.class, String.class, String.class })
	public void LoadData(String data, String mimeType, String encoding, String baseUrl) {
		((WebView)view).loadDataWithBaseURL(baseUrl, data, mimeType, encoding, null);
	}

	/**
	 * Stops loading
	 */
	@LuaFunction(manual = false, methodName = "StopLoading", arguments = { })
	public void StopLoading() {
		((WebView)view).stopLoading();
	}

	/**
	 * Check is loading
	 * @return boolean
	 */
	@LuaFunction(manual = false, methodName = "IsLoading", arguments = { })
	public boolean IsLoading() {
		return ((WebView)view).getProgress() != 100;
	}

	/**
	 * Can go back
	 * @return boolean
	 */
	@LuaFunction(manual = false, methodName = "CanGoBack", arguments = { })
	public boolean CanGoBack() {
		return ((WebView)view).canGoBack();
	}

	/**
	 * Can go forward
	 * @return boolean
	 */
	@LuaFunction(manual = false, methodName = "CanGoForward", arguments = { })
	public boolean CanGoForward() {
		return ((WebView)view).canGoForward();
	}

	/**
	 * Go back
	 */
	@LuaFunction(manual = false, methodName = "GoBack", arguments = { })
	public void GoBack() {
		((WebView)view).goBack();
	}

	/**
	 * Go forward
	 */
	@LuaFunction(manual = false, methodName = "GoForward", arguments = { })
	public void GoForward() {
		((WebView)view).goForward();
	}

	/**
	 * Set request action before loading
	 * @param lt +fun(webView: LGWebView, url: string):boolean
	 */
	@LuaFunction(manual = false, methodName = "SetRequestAction", arguments = { LuaTranslator.class })
	public void SetRequestAction(LuaTranslator lt) {
		this.ltRequestAction = lt;
	}
}
