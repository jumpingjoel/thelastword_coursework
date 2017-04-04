package android.example.com.thelastword;

import android.content.Intent;
import android.example.com.thelastword.moviedb.MovieDBPojo;
import android.example.com.thelastword.moviedb.MovieDBUtils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

public class MovieActivity extends AppCompatActivity {

    private TextView movieTitle;
    private ImageView moviePoster;
    private TextView userRating;
    private TextView overview;
    private TextView releaseDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        movieTitle = (TextView) findViewById(R.id.activity_movie_movie_title);
        moviePoster = (ImageView) findViewById(R.id.activity_movie_movie_poster);
        userRating = (TextView) findViewById(R.id.activity_movie_movie_rating);
        overview = (TextView) findViewById(R.id.activity_movie_movie_overview);
        releaseDate = (TextView) findViewById(R.id.activity_movie_release_date);

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(MovieDBUtils.parceable_movie)) {
                MovieDBPojo movie = intentThatStartedThisActivity.getParcelableExtra(MovieDBUtils.parceable_movie);
                movieTitle.setText(movie.getTitle());
                overview.setText(movie.getOverview());
                userRating.setText("User Rating: " + movie.getVote_average());
                releaseDate.setText("Release Date: " + movie.getRelease_date());
                String posterURL = MovieDBUtils.urlBaseURL + movie.getPoster_path();

                Picasso.with(this)
                        .load(posterURL)
                        .placeholder(R.drawable.blank_image).into(moviePoster);
            }
        }
    }
}
