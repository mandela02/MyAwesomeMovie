package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

public class ReviewList {
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("id")
    private String mID;
    @SerializedName("url")
    private String mUrl;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String mAuthor) {
        this.mAuthor = mAuthor;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(String mUrl) {
        this.mUrl = mUrl;
    }
}
