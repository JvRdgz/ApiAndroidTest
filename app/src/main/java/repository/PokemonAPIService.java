package repository;

import domain.PokemonFetchResults;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface PokemonAPIService {

    @GET("pokemon")
    Call <PokemonFetchResults> getPokemonList(@Query("limit") int limit, @Query("offset") int offset);
}
