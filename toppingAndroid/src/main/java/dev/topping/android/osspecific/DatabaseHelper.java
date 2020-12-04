/*
 Copyright 2011 by Erdoğan Kalemci.
 All rights reserved. No part of this document may be reproduced or transmitted in any form or by any means, 
 electronic, mechanical, photocopying, recording, or otherwise, without prior written permission of Erdoğan Kalemci.
 
 All material in this course is, unless otherwise stated, the property of Erdoğan Kalemci.
 Copyright and other intellectual property laws protect these materials. Reproduction or retransmission
 of the materials, in whole or in part, in any manner, without the prior written consent of the copyright holder,
 is a violation of copyright law.
 
 Contact:www.sombrenuit.org, www.alangoya.com
 */

package dev.topping.android.osspecific;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import dev.topping.android.LuaDatabase;

public class DatabaseHelper extends SQLiteOpenHelper{
	 
    //The Android's default system path of your application database.
    public static String DB_PATH = "/databases/";
 
    public static String DB_NAME = "sqlite.mp3";
 
    private SQLiteDatabase myDataBase; 
 
    private final Context myContext;
 
    /**
     * Constructor
     * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
     * @param context
     */
    public DatabaseHelper(Context context) 
    {
    	super(context, DB_NAME, null, 1);
    	DB_PATH = context.getFilesDir().getPath() + DB_PATH;
        this.myContext = context;
    }	
 
  /**
     * Creates a empty database on the system and rewrites it with your own database.
     * */
    public synchronized void CreateDatabase() throws IOException
    { 
		boolean dbExist = checkDataBase();
 
    	if(dbExist)
    	{
    		//do nothing - database already exist
    	}
    	else
    	{
     		//By calling this method and empty database will be created into the default system path
               //of your application so we are gonna be able to overwrite that database with our database.
    		try
    		{
    			this.getReadableDatabase();
    		}
    		catch(Exception ex)
    		{
    			
    		}
 
        	try 
        	{
     			copyDataBase();
     		} 
        	catch (IOException e) 
    		{
         		throw new Error("Error copying database");
         	}
    	}
    }
    
    public synchronized void ForceCreateDatabase() throws IOException
    {
    	//By calling this method and empty database will be created into the default system path
        //of your application so we are gonna be able to overwrite that database with our database.
    	this.getReadableDatabase();
 	
    	try 
    	{
    		deleteDatabase();
 			copyDataBase();
 		} 
    	catch (IOException e) 
		{
     		throw new Error("Error copying database");
     	}
    }
 
    /**
     * Check if the database already exist to avoid re-copying the file each time you open the application.
     * @return true if it exists, false if it doesn't
     */
    private boolean checkDataBase()
    {
     	SQLiteDatabase checkDB = null;
 
    	try
    	{
    		String myPath = DB_PATH + DB_NAME;
    		checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     	}
    	catch(SQLiteException e)
    	{
    		//database does't exist yet.
     	}
 
    	if(checkDB != null)
    	{
     		checkDB.close();
     	}
 
    	return checkDB != null ? true : false;
    }
    
    private void deleteDatabase() throws IOException
    {
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
    	File f = new File(outFileName);
    	if(f.exists())
    		f.delete();
    	try
		{
			Thread.sleep(1000);
		}
		catch(InterruptedException e)
		{
			e.printStackTrace();
		}
    }
 
    /**
     * Copies your database from your local assets-folder to the just created empty database in the
     * system folder, from where it can be accessed and handled.
     * This is done by transfering bytestream.
     * */
    private synchronized void copyDataBase() throws IOException
    {
     	//Open your local db as the input stream
    	InputStream myInput = myContext.getAssets().open(DB_NAME);
 
    	// Path to the just created empty db
    	String outFileName = DB_PATH + DB_NAME;
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
 
    	//transfer bytes from the inputfile to the outputfile
    	byte[] buffer = new byte[1024];
    	int length;
    	while ((length = myInput.read(buffer)) > 0)
    	{
    		myOutput.write(buffer, 0, length);
    	}
 
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput.close();
    }
 
    public synchronized void Open(int flags) throws SQLException
    {
     	//Open the database
        String myPath = DB_PATH + DB_NAME;
        if(LuaDatabase.openedDatabase != null)
        {
        	if(LuaDatabase.openedDatabase.isDbLockedByCurrentThread() || LuaDatabase.openedDatabase.isDbLockedByCurrentThread())
        		LuaDatabase.openedDatabase.close();
        }
        try
        {
	        myDataBase = SQLiteDatabase.openDatabase(myPath, null, flags);
	    	LuaDatabase.openedDatabase = myDataBase;
        }
        catch(Exception ex)
        {
        	
        }
    }
 
    @Override
	public synchronized void close() 
    { 
    	if(myDataBase != null)
    		myDataBase.close();
    	
    	if(LuaDatabase.openedDatabase != null)
    	{
    		LuaDatabase.openedDatabase.close();
    		LuaDatabase.openedDatabase = null;
    	}    	
 
    	super.close();
	}
 
	@Override
	public void onCreate(SQLiteDatabase db) 
	{
 
	}
 
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) 
	{
 
	}
 
    public Cursor Query(String query, String[] selectionArgs, boolean insert)
    {
    	if(insert)
    	{
    		myDataBase.execSQL(query);
    		return new DummyCursor();
    	}
    	else
    		return myDataBase.rawQuery(query, selectionArgs);
    }
    
    public boolean Read(Cursor cursor)
    {
    	return cursor.moveToNext();
    }
    
    public int GetInt(Cursor cursor, int column)
    {
    	return cursor.getInt(column);
    }
    
    public long GetInt64(Cursor cursor, int column)
    {
    	return cursor.getLong(column);
    }
    
    public String GetString(Cursor cursor, int column)
    {
    	return cursor.getString(column);
    }
    
    public byte[] GetBlob(Cursor cursor, int column)
    {
    	return cursor.getBlob(column);
    }

	public double GetDouble(Cursor cursor, int column)
	{
		return cursor.getDouble(column);
	}
}
