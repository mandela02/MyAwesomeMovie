package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PeopleModel  {
    @SerializedName("id")
    private String mID;
    @SerializedName("cast")
    private List<Movie> mCast;
    @SerializedName("crew")
    private List<Movie> mCrew;

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public List<Movie> getCast() {
        return mCast;
    }

    public void setCast(List<Movie> mCast) {
        this.mCast = mCast;
    }

    public List<Movie> getCrew() {
        return mCrew;
    }

    public void setCrew(List<Movie> mCrew) {
        this.mCrew = mCrew;
    }
}
