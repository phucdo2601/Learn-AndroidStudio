package org.meicode.connectandroid_sql;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    private Connection con;
    private String username;
    private String password;
    private String ip;
    private String port;
    private String database;

    @SuppressLint("NewApi")
    public Connection makeConnection(){
        ip = "192.168.100.4";
        username = "sa";
        password = "12345678";
        port = "1433";
        database = "Learn_YS_AndroidStudio_Connect_DB";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Connection connection = null;
        String connectionUrl = null;
        try {

            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            connectionUrl = "jdbc:jtds:sqlserver://"+ip+":"+port+";"+"databasename="+database+";user="
                    +username+";password="+password+";";

            connection = DriverManager.getConnection(connectionUrl);

        }catch (Exception ex){
            Log.e("Error ", ex.getMessage());
            ex.printStackTrace();
        }



        return  connection;
    }
}
