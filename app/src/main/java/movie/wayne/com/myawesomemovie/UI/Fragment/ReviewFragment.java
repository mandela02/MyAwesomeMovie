package movie.wayne.com.myawesomemovie.UI.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.ReviewList;
import movie.wayne.com.myawesomemovie.Model.ReviewListModel;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Adapter.ReviewAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by Bruce Wayne on 7/16/2017.
 */
public class ReviewFragment extends Fragment implements View.OnClickListener {
    private ReviewAdapter mReviewAdapter;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    public int mMovieID;

    private List<ReviewList> mReviewLists;

    public ReviewFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recycler, container, false);
        mReviewLists = new ArrayList<>();
        mRecyclerView = view.findViewById(R.id.recycler_review);
        getMovieID();
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        initRecyclerView();
        getMovieReviews();
        initRecyclerView();
        return view;
    }

    public void getMovieID() {
        mMovieID = getArguments().getInt("movieID");
        Log.d(TAG, "onCreateView: " + mMovieID);
    }

    public void getMovieReviews()
    {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieReviews(mMovieID, Const.API_KEY).enqueue(new Callback<ReviewListModel>() {
            @Override
            public void onResponse(Call<ReviewListModel> call, Response<ReviewListModel> response) {
                if (response != null)
                {
                    ReviewListModel model = response.body();
                    mReviewLists.addAll(model.getReviews());
                    mReviewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<ReviewListModel> call, Throwable t) {

            }
        });
    }


    public void initRecyclerView() {
        mReviewAdapter = new ReviewAdapter(mReviewLists, getContext());
        mRecyclerView.setAdapter(mReviewAdapter);
    }

    @Override
    public void onClick(View v) {
    }
}
