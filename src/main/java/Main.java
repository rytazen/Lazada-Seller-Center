import com.alibaba.normandie.api.core.LazadaClient;
import com.alibaba.normandie.api.core.exception.LazadaException;
import com.alibaba.normandie.api.endpoint.product.model.Attribute;
import com.alibaba.normandie.api.endpoint.product.model.Category;
import com.alibaba.normandie.api.endpoint.product.model.product.Product;
import com.alibaba.normandie.api.endpoint.product.request.CreateProduct;
import com.alibaba.normandie.api.endpoint.product.request.GetCategoryAttributes;
import com.alibaba.normandie.api.endpoint.product.request.GetCategoryTree;
import com.alibaba.normandie.api.endpoint.product.request.GetProducts;
import com.alibaba.normandie.api.endpoint.product.response.GetCategoryAttributesResponse;
import com.alibaba.normandie.api.endpoint.product.response.GetCategoryTreeResponse;
import com.alibaba.normandie.api.endpoint.product.response.GetProductsResponse;
import com.alibaba.normandie.api.endpoint.product.response.ModifyProductResponse;

import config.Config;
import entity.Item;
import org.xml.sax.InputSource;
import util.NexwayParser;

import java.io.File;
import java.lang.management.MemoryManagerMXBean;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Main {
    public static void main(String[] args) {



        File mainFolder = new File("/Users/yeecheng.intern/Desktop/Lazada Games Folder");
        if (!mainFolder.exists()) {
            if (mainFolder.mkdir()) {
                System.out.println("Created: " + mainFolder.toString());
            }
        }

        NexwayParser parser = new NexwayParser();
        ArrayList<Item> items = parser.parseXml(new InputSource(Config.feedURL));

        //System.out.println(items);

        LazadaClient.init(Config.url, Config.userId, Config.apiKey);
        //System.out.println(LazadaClient.getDefaultClient());

        //GetCategories();
        //GetCategoryAttributes();
        //GetProductsInSC();
        CreateProductTest(items);
        //GetCategories();

    }

    public static void GetCategories() {
        GetCategoryTree getCategoryTree = new GetCategoryTree();

        try {
            GetCategoryTreeResponse response = getCategoryTree.execute();
            List<Category> categories = response.getBody();
            for (Category category : categories) {
                //System.out.println(category.getChildren());
                for (Category leaf : category.getChildren()) {
                    //System.out.println(leaf.getCategoryId()+" "+leaf.getName());
                    for (Category childrenOfLeaves : leaf.getChildren()) {
                        //System.out.println(childrenOfLeaves.getCategoryId()+" "+childrenOfLeaves.getName());
                        for (Category grandChild : childrenOfLeaves.getChildren()) {
                            System.out.println(grandChild.getCategoryId() + " " + grandChild.getName());
                        }
                    }
                }
            }
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }
    }

    public static void GetCategoryAttributes(){
        GetCategoryAttributes getCategoryAttributes = new GetCategoryAttributes(10002814L);

        try {
            GetCategoryAttributesResponse response = getCategoryAttributes.execute();
            System.out.println("Product mandatory attributes:");
            for(Attribute attribute: response.getProductMandatoryAttributes()) {
                System.out.println(String.format("name:[%s], type:[%s],options:%s",
                        attribute.getName(), attribute.getInputType(), attribute.getOptionValues().toString()));
            }
            System.out.println("Sku mandatory attributes:");
            for(Attribute attribute: response.getSkuMandatoryAttributes()) {
                System.out.println(String.format("name:[%s], type:[%s],options:%s",
                        attribute.getName(), attribute.getInputType(), attribute.getOptionValues().toString()));
            }
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }
    }

    public static void GetProductsInSC() {
        GetProducts request = new GetProducts();
        /*
        query with one or more filter conditon(s)
        ProductsFilter filter = new ProductsFilter();
        filter.createdBefore(new Date(117, 0, 26, 0, 0));
        filter.search("ASC Test");
        GetProducts request = new GetProducts(filter);
        */

        try {
            GetProductsResponse response = request.execute();
            List<Product> products = response.getBody();
            for(Product product : products){
                System.out.println(product.getAttributes());
            }
        } catch (LazadaException e) {
            e.printStackTrace();
        }

    }

    //Single
    public static void CreateProductTest(ArrayList<Item> items){

        //System.out.println(items.get(0));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-DD HH:MM:SS");

        String gameName = ""+"- DRAFT";
        String brand = "";
        String price = "";
        String specialPrice = "";
        String specialFrom = "";
        String specialTo = "";

        //construct attributes of product
        Map<String, Object> attributes = new HashMap<String, Object>();
        attributes.put("warranty_type", "No Warranty");
        attributes.put("is_digital", "Email");
        attributes.put("color_family", "Not Specified");
        attributes.put("Hazmat","None");
        attributes.put("brand", items.get(5).getPublisher());
        attributes.put("model", items.get(5).getItemName());
        attributes.put("name", items.get(5).getItemName());
        attributes.put("short_description","description short in attribute");
        //attributes.put("package_width", 1);
        //attributes.put("package_height", 1);
        //attributes.put("package_weight", 1);
        //attributes.put("package_length", 1);


        //construct SKUs
        List<Map<String, Object>> skusList = new ArrayList<>();
        Map<String, Object> sku1 = new HashMap<String, Object>();
        sku1.put("SellerSku", items.get(5).getItemID());
        sku1.put("quantity",100);
        sku1.put("package_content", "1 x "+items.get(5).getItemName()+" key");
        sku1.put("package_height", 1);
        sku1.put("package_width", 1);
        sku1.put("package_weight", 1);
        sku1.put("package_length", 1);
        sku1.put("price", items.get(3).getSellPrice());
        //sku1.put("special_from_date",items.get(0).getPromoStartDate());
        //sku1.put("special_to_date",items.get(0).getPromoEndDate());
        //sku1.put("special price", items.get(0).getPromoPrice());
        //sku1.put("short_description","i am in sku!");
        //sku1.put("name","Beat Cop - Name in SKU");
        //sku1.put("model","Beat Cop - Model in SKU");
        //sku1.put("brand","11 bit studios");
        //sku1.put("short_description", "CreateProduct test");
        skusList.add(sku1);

        //System.out.println();
        //construct request by categoryId, attributes of product, attributes of sku(s)
        CreateProduct createProduct = new CreateProduct(10002814L, attributes, skusList);
        try {
            ModifyProductResponse response = createProduct.execute();
            System.out.println(String.format("CreateProduct succeeded?%b: ",response.getBody()));
        } catch (LazadaException e) {
            System.out.println(e.getResponseStr());
        }
    }
}