package org.meicode.uibasicsection2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView txtHello;
    private EditText edtTxtName;

    // Cach set method click cho button (cach 2)
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnHello:
                Toast.makeText(this, "Hello Button Clicked", Toast.LENGTH_SHORT).show();
                txtHello.setText("Hello "+edtTxtName.getText().toString());
                break;

            case R.id.edtTxtName:
                Toast.makeText(this, "Attempiting to type something", Toast.LENGTH_SHORT).show();
                break;

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Cach set phuong thuc onclick khac (Cach 1)
//        Button btnHello = findViewById(R.id.btnHello);
//        btnHello.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                System.out.println("Hello World!!!");
//            }
//        });

        Button btnHello = findViewById(R.id.btnHello);
        btnHello.setOnClickListener(this);

        //set long press listener
//        btnHello.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                Toast.makeText(MainActivity.this, "Long Press", Toast.LENGTH_LONG).show();
//                return true;
//            }
//        });

        edtTxtName = findViewById(R.id.edtTxtName);
        edtTxtName.setOnClickListener(this);
        txtHello = findViewById(R.id.txtHello);
    }
}