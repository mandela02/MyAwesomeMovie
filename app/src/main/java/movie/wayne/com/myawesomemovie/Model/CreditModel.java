package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CreditModel {
    @SerializedName("id")
    private int mId;
    @SerializedName("cast")
    private List<Credit> mCast;
    @SerializedName("crew")
    private List<Credit> mCrew;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public List<Credit> getCast() {
        return mCast;
    }

    public void setCast(List<Credit> cast) {
        mCast = cast;
    }

    public List<Credit> getCrew() {
        return mCrew;
    }

    public void setCrew(List<Credit> crew) {
        mCrew = crew;
    }
}
