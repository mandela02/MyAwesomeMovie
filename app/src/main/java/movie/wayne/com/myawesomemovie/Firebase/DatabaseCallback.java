package movie.wayne.com.myawesomemovie.Firebase;

import java.util.ArrayList;

import movie.wayne.com.myawesomemovie.Model.Account;
import movie.wayne.com.myawesomemovie.Model.FavoriteMovie;
import movie.wayne.com.myawesomemovie.Model.Genre;

public interface DatabaseCallback {
    void onFail(String message);
    void onGenresSuccess(ArrayList<Genre> genresList);
    void onUserSuccess(Account value);
}
