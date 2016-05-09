package com.goiching.iching.core;

import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;
import javax.xml.XMLConstants;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Text
{
    public void renderHexagrams(InputStream file, InputStream style)
    {
        try
        {
            InputStream schema = getHexagramSchema();

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.parse(file);

            validateDocument(xmlDoc, schema);

            NodeList hexNodes = xmlDoc.getElementsByTagName("hexagram");

            if(hexNodes.getLength() != _hexagram.length)
                throw new RuntimeException(String.format("%s: Invalid number of hexagram nodes (%d)", file, hexNodes.getLength()));

            Source xsl = new StreamSource(style);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(xsl);

            for(int i = 0; i < hexNodes.getLength(); ++i)
            {
                Node node = hexNodes.item(i);
                Node attr = node.getAttributes().getNamedItem("id");

                if(attr == null)
                    throw new RuntimeException(String.format("%s: Missing hexagram attribute id", file));

                int id = Integer.parseInt(attr.getNodeValue());

                Source xml = new DOMSource(node);

                StringWriter writer = new StringWriter();

                transformer.transform(xml, new StreamResult(writer));

                _hexagram[calculateHexagramIndex(id)] = writer.toString();

                NodeList name = ((Element)node).getElementsByTagName("title");

                if(name.getLength() < 1)
                    throw new RuntimeException(String.format("%s: Missing title for hexagram %d", file, id));

                _hexagramName[calculateHexagramIndex(id)] = name.item(0).getTextContent();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void renderTrigrams(InputStream file, InputStream style)
    {
        try
        {
            InputStream schema = getTrigramSchema();

            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            Document xmlDoc = docBuilder.parse(file);

            validateDocument(xmlDoc, schema);

            NodeList trigNodes = xmlDoc.getElementsByTagName("trigram");

            if(trigNodes.getLength() != _trigram.length)
                throw new RuntimeException(String.format("%s: Invalid number of trigram nodes (%d)", file, trigNodes.getLength()));

            Source xsl = new StreamSource(style);
            TransformerFactory tFactory = TransformerFactory.newInstance();
            Transformer transformer = tFactory.newTransformer(xsl);

            for(int i = 0; i < trigNodes.getLength(); ++i)
            {
                Node node = trigNodes.item(i);
                Node attr = node.getAttributes().getNamedItem("id");

                if(attr == null)
                    throw new RuntimeException(String.format("%s: Missing trigram attribute id", file));

                Trigram.Name id = Trigram.Name.valueOf(attr.getNodeValue());

                Source xml = new DOMSource(node);

                StringWriter writer = new StringWriter();

                transformer.transform(xml, new StreamResult(writer));

                _trigram[id.getId()] = writer.toString();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void setName(String name) { _name = name; }

    public String getName() { return _name; }

    public String getHexagramTitle(int id) { return _hexagramName[calculateHexagramIndex(id)]; }

    public String getHexagramText(int id)
    {
        return _hexagram[calculateHexagramIndex(id)];
    }

    public String getTrigramText(Trigram.Name name) { return _trigram[name.getId()]; }

    public Text()
    {
        _name = "Empty";

        for (int i = 0; i < _hexagram.length; i++)
        {
            _hexagram[i] = getHtmlDefaultText("Hexagram " + (i + 1));
            _hexagramName[i] = "Hexagram";
        }

        for (Trigram.Name name : Trigram.Name.values())
        {
            _trigram[name.getId()] = getHtmlDefaultText("Trigram " + name);
        }
    }

    private String getHtmlDefaultText(String name)
    {
        String url = Const.SITE_TEXTS;
        return String.format(
                "<html><body>" +
                    "<h1>%s</h1><p>The text is missing.</p>" +
                    "<p>For more information how to get texts please visit <a href=\"%s\">%s</a>.</p>" +
                "</body></html>", name, url, url);
    }

    private int calculateHexagramIndex(int id)
    {
        if(id < 1 || id > _hexagram.length)
            throw new IndexOutOfBoundsException(String.format("Hexagram id %d is out of bounds", id));

        return id - 1;
    }

    private void validateDocument(Document doc, InputStream schema)
    {
        try
        {
            SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Source schemaFile = new StreamSource(schema);
            Schema sch = schemaFactory.newSchema(schemaFile);

            Validator validator = sch.newValidator();
            validator.validate(new DOMSource(doc));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    private InputStream getHexagramSchema()
    {
        return getClass().getResourceAsStream("/text/hexagram.xsd");
    }

    private InputStream getTrigramSchema()
    {
        return getClass().getResourceAsStream("/text/trigram.xsd");
    }

    private String [] _hexagram = new String[Hexagram.COUNT];
    private String [] _hexagramName = new String[Hexagram.COUNT];
    private String [] _trigram = new String[Trigram.COUNT];
    private String _name;
}

