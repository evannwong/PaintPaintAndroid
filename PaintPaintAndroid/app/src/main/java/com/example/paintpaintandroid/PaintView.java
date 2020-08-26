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

    /*
    ViewGroup.LayoutParams are used by views to tell their parents how they want to be laid out. Basically,
    it allows the view to be as big as the parent, or wrap_content which means that the view just wants to be big enough
    to close its content.
    */

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

        For color, you can set it with rgb using argb(int alpha, int red, int green, int blue)
        In argb, alpha represents the transparency value (default is 255 for opaque)

        brush.setStyle(Paint.Style.STYLE) allows the brush to either be stroke, fill, etc
        brush.setStrokeJoin(Paint.Join.STRING) allows the brush to have three functions
        1.) BEVEL allows the outer edges of a join to meet with a straight line
        2.) MITER allows the outer edges of a join to meet with an angle
        3.) ROUND allows the outer edges of a join to meet with a circular arc
        brush.setStrokeWidth(INT) allows the brush to be a certain width
        */
        brush.setAntiAlias(true);
        brush.setColor(Color.argb(255,6,12,11));
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

        // mX is the position X on the screen
        // mY is the position Y on the screen
        float mX = event.getX();
        float mY = event.getY();

        // ACTION_DOWN is when the user first touches the screen, then the graphics path moves to the x and y location of the screen
        // ACTION_MOVE (From developer.android.com/reference/android/view/MotionEvent
        // Constant for getActionMasked(): A change has happened during a press gesture (between ACTION_DOWN and ACTION_UP).
        // The motion contains the most recent point, as well as any intermediate points since the last down or move event.

        // We return false for the action as a default since no action is a default

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
