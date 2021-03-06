package master_lupus.apk;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.ArrayAdapter;

public class Paparazzo extends Activity {

	Context mContext;
	
	public Paparazzo(Context mContext) {
		this.mContext = mContext;
	}

	public CharSequence spied() {
		
		DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
    	try {
    		 
    		mdbhelper.openDataBase();

    	}catch(SQLException sqle){

    		throw sqle;

    	}
    	Cursor mCursor = mdbhelper.fetchAllReminders();
    	startManagingCursor(mCursor);
    	mCursor.moveToFirst();
    	int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
    	int action = mCursor.getColumnIndexOrThrow(mdbhelper.action);
    	while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(character);
    		if (a.equals("paparazzo")) break;
    		mCursor.moveToNext();
    	}
    	String b = mCursor.getString(action);
    	mdbhelper.close();
    	return b;
	}
	
	public ArrayAdapter<String> createSpinnerAdapter() {
		
		int i = 0;
		String spied = spied().toString();
		
		
		DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
    	try {
    		 
    		mdbhelper.openDataBase();

    	}catch(SQLException sqle){

    		throw sqle;

    	}
    	Cursor mCursor = mdbhelper.fetchAllReminders();
    	startManagingCursor(mCursor);
    	mCursor.moveToFirst();
    	int action = mCursor.getColumnIndexOrThrow(mdbhelper.action);
    	int name = mCursor.getColumnIndexOrThrow(mdbhelper.name);
    	int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
    	while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(action);
    		String b = mCursor.getString(character);
    		if ((a.equals(spied))&& (!b.equals("paparazzo"))) {
    			i = i+1;
    		}
    		mCursor.moveToNext();
    	}
		String[] visitors = new String[i];
		mCursor.moveToFirst();
		i = 0;
		while (!mCursor.isAfterLast()) {
			String a = mCursor.getString(action);
			String b = mCursor.getString(character);
			String c = mCursor.getString(name);
			if ((a.equals(spied))&& (!b.equals("paparazzo"))) {
				visitors[i] = c; 
				i++;
			}
			mCursor.moveToNext();
		}
		mdbhelper.close();
		String[] novisitors = new String[1];
		novisitors[0] = "Nobody";
		// Creiamo l'adapter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, visitors);
		ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, novisitors);
		// Lo ritorniamo
		if (i==0) return arrayAdapter2;
		return arrayAdapter;
	}
	
	public CharSequence exit() {
		boolean exit = false;
		String spied = spied().toString();
		String wentout = "went out";
		String rested = "stayed at home";
		
		
		DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
    	try {
    		 
    		mdbhelper.openDataBase();

    	}catch(SQLException sqle){

    		throw sqle;

    	}
    	Cursor mCursor = mdbhelper.fetchAllReminders();
    	startManagingCursor(mCursor);
    	mCursor.moveToFirst();
    	int action = mCursor.getColumnIndexOrThrow(mdbhelper.action);
    	int name = mCursor.getColumnIndexOrThrow(mdbhelper.name);
    	int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
		while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(name);
    		if (a.equals(spied)) break;
    		mCursor.moveToNext();
    	}
		String b = mCursor.getString(character);
		String c = mCursor.getString(action);
		mdbhelper.close();
		if (b.equals("veggente")) exit = true;
		if (b.equals("guardian")) exit = true;
		if ((b.equals("werewolf")) && (!c.equals("label"))) exit = true;
		if (exit) return wentout;
		if (!exit) return rested;
		return "error";
	}

}
