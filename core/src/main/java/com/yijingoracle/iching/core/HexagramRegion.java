package com.yijingoracle.iching.core;

import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Orientation;


public class HexagramRegion extends Region
{
    public HexagramRegion()
    {
        getChildren().add(_canvas);
    }

    public HexagramRegion(Hexagram hexagram)
    {
        this();

        setHexagram(hexagram);
    }

    public Hexagram getHexagram()
    {
        return _hexagram;
    }

    public void setHexagram(Hexagram hexagram)
    {
        _hexagram = hexagram;

        redraw();
    }

    @Override
    public Orientation getContentBias()
    {
        return Orientation.HORIZONTAL;
    }

    @Override
    protected double computePrefHeight(double width)
    {
        return (1.0 / ASPECT_RATIO) * width;
    }

    @Override
    protected double computeMinHeight(double width)
    {
        return computePrefHeight(width);
    }

    @Override
    protected double computePrefWidth(double height)
    {
        return ASPECT_RATIO * height;
    }

    @Override
    protected double computeMinWidth(double height)
    {
        return computePrefWidth(height);
    }

    @Override
    protected void layoutChildren()
    {
        double x = 0;
        double y = 0;
        double w = getWidth();
        double h = getHeight();

        _canvas.setWidth(w);
        _canvas.setHeight(h);

        redraw();

        layoutInArea(_canvas, x, y, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    private void redraw()
    {
        _canvas.clear();

        if(_hexagram == null)
            return;

        drawHexagram();
        drawMarkedLines();
    }

    private void drawHexagram()
    {
        _canvas.setLineProperties(1, Color.BLACK);

        for(int i = 0; i < Hexagram.LINES; ++i)
        {
            if(_hexagram.getLine(i + 1) > 0)
                _canvas.drawYangLine(i);
            else
                _canvas.drawYinLine(i);
        }
    }

    private void drawMarkedLines()
    {
        _canvas.setLineProperties(MOVE_LINE_SIZE, Color.RED);

        for(int i = 0; i < Hexagram.LINES; ++i)
        {
            if(_hexagram.lineChanged(i + 1))
            {
                if(_hexagram.getLine(i + 1) > 0)
                    _canvas.circleLine(i);
                else
                    _canvas.crossLine(i);
            }
        }
    }

    private static final int MOVE_LINE_SIZE = 5;
    private static final double ASPECT_RATIO = 1.0;

    private LineCanvas _canvas = new LineCanvas(Hexagram.LINES);
    private Hexagram _hexagram;
}

