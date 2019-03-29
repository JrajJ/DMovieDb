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
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.sarangshende.themoviedb.MovieDetailsActivity;
import com.sarangshende.themoviedb.NetworkCheck.CheckNetwork;
import com.sarangshende.themoviedb.R;
import com.sarangshende.themoviedb.models.CastItem;
import com.sarangshende.themoviedb.models.MovieItem;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ___CastAdapter extends RecyclerView.Adapter<___CastAdapter.ViewHolder>
{
    private ArrayList<CastItem> mFilteredList;
    private Context ctx ;



    public ___CastAdapter(ArrayList<CastItem> arrayList)
    {
        mFilteredList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(
                viewGroup.getContext()).inflate(R.layout.card_view_cast_item,
                viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int i)
    {
        viewHolder.CAST_NAME.setText(mFilteredList.get(i).getName());
        viewHolder.CAST_CHARACTER.setText("as "+mFilteredList.get(i).getCharacter());

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
                            load(mFilteredList.get(i).getProfilePath())
                            .resize(width_custom, heightt_custom)

                            .into(viewHolder.CAST_IMAGE, new com.squareup.picasso.Callback() {
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
                        load(mFilteredList.get(i).getProfilePath())
                        .resize(width_custom, heightt_custom)

                        .into(viewHolder.CAST_IMAGE, new com.squareup.picasso.Callback() {
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
        TextView CAST_NAME,CAST_CHARACTER;
        ProgressBar pb;
        ImageButton CAST_IMAGE;

         ViewHolder(View view)
        {
            super(view);
            ctx = view.getContext();

            CAST_NAME      = view.findViewById(R.id.__CAST_NAME);
            CAST_CHARACTER      = view.findViewById(R.id.__CAST_CHARACTER);

            pb              = view.findViewById(R.id.CAST_pb_circular);
            CAST_IMAGE     = view.findViewById(R.id.__CAST_IMAGE);

           //=============================================================================================


        }
    }



}
