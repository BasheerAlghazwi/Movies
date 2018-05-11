package com.example.android.movies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class Addactivity extends AppCompatActivity {

    private EditText movie;
    private EditText IMBD;
  //  private Button adbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_movie);

        movie=findViewById(R.id.newmovei);
        IMBD=findViewById(R.id.newIMB);

    }

    @Override
    public void onBackPressed() {
        Intent data = new Intent();
        data.putExtra("TITLE", movie.getText().toString());
        data.putExtra("CODE", IMBD.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }
}