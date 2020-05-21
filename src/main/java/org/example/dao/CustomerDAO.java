package org.example.dao;

import org.example.domain.Customer;
import org.hibernate.Session;

public class CustomerDAO {
    private Session session;

    public CustomerDAO(Session session) {
        this.session = session;
    }

    public Customer insertNewCus(Customer customer) {
        return (Customer) session.merge(customer);
    }
    public Customer getByID(Long id){
        return session.get(Customer.class,id);
    }
}
