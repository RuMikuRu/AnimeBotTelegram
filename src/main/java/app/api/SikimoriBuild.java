package app.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SikimoriBuild{
    public static ShikimoriApi getRequest(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://shikimori.one/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ShikimoriApi shikimoriApi = retrofit.create(ShikimoriApi.class);
        return shikimoriApi;
    }
}
