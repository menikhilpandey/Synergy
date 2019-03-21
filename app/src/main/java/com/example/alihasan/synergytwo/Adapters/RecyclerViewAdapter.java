package com.example.alihasan.synergytwo.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.alihasan.synergytwo.CounterSingleton;
import com.example.alihasan.synergytwo.Database.ImageDatabase.ImageViewModel;
import com.example.alihasan.synergytwo.R;

import com.bumptech.glide.Glide;
import com.example.alihasan.synergytwo.api.service.Client;
import com.example.alihasan.synergytwo.api.service.ServerURL;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by User on 2/12/2018.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    static String SERVER_URL = new ServerURL().getSERVER_URL();

    private CounterSingleton counter;

    /**
     * DD changes
     */

    private ImageViewModel imageViewModel;
    /**
     *
     */


    //vars
    private ArrayList<Bitmap> mImageUrls = new ArrayList<>();
    private ArrayList<String> mimageName = new ArrayList<>();
    private Context mContext;
    TextView memptyView;

    public RecyclerViewAdapter(Context context, ArrayList<Bitmap> imageUrls, ArrayList<String> imageName, TextView emptyView, ImageViewModel mImageViewModel) {
        mImageUrls = imageUrls;
        mContext = context;
        memptyView = emptyView;
        mimageName = imageName;
        imageViewModel = mImageViewModel;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.form_imageitem, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mContext)
                .asBitmap()
                .load(mImageUrls.get(position))
                .into(holder.image);

        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteImageRetro(mimageName,mimageName.get(position),position);

            }
        });

    }




    @Override
    public int getItemCount() {
        return mImageUrls.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        ImageButton button;

        public ViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image_view);
            button = itemView.findViewById(R.id.clearButton);
        }

        // onClick Listener for view




    }



    private void deleteImageRetro(final ArrayList<String> mimageNameList, String mimageName, final int position){

        imageViewModel.delete(mimageName);

        /**
         * Delete Image from View
         */
        Toast.makeText(mContext, "Image Removed", Toast.LENGTH_SHORT).show();
        mImageUrls.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mImageUrls.size());

        if(mImageUrls.size() == 0)
        {
            memptyView.setVisibility(View.VISIBLE);
        }
        mimageNameList.remove(position);
        counter = CounterSingleton.getInstance();
        counter.decreaseCounter();

//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(SERVER_URL)
//                .addConverterFactory(GsonConverterFactory.create()) //Here we are using the GsonConverterFactory to directly convert json data to object
//                .build();
//
//        Client client = retrofit.create(Client.class);
//
//        Call<String> call = client.deleteImage(mimageName);
//
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//
//                if(response.body() == null)
//                {
//                    Toast.makeText(mContext, "SERVER IS DOWN", Toast.LENGTH_SHORT).show();
//
//                }
//
//                else if(response.body().equals("Success"))
//                {
//                    Toast.makeText(mContext, "Image Removed", Toast.LENGTH_SHORT).show();
//                    mImageUrls.remove(position);
//                    notifyItemRemoved(position);
//                    notifyItemRangeChanged(position, mImageUrls.size());
//
//                    if(mImageUrls.size() == 0)
//                    {
//                        memptyView.setVisibility(View.VISIBLE);
//                    }
//                    mimageNameList.remove(position);
//                    counter = CounterSingleton.getInstance();
//                    counter.decreaseCounter();
//                }
//                else
//                {
//                    Toast.makeText(mContext, "SOMETHING WENT WRONG", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                Toast.makeText(mContext, "IN FAILURE", Toast.LENGTH_SHORT).show();
//
//
//            }
//        });

    }


}
