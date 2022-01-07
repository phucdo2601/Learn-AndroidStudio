package com.example.basiccrudwithsqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.basiccrudwithsqlite.db.ConnectionHelper;
import com.example.basiccrudwithsqlite.model.UserDetail;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText contact;
    private EditText dob;
    private Button btnInsert;
    private Button btnUpdate;
    private Button btnDelete;
    private Button btnView;
    private ConnectionHelper con;
    private UserDetail userDetail = new UserDetail();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.name);
        contact = findViewById(R.id.contact);
        dob = findViewById(R.id.dob);

        btnInsert = findViewById(R.id.btnInsert);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        btnView = findViewById(R.id.btnView);

        con = new ConnectionHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
                userDetail.setName(nameTxt);
                userDetail.setContact(contactTXT);
                userDetail.setDob(dobTXT);

                Boolean checkInsertData = con.insertUserData(userDetail);
                if (checkInsertData == true) {
                    Toast.makeText(MainActivity.this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "New Entry Not Inserted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();
                String contactTXT = contact.getText().toString();
                String dobTXT = dob.getText().toString();
                userDetail.setName(nameTxt);
                userDetail.setContact(contactTXT);
                userDetail.setDob(dobTXT);

                Boolean checkUpdateData = con.updateUserData(userDetail);
                if (checkUpdateData == true) {
                    Toast.makeText(MainActivity.this, "Update is DONE!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Update is NOT SUCCESS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTxt = name.getText().toString();


                Boolean checkDeleteData = con.deleteUserData(nameTxt);
                if (checkDeleteData == true) {
                    Toast.makeText(MainActivity.this, "DELETE is DONE!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "DELETE is NOT SUCCESS", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = con.getData();
                if (res.getCount() == 0) {
                    Toast.makeText(MainActivity.this, "No Entry Exists", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    StringBuffer buffer  = new StringBuffer();
                    while (res.moveToNext()) {
                        buffer.append("Name : "+res.getString(0)+"\n");
                        buffer.append("Contact : "+res.getString(1)+"\n");
                        buffer.append("DOB : "+res.getString(2)+"\n\n");
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setCancelable(true);
                    builder.setTitle("User Entries");
                    builder.setMessage(buffer.toString());
                    builder.show();
                }
            }
        });
    }
}