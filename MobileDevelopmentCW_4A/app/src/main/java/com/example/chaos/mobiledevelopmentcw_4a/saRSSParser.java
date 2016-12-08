package com.example.chaos.mobiledevelopmentcw_4a;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rla on 22/10/2016.
 */

public class saRSSParser {
    private saRSSDataItem RSSDataItem;
    private String title, description;

    private boolean firstDesc = false;
    private boolean firstTitle = false;
    private boolean secondTitle = false;

    public static List<String> titleLst = new ArrayList<>();
    public static List<String> descLst = new ArrayList<>();

    public saRSSParser()
    {
        this.RSSDataItem = new saRSSDataItem();
        setRSSDataItem(null);
    }

    public static String getStringFromInputStream(InputStream stream, String
            charsetName) throws IOException
    {
        int n = 0;
        char[] buffer = new char[1024 * 4];
        InputStreamReader reader = new InputStreamReader(stream, charsetName);
        StringWriter writer = new StringWriter();
        while (-1 != (n = reader.read(buffer))) writer.write(buffer, 0, n);
        return writer.toString();
    }

    public saRSSDataItem getRSSDataItem()
    {
        return this.RSSDataItem;
    }

    public void setRSSDataItem(String sItemData)
    {
        RSSDataItem.setItemTitle(sItemData);
        RSSDataItem.setItemDesc(sItemData);
        RSSDataItem.setItemLink(sItemData);
    }

    public void parseRSSDataItem(XmlPullParser theParser, int theEventType)
    {
        titleLst.clear();
        descLst.clear();
        try
        {
            while (theEventType != XmlPullParser.END_DOCUMENT)
            {
                // Found a start tag
                if(theEventType == XmlPullParser.START_TAG)
                {
                    // Check which Tag has been found
                    if (theParser.getName().equalsIgnoreCase("title"))
                    {
                        if (firstTitle && secondTitle) {

                            // Now just get the associated text
                            String temp = theParser.nextText();
                            // store data in class
                            //RSSDataItem.setItemTitle(temp);
                            Log.d("titles: ", temp);
                            title = temp;
                            titleLst.add(title);
                        }
                        else if (firstTitle){
                            secondTitle = true;
                        }
                        else
                        {
                            firstTitle = true;
                        }
                    }
                    else
                        // Check which Tag we have
                        if (theParser.getName().equalsIgnoreCase("description"))
                        {
                            if (firstDesc) {
                                // Now just get the associated text
                                String temp = theParser.nextText();
                                // store data in class
                                //RSSDataItem.setItemDesc(temp);
                                Log.d("descs: ", temp);
                                description = temp;
                                description = description.replaceAll("&#39;", "'");
                                description = description.replaceAll("&#34;", "\"");
                                description = description.replaceAll("&#8212;", "\n");
                                description = description.replaceAll("&#8230;", "...");
                                descLst.add(description);
                            }
                            else
                            {
                                firstDesc = true;
                            }
                        }
                        else
                            // Check which Tag we have
                            if (theParser.getName().equalsIgnoreCase("link"))
                            {
                                // Now just get the associated text
                                String temp = theParser.nextText();
                                // store data in class
                                RSSDataItem.setItemLink(temp);
                            }
                }
                // Get the next event
                theEventType = theParser.next();
            } // End of while
        }
        catch (XmlPullParserException parserExp1)
        {
            Log.e("MyTag","Parsing error" + parserExp1.toString());
        }
        catch (IOException parserExp1)
        {
            Log.e("MyTag","IO error during parsing");
        }
    }

    public void parseRSSData(String RSSItemsToParse) throws MalformedURLException {
        URL rssURL = new URL(RSSItemsToParse);
        InputStream rssInputStream;
        try
        {
            XmlPullParserFactory parseRSSfactory = XmlPullParserFactory.newInstance();
            parseRSSfactory.setNamespaceAware(true);
            XmlPullParser RSSxmlPP = parseRSSfactory.newPullParser();
            String xmlRSS = getStringFromInputStream(getInputStream(rssURL), "UTF-8");
            RSSxmlPP.setInput(new StringReader(xmlRSS));
            int eventType = RSSxmlPP.getEventType();
            parseRSSDataItem(RSSxmlPP,eventType);
        }
        catch (XmlPullParserException ae1)
        {
            Log.e("MyTag","Parsing error" + ae1.toString());
        }
        catch (IOException ae1)
        {
            Log.e("MyTag","IO error during parsing");
        }
        Log.e("MyTag","End document");
    }

    public InputStream getInputStream(URL url) throws IOException
    {
        return url.openConnection().getInputStream();
    }

}