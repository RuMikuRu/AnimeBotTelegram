package app.api;


import app.model.Anime;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

public interface ShikimoriApi {
    @GET("/api/animes")
    Call<List<Anime>> getAnime();

    @GET("/api/animes")
    Call<List<Anime>> getAnimeSearch(@Query("search") String search);
}
