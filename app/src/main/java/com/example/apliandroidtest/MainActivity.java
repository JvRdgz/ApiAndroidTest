package com.example.apliandroidtest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.util.ArrayList;

import domain.Pokemon;
import domain.PokemonFetchResults;
import repository.PokemonAPIService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "POKEDEX";
    private Retrofit retrofit;
    private PokemonAdapterList pokemonAdapterList;
    private int offset;
    private boolean goodToGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView title = findViewById(R.id.titleTextView);
        // PokemonAPIService pokemonApiService = retrofit.create(PokemonAPIService.class);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        pokemonAdapterList = new PokemonAdapterList(this);
        recyclerView.setAdapter(pokemonAdapterList);
        recyclerView.setHasFixedSize(true);
        final GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (goodToGo) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i(TAG, "Llegamos al final");

                            goodToGo = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
        });

        retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create(
                        new GsonBuilder().serializeNulls().create()
                ))
                .build();
        goodToGo = true;
        offset = 0;
        getData(offset);
    }

    private void getData(int offset) {
        PokemonAPIService service = retrofit.create(PokemonAPIService.class);
        Call<PokemonFetchResults> pokemonFetchResultsCall = service.getPokemonList(20, offset);
        pokemonFetchResultsCall.enqueue(new Callback<PokemonFetchResults>() {
            @Override
            public void onResponse(Call<PokemonFetchResults> call, Response<PokemonFetchResults> response) {
                goodToGo = true;
                if (response.isSuccessful()) {
                    PokemonFetchResults pokemonFetchResults = response.body();
                    ArrayList<Pokemon> pokemonList = pokemonFetchResults.getResults();

                    pokemonAdapterList.addPokemonList(pokemonList);
                } else {
                    Log.e(TAG, " onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<PokemonFetchResults> call, Throwable t) {
                goodToGo = true;
                Log.e(TAG, " onFailure: " + t.getMessage());
            }
        });
    }
}