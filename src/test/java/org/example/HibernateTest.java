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


}
