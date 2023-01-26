package dev.topping.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;

/**
 * Lua Graphics manipulation class
 */
@LuaClass(className = "LuaGraphics")
public class LuaGraphics implements LuaInterface
{
    //Pen == paint android
    private Canvas gr;
    private Context c;
    private Paint lastPen;
    private Paint lastBrush;
    //private HashMap<String, Paint> m_fontStore;
    private Rect rectStore1;
    private Rect rectStore2;

    /**
     * (Ignore)
     */
    public LuaGraphics(Canvas g, Context cP)
    {
        gr = g;
        c = cP;
        rectStore1 = new Rect();
        rectStore2 = new Rect();
        lastPen = new Paint();
        lastBrush = new Paint();
        //m_fontStore = new HashMap<String, Paint>();
    }

    /**
     * (Ignore)
     */
    public static LuaGraphics fromGraphics(Canvas gr, Context c)
    {
        return new LuaGraphics(gr, c);
    }

    /**
     * Sets the pen color.
     * @param r
     * @param g
     * @param b
     */
    @LuaFunction(manual = false, methodName= "setPen", arguments = { Integer.class, Integer.class, Integer.class })
    public void setPen(int r, int g, int b)
    {
        lastPen = new Paint();
        lastPen.setColor(Color.rgb(r, g, b));
    }

    /**
     * Sets the pen color and width
     * @param r
     * @param g
     * @param b
     * @param width
     */
    @LuaFunction(manual = false, methodName= "setPenEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class })
    public void setPenEx(int r, int g, int b, float width)
    {
        lastPen = new Paint();
        lastPen.setColor(Color.rgb(r, g, b));
        lastPen.setStrokeWidth(width);
    }

    /**
     * Sets the brush type and color
     * @param type
     * @param r
     * @param g
     * @param b
     */
    @LuaFunction(manual = false, methodName= "setBrush", arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
    public void setBrush(int type, int r, int g, int b)
    {
        /*if (type == 0)
        {
            lastBrush = new SolidBrush(Color.FromArgb(r, g, b));
        }
        else
        {
            lastBrush = new TextureBrush(new Bitmap("asd"));
        }*/
    }

    /**
     * Sets the rectangle store based on id
     * @param id
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "setRectStore", arguments = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void setRectStore(int id, int x, int y, int width, int height)
    {
        if (id == 0)
            rectStore1 = new Rect(x, y, x+width, y+height);
        else
            rectStore2 = new Rect(x, y, x+width, y+height);
    }

    /**
     * Draws an ellipse from rectangle store
     */
    @LuaFunction(manual = false, methodName= "drawEllipsePenRectCache", arguments = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawEllipsePenRectCache()
    {
    	gr.drawOval(new RectF(rectStore1), lastPen);
    }

    /**
     * Draws an ellipse from rectangle store
     * @param r
     * @param g
     * @param b
     */
    @LuaFunction(manual = false, methodName= "drawEllipseRectCache", arguments = { Integer.class, Integer.class, Integer.class })
    public void drawEllipseRectCache(int r, int g, int b)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
        gr.drawOval(new RectF(rectStore1), p);
    }

    /**
     * Draws an ellipse from rectangle store
     * @param r
     * @param g
     * @param b
     * @param width
     */
    @LuaFunction(manual = false, methodName= "drawEllipseRectCacheEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class })
    public void drawEllipseRectCacheEx(int r, int g, int b, float width)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	p.setStrokeWidth(width);    	
        gr.drawOval(new RectF(rectStore1), p);
    }

    /**
     * Draws an ellipse from last pen used
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "drawEllipsePenCache", arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawEllipsePenCache(int x, int y, int width, int height)
    {    	
        gr.drawOval(new RectF(x, y, width, height), lastPen);
    }

    /**
     * Draws an ellipse
     * @param r
     * @param g
     * @param b
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "drawEllipse", arguments = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawEllipse(int r, int g, int b, int x, int y, int width, int height)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
        gr.drawOval(new RectF(x, y, width, height), p);
    }

    /**
     * Draws an ellipse
     * @param r
     * @param g
     * @param b
     * @param penWidth
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "drawEllipseEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawEllipseEx(int r, int g, int b, float penWidth, int x, int y, int width, int height)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	p.setStrokeWidth(width);
    	gr.drawOval(new RectF(x, y, width, height), p);
    }

    /**
     * Draws circle based on last pen
     * @param x
     * @param y
     * @param radius
     */
    @LuaFunction(manual = false, methodName= "drawCirclePenCache", arguments = { Integer.class, Integer.class, Integer.class })
    public void drawCirclePenCache(int x, int y, int radius)
    {
    	gr.drawCircle(x, y, radius, lastPen);
    }

    /**
     * Draws cicle
     * @param r
     * @param g
     * @param b
     * @param x
     * @param y
     * @param radius
     */
    @LuaFunction(manual = false, methodName= "drawCircle", arguments = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawCircle(int r, int g, int b, int x, int y, int radius)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	gr.drawCircle(x, y, radius, p);
    }

    /**
     * Draws circle
     * @param r
     * @param g
     * @param b
     * @param width
     * @param x
     * @param y
     * @param radius
     */
    @LuaFunction(manual = false, methodName= "drawCircleEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class, Integer.class, Integer.class, Integer.class })
    public void drawCircleEx(int r, int g, int b, float width, int x, int y, int radius)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	p.setStrokeWidth(width);    	
    	gr.drawCircle(x, y, radius, p);
    }

    /**
     * Draws icon
     * @param i
     * @param x
     * @param y
     */
    @LuaFunction(manual = false, methodName= "drawIcon", arguments = { String.class, Integer.class, Integer.class })
    public void drawIcon(String i, int x, int y)
    {
    	int id = c.getResources().getIdentifier(i, "drawable", c.getPackageName());
    	Bitmap b = ((BitmapDrawable)c.getResources().getDrawable(id)).getBitmap();
        gr.drawBitmap(b, x, y, null);
    }

    /**
     * Draws image
     * @param i
     * @param x
     * @param y
     */
    @LuaFunction(manual = false, methodName= "drawImage", arguments = { String.class, Integer.class, Integer.class })
    public void drawImage(String i, int x, int y)
    {
    	int id = c.getResources().getIdentifier(i, "drawable", c.getPackageName());
    	Bitmap b = ((BitmapDrawable)c.getResources().getDrawable(id)).getBitmap();
    	gr.drawBitmap(b, x, y, null);
    }

    /**
     * Draws image on rectangle cache based on source
     * @param i
     * @param srcX
     * @param srcY
     * @param srcWidth
     * @param srcHeight
     * @param rL
     * @param gL
     * @param bL
     * @param rH
     * @param gH
     * @param bH
     */
    @LuaFunction(manual = false, methodName= "drawImageRectCacheEx", arguments = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawImageRectCacheEx(String i, int srcX, int srcY, int srcWidth, int srcHeight, int rL, int gL, int bL, int rH, int gH, int bH)
    {
    	int id = c.getResources().getIdentifier(i, "drawable", c.getPackageName());
    	Bitmap b = ((BitmapDrawable)c.getResources().getDrawable(id)).getBitmap();
    	Paint p = new Paint();
        gr.drawBitmap(b, new Rect(srcX, srcY, srcWidth, srcHeight), rectStore1, p);
    }

    /**
     * Draws image on rectangle cache
     * @param i
     */
    @LuaFunction(manual = false, methodName= "drawImageRectCache", arguments = { String.class })
    public void drawImageRectCache(String i)
    {
    	int id = c.getResources().getIdentifier(i, "drawable", c.getPackageName());
    	Bitmap b = ((BitmapDrawable)c.getResources().getDrawable(id)).getBitmap();
    	Paint p = new Paint();
        gr.drawBitmap(b, rectStore1, rectStore2, p);
    }

    /**
     * Draws image
     * @param i
     * @param x
     * @param y
     * @param srcX
     * @param srcY
     * @param srcWidth
     * @param srcHeight
     */
    @LuaFunction(manual = false, methodName= "drawImageEx", arguments = { String.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawImageEx(String i, int x, int y, int srcX, int srcY, int srcWidth, int srcHeight)
    {
    	int id = c.getResources().getIdentifier(i, "drawable", c.getPackageName());
    	Bitmap b = ((BitmapDrawable)c.getResources().getDrawable(id)).getBitmap();
    	Paint p = new Paint();
    	gr.drawBitmap(b, x, y, p);
    }

    /**
     * Draws line
     * @param r
     * @param g
     * @param b
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    @LuaFunction(manual = false, methodName= "drawLine", arguments = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawLine(int r, int g, int b, int x1, int y1, int x2, int y2)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));    	
    	gr.drawLine(x1, y1, x2, y2, p);
    }

    /**
     * Draws line
     * @param r
     * @param g
     * @param b
     * @param width
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    @LuaFunction(manual = false, methodName= "drawLineEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawLineEx(int r, int g, int b, float width, int x1, int y1, int x2, int y2)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));   
    	p.setStrokeWidth(width);
        gr.drawLine(x1, y1, x2, y2, p);
    }

    /**
     * Draws line from last pen
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     */
    @LuaFunction(manual = false, methodName= "drawLinePenCache", arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawLinePenCache(int x1, int y1, int x2, int y2)
    {
        gr.drawLine(x1, y1, x2, y2, lastPen);
    }

    /**
     * Draws lines
     * @param r
     * @param g
     * @param b
     * @param points
     */
    @LuaFunction(manual = false, methodName= "drawLines", arguments = { Integer.class, Integer.class, Integer.class, String.class })
    public void drawLines(int r, int g, int b, String points)
    {
        String[] arr = points.split(",");
        float[] pointArr = new float[arr.length];
        for (int i = 0; i < arr.length; i += 2)
        {
            pointArr[i] = Float.valueOf(arr[i]);
        }
        
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
        gr.drawLines(pointArr, p);
    }

    /**
     * Draws lines
     * @param r
     * @param g
     * @param b
     * @param width
     * @param points
     */
    @LuaFunction(manual = false, methodName= "drawLinesEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class, String.class })
    public void drawLinesEx(int r, int g, int b, float width, String points)
    {
        String[] arr = points.split(",");
        float[] pointArr = new float[arr.length];
        for (int i = 0; i < arr.length; i += 2)
        {
            pointArr[i] = Float.valueOf(arr[i]);
        }

    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	p.setStrokeWidth(width);
        gr.drawLines(pointArr, p);
    }

    /**
     * Draws lines from pen caches
     * @param points
     */
    @LuaFunction(manual = false, methodName= "drawLinesPenCache", arguments = { String.class })
    public void drawLinesPenCache(String points)
    {
        String[] arr = points.split(",");
        float[] pointArr = new float[arr.length];
        for (int i = 0; i < arr.length; i += 2)
        {
            pointArr[i] = Float.valueOf(arr[i]);
        }

        
        gr.drawLines(pointArr, lastPen);
    }

    /**
     * Draws polygon
     * @param r
     * @param g
     * @param b
     * @param points
     */
    @LuaFunction(manual = false, methodName= "drawPolygon", arguments = { Integer.class, Integer.class, Integer.class, String.class })
    public void drawPolygon(int r, int g, int b, String points)
    {
        String[] arr = points.split(",");
        float[] pointArr = new float[arr.length];
        for (int i = 0; i < arr.length; i += 2)
        {
            pointArr[i] = Float.valueOf(arr[i]);
        }

    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	gr.drawPoints(pointArr, p);
    }

    /**
     * Draws polygon
     * @param r
     * @param g
     * @param b
     * @param width
     * @param points
     */
    @LuaFunction(manual = false, methodName= "drawPolygonEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class, String.class })
    public void drawPolygonEx(int r, int g, int b, float width, String points)
    {
        String[] arr = points.split(",");
        float[] pointArr = new float[arr.length];
        for (int i = 0; i < arr.length; i += 2)
        {
            pointArr[i] = Float.valueOf(arr[i]);
        }

    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	p.setStrokeWidth(width);
        gr.drawPoints(pointArr, p);
    }

    /**
     * Draws polygon from pen cache
     * @param points
     */
    @LuaFunction(manual = false, methodName= "drawPolygonPenCache", arguments = { String.class })
    public void drawPolygonPenCache(String points)
    {
        String[] arr = points.split(",");
        float[] pointArr = new float[arr.length];
        for (int i = 0; i < arr.length; i += 2)
        {
            pointArr[i] = Float.valueOf(arr[i]);
        }

        gr.drawPoints(pointArr, lastPen);
    }

    /**
     * Draws rectangle
     * @param r
     * @param g
     * @param b
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "drawRectangle", arguments = { Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawRectangle(int r, int g, int b, int x, int y, int width, int height)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
        gr.drawRect(x, y, x+width, y+width, p);
    }

    /**
     * Draws rectangle
     * @param r
     * @param g
     * @param b
     * @param widthP
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "drawRectangleEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class, Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawRectangleEx(int r, int g, int b, float widthP, int x, int y, int width, int height)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	p.setStrokeWidth(width);
        gr.drawRect(x, y, x+width, y+width, p);
    }

    /**
     * Draws rectangle from rectangle store
     * @param r
     * @param g
     * @param b
     */
    @LuaFunction(manual = false, methodName= "drawRectangleRectCache", arguments = { Integer.class, Integer.class, Integer.class })
    public void drawRectangleRectCache(int r, int g, int b)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
        gr.drawRect(rectStore1, p);    	
    }

    /**
     * Draws rectangle from rectangle store
     * @param r
     * @param g
     * @param b
     * @param width
     */
    @LuaFunction(manual = false, methodName= "drawRectangleRectCacheEx", arguments = { Integer.class, Integer.class, Integer.class, Float.class })
    public void drawRectangleRectCacheEx(int r, int g, int b, float width)
    {
    	Paint p = new Paint();
    	p.setColor(Color.rgb(r, g, b));
    	p.setStrokeWidth(width);
        gr.drawRect(rectStore1, p);
    }

    /**
     * Draws rectangle from rectangle store with pen cache
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "drawRectanglePenCache", arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
    public void drawRectanglePenCache(int x, int y, int width, int height)
    {
        gr.drawRect(new Rect(x, y, x+width, y+height), lastPen);
    }

    /**
     * Draws rectangle from rectangle store with pen cache
     */
    @LuaFunction(manual = false, methodName="drawRectanglePenRectCache")
    public void drawRectanglePenRectCache()
    {
    	gr.drawRect(rectStore1, lastPen);
    }

    /**
     * Draw string
     * @param s
     * @param f
     * @param size
     * @param style
     * @param x
     * @param y
     * @param valign
     * @param halign
     * @param flags
     */
    @LuaFunction(manual = false, methodName="drawString", arguments = { String.class, String.class, Float.class, Integer.class, Float.class, Float.class, Integer.class, Integer.class, Integer.class })
    public void drawString(String s, String f, float size, int style, float x, float y, int valign, int halign, int flags)
    {
    	/*
    	 *      // Summary:
        //     Specifies the text be aligned near the layout. In a left-to-right layout,
        //     the near position is left. In a right-to-left layout, the near position is
        //     right.
        Near = 0,
        //
        // Summary:
        //     Specifies that text is aligned in the center of the layout rectangle.
        Center = 1,
        //
        // Summary:
        //     Specifies that text is aligned far from the origin position of the layout
        //     rectangle. In a left-to-right layout, the far position is right. In a right-to-left
        //     layout, the far position is left.
        		Far = 2,
    	 */
        
        Paint p = new Paint(lastBrush);
        switch(halign)
        {
        case 0:
        	p.setTextAlign(Paint.Align.LEFT);
        	break;
        case 1:
        	p.setTextAlign(Paint.Align.CENTER);
        	break;
        case 2:
        	p.setTextAlign(Paint.Align.RIGHT);
        	break;
        }
        
        p.setTextSize(size);
    	/*
        Regular = 0,
        Bold = 1,
        Italic = 2,
        Underline = 4,
        Strikeout = 8,
    	 */
        int flagsAndroid = 0;
        if((flags & 4) > 0)
        	flagsAndroid |= Paint.STRIKE_THRU_TEXT_FLAG;
        if((flags & 8) > 0)
        	flagsAndroid |= Paint.UNDERLINE_TEXT_FLAG;
        p.setFlags(flagsAndroid);
        
        if((flags & 1) > 0)
    		p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        if((flags & 2) > 0)
        	p.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        if((flags & 3) > 0)
        	p.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD_ITALIC));
     
        gr.drawText(s, x, y, p);
    }

    /**
     * Fill ellipse
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "fillEllipse", arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
    public void fillEllipse(int x, int y, int width, int height)
    {
    	Paint p = new Paint(lastBrush);
    	p.setStyle(Paint.Style.FILL);
    	gr.drawOval(new RectF(x, y, width, height), p);
    }

    /**
     * Fill polygon
     * @param points
     */
    @LuaFunction(manual = false, methodName= "fillPolygon", arguments = { String.class })
    public void fillPolygon(String points)
    {
        String[] arr = points.split(",");
        float[] pointArr = new float[arr.length];
        for (int i = 0; i < arr.length; i += 2)
        {
            pointArr[i] = Float.valueOf(arr[i]);
        }
    	Paint p = new Paint(lastBrush);
    	p.setStyle(Paint.Style.FILL);
    	gr.drawPoints(pointArr, p);
    }

    /**
     * Fill rectangle
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "fillRectangle", arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
    public void fillRectangle(int x, int y, int width, int height)
    {
    	Paint p = new Paint(lastBrush);
    	p.setStyle(Paint.Style.FILL);
    	gr.drawRect(x, y, x+width, y+height, p);
    }

    /**
     * Fil region
     */
    @LuaFunction(manual = false, methodName= "fillRegion", arguments = { })
    public void fillRegion()
    {
    	Paint p = new Paint(lastBrush);
    	p.setStyle(Paint.Style.FILL);
    	gr.drawRect(rectStore1, p);
    }

    /**
     * Clear
     * @param red
     * @param green
     * @param blue
     */
    @LuaFunction(manual = false, methodName= "clear", arguments = { Integer.class, Integer.class, Integer.class })
    public void clear(int red, int green, int blue)
    {
    	gr.drawColor(Color.rgb(red, green, blue));
    }

    /**
     * Set drawing clip
     * @param x
     * @param y
     * @param width
     * @param height
     */
    @LuaFunction(manual = false, methodName= "setClip", arguments = { Integer.class, Integer.class, Integer.class, Integer.class })
    public void setClip(int x, int y, int width, int height)
    {
    	gr.clipRect(x, y, x+width, y+width);
    }

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId()
	{
		return "LuaGraphics";
	}
}
