package com.example.demo.util;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.demo.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageWorker{	

    static final String TAG = "ImageWorker";

	protected Context mContext;
	private ImageCache mImageCache;
	
	public ImageWorker(Context context){
		mContext = context;
	}
	
	public void loadBitmap(String url, ImageView imageView) {
        Bitmap bitmap = null;

        if (mImageCache != null) {
        	Log.v("loadBitmap","mImageCache is not null");
            bitmap = mImageCache.getBitmapFromMemCache(String.valueOf(url.substring(url.lastIndexOf("/"))));
        }
        
        Log.v("loadBitmap","checked mImageCache");
        
        if (bitmap != null) {
            // Bitmap found in memory cache
            imageView.setImageBitmap(bitmap);
        } else if (cancelPotentialWork(url, imageView)) {
			final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	        final AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.thumb_holder, 81, 81), task);
	        imageView.setImageDrawable(asyncDrawable);
	        task.execute(url);
	    }
	}
	
	public void setImageCache(ImageCache cacheCallback) {
        mImageCache = cacheCallback;
    }
	
	static class AsyncDrawable extends BitmapDrawable {
        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;

        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
            super(res, bitmap);
            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
        }

        public BitmapWorkerTask getBitmapWorkerTask() {
            return bitmapWorkerTaskReference.get();
        }
    }
	
	public static boolean cancelPotentialWork(String url, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final String bitmapData = bitmapWorkerTask.url;
            if (bitmapData != url) {
                // Cancel previous task
                bitmapWorkerTask.cancel(true);
            } else {
                // The same work is already in progress
                return false;
            }
        }
        // No task associated with the ImageView, or an existing task was cancelled
        return true;
    }

    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
       if (imageView != null) {
           final Drawable drawable = imageView.getDrawable();
           if (drawable instanceof AsyncDrawable) {
               final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
               return asyncDrawable.getBitmapWorkerTask();
           }
        }
        return null;
    }
    
    class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
    	private final WeakReference<ImageView> imageViewReference;
        private String url = "";

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(String... params) {
            url = params[0];
//            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
//            final Bitmap bitmap2 = decodeSampledBitmapFromResource(mContext.getResources(), data, 100, 100);
            final Bitmap bitmap = decodeSampledBitmapFromURL(mContext.getResources(), url, 100, 100);
            mImageCache.addBitmapToCache(url.substring(url.lastIndexOf("/")), bitmap);
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null && bitmap != null) {
                final ImageView imageView = imageViewReference.get();
                final BitmapWorkerTask bitmapWorkerTask =
                        getBitmapWorkerTask(imageView);
                if (this == bitmapWorkerTask && imageView != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
    }
    
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
        int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static Bitmap decodeSampledBitmapFromURL(Resources res, String link,
                                                         int reqWidth, int reqHeight) {

//        final DefaultHttpClient client = new DefaultHttpClient();
//        HttpGet g = new HttpGet(url);
        Bitmap bitmap = null;
        InputStream input = null;
        HttpURLConnection conn = null;
        final BitmapFactory.Options options = new BitmapFactory.Options();

        try{
            URL url = new URL(link);
            conn = (HttpURLConnection) url.openConnection();
            conn.setConnectTimeout(10000);
            conn.setDoInput(true);
            conn.setRequestMethod("GET");
            conn.connect();
            int response = conn.getResponseCode();
//            Log.d(TAG, "The response is: " + response);

//            HttpResponse response = client.execute(g);
//            HttpEntity entity = response.getEntity();

            if (response == 200){
                input = conn.getInputStream();
                // First decode with inJustDecodeBounds=true to check dimensions
//                conn.disconnect();
                options.inJustDecodeBounds = true;
//                    BitmapFactory.decodeResource(res, resId, options);
//                Log.d(TAG, "input is "+input.available());
                BitmapFactory.decodeStream(input, null, options);

                // Calculate inSampleSize
                options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

                // Decode bitmap with inSampleSize set
                options.inJustDecodeBounds = false;
                conn = (HttpURLConnection) url.openConnection();
                input = conn.getInputStream();
//                Log.d(TAG, "input is "+input.available());
                bitmap = BitmapFactory.decodeStream(input, null, options);
                if (input != null) input.close();
            }

        } catch (Exception e){
            e.printStackTrace();
            Log.e(TAG, "error when creating input stream");
        } finally {
            conn.disconnect();
        }

        return bitmap;
    }
    
    public static int calculateInSampleSize(
    	BitmapFactory.Options options, int reqWidth, int reqHeight) {
    // Raw height and width of image
    	final int height = options.outHeight;
    	final int width = options.outWidth;
    	int inSampleSize = 1;

    	if (height > reqHeight || width > reqWidth) {

    		final int halfHeight = height / 2;
    		final int halfWidth = width / 2;

    		// Calculate the largest inSampleSize value that is a power of 2 and keeps both
    		// height and width larger than the requested height and width.
    		while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
    			inSampleSize *= 2;
    		}
    	}
    	return inSampleSize;
    }
}	