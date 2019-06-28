package movie.wayne.com.myawesomemovie.UI.Activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Credit;
import movie.wayne.com.myawesomemovie.Model.CreditModel;
import movie.wayne.com.myawesomemovie.Model.MovieDetail;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Adapter.CreditAdapter;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class FullCreditActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Credit> mResults;
    private CreditAdapter mCastAdapter;
    private RecyclerView mRecyclerView;
    private int mDepartment = 1;
    private LinearLayoutManager mLinearLayoutManager;
    public int mMovieID;
    private Boolean mIsGrid = false;
    private GridLayoutManager mGridLayoutManager;
    private FloatingActionButton mBtnChange;

    private ProgressBar mProgressCast;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_credit);
        getMovieID();
        mResults = new ArrayList<>();
        mRecyclerView = findViewById(R.id.recycler_review);
        mProgressCast = findViewById(R.id.progress_cast);
        mLinearLayoutManager = new LinearLayoutManager(this);
        mGridLayoutManager = new GridLayoutManager(this, Const.COLUMN_NUMB);
        mRecyclerView
                .setLayoutManager(mIsGrid ? mGridLayoutManager : mLinearLayoutManager);
        initRecyclerView();
        getCast();
        initRecyclerView();
        mBtnChange = findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(this);

    }

    public void getMovieID() {
        Intent in = getIntent();
        mMovieID = in.getIntExtra("movieID", 0);
        mDepartment = in.getIntExtra("department", 0);
        Log.d(TAG, "FullCreditLog: " + mMovieID);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getMovie();
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


    public void getCast() {
        mProgressCast.setVisibility(View.VISIBLE);
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieCredits(mMovieID, Const.API_KEY).enqueue(new Callback<CreditModel>() {
            @Override
            public void onResponse(Call<CreditModel> call, Response<CreditModel> response) {
                if (response != null) {
                    CreditModel model = response.body();
                    if (mDepartment == Const.Department.CAST)
                        mResults.addAll(model.getCast());
                    if (mDepartment == Const.Department.CREW)
                        mResults.addAll(model.getCrew());
                    mCastAdapter.notifyDataSetChanged();
                }
                mProgressCast.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<CreditModel> call, Throwable t) {
                Toast.makeText(FullCreditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCast.setVisibility(View.GONE);
            }
        });
    }

    public void getMovie() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieDetail(mMovieID, Const.API_KEY).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call,
                                   Response<MovieDetail> response) {
                if (response != null) {
                    MovieDetail model = response.body();
                    getSupportActionBar().setTitle(model.getTitle());
                    if (mDepartment == Const.Department.CAST)
                        getSupportActionBar().setSubtitle("CAST");
                    if (mDepartment == Const.Department.CREW)
                        getSupportActionBar().setSubtitle("CREW");
                }
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                Toast.makeText(FullCreditActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void initRecyclerView() {
        mCastAdapter = new CreditAdapter(mResults, this, mDepartment, mIsGrid);
        mRecyclerView.setAdapter(mCastAdapter);
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
}
