package com.example.displaydatalistviewandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private SimpleAdapter ad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void getList(View v) {

        ListView listView = (ListView) findViewById(R.id.listView1);

        List<Map<String, String>> myDataList = null;
        ListItem myData = new ListItem();
        myDataList = myData.getList();

        String [] fromW = {"ProductId", "IName", "Des", "Col"};
        int[] tow = {R.id.ProductId, R.id.IName, R.id.Des, R.id.Col};
        ad= new SimpleAdapter(MainActivity.this, myDataList, R.layout.listlayout_template, fromW, tow);
        listView.setAdapter(ad);
    }
}