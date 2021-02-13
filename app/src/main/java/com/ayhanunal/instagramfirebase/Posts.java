package com.ayhanunal.instagramfirebase;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Posts extends ArrayAdapter<String> {

    private ArrayList<String> commentArray;
    private ArrayList<String> userEmailArray;
    private ArrayList<String> imageArray;
    private Activity context;

    public Posts(ArrayList<String> userEmail, ArrayList<String> userComment, ArrayList<String> userImage, Activity context) {
        super(context,R.layout.post_activity_custom_view,userEmail);
        this.userEmailArray = userEmail;
        this.commentArray = userComment;
        this.imageArray = userImage;
        this.context = context;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater layoutInflater = context.getLayoutInflater();
        View customView = layoutInflater.inflate(R.layout.post_activity_custom_view,null,true );

        TextView userEmailText = customView.findViewById(R.id.postActivityEmailText);
        TextView commentText = customView.findViewById(R.id.postActivityCommentText);
        ImageView imageView = customView.findViewById(R.id.postActivityImageView);

        userEmailText.setText("Kullanici Email :"+userEmailArray.get(position));
        commentText.setText("Yorum :"+commentArray.get(position));
        Picasso.get().load(imageArray.get(position)).into(imageView);

        return customView;
    }
}
