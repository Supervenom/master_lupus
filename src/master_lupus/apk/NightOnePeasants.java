package master_lupus.apk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NightOnePeasants extends Activity  {

	Context mContext;	
	Intent mIntent;
	
	/** Called when the activity is first created. */
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.nightonepeasants);
	        Button next = (Button) findViewById(R.id.button6);
	        final EditText peasant = (EditText) findViewById(R.id.editText3);
	        
	        mContext = this;
	        
	        next.setOnClickListener(new View.OnClickListener() {
	            @SuppressWarnings("deprecation")
				public void onClick(View v) {
	            	String c = peasant.getText().toString();
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
	            		String b = mCursor.getString(name);
	            		if ((a.equals("peasant")) && (b.equals("label"))) break;
	            		mCursor.moveToNext();
	            	}
	            	long rowId = mCursor.getLong(id);
	            	mdbhelper.insertName(rowId, c);
	            	mCursor.moveToNext();
	            	String a = mCursor.getString(character);
	            	mdbhelper.close();
	            	if (a.equals("peasant")) mIntent = new Intent(mContext, NightOnePeasants.class);
	            	if (a.equals("werewolf")) mIntent = new Intent(mContext, NightOneWolves.class);
	            	startActivity(mIntent);
	            	finish();
	            }
	        });
	        
		}
		

}
