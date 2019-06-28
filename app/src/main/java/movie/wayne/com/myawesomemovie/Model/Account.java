package movie.wayne.com.myawesomemovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Account implements Parcelable {
    private String mName;
    private String mPassword;
    private String mID;

    protected Account(Parcel in) {
        mName = in.readString();
        mPassword = in.readString();
        mID = in.readString();
    }

    public static final Creator<Account> CREATOR = new Creator<Account>() {
        @Override
        public Account createFromParcel(Parcel in) {
            return new Account(in);
        }

        @Override
        public Account[] newArray(int size) {
            return new Account[size];
        }
    };

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getID() {
        return mID;
    }

    public void setID(String ID) {
        mID = ID;
    }

    public Account() {
    }

    public Account(String mName, String mPassword, String mID) {
        this.mName = mName;
        this.mPassword = mPassword;
        this.mID = mID;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(mName);
        parcel.writeString(mPassword);
        parcel.writeString(mID);
    }
}
