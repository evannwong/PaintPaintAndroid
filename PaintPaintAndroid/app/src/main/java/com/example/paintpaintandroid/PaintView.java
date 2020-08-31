package com.example.paintpaintandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PaintView extends View {


    //Everything that the class requires as variables is set here.

    public static int brushSize = 10;
    public static final int defaultColor = Color.BLACK;
    public static final int defaultBackGroundColor = Color.WHITE;
    private static final float touchTolerance = 4;

    /*
    Bitmaps are representative of a bitmap image, which is used to store digital images.
    This is comparable to java.awt.Image used in the earlier years of our computer science degree
    Bitmaps are different from Drawables, as drawables are an abstraction of something that can be drawn, such as a drawable
    could be a bitmap (as BitmapDrawable), or, a drawable could be a collection of other Drawable objects.

    Bitmaps are only specific to a Drawable in which the Drawable is an image.

    Canvas is the class that holds the draw calls. Basically, if we draw something, we need the Bitmap to hold the pixels, and the canvas
    to hold the draw calls, which means that the canvas is writing onto the Bitmap. Canvas works with Paint to describe the styles
    and the brush sizes, etc.

    We're going to use an array list for undo and paths to handle the redo and undo features.
     */

    private float mX,mY;
    private Path mPath;
    private Paint mPaint;
    private int currentColor;
    private int backgroundColor = defaultBackGroundColor;
    private int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    private ArrayList<Draw> paths = new ArrayList<>();
    private ArrayList<Draw> undo = new ArrayList<>();


    // AUTO generated constructors
    public PaintView(Context context) {
        super(context);
    }

    public PaintView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(defaultColor);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setXfermode(null);
        mPaint.setAlpha(0xff);
    }
}
