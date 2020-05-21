package org.example;

import org.example.service.ProductService;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HibernateTest {
    @Test
    public void itShouldGetSessionNotNull() {
        assertThat(HibernateUtil.getSession()).isNotNull();
    }

    @Test
    public void itShouldSaveProductAndReturnThisContainsName() {
        ProductService service = new ProductService();
        Product product = new Product();
        product.setName("Bánh My");
        product.setPrice(300);
        Product productResult = service.addProduct(product);
        assertThat(productResult.getName()).isEqualTo(product.getName());
    }

    @Test
    public void itShouldSaveProductFailAndThrowException() {
        ProductService service = new ProductService();
        Product product = new Product();
        product.setPrice(300);
        Exception exception = Assertions.assertThrows(ProductException.class, () -> service.addProduct(product));
        assertThat(exception.getMessage()).isNotNull();
    }

    @Test
    public void itShouldGetProductsHasItem() {
        ProductService service = new ProductService();
        assertThat(service.getProducts().size()).isGreaterThan(0);
    }
}
