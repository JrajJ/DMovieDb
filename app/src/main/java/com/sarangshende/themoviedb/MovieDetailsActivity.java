package com.sarangshende.themoviedb;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.sarangshende.themoviedb.NetworkCheck.CheckNetwork;
import com.sarangshende.themoviedb.adapters.___MoviesAdapter;
import com.sarangshende.themoviedb.adapters.___MoviesDetailsAdapter;
import com.sarangshende.themoviedb.connecttoserver.ConnectToServer;
import com.sarangshende.themoviedb.interfaces.MovieDBInterface;
import com.sarangshende.themoviedb.models.AllMovies;
import com.sarangshende.themoviedb.models.MovieDetails;
import com.sarangshende.themoviedb.models.MovieItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
    int movie_id_;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MovieDetailsActivity.this;

        Intent i = getIntent();
        Bundle b = i.getExtras();
        if (b != null)
        {
            String id = b.getString("id");
            Log.e("Id  <--> ", "" + id);
             movie_id_ = Integer.parseInt(id);

        }
        initRecyclerViewMovieDetails();
        loadJSONMovieDetails(movie_id_);

    }




    //=========================================================================================================

    private void initRecyclerViewMovieDetails()
    {

        mRecyclerViewMovieDetails = findViewById(R.id.movie_details_rcv);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager

        mRecyclerViewMovieDetails.setLayoutManager(gridLayoutManager);

    }

    //=========================================================================================================

    private void loadJSONMovieDetails(int movie_id_)
    {
        if (CheckNetwork.isInternetAvailable(getApplicationContext())) {
            Log.e("Inside","--------------------------------------------------------------------------");
            // Create a progressdialog
            mProgressDialog = new ProgressDialog(context);
            // Set progressdialog title
            mProgressDialog.setTitle("Fetching Data");
            // Set progressdialog message
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

            mProgressDialog.setIndeterminate(false);
            // Show progressdialog
            mProgressDialog.show();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(ConnectToServer.URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            MovieDBInterface request = retrofit.create(MovieDBInterface.class);
            Call<List<MovieDetails>> call = request.getMovieDetails(movie_id_);
            call.enqueue(new Callback<List<MovieDetails>>()
            {
                @Override
                public void onResponse(@NonNull Call<List<MovieDetails>> call,
                                       @NonNull Response<List<MovieDetails>> response) {

                    mProgressDialog.dismiss();
                    List<MovieDetails> jsonResponse = response.body();
                    assert jsonResponse != null;
                    mArrayListMovieDetails = new ArrayList<>(jsonResponse);
                    mAdapterMovieDetails = new ___MoviesDetailsAdapter(mArrayListMovieDetails);
                    mRecyclerViewMovieDetails.setAdapter(mAdapterMovieDetails);

                }

                @Override
                public void onFailure(@NonNull Call<List<MovieDetails>> call, @NonNull Throwable t)
                {
                    Log.e("onFailure","--------------------------------------------------------------------------");

                    Log.e("Error", t.getMessage());
                    mProgressDialog.dismiss();
                }
            });

        } else
        {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


}
