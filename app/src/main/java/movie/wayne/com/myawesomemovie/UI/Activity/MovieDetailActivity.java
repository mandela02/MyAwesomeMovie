package movie.wayne.com.myawesomemovie.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.Constraints;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.MovieDetail;
import movie.wayne.com.myawesomemovie.Model.Movie;
import movie.wayne.com.myawesomemovie.MovieDataSource;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Fragment.ReviewFragment;
import movie.wayne.com.myawesomemovie.UI.Fragment.MovieDetailFragment;
import movie.wayne.com.myawesomemovie.UI.Fragment.SimilarFragment;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.ContentValues.TAG;


public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener,
        BottomNavigationView.OnNavigationItemSelectedListener {
    public static Intent getInstance(Context context, Movie result) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.putExtra(Const.Extra.EXTRA_RESULT, result);
        return intent;
    }

    private Movie mResult;
    private BottomNavigationView mNavigation;
    private Toolbar toolbar;
    private ImageView mImageBackdrop;
    private TextView mTextTitle;
    private TextView mTextTagLine;
    private AppBarLayout mAppBarLayout;
    private Fragment fragment;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private FloatingActionButton mBtnChange;
    FloatingActionButton mButtonLike;
    private int mCurrentState = 1;
    private boolean isGrid = false;
    private String mUserID;
    private boolean mIsLiked;
    private MovieDataSource mDatabase;

    private static final String TAG_FAMILIAR = "familiar";
    private static final String TAG_REVIEW = "review";
    private static final String TAG_MOVIE_DETAIL = "movie detail";
    public static String CURRENT_TAG = TAG_MOVIE_DETAIL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        getData();
        getMovie();
        initView();
    }

    public void initView() {
        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(this);
        Bundle bundle = new Bundle();
        bundle.putInt("movieID", mResult.getId());
        fragment = new MovieDetailFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);
        mCurrentState = 1;
        mImageBackdrop = findViewById(R.id.image_backdrop);
        mImageBackdrop.setOnClickListener(this);
        Glide.with(MovieDetailActivity.this).load(mResult.getBackdrop()).into(mImageBackdrop);
        Log.d(Constraints.TAG, "initView: " + mResult.getBackdrop());
        mBtnChange = findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(this);
        mBtnChange.setVisibility(View.GONE);
        mTextTitle = findViewById(R.id.text_title);
        mTextTagLine = findViewById(R.id.text_tagLine);
        mButtonLike = findViewById(R.id.fab);
        mButtonLike.setOnClickListener(this);
        mButtonLike
                .setImageResource(mIsLiked ? R.drawable.ic_red_like : R.drawable.ic_white_like);
        mDatabase = new MovieDataSource(getApplicationContext());
    }

    public void initToolbar(final MovieDetail model) {
        toolbar = findViewById(R.id.toolbar);
        mCollapsingToolbarLayout = findViewById(R.id.toolbar_layout);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mCollapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Collapsed);
        mCollapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.TextAppearance_MyApp_Title_Expanded);
        mCollapsingToolbarLayout.setTitle(" ");

        mAppBarLayout = findViewById(R.id.app_bar);
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    //when collapsingToolbar at that time display actionbar title
                    mCollapsingToolbarLayout.setTitle(model.getTitle());
                    isShow = true;
                } else if (isShow) {
                    mCollapsingToolbarLayout.setTitle(" ");
                    isShow = false;
                }
            }
        });
        mTextTitle.setText(model.getTitle());
        mTextTagLine.setText(model.getTagline());
    }

    public void getData() {
        Intent intent = getIntent();
        mResult = intent.getParcelableExtra(Const.Extra.EXTRA_RESULT);
        mIsLiked = mResult.isLiked();
        mUserID = mResult.getUserID();
        Log.d(TAG, "UserID + 2: " + mUserID);
        mResult.setBackdrop(Const.IMAGE_PATH + mResult.getBackdropPath());
    }

    public void getMovie() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getMovieDetail(mResult.getId(), Const.API_KEY)
                .enqueue(new Callback<MovieDetail>
                        () {
                    @Override
                    public void onResponse(Call<MovieDetail> call,
                                           Response<MovieDetail> response) {
                        if (response != null) {
                            MovieDetail model = response.body();
                            initToolbar(model);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieDetail> call, Throwable t) {
                        Toast.makeText(MovieDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT)
                                .show();
                    }
                });
    }

    private void addFavorite(Movie result) {
        mDatabase.insertMovie(result.getId());
        result.setLiked(true);
    }

    private void removeFavorite(Movie result) {
        mDatabase.deleteByID(result.getId());
        result.setLiked(false);
    }


    @Override
    public void onClick(View v) {
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.fab:
                mIsLiked = !mIsLiked;
                if (mIsLiked) {
                    addFavorite(mResult);
                } else {
                    removeFavorite(mResult);
                }
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                mButtonLike
                        .setImageResource(mIsLiked ? R.drawable.ic_red_like : R.drawable
                                .ic_white_like);
                Snackbar.make(v, mIsLiked ? R.string.add_message :
                                R.string.delete_message,
                        Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                break;
            case R.id.image_backdrop:
                startActivity(TrailerListActivity.getInstance(this, mResult));
                break;
            case R.id.btn_change:
                isGrid = !isGrid;
                bundle.putBoolean("isGrid", isGrid);
                mBtnChange.setImageResource(isGrid ? R.drawable.ic_list : R.drawable
                        .ic_option);
                mBtnChange.setVisibility(View.VISIBLE);
                bundle.putInt("movieID", mResult.getId());
                fragment = new SimilarFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
                break;

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Bundle bundle = new Bundle();
        bundle.putBoolean("isGrid", isGrid);
        switch (item.getItemId()) {
            case R.id.navigation_overview:
                CURRENT_TAG = TAG_MOVIE_DETAIL;
                mCurrentState = 1;
                mBtnChange.setVisibility(View.GONE);
                bundle.putInt("movieID", mResult.getId());
                fragment = new MovieDetailFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
                return true;
            case R.id.navigation_review:
                CURRENT_TAG = TAG_REVIEW;
                mCurrentState = 2;
                mBtnChange.setVisibility(View.GONE);
                bundle.putInt("movieID", mResult.getId());
                bundle.putInt("department", Const.Department.CAST);
                fragment = new ReviewFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
                return true;

            case R.id.navigation_similar:
                CURRENT_TAG = TAG_FAMILIAR;
                mCurrentState = 4;
                mBtnChange.setVisibility(View.VISIBLE);
                bundle.putInt("movieID", mResult.getId());
                fragment = new SimilarFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
                return true;

        }
        return false;
    }

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) return;
        switch (requestCode) {
            case Const.REQUEST_CODE_INFORMATION_1:
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(CURRENT_TAG);
                fragment.onActivityResult(requestCode, resultCode, data);
                break;
            default:
                break;
        }

    }
}
