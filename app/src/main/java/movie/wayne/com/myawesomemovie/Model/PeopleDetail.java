package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

public class PeopleDetail {
    @SerializedName("birthday")
    private String mBirthday;
    @SerializedName("known_for_department")
    private String mKnowForDepartment;
    @SerializedName("deathday")
    private String mDeathDay;
    @SerializedName("id")
    private int mID;
    @SerializedName("name")
    private String mName;
    @SerializedName("gender")
    private int mGender;
    @SerializedName("biography")
    private String mBiography;
    @SerializedName("popularity")
    private String mPopularity;
    @SerializedName("profile_path")
    private String mProfilePath;
    @SerializedName("place_of_birth")
    private String mPlaceOfBirth;
    private String mPicture;

    public String getBirthday() {
        return mBirthday;
    }

    public void setBirthday(String mBirthday) {
        this.mBirthday = mBirthday;
    }

    public String getKnowForDepartment() {
        return mKnowForDepartment;
    }

    public void setKnowForDepartment(String mKnowForDepartment) {
        this.mKnowForDepartment = mKnowForDepartment;
    }

    public String getDeathday() {
        return mDeathDay;
    }

    public void setDeathday(String mDeathDay) {
        this.mDeathDay = mDeathDay;
    }

    public int getID() {
        return mID;
    }

    public void setID(int mID) {
        this.mID = mID;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public int getGender() {
        return mGender;
    }

    public void setGender(int mGender) {
        this.mGender = mGender;
    }

    public String getBiography() {
        return mBiography;
    }

    public void setBiography(String mBiography) {
        this.mBiography = mBiography;
    }

    public String getPopularity() {
        return mPopularity;
    }

    public void setPopularity(String mPopularity) {
        this.mPopularity = mPopularity;
    }

    public String getProfilePath() {
        return mProfilePath;
    }

    public void setProfilePath(String mProfilePath) {
        this.mProfilePath = mProfilePath;
    }

    public String getPlaceOfBirth() {
        return mPlaceOfBirth;
    }

    public void setPlaceOfBirth(String mPlaceOfBirth) {
        this.mPlaceOfBirth = mPlaceOfBirth;
    }

    public String getPicture() {
        return mPicture;
    }

    public void setPicture(String mPicture) {
        this.mPicture = mPicture;
    }
}
