/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtai  a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.android.skeletonapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * This class provides a basic demonstration of how to write an Android
 * activity. Inside of its window, it places a single view: an EditText that
 * displays and edits some internal text.
 */
public class SkeletonActivity extends Activity {

    private TextView pinLabel;
    private DatePicker datePicker;
    
    public SkeletonActivity() {
    }

    /** Called with the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inflate our UI from its XML layout description.
        setContentView(R.layout.skeleton_activity);

        // Find the text editor view inside the layout, because we
        // want to do various programmatic things with it.
        pinLabel = (TextView) findViewById(R.id.textView1);
        datePicker = (DatePicker) findViewById(R.id.datePicker1);
        
        // Hook up button presses to the appropriate event handler.
        ((Button) findViewById(R.id.button1)).setOnClickListener(clickListener);
        
    }

    /**
     * Called when the activity is about to start interacting with the user.
     */
    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * A call-back for when the user presses the back button.
     */
    OnClickListener clickListener = new OnClickListener() {
        public void onClick(View v) {
        	
        	Integer day = 0;
        	Integer month = 0;
        	Integer year = 0;
        	Integer mid = 0;
        	
        	day = datePicker.getDayOfMonth();
        	month = datePicker.getMonth();
        	year = datePicker.getYear();
        	
        	//hash everything!
        	mid = day.hashCode() * (month.hashCode()+1) * year.hashCode();
        	
        	Integer pin = mid.hashCode() + 1;
        	
        	//add some salt
        	pin = pin * 523411;
        	
        	String pinStr = pin.toString();
        	
        	//can safely get this substring from the salt
        	pinLabel.setText(pinStr.substring(pinStr.length()-6, pinStr.length()-1));
        }
    };
    
    
    
    //Menu Stuff
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.guest_pin_menu, menu);
        return true;
    }
    
    //Detect Menu Selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sendEmail:         	
            	sendMail();
                return true;
            case R.id.addContacts:
            	editContacts();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    public void sendMail(){
    	String[] recipients;
    	String date;
    	date = datePicker.getMonth()+1+"/"+datePicker.getDayOfMonth()+"/"+datePicker.getYear();
    	
    	if(EmailsActivity.emailList != null && EmailsActivity.emailList.length()>0)
    	{
    		recipients = EmailsActivity.emailList.getText().toString().split("\n");
    	
    		Intent intent = new Intent(Intent.ACTION_SEND);
    		intent.setType("text/plain");
    		intent.putExtra(Intent.EXTRA_EMAIL, recipients);
    		intent.putExtra(Intent.EXTRA_SUBJECT,"Guest Pin Changed");
    		intent.putExtra(Intent.EXTRA_TEXT, "The new guest door pin as of "+date+" is: "+pinLabel.getText());
    		startActivity(intent); 
    	}else{
    		Toast toast = Toast.makeText(getApplicationContext(), "Please Enter Recipients", Toast.LENGTH_SHORT);
    		toast.show();
    	}
    }
    
    public void editContacts(){
    	Intent intent = new Intent(SkeletonActivity.this, EmailsActivity.class);
    	startActivity(intent);   	
    }

}
