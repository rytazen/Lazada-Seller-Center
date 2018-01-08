package util;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;

public class XMLParser {
    public static void main(String[] args) {

        //String url = "http://webservices.nexway.com/flow/getcatalog/v2.4/?secret=JNgPCZs18t&provider=1c173122-2bf0-8c36-50a3-b3fa6b919e86&config=7016b623-3853-3db5-a67a-cf6aec336459";
        String url = "http://webservices.nexway.com/flow/getcatalog/v2.4/?secret=JNgPCZs18t&provider=1c774002-211f-5012-9d53-80e6cb60759d&config=7016b623-3853-3db5-a67a-cf6aec336459";

        try {

            DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
            DocumentBuilder b = f.newDocumentBuilder();
            Document doc = b.parse(new URL(url).openStream());

            doc.getDocumentElement().normalize();

            NodeList products = doc.getElementsByTagName("product");
            NodeList name = doc.getElementsByTagName("name");
            NodeList publisher = doc.getElementsByTagName("publisher");
            //NodeList description = doc.getElementsByTagName("description");
            File mainFolder = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder");
            if (!mainFolder.exists()) {
                if (mainFolder.mkdir()) {
                    System.out.println("Created: " + mainFolder.toString());
                }
            }

            int numOfProducts = products.getLength();

            for (int i = 0; i < numOfProducts; i++) {
                Node product = products.item(i);

                if (product.getNodeType() == Node.ELEMENT_NODE) {
                    Element eProduct = (Element) product;

                    String productDetail = name.item(i).getTextContent().replace(":", "-") + " - " + eProduct.getAttributes().getNamedItem("id").getNodeValue();
                    String publisherName = publisher.item(i).getFirstChild().getNodeValue();

                    File gameFolder = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder/" + productDetail);
                    if (!gameFolder.exists()) {
                        if (gameFolder.mkdir()) {
                            System.out.println("created" + gameFolder.toString());
                        }
                    }

                    NodeList descriptionNode = eProduct.getElementsByTagName("description");
                    for (int j = 0; j < descriptionNode.getLength(); j++) {
                        Node individualDescription = descriptionNode.item(j);

                        if (individualDescription.getAttributes().getNamedItem("format").getNodeValue().equals("long")
                                && individualDescription.getAttributes().getNamedItem("language").getNodeValue().equals("EN")) {
                            String theExactDescription = individualDescription.getFirstChild().getNodeValue();
                            Files.write(Paths.get("\\Users\\yeecheng.intern\\Desktop\\Lazada Games Folder\\" + productDetail + "\\description.txt"), theExactDescription.getBytes());

                            System.out.println(productDetail + ": done");
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}