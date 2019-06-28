package movie.wayne.com.myawesomemovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Bruce Wayne on 7/17/2017.
 */
public class Date implements Parcelable {
    @SerializedName("maximum")
    private String mMaximum;
    @SerializedName("minimum")
    private String mMinimum;

    protected Date(Parcel in) {
        mMaximum = in.readString();
        mMinimum = in.readString();
    }

    public static final Creator<Date> CREATOR = new Creator<Date>() {
        @Override
        public Date createFromParcel(Parcel in) {
            return new Date(in);
        }

        @Override
        public Date[] newArray(int size) {
            return new Date[size];
        }
    };

    public String getMaximum() {
        return mMaximum;
    }

    public String getMinimum() {
        return mMinimum;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mMaximum);
        dest.writeString(mMinimum);
    }
}
