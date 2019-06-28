package movie.wayne.com.myawesomemovie.services;

import movie.wayne.com.myawesomemovie.Model.CreditModel;
import movie.wayne.com.myawesomemovie.Model.MovieDetail;
import movie.wayne.com.myawesomemovie.Model.MovieModel;
import movie.wayne.com.myawesomemovie.Model.PeopleDetail;
import movie.wayne.com.myawesomemovie.Model.PeopleModel;
import movie.wayne.com.myawesomemovie.Model.Review;
import movie.wayne.com.myawesomemovie.Model.ReviewListModel;
import movie.wayne.com.myawesomemovie.Model.TrailerList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bruce Wayne on 7/17/2017.
 */
public interface MovieService {
    @GET("movie/now_playing/")
    Call<MovieModel> getMovies(@Query("api_key") String api_key, @Query("page") int page);
    @GET("movie/{movieID}")
    Call<MovieDetail> getMovieDetail(@Path("movieID") int movieID, @Query("api_key") String
        api_key);
    @GET("movie/{movieID}/credits")
    Call<CreditModel> getMovieCredits(@Path("movieID") int movieID, @Query("api_key") String
        api_key);
    @GET("movie/{movieID}/similar")
    Call<MovieModel> getMovieSimilar(@Path("movieID") int movieID, @Query("api_key") String
        api_key);
    @GET("movie/{movieID}/videos")
    Call<TrailerList> getMovieVideos(@Path("movieID") int movieID, @Query("api_key") String
            api_key);
    @GET("movie/{movieID}/reviews")
    Call<ReviewListModel> getMovieReviews(@Path("movieID") int movieID, @Query("api_key") String
            api_key);
    @GET("review/{reviewID}")
    Call<Review> getReview(@Path("reviewID") String reviewID, @Query("api_key") String
            api_key);
    @GET("person/{person_id}/movie_credits")
    Call<PeopleModel> getPeopleCredit(@Path("person_id") int personID, @Query("api_key") String
            api_key);
    @GET("person/{person_id}")
    Call<PeopleDetail> getPeopleDetail(@Path("person_id") int personID, @Query("api_key") String
            api_key);


}
