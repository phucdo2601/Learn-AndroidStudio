package com.example.displaydatalistviewandroid;

import android.util.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListItem {
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
    private String connectionRes = "";
    private Boolean isSuccess = false;

    private void closeConn() throws SQLException, ClassNotFoundException {
        try {
            if (rs != null) {
                rs.close();
            }
            if (stm != null) {
                stm.close();
            }
            if (con != null) {
                con.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Map<String, String>> getList() {
        List<Map<String, String>> data = null;
        data = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            con = connectionHelper.makeConnection();

            if (con != null) {
                String sql = "Select * from Product_Setup_Tab";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    Map<String, String> dtName = new HashMap<>();
                    dtName.put("ProductId", rs.getString("Product_ID"));
                    dtName.put("IName", rs.getString("ItemName"));
                    dtName.put("Des", rs.getString("Design"));
                    dtName.put("Col", rs.getString("Color"));
                    data.add(dtName);
                }
                connectionRes = "Success";
                isSuccess = true;

            }else {
                connectionRes = "Falied";
            }

        } catch (Exception ex) {
            Log.e("Error: ", ex.getMessage());
        } finally {
            try {
                closeConn();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        return data;
    }

}
