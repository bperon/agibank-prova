package br.com.agibank.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Client {
    private String cnpj;
    private String name;
    private String businessArea;

    public Client(final String cnpj, final String name, final String businessArea) {
        this.cnpj = cnpj;
        this.name = name;
        this.businessArea = businessArea;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(final String cnpj) {
        this.cnpj = cnpj;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getBusinessArea() {
        return businessArea;
    }

    public void setBusinessArea(final String businessArea) {
        this.businessArea = businessArea;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("cnpj", cnpj)
                .append("name", name)
                .append("businessArea", businessArea)
                .toString();
    }
}
