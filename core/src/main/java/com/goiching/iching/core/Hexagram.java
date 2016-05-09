package com.goiching.iching.core;

import java.util.HashMap;


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
    }

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
        int i = getLineIndexWithGuard(line);

        return _changedLines[i];
    }

    public int getLine(int line)
    {
        int i = getLineIndexWithGuard(line);
        int[] table = HEXAGRAM_TO_LINES[_id - 1];

        return table[i];
    }

    public Hexagram getChangingHexagram()
    {
        int[] table = HEXAGRAM_TO_LINES[_id - 1];

        assert table.length == LINES;

        StringBuilder key = new StringBuilder(table.length);

        for (int i = 0; i < LINES; ++i)
            key.append(table[i] ^ (_changedLines[i] ? 1 : 0));

        Hexagram ret = new Hexagram(LINES_TO_HEXAGRAM.get(key.toString()));

        System.arraycopy(_changedLines, 0, ret._changedLines, 0, _changedLines.length);

        return ret;
    }

    private int getLineIndexWithGuard(int line)
    {
        if(line < 1 || line > LINES)
            throw new IndexOutOfBoundsException(String.format("Hexagram line %d is out of bounds", line));

        return line - 1;
    }

    private void changeLine(int line, boolean mark)
    {
        int i = getLineIndexWithGuard(line);

        _changedLines[i] = mark;
    }

    private static final HashMap<String, Integer> LINES_TO_HEXAGRAM = new HashMap<>();

    static
    {
        LINES_TO_HEXAGRAM.put("111111", 1);
        LINES_TO_HEXAGRAM.put("000000", 2);
        LINES_TO_HEXAGRAM.put("100010", 3);
        LINES_TO_HEXAGRAM.put("010001", 4);
        LINES_TO_HEXAGRAM.put("111010", 5);
        LINES_TO_HEXAGRAM.put("010111", 6);
        LINES_TO_HEXAGRAM.put("010000", 7);
        LINES_TO_HEXAGRAM.put("000010", 8);
        LINES_TO_HEXAGRAM.put("111011", 9);
        LINES_TO_HEXAGRAM.put("110111", 10);
        LINES_TO_HEXAGRAM.put("111000", 11);
        LINES_TO_HEXAGRAM.put("000111", 12);
        LINES_TO_HEXAGRAM.put("101111", 13);
        LINES_TO_HEXAGRAM.put("111101", 14);
        LINES_TO_HEXAGRAM.put("001000", 15);
        LINES_TO_HEXAGRAM.put("000100", 16);
        LINES_TO_HEXAGRAM.put("100110", 17);
        LINES_TO_HEXAGRAM.put("011001", 18);
        LINES_TO_HEXAGRAM.put("110000", 19);
        LINES_TO_HEXAGRAM.put("000011", 20);
        LINES_TO_HEXAGRAM.put("100101", 21);
        LINES_TO_HEXAGRAM.put("101001", 22);
        LINES_TO_HEXAGRAM.put("000001", 23);
        LINES_TO_HEXAGRAM.put("100000", 24);
        LINES_TO_HEXAGRAM.put("100111", 25);
        LINES_TO_HEXAGRAM.put("111001", 26);
        LINES_TO_HEXAGRAM.put("100001", 27);
        LINES_TO_HEXAGRAM.put("011110", 28);
        LINES_TO_HEXAGRAM.put("010010", 29);
        LINES_TO_HEXAGRAM.put("101101", 30);
        LINES_TO_HEXAGRAM.put("001110", 31);
        LINES_TO_HEXAGRAM.put("011100", 32);
        LINES_TO_HEXAGRAM.put("001111", 33);
        LINES_TO_HEXAGRAM.put("111100", 34);
        LINES_TO_HEXAGRAM.put("000101", 35);
        LINES_TO_HEXAGRAM.put("101000", 36);
        LINES_TO_HEXAGRAM.put("101011", 37);
        LINES_TO_HEXAGRAM.put("110101", 38);
        LINES_TO_HEXAGRAM.put("001010", 39);
        LINES_TO_HEXAGRAM.put("010100", 40);
        LINES_TO_HEXAGRAM.put("110001", 41);
        LINES_TO_HEXAGRAM.put("100011", 42);
        LINES_TO_HEXAGRAM.put("111110", 43);
        LINES_TO_HEXAGRAM.put("011111", 44);
        LINES_TO_HEXAGRAM.put("000110", 45);
        LINES_TO_HEXAGRAM.put("011000", 46);
        LINES_TO_HEXAGRAM.put("010110", 47);
        LINES_TO_HEXAGRAM.put("011010", 48);
        LINES_TO_HEXAGRAM.put("101110", 49);
        LINES_TO_HEXAGRAM.put("011101", 50);
        LINES_TO_HEXAGRAM.put("100100", 51);
        LINES_TO_HEXAGRAM.put("001001", 52);
        LINES_TO_HEXAGRAM.put("001011", 53);
        LINES_TO_HEXAGRAM.put("110100", 54);
        LINES_TO_HEXAGRAM.put("101100", 55);
        LINES_TO_HEXAGRAM.put("001101", 56);
        LINES_TO_HEXAGRAM.put("011011", 57);
        LINES_TO_HEXAGRAM.put("110110", 58);
        LINES_TO_HEXAGRAM.put("010011", 59);
        LINES_TO_HEXAGRAM.put("110010", 60);
        LINES_TO_HEXAGRAM.put("110011", 61);
        LINES_TO_HEXAGRAM.put("001100", 62);
        LINES_TO_HEXAGRAM.put("101010", 63);
        LINES_TO_HEXAGRAM.put("010101", 64);
    }

    private static final int[][] HEXAGRAM_TO_LINES = {
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
