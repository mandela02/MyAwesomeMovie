package movie.wayne.com.myawesomemovie.UI.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import movie.wayne.com.myawesomemovie.Model.ReviewList;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Activity.ReviewActivity;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {
    private List<ReviewList> mResult;
    private Context mContext;

    public ReviewAdapter(List<ReviewList> mResult, Context mContext) {
        this.mResult = mResult;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_review, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ReviewList item = mResult.get(position);
        holder.bindData(item);

    }

    @Override
    public int getItemCount() {
        return mResult.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView mTextAuthor;
        private TextView mTextContext;
        private TextView mTextSeeMore;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextSeeMore = itemView.findViewById(R.id.see_more);
            mTextSeeMore.setOnClickListener(this);
            mTextAuthor = itemView.findViewById(R.id.text_author);
            mTextContext = itemView.findViewById(R.id.text_content);
        }

        public void bindData(ReviewList item)
        {
            mTextContext.setText(item.getContent());
            mTextAuthor.setText(item.getAuthor());
        }

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(mContext, ReviewActivity.class);
            intent.putExtra("reviewID", mResult.get(getAdapterPosition()).getID());
            mContext.startActivity(intent);
        }
    }
}
