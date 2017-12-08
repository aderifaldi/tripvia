package com.playground.skypass.app.util.image;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveImage extends AsyncTask<Void, Void, Void> {
	private Context context;
	private Bitmap bitmap;
	private String fileName;
	private boolean success;
	private HandlerSaveImage handlerSaveImage;
	private boolean recycle;
	
	public interface HandlerSaveImage{
		public void afterSave(boolean successSave);
	}
	
	public SaveImage(Context context, String fileName, Bitmap bitmap, HandlerSaveImage handler, boolean recycle) {
		super();
		this.context = context;
		this.bitmap = bitmap;
		this.fileName = fileName;
		this.handlerSaveImage = handler;
		this.recycle = recycle;
	}
	
	private void saveImage(){
		success = false;
		try{
			File fileImage = new File(context.getFilesDir(), fileName);
			FileOutputStream fos = new FileOutputStream(fileImage);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
			bos.flush();
			bos.close();
			success = true;
		}catch (FileNotFoundException e){
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}
		if (recycle){
			bitmap.recycle();
		}
	}

	@Override
	protected Void doInBackground(Void... params) {
		if (bitmap != null && !bitmap.isRecycled()){
			saveImage();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if (handlerSaveImage != null){
			handlerSaveImage.afterSave(success);
		}
	}
}
