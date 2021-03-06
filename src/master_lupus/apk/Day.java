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
import android.widget.TextView;

public class Day extends Activity  {
	
	Intent mIntent;
	Lynch mLynch;
	Context mContext;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.day);
        
        mContext = this;
        
        Paparazzo mPaparazzo = new Paparazzo(mContext);
        Death mDeath = new Death(mContext);
        mLynch = new Lynch(mContext);
        
        ArrayAdapter<String> adapter1 = mDeath.createSpinnerAdapter();
        ArrayAdapter<String> adapter2 = mPaparazzo.createSpinnerAdapter();
        ArrayAdapter<String> adapter3 = mLynch.createSpinnerAdapter();
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner deads = (Spinner) findViewById(R.id.spinner2);
        final Spinner visitors = (Spinner) findViewById(R.id.spinner3);
        final Spinner choose = (Spinner) findViewById(R.id.spinner4);
        deads.setAdapter(adapter1);
        visitors.setAdapter(adapter2);
        choose.setAdapter(adapter3);
        
        final TextView spied = (TextView) findViewById(R.id.textView38);
		final TextView exit = (TextView) findViewById(R.id.textView34);
        spied.setText(mPaparazzo.spied());
		exit.setText(mPaparazzo.exit());
		Button night = (Button) findViewById(R.id.button21);
		night.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String lynched = choose.getItemAtPosition(choose.getSelectedItemPosition()).toString();
				mLynch.kill(lynched);
				newturn();
				mIntent = new Intent(mContext, NightWolves.class);//poi ci sara' il controllo per la puttana
	            startActivity(mIntent);
	            finish();
            }
        });
		
	}
	
	private void newturn() {//inserire controllo vittoria/sconfitta
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
    	int id = mCursor.getColumnIndexOrThrow(mdbhelper.id);
    	while (!mCursor.isAfterLast()) {
    		String a = mCursor.getString(action);
    		if (!a.equals("dead")) {
    			long rowId = mCursor.getLong(id);
    			mdbhelper.insertAction(rowId, "label");
    		}
    		mCursor.moveToNext();
    	}
    	mdbhelper.close();
	}
}



//Da fare: trovare un modo per non far fare le azioni ai morti. Tipo veggente, guardia, etc
