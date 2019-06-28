package movie.wayne.com.myawesomemovie.UI.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.PeopleDetail;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Fragment.PeopleMovieFragment;
import movie.wayne.com.myawesomemovie.UI.Fragment.PersonDetailFragment;

import static android.support.constraint.Constraints.TAG;

public class PersonActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG_CAST = "Cast";
    private static final String TAG_CREW = "Crew";
    private static final String TAG_BIO = "Biography";
    public static String CURRENT_TAG = TAG_BIO;


    private PeopleDetail mPerson;
    private Fragment fragment;
    private int mPersonID;

    private int mCurrentState = 1;
    private boolean isGrid = false;
    private FloatingActionButton mBtnChange;

    private ActionBar actionBar;
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = new Bundle();
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    CURRENT_TAG = TAG_BIO;
                    mCurrentState = 1;
                    actionBar.setSubtitle(" ");
                    mBtnChange.setVisibility(View.GONE);
                    bundle.putInt("personID", mPersonID);
                    fragment = new PersonDetailFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_dashboard:
                    mCurrentState = 2;
                    CURRENT_TAG = TAG_CAST;
                    mBtnChange.setVisibility(View.VISIBLE);
                    actionBar.setSubtitle("Cast");
                    bundle.putBoolean("isGrid", isGrid);
                    bundle.putInt("personID", mPersonID);
                    bundle.putInt("department", Const.Department.CAST);
                    fragment = new PeopleMovieFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
                case R.id.navigation_notifications:
                    mCurrentState = 3;
                    CURRENT_TAG = TAG_CREW;
                    mBtnChange.setVisibility(View.VISIBLE);
                    actionBar.setSubtitle("Produce");
                    bundle.putBoolean("isGrid", isGrid);
                    bundle.putInt("personID", mPersonID);
                    bundle.putInt("department", Const.Department.CREW);
                    fragment = new PeopleMovieFragment();
                    fragment.setArguments(bundle);
                    loadFragment(fragment);
                    return true;
            }
            return false;
        }
    };

    private void loadFragment(Fragment fragment) {
        // load fragment
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment, CURRENT_TAG);
        transaction.addToBackStack(null);
        transaction.commitAllowingStateLoss();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getPersonID();
        initView();
    }

    private void initView() {
        Bundle bundle = new Bundle();
        bundle.putInt("personID", mPersonID);
        fragment = new PersonDetailFragment();
        fragment.setArguments(bundle);
        loadFragment(fragment);
        mBtnChange = findViewById(R.id.btn_change);
        mBtnChange.setOnClickListener(this);
        mBtnChange.setVisibility(View.GONE);
    }

    public void getPersonID() {
        Intent in = getIntent();
        mPersonID = in.getIntExtra("personID", 0);
        Log.d(TAG, "getPersonID: " + mPersonID);
        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(in.getStringExtra("name"));
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
    public void onBackPressed() {
        finish();
    }


    @Override
    public void onClick(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_change:
                isGrid = !isGrid;
                bundle.putBoolean("isGrid", isGrid);
                mBtnChange.setImageResource(isGrid ? R.drawable.ic_list : R.drawable
                        .ic_option);
                bundle.putInt("personID", mPersonID);
                switch (mCurrentState) {
                    case 2:
                        bundle.putInt("department", Const.Department.CAST);
                        break;
                    case 3:
                        bundle.putInt("department", Const.Department.CREW);
                        break;
                }
                fragment = new PeopleMovieFragment();
                fragment.setArguments(bundle);
                loadFragment(fragment);
                break;
        }
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
