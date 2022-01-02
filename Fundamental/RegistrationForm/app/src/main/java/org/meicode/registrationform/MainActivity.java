package org.meicode.registrationform;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegisterBtnClick(View view){
        TextView txtFirstname = findViewById(R.id.txtFirstName);
        TextView txtLastname = findViewById(R.id.txtLastname);
        TextView txtEmail = findViewById(R.id.txtEmail);

        EditText edtTextFirstname = findViewById(R.id.edtTxtFirstName);
        EditText edtTextLastname = findViewById(R.id.edtTxtLastname);
        EditText edtTextEmail = findViewById(R.id.edtTxtEmail);

        txtFirstname.setText("First name: "+ edtTextFirstname.getText().toString());
        txtLastname.setText("Last name: "+ edtTextLastname.getText().toString());
        txtEmail.setText("Email: "+ edtTextEmail.getText().toString());
    }
}