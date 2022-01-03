package com.example.displaydatalistviewandroid;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    private String ip;
    private String port;
    private String db;
    private String user;
    private String password;

    @SuppressLint("NewApi")
    public Connection makeConnection() {
        ip = "192.168.100.4";
        port = "1433";
        db = "Learn_YS_AndroidStudio_Connect_DB";
        user = "sa";
        password = "12345678";

        StrictMode.ThreadPolicy tPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(tPolicy);
        Connection connection = null;
        String connectUrl = null;
        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectUrl = "jdbc:jtds:sqlserver://"+ip+":"+port+";databaseName="+db+";user="+user+";password="+password+";";
            connection = DriverManager.getConnection(connectUrl);
            if (connection != null) {
                System.out.println("Connection is OK!");
            }
        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        }

        return connection;
    }
}
