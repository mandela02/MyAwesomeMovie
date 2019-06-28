package movie.wayne.com.myawesomemovie.Firebase;

import android.content.Context;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import movie.wayne.com.myawesomemovie.Model.Account;
import movie.wayne.com.myawesomemovie.Model.FavoriteMovie;
import movie.wayne.com.myawesomemovie.Model.Genre;

import static android.content.ContentValues.TAG;

public class Database {
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference genresRef = database.getReference("genres");
    private DatabaseReference AccountRef = database.getReference("Account");

    public void getAllGenres(final Context context, final DatabaseCallback callback) {
        Query query1 = genresRef.orderByChild("name");
        Log.d(TAG, "getAllGenres: begin progress");
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Genre> map = new HashMap<>();
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();
                for (DataSnapshot child : children) {
                    Genre p = child.getValue(Genre.class);
                    map.put(child.getKey(), p);
                }
                ArrayList<Genre> values = new ArrayList<>(map.values());
                callback.onGenresSuccess(values);
                /*for (int j = 0; j < values.size(); j++)
                    Log.d(Constraints.TAG, "onGenresSuccess: " + values.get(j).getId() + ": "+ values
                        .get(j).getName());*/
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(TAG, "onCancelled: " + databaseError);
            }
        });
        Log.d(TAG, "getAllGenres: done");
    }

    public void searchInDatabase(final String userID, final String userPassword,
                                 final DatabaseCallback callback) {
        Query query1 = AccountRef.orderByChild("id").equalTo
                (userID);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists())
                    getUserPass(userPassword, dataSnapshot, callback);
                else
                    callback.onFail("Can't find this ID");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFail(databaseError.getMessage());
            }
        });
    }

    public void getUserPass(String userPassword, DataSnapshot dataSnapshot,
                            DatabaseCallback callback) {
        Map<String, Account> map = new HashMap<String, Account>();
        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            Account p = child.getValue(Account.class);
            map.put(child.getKey(), p);
        }
        ArrayList<Account> values = new ArrayList<>(map.values());
        if (!values.isEmpty()) {
            Log.d(TAG, "Name is: " + values.get(0).getID() + "Password is: " + values.get(0)
                    .getPassword());
            if (values.get(0).getPassword().equals(userPassword)) {
                callback.onUserSuccess(values.get(0));
            } else {
                callback.onFail("Wrong Password");
            }
        }
    }

    public void searchUserById(String userID, final DatabaseCallback callback)
    {
        Query query1 = AccountRef.orderByChild("id").equalTo
                (userID);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists())
                    getUserByID(dataSnapshot, callback);
                else
                    callback.onFail("can't find this user");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                callback.onFail(databaseError.getMessage());
            }
        });
    }

    private void getUserByID(DataSnapshot dataSnapshot, DatabaseCallback callback) {
        Map<String, Account> td = new HashMap<>();

        Iterable<DataSnapshot> children = dataSnapshot.getChildren();
        for (DataSnapshot child : children) {
            Account p = child.getValue(Account.class);
            td.put(child.getKey(), p);
        }

        ArrayList<Account> values = new ArrayList<Account>(td.values());

        if (!values.isEmpty()) {
            callback.onUserSuccess(values.get(0));
        } else {
            callback.onFail("U Dead Fuck U");
        }
    }


}
