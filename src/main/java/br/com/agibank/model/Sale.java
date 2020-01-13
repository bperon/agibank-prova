package br.com.agibank.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import java.util.List;

public class Sale {
    private Integer saleId;
    private List<Product> products;
    private String salesmanName;

    public Sale(final int saleId, final List<Product> products, final String salesmanName) {
        this.saleId = saleId;
        this.products = products;
        this.salesmanName = salesmanName;
    }

     public Integer getSaleId() {
        return saleId;
    }

    public void setSaleId(final Integer saleId) {
        this.saleId = saleId;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(final List<Product> products) {
        this.products = products;
    }

    public String getSalesmanName() {
        return salesmanName;
    }

    public void setSalesmanName(final String salesmanName) {
        this.salesmanName = salesmanName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("saleId", saleId)
                .append("products", products)
                .append("salesmanName", salesmanName)
                .toString();
    }
}
