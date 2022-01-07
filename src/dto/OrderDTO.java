package dto;

import java.time.LocalDate;
import java.util.List;

public class OrderDTO {
    private String orderId;
    private String CId;
    private LocalDate orderDate;
    private String orderTime;
    private double cost;
    private List<OrderDetailDTO> orderDetail;

    public OrderDTO() {
    }

    public OrderDTO(String orderId, String CId, LocalDate orderDate, String orderTime, double cost) {
        this.orderId = orderId;
        this.CId = CId;
        this.setOrderDate(orderDate);
        this.orderTime = orderTime;
        this.cost = cost;
    }

    public OrderDTO(String orderId, String CId, LocalDate orderDate, String orderTime, double cost, List<OrderDetailDTO> orderDetail) {
        this.orderId = orderId;
        this.CId = CId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
        this.setOrderDetail(orderDetail);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String cId) {
        this.CId = cId;
    }

    public String getOrderTime() { return orderTime; }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "order{" +
                "orderId='" + orderId + '\'' +
                ", cId='" + CId + '\'' +
                ", orderDate=" + getOrderDate() +
                ", orderTime='" + orderTime + '\'' +
                ", cost=" + cost +
                '}';
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(List<OrderDetailDTO> orderDetail) {
        this.orderDetail = orderDetail;
    }
}