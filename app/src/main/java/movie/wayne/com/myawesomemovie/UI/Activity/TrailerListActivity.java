package movie.wayne.com.myawesomemovie.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Movie;
import movie.wayne.com.myawesomemovie.Model.Trailer;
import movie.wayne.com.myawesomemovie.Model.TrailerList;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Adapter.TrailerAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TrailerListActivity extends AppCompatActivity {


    public static Intent getInstance(Context context, Movie result) {
        Intent intent = new Intent(context, TrailerListActivity.class);
        intent.putExtra(Const.Extra.EXTRA_RESULT, result);
        return intent;
    }

    private int mMovieID;
    private List<Trailer> mTrailerList;
    private TrailerAdapter mTrailerAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailer_list);
        mTrailerList = new ArrayList<>();
        getMovie();
        getMovieTrailer();
        mRecyclerView = findViewById(R.id.recycler_trailerList);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mTrailerAdapter = new TrailerAdapter(mTrailerList, this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.setAdapter(mTrailerAdapter);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public void getMovie() {
        Intent intent = getIntent();
        Movie mResult = intent.getParcelableExtra(Const.Extra.EXTRA_RESULT);
        mMovieID = mResult.getId();
        setTitle(mResult.getTitle());
    }


    public void getMovieTrailer() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieVideos(mMovieID, Const.API_KEY).enqueue(new Callback<TrailerList>() {
            @Override
            public void onResponse(Call<TrailerList> call, Response<TrailerList> response) {
                if (response != null) {
                    TrailerList model = response.body();
                    mTrailerList.addAll(model.getResult());
                    mTrailerAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onFailure(Call<TrailerList> call, Throwable t) {

            }

        });
    }


}
