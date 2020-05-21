package org.example;

import org.example.domain.*;
import org.example.service.CustomerService;
import org.example.service.OrderService;
import org.example.service.ProductService;
import org.example.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class OrderServiceTest {


    @Test
    public void itShouldSaveOrderDetail() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            CustomerService cs = new CustomerService(session);
            Customer customer = cs.insertNewCustomer(new Customer("Ha"));

            ProductService ps = new ProductService(session);
            Product p1 = ps.addProduct(new Product("Laptop", 1000));
            Product p2 = ps.addProduct(new Product("Tivi", 1000));

            Set<ProductDetail> productDetailList = Stream.of(
                    new ProductDetail(p1, 2),
                    new ProductDetail(p2, 3)
            ).collect(Collectors.toSet());

            OrderDetail od = customer.makeOrder(productDetailList);
            OrderService orderService = new OrderService(session);
            OrderDetail newOd = orderService.saveOderDetail(od);

            assertThat(newOd).isNotNull();
            assertThat(newOd.getTotal()).isEqualTo(5000);

            Predicate<String> filterByName = productName -> productName.equals(p1.getName());
            newOd.getProductDetailList().stream()
                    .map(productDetail -> productDetail.getProduct().getName())
                    .filter(filterByName).findFirst().ifPresent(
                    productName -> {
                        assertThat(productName).isEqualTo(p1.getName());
                    }
            );

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }


    @Test
    public void testMerge() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {

            int addressId;
            Person person = new Person("devender");
            Address address = buildAddress(person);
            person.setAddresses(Arrays.asList(address));

            session.persist(person);
            session.flush();
            addressId = address.getId();
            session.clear();

            Address savedAddressEntity = session.find(Address.class, addressId);
            Person savedPersonEntity = savedAddressEntity.getPerson();
            savedPersonEntity.setName("devender kumar");
            savedAddressEntity.setHouseNumber(24);
            session.persist(savedAddressEntity);
            session.flush();

            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }
    }

    @Test
    public void whenParentRefreshedThenChildRefreshed() {
        Session session = HibernateUtil.getSession();
        Transaction transaction = session.beginTransaction();
        try {
            Person person = buildPerson("devender");
            Address address = buildAddress(person);
            person.setAddresses(Arrays.asList(address));
            session.persist(person);
            session.flush();
            person.setName("Devender Kumar");
            address.setHouseNumber(24);
            session.refresh(person);

            assertThat(person.getName()).isEqualTo("devender");
            assertThat(address.getHouseNumber()).isEqualTo(23);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        }


    }

    private Person buildPerson(String devender) {
        return new Person(devender);
    }

    private Address buildAddress(Person person) {
        Address address = new Address();
        address.setPerson(person);
        return address;
    }


}
