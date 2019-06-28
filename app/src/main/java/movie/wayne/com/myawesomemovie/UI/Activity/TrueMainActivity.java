package movie.wayne.com.myawesomemovie.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import movie.wayne.com.myawesomemovie.Firebase.Database;
import movie.wayne.com.myawesomemovie.Firebase.DatabaseCallback;
import movie.wayne.com.myawesomemovie.Model.Account;
import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.FavoriteMovie;
import movie.wayne.com.myawesomemovie.Model.Genre;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Fragment.FavoriteMovieFragment;
import movie.wayne.com.myawesomemovie.UI.Fragment.NowPlayingFragment;

import static android.content.ContentValues.TAG;


public class TrueMainActivity extends AppCompatActivity {


    public static Intent getInstance(Context context, Account result) {
        Intent intent = new Intent(context, TrueMainActivity.class);
        intent.putExtra(Const.Extra.EXTRA_RESULT, result);
        return intent;
    }

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private View navHeader;
    private static final String TAG_NOW_PLAYING = "Now playing on theater";
    private static final String TAG_FAVORITE = "Your favorite movie";
    public static String CURRENT_TAG = TAG_NOW_PLAYING;
    private String activityTitles[] = {"Now playing on theater", "Your favorite movie"};
    private boolean shouldLoadHomeFragOnBackPress = true;
    public static int navItemIndex = 0;
    private String mUserID;

    private ImageView mImageView;
    private TextView mTextName;
    private TextView mTextID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_true_main);
        initView();
        getUserID();
        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_NOW_PLAYING;
            loadCurrentFragment();
        }
    }

    void getUserID() {
        Intent getIntent = getIntent();
        mUserID = getIntent.getStringExtra("userID");
        Log.d(TAG, "getUserID: " + mUserID);
        getUser();
    }

    private void getUser() {
        Database db = new Database();
        Log.d(TAG, "getUser: " + "ditmemay");
        db.searchUserById(mUserID, new DatabaseCallback() {
            @Override
            public void onFail(String message) {
                Log.d(TAG, "onFail: " + message);
            }

            @Override
            public void onUserSuccess(Account value) {
                Log.d(TAG, "onUserSuccess: " + value.getName());
                mImageView.setImageResource(R.drawable.robin);
                mTextName.setText(value.getName());
                mTextID.setText(value.getID());
            }

            @Override
            public void onGenresSuccess(ArrayList<Genre> genresList) {

            }


        });
    }

    public void initView() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navHeader = navigationView.getHeaderView(0);
        setUpNavigationView();
        mTextName = navHeader.findViewById(R.id.text_userName);
        mTextID = navHeader.findViewById(R.id.text_userID);
        mImageView = navHeader.findViewById(R.id.image_userImage);
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }
        if (shouldLoadHomeFragOnBackPress) {
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_NOW_PLAYING;
                loadCurrentFragment();
                return;
            }
        }
        super.onBackPressed();
    }

    private void setUpNavigationView() {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.nav_now_playing:
                                navItemIndex = 0;
                                CURRENT_TAG = TAG_NOW_PLAYING;
                                drawer.closeDrawers();
                                break;
                            case R.id.nav_favorite:
                                navItemIndex = 1;
                                CURRENT_TAG = TAG_FAVORITE;
                                drawer.closeDrawers();
                                break;
                            case R.id.nav_exit:
                                finish();
                                break;
                            default:
                                navItemIndex = 0;
                                CURRENT_TAG = TAG_NOW_PLAYING;
                                break;
                        }
                        if (menuItem.isChecked()) {
                            menuItem.setChecked(false);
                        } else {
                            menuItem.setChecked(true);
                        }
                        menuItem.setChecked(true);
                        loadCurrentFragment();
                        return true;
                    }
                });
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer,
                        R.string.closeDrawer) {
                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };
        drawer.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    private Fragment getNowPlayingFragment() {
        switch (navItemIndex) {
            case 0:
                NowPlayingFragment nowPlayingFragment = new NowPlayingFragment();
                return nowPlayingFragment;
            case 1:
                FavoriteMovieFragment favoriteFragment = new FavoriteMovieFragment();
                return favoriteFragment;
            default:
                return new NowPlayingFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void loadCurrentFragment() {
        selectNavMenu();
        setToolbarTitle();
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();
            return;
        }
        Fragment fragment = getNowPlayingFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
        fragmentTransaction.commitAllowingStateLoss();
        drawer.closeDrawers();
        invalidateOptionsMenu();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
