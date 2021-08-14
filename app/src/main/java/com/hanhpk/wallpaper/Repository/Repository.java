package com.hanhpk.wallpaper.Repository;




import com.hanhpk.wallpaper.api.ApiService;
import com.hanhpk.wallpaper.api.RetrofitClient;
import com.hanhpk.wallpaper.models.Result;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

public class Repository {
    private final ApiService apiService;


    public Repository() {
        this.apiService = RetrofitClient.getInstance().getApiService();

    }

    public Observable<Result> getResult(int page){
        return apiService.getResult(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }






}
