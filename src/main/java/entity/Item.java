package entity;

public class Item {

    public String itemID;
    public String itemName;
    public String itemHighlight;


    public String itemDescription;
    public String sellPrice;
    public String promoPrice;
    public String promoStartDate;
    public String promoEndDate;
    public String publisher;

    public String itemDRM;

    public Item() {
    }

    public Item(String itemID, String itemName, String itemDescription, String sellPrice, String publisher) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.sellPrice = sellPrice;
        this.publisher = publisher;
    }

    public Item(String itemID, String itemName, String itemDescription, String sellPrice, String promoPrice, String promoStartDate, String promoEndDate, String publisher) {
        this.itemID = itemID;
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.sellPrice = sellPrice;
        this.promoPrice = promoPrice;
        this.promoStartDate = promoStartDate;
        this.promoEndDate = promoEndDate;
        this.publisher = publisher;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public String getItemDRM() {
        return itemDRM;
    }

    public void setItemDRM(String itemDRM) {
        this.itemDRM = itemDRM;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(String sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getPromoPrice() {
        return promoPrice;
    }

    public void setPromoPrice(String promoPrice) {
        this.promoPrice = promoPrice;
    }

    public String getPromoStartDate() {
        return promoStartDate;
    }

    public String getItemHighlight() {
        return itemHighlight;
    }

    public void setItemHighlight(String itemHighlight) {
        this.itemHighlight = itemHighlight;
    }

    public void setPromoStartDate(String promoStartDate) {
        this.promoStartDate = promoStartDate;
    }

    public String getPromoEndDate() {
        return promoEndDate;
    }

    public void setPromoEndDate(String promoEndDate) {
        this.promoEndDate = promoEndDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemID='" + itemID + '\'' +
                ", itemName='" + itemName + '\'' +
                ", itemHighlight='" + itemHighlight + '\'' +
                ", itemDescription='" + itemDescription + '\'' +
                ", sellPrice='" + sellPrice + '\'' +
                ", promoPrice='" + promoPrice + '\'' +
                ", promoStartDate='" + promoStartDate + '\'' +
                ", promoEndDate='" + promoEndDate + '\'' +
                ", publisher='" + publisher + '\'' +
                ", itemDRM='" + itemDRM + '\'' +
                '}';
    }
}
