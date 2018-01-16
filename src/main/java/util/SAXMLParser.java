package util;

import config.Config;
import entity.Item;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;

public class SAXMLParser {
    public static void main(String[] args) {
        try {
            //SAXParserFactory spf = SAXParserFactory.newInstance();
            //SAXParser sp = spf.newSAXParser();

            File mainFolder = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder");
            if (!mainFolder.exists()) {
                if (mainFolder.mkdir()) {
                    System.out.println("Created: " + mainFolder.toString());
                }
            }

            //NexwayHandler handler = new NexwayHandler();

            //XMLReader xr = sp.getXMLReader();

            //xr.setContentHandler(handler);

            //InputSource inputSource = new InputSource(Config.feedURL2);
            //inputSource.setEncoding("UTF-8");

            //xr.parse(inputSource);
            //InputStream inputStream = new InputStream(Config.feedURL);

            NexwayParser parser = new NexwayParser();
            ArrayList<Item> items = parser.parseXml(new InputSource(Config.feedURL));


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
