package com.phucdn.learnaddtocartwithmssqlb1.db;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {

    private static Connection connection;
    private static String ip;
    private static String port;
    private static String db;
    private static String username;
    private static String password;

    @SuppressLint("NewApi")
    public static Connection makeConnection() {
        ip = "192.168.100.13";
        port = "1433";
        db = "Learn_YS_Android_CartTutorial";
        username = "sa";
        password = "12345678";
        StrictMode.ThreadPolicy tPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tPolicy);
        Connection con = null;
        String connectionUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = "jdbc:jtds:sqlserver://"+ip+":"+port+";databaseName="+db+";user="+username+";password="+password+";";
            con = DriverManager.getConnection(connectionUrl);
            if (con != null) {
                System.out.println("Connection is ok!");
            } else {
                System.out.println("Conn is falied!");
            }
        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }
        return  con;
    }
}
