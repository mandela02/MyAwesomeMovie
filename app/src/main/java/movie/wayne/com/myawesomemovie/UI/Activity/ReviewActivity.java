package movie.wayne.com.myawesomemovie.UI.Activity;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Review;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class ReviewActivity extends AppCompatActivity {
    private TextView mTextReivew;
    private String mReviewID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getData();
        initView();
        getReview();
    }
    private void initView()
    {
        mTextReivew = findViewById(R.id.text_review);
    }

    private void getData() {
        Intent intent = getIntent();
        mReviewID = intent.getStringExtra("reviewID");
        Log.d(TAG, "onCreate: " + mReviewID);
    }

    private void initDetail(Review review)
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(review.getAuthor());
        actionBar.setSubtitle("Review for" + review.getTitle());
        mTextReivew.setText("\t\t\t\t\t\t" + review.getContent());
        mTextReivew.setMovementMethod(new ScrollingMovementMethod());
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


    private void getReview()
    {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getReview(mReviewID, Const.API_KEY).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call,
                                   Response<Review> response) {
                if (response != null) {
                    Review model = response.body();
                    initDetail(model);
                }
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d(TAG, "Error review: " + t.getMessage());
            }
        });
    }
}
