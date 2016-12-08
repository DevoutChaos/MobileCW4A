package com.example.chaos.mobiledevelopmentcw_4a;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by chaos on 03/12/2016.
 */

public class initSaveData {
    SharedPreferences initSharedPrefs;
    private String initRSSLoc;

    private void setinitRSSLoc(String RSSLoc)
    {
        this.initRSSLoc = RSSLoc;
    }
    public String getinitRSSLoc()
    {
        return initRSSLoc;
    }

    public initSaveData(SharedPreferences initSDPrefs)
    {
        setinitRSSLoc("https://www.reddit.com/r/DnD.rss");

        try
        {
            this.initSharedPrefs = initSDPrefs;
        }
        catch (Exception e)
        {
            Log.e("n", "Pref Manager is NULL");
        }
        setDefaultPrefs();
    }

    public void savePreferences(String key, String value) {
        SharedPreferences.Editor editor = initSharedPrefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void setDefaultPrefs()
    {
        savePreferences("init_RSSFeed", "https://www.reddit.com/r/DnD.rss");
    }

}
