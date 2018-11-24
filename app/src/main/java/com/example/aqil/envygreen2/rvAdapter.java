package com.example.aqil.envygreen2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class rvAdapter extends RecyclerView.Adapter<rvAdapter.ViewHolder> {

    public final static String EXTRA_MOVIE = "movie";
    private ArrayList<TrashEntitiy> listTrashEntity = new ArrayList<>();
    private static final int REQUEST_DETAIL = 1;
    public static String EXTRA_REQUEST_CODE = "request code";

    public rvAdapter(Context context) {
        this.context = context;
    }

    private Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.trash_item_layout, parent, false);
        return new ViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") final int position) {

        Log.d("TAG", "onBindViewHolder: ");
        String baseUrl = "http://4a2f2ef1.ngrok.io/api/envybank/";

         Glide.with(context).load(baseUrl+listTrashEntity.get(position).thumbnail_path).apply(new RequestOptions().centerCrop().override(640, 480)).into(holder.thumbnail);
        holder.name.setText(listTrashEntity.get(position).productName);
        holder.price.setText(String.valueOf(listTrashEntity.get(position).price));
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra(EXTRA_MOVIE, listTrashEntity.get(position));
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return listTrashEntity.size();

    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnail;
        TextView price, name;
        Button btnBuy;


        ViewHolder(View itemView) {
            super(itemView);
            price = itemView.findViewById(R.id.item_layout_label_price);
            name = itemView.findViewById(R.id.item_layout_label_name);
            thumbnail = itemView.findViewById(R.id.item_layout_thumnail);
            btnBuy = itemView.findViewById(R.id.item_layout_buy_button);

        }
    }

    public void setListMvContent(ArrayList<TrashEntitiy> listMvContent) {
        this.listTrashEntity = listMvContent;
    }
}
