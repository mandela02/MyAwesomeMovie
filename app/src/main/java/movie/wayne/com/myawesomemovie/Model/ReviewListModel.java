package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewListModel {
    @SerializedName("id")
    private int mID;
    @SerializedName("results")
    private List<ReviewList> mReviewList;
    @SerializedName("page")
    private int mPage;
    @SerializedName("total_pages")
    private int mTotalPages;

    public int getTotalPages() {
        return mTotalPages;
    }

    public void setTotalPages(int mTotalPages) {
        this.mTotalPages = mTotalPages;
    }

    public int getPage() {
        return mPage;
    }

    public void setPage(int mPage) {
        this.mPage = mPage;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public List<ReviewList> getReviews() {
        return mReviewList;
    }

    public void setReviews(List<ReviewList> mReviewLists) {
        this.mReviewList = mReviewLists;
    }
}
