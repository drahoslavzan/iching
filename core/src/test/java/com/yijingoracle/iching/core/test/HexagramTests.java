package com.yijingoracle.iching.core.test;

import org.junit.Test;
import org.junit.Assert;
import com.yijingoracle.iching.core.Trigram;
import com.yijingoracle.iching.core.Hexagram;


public class HexagramTests
{
    @Test
    public void test_GetId_ReturnsIdProvidedInConstructor()
    {
        Hexagram hexagram = new Hexagram(1);
        Assert.assertEquals(1, hexagram.getId());
    }

    @Test
    public void test_LineChanged_ReturnsTrueAfterCallToChangeLine()
    {
        Hexagram hexagram = new Hexagram(1);
        hexagram.changeLine(3);
        Assert.assertTrue(hexagram.lineChanged(3));
    }

    @Test
    public void test_LineChanged_ReturnsFalseAfterUnchagedLine()
    {
        Hexagram hexagram = new Hexagram(1);
        hexagram.changeLine(3);
        hexagram.unchangeLine(3);
        Assert.assertFalse(hexagram.lineChanged(3));
    }

    @Test
    public void test_LineChanged_ReturnsFalseForAllLinesAfterConstruction()
    {
        Hexagram hexagram = new Hexagram(1);

        for (int i = 1; i < 7; ++i)
            Assert.assertFalse(hexagram.lineChanged(i));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_LineChanged_ThrowsForLineZero()
    {
        Hexagram hexagram = new Hexagram(1);
        hexagram.lineChanged(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_LineChanged_ThrowsForLineSeven()
    {
        Hexagram hexagram = new Hexagram(1);
        hexagram.lineChanged(7);
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_1_8()
    {
        Assert.assertEquals(1, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Heaven));
        Assert.assertEquals(2, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Earth));
        Assert.assertEquals(3, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Thunder));
        Assert.assertEquals(4, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Water));
        Assert.assertEquals(5, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Heaven));
        Assert.assertEquals(6, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Water));
        Assert.assertEquals(7, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Water));
        Assert.assertEquals(8, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Earth));
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_9_16()
    {
        Assert.assertEquals(9, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Heaven));
        Assert.assertEquals(10, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Lake));
        Assert.assertEquals(11, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Heaven));
        Assert.assertEquals(12, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Earth));
        Assert.assertEquals(13, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Fire));
        Assert.assertEquals(14, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Heaven));
        Assert.assertEquals(15, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Mountain));
        Assert.assertEquals(16, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Earth));
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_17_24()
    {
        Assert.assertEquals(17, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Thunder));
        Assert.assertEquals(18, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Wind));
        Assert.assertEquals(19, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Lake));
        Assert.assertEquals(20, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Earth));
        Assert.assertEquals(21, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Thunder));
        Assert.assertEquals(22, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Fire));
        Assert.assertEquals(23, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Earth));
        Assert.assertEquals(24, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Thunder));
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_25_32()
    {
        Assert.assertEquals(25, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Thunder));
        Assert.assertEquals(26, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Heaven));
        Assert.assertEquals(27, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Thunder));
        Assert.assertEquals(28, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Wind));
        Assert.assertEquals(29, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Water));
        Assert.assertEquals(30, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Fire));
        Assert.assertEquals(31, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Mountain));
        Assert.assertEquals(32, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Wind));
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_33_40()
    {
        Assert.assertEquals(33, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Mountain));
        Assert.assertEquals(34, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Heaven));
        Assert.assertEquals(35, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Earth));
        Assert.assertEquals(36, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Fire));
        Assert.assertEquals(37, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Fire));
        Assert.assertEquals(38, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Lake));
        Assert.assertEquals(39, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Mountain));
        Assert.assertEquals(40, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Water));
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_41_48()
    {
        Assert.assertEquals(41, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Lake));
        Assert.assertEquals(42, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Thunder));
        Assert.assertEquals(43, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Heaven));
        Assert.assertEquals(44, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Heaven, Trigram.Name.Wind));
        Assert.assertEquals(45, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Earth));
        Assert.assertEquals(46, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Earth, Trigram.Name.Wind));
        Assert.assertEquals(47, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Water));
        Assert.assertEquals(48, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Wind));
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_49_56()
    {
        Assert.assertEquals(49, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Fire));
        Assert.assertEquals(50, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Wind));
        Assert.assertEquals(51, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Thunder));
        Assert.assertEquals(52, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Mountain, Trigram.Name.Mountain));
        Assert.assertEquals(53, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Mountain));
        Assert.assertEquals(54, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Lake));
        Assert.assertEquals(55, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Fire));
        Assert.assertEquals(56, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Mountain));
    }

    @Test
    public void test_GetHexagramIdForTrigrams_ReturnsCorrectHexagramId_57_64()
    {
        Assert.assertEquals(57, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Wind));
        Assert.assertEquals(58, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Lake, Trigram.Name.Lake));
        Assert.assertEquals(59, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Water));
        Assert.assertEquals(60, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Lake));
        Assert.assertEquals(61, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Wind, Trigram.Name.Lake));
        Assert.assertEquals(62, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Thunder, Trigram.Name.Mountain));
        Assert.assertEquals(63, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Water, Trigram.Name.Fire));
        Assert.assertEquals(64, Hexagram.getHexagramIdFromTrigrams(Trigram.Name.Fire, Trigram.Name.Water));
    }

    private void checkDecomposition(int i, Trigram.Name ut, Trigram.Name uct, Trigram.Name lct, Trigram.Name lt)
    {
        Hexagram.Decomposition decs = new Hexagram(i).decompose();

        Assert.assertEquals(ut, decs.getUpperTrigram());
        Assert.assertEquals(uct, decs.getUpperCentralTrigram());
        Assert.assertEquals(lct, decs.getLowerCentralTrigram());
        Assert.assertEquals(lt, decs.getLowerTrigram());
    }

    @Test
    public void test_Decompose_ReturnsCorrectTrigrams()
    {
        checkDecomposition(1, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven);
        checkDecomposition(2, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth);
        checkDecomposition(3, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder);
        checkDecomposition(4, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water);
        checkDecomposition(5, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven);
        checkDecomposition(6, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water);
        checkDecomposition(7, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water);
        checkDecomposition(8, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth);
        checkDecomposition(9, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven);
        checkDecomposition(10, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake);
        checkDecomposition(11, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven);
        checkDecomposition(12, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth);
        checkDecomposition(13, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire);
        checkDecomposition(14, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven);
        checkDecomposition(15, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain);
        checkDecomposition(16, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth);
        checkDecomposition(17, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder);
        checkDecomposition(18, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind);
        checkDecomposition(19, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake);
        checkDecomposition(20, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth);
        checkDecomposition(21, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder);
        checkDecomposition(22, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire);
        checkDecomposition(23, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth);
        checkDecomposition(24, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder);
        checkDecomposition(25, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder);
        checkDecomposition(26, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven);
        checkDecomposition(27, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Earth, Trigram.Name.Thunder);
        checkDecomposition(28, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind);
        checkDecomposition(29, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water);
        checkDecomposition(30, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire);
        checkDecomposition(31, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain);
        checkDecomposition(32, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind);
        checkDecomposition(33, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Mountain);
        checkDecomposition(34, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven);
        checkDecomposition(35, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Earth);
        checkDecomposition(36, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire);
        checkDecomposition(37, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire);
        checkDecomposition(38, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake);
        checkDecomposition(39, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain);
        checkDecomposition(40, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water);
        checkDecomposition(41, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake);
        checkDecomposition(42, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth, Trigram.Name.Thunder);
        checkDecomposition(43, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven);
        checkDecomposition(44, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Heaven, Trigram.Name.Wind);
        checkDecomposition(45, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Earth);
        checkDecomposition(46, Trigram.Name.Earth, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind);
        checkDecomposition(47, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water);
        checkDecomposition(48, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind);
        checkDecomposition(49, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind, Trigram.Name.Fire);
        checkDecomposition(50, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Heaven, Trigram.Name.Wind);
        checkDecomposition(51, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder);
        checkDecomposition(52, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Mountain);
        checkDecomposition(53, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Mountain);
        checkDecomposition(54, Trigram.Name.Thunder, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Lake);
        checkDecomposition(55, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire);
        checkDecomposition(56, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain);
        checkDecomposition(57, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake, Trigram.Name.Wind);
        checkDecomposition(58, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Fire, Trigram.Name.Lake);
        checkDecomposition(59, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Water);
        checkDecomposition(60, Trigram.Name.Water, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake);
        checkDecomposition(61, Trigram.Name.Wind, Trigram.Name.Mountain, Trigram.Name.Thunder, Trigram.Name.Lake);
        checkDecomposition(62, Trigram.Name.Thunder, Trigram.Name.Lake, Trigram.Name.Wind, Trigram.Name.Mountain);
        checkDecomposition(63, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire);
        checkDecomposition(64, Trigram.Name.Fire, Trigram.Name.Water, Trigram.Name.Fire, Trigram.Name.Water);
    }
}
