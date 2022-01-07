package view.tm;

import java.time.LocalDate;

public class IncomeReportTM {
    private String CId;
    private String orderId;
    private LocalDate orderDate;
    private double cost;

    public IncomeReportTM() {
    }

    public IncomeReportTM(String CId, String orderId, LocalDate orderDate, double cost) {
        this.setCId(CId);
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
        this.setCost(cost);
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }
}
