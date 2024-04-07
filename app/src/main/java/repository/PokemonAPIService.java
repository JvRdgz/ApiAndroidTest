package repository;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonAPIService {

    @GET("pokemon/?limit=50")
    Call getPokemons();
}
