package util;

import config.Config;
import entity.Item;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;

public class NexwayParser {
    public ArrayList<Item> parseXml(InputSource inputSource) {

        ArrayList<Item> items = new ArrayList<Item>();
        try {
            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            NexwayHandler nexwayHandler = new NexwayHandler();

            XMLReader xmlReader = sp.getXMLReader();

            xmlReader.setContentHandler(nexwayHandler);

            //InputSource inputSource = new InputSource(in);
            inputSource.setEncoding("UTF-8");

            xmlReader.parse(inputSource);

            items = nexwayHandler.getItems();

        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return items;
    }
}