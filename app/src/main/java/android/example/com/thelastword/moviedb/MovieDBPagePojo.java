package android.example.com.thelastword.moviedb;

/**
 * Created by Joel on 26/03/2017.
 */
public class MovieDBPagePojo {

    private int page;
    private MovieDBPojo[] results;

    public MovieDBPagePojo(int page, MovieDBPojo[] results) {
        this.page = page;
        this.results = results;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public MovieDBPojo[] getResults() {
        return results;
    }

    public void setResults(MovieDBPojo[] results) {
        this.results = results;
    }


}
