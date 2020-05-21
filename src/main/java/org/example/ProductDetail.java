package org.example;

import javax.persistence.Entity;

@Entity
public class ProductDetail {
    private Product product;
    private int quantity;

    public ProductDetail(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public long getTotal() {
        return product.getPrice() * quantity;
    }

    public Product getProduct() {
        return product;
    }

}
