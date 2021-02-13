package com.ayhanunal.instagramfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {

    EditText emailText;
    EditText sifreText;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emailText = findViewById(R.id.emailEditText);
        sifreText = findViewById(R.id.sifreEditText);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            postsActivityYonlendir();
        }


    }


    public void kaydol(View view){

        firebaseAuth.createUserWithEmailAndPassword(emailText.getText().toString(),sifreText.getText().toString())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            Toast.makeText(MainActivity.this,"Kayit Basarili",Toast.LENGTH_SHORT).show();

                            firebaseUser = firebaseAuth.getCurrentUser();
                            postsActivityYonlendir();
                        }else {
                            Toast.makeText(MainActivity.this,"Hata Olustu/"+task.getException(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void girisYap(View view){

        firebaseAuth.signInWithEmailAndPassword(emailText.getText().toString(),sifreText.getText().toString())
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if(task.isSuccessful()){
                            //basarili olursa
                            firebaseUser = firebaseAuth.getCurrentUser();
                            postsActivityYonlendir();
                        }else {
                            //basarisiz.
                            Toast.makeText(MainActivity.this,"Hatalı Giriş/"+task.getException(),Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

    public void postsActivityYonlendir(){
        Intent intent = new Intent(MainActivity.this,PostsActivity.class);
        startActivity(intent);
        finish();
    }
}