package com.dressing.dressingproject.manager;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lee on 15. 11. 19.
 */
public class PropertyManager {
    private static PropertyManager instance;
    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    SharedPreferences mPrefs;
    SharedPreferences.Editor mEditor;

    private PropertyManager() {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(ApplicationLoader.getContext());
        mEditor = mPrefs.edit();
    }

    private static final String REG_ID = "regToken";

    public void setRegistrationToken(String regId) {
        mEditor.putString(REG_ID, regId);
        mEditor.commit();
    }

    public String getRegistrationToken() {
        return mPrefs.getString(REG_ID, "");
    }

}
