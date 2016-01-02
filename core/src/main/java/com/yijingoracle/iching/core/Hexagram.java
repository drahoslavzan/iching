package com.yijingoracle.iching.core;


public class Hexagram
{
    public class Decomposition
    {
        public Decomposition(Trigram.Name ut, Trigram.Name uct, Trigram.Name lct, Trigram.Name lt)
        {
            _ut = ut;
            _uct = uct;
            _lct = lct;
            _lt = lt;
        }

        public Trigram.Name getUpperTrigram() { return _ut; }
        public Trigram.Name getUpperCentralTrigram() { return _uct; }
        public Trigram.Name getLowerCentralTrigram() { return _lct; }
        public Trigram.Name getLowerTrigram() { return _lt; }

        private Trigram.Name _ut;
        private Trigram.Name _uct;
        private Trigram.Name _lct;
        private Trigram.Name _lt;
    };

    public static final int COUNT = 64;
    public static final int LINES = 6;

    public static int getHexagramIdFromTrigrams(Trigram.Name top, Trigram.Name bottom)
    {
        return TRIGRAM_MAPPING_TABLE[bottom.getId()][top.getId()];
    }

    public Hexagram(int id)
    {
       if (id < 1 || id > COUNT)
           throw new IndexOutOfBoundsException(String.format("Invalid value for hexagram (%d)", id));

       _id = id;
    }

    public int getId()
    {
        return _id;
    }

    public Decomposition decompose()
    {
        Trigram.Name[] decs = HEXAGRAM_DECOMPOSITION_MAPPING_TABLE[_id - 1];

        return new Decomposition(decs[0], decs[1], decs[2], decs[3]);
    }

    public void changeLine(int line)
    {
        changeLine(line, true);
    }

    public void unchangeLine(int line)
    {
        changeLine(line, false);
    }

    public boolean lineChanged(int line)
    {
        if(line < 1 || line > LINES)
            throw new IndexOutOfBoundsException(String.format("Hexagram line %d is out of bounds", line));

        return _changedLines[line - 1];
    }

    private void changeLine(int line, boolean mark)
    {
        if(line < 1 || line > LINES)
            throw new IndexOutOfBoundsException(String.format("Hexagram line %d is out of bounds", line));

        _changedLines[line - 1] = mark;
    }

    private static final Trigram.Name[][] HEXAGRAM_DECOMPOSITION_MAPPING_TABLE = {
            // 1 - 8
            { Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven },
            { Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth },
            { Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder },
            { Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water },
            { Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven },
            { Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water },
            { Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water },
            { Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth },

            // 9 - 16
            { Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven },
            { Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake },
            { Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven },
            { Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth },
            { Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire },
            { Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven },
            { Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain },
            { Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth },

            // 17 - 24
            { Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder },
            { Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind },
            { Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake },
            { Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth },
            { Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder },
            { Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire },
            { Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth },
            { Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder },

            // 25 - 32
            { Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder },
            { Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven },
            { Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder },
            { Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind },
            { Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water },
            { Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire },
            { Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain },
            { Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind },

            // 33 - 40
            { Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain },
            { Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven },
            { Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth },
            { Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire },
            { Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire },
            { Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake },
            { Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain },
            { Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water },

            // 41 - 48
            { Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake },
            { Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder },
            { Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven },
            { Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind },
            { Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth },
            { Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind },
            { Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water },
            { Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind },

            // 49 - 56
            { Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire },
            { Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind },
            { Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder },
            { Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain },
            { Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain },
            { Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake },
            { Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire },
            { Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain },

            // 57 - 64
            { Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind },
            { Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake },
            { Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water },
            { Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake },
            { Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake },
            { Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain },
            { Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire },
            { Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water },
    };

    private static final int[][] TRIGRAM_MAPPING_TABLE = {
            { 1 , 11, 43, 26, 14, 5 , 34, 9  },
            { 12, 2 , 45, 23, 35, 8 , 16, 20 },
            { 10, 19, 58, 41, 38, 60, 54, 61 },
            { 33, 15, 31, 52, 56, 39, 62, 53 },
            { 13, 36, 49, 22, 30, 63, 55, 37 },
            { 6 , 7 , 47, 4 , 64, 29, 40, 59 },
            { 25, 24, 17, 27, 21, 3 , 51, 42 },
            { 44, 46, 28, 18, 50, 48, 32, 57 },
    };

    private int _id;
    private boolean[] _changedLines = new boolean[LINES];
}
