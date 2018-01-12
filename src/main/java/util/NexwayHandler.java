package util;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class NexwayHandler extends DefaultHandler {
    boolean bname = false;
    boolean bprice = false;
    boolean bpromoprice = false;
    boolean bpromostart = false;
    boolean bpromoend = false;
    boolean bpublisher = false;
    boolean bdesc = false;

    String itemID = "";
    String itemName = "";
    String itemDescription = "";
    String itemSellPrice = "";
    String itemPromoPrice = "";
    String itemPromoStartDate ="";
    String itemPromoEndDate = "";
    String itemPublisher ="";

    String productDetail = "";

    BufferedWriter bw = null;
    FileWriter fw = null;
    PrintWriter pw = null;
    FileOutputStream fos = null;

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        //System.out.println("Start of element: "+qName);
        if(qName.equalsIgnoreCase("product")){
            itemID = attributes.getValue("id");
            //System.out.println(itemID);
        }

        if(qName.equalsIgnoreCase("name")){
            bname = true;
        }

        if(qName.equalsIgnoreCase("publisher")){
            bpublisher = true;
        }


        if(qName.equalsIgnoreCase("description")){
            if(attributes.getValue("format").equals("long") && attributes.getValue("language").equals("EN")){
                bdesc = true;
            }
        }

        if(qName.equalsIgnoreCase("public")){
            if(attributes.getValue("VATInclusive").equals("1")){
                bprice = true;
            }
        }

        if(qName.equalsIgnoreCase("startDatePromo")){
            bpromostart = true;
            if(qName.equalsIgnoreCase("sale")){
                if(attributes.getValue("VATInclusive").equals("1")){
                    bpromoprice = true;
                }
            }
        }

        if(qName.equalsIgnoreCase("endDatePromo")){
            bpromoend = true;
        }

        if(qName.equalsIgnoreCase("sale")){
            if(attributes.getValue("VATInclusive").equals("1")){
                bpromoprice = true;
            }
        }
    }

    public void endElement(String uri, String localName, String qName)throws SAXException {

        if (qName.equalsIgnoreCase("product")) {
            //System.out.println(productDetail);

        }

        if (qName.equalsIgnoreCase("description")) {
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail + "/description.html");
                if (file.createNewFile()) {
                    //System.out.println(file + " created successful!");
                }
                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\description.html"), itemDescription.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equalsIgnoreCase("public")){
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail+"/publicPrice.txt");
                if(file.createNewFile()){
                    //System.out.println(file +" created successful!");
                }
                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\publicPrice.txt"), itemSellPrice.getBytes());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equalsIgnoreCase("startDatePromo")){
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail+"/startDatePromo.txt");
                if(file.createNewFile()){
                    //System.out.println(file +" created successful!");
                }
                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\startDatePromo.txt"), itemPromoStartDate.getBytes());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equalsIgnoreCase("endDatePromo")){
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail+"/endDatePromo.txt");
                if(file.createNewFile()){
                    //System.out.println(file +" created successful!");
                }
                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\endDatePromo.txt"), itemPromoEndDate.getBytes());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equalsIgnoreCase("sale")){
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail+"/promoPrice.txt");
                if(file.createNewFile()){
                    //System.out.println(file +" created successful!");
                }
                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\promoPrice.txt"), itemPromoPrice.getBytes());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void characters(char ch[], int start, int length) throws SAXException{

        if(bname){
            //System.out.println("Product: "+ new String(ch,start,length));
            itemName = new String(ch,start,length).replace(":","-").trim();
            productDetail = itemName+" - "+itemID;
            //System.out.println(itemName+" - "+itemID);
            File gameFolder = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail);
            if (!gameFolder.exists()) {
                if (gameFolder.mkdir()) {
                    System.out.println("Created: " + gameFolder.toString());
                }
            }
            bname = false;
        }

        if(bdesc){
            itemDescription = new String(ch,start,length).trim();
            //System.out.println(itemDescription);
            bdesc = false;
        }

        if(bprice){
            itemSellPrice = new String(ch,start,length).trim();
            //System.out.println(itemSellPrice);
            //System.out.println(new String(ch,start,length));
            bprice = false;
        }

        if(bpromostart){
            itemPromoStartDate = new String(ch,start,length).trim();
            //System.out.println(new String(ch,start,length));
            bpromostart = false;
        }

        if(bpromoend){
            itemPromoEndDate = new String(ch,start,length).trim();
            //System.out.println(new String(ch,start,length));
            bpromoend = false;
        }

        if(bpromoprice){
            itemPromoPrice = new String(ch, start, length).trim();
            //System.out.println(new String(ch, start, length));
            if(!itemPromoPrice.equals(itemSellPrice)) {
                //System.out.println(itemPromoPrice);
                bpromoprice = false;
            }
        }

        if(bpublisher){
            itemPublisher = new String(ch, start, length).trim();
            //System.out.println(itemPublisher);
            bpublisher = false;
        }
    }
}