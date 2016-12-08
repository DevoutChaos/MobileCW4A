package com.example.chaos.mobiledevelopmentcw_4a;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by chaos on 06/12/2016.
 */

public class canvasSurfaceView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder canvasSurface;
    canvasThread drawingThread = null;
    public canvasSurfaceView(Context context)
    {
        super(context);
        canvasSurface = getHolder();
        canvasSurface.addCallback(this);
        drawingThread = new canvasThread(getHolder(), this);
        setFocusable(true);
    }
    public canvasThread getThread()
    {
        return drawingThread;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        drawingThread.setRunning(true);
        drawingThread.start();
    }
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int
            height)
    {
        drawingThread.setSurfaceSize(width,height);
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        drawingThread.setRunning(false);
        while(retry)
        {
            try {
                drawingThread.join();
                retry = false;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}