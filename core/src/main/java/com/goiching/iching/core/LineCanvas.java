package com.goiching.iching.core;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;


class LineCanvas extends Canvas
{
    public LineCanvas(int count)
    {
        _count = count;
        VSPACING = 0.25 / (count - 1);
        HEIGHT = (1.0 - (count - 1) * VSPACING) / count;
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

        double margin = MARGIN * (width < height ? width : height);
        width -= 2 * margin;
        height -= 2 * margin;

        double x = margin;
        double y = margin + i * (HEIGHT + VSPACING) * height;

        double ww = WIDTH * width;
        double hh = HEIGHT * height;

        _gc.fillRoundRect(x, y, ww, hh, 0.15 * ww, 0.9 * hh);
    }

    public void drawYinLine(int i)
    {
        i = remapLineId(i);

        double width = getWidth();
        double height = getHeight();

        double margin = MARGIN * (width < height ? width : height);
        width -= 2 * margin;
        height -= 2 * margin;

        double x = margin;
        double y = margin + i * (HEIGHT + VSPACING) * height;
        double w = (WIDTH - HSPACING) * width / 2.0;

        double hh = HEIGHT * height;

        _gc.fillRoundRect(x, y, w, hh, 0.4 * w, 0.9 * hh);
        _gc.fillRoundRect(w + x + HSPACING * width, y, w, hh, 0.4 * w, 0.9 * hh);
    }

    public void circleLine(int i)
    {
        i = remapLineId(i);

        double width = getWidth();
        double height = getHeight();

        double margin = MARGIN * (width < height ? width : height);
        width -= 2 * margin;
        height -= 2 * margin;

        double x = margin;
        double y = margin + i * (HEIGHT + VSPACING) * height;
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

        double margin = MARGIN * (width < height ? width : height);
        width -= 2 * margin;
        height -= 2 * margin;

        double x = margin;
        double y = margin + i * (HEIGHT + VSPACING) * height;
        double w = (WIDTH - HSPACING) * width / 2.0;
        double min = Math.min(HSPACING * width, HEIGHT * height) + (VSPACING / 2.0) * width;

        _gc.strokeLine(w + x + (HSPACING / 2.0) * width - min / 2.0,
                y + (HEIGHT / 2.0) * height - min / 2.0,
                margin + w + (HSPACING / 2.0) * width + min / 2.0,
                y + (HEIGHT / 2.0) * height + min / 2.0);
        _gc.strokeLine(margin + w + (HSPACING / 2.0) * width - min / 2.0,
                y + (HEIGHT / 2.0) * height + min / 2.0,
                margin + w + (HSPACING / 2.0) * width + min / 2.0,
                y + (HEIGHT / 2.0) * height - min / 2.0);
    }

    private boolean lineInRange(int i)
    {
        return !(i < 0 && i >= _count);

    }

    private int remapLineId(int i)
    {
        if (!lineInRange(i))
            throw new IndexOutOfBoundsException(String.format("Line %d is out of bounds", i));

        return _count - (i + 1);
    }

    private final double MARGIN   = 0.1;
    private final double HSPACING = 0.30;
    private final double WIDTH    = 1.0;
    private double VSPACING;
    private double HEIGHT;

    private GraphicsContext _gc = getGraphicsContext2D();
    private int _count;
}

