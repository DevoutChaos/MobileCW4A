package com.example.chaos.mobiledevelopmentcw_4a;

import java.io.Serializable;

/**
 * Created by chaos on 03/12/2016.
 */

public class InitiativeDB implements Serializable {
    private int combatantID, initValue;
    private String name;

    private static final long serialVersionUID = 0L;

    public int getCombatantID()
    {
        return combatantID;
    }

    public void setCombatantID(int combatantID)
    {
        this.combatantID = combatantID;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getInitValue()
    {
        return initValue;
    }

    public void setInitValue(int initValue)
    {
        this.initValue = initValue;
    }

    @Override
    public String toString()
    {
        String combatantData;
        combatantData = "InitiativeDB [combatantID=" + combatantID;
        combatantData += ", name=" + name;
        combatantData += ", initValue=" + initValue;
        return combatantData;
    }
}