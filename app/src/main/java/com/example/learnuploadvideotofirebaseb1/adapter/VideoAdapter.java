package com.example.learnuploadvideotofirebaseb1.adapter;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnuploadvideotofirebaseb1.R;
import com.example.learnuploadvideotofirebaseb1.model.ModelVideo;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.HolderVideo> {

    //context
    private Context context;
    //ArrayList
    private ArrayList<ModelVideo> videoArrayList;

    //constructor

    public VideoAdapter(Context context, ArrayList<ModelVideo> videoArrayList) {
        this.context = context;
        this.videoArrayList = videoArrayList;
    }

    @NonNull
    @Override
    public HolderVideo onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_video.xml
        View view = LayoutInflater.from(context).inflate(R.layout.row_video, parent, false);


        return new HolderVideo(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderVideo holder, int position) {
        /**
         * Get, format, set data, handle clicks etc
         */

        //Get data
        ModelVideo modelVideo = videoArrayList.get(position);
        String id = modelVideo.getId();
        String title = modelVideo.getTitle();
        String timestamp = modelVideo.getTimestamp();
        String videoUrl = modelVideo.getVideoUrl();

        //format timeStamp e.g. 07/09/2020 02:08 PM
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timestamp));
        String formattedDateTime = DateFormat.format("dd/MM/yyyy K:mm a", calendar).toString();

        //set data
        holder.titleTv.setText(title);
        holder.timeTv.setText(formattedDateTime);
        setVideoUrl(modelVideo, holder);
    }

    private void setVideoUrl(ModelVideo modelVideo, HolderVideo holder) {
        //shoe progress bar
        holder.progressBar.setVisibility(View.VISIBLE);

        //get video Url
        String videoUrl = modelVideo.getVideoUrl();

        //MediaController for play, pause, stop, seekbar, timeline
        MediaController mediaController = new MediaController(context);
        mediaController.setAnchorView(holder.videoView);

        Uri videoUri = Uri.parse(videoUrl);
        holder.videoView.setMediaController(mediaController);
        holder.videoView.setVideoURI(videoUri);
        holder.videoView.requestFocus();
        holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                //VIDEO is ready to play
                mediaPlayer.start();
            }
        });

        holder.videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                //to check if buffering, rendring etc
                switch (what) {
                    case MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START:{
                        //rendering started
                        holder.progressBar.setVisibility(View.VISIBLE);
                        return true;
                    }

                    case MediaPlayer.MEDIA_INFO_BUFFERING_START:{
                        //buffering started
                        holder.progressBar.setVisibility(View.VISIBLE);
                        return true;
                    }

                    case MediaPlayer.MEDIA_INFO_BUFFERING_END:{
                        //buffering ended
                        holder.progressBar.setVisibility(View.GONE);
                        return true;
                    }
                }

                return false;
            }
        });

        holder.videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mediaPlayer.start(); //restart video if completed
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoArrayList.size(); //RETURN SIZE OF LIST
    }


    //View holder class, holds, inits, the UI views

    class HolderVideo extends RecyclerView.ViewHolder {

        //UI view of row_video.xml
        private VideoView videoView;
        private TextView titleTv;
        private TextView timeTv;
        private ProgressBar progressBar;


        public HolderVideo(@NonNull View itemView) {
            super(itemView);


            //init Ui Views of row_video.xml
            videoView = itemView.findViewById(R.id.videoView);
            titleTv = itemView.findViewById(R.id.titleTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            progressBar = itemView.findViewById(R.id.progressBar);
        }
    }
}
