package master_lupus.apk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class NightWolves extends Activity  {

	Context mContext;	
	Intent mIntent;
	
	/** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nightwolves);
	        
	        Button next = (Button) findViewById(R.id.button102);
	        
	        mContext = this;
	        
	        ArrayAdapter<String> adapter1 = createSpinnerAdapterMorituri();
	        ArrayAdapter<String> adapter2 = createSpinnerAdapterWolves();
	        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        final Spinner moriturus = (Spinner) findViewById(R.id.spinner100);
	        final Spinner killer = (Spinner) findViewById(R.id.spinner101);
	        moriturus.setAdapter(adapter1);
	        killer.setAdapter(adapter2);
	        
	        
	        next.setOnClickListener(new View.OnClickListener() {
	            @SuppressWarnings("deprecation")
				public void onClick(View v) {
	            	String c = killer.getItemAtPosition(killer.getSelectedItemPosition()).toString();
	            	String d = moriturus.getItemAtPosition(moriturus.getSelectedItemPosition()).toString();
	            	DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
	            	try {
	            		 
	            		mdbhelper.openDataBase();
	     
	            	}catch(SQLException sqle){
	     
	            		throw sqle;
	     
	            	}
	            	Cursor mCursor = mdbhelper.fetchAllReminders();
	            	startManagingCursor(mCursor);
	            	int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
	            	int name = mCursor.getColumnIndexOrThrow(mdbhelper.name);
	            	int id = mCursor.getColumnIndexOrThrow(mdbhelper.id);
	            	mCursor.moveToFirst();
	            	while (!mCursor.isAfterLast()) {
	            		String b = mCursor.getString(name);
	            		if (b.equals(c))  break;
	            		mCursor.moveToNext();
	            	}
	            	long rowId = mCursor.getLong(id);
	            	mdbhelper.insertAction(rowId, d);
	            	mdbhelper.close();
	            	mIntent = new Intent(mContext, NightPaparazzo.class);
	            	startActivity(mIntent);
	            	finish();
	            }
	        });
		}
		
		private ArrayAdapter<String> createSpinnerAdapterMorituri() {
			
			int i = 0;
			
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
        	int id = mCursor.getColumnIndexOrThrow(mdbhelper.id);
        	while (!mCursor.isAfterLast()) {
        		String b = mCursor.getString(character);
        		String c = mCursor.getString(action);
        		if ((!b.equals("werewolf")) && (!c.equals("dead"))) i = i + 1;
        		mCursor.moveToNext();
        	}
        	mdbhelper.close();
        	
			String[] data = new String[i];
			i = 0;
			
			// Inizializziamo i dati
        	try {
        		 
        		mdbhelper.openDataBase();
 
        	}catch(SQLException sqle){
 
        		throw sqle;
 
        	}
        	mCursor = mdbhelper.fetchAllReminders();
        	startManagingCursor(mCursor);
        	int name = mCursor.getColumnIndexOrThrow(mdbhelper.name);
        	mCursor.moveToFirst();
        	while (!mCursor.isAfterLast()) {
        		String b = mCursor.getString(character);
        		String c = mCursor.getString(action);
        		if ((!b.equals("werewolf")) && (!c.equals("dead"))) {
        			data[i] = mCursor.getString(name);
        			i++;
        		}
        		mCursor.moveToNext();
        	}
        	mdbhelper.close();
			// Creiamo l'adapter
			ArrayAdapter<String> arrayAdapter2 = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, data);
			// Lo ritorniamo
			return arrayAdapter2;
		}	
		
		private ArrayAdapter<String> createSpinnerAdapterWolves() {
			
			int i = 0;
			
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
        	int id = mCursor.getColumnIndexOrThrow(mdbhelper.id);
        	int action = mCursor.getColumnIndexOrThrow(mdbhelper.action);
        	while (!mCursor.isAfterLast()) {
        		String b = mCursor.getString(character);
        		String c = mCursor.getString(action);
        		if ((b.equals("werewolf")) && (!c.equals("dead"))) i = i + 1;
        		mCursor.moveToNext();
        	}
        	mdbhelper.close();
			
			String[] data = new String[i];
			i = 0;
			
			// Inizializziamo i dati
        	try {
        		 
        		mdbhelper.openDataBase();
 
        	}catch(SQLException sqle){
 
        		throw sqle;
 
        	}
        	mCursor = mdbhelper.fetchAllReminders();
        	startManagingCursor(mCursor);
        	int name = mCursor.getColumnIndexOrThrow(mdbhelper.name);
        	mCursor.moveToFirst();
        	while (!mCursor.isAfterLast()) {
        		String b = mCursor.getString(character);
        		String c = mCursor.getString(action);
        		if ((b.equals("werewolf")) && (!c.equals("dead"))) {
        			data[i] = mCursor.getString(name);
        			i++;
        		}
        		mCursor.moveToNext();
        	}
        	mdbhelper.close();
			// Creiamo l'adapter
			ArrayAdapter<String> arrayAdapter1 = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, data);
			// Lo ritorniamo
			return arrayAdapter1;
		}	
		
}