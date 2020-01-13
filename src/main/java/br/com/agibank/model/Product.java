package br.com.agibank.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Product {
    private int itemId;
    private int quantity;
    private double price;

    public Product(final int itemId, final int quantity, final double price) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(final int itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("itemId", itemId)
                .append("quantity", quantity)
                .append("price", price)
                .toString();
    }
}
