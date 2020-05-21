package org.example.dao;

import org.example.domain.Product;
import org.hibernate.Session;

import java.util.List;

public class ProductDAO {
    private Session session;

    public ProductDAO(Session session) {
        this.session = session;
    }

    public Product save(Product product) {
        session.merge(product);
        return product;
    }

    public Product getProductByID(long id){
        Product product =  session.get(Product.class,id);
        return product ;
    }
    public List<Product> findAll() {
        return session.createQuery("from Product", Product.class).getResultList();
    }
}
