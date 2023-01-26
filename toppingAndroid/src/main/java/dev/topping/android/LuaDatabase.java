package dev.topping.android;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

import dev.topping.android.backend.LuaClass;
import dev.topping.android.backend.LuaFunction;
import dev.topping.android.backend.LuaInterface;
import dev.topping.android.luagui.LuaContext;
import dev.topping.android.osspecific.DatabaseHelper;

/**
 * Lua database interface for SQLite operations.
 * This class is used to create database file and manupilate it from lua.
 */
@LuaClass(className = "LuaDatabase")
public class LuaDatabase implements LuaInterface
{
	
	public static SQLiteDatabase openedDatabase;
	/**
	 * Database Object that is stored.
	 */
	DatabaseHelper db;
	
	/**
	 * Creates LuaDatabase Object From Lua.
	 * @return LuaDatabase
	 */
	@LuaFunction(manual = false, methodName = "create", self = LuaDatabase.class, arguments = { LuaContext.class })
    public static LuaDatabase create(LuaContext context)
    {
        LuaDatabase db = new LuaDatabase();
        db.db = new DatabaseHelper(context.getContext());
        return db;
    }
	
	/**
	 * Checks and Creates Database File on Storage.
	 */
	@LuaFunction(manual = false, methodName = "checkAndCreateDatabase", arguments = { })
	public void checkAndCreateDatabase()
	{
		try
		{
			db.CreateDatabase();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Opens connection to database.
	 * @return LuaObjectStore of connection.
	 */
	@LuaFunction(manual = false, methodName = "open", arguments = { })
	 public LuaObjectStore open()
	 {
		db.Open(SQLiteDatabase.OPEN_READWRITE);
		return new LuaObjectStore();
	 }
	
	/**
	 * Send sql query to connection.
	 * @param conn object store of connection
	 * @param str sql statement string
	 * @return LuaObjectStore of statement.
	 */
	@LuaFunction(manual = false, methodName = "query", arguments = { LuaObjectStore.class, String.class })
	public LuaObjectStore query(LuaObjectStore conn, String str)
	{
		LuaObjectStore los = new LuaObjectStore();
		los.obj = db.Query(str, null, false);
		return los;
	}
	
	/**
	 * Send sql query to connection for insert,update operations.
	 * @param conn object store of connection
	 * @param str sql statement string
	 * @return LuaObjectStore of statement.
	 */
	@LuaFunction(manual = false, methodName = "insert", arguments = { LuaObjectStore.class, String.class })
	public LuaObjectStore insert(LuaObjectStore conn, String str)
	{
		LuaObjectStore los = new LuaObjectStore();
		los.obj = db.Query(str, null, true);
		return los;
	}
	
	/**
	 * Finalize statement.
	 * @param LuaObjectStore of statement.
	 */
	@LuaFunction(manual = false, methodName = "finalize", arguments = { LuaObjectStore.class })
	public void finalize(LuaObjectStore stmt)
	{
		Cursor c = (Cursor)stmt.obj;
		c.close();
	}
	
	/**
	 * Finalize statement.
	 * @param LuaObjectStore of connection.
	 */
	@LuaFunction(manual = false, methodName = "close", arguments = { LuaObjectStore.class })
	public void close(LuaObjectStore conn)
	{
		db.close();
	}
	
	/**
	 * Get Integer value at column
	 * @param stmt statement object
	 * @param column column
	 * @return int value
	 */
	@LuaFunction(manual = false, methodName = "getInt", arguments = { LuaObjectStore.class, Integer.class })
	public Integer getInt(LuaObjectStore stmt, Integer column)
	{
		return Integer.valueOf(db.GetInt((Cursor)stmt.obj, column.intValue()));
	}
	
	/**
	 * Get Float value at column
	 * @param stmt statement object
	 * @param column column
	 * @return float value
	 */
	@LuaFunction(manual = false, methodName = "getFloat", arguments = { LuaObjectStore.class, Integer.class })
	public Float getFloat(LuaObjectStore stmt, Integer column)
	{
		return Float.valueOf(db.GetInt((Cursor)stmt.obj, column.intValue()));
	}
	
	/**
	 * Get String value at column
	 * @param stmt statement object
	 * @param column column
	 * @return String value
	 */
	@LuaFunction(manual = false, methodName = "getString", arguments = { LuaObjectStore.class, Integer.class })
	public String getString(LuaObjectStore stmt, Integer column)
	{
		return String.valueOf(db.GetInt((Cursor)stmt.obj, column.intValue()));
	}
	
	/**
	 * Get Double value at column
	 * @param stmt statement object
	 * @param column column
	 * @return double value
	 */
	@LuaFunction(manual = false, methodName = "getDouble", arguments = { LuaObjectStore.class, Integer.class })
	public Double getDouble(LuaObjectStore stmt, Integer column)
	{
		return Double.valueOf(db.GetInt((Cursor)stmt.obj, column.intValue()));
	}
	
	/**
	 * Get Long value at column
	 * @param stmt statement object
	 * @param column column
	 * @return long value
	 */
	@LuaFunction(manual = false, methodName = "getLong", arguments = { LuaObjectStore.class, Integer.class })
	public Long getLong(LuaObjectStore stmt, Integer column)
	{
		return Long.valueOf(db.GetInt64((Cursor)stmt.obj, column.intValue()));
	}

	/**
	 * (Ignore)
	 */
	@Override
	public String GetId() 
	{
		return "LuaDatabase";
	}
}
