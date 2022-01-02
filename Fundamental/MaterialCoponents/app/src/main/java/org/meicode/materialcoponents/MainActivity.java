package org.meicode.materialcoponents;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {

    private ConstraintLayout parent;
    private Button btnShowSnackbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        parent = findViewById(R.id.parentTre);
        btnShowSnackbar = findViewById(R.id.button6);
        btnShowSnackbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSnackbar();
            }
        });
    }

    private void showSnackbar(){
        Snackbar.make(parent, "This is a Snackbar", Snackbar.LENGTH_INDEFINITE)
                .setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(MainActivity.this, "Retry Clicked", Toast.LENGTH_SHORT).show();
                    }
                })
//                .setActionTextColor(getResources().getColor(R.color.red))
                .setActionTextColor(Color.RED)
                .setTextColor(Color.YELLOW)
                .show();
    }
}