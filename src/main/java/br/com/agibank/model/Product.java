package br.com.agibank.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Product {
    private Integer itemId;
    private Integer quantity;
    private Double price;

    public Product(final Integer itemId, final Integer quantity, final Double price) {
        this.itemId = itemId;
        this.quantity = quantity;
        this.price = price;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(final Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(final Double price) {
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
