package com.pideyapp.pideyapp.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.pideyapp.pideyapp.R;
import com.pideyapp.pideyapp.model.Restaurant;

import java.util.ArrayList;

public class AdapterRestaurants extends RecyclerView.Adapter<AdapterRestaurants.RestaurantViewHolder> {

    Context context;
    ArrayList<Restaurant> restaurantArrayList;

    public AdapterRestaurants(Context context, ArrayList<Restaurant> restaurantArrayList) {
        this.context = context;
        this.restaurantArrayList = restaurantArrayList;
    }

    @NonNull
    @Override
    public RestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.restaurant_list_item, parent, false);
        return new RestaurantViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RestaurantViewHolder holder, int position) {
        Restaurant restasurant = restaurantArrayList.get(position);
        holder.txtNombre.setText(restasurant.getNombre());
        holder.txtCiudad.setText(restasurant.getCiudad());
    }

    @Override
    public int getItemCount() {
        return restaurantArrayList.size();
    }

    public static class RestaurantViewHolder extends RecyclerView.ViewHolder {

        TextView txtNombre, txtCiudad;

        public RestaurantViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNombre = itemView.findViewById(R.id.tvNombreRestaurante);
            txtCiudad = itemView.findViewById(R.id.tvCiudadRestaurante);
        }
    }

}
