package com.sarangshende.themoviedb;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.sarangshende.themoviedb.connecttoserver.ConnectToServer;
import com.sarangshende.themoviedb.interfaces.MovieDBInterface;
import com.sarangshende.themoviedb.models.AllMovies;
import com.sarangshende.themoviedb.models.MovieItem;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity
{
    Context context;
    private RecyclerView mRecyclerViewMovies;
    private ___MoviesAdapter mAdapterMovies;
    ProgressDialog mProgressDialog;
    private ArrayList<MovieItem> mArrayListMovies;
    Spinner spinner_sort_list;
    ArrayAdapter<String> arrayAdapter_spinner;
    List<String> spinner_list;
    public  static boolean sort_flag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = MainActivity.this;

        initRecyclerViewMovies();

        spinner_sort_list = findViewById(R.id.spinner_sort_list);

        spinner_list = new ArrayList<>();
        spinner_list.add("Default");
        spinner_list.add("Sort by Date");
        spinner_list.add("Sort by Ratings");

        arrayAdapter_spinner = new ArrayAdapter<>(this.getApplicationContext(),
                R.layout.simple_spinner_dropdown_item, spinner_list);
        arrayAdapter_spinner    .setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        spinner_sort_list       .setAdapter(arrayAdapter_spinner);
        arrayAdapter_spinner    .notifyDataSetChanged();

        spinner_sort_list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                if (CheckNetwork.isInternetAvailable(getApplicationContext()))
                {
                    switch (position)
                    {
                        case 0:
                        {
                            loadJSONMovies();
                            break;
                        }
                        case 1:
                        {
                            sort_flag = true;
                            Collections.sort(mArrayListMovies);
                            mAdapterMovies = new ___MoviesAdapter(mArrayListMovies);
                            mRecyclerViewMovies.setAdapter(mAdapterMovies);
                            mAdapterMovies.notifyDataSetChanged();
                            break;
                        }
                        case 2:
                        {
                            sort_flag = false;
                            Collections.sort(mArrayListMovies);
                            mAdapterMovies = new ___MoviesAdapter(mArrayListMovies);
                            mRecyclerViewMovies.setAdapter(mAdapterMovies);
                            mAdapterMovies.notifyDataSetChanged();
                            break;
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, "No Internet Connection", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {

            }
        });
    }




    //=========================================================================================================

    private void initRecyclerViewMovies()
    {

        mRecyclerViewMovies = findViewById(R.id.movie_list_rcv);
        //Create new GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,
                1,//span count no of items in single row
                GridLayoutManager.VERTICAL,//Orientation
                false);//reverse scrolling of recyclerview
        //set layout manager as gridLayoutManager

        mRecyclerViewMovies.setLayoutManager(gridLayoutManager);

    }

    //=========================================================================================================

    private void loadJSONMovies()
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

            Call<AllMovies> call = request.getAllMovies();
            call.enqueue(new Callback<AllMovies>()
            {
                @Override
                public void onResponse(@NonNull Call<AllMovies> call,
                                       @NonNull Response<AllMovies> response) {

                    mProgressDialog.dismiss();
                    AllMovies jsonResponse = response.body();
                    assert jsonResponse != null;
                    mArrayListMovies = new ArrayList<>(jsonResponse.getItems());
                    mAdapterMovies = new ___MoviesAdapter(mArrayListMovies);
                    mRecyclerViewMovies.setAdapter(mAdapterMovies);

                }

                @Override
                public void onFailure(@NonNull Call<AllMovies> call, @NonNull Throwable t)
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
