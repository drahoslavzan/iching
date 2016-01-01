package com.yijingoracle.iching.core;

import javafx.scene.paint.Color;
import javafx.scene.layout.Region;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.geometry.Orientation;


public class Hexagram extends Region
{
    public class HexagramDecomposition
    {
        public HexagramDecomposition(int ut, int uct, int lct, int lt)
        {
            _ut = ut;
            _uct = uct;
            _lct = lct;
            _lt = lt;
        }

        public int getUpperTrigramId() { return _ut; }
        public int getUpperCentralTrigramId() { return _uct; }
        public int getLowerCentralTrigramId() { return _lct; }
        public int getLowerTrigramId() { return _lt; }

        private int _ut;
        private int _uct;
        private int _lct;
        private int _lt;
    };

    public static final int HEXAGRAM_COUNT = 64;

    public static int getHexagramIdFromTrigrams(int top, int bottom)
    {
        if(top < 1 || top > Trigram.TRIGRAM_COUNT)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", top));
        if(bottom < 1 || bottom > Trigram.TRIGRAM_COUNT)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", bottom));

        return TRIGRAM_MAPPING_TABLE[bottom-1][top-1];
    }

    public Hexagram() { this(ID_BLANK); }

    public Hexagram(int id)
    {
        getChildren().add(_canvas);

        setHexagramId(id);
    }

    public HexagramDecomposition decompose()
    {
        if (_id == ID_BLANK)
            return new HexagramDecomposition(0, 0, 0, 0);

        int[] decs = HEXAGRAM_DECOMPOSITION_MAPPING_TABLE[_id];

        return new HexagramDecomposition(decs[0], decs[1], decs[2], decs[3]);
    }

    public void markLine(int line, boolean mark)
    {
        if(line < 1 || line > LINE_COUNT)
            throw new IndexOutOfBoundsException(String.format("Hexagram line %d is out of bounds", line));

        _markedLine[line - 1] = mark;

        redraw();
    }

    public int getHexagramId()
    {
        return _id;
    }

    private void setHexagramId(int id)
    {
        if(id < 0 || id > HEXAGRAM_COUNT)
            throw new IndexOutOfBoundsException(String.format("Hexagram id %d is out of bounds", id));

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

        if(_id == ID_BLANK)
            return;

        int[] hex = TABLE[_id - 1];

        drawHexagram(hex);
        drawMarkedLines(hex);
    }

    private void drawHexagram(int[] hex)
    {
        _canvas.setLineProperties(1, Color.BLACK);

        for(int i = 0; i < hex.length; ++i)
        {
            if(hex[i] > 0)
                _canvas.drawYangLine(i);
            else
                _canvas.drawYinLine(i);
        }
    }

    private void drawMarkedLines(int[] hex)
    {
        _canvas.setLineProperties(MOVE_LINE_SIZE, Color.RED);

        for(int i = 0; i < _markedLine.length; ++i)
        {
            if(_markedLine[i])
            {
                if(hex[i] > 0)
                    _canvas.circleLine(i);
                else
                    _canvas.crossLine(i);
            }
        }
    }

    private static final int LINE_COUNT = 6;
    private static final int MOVE_LINE_SIZE = 5;
    private static final double ASPECT_RATIO = 1.0;
    private static final int ID_BLANK = 0;

    private static final int[][] TABLE = {
            // 1 - 8
            { 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0 },
            { 1, 0, 0, 0, 1, 0 },
            { 0, 1, 0, 0, 0, 1 },
            { 1, 1, 1, 0, 1, 0 },
            { 0, 1, 0, 1, 1, 1 },
            { 0, 1, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 0 },

            // 9 - 16
            { 1, 1, 1, 0, 1, 1 },
            { 1, 1, 0, 1, 1, 1 },
            { 1, 1, 1, 0, 0, 0 },
            { 0, 0, 0, 1, 1, 1 },
            { 1, 0, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 0, 1 },
            { 0, 0, 1, 0, 0, 0 },
            { 0, 0, 0, 1, 0, 0 },

            // 17 - 24
            { 1, 0, 0, 1, 1, 0 },
            { 0, 1, 1, 0, 0, 1 },
            { 1, 1, 0, 0, 0, 0 },
            { 0, 0, 0, 0, 1, 1 },
            { 1, 0, 0, 1, 0, 1 },
            { 1, 0, 1, 0, 0, 1 },
            { 0, 0, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 0 },

            // 25 - 32
            { 1, 0, 0, 1, 1, 1 },
            { 1, 1, 1, 0, 0, 1 },
            { 1, 0, 0, 0, 0, 1 },
            { 0, 1, 1, 1, 1, 0 },
            { 0, 1, 0, 0, 1, 0 },
            { 1, 0, 1, 1, 0, 1 },
            { 0, 0, 1, 1, 1, 0 },
            { 0, 1, 1, 1, 0, 0 },

            // 33 - 40
            { 0, 0, 1, 1, 1, 1 },
            { 1, 1, 1, 1, 0, 0 },
            { 0, 0, 0, 1, 0, 1 },
            { 1, 0, 1, 0, 0, 0 },
            { 1, 0, 1, 0, 1, 1 },
            { 1, 1, 0, 1, 0, 1 },
            { 0, 0, 1, 0, 1, 0 },
            { 0, 1, 0, 1, 0, 0 },

            // 41 - 48
            { 1, 1, 0, 0, 0, 1 },
            { 1, 0, 0, 0, 1, 1 },
            { 1, 1, 1, 1, 1, 0 },
            { 0, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 1, 1, 0 },
            { 0, 1, 1, 0, 0, 0 },
            { 0, 1, 0, 1, 1, 0 },
            { 0, 1, 1, 0, 1, 0 },

            // 49 - 56
            { 1, 0, 1, 1, 1, 0 },
            { 0, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 1, 0, 0 },
            { 0, 0, 1, 0, 0, 1 },
            { 0, 0, 1, 0, 1, 1 },
            { 1, 1, 0, 1, 0, 0 },
            { 1, 0, 1, 1, 0, 0 },
            { 0, 0, 1, 1, 0, 1 },

            // 57 - 64
            { 0, 1, 1, 0, 1, 1 },
            { 1, 1, 0, 1, 1, 0 },
            { 0, 1, 0, 0, 1, 1 },
            { 1, 1, 0, 0, 1, 0 },
            { 1, 1, 0, 0, 1, 1 },
            { 0, 0, 1, 1, 0, 0 },
            { 1, 0, 1, 0, 1, 0 },
            { 0, 1, 0, 1, 0, 1 },
    };

    private static final int[][] HEXAGRAM_DECOMPOSITION_MAPPING_TABLE = {
            // 1 - 8
            { 1, 1, 1, 1 },
            { 2, 2, 2, 2 },
            { 6, 4, 2, 7 },
            { 4, 2, 7, 6 },
            { 6, 5, 3, 1 },
            { 1, 8, 5, 6 },
            { 2, 2, 7, 6 },
            { 6, 4, 2, 2 },

            // 9 - 16
            { 8, 5, 3, 1 },
            { 1, 8, 5, 3 },
            { 2, 7, 3, 1 },
            { 1, 8, 4, 2 },
            { 1, 1, 8, 5 },
            { 5, 3, 1, 1 },
            { 2, 7, 6, 4 },
            { 7, 6, 4, 2 },

            // 17 - 24
            { 3, 8, 4, 7 },
            { 4, 7, 3, 8 },
            { 2, 2, 7, 3 },
            { 8, 4, 2, 2 },
            { 5, 6, 4, 7 },
            { 4, 7, 6, 5 },
            { 4, 2, 2, 2 },
            { 2, 2, 2, 7 },

            // 25 - 32
            { 1, 8, 4, 7 },
            { 4, 7, 3, 1 },
            { 4, 2, 2, 7 },
            { 3, 1, 1, 8 },
            { 6, 4, 7, 6 },
            { 5, 3, 8, 5 },
            { 3, 1, 8, 4 },
            { 7, 3, 1, 8 },

            // 33 - 40
            { 1, 1, 8, 4 },
            { 7, 3, 1, 1 },
            { 5, 6, 4, 2 },
            { 2, 7, 6, 5 },
            { 8, 5, 6, 5 },
            { 5, 6, 5, 3 },
            { 6, 5, 6, 4 },
            { 7, 6, 5, 6 },

            // 41 - 48
            { 4, 2, 7, 3 },
            { 8, 4, 2, 7 },
            { 3, 1, 1, 1 },
            { 1, 1, 1, 8 },
            { 3, 8, 4, 2 },
            { 2, 7, 3, 8 },
            { 3, 8, 5, 6 },
            { 6, 5, 3, 8 },

            // 49 - 56
            { 3, 1, 8, 5 },
            { 5, 3, 1, 8 },
            { 7, 6, 4, 7 },
            { 4, 7, 6, 4 },
            { 8, 5, 6, 4 },
            { 7, 6, 5, 3 },
            { 7, 3, 8, 5 },
            { 5, 3, 8, 4 },

            // 57 - 64
            { 8, 5, 3, 8 },
            { 3, 8, 5, 3 },
            { 8, 4, 7, 6 },
            { 6, 4, 7, 3 },
            { 8, 4, 7, 3 },
            { 7, 3, 8, 4 },
            { 6, 5, 6, 5 },
            { 5, 6, 5, 6 },
    };

    private static final int[][] TRIGRAM_MAPPING_TABLE = {
            { 1 , 14, 9 , 26, 11, 5 , 34, 43 },
            { 13, 30, 37, 22, 36, 63, 55, 49 },
            { 44, 50, 57, 18, 46, 48, 32, 28 },
            { 33, 56, 53, 52, 15, 39, 62, 21 },
            { 12, 35, 20, 23, 2 , 8 , 16, 45 },
            { 6 , 64, 59, 4 , 7 , 29, 40, 7  },
            { 25, 21, 42, 27, 24, 3 , 51, 17 },
            { 10, 38, 61, 41, 19, 60, 54, 58 },
    };

    private LineCanvas _canvas = new LineCanvas(LINE_COUNT);
    private boolean[] _markedLine = new boolean[LINE_COUNT];
    private int _id;
}

