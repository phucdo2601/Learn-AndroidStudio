package com.phucdn.learnaddtocartwithmssqlb1.dao;

import android.util.Log;

import com.phucdn.learnaddtocartwithmssqlb1.db.ConnectionHelper;
import com.phucdn.learnaddtocartwithmssqlb1.model.ItemModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ItemDao {
    private Connection con;
    private String connResults = "";
    private PreparedStatement stm;
    private ResultSet rs;

    private void closeConn () throws SQLException, ClassNotFoundException {
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

    public List<ItemModel> loadAllItems() {
        List<ItemModel> result = new ArrayList<>();
        ItemModel itemModel =null;
        try {
            con = ConnectionHelper.makeConnection();
            if (con != null) {
                String sql = "select * from item";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    int itemId = rs.getInt("itemId");
                    String itemName = rs.getString("itemName");
                    System.out.println(itemName);
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    itemModel = new ItemModel(itemId, itemName, image, price);
                    result.add(itemModel);
                }
            } else {
                System.out.println("Connection is Failed!");
            }
        } catch (Exception ex) {
            Log.e("Error : ", ex.getMessage());
            ex.printStackTrace();
        } finally {
            try {
                closeConn();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
