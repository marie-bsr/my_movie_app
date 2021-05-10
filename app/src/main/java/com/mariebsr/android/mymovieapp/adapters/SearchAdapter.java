package com.mariebsr.android.mymovieapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mariebsr.android.mymovieapp.R;
import com.mariebsr.android.mymovieapp.activities.MovieActivity;
import com.mariebsr.android.mymovieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>{

    private Context mContext;
    private ArrayList <Movie> mMovies;

    public SearchAdapter(Context mContext, ArrayList<Movie> mMovies) {
        this.mContext = mContext;
        this.mMovies = mMovies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_search_movie_result, parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Movie movie = mMovies.get(position);
        Picasso.get().load(movie.getPoster()).into(holder.mImageViewPoster);
        holder.mTextViewTitle.setText(movie.getTitle());
        holder.mTextViewYear.setText(movie.getYear());
    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView mImageViewPoster;
        public TextView mTextViewTitle;
        public TextView mTextViewYear;
        public Movie mMovie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageViewPoster = itemView.findViewById(R.id.image_view_item_movie);
            mTextViewTitle = itemView.findViewById(R.id.text_view_item_movie_title);
            mTextViewYear = itemView.findViewById(R.id.text_view_item_movie_year);
        }

        public void onClick(View v) {
            Intent intent = new Intent(mContext, MovieActivity.class);
            intent.putExtra("imdb", mMovie.getImdbID());
            mContext.startActivity(intent);
        }
    }
}
