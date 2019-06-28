package movie.wayne.com.myawesomemovie.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
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
import movie.wayne.com.myawesomemovie.Model.Movie;
import movie.wayne.com.myawesomemovie.Model.PeopleModel;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Adapter.MovieAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class PeopleMovieFragment extends Fragment {

    private List<Movie> mResults;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mRecyclerView;
    private int mPersonID;
    private boolean mIsGrid;
    private LinearLayoutManager mLinearLayoutManager;
    private GridLayoutManager mGridLayoutManager;
    private FloatingActionButton mFloatingActionButton;
    private int mDepartment;
    private PeopleModel mPeopleModel;

    public PeopleMovieFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mResults = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recycler_review);
        mRecyclerView.setPadding(0, 0, 0, 150);
        getData();
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
        service.getPeopleCredit(mPersonID, Const.API_KEY).enqueue(new Callback<PeopleModel>() {
            @Override
            public void onResponse(Call<PeopleModel> call, Response<PeopleModel> response) {
                if (response != null) {
                    PeopleModel model = response.body();
                    if (mDepartment == Const.Department.CAST)
                        mResults.addAll(model.getCast());
                    else if (mDepartment == Const.Department.CREW)
                        mResults.addAll(model.getCrew());
                }
                mMovieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PeopleModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean getData() {
        if (getArguments() != null) {
            mPersonID = getArguments().getInt("personID");
            mIsGrid = getArguments().getBoolean("isGrid");
            mDepartment = getArguments().getInt("department");
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
