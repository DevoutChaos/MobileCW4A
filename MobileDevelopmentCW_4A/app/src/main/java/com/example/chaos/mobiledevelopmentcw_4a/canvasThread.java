package com.example.chaos.mobiledevelopmentcw_4a;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * Created by chaos on 06/12/2016.
 */

public class canvasThread extends Thread{
    private int canvasWidth, canvasHeight, i;
    private float xPos, yPos, HalfAppletHeight, HalfAppletWidth;
    private boolean first = true, run = false;
    private SurfaceHolder canvasSurface;
    private Paint paintCanvas;
    private canvasSurfaceView canvasSF;

    int dDate = (int) (31 * Math.random()) + 1;

    public canvasThread(SurfaceHolder surfaceHolder, canvasSurfaceView canvasSurfV)
    {
        this.canvasSurface = surfaceHolder;
        this.canvasSF = canvasSurfV;
        paintCanvas = new Paint();
    }

    public void doStart()
    {
        first = false;
    }

    public void run()
    {
        while (run)
        {
            Canvas c = null;
            try
            {
                c = canvasSurface.lockCanvas(null);
                synchronized (canvasSurface)
                {
                    svDraw(c);
                }
            }
            finally {
                if (c != null)
                {
                    canvasSurface.unlockCanvasAndPost(c);
                }
            }
        }
    }
    public void setRunning(boolean b)
    {
        run = b;
    }

    public void setSurfaceSize(int width, int height)
    {
        synchronized (canvasSurface)
        {
            canvasWidth = width;
            canvasHeight = height;
            HalfAppletHeight = canvasHeight/2;
            HalfAppletWidth = canvasWidth/32;
            doStart();
        }
    }

    private void svDraw(Canvas canvas)
    {
        if (run)
        {
            canvas.save();
            canvas.restore();
            canvas.drawColor(Color.WHITE);
            paintCanvas.setStyle(Paint.Style.FILL);
            drawAxes(canvas);
            paintCanvas.setColor(Color.RED);
            drawWave(canvas, 23);
            paintCanvas.setColor(Color.GREEN);
            drawWave(canvas, 28);
            paintCanvas.setColor(Color.BLUE);
            drawWave(canvas, 33);
        }
    }

    public void drawWave(Canvas theCanvas, int period)
    {
        float xPosOld = 0.0f, yPosOld = 0.0f;
        int dStart = -15, sDate = 0, tDate = 0;
        sDate = dDate + dStart;

        for (i = 0; i <= 30; i++)
        {
            xPos = i * HalfAppletWidth;
            tDate = sDate + i;
            yPos = (float)(-100.0f * (Math.sin((tDate%period)*2*Math.PI/period)));
            if ( i == 0) {
                paintCanvas.setStyle(Paint.Style.FILL);
            }
            else {
                theCanvas.drawLine(xPosOld, (yPosOld + HalfAppletHeight), xPos, (yPos + HalfAppletHeight), paintCanvas);
            }
            xPosOld = xPos;
            yPosOld = yPos;
        }
    }

    public void drawAxes(Canvas theCanvas)
    {
        paintCanvas.setColor(Color.BLACK);
        theCanvas.drawLine(0,HalfAppletHeight,30*HalfAppletWidth,HalfAppletHeight,paintCanvas); // Horizontal X Axes
        theCanvas.drawLine(15* HalfAppletWidth,0,15* HalfAppletWidth,canvasHeight,paintCanvas); // Vertical Y Axes
    }
}
