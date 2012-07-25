package master_lupus.apk;

import android.content.Context;
import android.widget.ArrayAdapter;

public class Lynch {
	
	Context mContext;
	
	public Lynch(Context mContext) {
		this.mContext = mContext;
	}
	
	public ArrayAdapter<String> createSpinnerAdapter() {
		
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
		while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(action);
    		if (!a.equals("dead")) {
    			i = i+1;
    		}
    		mCursor.moveToNext();
    	}
		String[] alives = new String[i];
		mCursor.moveToFirst();
		i = 0;
		while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(action);
    		String b = mCursor.getString(name);
    		if (!a.equals("dead")) {
    			alives[i] = b; 
    			i++;
    		}
    		mCursor.moveToNext();
    	}
		mdbhelper.close();
		// Creiamo l'adapter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, alives);
		// Lo ritorniamo
		return arrayAdapter;
	}
	
	public void kill(String lynched) {
		DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
    	try {
    		 
    		mdbhelper.openDataBase();

    	}catch(SQLException sqle){

    		throw sqle;

    	}
    	Cursor mCursor = mdbhelper.fetchAllReminders();
    	startManagingCursor(mCursor);
    	mCursor.moveToFirst();
	    int name = mCursor.getColumnIndexOrThrow(mdbhelper.name);
	    int id = mCursor.getColumnIndexOrThrow(mdbhelper.id);
		while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(name);
    		if (a.equals(lynched)) {
    			break;
    		}
    		mCursor.moveToNext();
    	}
		long rowId = mCursor.getLong(id);
		mdbhelper.insertAction(rowId, "dead");
		
	}

}
