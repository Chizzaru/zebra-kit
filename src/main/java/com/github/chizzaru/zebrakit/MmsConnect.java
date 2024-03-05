package com.github.chizzaru.zebrakit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MmsConnect {
    public Connection mms_conn;
    public String error;
    private final String url;
    private final String username;
    private final String password;

    public MmsConnect(String server, String username, String password){
        this.url = "jdbc:as400://" + server;
        this.username = username;
        this.password = password;
    }

    public void mms_makeConnection(){
        try{
            // Load IBM Toolbox for Java JDBC driver
            Class.forName("com.ibm.as400.access.AS400JDBCDriver");
            mms_conn = DriverManager.getConnection(url, username, password);
        }catch(SQLException | ClassNotFoundException e){
            error = null;
            error = e.toString();
        }finally {
            try {
                if (mms_conn != null && !mms_conn.isClosed()) {
                    mms_conn.close();
                }
            } catch (SQLException ex) {
                error=null;
                error = ex.toString();
            }
        }
    }
}
