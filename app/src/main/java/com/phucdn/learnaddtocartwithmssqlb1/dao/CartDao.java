package com.phucdn.learnaddtocartwithmssqlb1.dao;

import android.util.Log;

import com.phucdn.learnaddtocartwithmssqlb1.db.ConnectionHelper;
import com.phucdn.learnaddtocartwithmssqlb1.model.CartModel;
import com.phucdn.learnaddtocartwithmssqlb1.model.ItemModel;

import java.security.PublicKey;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private Connection con;
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

    public boolean addCart(ItemModel cartModel) {
        boolean result = false;
        long mills = System.currentTimeMillis();
        Date currentDate = new Date(mills);
        String cartId = "C-" + currentDate.getTime();
        int quanCreate =  cartModel.getQuantity() + 1;
        System.out.println(cartId);
        try {
            con = ConnectionHelper.makeConnection();
            if (con != null) {
                String sql = "insert into cart (cartId, itemId, itemName, [image],price, quantity,totalPrice)\n" +
                        "values (?,?,?,?,?,?,?)";
                stm = con.prepareStatement(sql);
                stm.setString(1, cartId);
                stm.setInt(2, cartModel.getItemId());
                stm.setString(3, cartModel.getItemName());
                stm.setString(4, cartModel.getImage());
                stm.setFloat(5, cartModel.getPrice());
                stm.setInt(6, quanCreate);
                stm.setFloat(7, cartModel.getPrice() * quanCreate);
                result = stm.executeUpdate() > 0;
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

    public boolean updateCartByCartId(CartModel cartModel) {
        boolean result = false;

        try {
            con = ConnectionHelper.makeConnection();
            if (con != null) {
                String sql = "update cart set quantity = ?, " +
                        "totalPrice = ?\n" +
                        "where cartId =?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, cartModel.getQuantity());
                stm.setFloat(2, cartModel.getTotalPrice());
                stm.setString(3, cartModel.getCartId());
                result = stm.executeUpdate() > 0;

            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }finally {
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

    public CartModel findCartByItemId(int itemId) {
        CartModel result = new CartModel();

        try {
            con = ConnectionHelper.makeConnection();
            if (con != null) {
                String sql = "select * from cart where itemId = ?";
                stm = con.prepareStatement(sql);
                stm.setInt(1, itemId);
                rs = stm.executeQuery();
                if (rs.next()) {
                    String cartId = rs.getString("cartId");
                    String itemName = rs.getString("itemName");
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    float totalPrice = rs.getFloat("totalPrice");
                    result = new CartModel(cartId, itemId, itemName, image, price, quantity, totalPrice);
                }

            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
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

    public List<CartModel> findAllCart() {
        List<CartModel> result = new ArrayList<>();

        try {
            con = ConnectionHelper.makeConnection();
            if (con != null) {
                String sql = "select * from cart";
                stm = con.prepareStatement(sql);
                rs = stm.executeQuery();
                while (rs.next()) {
                    String cartId = rs.getString("cartId");
                    String itemName = rs.getString("itemName");
                    int itemId = rs.getInt("itemId");
                    String image = rs.getString("image");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    float totalPrice = rs.getFloat("totalPrice");
                    CartModel cartModel = new CartModel(cartId, itemId, itemName, image, price, quantity, totalPrice);
                    result.add(cartModel);
                }
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
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

    public boolean deleteByCartId(String cartId) {
        boolean result = false;

        try {
            con = ConnectionHelper.makeConnection();
            if (con != null) {
                String sql = "delete from cart where cartId = ?";
                stm =con.prepareStatement(sql);
                stm.setString(1, cartId);
                result = stm.executeUpdate() > 0;
            }
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
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
