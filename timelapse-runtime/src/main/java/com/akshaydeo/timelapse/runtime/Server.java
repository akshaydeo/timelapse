package com.akshaydeo.timelapse.runtime;

import android.util.Log;

import fi.iki.elonen.NanoHTTPD;

/**
 * Timelapse server serving an html containing all the information about the current run
 * Created by akshay.d on 21/09/17.
 */

public final class Server extends NanoHTTPD {
    private static final String TAG = "##Server##";

    public Server(int port) {
        super(port);
    }

    public Server(String hostname, int port) {
        super(hostname, port);
    }

    @Override
    public Response serve(IHTTPSession session) {
        Log.d(TAG, "serving");
        String msg = "<html><body><h1>Timelapse</h1>\n";
        msg += Stopwatch.getInstance().getLapsHtml();
        return newFixedLengthResponse(msg + "</body></html>\n");
    }
}
