package org.example.service;

import org.example.domain.Customer;
import org.example.dao.CustomerDAO;
import org.hibernate.Session;

import java.util.Optional;

public class CustomerService {

    private Session session;
    private CustomerDAO customerDAO;

    public CustomerService() {
    }

    public CustomerService(Session session) {
        this.session = session;
        customerDAO = new CustomerDAO(session);
    }

    public Customer insertNewCustomer(Customer customer) throws CustomerException {
        return customerDAO.insertNewCus(customer);
    }

    public Customer getCusByID(Long id) throws CustomerException {
        return Optional.of(customerDAO.getByID(id)).orElseThrow(()-> new CustomerException("Insert Customer Failed"));
    }
    public class CustomerException extends RuntimeException{
        public CustomerException(String s) {
            super(s);
        }
    }
}

