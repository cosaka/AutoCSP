package com.example.autocsp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.view.Menu;
import android.view.MenuItem;
 
public class AutoCSP extends PreferenceActivity
		implements OnSharedPreferenceChangeListener {
	
	private static final boolean DEFAULT_ACTIVE = false; 
	private static boolean mActive;// = DEFAULT_ACTIVE;
	private static final String DEFAULT_CSP = ""; 
	private static String mCSP;// = DEFAULT_CSP;
	private static final boolean DEFAULT_LCALL_ACTIVE = false; 
	private static boolean mLocalCall;// = DEFAULT_LCALL_ACTIVE;
	private static final String DEFAULT_DDD = ""; 
	private static String mDDD;// = DEFAULT_DDD;
	private static final boolean DEFAULT_TOAST = false; 
	private static boolean mToast;// = DEFAULT_TOAST;
	private static Context mAppContext;
	private static Resources mAppResources;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
       	addPreferencesFromResource(R.xml.settings);
       	
        loadSettings();
        
        mAppContext = getApplicationContext();
        
        mAppResources = getResources();
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        
		//enableSettings();
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        
        loadSettings();
        
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
	            finish();
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
	
	public static boolean getToast() {
		return mToast;
	}
	
	public static Context getAppContext() {
		return mAppContext;
	}
	
	public static Resources getAppResources() {
		return mAppResources;
	}
	
	public void loadSettings() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		mActive = sharedPrefs.getBoolean("activate", DEFAULT_ACTIVE);
		mCSP = sharedPrefs.getString("csp", DEFAULT_CSP);
		mLocalCall = sharedPrefs.getBoolean("local_call", DEFAULT_LCALL_ACTIVE);
		mDDD = sharedPrefs.getString("ddd", DEFAULT_DDD);
		mToast = sharedPrefs.getBoolean("show_toast", DEFAULT_TOAST);
	}
	
	private void enableSettings() {
		SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
		
		boolean isActive = sharedPrefs.getBoolean("activate", false);
		boolean isLCEnabled = sharedPrefs.getBoolean("local_call", false);
		
		PreferenceScreen prefScreen = getPreferenceScreen();
		
		prefScreen.findPreference("csp").setEnabled(isActive);
		prefScreen.findPreference("local_call").setEnabled(isActive);
		prefScreen.findPreference("ddd").setEnabled(isActive && isLCEnabled);
		prefScreen.findPreference("show_toast").setEnabled(isActive);
	}
}
