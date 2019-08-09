package com.cyber.accounting.movies.app.presentation.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyber.accounting.movies.app.R;
import com.cyber.accounting.movies.app.domain.models.movies.Movies;
import com.cyber.accounting.movies.app.presentation.presenters.callbacks.MoviesCallback;
import com.cyber.accounting.movies.app.presentation.presenters.movie.MoviesPresenter;
import com.cyber.accounting.movies.app.presentation.presenters.movie.MoviesPresenterImp;
import com.cyber.accounting.movies.app.presentation.ui.activities.MovieDetailsActivity;
import com.cyber.accounting.movies.app.presentation.ui.adapters.pagination.BasePaginationAdapter;
import com.cyber.accounting.movies.app.presentation.ui.adapters.pagination.MoviesAdapter;
import com.cyber.accounting.movies.app.presentation.ui.communicator.OnListInteractionListener;
import com.cyber.accounting.movies.app.presentation.ui.custom.CustomDividerItemDecoration;
import com.cyber.accounting.movies.app.presentation.ui.utils.Constants;
import com.cyber.accounting.movies.app.presentation.ui.utils.paper.PaperUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListInteractionListener}
 * interface.
 */
public class MoviesFragment extends BaseFragment implements MoviesCallback, BasePaginationAdapter.OnLoadMoreClickListener, BasePaginationAdapter.OnItemClickListener {
    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.empty)
    TextView empty;

    private MoviesPresenter presenter;
    private MoviesAdapter adapter;
    private LinearLayoutManager layoutManager;
    private boolean isLastPage = false;
    private int currentPage;
    private List<Long> marked;
    private String filter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoviesFragment() {
    }

    public static MoviesFragment newInstance(String filter) {
        MoviesFragment fragment = new MoviesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.KEY_FILTER, filter);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);

        ButterKnife.bind(this, view);

        filter = getArguments().getString(Constants.KEY_FILTER);

        presenter = new MoviesPresenterImp(this);
        marked = PaperUtils.getNotificationsMarked();
        adapter = new MoviesAdapter(marked, this, this);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        CustomDividerItemDecoration dividerItemDecoration = new CustomDividerItemDecoration(getContext(), R.dimen.divider_mid);
        recyclerView.addItemDecoration(dividerItemDecoration);
        recyclerView.addOnScrollListener(recyclerViewOnScrollListener);
        recyclerView.setAdapter(adapter);
        refreshLayout.setColorSchemeResources(R.color.refreshColor1, R.color.refreshColor2,
                R.color.refreshColor3, R.color.refreshColor4);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                load();
            }
        });

        load();
        return view;
    }

    @Override
    public void onLoadMoreClick() {
        loadMore();
    }

    private void load() {
        currentPage = 1;
        loadMore();
    }

    private void loadMore() {
        presenter.getMovies(filter, currentPage);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onShowProgress() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void onHideProgress() {
        refreshLayout.setRefreshing(false);
    }

    @Override
    protected View getSnackBarAnchorView() {
        return recyclerView;
    }

    @Override
    public void onShowConnectionError(View.OnClickListener onClickListener) {
        showConnectionSnackBar(onClickListener);
    }

    @Override
    public void onShowError(String message, View.OnClickListener onClickListener) {
        showRetrySnackBar(message, onClickListener);
    }

    @Override
    public void onGetMoviesComplete(Movies movies) {
        if (currentPage == 1) {
            adapter.clear();
        } else if (adapter.isFooterAdded()) {
            adapter.removeFooter();
        }

        adapter.addAll(movies.getMovies());
        if (currentPage < movies.getTotalPages()) {
            isLastPage = true;
        } else {
            adapter.addFooter();
            currentPage++;
        }
        if (adapter.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            empty.setVisibility(View.GONE);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.onDestroy();
    }

    @Override
    public void onItemClick(int position, View view) {
        long id = adapter.getItemId(position);
        if (!marked.contains(id)) {
            marked.add(id);
            PaperUtils.saveNotificationsMarked(marked);
            adapter.notifyItemChanged(position);
        }

        Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
        intent.putExtra(Constants.KEY_MOVIE_ID, id);
        startActivity(intent);
    }

    private RecyclerView.OnScrollListener recyclerViewOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

            if (!refreshLayout.isRefreshing() && !isLastPage) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                        && firstVisibleItemPosition >= 0
                        && totalItemCount >= 10) {
                    loadMore();
                }
            }
        }
    };
}
