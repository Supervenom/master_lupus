package master_lupus.apk;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class Lupus_masterActivity extends Activity {
    
	String c;
	int a;
	int b;
	Intent mIntent;
	Initialize_game mGame;
	
	/** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button plus_1 = (Button) findViewById(R.id.button2);
        Button plus_2 = (Button) findViewById(R.id.button4);
        Button minus_1 = (Button) findViewById(R.id.button1);
        Button minus_2 = (Button) findViewById(R.id.button3);
        Button play = (Button) findViewById(R.id.button5);
        final EditText number_1 = (EditText) findViewById(R.id.editText1);
        final EditText number_2 = (EditText) findViewById(R.id.editText2);
        @SuppressWarnings("unused")
		CheckBox master = (CheckBox) findViewById(R.id.checkBox4);
        @SuppressWarnings("unused")
		CheckBox veggente = (CheckBox) findViewById(R.id.checkBox1);
        final CheckBox paparazzo = (CheckBox) findViewById(R.id.checkBox2);
        final CheckBox guardian = (CheckBox) findViewById(R.id.checkBox3);
        final Context mContext = this;
        
        plus_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	c = number_1.getText().toString();
            	a = Integer.parseInt(c);
            	a = a + 1;
            	c = Integer.toString(a);
            	number_1.setText(c);
            }
        });
        plus_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	c = number_2.getText().toString();
            	a = Integer.parseInt(c);
            	a = a + 1;
            	c = Integer.toString(a);
            	number_2.setText(c);
            }
        });
        minus_1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	c = number_1.getText().toString();
            	a = Integer.parseInt(c);
            	a = a - 1;
            	c = Integer.toString(a);
            	number_1.setText(c);
            }
        });
        minus_2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	c = number_2.getText().toString();
            	a = Integer.parseInt(c);
            	a = a - 1;
            	c = Integer.toString(a);
            	number_2.setText(c);
            }
        });
        
        play.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	c = number_1.getText().toString();
            	a = Integer.parseInt(c);
            	c = number_2.getText().toString();
            	b = Integer.parseInt(c);
            	mGame = new Initialize_game(a, b, true, paparazzo.isChecked(), guardian.isChecked(), mContext);
            	mGame.setupDB();
            	if (mGame.n_peasants >0) mIntent = new Intent(mContext, NightOnePeasants.class);
            	else mIntent = new Intent(mContext, NightOneWolves.class);
        		startActivity(mIntent);
            	finish();
            }
        });
    }
}