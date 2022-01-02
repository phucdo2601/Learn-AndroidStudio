package com.example.addingwhereclauseinandroidstudio;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainActivity extends AppCompatActivity {

    private Connection conn;
    private PreparedStatement stm;
    private ResultSet rs;
    private String connResult;

    private void closeConn () throws SQLException, ClassNotFoundException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void displayDataToTextViewBasedOnWhere (View v) {
        TextView edtx1 = (TextView) findViewById(R.id.editTextNumber);
        TextView txv1 = (TextView) findViewById(R.id.textView);
        TextView txv2 = (TextView) findViewById(R.id.textView2);
        TextView txv3 = (TextView) findViewById(R.id.textView3);

        try {
            ConnectionHelper connHelper = new ConnectionHelper();
            conn = connHelper.makeConnection();
            if (conn != null) {
                String query = "select * from Product_Setup_Tab where Product_ID = ?";
                stm = conn.prepareStatement(query);
                stm.setString(1, edtx1.getText().toString());
                rs = stm.executeQuery();
                while (rs.next()) {
                    txv2.setText(rs.getString("ItemName"));
                    txv3.setText(rs.getString("Design"));
                    txv1.setText(rs.getString("Color"));
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