package com.example.soundswavedetection;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

class ScreenVisualization extends View {

    private static final int MAX_AMPLITUDE = 32767;
    private DotsPointArray vectors;
    private ColorScheme colorScheme;
    private DotsPointArray amplitudes;
    private int widht, height;

    public ScreenVisualization(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        colorScheme = new ColorScheme();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        widht = w;
        height = h;
        amplitudes = new DotsPointArray(widht / 2, 1);
        vectors = new DotsPointArray(widht / 2, 2);
    }

    public void addAmplitude(int amplitude){
        invalidate();
        float scaledHeight = ((float) amplitude / MAX_AMPLITUDE) * (height - 1);
        amplitudes.add(0, height - scaledHeight);
        vectors.add(0, height, 0, height - scaledHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        colorScheme.shuffle();
        canvas.drawPoint(colorScheme.CanvasPoint);
        canvas.drawLines(colorScheme.getIndexedArray(0), colorScheme.LinePoint);
        canvas.drawLine(widht / 2, height, widht / 2, 0, colorScheme.LinePaint);
        canvas.drawPoints(amplitudes.getIndexedArray(widht / 2), colorScheme.CirclePaint);

    }
}
