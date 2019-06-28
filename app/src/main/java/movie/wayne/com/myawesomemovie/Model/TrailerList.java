package movie.wayne.com.myawesomemovie.Model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrailerList {
    @SerializedName("results")
    private List<Trailer> mResult;

    public List<Trailer> getResult() {
        return mResult;
    }

    public void setResult(List<Trailer> mResult) {
        this.mResult = mResult;
    }
}
