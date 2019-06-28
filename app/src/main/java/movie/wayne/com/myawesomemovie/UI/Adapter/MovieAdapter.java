package movie.wayne.com.myawesomemovie.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import movie.wayne.com.myawesomemovie.Firebase.Database;
import movie.wayne.com.myawesomemovie.Firebase.DatabaseCallback;
import movie.wayne.com.myawesomemovie.Model.Account;
import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Genre;
import movie.wayne.com.myawesomemovie.Model.Movie;
import movie.wayne.com.myawesomemovie.MovieDataSource;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Activity.MovieDetailActivity;

import static android.app.Activity.RESULT_OK;
import static android.support.constraint.Constraints.TAG;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {
    private List<Movie> mResults;
    private Context mContext;
    private boolean mIsGrid;
    private boolean mIsInFav;

    public MovieAdapter(List<Movie> mResults, Context mContext, boolean mIsGrid, boolean mIsInFav) {
        this.mResults = mResults;
        this.mContext = mContext;
        this.mIsGrid = mIsGrid;
        this.mIsInFav = mIsInFav;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mIsGrid ? R.layout.item_gird : R.layout.item_movie, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie item = mResults.get(position);
        MovieDataSource database = new MovieDataSource(mContext);
        item.setLiked(database.isInDatabase(item.getId()));
        item.setMoviePoster(Const.IMAGE_PATH + item.getPosterPath());
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImagePoster;
        private TextView mTextTitle;
        private TextView mTextDate;
        private TextView mTextGenres;
        private TextView mTextVote;
        private TextView mTextDes;
        private TextView mTextMore;
        private ImageView mImageLike;
        private CardView mCardView;
        private MovieDataSource mDatabase;

        public ViewHolder(View itemView) {
            super(itemView);
            mImagePoster = itemView.findViewById(R.id.image_face);
            mTextTitle = itemView.findViewById(R.id.text_title);
            mCardView = itemView.findViewById(R.id.card);
            mCardView.setOnClickListener(this);
            mImageLike = itemView.findViewById(R.id.image_like);
            mImageLike.setOnClickListener(this);
            if (!mIsGrid) {
                mTextDate = itemView.findViewById(R.id.text_date);
                mTextVote = itemView.findViewById(R.id.text_vote);
                mTextDes = itemView.findViewById(R.id.text_description);
                mTextGenres = itemView.findViewById(R.id.text_genre);
                mTextMore = itemView.findViewById(R.id.text_more);
                mTextMore.setOnClickListener(this);
            }
            mDatabase = new MovieDataSource(mContext);
        }

        public void bindData(Movie item) {
            if (item == null) return;
            Glide.with(mContext).load(item.getMoviePoster()).into(mImagePoster);
            mTextTitle.setText(item.getTitle());
            mTextTitle.setSelected(true);
            Log.d(TAG, "bindData: " + item.getTitle() + "\n");
            if (!mIsGrid) {
                mTextVote.setText(Float.toString(item.getVoteAverage()));
                mTextDes.setText(item.getOverview());
                mTextDate.setText(item.getReleaseDate());
                getGenres(item);
            }
            mImageLike.setImageResource(item.isLiked() ? R.drawable
                    .ic_red_like : R.drawable.ic_white_like);
        }

        private void getGenres(final Movie item) {
            Database db = new Database();
            db.getAllGenres(mContext, new DatabaseCallback() {
                @Override
                public void onFail(String message) {
                    Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                }

                @Override
                public void onGenresSuccess(ArrayList<Genre> genresList) {
                    String genres = "";
                    for (int i = 0; i < item.getGenreIds().size(); i++) {
                        for (int j = 0; j < genresList.size(); j++)
                            if (item.getGenreIds().get(i) == genresList.get(j).getId()) {
                                if (i != item.getGenreIds().size() - 1)
                                    genres = genres.concat(genresList.get(j).getName() + ", ");
                                else genres = genres.concat(genresList.get(j).getName() + ".");
                            }
                    }
                    mTextGenres.setText(genres);
                }

                @Override
                public void onUserSuccess(Account value) {

                }

            });
        }


        @Override
        public void onClick(View v) {
            Movie movie;
            switch (v.getId()) {
                case R.id.text_more:
                    movie = mResults.get(getAdapterPosition());
                    ((Activity) mContext)
                            .startActivityForResult(MovieDetailActivity.getInstance(mContext, movie),
                                    Const.REQUEST_CODE_INFORMATION_1);
                    break;

                case R.id.card:
                    movie = mResults.get(getAdapterPosition());
                    ((Activity) mContext)
                            .startActivityForResult(MovieDetailActivity.getInstance(mContext, movie),
                                    Const.REQUEST_CODE_INFORMATION_1);
                    break;

                case R.id.image_like:
                    Log.d(TAG, "onClick: clicked");
                    movie = mResults.get(getAdapterPosition());
                    if (movie.isLiked()) {
                        final Movie deletedMovie = mResults.get(getAdapterPosition());
                        final int deletedPosition = getAdapterPosition();
                        mDatabase.deleteByID(deletedMovie.getId());
                        mResults.get(getAdapterPosition()).setLiked(false);
                        notifyItemChanged(getAdapterPosition());
                        if (mIsInFav == true) {
                            mResults.remove(getAdapterPosition());
                            notifyDataSetChanged();
                            String message = "You just delete a movie. Undo?";
                            int duration = Snackbar.LENGTH_LONG;

                            final Snackbar snackbar = Snackbar.make(v, message, duration);
                            snackbar.setAction("UNDO", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    snackbar.dismiss();
                                    mResults.add(deletedPosition, deletedMovie);
                                    notifyItemInserted(deletedPosition);
                                    mDatabase.insertMovie(deletedMovie.getId());
                                    mResults.get(deletedPosition).setLiked(true);
                                    notifyItemChanged(deletedPosition);
                                }
                            });

                            snackbar.show();
                        }
                    } else {
                        mDatabase.insertMovie(mResults.get(getAdapterPosition()).getId());
                        mResults.get(getAdapterPosition()).setLiked(true);
                        notifyItemChanged(getAdapterPosition());
                    }
                    Intent intent = new Intent();
                    ((Activity) mContext).setResult(RESULT_OK, intent);
                    break;
            }
        }
    }
}
