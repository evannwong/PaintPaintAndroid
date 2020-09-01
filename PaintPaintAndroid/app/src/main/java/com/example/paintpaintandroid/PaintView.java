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
    public int strokeWidth;
    private Bitmap mBitmap;
    private Canvas mCanvas;
    private Paint mBitmapPaint = new Paint(Paint.DITHER_FLAG);

    private ArrayList<Draw> paths = new ArrayList<>();
    private ArrayList<Draw> undo = new ArrayList<>();


    // AUTO generated constructors
    public PaintView(Context context) {
        super(context, null);
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

    public void initialize (DisplayMetrics displayMetrics) {

        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(mBitmap);

        currentColor = defaultColor;
        strokeWidth = brushSize;
    }

    @Override

    protected void onDraw(Canvas canvas) {

        canvas.save();
        mCanvas.drawColor(backgroundColor);

        for (Draw draw : paths) {
            mPaint.setColor(draw.color);
            mPaint.setStrokeWidth(draw.strokeWidth);
            mPaint.setMaskFilter(null);

            mCanvas.drawPath(draw.getPath(), mPaint);

        }

        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
        canvas.restore();
    }

    protected void touchStart(float x, float y) {

        mPath = new Path();

        Draw draw = new Draw(currentColor, strokeWidth, mPath);
        paths.add(draw);

        mPath.reset();
        mPath.moveTo(x,y);

        mX = x;
        mY = y;

    }

    private void touchMove(float x, float y) {

        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);

        if (dx >= touchTolerance || dy >= touchTolerance) {

            mPath.quadTo(mX,mY, (x+mX) / 2, (y + mY) / 2);

            mX = x;
            mY = y;
        }

    }

    private void touchUp() {

        mPath.lineTo(mX, mY);

    }

    @Override

    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
                touchStart(x,y);
                invalidate();
                break;

            case MotionEvent.ACTION_UP:
                touchUp();
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                touchMove(x,y);
                invalidate();
                break;

        }

        return true;

    }

    public void clear() {

        backgroundColor = defaultBackGroundColor;

        paths.clear();
        invalidate();

    }

    public void undo() {

        if (paths.size() > 0) {

            undo.add(paths.remove(paths.size() - 1));
            invalidate();

        } else {

            Toast.makeText(getContext(), "Nothing to undo", Toast.LENGTH_LONG).show();

        }

    }

    public void redo() {

        if (undo.size() > 0) {

            paths.add(undo.remove(undo.size() - 1));
            invalidate();

        } else {

            Toast.makeText(getContext(), "Nothing to redo", Toast.LENGTH_LONG).show();

        }

    }

    public void setStrokeWidth(int size) {

        strokeWidth = size;

    }

    public void setColor(int color) {

        currentColor = color;

    }

    public void saveImage() {

        int count = 0;

        File sdDirectory = Environment.getExternalStorageDirectory();
        File subDirectory = new File(sdDirectory.toString() + "/Pictures/Paint");

        if (subDirectory.exists()) {

            File[] existing = subDirectory.listFiles();

            for (File file : existing) {

                if (file.getName().endsWith(".jpg") || file.getName().endsWith(".png")) {

                    count++;

                }

            }

        } else {
            subDirectory.mkdir();
        }

        if (subDirectory.exists()) {
            File image = new File(subDirectory, "/drawing_" + (count + 1));
            FileOutputStream fileOutputStream;

            try {

                fileOutputStream = new FileOutputStream(image);

                mBitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);

                fileOutputStream.flush();
                fileOutputStream.close();

                Toast.makeText(getContext(), "saved", Toast.LENGTH_LONG).show();

            } catch(FileNotFoundException e) {

            } catch (IOException e) {

            }
        }

    }


}
