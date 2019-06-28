package movie.wayne.com.myawesomemovie.UI.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Trailer;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Activity.MovieTrailerActivity;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    private List<Trailer> mResult;
    private Context mContext;

    public TrailerAdapter(List<Trailer> mResult, Context mContext) {
        this.mResult = mResult;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public TrailerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_trailer, parent, false);
        return new ViewHolder(v);    }

    @Override
    public void onBindViewHolder(@NonNull TrailerAdapter.ViewHolder holder, int position) {
        Trailer item = mResult.get(position);
        item.setFullImagePath("https://img.youtube.com/vi/" + item.getKey() + "/maxresdefault.jpg");
        holder.bindData(item);

    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextTitle;
        private ImageView mImage;
        public ViewHolder(View itemView) {
            super(itemView);
            mTextTitle = itemView.findViewById(R.id.text_trailerTitle);
            mImage = itemView.findViewById(R.id.image_trailerImage);
            mImage.setOnClickListener(this);
            mTextTitle.setOnClickListener(this);
        }
        public void bindData(Trailer item) {
            if (item == null) return;
            Glide.with(mContext).load(item.getFullImagePath()).into(mImage);
            mTextTitle.setText(item.getName());
        }

        @Override
        public void onClick(View v) {
            Trailer trailer = mResult.get(getAdapterPosition());
            mContext.startActivity(MovieTrailerActivity.getInstance(mContext, trailer));
        }
    }
}
