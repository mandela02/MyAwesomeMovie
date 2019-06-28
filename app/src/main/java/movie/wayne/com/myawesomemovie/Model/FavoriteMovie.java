package movie.wayne.com.myawesomemovie.Model;

import java.util.List;

public class FavoriteMovie {
    private String mID;
    private int Num_Of_Favorite_Movie;
    private List<Integer> Favorite_Movie_Ids;

    public FavoriteMovie() {
    }

    public FavoriteMovie(String mID, int num_Of_Favorite_Movie, List<Integer> favorite_Movie_Ids) {
        this.mID = mID;
        Num_Of_Favorite_Movie = num_Of_Favorite_Movie;
        Favorite_Movie_Ids = favorite_Movie_Ids;
    }

    public int getNumOfFavoriteMovie() {
        return Num_Of_Favorite_Movie;
    }

    public void setNumOfFavoriteMovie(int num_Of_Favorite_Movie) {
        this.Num_Of_Favorite_Movie = num_Of_Favorite_Movie;
    }

    public List<Integer> getFavoriteMovieIds() {
        return Favorite_Movie_Ids;
    }

    public void setFavoriteMovieIds(List<Integer> favorite_Movie_Ids) {
        this.Favorite_Movie_Ids = favorite_Movie_Ids;
    }

    public String getID() {
        return mID;
    }

    public void setID(String mID) {
        this.mID = mID;
    }
}
