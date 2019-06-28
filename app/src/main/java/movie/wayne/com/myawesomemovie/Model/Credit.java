package movie.wayne.com.myawesomemovie.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Credit implements Parcelable {
    @SerializedName("cast_id")
    private int mCastID;
    @SerializedName("character")
    private String mCharacter;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_path")
    private String mProfilePath;
    private String mProfile;
    @SerializedName("credit_id")
    private String mCreditId;
    @SerializedName("gender")
    private int mGender;
    @SerializedName("department")
    private String mDepartment;
    @SerializedName("id")
    private int mId;
    @SerializedName("job")
    private String mJob;

    protected Credit(Parcel in) {
        mCastID = in.readInt();
        mCharacter = in.readString();
        mName = in.readString();
        mProfilePath = in.readString();
        mProfile = in.readString();
        mCreditId = in.readString();
        mGender = in.readInt();
        mDepartment = in.readString();
        mId = in.readInt();
        mJob = in.readString();
    }

    public static final Creator<Credit> CREATOR = new Creator<Credit>() {
        @Override
        public Credit createFromParcel(Parcel in) {
            return new Credit(in);
        }

        @Override
        public Credit[] newArray(int size) {
            return new Credit[size];
        }
    };

    public int getCastID() {
        return mCastID;
    }

    public void setCastID(int castID) {
        mCastID = castID;
    }

    public String getCharacter() {
        return mCharacter;
    }

    public void setCharacter(String character) {
        mCharacter = character;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String profilePath) {
        mProfilePath = profilePath;
    }

    public String getProfile() {
        return mProfile;
    }

    public void setProfile(String profile) {
        mProfile = profile;
    }

    public String getCreditId() {
        return mCreditId;
    }

    public void setCreditId(String creditId) {
        mCreditId = creditId;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int gender) {
        mGender = gender;
    }

    public String getDepartment() {
        return mDepartment;
    }

    public void setDepartment(String department) {
        mDepartment = department;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getJob() {
        return mJob;
    }

    public void setJob(String job) {
        mJob = job;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(mCastID);
        parcel.writeString(mCharacter);
        parcel.writeString(mName);
        parcel.writeString(mProfilePath);
        parcel.writeString(mProfile);
        parcel.writeString(mCreditId);
        parcel.writeInt(mGender);
        parcel.writeString(mDepartment);
        parcel.writeInt(mId);
        parcel.writeString(mJob);
    }
}
