package com.yijingoracle.iching.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


class LineCanvas extends Canvas
{
    public LineCanvas(int count)
    {
        _count = count;
        VSPACING = 0.25 / (count - 1);
        HEIGHT = (1.0 - 2 * VMARGIN - (count - 1) * VSPACING) / count;
    }

    public void clear()
    {
        _gc.clearRect(0, 0, getWidth(), getHeight());
    }

    public void setLineProperties(int width, Color color)
    {
        _gc.setFill(color);
        _gc.setStroke(color);
        _gc.setLineWidth(width);
    }

    public void drawYangLine(int i)
    {
        i = remapLineId(i);

        double width = getWidth();
        double height = getHeight();
        double x = HMARGIN * width;
        double y = VMARGIN * height + i * (HEIGHT + VSPACING) * height;

        _gc.fillRect(x, y, WIDTH * width, HEIGHT * height);
    }

    public void drawYinLine(int i)
    {
        i = remapLineId(i);

        double width = getWidth();
        double height = getHeight();
        double x = HMARGIN * width;
        double y = VMARGIN * height + i * (HEIGHT + VSPACING) * height;
        double w = (WIDTH - HSPACING) * width / 2.0;

        _gc.fillRect(x, y, w, HEIGHT * height);
        _gc.fillRect(w + x + HSPACING * width, y, w, HEIGHT * height);
    }

    public void circleLine(int i)
    {
        i = remapLineId(i);

        double width = getWidth();
        double height = getHeight();
        double x = HMARGIN * width;
        double y = VMARGIN * height + i * (HEIGHT + VSPACING) * height;
        double w = (WIDTH - HSPACING) * width / 2.0;
        double min = Math.min(HSPACING * width, HEIGHT * height) + (VSPACING / 2.0) * width;

        _gc.strokeOval(w + x + (HSPACING / 2.0) * width - min / 2.0,
                y + (HEIGHT / 2.0) * height - min / 2.0, min, min);
    }

    public void crossLine(int i)
    {
        i = remapLineId(i);

        double width = getWidth();
        double height = getHeight();
        double x = HMARGIN * width;
        double y = VMARGIN * height + i * (HEIGHT + VSPACING) * height;
        double w = (WIDTH - HSPACING) * width / 2.0;
        double min = Math.min(HSPACING * width, HEIGHT * height) + (VSPACING / 2.0) * width;

        _gc.strokeLine(w + x + (HSPACING / 2.0) * width - min / 2.0,
                y + (HEIGHT / 2.0) * height - min / 2.0,
                HMARGIN * width + w + (HSPACING / 2.0) * width + min / 2.0,
                y + (HEIGHT / 2.0) * height + min / 2.0);
        _gc.strokeLine(HMARGIN * width + w + (HSPACING / 2.0) * width - min / 2.0,
                y + (HEIGHT / 2.0) * height + min / 2.0,
                HMARGIN * width + w + (HSPACING / 2.0) * width + min / 2.0,
                y + (HEIGHT / 2.0) * height - min / 2.0);
    }

    private boolean lineInRange(int i)
    {
        if (i < 0 && i >= _count)
            return false;

        return true;
    }

    private int remapLineId(int i)
    {
        if (!lineInRange(i))
            throw new IndexOutOfBoundsException(String.format("Line %d is out of bounds", i));

        return _count - (i + 1);
    }

    private final double HMARGIN  = 0.0;
    private final double VMARGIN  = 0.0;
    private final double HSPACING = 0.30;
    private final double WIDTH    = 1.0 - 2 * HMARGIN;
    private double VSPACING;
    private double HEIGHT;

    private GraphicsContext _gc = getGraphicsContext2D();
    private int _count;
}

