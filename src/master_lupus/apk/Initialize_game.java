package master_lupus.apk;

import java.io.IOException;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.app.Activity;

public class Initialize_game extends Activity{

	int n_peasants;
	int n_werewolves;
	int i;
	boolean ifmaster;
	boolean ifveggente;
	boolean ifpaparazzo;
	boolean ifguardian;
	
	Context mContext;
	Intent mIntent;
	//Paparazzo mPaparazzo;
	//Veggente mVeggente;
	//Guardian mGuardian;
	//Peasant mPeasant[] = new Peasant[20];
	//Werewolf mWerewolf[] = new Werewolf[5];
	
	public Initialize_game(int peasants, int werewolves, boolean veggente,
			boolean paparazzo, boolean guardian, Context context) {
		ifveggente = veggente;
		ifpaparazzo = paparazzo;
		ifguardian = guardian;
		n_peasants = peasants;
		n_werewolves = werewolves;
		mContext = context;
		//if(n_peasants != 0) for (i=0; i<n_peasants; i++) mPeasant[i] = new Peasant();
		//for (i=0; i<n_werewolves; i++) mWerewolf[i] = new Werewolf();
		//if(ifveggente == true) mVeggente = new Veggente();
		//if(ifpaparazzo == true) mPaparazzo = new Paparazzo();
		//if(ifguardian == true) mGuardian = new Guardian();
	}
	
	@SuppressWarnings("deprecation")
	public void setupDB() {
		
		DataBaseHelper mdbhelper = new DataBaseHelper (mContext);
        try {
        	 
        	mdbhelper.createDataBase();
 
        	} catch (IOException ioe) {
 
        		throw new Error("Unable to create database");
 
        }
 
        	try {
 
        		mdbhelper.openDataBase();
 
        	}catch(SQLException sqle){
 
        		throw sqle;
 
        	}
        Cursor mCursor = mdbhelper.fetchAllReminders();
        startManagingCursor(mCursor);
        //int character = mCursor.getColumnIndexOrThrow(mdbhelper.character);
        mCursor.moveToFirst();
        String[] characters = new String[n_characters()];
        fill_character(characters);
        for (i=0; i<n_characters(); i++) {
        	@SuppressWarnings("unused")
			long line = mdbhelper.createCharacter(characters[i]);
        	mCursor.moveToLast();
        }
        mdbhelper.close();
	}
	
	int n_characters() {
		int n = 0;
		n = n + n_peasants + n_werewolves;
		if(ifveggente == true) n = n + 1;
		if(ifpaparazzo == true) n = n + 1;
		if(ifguardian == true) n = n + 1;
		
		return n;
	}
	
	void fill_character(String[] string) {
		int a;
		for (a=0; a<n_peasants; a++) string[a] = "peasant";
		for (a=0; a<n_werewolves; a++) string[a+n_peasants] = "werewolf";
		a = n_peasants+n_werewolves;
		if(ifpaparazzo == true) {string[a] = "paparazzo"; a = a +1; }
		if(ifguardian == true) {string[a] = "guardian"; a = a +1; }
		if(ifveggente == true) {string[a] = "veggente"; a = a +1; }
	}
}
