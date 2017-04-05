package android.example.com.thelastword;

import android.content.Context;
import android.content.Intent;
import android.example.com.thelastword.moviedb.MovieDBPagePojo;
import android.example.com.thelastword.moviedb.MovieDBPojo;
import android.example.com.thelastword.moviedb.MovieDBUtils;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    private RecyclerView mRecyclerView;
    private TextView mErrorDisplay;
    private MovieAdapter mMovieAdapter;
    private ProgressBar mLoadingIndicator;
    private final static String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int numberOfColumnsInGrid = 2;
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int imageHeight = size.y /numberOfColumnsInGrid;


        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_movies);

        //This is still a grid! I like the look with just one column..
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, numberOfColumnsInGrid);

        mRecyclerView.setLayoutManager(gridLayoutManager);

        mMovieAdapter = new MovieAdapter(this, imageHeight);

        /* Setting the adapter attaches it to the RecyclerView in our layout. */
        mRecyclerView.setAdapter(mMovieAdapter);

        //It is unknown how many elements there will be
        mRecyclerView.setHasFixedSize(true);

        mErrorDisplay = (TextView) findViewById(R.id.movie_error_message_display);
        mLoadingIndicator = (ProgressBar) findViewById(R.id.movie_loading_indicator);

        refreshMovies(R.id.search_most_popular);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        refreshMovies(item.getItemId());
        return true;
    }


    protected static String readUrl(String urlString) {

        String result = null;
        try{
            BufferedReader reader = null;
            try {
                URL url = new URL(urlString);
                reader = new BufferedReader(new InputStreamReader(url.openStream()));
                StringBuffer buffer = new StringBuffer();
                int read;
                char[] chars = new char[1024];
                while ((read = reader.read(chars)) != -1)
                    buffer.append(chars, 0, read);
                    result =  buffer.toString();
            } finally {
                if (reader != null)
                    reader.close();
            }
        } catch(Exception e){
            Log.e(TAG, "Issue reading URL", e);
        }
        return result;
    }

    private MovieDBPagePojo createMovieDBPagePojoFromJson(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, MovieDBPagePojo.class);
    }


    private void showRecyclerView() {
        /* First, make sure the error is invisible */
        mErrorDisplay.setVisibility(View.INVISIBLE);
        /* Then, make sure the weather data is visible */
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    public  void refreshMovies(int searchID){
        showRecyclerView();

        switch (searchID) {

            case R.id.search_most_popular:
                new FetchMovieTask().execute(MovieDBUtils.movieDBPopularMoviesQuery);
                return;
            case R.id.search_top_rated:
                new FetchMovieTask().execute(MovieDBUtils.movieDBTopMoviesQuery);
                return;
        }

    }

    private  void showErrorMessage(){

        if(!isOnline()){
            mErrorDisplay.setText(R.string.network_error_message);
        } else {
            mErrorDisplay.setText(R.string.error_message);
        }
        mErrorDisplay.setVisibility(View.VISIBLE);
        mRecyclerView.setVisibility(View.INVISIBLE);
    }

    public class FetchMovieTask extends AsyncTask<String, Void, MovieDBPagePojo> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }

        @Override
        protected MovieDBPagePojo doInBackground(String... strings) {

            MovieDBPagePojo movieDBPojo = createMovieDBPagePojoFromJson(readUrl(strings[0]));
            return movieDBPojo;
        }

        @Override
        protected void onPostExecute(MovieDBPagePojo movies) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);

            if (movies != null && movies.getResults().length > 0) {
                showRecyclerView();
                mMovieAdapter.setMovies(movies.getResults());

            } else {
                showErrorMessage();
            }
        }
    }

    @Override
    public void onClick(MovieDBPojo movie) {

        Context context = this;
        Class destinationClass = MovieActivity.class;
        Intent intentToStartMovieActivity = new Intent(context, destinationClass);
        intentToStartMovieActivity.putExtra(MovieDBUtils.parceable_movie, movie);
        startActivity(intentToStartMovieActivity);

    }

    public boolean isOnline(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}
