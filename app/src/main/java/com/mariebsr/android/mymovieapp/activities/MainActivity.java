package com.mariebsr.android.mymovieapp.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mariebsr.android.mymovieapp.R;

public class MainActivity extends AppCompatActivity {

    private TextView mTextViewWelcome;
    private Button mButtonSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //relier l'objet à son élément du layout grace à son id
        mTextViewWelcome = (TextView) findViewById(R.id.text_view_welcome);
        //message qui apparait et disparait
        Toast.makeText(this, "Bonjour Marie!", Toast.LENGTH_SHORT).show();
        //modifier le texte
        mTextViewWelcome.setText(R.string.bienvenue_marie);
        mTextViewWelcome.setTextColor(getResources().getColor(R.color.colorPrimaryDark));
        //lier le bouton à la vue par son id
        mButtonSearch = findViewById(R.id.button_rechercher);
        /* une façon de gérer le click avec onClickListener, autre façon: attribut onClick dans la vue  et methode associée
        mButtonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lol", "onClick: Clic sur bouton recherche");
            }
        });
        */
        Log.d("TAG","MainActivity: onCreate()");

    }



    public void onClickFilm(View view) {

        Intent intent = new Intent(this, MovieActivity.class);
        switch (view.getId()) {
            case R.id.linear_layout_batman:
                Toast.makeText(getApplicationContext(), "Clic sur film1", Toast.LENGTH_SHORT).show();
                intent.putExtra("EXTRA_MESSAGE","Movie1");
                break;
            case R.id.linear_layout_starwars:
                Toast.makeText(getApplicationContext(), "Clic sur film2", Toast.LENGTH_SHORT).show();
                intent.putExtra("EXTRA_MESSAGE","Movie2");
                break;
        }


        startActivity(intent);
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("TAG","MainActivity: onDestroy()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("TAG","MainActivity: onStart()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d("TAG","MainActivity: onRestart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("TAG","MainActivity: onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("TAG","MainActivity: onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("TAG","MainActivity: onStop()");
    }

    //click sur bouton recherche
    public void onClickSearch(View view) {

        Intent intent = new Intent(this, SearchActivity.class);
        //intent.putExtra("KEY","Recherche");
        startActivity(intent);

    }
}
