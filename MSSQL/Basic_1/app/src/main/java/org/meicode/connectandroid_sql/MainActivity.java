package org.meicode.connectandroid_sql;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class MainActivity extends AppCompatActivity {

    private Connection connection;
    private String connectionResult;
    private PreparedStatement stm;
    private ResultSet rs;

    public void closeCon(){

            try {
                if (rs != null){
                    rs.close();
                }
                if (stm != null){
                    stm.close();
                }
                if (connection != null){
                    connection.close();
                }
            }catch (Exception ex){
                System.out.println("Error at close SQL connection: "+ ex.getMessage());
                ex.printStackTrace();
            }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getTextFromSql(View v){
        TextView txt1 = findViewById(R.id.textView);
        TextView txt2 = findViewById(R.id.textView2);

            try {
                ConnectionHelper connectionHelper = new ConnectionHelper();
                connection = connectionHelper.makeConnection();
                if (connection != null){
                    System.out.println("Conection is Ok!");
                    String query = "Select * From Product_Setup_Tab";
                    stm = connection.prepareStatement(query);
                    rs = stm.executeQuery();

                    while (rs.next()){
                        txt1.setText(rs.getString(1));
                        txt2.setText(rs.getString(2));
                    }
                }
            } catch (Exception ex){
                System.out.println("Error at getTextFromSql: "+ex.getMessage());
                ex.printStackTrace();
            }

    }
}