package com.goiching.iching.core.util;

import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;


public class Arrow extends Region
{
    public Arrow()
    {
        getChildren().add(_canvas);
    }

    @Override
    public Orientation getContentBias()
    {
        return Orientation.HORIZONTAL;
    }

    @Override
    protected double computePrefHeight(double width)
    {
        return ARROW_HEAD_HEIGHT;
    }

    @Override
    protected double computeMinHeight(double width)
    {
        return computePrefHeight(width);
    }

    @Override
    protected double computePrefWidth(double height)
    {
        return 3 * ARROW_HEAD_WIDTH;
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

        redraw(w, h);

        layoutInArea(_canvas, x, y, w, h, 0, HPos.CENTER, VPos.CENTER);
    }

    private void redraw(double w, double h)
    {
        GraphicsContext gc = _canvas.getGraphicsContext2D();

        gc.setFill(Color.BLACK);
        gc.clearRect(0, 0, w, h);

        double wahw = w - ARROW_HEAD_WIDTH;
        double hahh = ARROW_HEAD_HEIGHT / 2 - ARROW_BODY_HEIGHT / 2;

        gc.fillPolygon(
            new double[] {0, wahw, wahw, w, wahw, wahw, 0},
            new double[] {hahh, hahh, 0, ARROW_HEAD_HEIGHT / 2, ARROW_HEAD_HEIGHT, ARROW_HEAD_HEIGHT - hahh, ARROW_HEAD_HEIGHT - hahh}, 7);
    }

    private static final double ARROW_HEAD_WIDTH  = 50;
    private static final double ARROW_HEAD_HEIGHT = 70;
    private static final double ARROW_BODY_HEIGHT = 20;

    private Canvas _canvas = new Canvas();
}
