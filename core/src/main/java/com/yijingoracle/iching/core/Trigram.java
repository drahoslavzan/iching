package com.yijingoracle.iching.core;

import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Orientation;


public class Trigram extends Region
{
    public enum Relation
    {
        Nurturing (1),
        Exhausting (0),
        Controlling (-1),
        Injuring (-1);

        Relation(int eval) { _eval = eval; }

        private int _eval;
    };

    public static final int TRIGRAM_COUNT = 8;

    public static int getIdFromEarlyHeaven(int id)
    {
        if (id < 1 || id >= HoTu.length || HoTu[id] < 0)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", id));

        return HoTu[id];
    }

    public static int getIdFromLaterHeaven(int id)
    {
        if (id < 1 || id >= LoSu.length || LoSu[id] < 0)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", id));

        return LoSu[id];
    }

    public static Relation getTrigramsRelation(int i, int j)
    {
        if (i < 1 || i > TRIGRAM_COUNT)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", i));
        if (j < 1 || j > TRIGRAM_COUNT)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", j));

        return RELATION[i-1][j-1];
    }

    public Trigram(int id)
    {
        getChildren().add(_canvas);

        setTrigramId(id);
    }

    public Trigram() { this(ID_BLANK); }

    public int getTrigramId()
    {
        return _id;
    }

    public void setTrigramId(int id)
    {
        if(id < 0 || id > TRIGRAM_COUNT)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", id));

        _id = id;

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

        if (_id == ID_BLANK)
            return;

        _canvas.setLineProperties(1, Color.BLACK);

        int[] trig = TABLE[_id - 1];

        for (int i = 0; i < trig.length; ++i)
        {
            if(trig[i] > 0)
                _canvas.drawYangLine(i);
            else
                _canvas.drawYinLine(i);
        }
    }

    private static final int LINE_COUNT = 3;
    private static final double ASPECT_RATIO = 2.0;
    private static final int ID_BLANK = 0;

    private static final int[][] TABLE = {
                          // N  HoTu LoSu Name
            { 1, 1, 1 },  // 1  1    6    Heaven
            { 0, 0, 0 },  // 2  8    2    Earth
            { 1, 1, 0 },  // 3  2    7    Lake
            { 0, 0, 1 },  // 4  7    8    Mountain
            { 1, 0, 1 },  // 5  3    9    Fire
            { 0, 1, 0 },  // 6  6    1    Water
            { 1, 0, 0 },  // 7  4    3    Thunder
            { 0, 1, 1 },  // 8  5    4    Wind
    };

    private static final Relation[][] RELATION = {
            // Heaven               Earth                 Lake                  Mountain              Fire                  Water                 Thunder               Wind
            { Relation.Nurturing  , Relation.Exhausting , Relation.Nurturing  , Relation.Exhausting , Relation.Injuring   , Relation.Nurturing  , Relation.Controlling, Relation.Controlling },  // Heaven
            { Relation.Nurturing  , Relation.Nurturing  , Relation.Nurturing  , Relation.Nurturing  , Relation.Exhausting , Relation.Controlling, Relation.Injuring   , Relation.Injuring    },  // Earth
            { Relation.Nurturing  , Relation.Exhausting , Relation.Nurturing  , Relation.Exhausting , Relation.Injuring   , Relation.Nurturing  , Relation.Controlling, Relation.Controlling },  // Lake
            { Relation.Nurturing  , Relation.Nurturing  , Relation.Nurturing  , Relation.Nurturing  , Relation.Exhausting , Relation.Controlling, Relation.Injuring   , Relation.Injuring    },  // Mountain
            { Relation.Controlling, Relation.Nurturing  , Relation.Controlling, Relation.Nurturing  , Relation.Nurturing  , Relation.Injuring   , Relation.Exhausting , Relation.Exhausting  },  // Fire
            { Relation.Exhausting , Relation.Injuring   , Relation.Exhausting , Relation.Injuring   , Relation.Controlling, Relation.Nurturing  , Relation.Nurturing  , Relation.Nurturing   },  // Water
            { Relation.Injuring   , Relation.Controlling, Relation.Injuring   , Relation.Controlling, Relation.Nurturing  , Relation.Exhausting , Relation.Nurturing  , Relation.Nurturing   },  // Thunder
            { Relation.Injuring   , Relation.Controlling, Relation.Injuring   , Relation.Controlling, Relation.Nurturing  , Relation.Exhausting , Relation.Nurturing  , Relation.Nurturing   },  // Wind
    };

    private static final int[] HoTu = {
            //  1  2  3  4  5  6  7  8  9
            -1, 0, 2, 4, 6, 7, 5, 3, 1, -1
    };

    private static final int[] LoSu = {
            //  1  2  3  4   5  6  7  8  9
            -1, 5, 1, 6, 7, -1, 0, 2, 3, 4
    };

    private LineCanvas _canvas = new LineCanvas(LINE_COUNT);
    private int _id;
}

