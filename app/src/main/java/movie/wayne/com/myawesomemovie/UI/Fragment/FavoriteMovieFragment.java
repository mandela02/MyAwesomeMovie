package movie.wayne.com.myawesomemovie.UI.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Movie;
import movie.wayne.com.myawesomemovie.Model.MovieDetail;
import movie.wayne.com.myawesomemovie.Model.MovieModel;
import movie.wayne.com.myawesomemovie.MovieDataSource;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Adapter.EndlessRecyclerViewScrollListener;
import movie.wayne.com.myawesomemovie.UI.Adapter.MovieAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


/**
 * Created by Bruce Wayne on 7/16/2017.
 */
public class FavoriteMovieFragment extends Fragment implements View.OnClickListener {
    private List<Movie> mResults;
    private List<Integer> mMovieIdList;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private boolean mIsAllowLoadMore = true;
    private int mPage = 1;
    private ProgressBar mProgressMovie;
    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private FloatingActionButton mBtnChange;
    private boolean mIsGrid = false;
    public String mUserID;
    private MovieDataSource mDatabase;

    public FavoriteMovieFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        initView(view);
        getData();
        initRecyclerView();
        getMovies();
        return view;
    }

    public void initView(View view) {
        mResults = new ArrayList<>();
        mMovieIdList = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recycler_movie);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mGridLayoutManager = new GridLayoutManager(getContext(), Const.COLUMN_NUMB);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mProgressMovie = view.findViewById(R.id.progress_movie);
        mBtnChange = view.findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(this);
        Log.d(TAG, "UserID: " + mUserID);

    }

    public void setDialog() {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        //Yes button clicked
                        break;

                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("U have no favorite movie. Go check some :)").setPositiveButton("Yes", dialogClickListener).show();
    }

    public void getMovies() {
        if (mMovieIdList != null && !mMovieIdList.isEmpty()) {
            for (int i = 0; i < mMovieIdList.size(); i++) {
                MovieService service = ServiceGenerator.createService(MovieService.class);
                service.getMovieDetail(mMovieIdList.get(i), Const.API_KEY).enqueue(new Callback<MovieDetail>() {
                    @Override
                    public void onResponse(Call<MovieDetail> call,
                                           Response<MovieDetail> response) {
                        if (response != null) {
                            List<Integer> genres = new ArrayList<>();
                            MovieDetail model = response.body();
                            for (int j = 0; j < model.getGenre().size(); j++)
                                genres.add(model.getGenre().get(j).getId());
                            Movie movie = new Movie(model.getId(), model.getVoteAverage(), model.getTitle(), model.getPosterPath(), model.getOverview(), model.getReleaseDate(), genres, true);
                            mResults.add(movie);
                            mMovieAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetail> call, Throwable t) {
                    }
                });
            }
        }
        else setDialog();
    }


    public void getData() {
        mDatabase = new MovieDataSource(getContext());
        mMovieIdList = mDatabase.getMovieID();
    }


    public void initRecyclerView() {
        mMovieAdapter = new MovieAdapter(mResults, getContext(), mIsGrid, true);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                mIsGrid = !mIsGrid;
                mRecyclerView
                        .setLayoutManager(mIsGrid ? mGridLayoutManager : mLinearLayoutManager);
                mBtnChange.setImageResource(mIsGrid ? R.drawable.ic_list : R.drawable.ic_option);
                initRecyclerView();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMovieAdapter.notifyDataSetChanged();
    }
}
