package com.example.telstrasample;

import java.io.File;
import java.util.List;

import com.example.telstrasample.R;
import com.example.telstrasample.data.JsonRecord;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageListAdapter extends BaseAdapter {
	private Context context = null;
	private List<JsonRecord> recordList = null;
		
	public ImageListAdapter(Context context, List<JsonRecord> jsonRecords) {
		this.context = context ;	
		this.recordList = jsonRecords;
		
		/*
		 * Initializing the universal image loader
		 */
		DisplayImageOptions options = new DisplayImageOptions.Builder()
		.cacheInMemory(true)
		.cacheOnDisk(true)
		.considerExifParams(true)
		.showImageOnFail(R.drawable.no_image)
		.showImageOnLoading(R.drawable.loading_image)
		.build();
		
		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
			.memoryCache(new LruMemoryCache(4 * 1024 * 1024))
	        .memoryCacheSize(4 * 1024 * 1024)
	        .memoryCacheSizePercentage(13)
	        .diskCache(new UnlimitedDiscCache(cacheDir))
	        .diskCacheSize(50 * 1024 * 1024)
	        .diskCacheFileCount(100)
	        .defaultDisplayImageOptions(options)
	        .build();
		
		ImageLoader.getInstance().init(config);
	}

	@Override
	public int getCount() {
		return recordList.size();
	}

	@Override
	public JsonRecord getItem(int position) {
		return recordList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	private class ViewHolder {
		TextView title;
		TextView secondLine;
		ImageView image;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		JsonRecord record = getItem(position);
		
		if (convertView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.image_list_item, parent, false);
			
			holder = new ViewHolder();
			holder.title = (TextView) convertView.findViewById(R.id.firstLine);
			holder.secondLine = (TextView) convertView.findViewById(R.id.secondLine);
			holder.image = (ImageView) convertView.findViewById(R.id.icon);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.title.setText(record.title);
		holder.secondLine.setText(record.description);
		
		if (record.imageHref != null) {
			holder.image.setImageDrawable(context
										.getResources()
										.getDrawable(R.drawable.loading_image));
			ImageLoader.getInstance().displayImage(record.imageHref, holder.image);
		} else {
			holder.image.setImageDrawable(context
					.getResources()
					.getDrawable(R.drawable.no_image));
		}

		
		return convertView;
	}
}
