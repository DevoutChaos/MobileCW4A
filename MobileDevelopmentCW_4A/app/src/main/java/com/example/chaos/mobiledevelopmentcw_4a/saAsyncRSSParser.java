package com.example.chaos.mobiledevelopmentcw_4a;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.net.MalformedURLException;

/**
 * Created by rla on 22/10/2016.
 */

public class saAsyncRSSParser extends AsyncTask<String, Integer, saRSSDataItem>
{

    private Context appContext;
    private String urlRSSToParse;
    public saAsyncRSSParser(Context currentAppContext, String urlRSS)
    {
        appContext = currentAppContext;
        urlRSSToParse = urlRSS;
    }
    // A callback method executed on UI thread on starting the task
    @Override
    protected void onPreExecute() {
        // Message to indicate start of parsing
        Toast.makeText(appContext,"Parsing " + urlRSSToParse, Toast.LENGTH_SHORT).show();
    }
    @Override
    protected saRSSDataItem doInBackground(String... params)
    {
        saRSSDataItem parsedData;
        saRSSParser rssParser = new saRSSParser();
        try {
            rssParser.parseRSSData(urlRSSToParse);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        parsedData = rssParser.getRSSDataItem();
        return parsedData;
    }
    // A callback method executed on UI thread, invoked after the completion of the task
    // When doInbackground has completed, the return value from that method ispassed into this event
    // handler.
    @Override
    protected void onPostExecute(saRSSDataItem result) {
        // Message to indicate end of parsing
        Toast.makeText(appContext,"Parsing finished!", Toast.LENGTH_SHORT).show();
    }


}

