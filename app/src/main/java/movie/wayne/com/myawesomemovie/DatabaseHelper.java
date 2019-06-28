package movie.wayne.com.myawesomemovie;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {


    private final static String DB_NAME = "FavoriteMovie.db";
    private final static int DB_VERSION = 1;
    private final static String COMMAND_CREATE_TABLE =
            "CREATE TABLE " + FavoriteMovieDatabase.MovieEntry.TABLE_NAME
                    + "("
                    + FavoriteMovieDatabase.MovieEntry._ID
                    + " INTEGER PRIMARY KEY NOT NULL, "
                    + FavoriteMovieDatabase.MovieEntry.COLUMN_MOVIE_ID
                    + " INTEGER"
                    + ");"
            ;

    private final static String COMMAND_DELETE_TABLE =
            "DROP TABLE " + FavoriteMovieDatabase.MovieEntry.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);

    }

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(COMMAND_DELETE_TABLE);
        sqLiteDatabase.execSQL(COMMAND_CREATE_TABLE);
    }
}
