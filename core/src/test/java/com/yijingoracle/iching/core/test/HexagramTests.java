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

    private void checkHexLines(int hex, int l1, int l2, int l3, int l4, int l5, int l6)
    {
        Hexagram hexagram = new Hexagram(hex);

        Assert.assertEquals(l1, hexagram.getLine(1));
        Assert.assertEquals(l2, hexagram.getLine(2));
        Assert.assertEquals(l3, hexagram.getLine(3));
        Assert.assertEquals(l4, hexagram.getLine(4));
        Assert.assertEquals(l5, hexagram.getLine(5));
        Assert.assertEquals(l6, hexagram.getLine(6));
    }

    @Test
    public void test_GetLine_ReturnsCorrectValuesForHexagram()
    {
        checkHexLines(1 , 1, 1, 1, 1, 1, 1);
        checkHexLines(2 , 0, 0, 0, 0, 0, 0);
        checkHexLines(3 , 1, 0, 0, 0, 1, 0);
        checkHexLines(4 , 0, 1, 0, 0, 0, 1);
        checkHexLines(5 , 1, 1, 1, 0, 1, 0);
        checkHexLines(6 , 0, 1, 0, 1, 1, 1);
        checkHexLines(7 , 0, 1, 0, 0, 0, 0);
        checkHexLines(8 , 0, 0, 0, 0, 1, 0);
        checkHexLines(9 , 1, 1, 1, 0, 1, 1);
        checkHexLines(10, 1, 1, 0, 1, 1, 1);
        checkHexLines(11, 1, 1, 1, 0, 0, 0);
        checkHexLines(12, 0, 0, 0, 1, 1, 1);
        checkHexLines(13, 1, 0, 1, 1, 1, 1);
        checkHexLines(14, 1, 1, 1, 1, 0, 1);
        checkHexLines(15, 0, 0, 1, 0, 0, 0);
        checkHexLines(16, 0, 0, 0, 1, 0, 0);
        checkHexLines(17, 1, 0, 0, 1, 1, 0);
        checkHexLines(18, 0, 1, 1, 0, 0, 1);
        checkHexLines(19, 1, 1, 0, 0, 0, 0);
        checkHexLines(20, 0, 0, 0, 0, 1, 1);
        checkHexLines(21, 1, 0, 0, 1, 0, 1);
        checkHexLines(22, 1, 0, 1, 0, 0, 1);
        checkHexLines(23, 0, 0, 0, 0, 0, 1);
        checkHexLines(24, 1, 0, 0, 0, 0, 0);
        checkHexLines(25, 1, 0, 0, 1, 1, 1);
        checkHexLines(26, 1, 1, 1, 0, 0, 1);
        checkHexLines(27, 1, 0, 0, 0, 0, 1);
        checkHexLines(28, 0, 1, 1, 1, 1, 0);
        checkHexLines(29, 0, 1, 0, 0, 1, 0);
        checkHexLines(30, 1, 0, 1, 1, 0, 1);
        checkHexLines(31, 0, 0, 1, 1, 1, 0);
        checkHexLines(32, 0, 1, 1, 1, 0, 0);
        checkHexLines(33, 0, 0, 1, 1, 1, 1);
        checkHexLines(34, 1, 1, 1, 1, 0, 0);
        checkHexLines(35, 0, 0, 0, 1, 0, 1);
        checkHexLines(36, 1, 0, 1, 0, 0, 0);
        checkHexLines(37, 1, 0, 1, 0, 1, 1);
        checkHexLines(38, 1, 1, 0, 1, 0, 1);
        checkHexLines(39, 0, 0, 1, 0, 1, 0);
        checkHexLines(40, 0, 1, 0, 1, 0, 0);
        checkHexLines(41, 1, 1, 0, 0, 0, 1);
        checkHexLines(42, 1, 0, 0, 0, 1, 1);
        checkHexLines(43, 1, 1, 1, 1, 1, 0);
        checkHexLines(44, 0, 1, 1, 1, 1, 1);
        checkHexLines(45, 0, 0, 0, 1, 1, 0);
        checkHexLines(46, 0, 1, 1, 0, 0, 0);
        checkHexLines(47, 0, 1, 0, 1, 1, 0);
        checkHexLines(48, 0, 1, 1, 0, 1, 0);
        checkHexLines(49, 1, 0, 1, 1, 1, 0);
        checkHexLines(50, 0, 1, 1, 1, 0, 1);
        checkHexLines(51, 1, 0, 0, 1, 0, 0);
        checkHexLines(52, 0, 0, 1, 0, 0, 1);
        checkHexLines(53, 0, 0, 1, 0, 1, 1);
        checkHexLines(54, 1, 1, 0, 1, 0, 0);
        checkHexLines(55, 1, 0, 1, 1, 0, 0);
        checkHexLines(56, 0, 0, 1, 1, 0, 1);
        checkHexLines(57, 0, 1, 1, 0, 1, 1);
        checkHexLines(58, 1, 1, 0, 1, 1, 0);
        checkHexLines(59, 0, 1, 0, 0, 1, 1);
        checkHexLines(60, 1, 1, 0, 0, 1, 0);
        checkHexLines(61, 1, 1, 0, 0, 1, 1);
        checkHexLines(62, 0, 0, 1, 1, 0, 0);
        checkHexLines(63, 1, 0, 1, 0, 1, 0);
        checkHexLines(64, 0, 1, 0, 1, 0, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_GetLine_ThrowsForLineZero()
    {
        Hexagram hexagram = new Hexagram(1);
        hexagram.getLine(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void test_GetLine_ThrowsForLineSeven()
    {
        Hexagram hexagram = new Hexagram(1);
        hexagram.getLine(7);
    }

    private void checkHexagramChanged(int i)
    {
        Hexagram hexagram = new Hexagram(i);
        Hexagram changed = hexagram.getChangingHexagram();

        Assert.assertEquals(hexagram.getId(), changed.getId());
    }

    @Test
    public void test_GetChangingHexagram_ReturnsSameHexagramIfNoLineWasChanged()
    {
        for (int i = 1; i <= Hexagram.COUNT; ++i)
            checkHexagramChanged(i);
    }

    @Test
    public void test_GetChangingHexagram_ReturnsCorrectlyChangedHexagram_38_Line_3()
    {
        Hexagram hexagram = new Hexagram(38);
        hexagram.changeLine(3);

        Hexagram changed = hexagram.getChangingHexagram();

        Assert.assertEquals(14, changed.getId());
    }
}
