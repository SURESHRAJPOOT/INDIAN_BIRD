package com.example.nav_bird11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.nav_bird11.databinding.ActivityDescriptionBinding;
import com.example.nav_bird11.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;

public class DescriptionActivity extends AppCompatActivity {

    private ActivityDescriptionBinding binding;
    private DatabaseReference mDatabase;
    MediaPlayer mediaPlayer;
    String audio = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescriptionBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Bundle extras = getIntent().getExtras();
        mediaPlayer = new MediaPlayer();
        if (extras != null) {
            String name = extras.getString("name");
            String id = extras.getString("id");
            String url = extras.getString("url");
            Picasso.get().load(url).into(binding.image);
            binding.name.setText(name);
            GetDescription(id);


        }

        binding.music.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (audio!= ""){
                    playAudio();
                }

            }
        });
    }


    private void GetDescription(String id){
        mDatabase.child("info").child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    DescpModel descpModel = task.getResult().getValue(DescpModel.class);
                    if (descpModel !=null){
                        binding.desc.setText(descpModel.getDesc());

                        if (descpModel.getAudio()==null || descpModel.getAudio()==""){
                            binding.music.setVisibility(View.GONE);
                        } else  {
                            audio = descpModel.getAudio();
                        }
                    }



                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                // Write failed
                // ...
            }
        });
    }


    private void playAudio() {

        String audioUrl = audio;

        // initializing media player


        // below line is use to set the audio
        // stream type for our media player.
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        // below line is use to set our
        // url to our media player.
        try {
            mediaPlayer.setDataSource(audioUrl);
            // below line is use to prepare
            // and start our media player.
            mediaPlayer.prepare();
            mediaPlayer.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
        // below line is use to display a toast message.
        Toast.makeText(this, "Audio started playing..", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mediaPlayer.isPlaying()){
            mediaPlayer.stop();
        }
    }
}