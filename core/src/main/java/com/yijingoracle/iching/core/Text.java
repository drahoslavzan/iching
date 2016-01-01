package com.yijingoracle.iching.core;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class Text
{
    public void renderHexagram(InputStream file, InputStream style)
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
                    throw new RuntimeException(String.format("%s: Missing hexagram attribute id", file, hexNodes.getLength()));

                int id = Integer.parseInt(attr.getNodeValue());

                Source xml = new DOMSource(node);

                StringWriter writer = new StringWriter();

                transformer.transform(xml, new StreamResult(writer));

                _hexagram[calculateHexagramIndex(id)] = writer.toString();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void renderTrigram(InputStream file, InputStream style)
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
                    throw new RuntimeException(String.format("%s: Missing trigram attribute id", file, trigNodes.getLength()));

                int id = Integer.parseInt(attr.getNodeValue());

                Source xml = new DOMSource(node);

                StringWriter writer = new StringWriter();

                transformer.transform(xml, new StreamResult(writer));

                _trigram[calculateTrigramIndex(id)] = writer.toString();
            }
        }
        catch(Exception e)
        {
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getHexagramText(int id)
    {
        return _hexagram[calculateHexagramIndex(id)];
    }

    public String getTrigramText(int id) { return _trigram[calculateTrigramIndex(id)]; }

    private int calculateHexagramIndex(int id)
    {
        if(id < 1 || id > _hexagram.length)
            throw new IndexOutOfBoundsException(String.format("Hexagram id %d is out of bounds", id));

        return id - 1;
    }

    private int calculateTrigramIndex(int id)
    {
        if(id < 1 || id > _trigram.length)
            throw new IndexOutOfBoundsException(String.format("Trigram id %d is out of bounds", id));

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

    private String [] _hexagram = new String[Hexagram.HEXAGRAM_COUNT];
    private String [] _trigram = new String[Trigram.TRIGRAM_COUNT];
}

