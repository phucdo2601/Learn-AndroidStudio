package com.example.crudapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.crudapplication.db.ConnectionHelper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView id = (TextView) findViewById(R.id.editTextId);
        TextView name = (TextView) findViewById(R.id.editTextName);
        TextView address = (TextView) findViewById(R.id.editTextAddress);
        Button btnInsert = (Button) findViewById(R.id.btnCreate);
        Button btnUpdate = (Button) findViewById(R.id.btnUpdate);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        Button btnGet = (Button) findViewById(R.id.btnGet);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = false;
                con = ConnectionHelper.makeConnection();
                try {
                    if (con != null) {
                        String sql = "Insert into UserInfo_Tab (ID, Name, Address) values ('" + id.getText().toString() + "','" + name.getText().toString() + "','" + address.getText().toString() + "')";
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery(sql);
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
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = false;
                con = ConnectionHelper.makeConnection();
                try {
                    if (con != null) {
                        String sqlUpdate = "Update UserInfo_Tab set Name = '" + name.getText().toString() + "', Address = '" + address.getText().toString() + "' where ID = '"+id.getText().toString()+ "'";
                        Statement st = con.createStatement();
                        rs = st.executeQuery(sqlUpdate);
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
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean result = false;
                con = ConnectionHelper.makeConnection();
                try {
                    if (con != null) {
                            String sqlDelete = "Delete UserInfo_Tab where ID = '"+id.getText().toString()+ "'";
                        Statement st = con.createStatement();
                        rs = st.executeQuery(sqlDelete);
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
            }
        });

        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                con = ConnectionHelper.makeConnection();
                try {
                    if (con != null) {
                        String sqlGetById = "select * from UserInfo_Tab where ID = '"+id.getText().toString()+ "'";
                        stm = con.prepareStatement(sqlGetById);
                        rs = stm.executeQuery();

                        if (rs.next()) {
                            name.setText(rs.getString("Name"));
                            address.setText(rs.getString("Address"));
                        }
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
            }
        });

    }


}