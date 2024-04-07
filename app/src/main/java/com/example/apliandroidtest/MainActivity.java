package com.example.apliandroidtest;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.GsonBuilder;

import repository.PokemonAPIService;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    // Url de la api
    // String url = "https://657cb13c853beeefdb99d85b.mockapi.io/test/v1";
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://pokeapi.co/api/v2/")
            .addConverterFactory(GsonConverterFactory.create(
                    new GsonBuilder().serializeNulls().create()
            ))
            .build();
    PokemonAPIService pokemonApiService = retrofit.create(PokemonAPIService.class);
    Call call = pokemonApiService.getPokemons();

    TextView displayData;
    Button displayButton;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        displayData = (TextView) findViewById(R.id.displayData);
        displayButton = (Button) findViewById(R.id.displayDataButton);
    }
}