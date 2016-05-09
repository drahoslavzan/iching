package com.goiching.iching.app;

import com.goiching.iching.core.Const;
import com.goiching.iching.core.Text;
import com.goiching.iching.core.util.Dialog;
import javafx.application.Platform;

import java.io.File;
import java.io.InputStream;
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
            File[] fList = FileLoader.loadFilesFromDirectoryRelativeToJar(Const.TEXT_PATH, Const.TEXT_SUFFIX);

            if (fList == null)
            {
                return ret;
            }

            for (File file : fList)
            {
                Text t = getTextFromFile(file);

                if (t != null)
                {
                    ret.add(t);
                }
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }

        return ret;
    }

    public Text installText(File file)
    {
        try
        {
            if (!file.getName().endsWith(Const.TEXT_SUFFIX))
                throw new RuntimeException(String.format("File does not end with '%s' suffix", Const.TEXT_SUFFIX));

            Text text = getTextFromFile(file);

            if (text != null)
            {
                copyText(file);
                return text;
            }
        }
        catch (Exception e)
        {
        }

        throw new RuntimeException("Not a valid text");
    }

    public boolean isTextFile(File file)
    {
        try
        {
            if (!file.getName().endsWith(Const.TEXT_SUFFIX))
                throw new RuntimeException(String.format("File does not end with '%s' suffix", Const.TEXT_SUFFIX));

            Text text = getTextFromFile(file);

            if (text != null)
                return true;
        }
        catch (Exception e)
        {
            // Ignored
        }

        return false;
    }

    private Text getTextFromFile(File file)
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

            text.setName(name == null ? file.getName() : name);
            text.renderHexagrams(xmlHexagrams, xslHexagram);
            text.renderTrigrams(xmlTrigrams, xslTrigram);

            return text;
        }
        catch (Exception e)
        {
            Platform.runLater(() -> Dialog.showException(e, file.toString()));
        }

        return null;
    }

    private static void copyText(File file)
    {
        FileLoader.copyFileToDirectoryRelativeToJar(file, Const.TEXT_PATH);
    }

}
