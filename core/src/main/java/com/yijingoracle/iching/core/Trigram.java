package com.yijingoracle.iching.core;


public class Trigram
{
    public static final int COUNT = 8;
    public static final int LINES = 3;

    public enum Relation
    {
        Nurturing (1),
        Exhausting (0),
        Controlling (-1),
        Injuring (-1);

        Relation(int eval) { _eval = eval; }

        private int _eval;
    };

    public enum Name
    {
        Heaven (0),
        Earth (1),
        Lake (2),
        Mountain (3),
        Fire (4),
        Water (5),
        Thunder (6),
        Wind (7);

        Name(int id) { _id = id; }

        public int getId() { return _id; }

        private int _id;
    };

    public static Name getNameFromEarlyHeavenValue(int i)
    {
        return getNameFromNameArrayWithGuardingIndex(i, HOTU_NAME);
    }

    public static Name getNameFromLaterHeavenValue(int i)
    {
        return getNameFromNameArrayWithGuardingIndex(i, LOSU_NAME);
    }

    public Trigram(Name name)
    {
        if (name == null)
            throw new NullPointerException("Name from trigram required");

        _name = name;
    }

    public Name getName()
    {
        return _name;
    }

    public int getEarlyHeavenValue()
    {
        return HOTU_VALUE[getName().getId()];
    }

    public int getLateHeavenValue()
    {
        return HOTU_VALUE[getName().getId()];
    }

    public Relation getRelationToTrigram(Trigram trigram)
    {
        int i = getName().getId();
        int j = trigram.getName().getId();

        return RELATION[i][j];
    }

    private static Name getNameFromNameArrayWithGuardingIndex(int i, Name[] array)
    {
        --i;

        if (i < 0 || i >= array.length || array[i] == null)
            throw new IndexOutOfBoundsException(String.format("Index %d is out of bounds", i));

        return array[i];
    }

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

    private static final int[] HOTU_VALUE = {
            // Heaven  Earth  Lake  Mountain  Fire  Water  Thunder  Wind
               1,      8,     2,    7,        3,    6,     4,       5
    };

    private static final Name[] HOTU_NAME = {
            Name.Heaven, Name.Lake, Name.Fire, Name.Thunder, Name.Wind, Name.Water, Name.Mountain, Name.Earth
    };

    private static final int[] LOSU_VALUE = {
            // Heaven  Earth  Lake  Mountain  Fire  Water  Thunder  Wind
               6,      2,     7,    8,        9,    1,     3,       4
    };

    private static final Name[] LOSU_NAME = {
            Name.Water, Name.Earth, Name.Thunder, Name.Wind, null, Name.Heaven, Name.Lake, Name.Mountain, Name.Fire
    };

    private Name _name;
}
