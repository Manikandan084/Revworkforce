package com.revworkforce.test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import com.revworkforce.util.DBConnection;

public class DBConnectionTest {

    @Test
    void testDBConnection() {

        Connection con = DBConnection.getConnection();

        assertNotNull(con, "Database connection should not be null");
    }
}
