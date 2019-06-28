package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

public class Review {
    @SerializedName("author")
    private String mAuthor;
    @SerializedName("content")
    private String mContent;
    @SerializedName("id")
    private String mID;
    @SerializedName("url")
    private String mUrl;
    @SerializedName("media_title")
    private String mTitle;
    @SerializedName("media_type")
    private String mType;
    @SerializedName("media_id")
    private String mMediaId;

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

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getType() {
        return mType;
    }

    public void setType(String mType) {
        this.mType = mType;
    }

    public String getMediaId() {
        return mMediaId;
    }

    public void setMediaId(String mMediaId) {
        this.mMediaId = mMediaId;
    }
}
