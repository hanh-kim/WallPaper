package com.hanhpk.wallpaper.api;

import android.content.Context;

import com.hanhpk.wallpaper.exception.NoInternetConnectionException;
import com.hanhpk.wallpaper.util.Utils;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * //  created by PhungKimHanh
 *
 */

public class ConnectivityInterceptor implements Interceptor {
    private Context context;

    public ConnectivityInterceptor(Context context) {
        this.context = context;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        if (!Utils.checkNetwork(context)){
            throw new NoInternetConnectionException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }
}
