package com.yijingoracle.iching.core;

import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Orientation;


public class TrigramRegion extends Region
{
    public TrigramRegion()
    {
        getChildren().add(_canvas);
    }

    public TrigramRegion(Trigram trigram)
    {
        this();

        setTrigram(trigram);
    }

    public void setTrigram(Trigram trigram)
    {
        _trigram = trigram;

        redraw();
    }

    public Trigram getTrigram()
    {
        return _trigram;
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

        if (_trigram == null)
            return;

        int[] trig = TABLE[_trigram.getName().getId()];

        _canvas.setLineProperties(1, Color.BLACK);

        for (int i = 0; i < trig.length; ++i)
        {
            if(trig[i] > 0)
                _canvas.drawYangLine(i);
            else
                _canvas.drawYinLine(i);
        }
    }

    private static final double ASPECT_RATIO = 2.0;

    private static final int[][] TABLE = {
            //               N  HoTu LoSu Name
            { 1, 1, 1 },  // 0  1    6    Heaven
            { 0, 0, 0 },  // 1  8    2    Earth
            { 1, 1, 0 },  // 2  2    7    Lake
            { 0, 0, 1 },  // 3  7    8    Mountain
            { 1, 0, 1 },  // 4  3    9    Fire
            { 0, 1, 0 },  // 5  6    1    Water
            { 1, 0, 0 },  // 6  4    3    Thunder
            { 0, 1, 1 },  // 7  5    4    Wind
    };

    private LineCanvas _canvas = new LineCanvas(Trigram.LINES);
    private Trigram _trigram;
}

