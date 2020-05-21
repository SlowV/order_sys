package org.example.service;

import org.example.domain.Product;
import org.example.ProductException;
import org.example.dao.ProductDAO;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class ProductService {
    private Session session;
    private ProductDAO productDAO;

    public ProductService() {
    }

    public ProductService(Session session) {
        this.session = session;
        productDAO = new ProductDAO(session);
    }

    public Product addProduct(Product product) {
        return Optional.of(this.productDAO.save(product)).orElseThrow(()-> new ProductException("Product save failed!"));
    }

    public List<Product> getProducts() {
        return this.productDAO.findAll();
    }

    public Product getProductByID(long inputID) {
        return Optional.of(this.productDAO.getProductByID(inputID)).orElseThrow(()-> new ProductException("Product not found"));
    }
}
