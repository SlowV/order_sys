package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.example.domain.Customer;
import org.example.domain.OrderDetail;
import org.example.domain.Product;
import org.example.domain.ProductDetail;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class OrderTest {

    @Test
    public void customerCanMakeOrder() {
        Customer customer = new Customer();
        Set<ProductDetail> productDetailList = getProductDetails();
        OrderDetail orderDetail = customer.makeOrder(productDetailList);
        assertThat(orderDetail).isNotNull();
    }

    @Test
    public void orderShouldContain2Products() {
        Customer customer = new Customer();
        Set<ProductDetail> productDetailList = getProductDetails();
        OrderDetail orderDetail = customer.makeOrder(productDetailList);
        assertThat(orderDetail).isNotNull();
        assertThat(orderDetail.getProductDetailList().size()).isEqualTo(2);
    }

    private Set<ProductDetail> getProductDetails() {
        return Stream.of(
                new ProductDetail(new Product(1l, "Laptop", 1000l), 2),
                new ProductDetail(new Product(2l, "Tivi", 3000l), 3)
        ).collect(Collectors.toSet());
    }

    @Test
    public void orderShouldCalculateTheTotalProductInCart() {
        Customer customer = new Customer();
        Set<ProductDetail> productDetailList = getProductDetails();
        OrderDetail orderDetail = customer.makeOrder(productDetailList);
        assertThat(orderDetail).isNotNull();
        assertThat(orderDetail.getProductDetailList().size()).isEqualTo(2);
        assertThat(orderDetail.getTotal()).isEqualTo(11000);
    }

    @Test
    public void shouldGenerateHTML() throws IOException {
        Customer customer = new Customer();
        Set<ProductDetail> productDetailList = getProductDetails();
        OutputStream out = new FileOutputStream(new File("./orderHTML.html"));
        customer.makeOrder(productDetailList).generate(out);

        List<String> contents = Files.readAllLines(Paths.get("./orderHTML.html"));
        assertThat(contents).isNotNull();
        assertThat(contents.size()).isGreaterThan(0);
    }

    @Test
    public void fileShouldContainsProductInIt() throws IOException {
        Customer customer = new Customer();
        Set<ProductDetail> productDetailList = getProductDetails();
        OutputStream out = new FileOutputStream(new File("./orderHTML.html"));
        customer.makeOrder(productDetailList).generate(out);

        String contents = new String(Files.readAllBytes(Paths.get("./orderHTML.html")));
        assertThat(contents).isNotNull();
        assertThat(contents).contains("Laptop");
        assertThat(contents).contains("Tivi");
        assertThat(contents).contains("2000");
        assertThat(contents).contains("9000");

    }

    @Test
    public void fileShouldContainsTotal() throws IOException {
        Customer customer = new Customer(1l,"Ha");
        Set<ProductDetail> productDetailList = getProductDetails();

        OutputStream out = new FileOutputStream(new File("./orderHTML.html"));
        customer.makeOrder(productDetailList).generate(out);

        String contents = new String(Files.readAllBytes(Paths.get("./orderHTML.html")));
        assertThat(contents).isNotNull();
        assertThat(contents).contains("Total");
        assertThat(contents).contains("11000");
        assertThat(contents).contains("Ha");
    }
}
