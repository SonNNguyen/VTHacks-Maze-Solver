package com.davidhaas.mazesolver;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

class Dot extends View {
    private int RADIUS;
    private int x;
    private int y;
    private Rect boundingBox;
    private int feather;
    private int initialX;
    private int initialY;
    private int offsetX;
    private int offsetY;
    private Paint myPaint;
    private Paint rPaint;


    private static String TAG = "Dot";

    // https://stackoverflow.com/questions/2047573/how-to-draw-filled-polygon
    public Dot(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Dot(Context context, AttributeSet attrs, int x, int y, int radius) {
        super(context, attrs);

        this.x = x;
        this.y = y;
        this.RADIUS = radius;
        feather = (int) (RADIUS * 2.2);

        // Log.i(TAG, "Dot: radius: " + radius);
        // Log.i(TAG, "Dot: feather: " + feather);

        boundingBox = new Rect(x - (RADIUS + feather), y - (RADIUS + feather),
                x + (RADIUS + feather),
                y + (RADIUS + feather));

        // Log.i(TAG, "Dot: x: " + boundingBox.left + " y: " + boundingBox.top + " width: " + boundingBox.width() + " height: " + boundingBox.height());

        myPaint = new Paint();
        myPaint.setColor(Color.argb(255, 153,153,255));
        myPaint.setAntiAlias(true);

        rPaint = new Paint();
        rPaint.setStyle(Paint.Style.STROKE);
        rPaint.setColor(Color.RED);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        Point touch;
        //Log.i(TAG, "onTouchEvent: Touched!");
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                touch = new Point((int) event.getX(), (int) event.getY());
                // Log.i(TAG, "onTouchEvent: " + touch);

                if (boundingBox.contains(touch.x, touch.y)) {
                    initialX = x;
                    initialY = y;
                    offsetX = touch.x;
                    offsetY = touch.y;
                }

                break;

            case MotionEvent.ACTION_MOVE:
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                touch = new Point((int) event.getX(), (int) event.getY());
                if (boundingBox.contains(touch.x, touch.y)) {
                    //Log.i(TAG, "onTouchEvent: " + touch);
                    //Log.i(TAG, "onTouchEvent: " + boundingBox);

                    x = initialX + touch.x - offsetX;
                    y = initialY + touch.y - offsetY;
                    boundingBox.offsetTo(x - (RADIUS + feather), y - (RADIUS + feather));
                }
                break;
        }
        return (true);
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);

        canvas.drawCircle(x, y, RADIUS, myPaint);
        // canvas.drawRect(boundingBox, rPaint);
        // invalidate();
    }

    public Point getLocation() {
        return new Point(x,y);
    }
}