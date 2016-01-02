package com.yijingoracle.iching.core.test;

import org.junit.Test;
import org.junit.Assert;
import com.yijingoracle.iching.core.Trigram;


public class TrigramTests
{
    @Test
    public void test_TrigramNameEnumHasConsistentValues()
    {
        Assert.assertEquals(0, Trigram.Name.Heaven.getId());
        Assert.assertEquals(1, Trigram.Name.Earth.getId());
        Assert.assertEquals(2, Trigram.Name.Lake.getId());
        Assert.assertEquals(3, Trigram.Name.Mountain.getId());
        Assert.assertEquals(4, Trigram.Name.Fire.getId());
        Assert.assertEquals(5, Trigram.Name.Water.getId());
        Assert.assertEquals(6, Trigram.Name.Thunder.getId());
        Assert.assertEquals(7, Trigram.Name.Wind.getId());
    }

    @Test
    public void test_GetName_ReturnsNameProvidedInConstructor()
    {
        Trigram trigram = new Trigram(Trigram.Name.Earth);
        Assert.assertEquals(Trigram.Name.Earth, trigram.getName());
    }

    @Test
    public void test_GetEarlyHeavenValue_ReturnsCorrectValue()
    {
        Assert.assertEquals(1, new Trigram(Trigram.Name.Heaven).getEarlyHeavenValue());
        Assert.assertEquals(2, new Trigram(Trigram.Name.Lake).getEarlyHeavenValue());
        Assert.assertEquals(3, new Trigram(Trigram.Name.Fire).getEarlyHeavenValue());
        Assert.assertEquals(4, new Trigram(Trigram.Name.Thunder).getEarlyHeavenValue());
        Assert.assertEquals(5, new Trigram(Trigram.Name.Wind).getEarlyHeavenValue());
        Assert.assertEquals(6, new Trigram(Trigram.Name.Water).getEarlyHeavenValue());
        Assert.assertEquals(7, new Trigram(Trigram.Name.Mountain).getEarlyHeavenValue());
        Assert.assertEquals(8, new Trigram(Trigram.Name.Earth).getEarlyHeavenValue());
    }

    @Test
    public void test_GetLateHeavenValue_ReturnsCorrectValue()
    {
        Assert.assertEquals(1, new Trigram(Trigram.Name.Water).getLateHeavenValue());
        Assert.assertEquals(2, new Trigram(Trigram.Name.Earth).getLateHeavenValue());
        Assert.assertEquals(3, new Trigram(Trigram.Name.Thunder).getLateHeavenValue());
        Assert.assertEquals(4, new Trigram(Trigram.Name.Wind).getLateHeavenValue());
        Assert.assertEquals(6, new Trigram(Trigram.Name.Heaven).getLateHeavenValue());
        Assert.assertEquals(7, new Trigram(Trigram.Name.Lake).getLateHeavenValue());
        Assert.assertEquals(8, new Trigram(Trigram.Name.Mountain).getLateHeavenValue());
        Assert.assertEquals(9, new Trigram(Trigram.Name.Fire).getLateHeavenValue());
    }

    @Test
    public void test_GetNameFromEarlyHeavenValue_ReturnsCorrectTrigramName()
    {
        Assert.assertEquals(Trigram.Name.Heaven, Trigram.getNameFromEarlyHeavenValue(1));
        Assert.assertEquals(Trigram.Name.Lake, Trigram.getNameFromEarlyHeavenValue(2));
        Assert.assertEquals(Trigram.Name.Fire, Trigram.getNameFromEarlyHeavenValue(3));
        Assert.assertEquals(Trigram.Name.Thunder, Trigram.getNameFromEarlyHeavenValue(4));
        Assert.assertEquals(Trigram.Name.Wind, Trigram.getNameFromEarlyHeavenValue(5));
        Assert.assertEquals(Trigram.Name.Water, Trigram.getNameFromEarlyHeavenValue(6));
        Assert.assertEquals(Trigram.Name.Mountain, Trigram.getNameFromEarlyHeavenValue(7));
        Assert.assertEquals(Trigram.Name.Earth, Trigram.getNameFromEarlyHeavenValue(8));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_GetNameFromEarlyHeavenValue_Throws_WhenValueZeroProvided()
    {
        Trigram.getNameFromEarlyHeavenValue(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_GetNameFromEarlyHeavenValue_Throws_WhenValueNineProvided()
    {
        Trigram.getNameFromEarlyHeavenValue(9);
    }

    @Test
    public void test_GetNameFromLaterHeavenValue_ReturnsCorrectTrigramName()
    {
        Assert.assertEquals(Trigram.Name.Water, Trigram.getNameFromLaterHeavenValue(1));
        Assert.assertEquals(Trigram.Name.Earth, Trigram.getNameFromLaterHeavenValue(2));
        Assert.assertEquals(Trigram.Name.Thunder, Trigram.getNameFromLaterHeavenValue(3));
        Assert.assertEquals(Trigram.Name.Wind, Trigram.getNameFromLaterHeavenValue(4));
        Assert.assertEquals(Trigram.Name.Heaven, Trigram.getNameFromLaterHeavenValue(6));
        Assert.assertEquals(Trigram.Name.Lake, Trigram.getNameFromLaterHeavenValue(7));
        Assert.assertEquals(Trigram.Name.Mountain, Trigram.getNameFromLaterHeavenValue(8));
        Assert.assertEquals(Trigram.Name.Fire, Trigram.getNameFromLaterHeavenValue(9));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_GetNameFromLaterHeavenValue_Throws_WhenValueZeroProvided()
    {
        Trigram.getNameFromLaterHeavenValue(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_GetNameFromLaterHeavenValue_Throws_WhenValueFiveProvided()
    {
        Trigram.getNameFromLaterHeavenValue(5);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_GetNameFromLaterHeavenValue_Throws_WhenValueTenProvided()
    {
        Trigram.getNameFromLaterHeavenValue(10);
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramHeaven()
    {
        Trigram heaven = new Trigram(Trigram.Name.Heaven);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramLake()
    {
        Trigram heaven = new Trigram(Trigram.Name.Lake);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramEarth()
    {
        Trigram heaven = new Trigram(Trigram.Name.Earth);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramMountain()
    {
        Trigram heaven = new Trigram(Trigram.Name.Mountain);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramWater()
    {
        Trigram heaven = new Trigram(Trigram.Name.Water);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramFire()
    {
        Trigram heaven = new Trigram(Trigram.Name.Fire);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramWind()
    {
        Trigram heaven = new Trigram(Trigram.Name.Wind);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
    }

    @Test
    public void test_GetRelationToTrigram_ReturnsCorrectRelation_ForTrigramThunder()
    {
        Trigram heaven = new Trigram(Trigram.Name.Thunder);

        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Wind)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Thunder)));
        Assert.assertEquals(Trigram.Relation.Nurturing, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Fire)));
        Assert.assertEquals(Trigram.Relation.Exhausting, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Water)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Earth)));
        Assert.assertEquals(Trigram.Relation.Controlling, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Mountain)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Heaven)));
        Assert.assertEquals(Trigram.Relation.Injuring, heaven.getRelationToTrigram(new Trigram(Trigram.Name.Lake)));
    }
}
