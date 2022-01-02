package com.example.displaydatafromsql;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    private Connection connection;
    private String connResults = "";
    private PreparedStatement stm;
    private ResultSet rs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void closeConn () throws SQLException, ClassNotFoundException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDataToTextView(View v) {

        TextView txv1 = (TextView) findViewById(R.id.textView);
        TextView txv2 = (TextView) findViewById(R.id.textView2);
        TextView txv3 = (TextView) findViewById(R.id.textView3);

        try {
            ConnectionHelper connHelper = new ConnectionHelper();
            connection = connHelper.makeConnection();
            if (connection != null) {
                String query = "select * from Product_Setup_Tab";
                stm = connection.prepareStatement(query);
                rs = stm.executeQuery();
                while (rs.next()) {
                    txv1.setText(rs.getString("Product_ID"));
                    txv2.setText(rs.getString("ItemName"));
                    txv3.setText(rs.getString("Design"));
                }
            }

        } catch (Exception ex) {
            Log.e("Error : ", ex.getMessage());
        } finally {
            try {
                closeConn();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}