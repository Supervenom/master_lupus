package master_lupus.apk;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.widget.ArrayAdapter;

public class Death extends Activity{

	Context mContext;
	
	public Death(Context mContext) {
		this.mContext = mContext;
	}
	
	private boolean nightOne() {
		
		DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
    	try {
    		 
    		mdbhelper.openDataBase();

    	}catch(SQLException sqle){

    		throw sqle;

    	}
    	Cursor mCursor = mdbhelper.fetchAllReminders();
    	startManagingCursor(mCursor);
    	int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
    	int action = mCursor.getColumnIndexOrThrow(mdbhelper.action);
    	mCursor.moveToFirst();
    	while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(character);
    		if (a.equals("guardian")) break;
    		mCursor.moveToNext();
    	}
    	String b = mCursor.getString(action);
    	mdbhelper.close();
    	if (b.equals("nightone")) return true;
    	else return false;
	}

	public ArrayAdapter<String> createSpinnerAdapter() {
		
		int i = 0;
		String ba = new String();
		
		if (nightOne()) {    
			i = i + 1;
		}
		if (!nightOne()) {  //criceto ancora da aggiungere
			DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
			try {
    		 
				mdbhelper.openDataBase();

			}catch(SQLException sqle){

				throw sqle;

			}
			Cursor mCursor = mdbhelper.fetchAllReminders();
			startManagingCursor(mCursor);
			int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
			int action = mCursor.getColumnIndexOrThrow(mdbhelper.action);
			mCursor.moveToFirst();
			while (!mCursor.isAfterLast()) {
				String a = mCursor.getString(character);
				String b = mCursor.getString(action);
				if ((a.equals("werewolf")) && (!b.equals("label")) && (!b.equals("dead"))) i = i + 1;//controllo guardia da aggiungere
				mCursor.moveToNext();
			}
			mdbhelper.close();
		}
		
		String[] deaths = new String[i];
		if (nightOne()) {    
			deaths[0] = "Master";
		}
		if (!nightOne()) {
			DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
			try {
    		 
				mdbhelper.openDataBase();

			}catch(SQLException sqle){

				throw sqle;

			}
			Cursor mCursor = mdbhelper.fetchAllReminders();
			startManagingCursor(mCursor);
			int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
			int action = mCursor.getColumnIndexOrThrow(mdbhelper.action);
			mCursor.moveToFirst();
			while (!mCursor.isAfterLast()) {
				String a = mCursor.getString(character);
				ba = mCursor.getString(action);
				if ((a.equals("werewolf")) && (!ba.equals("label")) && (!ba.equals("dead"))) deaths[0] = ba;
				//controllo guardia da aggiungere
				mCursor.moveToNext();                                            
			}
			mdbhelper.close();
			kill(ba);
		}
		// Creiamo l'adapter
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, deaths);
		// Lo ritorniamo
		return arrayAdapter;
	}
	
	public void kill(String killed) {
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
    		if (a.equals(killed)) {
    			break;
    		}
    		mCursor.moveToNext();
    	}
		long rowId = mCursor.getLong(id);
		mdbhelper.insertAction(rowId, "dead");
		mdbhelper.close();
	}

}
