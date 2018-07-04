package com.shoaibnwar.iwsm.Database;

/**
 * Created by gold on 7/4/2018.
 */

public class OrdersHelper {



    public String getId() {
        return id;
    }

    public String getAssignerId() {
        return assignerId;
    }

    public String getSalemanId() {
        return salemanId;
    }

    public String getItemName() {
        return itemName;
    }

    public String getItemCode() {
        return itemCode;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public String getQuantity() {
        return quantity;
    }

    public String getDiscount() {
        return discount;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setAssignerId(String assignerId) {
        this.assignerId = assignerId;
    }

    public void setSalemanId(String salemanId) {
        this.salemanId = salemanId;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public void setUnitPrice(String unitPrice) {
        this.unitPrice = unitPrice;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    String id;

    public String getOrderId() {
        return orderId;
    }

    String orderId;

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    String assignerId;
    String salemanId;
    String itemName;
    String itemCode;
    String unitPrice;
    String quantity;

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    String totalItems;

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    String discount;
    String totalPrice;

    public String getStartDate() {
        return startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    String startDate;
    String startTime;
}
