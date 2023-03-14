package com.example.nav_bird11;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public class BirdActivity extends AppCompatActivity {

    ArrayList<BirdsData> imagelist;
    RecyclerView recyclerView;
    StorageReference root;
    ProgressBar progressBar;
    ImageAdapter adapter;


    private DatabaseReference mDatabase;
// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bird);

        mDatabase = FirebaseDatabase.getInstance().getReference();


        recyclerView=findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        mDatabase.child("Data").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {

                }
                else {
                    imagelist = new ArrayList<>();

                    for (DataSnapshot ds : task.getResult().getChildren()) {
                        BirdsData birdsData = ds.getValue(BirdsData.class);
                        imagelist.add(birdsData);

                    }


                    adapter=new ImageAdapter(imagelist,BirdActivity.this);
                    recyclerView.setAdapter(adapter);
////                    progressBar.setVisibility(View.GONE);
                    Log.d("birdsData", String.valueOf(imagelist));

//                    Toast.makeText(BirdActivity.this, String.valueOf(task.getResult().getValue()), Toast.LENGTH_LONG).show();
//                    Log.d("firebase", String.valueOf(task.getResult().getValue()));
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
}