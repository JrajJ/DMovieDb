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
import com.sarangshende.themoviedb.models.MovieItem;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class ___MoviesAdapter extends RecyclerView.Adapter<___MoviesAdapter.ViewHolder>
{
    private ArrayList<MovieItem> mFilteredList;
    private Context ctx ;
    int num = 1;


    public ___MoviesAdapter(ArrayList<MovieItem> arrayList)
    {
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.custom_row_movies,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {
        Bitmap bitmap;
        viewHolder.MOVIE_NAME.setText(mFilteredList.get(i).getTitle());
        viewHolder.MOVIE_DATE.setText(mFilteredList.get(i).getReleaseDate());
        viewHolder.MOVIE_DESC.setText(mFilteredList.get(i).getOverview());
        viewHolder.MOVIE_ID.setText(Integer.toString(mFilteredList.get(i).getId()));
        viewHolder.MOVIE_RATING.setText(Double.toString(mFilteredList.get(i).getVoteAverage()).trim());
        Log.e("MOVIE_RATING   =   ","_____________________________________"+viewHolder.MOVIE_RATING.getText()+"===");

        viewHolder.MOVIE_VOTES.setText(Integer.toString(mFilteredList.get(i).getVoteCount())+" Votes");

        viewHolder.MOVIE_LANGUAGE.setText(mFilteredList.get(i).getOriginalLanguage()+" | ");
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
        /*if(num*100 > mFilteredList.size()){
            return mFilteredList.size();
        }else{
            return num*100;
        }*/
        return mFilteredList.size();
    }





    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView MOVIE_NAME,MOVIE_DATE,MOVIE_DESC,MOVIE_ID,MOVIE_RATING,MOVIE_LANGUAGE,MOVIE_VOTES;
        ProgressBar pb;
        ImageButton MOVIE_IMAGE;

         ViewHolder(View view)
        {
            super(view);
            ctx = view.getContext();

            MOVIE_NAME      = view.findViewById(R.id.__MOVIE_NAME);
            MOVIE_DATE      = view.findViewById(R.id.__MOVIE_DATE);
            MOVIE_DESC      = view.findViewById(R.id.__MOVIE_DESC);
            MOVIE_ID        = view.findViewById(R.id.__MOVIE_ID);
            MOVIE_RATING    = view.findViewById(R.id.__MOVIE_RATING);
            MOVIE_LANGUAGE  = view.findViewById(R.id.__MOVIE_LANGUAGE);
            MOVIE_VOTES     = view.findViewById(R.id.__MOVIE_VOTES);
            pb              = view.findViewById(R.id.progress_bar_circular);
            MOVIE_IMAGE     = view.findViewById(R.id.__MOVIE_IMAGE);

           //=============================================================================================

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    if(CheckNetwork.isInternetAvailable(ctx))
                    {
                        Intent i = new Intent(ctx,MovieDetailsActivity.class);
                        i.putExtra("id",    MOVIE_ID.getText().toString().trim());

                        ctx.startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(ctx, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            MOVIE_IMAGE.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if(CheckNetwork.isInternetAvailable(ctx))
                    {
                        Intent i = new Intent(ctx,MovieDetailsActivity.class);
                        i.putExtra("id",    MOVIE_ID.getText().toString().trim());

                        ctx.startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(ctx, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }



}
