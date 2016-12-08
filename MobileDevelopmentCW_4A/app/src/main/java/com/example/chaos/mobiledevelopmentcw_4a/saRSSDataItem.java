package com.example.chaos.mobiledevelopmentcw_4a;

import java.io.Serializable;

/**
 * Created by rla on 22/10/2016.
 */

public class saRSSDataItem implements Serializable {

    // *********************************************
// Declare variables etc.
// *********************************************
    private String itemTitle;
    private String itemDesc;
    private String itemLink;
    // *********************************************
// Declare getters and setters etc.
// *********************************************
    public String getItemTitle()
    {
        return this.itemTitle;
    }
    public void setItemTitle(String sItemTitle)
    {
        this.itemTitle = sItemTitle;
    }
    public String getItemDesc()
    {
        return this.itemDesc;
    }
    public void setItemDesc(String sItemDesc)
    {
        this.itemDesc = sItemDesc;
    }
    public String getItemLink()
    {
        return this.itemLink;
    }
    public void setItemLink(String sItemLink)
    {
        this.itemLink = sItemLink;
    }
    // **************************************************
// Declare constructor.
// **************************************************
    public saRSSDataItem()
    {
        this.itemTitle = "";
        this.itemDesc = "";
        this.itemLink = "";
    }
    @Override
    public String toString() {
        String sageAdviceRSSData;
        sageAdviceRSSData = "saRSSDataItem [itemTitle=" + itemTitle;
        sageAdviceRSSData += ", itemDesc=" + itemDesc;
        sageAdviceRSSData += ", itemLink=" + itemLink +"]";
        return sageAdviceRSSData;
    }


}