package com.yijingoracle.iching.app;

import com.yijingoracle.iching.core.Const;
import com.yijingoracle.iching.core.Text;
import com.yijingoracle.iching.core.util.Dialog;
import javafx.application.Platform;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


public class TextLoader
{
    public List<Text> loadTexts()
    {
        ArrayList<Text> ret = new ArrayList<>();

        try
        {
            Path exePath = Paths.get(TextLoader.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());

            File loc = new File(exePath.getParent() + File.separator + Const.TEXT_PATH);
            File[] flist = loc.listFiles(file -> file.getPath().toLowerCase().endsWith(".zip"));

            if (flist == null)
            {
                return ret;
            }

            for (File file : flist)
            {
                Text t = processFile(file);

                if (t != null)
                {
                    ret.add(t);
                }
            }
        }
        catch (Exception e)
        {
            Platform.runLater(() -> Dialog.showException(e));
        }

        return ret;
    }

    private Text processFile(File file)
    {
        try
        {
            InputStream xslHexagram = getClass().getResourceAsStream("/text/hexagram.xsl");
            InputStream xslTrigram = getClass().getResourceAsStream("/text/trigram.xsl");

            ZipFile zipFile = new ZipFile(file);
            ZipEntry zeHexagrams = zipFile.getEntry("hexagrams.xml");
            ZipEntry zeTrigrams = zipFile.getEntry("trigrams.xml");

            if (zeHexagrams == null)
                throw new RuntimeException("Missing file 'hexagrams.xml'");

            if (zeTrigrams == null)
                throw new RuntimeException("Missing file 'trigrams.xml'");

            InputStream xmlHexagrams = zipFile.getInputStream(zeHexagrams);
            InputStream xmlTrigrams = zipFile.getInputStream(zeTrigrams);

            String name = zipFile.getComment();

            Text text = new Text();

            text.setName(name == null ? "???" : name);
            text.renderHexagrams(xmlHexagrams, xslHexagram);
            text.renderTrigrams(xmlTrigrams, xslTrigram);

            return text;
        }
        catch (Exception e)
        {
            Platform.runLater(() -> Dialog.showException(e));
        }

        return null;
    }
}
