package com.cyber.accounting.movies.app.presentation.ui.adapters.pagination;

import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cyber.accounting.movies.app.BuildConfig;
import com.cyber.accounting.movies.app.R;
import com.cyber.accounting.movies.app.domain.models.movies.Movie;
import com.cyber.accounting.movies.app.presentation.ui.utils.LanguageUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MoviesAdapter extends BasePaginationAdapter<Movie> {
    private FooterViewHolder footerViewHolder;
    private List<Long> marked;

    public MoviesAdapter(List<Long> marked, OnItemClickListener onItemClickListener, OnLoadMoreClickListener onLoadMoreClickListener) {
        super(onItemClickListener, onLoadMoreClickListener);
        this.marked = marked;
    }

    @Override
    public int getItemViewType(int position) {
        return (isLastPosition(position) && isFooterAdded) ? FOOTER : ITEM;
    }

    @Override
    protected RecyclerView.ViewHolder createHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    protected RecyclerView.ViewHolder createItemViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);

        final ViewHolder holder = new ViewHolder(view);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(position, holder.itemView);
                    }
                }
            }
        });

        return holder;
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    protected RecyclerView.ViewHolder createFooterViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_load_more, parent, false);

        final FooterViewHolder holder = new FooterViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.progress.setVisibility(View.VISIBLE);
                if (onLoadMoreClickListener != null) {
                    onLoadMoreClickListener.onLoadMoreClick();
                }
            }
        });

        return holder;
    }

    @Override
    protected void bindHeaderViewHolder(RecyclerView.ViewHolder viewHolder) {

    }

    @Override
    protected void bindItemViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final ViewHolder holder = (ViewHolder) viewHolder;

        final Movie movie = getItem(position);
        holder.movie = movie;
        holder.title.setText(holder.movie.getTitle());
        holder.date.setText(holder.movie.getReleaseDate());
        holder.overview.setText(holder.movie.getOverview());
        holder.voteCount.setText(holder.voteCount.getResources().getString(R.string.str_vote_count) + holder.movie.getVoteAverage());
        holder.language.setText(holder.language.getResources().getString(R.string.str_language) + LanguageUtil.getLanguageName(holder.movie.getOriginalLanguage()));
        holder.marked.setImageResource(marked.contains(movie.getId()) ? R.drawable.ic_action_star_border : R.drawable.ic_action_star_marked);

        Glide.with(holder.poster.getContext())
                .load(BuildConfig.BASE_POSTER_URL + movie.getPosterPath())
                .placeholder(ResourcesCompat.getDrawable(holder.poster.getContext().getResources(), R.drawable.ic_movie, null))
                .error(ResourcesCompat.getDrawable(holder.poster.getContext().getResources(), R.drawable.ic_movie, null))
                .dontAnimate()
                .into(holder.poster);
    }

    @Override
    protected void bindFooterViewHolder(RecyclerView.ViewHolder viewHolder) {
        FooterViewHolder holder = (FooterViewHolder) viewHolder;
        footerViewHolder = holder;
    }

    @Override
    protected void displayLoadMoreFooter() {
        if (footerViewHolder != null) {
            footerViewHolder.progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void displayErrorFooter() {
    }

    @Override
    public void addFooter() {
        isFooterAdded = true;
        add(new Movie());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.marked)
        ImageView marked;
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.overview)
        TextView overview;
        @BindView(R.id.vote_count)
        TextView voteCount;
        @BindView(R.id.language)
        TextView language;
        View view;
        Movie movie;

        public ViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.progress_bar)
        ProgressBar progress;

        public FooterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
