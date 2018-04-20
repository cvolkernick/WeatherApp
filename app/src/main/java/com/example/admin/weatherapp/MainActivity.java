package com.example.admin.weatherapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.example.admin.weatherapp.adapters.RecyclerViewAdapter;
import com.example.admin.weatherapp.model.ListItem;
import com.example.admin.weatherapp.model.WeatherItem;
import com.example.admin.weatherapp.model.WeatherResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    String BASE_URL = "http://api.openweathermap.org/data/2.5/";
    private RecyclerView rvMain;
    private RecyclerView.LayoutManager layoutManager;
    List<ListItem> weatherItems = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvMain = findViewById(R.id.rvMain);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        adapter = new RecyclerViewAdapter(weatherItems);

        rvMain.setLayoutManager(layoutManager);
        rvMain.setAdapter(adapter);

        getWeather();

    }

    public void getWeather() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        IWeatherService apiService = retrofit.create(IWeatherService.class);

        Call<WeatherResponse> call = apiService.getResponse();
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                int statusCode = response.code();
                WeatherResponse weatherResponse = response.body();

                weatherItems.clear();
                weatherItems.addAll(weatherResponse.getList());

                for (int i = 0; i < weatherItems.size(); i += 8) {
                    Log.d(TAG, "onResponse: " + weatherItems.get(i).getDtTxt() + " : "
                            + weatherItems.get(i).getWeather().get(0).getDescription().toString());
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<WeatherResponse> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    public void onGetWeather(View view) {
    }

    public interface IWeatherService {
        // Request method and URL specified in the annotation

        @GET("forecast?lat=35&lon=139&appid=ccb67a7bbec4c82b2b835fd65d0c0ca1")
        Call<WeatherResponse> getResponse();
    }
}
