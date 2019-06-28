package movie.wayne.com.myawesomemovie.UI.Fragment;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
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
import movie.wayne.com.myawesomemovie.Model.MovieModel;
import movie.wayne.com.myawesomemovie.Model.Movie;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Adapter.EndlessRecyclerViewScrollListener;
import movie.wayne.com.myawesomemovie.UI.Adapter.MovieAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;


/**
 * Created by Bruce Wayne on 7/16/2017.
 */
public class NowPlayingFragment extends Fragment implements View.OnClickListener {
    private List<Movie> mResults;
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

    public NowPlayingFragment() {
    }


    @SuppressLint("ValidFragment")
    public NowPlayingFragment(String mUserID) {
        this.mUserID = mUserID;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        mResults = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recycler_movie);
        mLinearLayoutManager = new LinearLayoutManager(getContext());
        mGridLayoutManager = new GridLayoutManager(getContext(), Const.COLUMN_NUMB);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mProgressMovie = view.findViewById(R.id.progress_movie);
        mBtnChange = view.findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(this);
        //setDialog();
        getMovies();
        initRecyclerView();
        Log.d(TAG, "UserID: " + mUserID);
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void getMovies() {
        mProgressMovie.setVisibility(View.VISIBLE);
        mIsAllowLoadMore = false;
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovies(Const.API_KEY, mPage).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response != null) {
                    MovieModel model = response.body();
                    mResults.addAll(model.getResults());
                    if (mPage == model.getTotalPages())
                        mIsAllowLoadMore = false;
                    else
                        mIsAllowLoadMore = true;
                    mMovieAdapter.notifyDataSetChanged();
                }
                 /*   if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();*/
                mProgressMovie.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                if (getActivity() == null) return;
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    /*if (mProgressDialog.isShowing())
                        mProgressDialog.dismiss();
                    */
                mProgressMovie.setVisibility(View.GONE);
                mIsAllowLoadMore = true;
            }
        });
        mPage++;
    }

    public void initRecyclerView() {
        mMovieAdapter = new MovieAdapter(mResults, getContext(), mIsGrid, false);
        mRecyclerView.setAdapter(mMovieAdapter);
        mRecyclerView
                .addOnScrollListener(new EndlessRecyclerViewScrollListener(
                        mIsGrid ? mGridLayoutManager : mLinearLayoutManager) {
                    @Override
                    public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                        if (mIsAllowLoadMore) {
                            getMovies();
                        }
                    }
                });
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
