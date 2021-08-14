package com.hanhpk.wallpaper.api;

import com.hanhpk.wallpaper.models.Result;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * //  created by PhungKimHanh
 *
 */

public interface ApiService {

    //services/rest/?method=flickr.favorites.getList&api_key=c80727edc0b98577bf0989a24613ad08&user_id=191864893%40N06&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=10&format=json&nojsoncallback=1

    @GET("services/rest/?method=flickr.favorites.getList&api_key=c80727edc0b98577bf0989a24613ad08&user_id=191864893%40N06&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=20&format=json&nojsoncallback=1")
    Observable<Result> getResult(@Query("page") int page);

}
