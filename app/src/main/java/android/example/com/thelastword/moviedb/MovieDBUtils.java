package android.example.com.thelastword.moviedb;

/**
 * Created by Joel on 01/04/2017.
 */

public class MovieDBUtils {

    private MovieDBUtils(){

    }
    //TODO input your own key
    public final static String movieDBKey = "";
    public final static String urlBaseURL = "http://image.tmdb.org/t/p/w342/";

    //ParceableKeys
    public final static String parceable_movie = "MOVIE";

    //Change to URI builder if this becomes anymore complex
    public final static String movieDBPopularMoviesQuery = "https://api.themoviedb.org/3/movie/popular?api_key=" + movieDBKey + "&language=en-US&page=1";
    public final static String movieDBTopMoviesQuery = "https://api.themoviedb.org/3/movie/top_rated?api_key=" + movieDBKey + "&language=en-US&page=1";


}
