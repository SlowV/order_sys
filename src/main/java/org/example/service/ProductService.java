package org.example.service;

import org.example.Product;
import org.example.ProductException;
import org.example.dao.ProductDAO;
import org.example.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.persistence.PersistenceException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class ProductService {
    private Session session = HibernateUtil.getSession();
    private ProductDAO productDAO = new ProductDAO(session);

    public Product addProduct(Product product) {
        Transaction transaction = null;
        Optional<Product> optional = Optional.empty();
        try {
            transaction = this.session.beginTransaction();
            optional = Optional.of(this.productDAO.save(product));
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new ProductException("Product save failed!");
        } finally {
            session.close();
        }
        return optional.orElseThrow(()-> new ProductException("Product save failed!"));
    }

    public List<Product> getProducts() {
        return this.productDAO.findAll();
    }

    public ProductDAO getProductDAO() {
        return productDAO;
    }

    public void setProductDAO(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }
}
