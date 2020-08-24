package com.example.paintpaintandroid;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

public class PaintView extends View {

    public ViewGroup.LayoutParams params;
    private Path path = new Path();
    private Paint brush = new Paint();

    public PaintView(Context context) {
        super(context);

        /*
        Nessecary documentation for understanding the Android Paint library
        https://developer.android.com/reference/android/graphics/Paint

        brush.setAntiAlias(booleanValue) allows the smoothing of edges
        brush.setColor(Color.COLOR) allows the brush to be a certain color
        brush.setStyle(Paint.Style.STYLE) allows the brush to either be stroke, fill, etc
        brush.setStrokeJoin(Paint.Join.STRING) allows the brush to have three functions
        1.) BEVEL allows the outer edges of a join to meet with a straight line
        2.) MITER allows the outer edges of a join to meet with an angle
        3.) ROUND allows the outer edges of a join to meet with a circular arc
        brush.setStrokeWidth(INT) allows the brush to be a certain width
        */
        brush.setAntiAlias(true);
        brush.setColor(Color.BLACK);
        brush.setStyle(Paint.Style.STROKE);
        brush.setStrokeJoin(Paint.Join.ROUND);
        brush.setStrokeWidth(8);

        // As taken from https://developer.android.com/reference/android/view/ViewGroup.LayoutParams
        /*
        LayoutParams are used by views to tell their parents how they want to be laid out. See ViewGroup Layout Attributes for a list of all child view attributes that this class supports.

        The base LayoutParams class just describes how big the view wants to be for both width and height. For each dimension, it can specify one of:

        FILL_PARENT (renamed MATCH_PARENT in API Level 8 and higher), which means that the view wants to be as big as its parent (minus padding)
        WRAP_CONTENT, which means that the view wants to be just big enough to enclose its content (plus padding)
        an exact number
        There are subclasses of LayoutParams for different subclasses of ViewGroup. For example, AbsoluteLayout has its own subclass of LayoutParams which adds an X and Y value.
        */
        params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        path = new Path();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float mX = event.getX();
        float mY = event.getY();

        switch(event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(mX,mY);
                return true;

            case MotionEvent.ACTION_MOVE:
                path.lineTo(mX,mY);
                break;

            default:
                return false;
        }

        postInvalidate();
        return false;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, brush);
    }
}
