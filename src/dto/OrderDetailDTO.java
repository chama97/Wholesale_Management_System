package dto;

public class OrderDetailDTO {
    private String itemCode;
    private String orderId;
    private int qty;
    private double price;

    public OrderDetailDTO() {
    }

    public OrderDetailDTO(String itemCode, String orderId, int qty, double price) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setQty(qty);
        this.setPrice(price);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("OrderDetailDTO{");
        sb.append("itemCode='").append(itemCode).append('\'');
        sb.append(", orderId='").append(orderId).append('\'');
        sb.append(", qty=").append(qty);
        sb.append(", price=").append(price);
        sb.append('}');
        return sb.toString();
    }
}
