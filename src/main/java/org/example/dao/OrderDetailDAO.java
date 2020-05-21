package org.example.dao;

import org.example.domain.OrderDetail;
import org.hibernate.Session;

public class OrderDetailDAO {
    private Session session;

    public OrderDetailDAO(Session session) {
        this.session = session;
    }

    public OrderDetail save(OrderDetail od) {
        session.persist(od);
        return od;
    }

    public OrderDetail getOrderById(long l) {
        return session.get(OrderDetail.class, l);
    }
}
