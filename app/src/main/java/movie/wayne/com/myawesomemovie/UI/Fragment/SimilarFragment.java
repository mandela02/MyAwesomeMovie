package movie.wayne.com.myawesomemovie.UI.Fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.MovieModel;
import movie.wayne.com.myawesomemovie.Model.Movie;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Adapter.MovieAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by Bruce Wayne on 7/16/2017.
 */
public class SimilarFragment extends Fragment {
    private List<Movie> mResults;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private int mMovieID;
    private boolean mIsGrid;
    private ProgressDialog mProgressDialog;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private FloatingActionButton mFloatingActionButton;
    private String mUserID;


    public SimilarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        mResults = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recycler_movie);
        mRecyclerView.setPadding(0, 0, 0, 150);
        getData();
        mFloatingActionButton = view.findViewById(R.id.btn_change);
        mFloatingActionButton.setVisibility(View.GONE);
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mGridLayoutManager = new GridLayoutManager(getActivity(), Const.COLUMN_NUMB);
        mRecyclerView
                .setLayoutManager(mIsGrid ? mGridLayoutManager : mLinearLayoutManager);
        getMovies();
        initRecyclerView();
        return view;
    }

    public void getMovies() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieSimilar(mMovieID, Const.API_KEY).enqueue(new Callback<MovieModel>() {
            @Override
            public void onResponse(Call<MovieModel> call, Response<MovieModel> response) {
                if (response != null) {
                    MovieModel model = response.body();
                    mResults.addAll(model.getResults());
                }
                mMovieAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<MovieModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean getData() {
        if (getArguments() != null) {
            mMovieID = getArguments().getInt("movieID");
            mIsGrid = getArguments().getBoolean("isGrid");
            Log.d(TAG, "onCreateView: " + mMovieID);
            return true;
        } else return false;
    }

    public void initRecyclerView() {
        mMovieAdapter = new MovieAdapter(mResults, getContext(), mIsGrid, false);
        mRecyclerView.setAdapter(mMovieAdapter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mMovieAdapter.notifyDataSetChanged();
    }
}
