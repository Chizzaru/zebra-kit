package com.github.chizzaru.zebrakitTest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MmsConnectTest {

    @Test
    void when_connectingToMMSwithCorrectCredentials_expect_notNull(){

        Connection conn;

        String url = "jdbc:as400://192.168.0.40";
        String username = "STUDENTWHS";
        String password = "studentwhs";
        try {
            // Load IBM Toolbox for Java JDBC driver
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            conn = DriverManager.getConnection(url, username, password);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        assertNotNull(conn);

    }
    //java.lang.RuntimeException: java.sql.SQLException
    @Test
    void when_connectingToMMSwithWrongCredentials_throws_exception(){
        String url = "jdbc:as400://192.168.0.40";
        String username = "STUDENTWS";
        String password = "studentwhs";
        assertThrows(SQLException.class, ()->{
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            DriverManager.getConnection(url, username, password);
        });
    }
}
