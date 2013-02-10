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
import android.widget.Toast;

public class NightVeggente extends Activity  {

	Context mContext;	
	Intent mIntent;
	
	/** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nightveggente);
	        
	        Button next = (Button) findViewById(R.id.button20);
	        
	        mContext = this;
	        
	        ArrayAdapter<String> adapter = createSpinnerAdapter();
	        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        final Spinner choose = (Spinner) findViewById(R.id.spinner1);
	        choose.setAdapter(adapter);
	        
	        next.setOnClickListener(new View.OnClickListener() {
	            @SuppressWarnings("deprecation")
				public void onClick(View v) {
	            	String d = choose.getItemAtPosition(choose.getSelectedItemPosition()).toString();
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
	            		String a = mCursor.getString(character);
	            		if (a.equals("veggente")) break;
	            		mCursor.moveToNext();
	            	}
	            	long rowId = mCursor.getLong(id);
	            	mdbhelper.insertAction(rowId, d);
	            	mCursor.moveToFirst();
	            	while (!mCursor.isAfterLast()) {
	            		String bbb = mCursor.getString(name);
	            		if (bbb.equals(d)) break;
	            		mCursor.moveToNext();
	            	}
	            	String aaa = mCursor.getString(character);
	            	mdbhelper.close();
	            	if ((aaa.equals("peasant")) || (aaa.equals("guardian")) || (aaa.equals("paparazzo"))) {
	            		Toast.makeText(getBaseContext(), "è buono (pollice in su)", 
	            				Toast.LENGTH_LONG).show();
	            	}
	            	if ((aaa.equals("werewolf"))) {
	            		Toast.makeText(getBaseContext(), "è cattivo (pollice in giù)", 
	            				Toast.LENGTH_LONG).show();
	            	}
	            	mIntent = new Intent(mContext, Day.class);
	            	startActivity(mIntent);
	            	finish();
	            }
	        });
		}
		
private ArrayAdapter<String> createSpinnerAdapter() {
			
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
        	while (!mCursor.isAfterLast()) {
        		String b = mCursor.getString(character);
        		String c = mCursor.getString(action);
        		if ((!b.equals("veggente")) && (!c.equals("dead"))) i = i + 1;
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
        		if ((!b.equals("veggente")) && (!c.equals("dead"))) {
        			data[i] = mCursor.getString(name);
        			i++;
        		}
        		mCursor.moveToNext();
        	}
        	mdbhelper.close();
			
        	// Creiamo l'adapter
			ArrayAdapter<String> arrayAdapter3 = new ArrayAdapter<String>(mContext,android.R.layout.simple_spinner_item, data);
			// Lo ritorniamo
			return arrayAdapter3;
		}
		
}