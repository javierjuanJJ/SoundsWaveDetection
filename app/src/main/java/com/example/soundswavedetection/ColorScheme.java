package com.example.soundswavedetection;

import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

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

    public void shuffle(){
        if (colorChangeFrameSea-- >= 0){
            return;
        }
        colorChangeFrameSea = COLOR_CHANGE_FRAME_RATE;
        LinePaint.setColor(randomColor());
        CirclePaint.setColor(randomColor());
    }

    private int randomColor() {
        Random r = new Random();
        int ra = r.nextInt(255);
        int g = r.nextInt(255);
        int b = r.nextInt(255);
        return Color.rgb(ra,g,b);
    }

}
