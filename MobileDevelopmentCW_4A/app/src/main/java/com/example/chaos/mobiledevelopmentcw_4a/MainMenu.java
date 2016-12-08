package com.example.chaos.mobiledevelopmentcw_4a;

import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.ExecutionException;

public class MainMenu extends AppCompatActivity{

    /***
     * Declarations (UI)
     ***/
    Button but1, but2, but5, but6, but8, but9, but10, but11, but12, but13, but16, but17, but18, but19;
    ListView lstVw1, lstVwDB, lstVwRSS;
    ListViewAdapter lstVwAda;
    DBListViewAdapter lstVwAda2;
    RSSListViewAdapter lstVwAda3;
    EditText name, init, txtInit, name2, init2;
    TextView txtName, txtRSSLoc;
    private ViewFlipper switcher;

    /***
     * Declarations (Lists)
     ***/
    public static ArrayList<String> nameLst = new ArrayList<>();
    public static ArrayList<Integer> initLst = new ArrayList<>();
    public ArrayList<String> tempNameLst = new ArrayList<>();
    public ArrayList<Integer> tempInitLst = new ArrayList<>();
    public ArrayList<Integer> combatantIDLst = new ArrayList<>();
    public ArrayList<String> combatantNameLst = new ArrayList<>();
    public ArrayList<Integer> combatantInitLst = new ArrayList<>();
    public ArrayList<String> titleLst = new ArrayList<>();
    public ArrayList<String> descLst = new ArrayList<>();
    public static ArrayList<Integer> toBeDeleted = new ArrayList<>();


    /***
     * Declarations (Variables)
     ***/
    static String[] nameArr, combNameArr, titleArr, descArr;
    String[] tempNameArr;
    static Integer[] initArr, tempInitArr, combInitArr;
    int count;
    long noInDB;
    private String tempName;
    private Integer tempInit;
    public static boolean dbAdd = false;

    /***
     * Declarations (Database)
     ***/
    InitiativeDB userInitiatives, userAdd;

    /***
     * Declarations (Other)
     ***/
    SharedPreferences initSharedPrefs;
    initSaveData initSDPrefs;
    SharedPreferences mySharedPrefs;
    FragmentManager fmAboutDialogue;

    /***
     * When the application loads
     ***/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*** Resets the lists ***/
        nameLst.clear();
        initLst.clear();
        combatantIDLst.clear();
        combatantNameLst.clear();
        combatantInitLst.clear();
        toBeDeleted.clear();

        /*** Sets up all UI components ***/
        switcher = (ViewFlipper) findViewById(R.id.ViewFlipper);
        but1 = (Button) findViewById(R.id.butBack_Tracker);
        but2 = (Button) findViewById(R.id.butSort);
        but5 = (Button) findViewById(R.id.butConfirm);
        but6 = (Button) findViewById(R.id.butBack_AddComb);
        but8 = (Button) findViewById(R.id.butInitTracker);
        but9 = (Button) findViewById(R.id.butBack_Prefs);
        but10 = (Button) findViewById(R.id.butLoad);
        but11 = (Button) findViewById(R.id.butBack_DB);
        but12 = (Button) findViewById(R.id.butBack_SA);
        but13 = (Button) findViewById(R.id.butRSS);
        but16 = (Button) findViewById(R.id.butMap);
        but17 = (Button) findViewById(R.id.butConfirm2);
        but18 = (Button) findViewById(R.id.butBack_AddDB);

        txtName = (TextView) findViewById(R.id.txtName);
        txtInit = (EditText) findViewById(R.id.txtInitiative);
        txtRSSLoc = (TextView) findViewById(R.id.txtRSSLocation);
        name = (EditText) findViewById(R.id.editName);
        init = (EditText) findViewById(R.id.editInitiative);
        name2 = (EditText) findViewById(R.id.editName2);
        init2 = (EditText) findViewById(R.id.editInitiative2);
        lstVw1 = (ListView) findViewById(R.id.mainListView);
        lstVwDB = (ListView) findViewById(R.id.dbListView);
        lstVwRSS = (ListView) findViewById(R.id.saListView);

        /*** Load any saved preferences ***/
        initSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        loadSavedPreferences();

        /***Shared Preferences ***/
        mySharedPrefs = PreferenceManager.getDefaultSharedPreferences(this);
        initSDPrefs = new initSaveData(mySharedPrefs);
        initSDPrefs.setDefaultPrefs();

        /*** Dialogue Fragment ***/
        fmAboutDialogue = this.getFragmentManager();

        /*** Handles a floating button to add combatants ***/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearInputs();
                if (count > 0) {
                    ArraysToList();
                }
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.addCombatant)));
            }
        });

        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearInputs();
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.addDB)));

            }
        });

        /***
         * Database handler
         ***/
        userInitiatives = new InitiativeDB();

        final InitiativeDBMgr dbInitMgr = new InitiativeDBMgr(this,"initiative.s3db",null,1);
        try {
            dbInitMgr.dbCreate();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "We have a problem.jpg", Toast.LENGTH_LONG).show();
        }

        userInitiatives = dbInitMgr.findCombatant(userInitiatives.getCombatantID());

        ParseOnly();

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*** Return to Home Screen ***/
        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.MainMenu)));
            }
        });

        /*** Sort all combatants by Initiative (High -> Low) ***/
        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count > 1) {
                    SortEverything();
                }
            }
        });

        /*** Return to Initiative Order - adding the combatant in the process ***/
        but5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name == null) {
                    MissingName();
                } else if (init == null) {
                    MissingInit();
                } else {
                    PrepForArrays();
                    switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.TrackerView)));
                    AddToArrays();
                }
            }
        });

        /*** Return to main view without adding a combatant ***/
        but6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.TrackerView)));
            }
        });

        /*** Goes to the initiative tracker screen from the main menu ***/
        but8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.TrackerView)));
            }
        });

        /*** Returns to the main menu from the initiative tracker ***/
        but9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.MainMenu)));
            }
        });

        /*** Goes to the view database screen from the initiative tracker ***/
        but10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.DatabaseView)));

                /*
                if (toBeDeleted != null)
                {
                    int len3 = toBeDeleted.size();
                    for (int i = 0; i < len3; i++)
                    {
                        dbInitMgr.removeCombatant(toBeDeleted.get(i));
                    }

                    toBeDeleted.clear();
                }
                */
                noInDB = dbInitMgr.CountDatabase();
                combatantIDLst.clear();
                combatantNameLst.clear();
                combatantInitLst.clear();
                combatantNameLst = dbInitMgr.GetNameInDB(noInDB);
                combatantInitLst = dbInitMgr.GetInitInDB(noInDB);
                ShowDB();
            }
        });

        /*** Returns to the Initiative Tracker from the Database screen ***/
        but11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.TrackerView)));
                if (dbAdd)
                {
                    AddToArrays();
                    dbAdd = false;
                }
            }
        });

        /*** Returns to the main menu from the RSS Feed ***/
        but12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.MainMenu)));
            }
        });

        /*** Goes to the RSS view from the Main Menu ***/
        but13.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.RSSView)));
                PrepForArraysRSS();
                AddToArraysRSS();
            }
        });

        but16.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setContentView(R.layout.activity_maps);
            }
        });

        /*** Return to Initiative Order - adding the combatant in the process ***/
        but17.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name2 == null) {
                    MissingName();
                } else if (init2 == null) {
                    MissingInit();
                } else {
                    userAdd = new InitiativeDB();
                    tempName = name2.getText().toString();
                    tempInit = Integer.parseInt(init2.getText().toString());
                    userAdd.setName(tempName);
                    userAdd.setInitValue(tempInit);
                    dbInitMgr.addCombatants(userAdd);
                    noInDB = dbInitMgr.CountDatabase();
                    combatantIDLst.clear();
                    combatantNameLst.clear();
                    combatantInitLst.clear();
                    combatantNameLst = dbInitMgr.GetNameInDB(noInDB);
                    combatantInitLst = dbInitMgr.GetInitInDB(noInDB);
                    ShowDB();
                    switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.DatabaseView)));
                }
            }
        });

        but18.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.DatabaseView)));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.about:
                // About Dialogue;
                DialogFragment initAboutDlg = new initAboutDialogue();
                initAboutDlg.show(fmAboutDialogue, "menu");
                return true;
            case R.id.action_settings:
                switcher.setDisplayedChild(switcher.indexOfChild(findViewById(R.id.PrefsView)));
                return true;
            case R.id.mBio:
                Intent mcBioDraw = new Intent(this, canvasActivity.class);
                this.startActivity(mcBioDraw);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /***
     * Resets input fields
     ***/
    public void ClearInputs() {
        name.setText("");
        init.setText("");
    }

    /***
     * Gets new data into Lists for adding to arrays
     ***/
    public void PrepForArrays() {
        /*** Gets inputs and places them into temporary holders ***/
        tempName = name.getText().toString();
        tempInit = Integer.parseInt(init.getText().toString());

        /*** Adds the temporary holders into the relevant lists ***/
        nameLst.add(tempName);
        initLst.add(tempInit);
    }

    /***
     * Passes arrays into ListViewAdapter
     ***/
    public void AddToArrays() {
        /*** Overwrites arrays with List data ***/
        nameArr = nameLst.toArray(new String[0]);
        initArr = initLst.toArray(new Integer[0]);

        lstVwAda = new com.example.chaos.mobiledevelopmentcw_4a.ListViewAdapter(this, nameArr, initArr, nameLst, initLst);
        lstVw1.setAdapter(lstVwAda);

        /*** Gets number of entries ***/
        count = nameArr.length;

        /*** Displays number of entries ***/
        Toast.makeText(this, "Combatants present: " + count, Toast.LENGTH_SHORT).show();
    }

    /***
     * Overwrites lists with Array data
     ***/
    public void ArraysToList() {
        nameLst.clear();
        initLst.clear();

        nameLst = new ArrayList(Arrays.asList(nameArr));
        initLst = new ArrayList(Arrays.asList(initArr));
    }

    /***
     * Displays a toast when called
     ***/
    public void MissingName() {
        Toast.makeText(this, "Please fill in the 'Name' section", Toast.LENGTH_SHORT).show();
    }

    /***
     * Displays a toast when called
     ***/
    public void MissingInit() {
        Toast.makeText(this, "Please fill in the 'Initiative' section", Toast.LENGTH_SHORT).show();
    }

    /***
     * Sorts both name and initiative arrays, by initiative, in descending order
     ***/
    public void SortEverything() {
        tempNameArr = Arrays.copyOf(nameArr, nameArr.length);
        tempInitArr = Arrays.copyOf(initArr, initArr.length);

        Arrays.sort(tempInitArr, Collections.<Integer>reverseOrder());

        tempNameLst.clear();
        tempInitLst.clear();

        int len1, len2;

        len1 = tempInitArr.length;
        len2 = initLst.size();

        for (int i = 0; i < len1; i++) {
            for (int x = 0; x < len2; x++) {
                if (initLst.get(x) == tempInitArr[i]) {
                    tempNameLst.add(nameLst.get(x));
                    initLst.remove(x);
                    nameLst.remove(x);

                    len1 = tempInitArr.length;
                    len2 = initLst.size();
                }
            }
        }

        nameArr = tempNameLst.toArray(new String[0]);
        initArr = Arrays.copyOf(tempInitArr, tempInitArr.length);

        ArraysToList();
        AddToArrays();
    }

    private void loadSavedPreferences()
    {
        txtRSSLoc.setText(txtRSSLoc.getText() + initSharedPrefs.getString("init_RSSFeed", "www.sageadvice.eu/feed"));
    }

    /***
     * Outputs the number of records, and displays them sans ID
     ***/
    public void ShowDB()
    {
        Toast.makeText(this, "Database holds " + noInDB + " records", Toast.LENGTH_SHORT).show();
        PrepForArraysDB();
        AddToArraysDB();
    }

    /***
     * Places all of the ArrayList data into Arrays
     ***/
    public void PrepForArraysDB()
    {
        /*** Overwrites arrays with List data ***/
        combNameArr = combatantNameLst.toArray(new String[0]);
        combInitArr = combatantInitLst.toArray(new Integer[0]);
    }

    /***
     * Calls for the arrays to be displayed in the DBListViewAdapter
     ***/
    public void AddToArraysDB()
    {
        lstVwAda2 = new com.example.chaos.mobiledevelopmentcw_4a.DBListViewAdapter(this, combNameArr, combInitArr, combatantNameLst, combatantInitLst);
        lstVwDB.setAdapter(lstVwAda2);
    }
    public void ParseOnly()
    {
        /***
         * Parse the RSS feed
         ***/
        saRSSDataItem sageAdvice = new saRSSDataItem();
        String RSSFeedURL = "http://www.sageadvice.eu/feed/";
        saAsyncRSSParser rssAsyncParse = new saAsyncRSSParser(this,RSSFeedURL);
        try {
            sageAdvice = rssAsyncParse.execute("").get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    public void PrepForArraysRSS()
    {
        titleArr = saRSSParser.titleLst.toArray(new String[0]);
        descArr = saRSSParser.descLst.toArray(new String[0]);
    }

    public void AddToArraysRSS()
    {
        lstVwAda3 = new com.example.chaos.mobiledevelopmentcw_4a.RSSListViewAdapter(this, titleArr, descArr, titleLst, descLst);
        lstVwRSS.setAdapter(lstVwAda3);
    }

}