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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NightPaparazzo extends Activity  {

	Context mContext;	
	Intent mIntent;
	
	/** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nightpaparazzo);
	        
	        mContext = this;
	        
	        Button next = (Button) findViewById(R.id.button8);
	        ArrayAdapter<String> adapter1 = createSpinnerAdapterSpied();
	        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	        final Spinner spied = (Spinner) findViewById(R.id.spinner102);
	        spied.setAdapter(adapter1);
	        
	        next.setOnClickListener(new View.OnClickListener() {
	            @SuppressWarnings("deprecation")
				public void onClick(View v) {
	            	
	            	String d = spied.getItemAtPosition(spied.getSelectedItemPosition()).toString();
	            	DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
	            	try {
	            		 
	            		mdbhelper.openDataBase();
	     
	            	}catch(SQLException sqle){
	     
	            		throw sqle;
	     
	            	}
	            	Cursor mCursor = mdbhelper.fetchAllReminders();
	            	startManagingCursor(mCursor);
	            	int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
	            	int id = mCursor.getColumnIndexOrThrow(mdbhelper.id);
	            	mCursor.moveToFirst();
	            	while (!mCursor.isAfterLast()) {
	            		String a = mCursor.getString(character);
	            		if (a.equals("paparazzo"))  break;
	            		mCursor.moveToNext();
	            	}
	            	long rowId = mCursor.getLong(id);
	            	mdbhelper.insertAction(rowId, d);
	            	mCursor.moveToNext();
	            	String a = mCursor.getString(character);
	            	mdbhelper.close();
	            	//if (a.equals("guardian")) mIntent = new Intent(mContext, NightGuardian.class);
	            	//if (a.equals("veggente")) mIntent = new Intent(mContext, NightVeggente.class);
	            	startActivity(mIntent);
	            	finish();
	            }
	        });
		}
		
		private ArrayAdapter<String> createSpinnerAdapterSpied() {
			
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
        		if ((!b.equals("paparazzo")) && (!c.equals("dead"))) i = i + 1;
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
        		if ((!b.equals("paparazzo")) && (!c.equals("dead"))) {
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
