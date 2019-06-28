package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

public class ProductionCompanies {
    @SerializedName("id")
    private int mId;
    @SerializedName("logo_path")
    private String mLogoPath;
    @SerializedName("name")
    private String mName;
    @SerializedName("origin_country")
    private String mOriginalCountry;
}
