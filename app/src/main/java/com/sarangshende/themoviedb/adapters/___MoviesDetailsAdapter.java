package com.sarangshende.themoviedb.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sarangshende.themoviedb.MovieDetailsActivity;
import com.sarangshende.themoviedb.NetworkCheck.CheckNetwork;
import com.sarangshende.themoviedb.R;
import com.sarangshende.themoviedb.models.GenresItem;
import com.sarangshende.themoviedb.models.MovieDetails;
import com.sarangshende.themoviedb.models.ProductionCompaniesItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ___MoviesDetailsAdapter extends RecyclerView.Adapter<___MoviesDetailsAdapter.ViewHolder>
{
    private ArrayList<MovieDetails> mFilteredList;
    private Context ctx ;


    public ___MoviesDetailsAdapter(ArrayList<MovieDetails> arrayList)
    {
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.card_view_movie_details,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {
        viewHolder.MOVIE_NAME.setText(mFilteredList.get(i).getTitle());
        viewHolder.MOVIE_DATE.setText(mFilteredList.get(i).getReleaseDate());
        viewHolder.MOVIE_DESC.setText(mFilteredList.get(i).getOverview());
        viewHolder.MOVIE_ID.setText(Integer.toString(mFilteredList.get(i).getId()));
        viewHolder.MOVIE_RATING.setText(Double.toString(mFilteredList.get(i).getVoteAverage()).trim());
        Log.e("MOVIE_RATING   =   ","_____________________________________"+viewHolder.MOVIE_RATING.getText()+"===");

        viewHolder.MOVIE_VOTES.setText(Integer.toString(mFilteredList.get(i).getVoteCount())+" Votes");
        viewHolder.MOVIE_RUNTIME.setText(Integer.toString(mFilteredList.get(i).getRuntime())+" | ");
        viewHolder.MOVIE_BUDGET.setText(Integer.toString(mFilteredList.get(i).getBudget()));
        viewHolder.MOVIE_REVENUE.setText(Integer.toString(mFilteredList.get(i).getRevenue()));
        viewHolder.MOVIE_TAGLINE.setText(mFilteredList.get(i).getTagline());

        ArrayList<GenresItem> genreList = new ArrayList<>(mFilteredList.get(i).getGenres());
        ArrayList<ProductionCompaniesItem> productionCompanyList = new ArrayList<>(mFilteredList.get(i).getProductionCompanies());
        String genre_str = "",production_company_str = "";
        for(i=0;i<genreList.size();i++)
        {
            genre_str = genre_str.concat(genreList.get(i).getName()+",");
        }

        for(i=0;i<productionCompanyList.size();i++)
        {
            production_company_str = production_company_str.concat(productionCompanyList.get(i).getName());
        }
        viewHolder.MOVIE_GENRE.setText(genre_str);
        viewHolder.MOVIE_PRODUCTION_COMPANY.setText(production_company_str);



        WindowManager wm = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
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
                        Picasso.with(ctx).
                            load(mFilteredList.get(i).getPosterPath())
                            .resize(width_custom, heightt_custom)

                            .into(viewHolder.MOVIE_IMAGE, new com.squareup.picasso.Callback() {
                                @Override
                                public void onSuccess() {
                                    viewHolder.pb.setVisibility(View.GONE);
                                }

                                @Override
                                public void onError() {

                                }
                            });


            }
            else {
                Picasso.with(ctx).
                        load(mFilteredList.get(i).getPosterPath())
                        .resize(width_custom, heightt_custom)

                        .into(viewHolder.MOVIE_IMAGE, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                viewHolder.pb.setVisibility(View.GONE);
                            }

                            @Override
                            public void onError() {

                            }
                        });
            }



        }





    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }




    @Override
    public int getItemCount() {

        return mFilteredList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView MOVIE_NAME,MOVIE_DATE,MOVIE_DESC,MOVIE_ID,MOVIE_RATING,MOVIE_LANGUAGE,MOVIE_VOTES,
                MOVIE_BUDGET,MOVIE_GENRE,MOVIE_PRODUCTION_COMPANY, MOVIE_REVENUE,MOVIE_RUNTIME,MOVIE_TAGLINE;
        ProgressBar pb;
        ImageButton MOVIE_IMAGE;

         ViewHolder(View view)
        {
            super(view);
            ctx = view.getContext();

            MOVIE_NAME      = view.findViewById(R.id.__MOVIE_DETAILS_NAME);
            MOVIE_DATE      = view.findViewById(R.id.__MOVIE_DETAILS_DATE);
            MOVIE_DESC      = view.findViewById(R.id.__MOVIE_DETAILS_DESC);
            MOVIE_ID        = view.findViewById(R.id.__MOVIE_DETAILS_ID);
            MOVIE_RATING    = view.findViewById(R.id.__MOVIE_DETAILS_RATING);
            MOVIE_LANGUAGE  = view.findViewById(R.id.__MOVIE_DETAILS_LANGUAGE);
            MOVIE_VOTES     = view.findViewById(R.id.__MOVIE_DETAILS_VOTES);
            pb              = view.findViewById(R.id.progress_bar_circular);
            MOVIE_BUDGET     = view.findViewById(R.id.__MOVIE_DETAILS_BUDGET);
            MOVIE_GENRE     = view.findViewById(R.id.__MOVIE_DETAILS_GENRE);
            MOVIE_PRODUCTION_COMPANY     = view.findViewById(R.id.__MOVIE_DETAILS_PRDC_COMPANY_NAME);
            MOVIE_REVENUE     = view.findViewById(R.id.__MOVIE_DETAILS_REVENUE);
            MOVIE_RUNTIME     = view.findViewById(R.id.__MOVIE_DETAILS_RUNTIME);
            MOVIE_TAGLINE     = view.findViewById(R.id.__MOVIE_DETAILS_TAGLINE);

           //=============================================================================================


        }
    }



}
