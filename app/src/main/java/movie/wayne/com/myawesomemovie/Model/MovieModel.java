package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bruce Wayne on 7/17/2017.
 */
public class MovieModel{
    @SerializedName("results")
    private List<Movie> mResults;
    @SerializedName("dates")
    private Date mDate;
    @SerializedName("page")
    private int mPage;
    @SerializedName("total_pages")
    private int mTotalPages;

    public List<Movie> getResults() {
        return mResults;
    }

    public Date getDate() {
        return mDate;
    }

    public int getPage() {
        return mPage;
    }

    public int getTotalPages() {
        return mTotalPages;
    }
}
