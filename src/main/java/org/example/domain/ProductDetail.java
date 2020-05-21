package org.example.domain;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class ProductDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Product product;
    private int quantity;

    @ManyToOne
    private OrderDetail orderDetail;

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductDetail that = (ProductDetail) o;
        return quantity == that.quantity &&
                Objects.equals(id, that.id) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, quantity);
    }
}
