package com.ayhanunal.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PostsActivity extends AppCompatActivity {


    Posts adapter;
    FirebaseAuth firebaseAuth;
    ListView listView;
    FirebaseFirestore firebaseFirestore;

    ArrayList<String> emailFB;
    ArrayList<String> commentFB;
    ArrayList<String> imageUrlFB;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.posts_activity_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.newPost){

            Intent intent = new Intent(PostsActivity.this,AddPostActivity.class);
            startActivity(intent);

        }else if (item.getItemId() == R.id.signOut){

            firebaseAuth.signOut();
            Intent intent = new Intent(PostsActivity.this,MainActivity.class);
            startActivity(intent);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_posts);

        firebaseAuth = FirebaseAuth.getInstance();

        listView = findViewById(R.id.postActivityListView);

        firebaseFirestore = FirebaseFirestore.getInstance();

        emailFB = new ArrayList<>();
        commentFB = new ArrayList<>();
        imageUrlFB = new ArrayList<>();

        adapter = new Posts(emailFB,commentFB,imageUrlFB,this);
        listView.setAdapter(adapter);

        getDataFirebase();




    }

    public void getDataFirebase(){

        firebaseFirestore.collection("Posts").orderBy("Date", Query.Direction.DESCENDING)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()){
                            for(QueryDocumentSnapshot documentSnapshot : task.getResult()){
                                Map<String,Object> getDataFromFB = documentSnapshot.getData();

                                String email = (String) getDataFromFB.get("UserEmail");
                                String comment = (String) getDataFromFB.get("Comment");
                                String imageURL = (String) getDataFromFB.get("ImageUrl");

                                emailFB.add(email);
                                commentFB.add(comment);
                                imageUrlFB.add(imageURL);

                                adapter.notifyDataSetChanged();

                            }
                        }else {
                            System.out.println(task.getException());
                        }

                    }
                });

    }
}