package com.mariebsr.android.mymovieapp.activities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import com.google.gson.Gson;
import com.mariebsr.android.mymovieapp.R;
import com.mariebsr.android.mymovieapp.models.Movie;
import com.squareup.picasso.Picasso;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieActivity extends AppCompatActivity {

    private TextView mTextViewMovieTitle;

    private Movie mMovie;

    private OkHttpClient mOkHttpClient;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

    //on recupere l'intention lancée par la mainActivity
        Bundle extras = getIntent().getExtras();
        String strMessage = extras.getString("EXTRA_MESSAGE");
        Log.d("TAG", "MovieActivity: onCreate()------>" + strMessage);

        mTextViewMovieTitle = findViewById(R.id.text_view_movie_activity);
        mTextViewMovieTitle.setText(strMessage);


        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
// Oui il y a Internet je lance un appel API
            callAPI();
        } else{
            displayNoInternet();
        }




    }

    private void displayNoInternet() {
        findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        findViewById(R.id.no_internet).setVisibility(View.VISIBLE);
    }

    private void callAPI() {
        //requete à l'API
        mOkHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url("http://www.omdbapi.com/?i=tt0076759&apikey=bf4e1adb&plot=full").build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

                if (response.isSuccessful()) {
                    final String stringJson = response.body().string();
                    //Log.d("LOL", stringJson);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // Code exécuté dans le Thread principal
                            Gson gson = new Gson();
                            mMovie = gson.fromJson(stringJson, Movie.class);
                            updateUi();
                            findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                            findViewById(R.id.linear_layout_top).setVisibility(View.VISIBLE);
                        }
                    });

                }
            }

        });

    }


    private void updateUi() {
        ((TextView) findViewById(R.id.text_view_titre)).setText(mMovie.getTitle());
        ((TextView) findViewById(R.id.text_view_date_sortie)).setText(mMovie.getReleased());
        ((TextView) findViewById(R.id.text_view_categorie)).setText(mMovie.getGenre());
        ((TextView) findViewById(R.id.text_view_description)).setText(mMovie.getPlot());
        ((TextView) findViewById(R.id.text_view_nom_actors)).setText(mMovie.getActors());
        ((TextView) findViewById(R.id.text_view_nom_awards)).setText(mMovie.getAwards());
        ((TextView) findViewById(R.id.text_view_nom_director)).setText(mMovie.getDirector());
        Picasso.get().load(mMovie.getPoster()).into(((ImageView) findViewById(R.id.image_film)));
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG", "MovieActivity: onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG", "MovieActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG", "MovieActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG", "MovieActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG", "MovieActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG", "MovieActivity: onStop()");
    }

    public void onClickLireLaSuite(View view) {
        TextView viewLireLasuite = (TextView) view;
        if (((TextView) findViewById(R.id.text_view_description)).getMaxLines() == 3) {
            ((TextView) findViewById(R.id.text_view_description)).setMaxLines(100);
            viewLireLasuite.setText("Lire moins");

        } else {
            ((TextView) findViewById(R.id.text_view_description)).setMaxLines(3);
        }


    }
}

