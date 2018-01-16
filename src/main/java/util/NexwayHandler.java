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

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {

        this.elementStack.push(qName);
        //System.out.println("CurrElement:"+ currentElement());
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
    }

    public void endElement(String uri, String localName, String qName)throws SAXException {

        this.elementStack.pop();


        if (qName.equals("description")) {
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

        if(qName.equals("public")){
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

        if(qName.equals("startDatePromo")){
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

        if(qName.equals("endDatePromo")){
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

        if(qName.equals("sale")) {
            try {
                File file = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail + "/promoPrice.txt");
                if (file.createNewFile()) {
                    //System.out.println(file +" created successful!");
                }
                Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\promoPrice.txt"), itemPromoPrice.getBytes());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (qName.equals("product")) {
            //System.out.println(productDetail);
            Item item = this.objectStack.pop();
            this.itemList.add(item);
        }

    }
    
    public void characters(char ch[], int start, int length) throws SAXException{

        if(bname){
            //System.out.println("Product: "+ new String(ch,start,length));
            itemName = new String(ch,start,length).replace(":","-").trim();

            Item item = this.objectStack.peek();
            item.setItemName(itemName);

            //System.out.println("This object is : " +this.objectStack.peek().itemName);
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
            Item item = this.objectStack.peek();
            item.setItemDescription(itemDescription);
            //System.out.println(item.getItemDescription());
            //System.out.println(item.getItemDescription());
            bdesc = false;
        }

        if(bprice){
            itemSellPrice = new String(ch,start,length).trim();
            //System.out.println(itemSellPrice);
            //System.out.println(new String(ch,start,length));
            Item item = this.objectStack.peek();
            item.setSellPrice(itemSellPrice);
            //System.out.println("This object is : " +this.objectStack.peek());
            bprice = false;
        }

        if(bpromostart){
            itemPromoStartDate = new String(ch,start,length).trim();
            //System.out.println(new String(ch,start,length));
            Item item = this.objectStack.peek();
            item.setPromoStartDate(itemPromoStartDate);
            bpromostart = false;
        }

        if(bpromoend){
            itemPromoEndDate = new String(ch,start,length).trim();
            //System.out.println(new String(ch,start,length));
            Item item = this.objectStack.peek();
            item.setPromoEndDate(itemPromoEndDate);
            bpromoend = false;
        }

        if(bpromoprice){
            itemPromoPrice = new String(ch, start, length).trim();
            //System.out.println(new String(ch, start, length));
            if(!itemPromoPrice.equals(itemSellPrice)) {
                Item item = this.objectStack.peek();
                item.setPromoPrice(itemPromoPrice);
                //System.out.println(itemPromoPrice);
                bpromoprice = false;
            }
        }

        if(bpublisher){
            itemPublisher = new String(ch, start, length).trim();
            //System.out.println(itemPublisher);
            Item item = this.objectStack.peek();
            item.setPublisher(itemPublisher);
            //System.out.println("This object is : " +this.objectStack.peek());
            bpublisher = false;
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