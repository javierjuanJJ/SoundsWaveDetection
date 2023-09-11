package com.example.soundswavedetection;

import android.graphics.Color;
import android.graphics.Paint;

class ColorScheme {


    private static final int COLOR_CHANGE_FRAME_RATE = 20;
    private static int colorChangeFrameSea = 0;

    public Paint CanvasPoint, CirclePaint, LinePaint;

    public ColorScheme (){
        LinePaint = new Paint();
        LinePaint.setStrokeWidth(1);
        LinePaint.setColor(Color.GREEN);

        CirclePaint = new Paint();
        CirclePaint.setStrokeWidth(5);
        CirclePaint.setColor(Color.GREEN);

        CanvasPoint = new Paint();
        CanvasPoint.setStrokeWidth(5);
        CanvasPoint.setColor(Color.WHITE);
    }

}
