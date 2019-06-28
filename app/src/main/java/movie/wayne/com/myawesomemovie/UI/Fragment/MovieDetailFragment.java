package movie.wayne.com.myawesomemovie.UI.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Credit;
import movie.wayne.com.myawesomemovie.Model.CreditModel;
import movie.wayne.com.myawesomemovie.Model.MovieDetail;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Activity.FullCreditActivity;
import movie.wayne.com.myawesomemovie.UI.Adapter.CreditAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class MovieDetailFragment extends Fragment implements View.OnClickListener {
    public int mMovieID;
    private TextView mTextOriginalTitle;
    private TextView mTextReleaseDate;
    private TextView mTextVote;
    private TextView mTextVoteCount;
    private TextView mTextRuntime;
    private TextView mTextOverview;
    private TextView mTextGenres;
    private ImageView mImagePoster;

    private List<Credit> mCast;
    private CreditAdapter mCastAdapter;
    private RecyclerView mRecyclerViewCast;

    private List<Credit> mCrew;
    private CreditAdapter mCrewAdapter;
    private RecyclerView mRecyclerViewCrew;

    private LinearLayoutManager mCastLinearLayoutManager;
    private LinearLayoutManager mCrewLinearLayoutManager;

    private LinearLayout mCastLinear;
    private LinearLayout mCrewLinear;


    public MovieDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        getMovieID();
        initView(view);
        getMovie();
        getCast();
        initRecyclerView();
        return view;
    }

    void initView(View view) {
        mCast = new ArrayList<>();
        mCrew = new ArrayList<>();
        mTextOriginalTitle = view.findViewById(R.id.text_original_title);
        mTextOverview = view.findViewById(R.id.text_overview);
        mTextReleaseDate = view.findViewById(R.id.text_release_date);
        mTextRuntime = view.findViewById(R.id.text_runtime);
        mTextVote = view.findViewById(R.id.text_vote);
        mTextVoteCount = view.findViewById(R.id.text_vote_count);
        mImagePoster = view.findViewById(R.id.image_face);
        mRecyclerViewCast = view.findViewById(R.id.recycler_review);
        mRecyclerViewCrew = view.findViewById(R.id.recycler_crew);
        mCastLinear = view.findViewById(R.id.linear_cast);
        mCastLinear.setOnClickListener(this);
        mCrewLinear = view.findViewById(R.id.linear_crew);
        mCrewLinear.setOnClickListener(this);
        mTextGenres = view.findViewById(R.id.text_genres);
    }

    public void initRecyclerView() {
        mCastLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCast.setLayoutManager(mCastLinearLayoutManager);
        mCastAdapter = new CreditAdapter(mCast, getContext(), Const.Department.CAST, true);
        mRecyclerViewCast.setAdapter(mCastAdapter);
        mCrewLinearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerViewCrew.setLayoutManager(mCrewLinearLayoutManager);
        mCrewAdapter = new CreditAdapter(mCrew, getContext(), Const.Department.CREW, true);
        mRecyclerViewCrew.setAdapter(mCrewAdapter);
    }


    public void getMovieID() {
        mMovieID = getArguments().getInt("movieID");
        Log.d(TAG, "onCreateView: " + mMovieID);
    }

    public void getMovie() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieDetail(mMovieID, Const.API_KEY).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call,
                                   Response<MovieDetail> response) {
                if (response != null) {
                    MovieDetail model = response.body();
                    initDetail(model);
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getCast() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieCredits(mMovieID, Const.API_KEY).enqueue(new Callback<CreditModel>() {
            @Override
            public void onResponse(Call<CreditModel> call, Response<CreditModel> response) {
                if (response != null) {
                    CreditModel model = response.body();
                    if (model.getCast().size() > 5)
                        for (int i = 0; i < 5; i++) {
                            mCast.add(model.getCast().get(i));
                        }
                    else {
                        mCast.addAll(model.getCast());
                    }
                    if (model.getCrew().size() > 5) {
                        for (int i = 0; i < 5; i++) {
                            mCrew.add(model.getCrew().get(i));
                        }
                    } else {
                        mCrew.addAll(model.getCrew());

                    }
                    mCastAdapter.notifyDataSetChanged();
                    mCrewAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<CreditModel> call, Throwable t) {
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    void initDetail(MovieDetail model) {
        if (getActivity() == null) {
            return;
        }
        String genres = "";
        for(int i = 0; i < model.getGenre().size(); i++)
        {
            Log.d(TAG, "initDetail name: " + model.getGenre().get(i).getName());
            if (i != model.getGenre().size() - 1)
                genres = genres.concat(model.getGenre().get(i).getName() + ", ");
            else
                genres = genres.concat(model.getGenre().get(i).getName() + ".");
        }
        Log.d(TAG, "initDetail: " + genres);

        mTextGenres.setText(genres);

        mTextOriginalTitle.setText("Original Title: \n" + model.getOriginalTitle());
        mTextReleaseDate.setText("Release Date: " + model.getReleaseDate());
        if (model.getRuntime() == null) mTextRuntime.setText("Runtime: don't have information");
        else mTextRuntime.setText("Runtime: " + model.getRuntime() + " min");
        model.setMoviePoster(Const.IMAGE_PATH + model.getPosterPath());
        Log.d(TAG, "initDetail: " + model.getMoviePoster());
        Glide.with(getActivity()).load(model.getMoviePoster()).into(mImagePoster);
        mTextOverview.setText(model.getOverview());
        mTextVote.setText(model.getVoteAverage() + "/10");
        mTextVoteCount.setText(model.getVoteCount() + " vote.");
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.linear_cast:
                Intent intentCast = new Intent(getActivity(), FullCreditActivity.class);
                intentCast.putExtra("movieID", mMovieID);
                intentCast.putExtra("department", Const.Department.CAST);
                startActivity(intentCast);
                break;
            case R.id.linear_crew:
                Intent intentCrew = new Intent(getActivity(), FullCreditActivity.class);
                intentCrew.putExtra("movieID", mMovieID);
                intentCrew.putExtra("department", Const.Department.CREW);
                startActivity(intentCrew);
                break;
        }

    }
}
