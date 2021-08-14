package com.hanhpk.wallpaper.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.hanhpk.wallpaper.MyApp;
import com.hanhpk.wallpaper.Repository.Repository;
import com.hanhpk.wallpaper.models.Photo;
import com.hanhpk.wallpaper.models.Result;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * //  created by PhungKimHanh
 *
 */
public class PhotoViewModel extends ViewModel {

    private static PhotoViewModel instance;

    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final MutableLiveData<List<Photo>> listMutableLiveData = new MutableLiveData<>();
    ;
    private final MutableLiveData<Boolean> loading = new MutableLiveData<>();
    private final MutableLiveData<Boolean> downloading = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final Repository repository = new Repository();
    private final List<Photo> photoList = new ArrayList<>();


    public MutableLiveData<List<Photo>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public static PhotoViewModel getInstance(ViewModelStoreOwner owner) {
        if (instance == null) {
            instance = new ViewModelProvider(owner).get(PhotoViewModel.class);
        }
        return instance;
    }


    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }


    public MutableLiveData<Boolean> isLoading() {
        return loading;
    }

    public MutableLiveData<Boolean> isDownloading() {
        return downloading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

    public void loadPhoto(int page) {
        compositeDisposable.add(
                repository.getResult(page)
                        .doOnSubscribe(disposable -> {
                            loading.setValue(true);
                            //progressBar.setVisibility(View.VISIBLE);
                        })
                        .doFinally(() -> {
                            loading.setValue(false);
                        })
                        .subscribe(
                                result -> {
                                    if (result == null) return;
                                    photoList.addAll(result.getPhotos().getPhoto());
                                    listMutableLiveData.setValue(photoList);
                                }
                                , throwable -> {
                                    error.setValue(throwable.getMessage());
                                }));


    }


    @Override
    protected void onCleared() {
compositeDisposable.dispose();
        super.onCleared();
    }
}
