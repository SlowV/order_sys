package org.example;

import org.example.domain.Product;
import org.example.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ProductServiceTest {

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


    // lấy ra Product theo ID
    @Test
    public void itShouldGetProductByID(){
        ProductService productService = new ProductService();
        long inputID = 1;
        Product product = productService.getProductByID(inputID);

        assertThat(product.getId()).isEqualTo(inputID);
    }

    @Test
    public void itShouldGetNoProduct(){
        ProductService productService = new ProductService();
        long inputID = 3;
        Exception e = Assertions.assertThrows(ProductException.class,()->{
            productService.getProductByID(inputID);
        });
        assertThat(e.getMessage()).isEqualTo("Product not found");
    }
}
