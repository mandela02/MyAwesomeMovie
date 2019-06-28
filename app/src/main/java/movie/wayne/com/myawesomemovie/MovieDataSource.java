package movie.wayne.com.myawesomemovie;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class MovieDataSource extends DatabaseHelper {
    public MovieDataSource(Context context) {
        super(context);
    }

    public List<Integer> getMovieID() {
        List<Integer> result = new ArrayList<>();
        String[] Column = {FavoriteMovieDatabase.MovieEntry._ID,
                FavoriteMovieDatabase.MovieEntry.COLUMN_MOVIE_ID
        };
        SQLiteDatabase database = getWritableDatabase();
        String orderBY = FavoriteMovieDatabase.MovieEntry.COLUMN_MOVIE_ID;
        Cursor cursor = database.query(FavoriteMovieDatabase.MovieEntry.TABLE_NAME,
                Column,
                null,
                null,
                null,
                null,
                orderBY
        );
        try {
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndex(FavoriteMovieDatabase.MovieEntry.COLUMN_MOVIE_ID));
                    result.add(id);

                } while (cursor.moveToNext());
                return result;
            }

        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
            database.close();
        }
        return null;
    }

    public long insertMovie(int id) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(FavoriteMovieDatabase.MovieEntry.COLUMN_MOVIE_ID, id);
            return database.insert(FavoriteMovieDatabase.MovieEntry.TABLE_NAME, null, values);
        } catch (Exception e) {
            return -1;
        } finally {
            database.close();
        }
    }

    public boolean deleteByID(int id) {
        SQLiteDatabase database = getWritableDatabase();
        try {
            String whereClause = FavoriteMovieDatabase.MovieEntry.COLUMN_MOVIE_ID + " =?";
            String[] whereArgs = {String.valueOf(id)};
            return database.delete(FavoriteMovieDatabase.MovieEntry.TABLE_NAME, whereClause, whereArgs)
                    > 0;
        } catch (Exception e) {
            return false;
        } finally {
            database.close();
        }
    }

    public boolean isInDatabase(int id) {
        SQLiteDatabase database = getReadableDatabase();
        String whereClause = FavoriteMovieDatabase.MovieEntry.COLUMN_MOVIE_ID + "=?";
        String[] whereArgs = {String.valueOf(id)};
        Cursor cursor = database.query(FavoriteMovieDatabase.MovieEntry.TABLE_NAME,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null);
        try {
            return cursor.getCount() > 0;
        } catch (Exception e) {
            return false;
        } finally {
            cursor.close();
            database.close();
        }
    }

}
