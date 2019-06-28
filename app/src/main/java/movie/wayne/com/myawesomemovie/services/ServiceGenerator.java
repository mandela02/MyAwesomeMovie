package movie.wayne.com.myawesomemovie.services;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.support.constraint.Constraints.TAG;

public class ServiceGenerator {
    private static final String BASE_URL = "https://api.themoviedb.org/3/";
    private static Retrofit retrofit = null;
    private static Retrofit.Builder builder = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create());
    private static HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient.Builder okhttpClientBuild =
        new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor);
    private static OkHttpClient okHttpClient = okhttpClientBuild.build();

    public static <T> T createService(Class<T> serviceClass) {
        if (retrofit == null) {
            retrofit = builder.client(okHttpClient).build();
        }
        return retrofit.create(serviceClass);
    }
}
