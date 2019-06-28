package movie.wayne.com.myawesomemovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bruce Wayne on 7/17/2017.
 */
public class Movie implements Parcelable {
    @SerializedName("id")
    private int mId;
    @SerializedName("vote_average")
    private float mVoteAverage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("original_title")
    private String mOriginalTitle;
    @SerializedName("poster_path")
    private String mPosterPath;
    @SerializedName("backdrop_path")
    private String mBackdropPath;
    @SerializedName("overview")
    private String mOverview;
    @SerializedName("release_date")
    private String mReleaseDate;
    @SerializedName("genre_ids")
    private List<Integer> mGenreIds;
    private String mMoviePoster;
    private String mBackdrop;
    private boolean liked = false;

    private String mUserID;

    @SerializedName("character")
    private String mCharacter;
    @SerializedName("department")
    private String mDepartment;

    public Movie(int mId, float mVoteAverage, String mTitle, String mPosterPath, String mOverview, String mReleaseDate, List<Integer> mGenreIds, boolean liked) {
        this.mId = mId;
        this.mVoteAverage = mVoteAverage;
        this.mTitle = mTitle;
        this.mPosterPath = mPosterPath;
        this.mOverview = mOverview;
        this.mReleaseDate = mReleaseDate;
        this.mGenreIds = mGenreIds;
        this.liked = liked;
    }

    public Movie(int id) {
        mId = id;
    }

    protected Movie(Parcel in) {
        mId = in.readInt();
        mVoteAverage = in.readFloat();
        mTitle = in.readString();
        mOriginalTitle = in.readString();
        mPosterPath = in.readString();
        mBackdropPath = in.readString();
        mOverview = in.readString();
        mReleaseDate = in.readString();
        mUserID = in.readString();
        mCharacter = in.readString();
        mDepartment = in.readString();
        liked = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(mId);
        dest.writeFloat(mVoteAverage);
        dest.writeString(mTitle);
        dest.writeString(mOriginalTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mBackdropPath);
        dest.writeString(mOverview);
        dest.writeString(mReleaseDate);
        dest.writeString(mUserID);
        dest.writeString(mCharacter);
        dest.writeString(mDepartment);
        dest.writeByte((byte) (liked ? 1 : 0));
    }

    public int getId() {
        return mId;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public List<Integer> getGenreIds() {
        return mGenreIds;
    }

    public String getMoviePoster() {
        return mMoviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        mMoviePoster = moviePoster;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public void setBackdrop(String backdrop) {
        mBackdrop = backdrop;
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean mIsLiked) {
        this.liked = mIsLiked;
    }

    public String getUserID() {
        return mUserID;
    }

    public void setUserID(String mUserID) {
        this.mUserID = mUserID;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String mCharacter) {
        this.mCharacter = mCharacter;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String mDepartment) {
        this.mDepartment = mDepartment;
    }
}
