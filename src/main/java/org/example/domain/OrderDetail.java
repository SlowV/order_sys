package org.example.domain;

import javax.persistence.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;

@Entity
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy = "orderDetail", cascade = CascadeType.ALL)
    private Set<ProductDetail> productDetailList =  new HashSet<>();

    @Column(nullable = false)
    private long total;

    @ManyToOne
    private Customer customer;

    public Customer getCustomer() {
        return customer;
    }

    public Set<ProductDetail> getProductDetailList() {
        return productDetailList;
    }

    public OrderDetail(Set<ProductDetail> productDetailList, Customer customer) {
        this.productDetailList = productDetailList;
//        this.productDetailList.stream().forEach(productDetail -> productDetail.setOrderDetail(this));

        this.customer = customer;

        total = productDetailList.stream()
                .mapToLong(productDetail -> productDetail.getTotal())
                .sum();
    }

    public long getTotal() {
        return total;
    }

    public void generate(OutputStream out) throws IOException {
        String content = new String(Files.readAllBytes(Paths.get("./src/main/resources/orderTempl.html")));
        //System.out.println(content);

        String result = String.format(content,customer.getName(), getProductListRow(productDetailList), total + "");
        try(PrintWriter writer = new PrintWriter(out)) {
            writer.print(result);
        };

    }

    public String getProductListRow(Set<ProductDetail> productDetails) {
        //<tr><td>product name</td><td>price</td><tr>
        //<tr><td>product name</td><td>price</td><tr>
//        StringBuilder builder = new StringBuilder();
        return productDetails.stream().map(productDetail -> getProductRow(productDetail))
                 .reduce("", (x, y) -> x + y);
    }

    public String getProductRow(ProductDetail productDetail) {
        //<tr><td>product name</td><td>price</td><tr>
        return String.format("<tr><td>%s</td><td>%s</td><tr>",
                productDetail.getProduct().getName(),
                productDetail.getTotal() + "");
    }


}
