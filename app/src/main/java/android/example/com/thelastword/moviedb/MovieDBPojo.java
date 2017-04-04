package android.example.com.thelastword.moviedb;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Joel on 26/03/2017.
 */
public class MovieDBPojo implements Parcelable {

    private String poster_path;
    private String title;
    private String overview;

    private String release_date;
    private double vote_average;

    public MovieDBPojo(String poster_path, String title, String overview, double vote_average, String release_date) {
        this.poster_path = poster_path;
        this.title = title;
        this.overview = overview;
        this.vote_average = vote_average;
        this.release_date = release_date;
    }


    protected MovieDBPojo(Parcel in) {
        poster_path = in.readString();
        title = in.readString();
        overview = in.readString();
        vote_average = in.readDouble();
        release_date = in.readString();
    }

    public static final Creator<MovieDBPojo> CREATOR = new Creator<MovieDBPojo>() {
        @Override
        public MovieDBPojo createFromParcel(Parcel in) {
            return new MovieDBPojo(in);
        }

        @Override
        public MovieDBPojo[] newArray(int size) {
            return new MovieDBPojo[size];
        }
    };

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(poster_path);
        parcel.writeString(title);
        parcel.writeString(overview);
        parcel.writeDouble(vote_average);
        parcel.writeString(release_date);
    }
}
