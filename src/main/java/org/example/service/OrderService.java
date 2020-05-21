package org.example.service;

import org.example.domain.OrderDetail;
import org.example.dao.OrderDetailDAO;
import org.hibernate.Session;

public class OrderService {
    private Session session;
    private OrderDetailDAO orderDetailDAO;

    public OrderService(Session session) {
        this.session = session;
        orderDetailDAO = new OrderDetailDAO(session);
    }

    public OrderDetail saveOderDetail(OrderDetail od) {
        return orderDetailDAO.save(od);
    }

    public OrderDetail getOrderById(long l) {
        return orderDetailDAO.getOrderById(l);
    }
}
