package domain;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class PokemonFetchResults {

    private ArrayList<Pokemon> results;

    public ArrayList<Pokemon> getResults () {
        return results;
    }
}
