package com.cyber.accounting.movies.app.presentation.ui.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.cyber.accounting.movies.app.BuildConfig;
import com.cyber.accounting.movies.app.R;
import com.cyber.accounting.movies.app.domain.models.movies.MovieDetails;
import com.cyber.accounting.movies.app.domain.models.movies.ProductionCompany;
import com.cyber.accounting.movies.app.presentation.presenters.callbacks.MoviesCallback;
import com.cyber.accounting.movies.app.presentation.presenters.movie.MoviesPresenter;
import com.cyber.accounting.movies.app.presentation.presenters.movie.MoviesPresenterImp;
import com.cyber.accounting.movies.app.presentation.ui.utils.Constants;
import com.cyber.accounting.movies.app.presentation.ui.utils.LanguageUtil;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class MovieDetailsActivity extends BaseActivity implements MoviesCallback {
    @BindView(R.id.account_info_form)
    View accountInfoFormView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.backdrop)
    ImageView backdrop;
    @BindView(R.id.vote_count)
    TextView voteCount;
    @BindView(R.id.vote_average)
    RatingBar rating;
    @BindView(R.id.text_view_home_page)
    TextView textViewHomePage;
    @BindView(R.id.text_view_status)
    TextView textViewStatus;
    @BindView(R.id.text_view_release_date)
    TextView textViewReleasedDate;
    @BindView(R.id.text_view_original_language)
    TextView textViewOriginalLanguage;
    @BindView(R.id.text_view_production_company)
    TextView textViewProductionCompany;
    @BindView(R.id.text_budget)
    TextView textViewBudget;
    @BindView(R.id.text_view_overview)
    TextView textViewOverView;

    private MoviesPresenter presenter;
    private MovieDetails details;
    private long movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        ButterKnife.bind(this);
        movieId = getIntent().getLongExtra(Constants.KEY_MOVIE_ID, -1);
        presenter = new MoviesPresenterImp(this);
        presenter.getMovieDetails(movieId);
        setupUI();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.onDestroy();
        }
    }

    private void setupUI() {
        setupSupportedActionBarWithHome(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.btn_view_home_page)
    public void onHomePageClick(View view) {
        if (details != null) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(details.getHomepage()));
                startActivity(intent);
            } catch (Exception ex) {
            }
        } else {
            Toast.makeText(this, "Please Wait...", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected View getSnackBarAnchorView() {
        return accountInfoFormView;
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
    public void onGetMovieDetailsComplete(MovieDetails details) {
        if (details != null) {
            this.details = details;
            getSupportActionBar().setTitle(details.getTitle());
            voteCount.setText(details.getVoteCount() + "");
            rating.setRating(details.getVoteAverage().floatValue());
            textViewHomePage.setText(details.getHomepage());
            textViewStatus.setText(details.getStatus());
            textViewReleasedDate.setText(details.getReleaseDate());
            textViewOriginalLanguage.setText(LanguageUtil.getLanguageName(details.getOriginalLanguage()));
            textViewProductionCompany.setText(getProductionCompany());
            textViewBudget.setText(getCurrencyFormatted(details.getBudget() + ""));
            textViewOverView.setText(details.getOverview());

            Glide.with(this)
                    .load(BuildConfig.BASE_BACK_DROP_URL + details.getBackdropPath())
                    .placeholder(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_movie, null))
                    .error(ResourcesCompat.getDrawable(getResources(), R.drawable.ic_movie, null))
                    .dontAnimate()
                    .into(backdrop);
        }
    }

    private String getCurrencyFormatted(String amount) {
        try {
            DecimalFormat formatter = new DecimalFormat("###,###,##0.00");
            return formatter.format(Double.parseDouble(amount));
        } catch (Exception ex) {
        }
        return amount;
    }

    private String getProductionCompany() {
        try {
            for (ProductionCompany company : details.getProductionCompanies()) {
                return company.getName();
            }
        } catch (Exception ex) {
        }
        return getString(R.string.str_not_available);
    }
}

