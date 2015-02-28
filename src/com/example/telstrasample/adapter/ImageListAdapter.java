package com.example.telstrasample.adapter;

import java.util.List;

import com.example.telstrasample.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.telstrasample.data.JsonRecord;

public class ImageListAdapter {
	public class VivantListAdapter extends BaseAdapter {
		private Context context = null;
		private List<JsonRecord> jsonRecord = null;
		
		public VivantListAdapter(Context context, List<JsonRecord> jsonRecord) {
			this.context = context ;	
			this.jsonRecord = jsonRecord;
		}

		@Override
		public int getCount() {
			return jsonRecord.size();
		}

		@Override
		public JsonRecord getItem(int position) {
			return jsonRecord.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
			        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View view = inflater.inflate(R.layout.image_list_item, parent, false);
			
			return view;
		}
	}


}
