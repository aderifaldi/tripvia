package com.playground.skypass.app.util.image;

import android.content.Context;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;

public class DeleteImage extends AsyncTask<Void, Void, Void> {
	protected Context context;
	private ArrayList<File> files;
	private HandlerDeleteImage handler;
	
	public interface HandlerDeleteImage{
		public void afterDelete();
	}
	
	public DeleteImage(Context context, ArrayList<File> files, HandlerDeleteImage handler) {
		super();
		this.context = context;
		this.files = files;
		this.handler = handler;
	}
	
	public DeleteImage(Context context, File file, HandlerDeleteImage handler) {
		super();
		this.context = context;
		this.files = new ArrayList<File>();
		files.add(file);
		this.handler = handler;
	}

	@Override
	protected Void doInBackground(Void... params) {
		for (File file : files) {
			file.delete();
		}
		return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		if (handler != null){
			handler.afterDelete();
		}
	}
}
