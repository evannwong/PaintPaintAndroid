package com.example.paintpaintandroid;

import android.graphics.Path;

public class Draw {

    public int color;
    public int strokeWidth;
    public Path path;

    public Draw(int color, int strokeWidth, Path path) {

        this.color = color;
        this.strokeWidth = strokeWidth;
        this.path = path;
    }

    public int getColor() {
        return this.color;
    }

    public int getStrokeWidth() {
        return this.strokeWidth;
    }

    public Path getPath() {
        return this.path;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setStrokeWidth(int width) {
        this.strokeWidth = width;
    }

    public void setPath(Path path) {
        this.path = path;
    }

}
