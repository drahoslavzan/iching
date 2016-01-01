package com.yijingoracle.iching.app;

import java.io.InputStream;
import com.yijingoracle.iching.core.*;


public final class TextFactory
{
    public Text getText()
    {
        Text text = new Text();

        InputStream xslHexagram = getClass().getResourceAsStream("/text/hexagram.xsl");
        InputStream xslTrigram = getClass().getResourceAsStream("/text/trigram.xsl");
        InputStream hex = getClass().getResourceAsStream("/text/hexagrams.xml");
        InputStream tri = getClass().getResourceAsStream("/text/trigrams.xml");

        text.renderHexagrams(hex, xslHexagram);
        text.renderTrigrams(tri, xslTrigram);

        return text;
    }
}
