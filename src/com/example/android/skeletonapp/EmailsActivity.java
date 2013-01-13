package com.example.android.skeletonapp;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;

public class EmailsActivity extends Activity {
	
	private static final String PREFS_NAME = "emails";
	public static TextView emailList;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
     // Inflate our UI from its XML layout description.
        setContentView(R.layout.emails_activity);
        
        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String emails = settings.getString(PREFS_NAME, "");
        
        emailList = (TextView) findViewById(R.id.editText1);
        
        emailList.setText(emails);
	}
	
	 /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }
    
    
    @Override
    protected void onStop(){
       super.onStop();

      // We need an Editor object to make preference changes.
      // All objects are from android.context.Context
      SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
      SharedPreferences.Editor editor = settings.edit();
      editor.putString(PREFS_NAME, emailList.getText().toString());

      // Commit the edits!
      editor.commit();
    }
    

}
