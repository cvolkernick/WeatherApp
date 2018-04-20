package com.example.admin.weatherapp.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.admin.weatherapp.R;
import com.example.admin.weatherapp.model.ListItem;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    List<ListItem> weatherItems;

    public RecyclerViewAdapter(List<ListItem> weatherItems) {
        this.weatherItems = weatherItems;
    }

    @NonNull
    @Override

    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.weather_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ListItem weatherItem = weatherItems.get(position);

        if (position % 8 == 0)
            holder.tvWeather.setText(""+ weatherItem.getWeather().get(0).getDescription());

    }

    @Override
    public int getItemCount() {
        return weatherItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tvWeather;

        public ViewHolder(View itemView) {
            super(itemView);
            tvWeather = itemView.findViewById(R.id.tvWeather);
        }
    }
}
