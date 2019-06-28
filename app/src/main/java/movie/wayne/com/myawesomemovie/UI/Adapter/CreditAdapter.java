package movie.wayne.com.myawesomemovie.UI.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import movie.wayne.com.myawesomemovie.Model.Const;
import movie.wayne.com.myawesomemovie.Model.Credit;
import movie.wayne.com.myawesomemovie.R;
import movie.wayne.com.myawesomemovie.UI.Activity.FullCreditActivity;
import movie.wayne.com.myawesomemovie.UI.Activity.MovieDetailActivity;
import movie.wayne.com.myawesomemovie.UI.Activity.PersonActivity;

public class CreditAdapter extends RecyclerView.Adapter<CreditAdapter.ViewHolder> {
    private List<Credit> mResults;
    private Context mContext;
    private int mDepartment;
    private Boolean mIsGrid;

    public CreditAdapter(List<Credit> results, Context context, int department, Boolean isGrid) {
        mResults = results;
        mContext = context;
        mDepartment = department;
        mIsGrid = isGrid;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(mIsGrid ? R.layout.item_credit_grid : R.layout.item_credit, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Credit item = mResults.get(position);
        holder.bindData(item);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView mImageFace;
        private TextView mTextRealName;
        private TextView mTextCharacterName;
        private CardView mCard;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageFace = itemView.findViewById(R.id.image_face);
            mTextCharacterName = itemView.findViewById(R.id.text_role);
            mTextCharacterName.setSelected(true);
            mTextRealName = itemView.findViewById(R.id.text_name);
            mTextRealName.setSelected(true);
            mCard = itemView.findViewById(R.id.card);
            mCard.setOnClickListener(this);
        }

        public void bindData(Credit item) {
            if (item == null) return;
            if (item.getProfilePath() != null) {
                item.setProfile(Const.IMAGE_PATH + item.getProfilePath());
                Glide.with(mContext).load(item.getProfile()).into(mImageFace);
            }
            else mImageFace.setImageResource(R.drawable.pic_not_found);
            mTextRealName.setText(item.getName());
            if (mDepartment == Const.Department.CAST)
                mTextCharacterName.setText(item.getCharacter());
            else if (mDepartment == Const.Department.CREW)
                mTextCharacterName.setText(item.getJob());
        }

        @Override
        public void onClick(View v) {
            Credit credit;
            switch (v.getId()) {
                case R.id.card:
                    credit = mResults.get(getAdapterPosition());
                    Intent intentCast = new Intent(mContext, PersonActivity.class);
                    intentCast.putExtra("personID", credit.getId());
                    intentCast.putExtra("name", credit.getName());
                    mContext.startActivity(intentCast);
                    break;
            }
        }
    }
}
