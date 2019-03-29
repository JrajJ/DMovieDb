package com.sarangshende.themoviedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.sarangshende.themoviedb.NetworkCheck.CheckNetwork;
import com.sarangshende.themoviedb.adapters.___MoviesAdapter;
import com.sarangshende.themoviedb.adapters.___MoviesDetailsAdapter;
import com.sarangshende.themoviedb.connecttoserver.ConnectToServer;
import com.sarangshende.themoviedb.interfaces.MovieDBInterface;
import com.sarangshende.themoviedb.models.AllMovies;
import com.sarangshende.themoviedb.models.MovieDetails;
import com.sarangshende.themoviedb.models.MovieItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.icu.text.UnicodeSet.CASE;

public class MovieDetailsActivity extends AppCompatActivity
{
    Context context;
    private RecyclerView mRecyclerViewMovieDetails;
    private ___MoviesDetailsAdapter mAdapterMovieDetails;
    ProgressDialog mProgressDialog;
    private ArrayList<MovieDetails> mArrayListMovieDetails;
    Spinner spinner_sort_list;
    TextView MOVIE_NAME,MOVIE_DATE,MOVIE_DESC,MOVIE_ID,MOVIE_RATING,MOVIE_LANGUAGE,MOVIE_VOTES,
            MOVIE_BUDGET,MOVIE_GENRE,MOVIE_PRODUCTION_COMPANY, MOVIE_REVENUE,MOVIE_RUNTIME,MOVIE_TAGLINE;
    ProgressBar pb;
    ImageButton MOVIE_IMAGE;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        context = MovieDetailsActivity.this;

        viewsInitialization();

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null)
        {
            String id = b.getString("id");
            Log.e("Id  <--> ", "" + id);
            loadJSONMovieDetails(id);
        }

    }

    private void viewsInitialization()
    {
        MOVIE_NAME      = findViewById(R.id.__MOVIE_DETAILS_NAME);
        MOVIE_DATE      = findViewById(R.id.__MOVIE_DETAILS_DATE);
        MOVIE_DESC      = findViewById(R.id.__MOVIE_DETAILS_DESC);
        MOVIE_ID        = findViewById(R.id.__MOVIE_DETAILS_ID);
        MOVIE_RATING    = findViewById(R.id.__MOVIE_DETAILS_RATING);
        MOVIE_LANGUAGE  = findViewById(R.id.__MOVIE_DETAILS_LANGUAGE);
        MOVIE_VOTES     = findViewById(R.id.__MOVIE_DETAILS_VOTES);
        pb              = findViewById(R.id.movie_details_pb_circular);
        MOVIE_IMAGE     = findViewById(R.id.__MOVIE_DETAILS_IMAGE);
        MOVIE_BUDGET    = findViewById(R.id.__MOVIE_DETAILS_BUDGET);
        MOVIE_GENRE     = findViewById(R.id.__MOVIE_DETAILS_GENRE);
        MOVIE_REVENUE   = findViewById(R.id.__MOVIE_DETAILS_REVENUE);
        MOVIE_RUNTIME   = findViewById(R.id.__MOVIE_DETAILS_RUNTIME);
        MOVIE_TAGLINE   = findViewById(R.id.__MOVIE_DETAILS_TAGLINE);
        MOVIE_PRODUCTION_COMPANY  = findViewById(R.id.__MOVIE_DETAILS_PRDC_COMPANY_NAME);
    }


    //=========================================================================================================

    private void loadJSONMovieDetails(String movie_id_)
    {

            Log.e("Inside","--------------------------------------------------------------------------");
            /*// Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            mProgressDialog.setTitle("Fetching Data");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();*/

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConnectToServer.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            final MovieDBInterface request = retrofit.create(MovieDBInterface.class);
            Call<MovieDetails> call = request.getMovieDetails(movie_id_);
            call.enqueue(new Callback<MovieDetails>()
            {
                @Override
                public void onResponse(@NonNull Call<MovieDetails> call,
                                       @NonNull Response<MovieDetails> response) {

                    //mProgressDialog.dismiss();
                    MovieDetails jsonResponse = response.body();

                    assert response.body() != null;
                    mArrayListMovieDetails = new ArrayList<>();
                    mArrayListMovieDetails.add(jsonResponse);
                    MOVIE_NAME.setText(mArrayListMovieDetails.get(0).getTitle());
                    MOVIE_DATE.setText(mArrayListMovieDetails.get(0).getReleaseDate());
                    MOVIE_DESC.setText(mArrayListMovieDetails.get(0).getOverview());
                    MOVIE_ID.setText(mArrayListMovieDetails.get(0).getId());
                    MOVIE_RATING.setText(String.valueOf(mArrayListMovieDetails.get(0).getVoteAverage()));
                    MOVIE_LANGUAGE.setText(mArrayListMovieDetails.get(0).getOriginalLanguage()+" | ");
                    MOVIE_VOTES.setText(mArrayListMovieDetails.get(0).getVoteCount());
                    MOVIE_BUDGET.setText(mArrayListMovieDetails.get(0).getBudget());
                    MOVIE_GENRE.setText(mArrayListMovieDetails.get(0).getGenres().get(0).getName());
                    MOVIE_REVENUE.setText(mArrayListMovieDetails.get(0).getRevenue());
                    MOVIE_TAGLINE.setText(mArrayListMovieDetails.get(0).getTagline());
                    MOVIE_RUNTIME.setText(mArrayListMovieDetails.get(0).getRuntime()+" | ");
                    MOVIE_PRODUCTION_COMPANY.setText(mArrayListMovieDetails.get(0).getProductionCompanies().get(0).getName()+" | "+
                            mArrayListMovieDetails.get(0).getProductionCompanies().get(0).getOriginCountry());


                    WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                    assert wm != null;
                    Display display = wm.getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    int heightt = size.y;
                    Log.e("width   =   ",""+width);
                    Log.e("height   =   ",""+heightt);

                    int width_custom = width/3;
                    int heightt_custom = heightt/4;
                    Log.e("width_custom   =   ",""+width_custom);
                    Log.e("heightt_custom   =   ",""+heightt_custom);

                    if(width>800) {
                        Picasso.with(context).
                                load(mArrayListMovieDetails.get(0).getPosterPath())
                                .resize(width_custom, heightt_custom)

                                .into(MOVIE_IMAGE, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        pb.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });


                    }
                    else {
                        Picasso.with(context).
                                load(mArrayListMovieDetails.get(0).getPosterPath())
                                .resize(width_custom, heightt_custom)

                                .into(MOVIE_IMAGE, new com.squareup.picasso.Callback() {
                                    @Override
                                    public void onSuccess() {
                                        pb.setVisibility(View.GONE);
                                    }

                                    @Override
                                    public void onError() {

                                    }
                                });
                    }
                }

                @Override
                public void onFailure(@NonNull Call<MovieDetails> call, @NonNull Throwable t)
                {
                    Log.e("onFailure","--------------------------------------------------------------------------");

                    Log.e("Error", t.getMessage());
                    //mProgressDialog.dismiss();
                }
            });


    }


}
