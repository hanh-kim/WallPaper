package com.hanhpk.wallpaper.exception;

import java.io.IOException;

public class NoInternetConnectionException extends IOException {
    public NoInternetConnectionException() {
        super("No internet connection");
    }
}
