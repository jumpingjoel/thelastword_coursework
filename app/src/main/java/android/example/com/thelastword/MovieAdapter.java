package android.example.com.thelastword;

import android.content.Context;
import android.example.com.thelastword.moviedb.MovieDBPojo;
import android.example.com.thelastword.moviedb.MovieDBUtils;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


/**
 * Created by Joel on 18/03/2017.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder>  {

    private static String TAG;

    private MovieDBPojo[] movies = {};

    private int imageHeight;

    private MovieAdapterOnClickHandler mOnClickHandler;

    public MovieAdapter(MovieAdapterOnClickHandler mOnClickHandler, int imageHeight){
        this.mOnClickHandler = mOnClickHandler;
        this.imageHeight = imageHeight;
        TAG = this.getClass().getName();
    }


    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForGridItem = R.layout.movie_grid_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForGridItem, viewGroup, shouldAttachToParentImmediately);

        return new MovieAdapterViewHolder(view);

    }

    @Override
    public void onBindViewHolder(final MovieAdapterViewHolder holder, int position) {

        MovieDBPojo movie = movies[position];
        String posterURL = MovieDBUtils.urlBaseURL + movie.getPoster_path();
        Log.i(TAG, "Loading PosterURL:" + posterURL);
        Picasso.with(holder.itemView.getContext())
                .load(posterURL)
                .placeholder(R.drawable.blank_image)
                .resize(0, imageHeight).into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        return movies.length;
    }



    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final TextView movieTextView;
        public final ImageView imageView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            movieTextView = (TextView) itemView.findViewById(R.id.movie_data);
            imageView = (ImageView) itemView.findViewById(R.id.movie_image_view);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            MovieDBPojo movie = movies[adapterPosition];
            mOnClickHandler.onClick(movie);

        }

    }

    public void setMovies(MovieDBPojo[] movies){
        this.movies = movies;
        notifyDataSetChanged();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface MovieAdapterOnClickHandler {
        void onClick(MovieDBPojo movie);
    }
}
