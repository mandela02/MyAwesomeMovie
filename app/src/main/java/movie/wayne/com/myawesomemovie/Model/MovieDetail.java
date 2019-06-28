package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieDetail {
    @SerializedName("id")
    private int mId;
    @SerializedName("vote_average")
    private float mVoteAverage;
    @SerializedName("vote_count")
    private float mVoteCount;
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
    @SerializedName("genres")
    private List<Genre> mGenre;
    @SerializedName("tagline")
    private String mTagline;
    @SerializedName("production_companies")
    private List<ProductionCompanies> mCompanies;
    @SerializedName("runtime")
    private String mRuntime;
    private String mMoviePoster;
    private String mBackdrop;

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public float getVoteAverage() {
        return mVoteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        mVoteAverage = voteAverage;
    }

    public float getVoteCount() {
        return mVoteCount;
    }

    public void setVoteCount(float voteCount) {
        mVoteCount = voteCount;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        mOriginalTitle = originalTitle;
    }

    public String getTagline() {
        return mTagline;
    }

    public void setTagline(String tagline) {
        mTagline = tagline;
    }

    public List<ProductionCompanies> getCompanies() {
        return mCompanies;
    }

    public void setCompanies(
        List<ProductionCompanies> companies) {
        mCompanies = companies;
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

    public String getPosterPath() {
        return mPosterPath;
    }

    public void setPosterPath(String posterPath) {
        mPosterPath = posterPath;
    }

    public String getBackdropPath() {
        return mBackdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        mBackdropPath = backdropPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        mOverview = overview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        mReleaseDate = releaseDate;
    }

    public List<Genre> getGenre() {
        return mGenre;
    }

    public void setGenre(List<Genre> genre) {
        mGenre = genre;
    }

    public String getRuntime() {
        return mRuntime;
    }

    public void setRuntime(String runtime) {
        mRuntime = runtime;
    }
}
