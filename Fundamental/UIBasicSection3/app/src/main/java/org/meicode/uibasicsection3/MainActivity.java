package org.meicode.uibasicsection3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CheckBox cbxHarry;
    private CheckBox cbxMatrix;
    private CheckBox cbxJoker;

    private RadioGroup rgMaritalStatus;

    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cbxHarry = findViewById(R.id.cbxHarry);
        cbxMatrix = findViewById(R.id.cbxMatrix);
        cbxJoker = findViewById(R.id.cbxJoker);

        rgMaritalStatus = findViewById(R.id.rgMaritalStatus);

        progressBar = findViewById(R.id.proccessBar);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {
                    progressBar.incrementProgressBy(10);
                    SystemClock.sleep(500);
                }
            }
        });

        thread.start();

        int checkedButton = rgMaritalStatus.getCheckedRadioButtonId();
        switch (checkedButton){
            case R.id.rbMarried:
                Toast.makeText(MainActivity.this, "Married", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbSingle:
                Toast.makeText(MainActivity.this, "Single", Toast.LENGTH_SHORT).show();
                break;
            case R.id.rbInRel:
                Toast.makeText(MainActivity.this, "In a Relationship", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }

        rgMaritalStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rbMarried:
                        Toast.makeText(MainActivity.this, "Married", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.rbSingle:
                        Toast.makeText(MainActivity.this, "Single", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case R.id.rbInRel:
                        Toast.makeText(MainActivity.this, "In a Relationship", Toast.LENGTH_SHORT).show();
//                        progressBar.setVisibility(View.GONE);
                        break;
                    default:
                        break;
                }
            }
        });

        if (cbxHarry.isChecked()){
            Toast.makeText(MainActivity.this, "You have watched Harry Potter, Yay", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(MainActivity.this, "You need to watch Harry Potter", Toast.LENGTH_SHORT).show();

        }
        cbxHarry.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(MainActivity.this, "You have watched Harry Potter", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "You need to watch Harry Potter", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}