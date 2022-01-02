package org.meicode.list2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView citiesList;
    private Spinner studentsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        citiesList = findViewById(R.id.citiesList);
        studentsSpinner = findViewById(R.id.studentsSpinner);

//        ArrayList<String> students = new ArrayList<>();
//        students.add("Phuc Do");
//        students.add("Le Vinh");
//        students.add("Hoang Thai");
//        students.add("Khanh Tram");
//        students.add("Le Vinh");
//        students.add("Phuoc Duy");
//        students.add("Trung Ta");
//        students.add("Khoi Pham");
//        students.add("Huu Toan");
//        students.add("Thanh Tu");
//
//        ArrayAdapter<String> studentsAdapter = new ArrayAdapter<>(
//                this,
//                android.R.layout.simple_spinner_dropdown_item,
//                students
//        );
//
//        studentsSpinner.setAdapter(studentsAdapter);

        studentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
//                Toast.makeText(MainActivity.this, students.get(position), Toast.LENGTH_SHORT).show();
                Toast.makeText(MainActivity.this, studentsSpinner.getSelectedItem().toString() + " Selected", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        final ArrayList<String> cities = new ArrayList<>();
        cities.add("Zurich");
        cities.add("New York");
        cities.add("Berlin");
        cities.add("Moscow");
        cities.add("Hanoi");
        cities.add("Tokyo");
        cities.add("Bang kok");

        ArrayAdapter<String> citiesAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                cities
        );

        citiesList.setAdapter(citiesAdapter);

        citiesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Toast.makeText(MainActivity.this, cities.get(position) + " Selected", Toast.LENGTH_SHORT).show();
            }
        });
    }
}