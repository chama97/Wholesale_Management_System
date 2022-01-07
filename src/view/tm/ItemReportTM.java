package view.tm;

public class ItemReportTM {
    private String itemCode;
    private int qty;
    private double price;

    public ItemReportTM() {
    }

    public ItemReportTM(String itemCode, int qty, double price) {
        this.setItemCode(itemCode);
        this.setQty(qty);
        this.setPrice(price);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
