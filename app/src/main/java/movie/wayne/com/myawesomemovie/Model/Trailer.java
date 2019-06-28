package movie.wayne.com.myawesomemovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Trailer implements Parcelable {
    @SerializedName("key")
    private String mKey;
    @SerializedName("id")
    private String mId;
    @SerializedName("name")
    private String mName;

    private String mFullImagePath;

    protected Trailer(Parcel in) {
        mKey = in.readString();
        mId = in.readString();
        mName = in.readString();
        mFullImagePath = in.readString();
    }

    public static final Creator<Trailer> CREATOR = new Creator<Trailer>() {
        @Override
        public Trailer createFromParcel(Parcel in) {
            return new Trailer(in);
        }

        @Override
        public Trailer[] newArray(int size) {
            return new Trailer[size];
        }
    };

    public String getFullImagePath() {
        return mFullImagePath;
    }

    public void setFullImagePath(String mFullImagePath) {
        this.mFullImagePath = mFullImagePath;
    }

    public String getKey() {
        return mKey;
    }

    public void setKey(String mKey) {
        this.mKey = mKey;
    }

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mKey);
        parcel.writeString(mId);
        parcel.writeString(mName);
        parcel.writeString(mFullImagePath);
    }
}
