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

import java.io.InputStream;
import java.lang.ref.WeakReference;

public class ImageWorker{	

	protected Context mContext;
	private ImageCache mImageCache;
	
	public ImageWorker(Context context){
		mContext = context;
	}
	
	public void loadBitmap(int resId, ImageView imageView) {
        Bitmap bitmap = null;

        if (mImageCache != null) {
        	Log.v("loadBitmap","mImageCache is not null");
            bitmap = mImageCache.getBitmapFromMemCache(String.valueOf(resId));
        }
        
        Log.v("loadBitmap","checked mImageCache");
        
        if (bitmap != null) {
            // Bitmap found in memory cache
            imageView.setImageBitmap(bitmap);
        } else if (cancelPotentialWork(resId, imageView)) {
			final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	        final AsyncDrawable asyncDrawable = new AsyncDrawable(mContext.getResources(), decodeSampledBitmapFromResource(mContext.getResources(), R.drawable.ic_launcher, 81, 81), task);
	        imageView.setImageDrawable(asyncDrawable);
	        task.execute(resId);
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
	
	public static boolean cancelPotentialWork(int data, ImageView imageView) {
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);

        if (bitmapWorkerTask != null) {
            final int bitmapData = bitmapWorkerTask.data;
            if (bitmapData != data) {
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
    
    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
    	private final WeakReference<ImageView> imageViewReference;
        private int data = 0;

        public BitmapWorkerTask(ImageView imageView) {
            // Use a WeakReference to ensure the ImageView can be garbage collected
            imageViewReference = new WeakReference<ImageView>(imageView);
        }

        // Decode image in background.
        @Override
        protected Bitmap doInBackground(Integer... params) {
            data = params[0];
//            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
            final Bitmap bitmap = decodeSampledBitmapFromResource(mContext.getResources(), data, 100, 100);
            mImageCache.addBitmapToCache(String.valueOf(data), bitmap);
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

    public static Bitmap decodeSampledBitmapFromURL(Resources res, String url,
                                                         int reqWidth, int reqHeight) {

        final DefaultHttpClient client = new DefaultHttpClient();
        HttpGet g = new HttpGet(params[0]);
        InputStream input = null;
        final BitmapFactory.Options options = new BitmapFactory.Options();

        try{
            HttpResponse response = client.execute(g);
            HttpEntity entity = response.getEntity();

            if (entity != null){
                try{
                    input = entity.getContent();
                    // First decode with inJustDecodeBounds=true to check dimensions

                    options.inJustDecodeBounds = true;
//                    BitmapFactory.decodeResource(res, resId, options);
                    BitmapFactory.decodeStream(input, null, options);

                    // Calculate inSampleSize
                    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

                    // Decode bitmap with inSampleSize set
                    options.inJustDecodeBounds = false;
                } finally {
                    input.close();
                }
            }

        } catch (Exception e){
            e.printStackTrace();
        }

        return BitmapFactory.decodeStream(input, null, options);
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