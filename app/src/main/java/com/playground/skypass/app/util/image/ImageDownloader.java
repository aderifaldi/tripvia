package com.playground.skypass.app.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v4.util.LruCache;

import com.radyalabs.async.AsyncHttpClient;
import com.radyalabs.async.FileAsyncHttpResponseHandler;

import org.apache.http.Header;

import java.io.File;

public class ImageDownloader implements LoadImage.ImageFinishLoad {
    private static final long MAX_CACHE_FILE_TIME = 36000000l; //10jam
    private static final String DIR_NAME = "cache_image_downloader";
    private static final String TAG = "ImageDownloader";
    private String url;
    private File fileSave;
    private int sizeOption;
    private boolean useWidth;
    private Context context;
    private int returnCode;
    private BitmapLruCache lruCache;
    private String hash;
    private OnImageFinishDownload onImageFinishDownload;

    public interface OnImageFinishDownload {
        void onFinish(Bitmap bitmap, int returnCode);
    }

    public ImageDownloader(String url, Context context, OnImageFinishDownload onFinish) {
        this.url = url;
        this.onImageFinishDownload = onFinish;
        this.context = context;
        lruCache = new BitmapLruCache();
        hash = CipherUtil.sha256(url);
        this.fileSave = new File(context.getDir("cache_url", Context.MODE_PRIVATE), hash);
        DeleteOldImage delete = new DeleteOldImage();
        delete.execute();
    }

    public void setForceDownload() {
        lruCache.remove(hash);
        if (fileSave.exists()) {
            fileSave.delete();
        }
    }

    public void setSizeOption(int sizeOption, boolean useWidth) {
        this.sizeOption = sizeOption;
        this.useWidth = useWidth;
    }

    public void execute() {
        Bitmap bitmap = lruCache.get(hash);

        LoadImage.ImageFinishLoad onCacheLoad = new LoadImage.ImageFinishLoad() {
            @Override
            public void onImageFinishLoad(Bitmap bitmap) {
//                AppUtility.logD(TAG, "image load using file");
                if (bitmap != null) {
                    ImageDownloader.this.onImageFinishLoad(bitmap);
                }

                try {
                    AsyncHttpClient downloader = new AsyncHttpClient();
                    FileAsyncHttpResponseHandler fileAsync = new FileAsyncHttpResponseHandler(fileSave) {

                        @Override
                        public void onSuccess(int arg0, Header[] arg1, File file) {
//                            AppUtility.logD(TAG, "download success");
                            LoadImage load = new LoadImage(file, context, ImageDownloader.this, sizeOption, useWidth);
                            load.execute();
                        }

                        @Override
                        public void onFailure(int code, Header[] arg1, Throwable arg2, File arg3) {
//                            AppUtility.logD(TAG, "download failed");
                            returnCode = code;
                            onImageFinishDownload.onFinish(null, returnCode);
                        }
                    };
                    downloader.get(url, fileAsync);
                } catch (OutOfMemoryError oomError) {

                }

            }
        };

        if (bitmap == null && fileSave.exists()) {
            LoadImage load = new LoadImage(fileSave, context, onCacheLoad, sizeOption, useWidth);
            load.execute();
        } else if (bitmap != null) {
//            AppUtility.logD(TAG, "image load using lrucache");
            onImageFinishDownload.onFinish(bitmap, 100);
        } else {
//            AppUtility.logD(TAG, "image load using download");
            try {
                AsyncHttpClient downloader = new AsyncHttpClient();
                FileAsyncHttpResponseHandler fileAsync = new FileAsyncHttpResponseHandler(fileSave) {

                    @Override
                    public void onSuccess(int arg0, Header[] arg1, File file) {
//                        AppUtility.logD(TAG, "download success");
                        LoadImage load = new LoadImage(file, context, ImageDownloader.this, sizeOption, useWidth);
                        load.execute();
                    }

                    @Override
                    public void onFailure(int code, Header[] arg1, Throwable arg2, File arg3) {
//                        AppUtility.logD(TAG, "download failed");
                        returnCode = code;
                        onImageFinishDownload.onFinish(null, returnCode);
                    }
                };
                downloader.get(url, fileAsync);
            } catch (OutOfMemoryError oomError) {

            }

        }
    }

    @Override
    public void onImageFinishLoad(Bitmap bitmap) {
        if (bitmap != null) {
            lruCache.put(hash, bitmap);
        }
        onImageFinishDownload.onFinish(bitmap, returnCode);
    }

    private class DeleteOldImage extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... params) {
            File file = context.getDir(DIR_NAME, Context.MODE_PRIVATE);
            if (file.exists() && file.isDirectory()) {
                File[] files = file.listFiles();
                long timeNow = System.currentTimeMillis();
                for (int i = 0; i < files.length; i++) {
                    long difference = timeNow - file.lastModified();
                    if (difference > MAX_CACHE_FILE_TIME) {
                        file.delete();
                    }
                }
            }
            return null;
        }

    }

    public static class BitmapLruCache extends LruCache<String, Bitmap> {
        private static final int DEFAULT_CACHE_SIZE = (int) (Runtime.getRuntime().maxMemory() / 1024) / 8;

        public BitmapLruCache() {
            this(DEFAULT_CACHE_SIZE);
        }

        public BitmapLruCache(int maxSize) {
            super(maxSize);
        }

        @Override
        protected int sizeOf(String key, Bitmap value) {
            return value == null ? 0 : value.getRowBytes() * value.getHeight() / 1024;
        }
    }
}
