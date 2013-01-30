package com.example.autocsp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
 
public class SettingsActivity extends PreferenceActivity
		implements OnSharedPreferenceChangeListener {
	
	private static final boolean DEFAULT_ACTIVE = false; 
	private static boolean mActive = DEFAULT_ACTIVE;
	private static final String DEFAULT_CSP = ""; 
	private static String mCSP = DEFAULT_CSP;
	private static final boolean DEFAULT_LCALL_ACTIVE = false; 
	private static boolean mLocalCall = DEFAULT_LCALL_ACTIVE;
	private static final String DEFAULT_DDD = ""; 
	private static String mDDD = DEFAULT_DDD;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       	addPreferencesFromResource(R.xml.settings);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        
		enableSettings();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
		enableSettings();
		
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        
		loadSettings();
		
	    getPreferenceScreen().getSharedPreferences()
        		.unregisterOnSharedPreferenceChangeListener(this);
	}
    
    @Override
    public void onSharedPreferenceChanged(final SharedPreferences sharedPreferences, final String key) {
    	enableSettings();
    }
    
    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_settings, menu);
		return true;
	}
    
	@Override
	public boolean onOptionsItemSelected(MenuItem item) 
	{
	    switch (item.getItemId()) 
	    {
	        case R.id.menu_exit:
	            finish(); // TODO: alterar para sair do aplicativo, e não só do Activity
	            return true;
	    }
	    
	    return false;
	}
	
	public static boolean getActive() {
		return mActive;
	}
	
	public static String getCSP() {
		return mCSP;
	}
	
	public static String getDDD() {
		return mDDD;
	}
	
	public static boolean getLocalCall() {
		return mLocalCall;
	}
	
	private void loadSettings() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		mActive = sharedPrefs.getBoolean("activate", DEFAULT_ACTIVE);
		mCSP = sharedPrefs.getString("csp", DEFAULT_CSP);
		mLocalCall = sharedPrefs.getBoolean("local_call", DEFAULT_LCALL_ACTIVE);
		mDDD = sharedPrefs.getString("ddd", DEFAULT_DDD);
	}
	
	private void enableSettings() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean isActive = sharedPrefs.getBoolean("activate", false);
		boolean isLCEnabled = sharedPrefs.getBoolean("local_call", false);
		
		getPreferenceScreen().findPreference("csp").setEnabled(isActive);
		getPreferenceScreen().findPreference("local_call").setEnabled(isActive);
		getPreferenceScreen().findPreference("ddd").setEnabled(isActive && isLCEnabled);
	}
}
