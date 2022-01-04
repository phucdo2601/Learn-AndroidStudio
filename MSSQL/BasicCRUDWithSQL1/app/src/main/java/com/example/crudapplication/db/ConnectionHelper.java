package com.example.crudapplication.db;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    private static Connection con;
    private static String ip;
    private static String port;
    private static String db;
    private static String username;
    private static String password;

    @SuppressLint("NewApi")
    public static Connection makeConnection() {
        ip = "192.168.100.4";
        port = "1433";
        db = "Learn_YS_AndroidStudio_Connect_DB";
        username = "sa";
        password = "12345678";
        StrictMode.ThreadPolicy tPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tPolicy);
        String connectionUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = "jdbc:jtds:sqlserver://"+ip+":"+port+";databaseName="+db+";user="+username+";password="+password+";";
            con = DriverManager.getConnection(connectionUrl);
            if (con != null) {
                System.out.println("Connection is ok!");
            }

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }

        return con;

    }
}
