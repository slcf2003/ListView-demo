//
//package com.example.demo.ui;
//
////import java.lang.ref.WeakReference;
//import java.util.ArrayList;
//import java.util.HashMap;
////import java.util.List;
//
//import com.example.demo.R;
//import com.example.demo.util.ImageCache;
//import com.example.demo.util.ImageWorker;
//import com.example.demo.util.ImageCache.ImageCacheParams;
//
//import android.support.v4.app.ListFragment;
//import android.app.Activity;
//import android.content.Context;
//import android.content.Intent;
///*import android.content.res.Resources;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.drawable.BitmapDrawable;
//import android.graphics.drawable.Drawable;
//import android.os.AsyncTask;  */
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.AbsListView;
//import android.widget.AbsListView.OnScrollListener;
//import android.widget.BaseAdapter;
//import android.widget.ImageView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//public class Club extends ListFragment implements OnScrollListener{
//
//	private static final String TAG = "Club";
//
////	private SimpleAdapter adapter;
////	private ImageCache mImageCache;
//	private ImageWorker mImageWorker;
//	private MyAdapter mAdapter;
//	private ArrayList<HashMap<String, Object>> mHashmaps;
//	private HashMap<String, Object> map;
//	ArrayList<HashMap<String, Object>> data;
//
//	private String[] title = {"club 1","club 2","club 3","club Gin","club Ice wine","club 6","club 7","club Latte","club Fish&Chips"};
//	private String[] desc = {"wonderful 1","wonderful 2","wonderful 3","wonderful 4","wonderful 5","wonderful 6","wonderful 7","wonderful 8","wonderful 9"};
//	private int[] img = {R.drawable.excel, R.drawable.lync, R.drawable.note, R.drawable.out, R.drawable.power, R.drawable.pub, R.drawable.word
//			,R.drawable.airplane, R.drawable.note};
//
//
//	@Override
//	public void onAttach(Activity activity) {
//		// TODO Auto-generated method stub
//		super.onAttach(activity);
//		data = getData();
//	}
//
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		Log.d(TAG, "onCreate in Fragment2");
//		super.onCreate(savedInstanceState);
//	//	adapter = new SimpleAdapter(getActivity(), getData(), R.layout.club_list_layout, new String[] {"img", "title", "member", "desc"}
//	//	, new int[]{R.id.thumbnail, R.id.title, R.id.member, R.id.desc});
//	//	setListAdapter(adapter);
//	//	mAdapter = new MyAdapter(getActivity(), getData());
//		mAdapter = new MyAdapter(getActivity(), data);
//		mImageWorker = new ImageWorker(getActivity());
//
//		ImageCacheParams cacheParams = new ImageCacheParams("thumb");
//		mImageWorker.setImageCache(ImageCache.findOrCreateCache(getActivity(), cacheParams));
//
//	}
//
//	@Override
//	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//		View v = inflater.inflate(R.layout.main_club, container, false);
//		setListAdapter(mAdapter);
//		return v;
//	}
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        getListView().setOnScrollListener(this);
//    }
//
//    private ArrayList<HashMap<String, Object>> getData(){
//		mHashmaps = new ArrayList<HashMap<String, Object>>();
//
//		for(int i = 0; i < title.length; i++){
//			map = new HashMap<String, Object>();
//			map.put("img", img[i]);
//			map.put("title", title[i]);
//	//		map.put("member", member[i]);
//			map.put("desc", desc[i]);
//			mHashmaps.add(map);
//		}
//		return mHashmaps;
//	}
//
//	@Override
//	public void onScrollStateChanged(AbsListView view, int scrollState) {
//		// TODO Auto-generated method stub
//
//	}
//
//	@Override
//	public void onScroll(AbsListView view, int firstVisibleItem,
//			int visibleItemCount, int totalItemCount) {
//		// TODO Auto-generated method stub
//
//	}
//	/*
//	@Override
//	public void onListItemClick(ListView l, View v, int position, long id) {
//		// TODO Auto-generated method stub
//		super.onListItemClick(l, v, position, id);
//		Log.v(TAG,"Click");
//        Toast.makeText(getActivity(), "You have selected " + position, Toast.LENGTH_SHORT).show();
//
//        Intent intent = new Intent();
//        intent.setClass(getActivity(), Club.class);
//        startActivity(intent);
//	}
//	*/
//	private class MyAdapter extends BaseAdapter{
//
//		LayoutInflater inflater;
//		Context context;
//		ArrayList<HashMap<String, Object>> data;
//
//		MyAdapter(Context context, ArrayList<HashMap<String, Object>> data){
//			this.context = context;
//			this.data = data;
//		}
//
//		@Override
//		public int getCount() {
//			// TODO Auto-generated method stub
//			return (data.size()<10)?data.size():10;
//		}
//
//		@Override
//		public Object getItem(int position) {
//			// TODO Auto-generated method stub
//			return data.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			// TODO Auto-generated method stub
//			return position;
//		}
//
//		@Override
//		public View getView(final int position, View convertView, ViewGroup parent) {
//			// TODO Auto-generated method stub
//			inflater = LayoutInflater.from(context);
//			ViewHolder holder;
//
//			if(convertView == null){
//				holder = new ViewHolder();
//
//				convertView = inflater.inflate(R.layout.club_list_layout, parent, false);
//				holder.img = (ImageView)convertView.findViewById(R.id.thumbnail);
//				holder.title = (TextView)convertView.findViewById(R.id.title);
//				holder.desc = (TextView)convertView.findViewById(R.id.desc);
//
//				convertView.setTag(holder);
//			}else{
//				holder = (ViewHolder)convertView.getTag();
//			}
//
//			holder.title.setText(data.get(position).get("title").toString());
//			holder.desc.setText(data.get(position).get("desc").toString());
//	//		holder.img.setImageResource((Integer) data.get(position).get("img"));
//			mImageWorker.loadBitmap((Integer) data.get(position).get("img"), holder.img);
//
//			convertView.setOnClickListener(new OnClickListener(){
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Log.v(TAG,"Click");
//			        Toast.makeText(getActivity(), "You have selected " + position, Toast.LENGTH_SHORT).show();
//			        Intent intent = new Intent();
//			        intent.setClass(getActivity(), ClubDetail.class);
//			        startActivity(intent);
//				}
//			});
//			return convertView;
//		}
//	}
///*
//	public void loadBitmap(int resId, ImageView imageView) {
//        Bitmap bitmap = null;
//
//        if (mImageCache != null) {
//        	Log.v("loadBitmap","mImageCache is not null");
//            bitmap = mImageCache.getBitmapFromMemCache(String.valueOf(resId));
//        }
//
//        Log.v("loadBitmap","checked mImageCache");
//
//        if (bitmap != null) {
//            // Bitmap found in memory cache
//            imageView.setImageBitmap(bitmap);
//        } else if (cancelPotentialWork(resId, imageView)) {
//			final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
//	        final AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(), decodeSampledBitmapFromResource(getResources(), R.drawable.ic_launcher, 81, 81), task);
//	        imageView.setImageDrawable(asyncDrawable);
//	        task.execute(resId);
//	    }
//	}
//
//	public void setImageCache(ImageCache cacheCallback) {
//        mImageCache = cacheCallback;
//    }
//
//	static class AsyncDrawable extends BitmapDrawable {
//        private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;
//
//        public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerTask bitmapWorkerTask) {
//            super(res, bitmap);
//            bitmapWorkerTaskReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
//        }
//
//        public BitmapWorkerTask getBitmapWorkerTask() {
//            return bitmapWorkerTaskReference.get();
//        }
//    }
//
//	public static boolean cancelPotentialWork(int data, ImageView imageView) {
//        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
//
//        if (bitmapWorkerTask != null) {
//            final int bitmapData = bitmapWorkerTask.data;
//            if (bitmapData != data) {
//                // Cancel previous task
//                bitmapWorkerTask.cancel(true);
//            } else {
//                // The same work is already in progress
//                return false;
//            }
//        }
//        // No task associated with the ImageView, or an existing task was cancelled
//        return true;
//    }
//
//    private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
//       if (imageView != null) {
//           final Drawable drawable = imageView.getDrawable();
//           if (drawable instanceof AsyncDrawable) {
//               final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
//               return asyncDrawable.getBitmapWorkerTask();
//           }
//        }
//        return null;
//    }
//
//    class BitmapWorkerTask extends AsyncTask<Integer, Void, Bitmap> {
//    	private final WeakReference<ImageView> imageViewReference;
//        private int data = 0;
//
//        public BitmapWorkerTask(ImageView imageView) {
//            // Use a WeakReference to ensure the ImageView can be garbage collected
//            imageViewReference = new WeakReference<ImageView>(imageView);
//        }
//
//        // Decode image in background.
//        @Override
//        protected Bitmap doInBackground(Integer... params) {
//            data = params[0];
////            return decodeSampledBitmapFromResource(getResources(), data, 100, 100);
//            final Bitmap bitmap = decodeSampledBitmapFromResource(getResources(), data, 100, 100);
//            mImageCache.addBitmapToCache(String.valueOf(data), bitmap);
//            return bitmap;
//        }
//
//        @Override
//        protected void onPostExecute(Bitmap bitmap) {
//            if (isCancelled()) {
//                bitmap = null;
//            }
//
//            if (imageViewReference != null && bitmap != null) {
//                final ImageView imageView = imageViewReference.get();
//                final BitmapWorkerTask bitmapWorkerTask =
//                        getBitmapWorkerTask(imageView);
//                if (this == bitmapWorkerTask && imageView != null) {
//                    imageView.setImageBitmap(bitmap);
//                }
//            }
//        }
//    }
//
//    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
//        int reqWidth, int reqHeight) {
//
//        // First decode with inJustDecodeBounds=true to check dimensions
//        final BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        BitmapFactory.decodeResource(res, resId, options);
//
//        // Calculate inSampleSize
//        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
//
//        // Decode bitmap with inSampleSize set
//        options.inJustDecodeBounds = false;
//        return BitmapFactory.decodeResource(res, resId, options);
//    }
//
//    public static int calculateInSampleSize(
//    	BitmapFactory.Options options, int reqWidth, int reqHeight) {
//    // Raw height and width of image
//    	final int height = options.outHeight;
//    	final int width = options.outWidth;
//    	int inSampleSize = 1;
//
//    	if (height > reqHeight || width > reqWidth) {
//
//    		final int halfHeight = height / 2;
//    		final int halfWidth = width / 2;
//
//    		// Calculate the largest inSampleSize value that is a power of 2 and keeps both
//    		// height and width larger than the requested height and width.
//    		while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) > reqWidth) {
//    			inSampleSize *= 2;
//    		}
//    	}
//    	return inSampleSize;
//    }
//*/
//	public final class ViewHolder{
//		ImageView img;
//		TextView title;
//		TextView desc;
//	}
//
//}
