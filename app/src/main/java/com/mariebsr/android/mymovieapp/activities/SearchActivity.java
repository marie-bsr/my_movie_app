package com.mariebsr.android.mymovieapp.activities;

import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.mariebsr.android.mymovieapp.R;
import com.mariebsr.android.mymovieapp.adapters.SearchAdapter;
import com.mariebsr.android.mymovieapp.models.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private SearchAdapter mAdapter;
    private ArrayList<Movie> mMovies;
    private OkHttpClient mOkHttpClient;
    private SearchView mSearchView;
    private Button mBoutonRechercher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mOkHttpClient = new OkHttpClient();

        mMovies = new ArrayList<>();

        mRecyclerView = findViewById(R.id.my_recycler_view);
        mAdapter = new SearchAdapter(this, mMovies);

        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        mRecyclerView.setAdapter(mAdapter);
    }

    public void onClickButtonSearch(View view) {

        String strRecherche = ((EditText) findViewById(R.id.edit_text_search)).getText().toString();
        mMovies.clear();
        searchByMovieName(strRecherche);
    }

    private void searchByMovieName(String strRecherche) {

        String s = "http://www.omdbapi.com/?s=" + strRecherche + "&apikey=bf4e1adb";
        Log.d("lol", s);

        // Change la visibilité entre le "loader" et le text "no result"
        findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        findViewById(R.id.text_view_no_result_found).setVisibility(View.INVISIBLE);
        findViewById(R.id.my_recycler_view).setVisibility(View.INVISIBLE);

        Request request = new Request.Builder().url(s).build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        failUI();
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String stringJson = response.body().string();
                Log.d("lol", "onResponse ------> " + stringJson);

                if (response.isSuccessful())
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateUI(stringJson);
                        }
                    });
                else
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            failUI();
                        }
                    });
            }
        });
    }

    private void updateUI(String stringJson) {

        findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        findViewById(R.id.text_view_no_result_found).setVisibility(View.INVISIBLE);
        findViewById(R.id.my_recycler_view).setVisibility(View.VISIBLE);

        try {
            // On parse à l'ancienne le Json
            JSONObject jsonObject = new JSONObject(stringJson);
            JSONArray jsonArray = jsonObject.getJSONArray("Search");

            for (int i = 0; i < jsonArray.length(); i++) {

                // Utilisation de GSON pour les résultats : Movie
                Movie result = new Gson().fromJson(jsonArray.getJSONObject(i).toString(), Movie.class);
                mMovies.add(result);
            }

            mAdapter.notifyDataSetChanged();

        } catch (JSONException e) {
            failUI();
        }
    }

    private void failUI() {
        findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
        findViewById(R.id.text_view_no_result_found).setVisibility(View.VISIBLE);
        findViewById(R.id.my_recycler_view).setVisibility(View.INVISIBLE);
    }
}
