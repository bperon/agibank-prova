package br.com.agibank.model;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Saler {
    private String cpf;
    private String name;
    private Double salary;

    public Saler(final String cpf, final String name, final Double salary) {
        this.cpf = cpf;
        this.name = name;
        this.salary = salary;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(final String cpf) {
        this.cpf = cpf;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(final Double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.JSON_STYLE)
                .append("cpf", cpf)
                .append("name", name)
                .append("salary", salary)
                .toString();
    }
}
