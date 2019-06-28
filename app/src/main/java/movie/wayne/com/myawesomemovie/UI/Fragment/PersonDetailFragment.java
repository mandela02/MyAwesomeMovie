package movie.wayne.com.myawesomemovie.UI.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.PeopleDetail;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.services.MovieService;
import movie.wayne.com.myawesomemovie.services.ServiceGenerator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.support.constraint.Constraints.TAG;

public class PersonDetailFragment extends Fragment {

    private ImageView mImageFace;
    private TextView mTextBirthDay;
    private TextView mTextDeathDay;
    private TextView mTextBirthPlace;
    private TextView mTextGenres;
    private TextView mTextBiography;

    private int mPersonID;


    public PersonDetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person_detail, container, false);
        initView(view);
        getPersonID();
        return view;
    }

    private void initView(View view) {
        mImageFace = view.findViewById(R.id.image_face);
        mTextBirthDay = view.findViewById(R.id.text_birthday);
        mTextDeathDay = view.findViewById(R.id.text_death_day);
        mTextBirthPlace = view.findViewById(R.id.text_place_of_birth);
        mTextGenres = view.findViewById(R.id.text_genre);
        mTextBiography = view.findViewById(R.id.text_biography);
        mTextBiography.setMovementMethod(new ScrollingMovementMethod());
    }

    private void getPersonID() {
        mPersonID = getArguments().getInt("personID");
        Log.d(TAG, "onCreateView: " + mPersonID);
        getPerson();
    }

    public void getPerson() {
        MovieService service = ServiceGenerator.createService(MovieService.class);
        service.getPeopleDetail(mPersonID, Const.API_KEY).enqueue(new Callback<PeopleDetail>() {
            @Override
            public void onResponse(Call<PeopleDetail> call,
                                   Response<PeopleDetail> response) {
                if (response != null) {
                    PeopleDetail model = response.body();
                    initInformation(model);
                }
            }

            @Override
            public void onFailure(Call<PeopleDetail> call, Throwable t) {
            }
        });
    }

    private void initInformation(PeopleDetail model) {
        if (model.getProfilePath() != null) {
            model.setPicture(Const.IMAGE_PATH + model.getProfilePath());
            Glide.with(getActivity()).load(model.getPicture()).into(mImageFace);
        } else {
            mImageFace.setImageResource(R.drawable.pic_not_found);
        }
        if (model.getBirthday() != null)
            mTextBirthDay.setText("Date of birth: " + model.getBirthday());
        else mTextBirthDay.setText("Date of birth: unknown");
        if (model.getPlaceOfBirth() != null)
            mTextBirthPlace.setText("Place of birth: " + model.getPlaceOfBirth());
        else mTextBirthPlace.setText("Place of birth: unknown");
        if (model.getDeathday() != null)
            mTextDeathDay.setText("Death date: " + model.getDeathday());
        else mTextDeathDay.setVisibility(View.INVISIBLE);
        if (model.getGender() == 2) mTextGenres.setText("Genre: MALE");
        else if (model.getGender() == 1) mTextGenres.setText("Genre: FEMALE");
        else mTextGenres.setText("Genre: ELSE");
        mTextBiography.setText("\t\t\t\t\t" + model.getBiography());
    }
}
