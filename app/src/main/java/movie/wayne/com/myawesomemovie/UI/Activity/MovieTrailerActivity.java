package movie.wayne.com.myawesomemovie.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Trailer;
import movie.wayne.com.myawesomemovie.R;

public class MovieTrailerActivity extends AppCompatActivity implements YouTubePlayer.OnInitializedListener {
    private ImageView mImage;
    private YouTubePlayerFragment playerFragment;
    private YouTubePlayer mPlayer;
    private Trailer mTrailer;

    public static Intent getInstance(Context context, Trailer result) {
        Intent intent = new Intent(context, MovieTrailerActivity.class);
        intent.putExtra(Const.Extra.EXTRA_RESULT, result);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);
        getMovie();
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        playerFragment =
                (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.youTubePlayer);
        playerFragment.initialize(Const.YOUTUBE_KEY, this);

    }

    public void getMovie() {
        Intent intent = getIntent();
        mTrailer = intent.getParcelableExtra(Const.Extra.EXTRA_RESULT);
        setTitle("WATCH THE FULL TRAILER HERE");
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

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        mPlayer = youTubePlayer;

        //Enables automatic control of orientation
        mPlayer.setFullscreenControlFlags(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION);

        //Show full screen in landscape mode always
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE);

        //System controls will appear automatically
        mPlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI);

        if (!b) {
            //youTubePlayer.cueVideo(mTrailer.getKey());
            youTubePlayer.loadVideo(mTrailer.getKey());
            //mPlayer.loadVideo("9rLZYyMbJic");
        } else {
            mPlayer.play();
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        mPlayer = null;
    }

}
