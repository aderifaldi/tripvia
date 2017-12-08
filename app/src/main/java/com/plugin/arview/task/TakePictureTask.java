package com.plugin.arview.task;

import android.os.AsyncTask;

import com.plugin.arview.recognizeim.RecognizeImHelper;

public class TakePictureTask extends AsyncTask<String, String, String> {

    private RecognizeImHelper recognizeImHelper;

    public TakePictureTask(RecognizeImHelper recognizeImHelper) {
        this.recognizeImHelper = recognizeImHelper;
    }

    @Override
    protected String doInBackground(String... params) {
        recognizeImHelper.takePicture();
        return null;
    }
}

