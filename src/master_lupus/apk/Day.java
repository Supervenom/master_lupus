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
				//mIntent = new Intent(mContext, NightWolves.class);//poi ci sara' il controllo per la puttana
	            startActivity(mIntent);
	            finish();
            }
        });
		
	}
}

//Alla fine del giorno rimettere "label" a tutta la colonna azioni, meglio dopo la lettura. NON se 
//e' "dead"

//Chiamare il lupo che uccide werewolf1 e l'altro werewolf ogni volta alla fine della activity lupo