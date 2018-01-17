package util;

import entity.Item;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Stack;

public class NexwayHandler extends DefaultHandler {

    private ArrayList<Item> itemList = new ArrayList<Item>();

    private Stack<String> elementStack = new Stack<String>(); //targets a single product

    private Stack<Item> objectStack = new Stack<Item>();

    private boolean bname = false;
    private boolean bprice = false;
    private boolean bpromoprice = false;
    private boolean bpromostart = false;
    private boolean bpromoend = false;
    private boolean bpublisher = false;
    private boolean bdesc = false;
    private boolean bdrm = false;

    private String itemID = "";
    private String itemName = "";
    private String itemDescription = "";
    private String itemSellPrice = "";
    private String itemPromoPrice = "";
    private String itemPromoStartDate ="";
    private String itemPromoEndDate = "";
    private String itemPublisher ="";
    private String itemDRM = "";

    private String productDetail = "";

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        this.elementStack.push(qName);
        if(qName.equals("product")){
            Item item = new Item();
            if(attributes != null && attributes.getLength() >0 ) {
                itemID = attributes.getValue("id");

                if (item != null) {
                    item.setItemID(itemID);
                }
            }
            this.objectStack.push(item);
        }

        if(qName.equals("name")){
            bname = true;
        }

        if(qName.equals("publisher")){
            bpublisher = true;
        }


        if(qName.equals("description")){
            if(attributes.getValue("format").equals("long") && attributes.getValue("language").equals("EN")){
                bdesc = true;
            }
        }

        if(qName.equals("public")){
            if(attributes.getValue("VATInclusive").equals("1")){
                bprice = true;
            }
        }

        if(qName.equals("startDatePromo")){
            bpromostart = true;
            if(qName.equals("sale")){
                if(attributes.getValue("VATInclusive").equals("1")){
                    bpromoprice = true;
                }
            }
        }

        if(qName.equals("endDatePromo")){
            bpromoend = true;
        }

        if(qName.equals("sale")){
            if(attributes.getValue("VATInclusive").equals("1")){
                bpromoprice = true;
            }
        }

        if(qName.equals("drm")){
            Item item = this.objectStack.peek();
            if(attributes != null && attributes.getLength() > 0){
                itemDRM = attributes.getValue("id");

                if (item != null) {
                    item.setItemDRM(itemDRM);

                    if(attributes.getValue("id").equalsIgnoreCase("STEAM")){
                        item.setItemHighlight("<ul>\n" +
                                "\t<li>Steam key</li>\t\n" +
                                "\t<li>Use or create a free Razer ID account to redeem your game</li>\n" +
                                "\t<li>Instructions on how to activate the game will be sent to your email</li>\n" +
                                "\t<li>Product once sold is non-refundable or exchangeable</li>\n" +
                                "</ul>");
                    } else if (attributes.getValue("id").equalsIgnoreCase("UPLAY")){
                        item.setItemHighlight("<ul>\n" +
                                "\t<li>Uplay key</li>\t\n" +
                                "\t<li>Use or create a free Razer ID account to redeem your game</li>\n" +
                                "\t<li>Instructions on how to activate the game will be sent to your email</li>\n" +
                                "\t<li>Product once sold is non-refundable or exchangeable</li>\n" +
                                "</ul>");
                    }
                }
                bdrm = true;
            }
        }
    }

    public void endElement(String uri, String localName, String qName)throws SAXException {

        this.elementStack.pop();


        if (qName.equals("description")) {
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail + "/description.html");

                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\description.html"), itemDescription.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equals("public")){
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail+"/publicPrice.txt");

                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\publicPrice.txt"), itemSellPrice.getBytes());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equals("startDatePromo")){
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail+"/startDatePromo.txt");

                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\startDatePromo.txt"), itemPromoStartDate.getBytes());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equals("endDatePromo")){
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail+"/endDatePromo.txt");

                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\endDatePromo.txt"), itemPromoEndDate.getBytes());
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equals("sale")) {
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail + "/promoPrice.txt");

                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\promoPrice.txt"), itemPromoPrice.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if(qName.equals("drm")){
            try{
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/"+productDetail+"/DRM.txt");

                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" +productDetail+"\\DRM.txt"),itemDRM.getBytes());
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        if (qName.equals("product")) {
            Item item = this.objectStack.pop();
            this.itemList.add(item);
        }

    }

    public void characters(char ch[], int start, int length) throws SAXException{

        if(bname){
            itemName = new String(ch,start,length).replace(":","-").trim();

            Item item = this.objectStack.peek();
            item.setItemName(itemName);

            productDetail = itemName+" - "+itemID;
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
            Item item = this.objectStack.peek();
            item.setItemDescription(itemDescription);
            bdesc = false;
        }

        if(bprice){
            itemSellPrice = new String(ch,start,length).trim();
            Item item = this.objectStack.peek();
            item.setSellPrice(itemSellPrice);
            bprice = false;
        }

        if(bpromostart){
            itemPromoStartDate = new String(ch,start,length).trim();
            Item item = this.objectStack.peek();
            item.setPromoStartDate(itemPromoStartDate);
            bpromostart = false;
        }

        if(bpromoend){
            itemPromoEndDate = new String(ch,start,length).trim();
            Item item = this.objectStack.peek();
            item.setPromoEndDate(itemPromoEndDate);
            bpromoend = false;
        }

        if(bpromoprice){
            itemPromoPrice = new String(ch, start, length).trim();
            if(!itemPromoPrice.equals(itemSellPrice)) {
                Item item = this.objectStack.peek();
                item.setPromoPrice(itemPromoPrice);
                bpromoprice = false;
            }
        }

        if(bpublisher){
            itemPublisher = new String(ch, start, length).trim();
            Item item = this.objectStack.peek();
            item.setPublisher(itemPublisher);
            bpublisher = false;
        }

        if(bdrm){

        }
    }

    private String currentElement()
    {
        return this.elementStack.peek();
    }

    public ArrayList<Item> getItems()
    {
        return itemList;
    }
}