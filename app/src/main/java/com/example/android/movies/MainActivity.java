package com.example.android.movies;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.res.TypedArrayUtils;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    /* String url2 = "https://www.imdb.com/title/tt0065377/";
    String url3 = "https://www.imdb.com/title/tt0082971/";
    String url4 = "https://www.imdb.com/title/tt1289401/";
    String url5 = "https://www.imdb.com/title/tt0107048/";*/
    private static final String[] movies = {"JAWS", "Airplane!", "Raiders of the Lost Ark", "Ghostbusters", "Groundhog Day", "Dumb and Dumber"};
    private static final String[] urll = {"tt0073195", "tt0080339", "tt0082971", "tt0087332", "tt0107048", "tt0109686"};
    private ListView moviesList;
    private ArrayList<String> movieTitles;
    private ArrayList<String> urlList;
    private AlertDialog dialog;
    private int itemdelete;
    Button newaddbtn;
    Button vibtn;
    private SharedPreferences p;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        moviesList=findViewById(R.id.Ml);
        
        movieTitles = new ArrayList<String>();
        urlList = new ArrayList<String>();
        p = getPreferences(Context.MODE_PRIVATE);
        moviesList.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.imdb.com/title/" + urlList.get(i)));
                        startActivity(in);
                    }
                }
                //  Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
       /* Intent in2 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
        Intent in3 = new Intent(Intent.ACTION_VIEW, Uri.parse(url3));
        Intent in4 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));
        Intent in5 = new Intent(Intent.ACTION_VIEW, Uri.parse(url2));*/
                // startActivity(in);
       /* startActivity(in2);
        startActivity(in3);
        startActivity(in4);
        startActivity(in5);*/
        );

       // startActivityForResult(ii, 1);

        int size = p.getInt("size", 0);
        for(int j=0; j<size; j++){
            movieTitles.add(p.getString("title",null));
            urlList.add(p.getString("code",null));
        }

        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
        moviesList.setAdapter(adapter);


        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Deleting' this MOVIE!?");
        alert.setMessage("Are you sure you want to remove this movie?");
        alert.setPositiveButton("YES!", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                movieTitles.remove(itemdelete);
                urlList.remove(itemdelete);

                ArrayAdapter<String> adapter;
                adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.list_item_view, movieTitles);
                moviesList.setAdapter(adapter);
            }
        });
        alert.setNegativeButton("NAH", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialog=alert.create();

        moviesList.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                        itemdelete=i;
                        dialog.show();
                        return true;
                    }
                }
        );


         for (int i=0; i<movies.length; i++) {
            movieTitles.add(movies[i]);
            urlList.add(urll[i]);
        }

    }

    public void addButtonPressed (View v) {
        Intent i = new Intent(this, Addactivity.class);
        startActivityForResult(i,2);
    }
  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data){
      if(requestCode == 1){
          if(resultCode == RESULT_OK){
              String titleView = data.getStringExtra("TITLE");
              String codeView = data.getStringExtra("CODE");
              movieTitles.add(titleView);
              urlList.add(codeView);
              ArrayAdapter<String> adapter;
              adapter = new ArrayAdapter<String>(this, R.layout.list_item_view, movieTitles);
            //  adapter.notifyDataSetChanged();
              moviesList.setAdapter(adapter);

          }
      }
  }
    @Override
    public void onStop() {
        super.onStop();

        SharedPreferences.Editor e = getPreferences(this.MODE_PRIVATE).edit();

        for(int i = 0; i < movieTitles.size(); i++) {
            e.putString("TITLE" + i, movieTitles.get(i));
            e.putString("CODE" + i, urlList.get(i));
            e.putInt("SIZE", movieTitles.size());
        }

        e.apply();
    }



}
