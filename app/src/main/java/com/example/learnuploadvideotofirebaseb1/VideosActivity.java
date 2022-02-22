package com.example.learnuploadvideotofirebaseb1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.learnuploadvideotofirebaseb1.adapter.VideoAdapter;
import com.example.learnuploadvideotofirebaseb1.model.ModelVideo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class VideosActivity extends AppCompatActivity {

    FloatingActionButton addVideosBtn;
    private RecyclerView videosRv;

    private ArrayList<ModelVideo> videoArrayList;

    //VideoAdapter
    private VideoAdapter videoAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        //init UI
        addVideosBtn = findViewById(R.id.addVideosBtn);
        videosRv = findViewById(R.id.videosRv);

        //function call, load videos
        loadVideosFromFirebase();


        //change actiobar
        setTitle("Videos");

        //handle click
        addVideosBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start activity to add videos
                Intent intent = new Intent(VideosActivity.this, AddVideoActivity.class);
                startActivity(intent);

            }
        });
    }

    private void loadVideosFromFirebase() {
        //init array list
        videoArrayList = new ArrayList<>();

        //db Reference
        DatabaseReference ref = FirebaseDatabase.getInstance(
                "https://learnuploadvideotofirebaseb1-default-rtdb.asia-southeast1.firebasedatabase.app/"
        )
                .getReference("Videos");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear list before adding data to it
                for (DataSnapshot ds : snapshot.getChildren()) {
                    //get Data
                    ModelVideo modelVideo = ds.getValue(ModelVideo.class);
                    //add model/ data into list
                    videoArrayList.add(modelVideo);
                }
                //setup adapter
                videoAdapter = new VideoAdapter(VideosActivity.this, videoArrayList);
                //set adapter to recycler view
                videosRv.setAdapter(videoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}