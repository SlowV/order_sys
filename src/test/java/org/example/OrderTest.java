package org.example;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

public class OrderTest {

    @Test
    public void customerCanMakeOrder() {
        Customer customer = new Customer();
        List<ProductDetail> productDetailList = Arrays.asList(
                new ProductDetail(new Product(1, "Laptop", 1000), 2),
                new ProductDetail(new Product(2, "Tivi", 3000), 3)
        );
        OrderDetail orderDetail = customer.makeOrder(productDetailList);
        assertThat(orderDetail).isNotNull();
    }

    @Test
    public void orderShouldContain2Products() {
        Customer customer = new Customer();
        List<ProductDetail> productDetailList = Arrays.asList(
                new ProductDetail(new Product(1, "Laptop", 1000), 2),
                new ProductDetail(new Product(2, "Tivi", 3000), 3)
        );
        OrderDetail orderDetail = customer.makeOrder(productDetailList);
        assertThat(orderDetail).isNotNull();
        assertThat(orderDetail.getProductDetailList().size()).isEqualTo(2);
    }
    @Test
    public void orderShouldCalculateTheTotalProductInCart() {
        Customer customer = new Customer();
        List<ProductDetail> productDetailList = Arrays.asList(
                new ProductDetail(new Product(1, "Laptop", 1000), 2),
                new ProductDetail(new Product(2, "Tivi", 3000), 3)
        );
        OrderDetail orderDetail = customer.makeOrder(productDetailList);
        assertThat(orderDetail).isNotNull();
        assertThat(orderDetail.getProductDetailList().size()).isEqualTo(2);
        assertThat(orderDetail.getTotal()).isEqualTo(11000);
    }

    @Test
    public void shouldGenerateHTML() throws IOException {
        Customer customer = new Customer();
        List<ProductDetail> productDetailList = Arrays.asList(
                new ProductDetail(new Product(1, "Laptop", 1000), 2),
                new ProductDetail(new Product(2, "Tivi", 3000), 3)
        );

        OutputStream out = new FileOutputStream(new File("./orderHTML.html"));
        customer.makeOrder(productDetailList).generate(out);

        List<String> contents = Files.readAllLines(Paths.get("./orderHTML.html"));
        assertThat(contents).isNotNull();
        assertThat(contents.size()).isGreaterThan(0);
    }

    @Test
    public void fileShouldContainsProductInIt() throws IOException {
        Customer customer = new Customer();
        List<ProductDetail> productDetailList = Arrays.asList(
                new ProductDetail(new Product(1, "Laptop", 1000), 2),
                new ProductDetail(new Product(2, "Tivi", 3000), 3)
        );

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
        Customer customer = new Customer(1,"Ha");
        List<ProductDetail> productDetailList = Arrays.asList(
                new ProductDetail(new Product(1, "Laptop", 1000), 2),
                new ProductDetail(new Product(2, "Tivi", 3000), 3)
        );

        OutputStream out = new FileOutputStream(new File("./orderHTML.html"));
        customer.makeOrder(productDetailList).generate(out);

        String contents = new String(Files.readAllBytes(Paths.get("./orderHTML.html")));
        assertThat(contents).isNotNull();
        assertThat(contents).contains("Total");
        assertThat(contents).contains("11000");
        assertThat(contents).contains("Ha");
    }
}
