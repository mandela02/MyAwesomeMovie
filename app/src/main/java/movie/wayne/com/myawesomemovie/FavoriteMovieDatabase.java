package movie.wayne.com.myawesomemovie;

import android.provider.BaseColumns;

public class FavoriteMovieDatabase {
    public class MovieEntry implements BaseColumns
    {
        public static final String TABLE_NAME = "tbl_movie";
        public static final String COLUMN_MOVIE_ID = "column_ID";
    }
}
