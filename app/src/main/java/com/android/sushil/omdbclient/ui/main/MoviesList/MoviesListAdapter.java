package com.android.sushil.omdbclient.ui.main.MoviesList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.sushil.omdbclient.R;
import com.android.sushil.omdbclient.data.model.Search;
import com.android.sushil.omdbclient.ui.main.MovieDetails.MovieDetailsActivity;
import com.android.sushil.omdbclient.utils.Constants;
import com.android.sushil.omdbclient.utils.PaginationAdapterCallback;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sushiljha on 09/09/2017.
 */

public class MoviesListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int ITEM = 0;
    private static final int LOADING = 1;
    private List<Search> mSearchResults;
    private Context mContext;
    private final OnItemClickListener mListener;

    private boolean mIsLoadingAdded = false;
    private boolean mRetryPageLoad = false;
    private PaginationAdapterCallback mCallback;
    private String mErrorMsg;

    public MoviesListAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        mListener = listener;
        mCallback = (PaginationAdapterCallback) context;
        mSearchResults = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                viewHolder = getViewHolder(parent, inflater);
                break;

            case LOADING:
                View v2 = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingVH(v2);
                break;
        }
        return viewHolder;
    }

    @NonNull
    private RecyclerView.ViewHolder getViewHolder(ViewGroup parent, LayoutInflater inflater) {
        RecyclerView.ViewHolder viewHolder;
        View v1 = inflater.inflate(R.layout.cardview_movie_item, parent, false);
        viewHolder = new MoviesVH(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Search result = mSearchResults.get(position);

        switch (getItemViewType(position)) {
            case ITEM:
                final MoviesVH moviesVH = (MoviesVH) holder;
                moviesVH.click(result, mListener);

                moviesVH.mMovieTitle.setText(result.getTitle());
                moviesVH.mMovieYear.setText(result.getYear());
                Glide.with(mContext)
                        .load(result.getPoster())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .skipMemoryCache(true)
                        .error(R.drawable.image_not_available)
                        .into(moviesVH.mImageThumb);

                break;

            case LOADING:
                LoadingVH loadingVH = (LoadingVH) holder;

                if (mRetryPageLoad) {
                    loadingVH.mErrorLayout.setVisibility(View.VISIBLE);
                    loadingVH.mProgressBar.setVisibility(View.GONE);

                    loadingVH.mErrorTxt.setText(
                            mErrorMsg != null ?
                                    mErrorMsg :
                                    mContext.getString(R.string.error_msg_unknown));

                } else {
                    loadingVH.mErrorLayout.setVisibility(View.GONE);
                    loadingVH.mProgressBar.setVisibility(View.VISIBLE);
                }

                break;
        }
    }

    @Override
    public int getItemCount() {
        return mSearchResults == null ? 0 : mSearchResults.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mSearchResults.size() - 1 && mIsLoadingAdded) ? LOADING : ITEM;
    }

    //Helpers
    public void add(Search r) {
        mSearchResults.add(r);
        notifyItemInserted(mSearchResults.size() - 1);
    }

    public void addAll(List<Search> moveResults) {
        for (Search result : moveResults) {
            add(result);
        }
    }

    public void remove(Search r) {
        int position = mSearchResults.indexOf(r);
        if (position > -1) {
            mSearchResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void removeAll() {
        if(!mSearchResults.isEmpty()) {
            mSearchResults.clear();
            notifyDataSetChanged();
        }
    }

    public void addLoadingFooter() {
        mIsLoadingAdded = true;
        add(new Search());
    }

    public void removeLoadingFooter() {
        mIsLoadingAdded = false;

        int position = mSearchResults.size() - 1;
        Search result = getItem(position);

        if (result != null) {
            mSearchResults.remove(position);
            notifyItemRemoved(position);
        }
    }

    public Search getItem(int position) {
        return mSearchResults.get(position);
    }

    public void showRetry(boolean show, @Nullable String errorMsg) {
        mRetryPageLoad = show;
        notifyItemChanged(mSearchResults.size() - 1);

        if (errorMsg != null) this.mErrorMsg = errorMsg;
    }


    //View Holders

    protected class MoviesVH extends RecyclerView.ViewHolder {

        private ImageView mImageThumb;
        private TextView mMovieTitle;
        private TextView mMovieYear;


        public MoviesVH(View itemView) {
            super(itemView);

            mImageThumb = (ImageView) itemView.findViewById(R.id.poster);
            mMovieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            mMovieYear = (TextView) itemView.findViewById(R.id.movie_year);
        }

        public void click(final Search movieDetails, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent movieDetailsIntent = new Intent(mContext, MovieDetailsActivity.class);
                    Bundle dataBundle = new Bundle();
                    dataBundle.putString(Constants.ARG_ID, movieDetails.getImdbID());
                    dataBundle.putString(Constants.ARG_URL, movieDetails.getPoster());
                    movieDetailsIntent.putExtras(dataBundle);
                    mContext.startActivity(movieDetailsIntent);
                }
            });
        }
    }


    protected class LoadingVH extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ProgressBar mProgressBar;
        private ImageButton mRetryBtn;
        private TextView mErrorTxt;
        private LinearLayout mErrorLayout;

        public LoadingVH(View itemView) {
            super(itemView);

            mProgressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);
            mRetryBtn = (ImageButton) itemView.findViewById(R.id.loadmore_retry);
            mErrorTxt = (TextView) itemView.findViewById(R.id.loadmore_errortxt);
            mErrorLayout = (LinearLayout) itemView.findViewById(R.id.loadmore_errorlayout);

            mRetryBtn.setOnClickListener(this);
            mErrorLayout.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.loadmore_retry:
                case R.id.loadmore_errorlayout:

                    showRetry(false, null);
                    mCallback.retryPageLoad();

                    break;
            }
        }
    }

    public interface OnItemClickListener {
        void onClick(View v, Search Item);
    }

}
