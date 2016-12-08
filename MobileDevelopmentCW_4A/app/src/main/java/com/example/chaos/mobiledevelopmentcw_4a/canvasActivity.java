package com.example.chaos.mobiledevelopmentcw_4a;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by chaos on 06/12/2016.
 */

public class canvasActivity extends Activity {

    Button but19;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.canvas_draw_screen); // app main UI screen
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(new canvasSurfaceView(this));

        /*View rootview = inflater.inflate(R.layout.canvas_draw_screen, container,false);

        but19 = (Button) rootview.findViewById(R.id.butBack_Canvas);

        but19.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                setContentView(R.layout.content_main_menu);
            }
        });
        */
    }
}
