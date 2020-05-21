package org.example;

import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.assertj.core.api.Assertions.assertThat;

public class DbManagerTest {
    @Test
    public void itShouldGetConnectionNotNull() {
        assertThat(DbManager.getConnection()).isNotNull();
    }

    @Test
    public void itShouldGetCustomerById() {
        Connection connection = DbManager.getConnection();
    }
}
