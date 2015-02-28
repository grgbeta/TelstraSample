package com.example.telstrasample;

import com.example.telstrasample.data.JsonResponse;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class ImageListActivity extends Activity implements OnJsonDataResponse {
	
	private ProgressDialog progressDialog = null ;
	private ListView listView = null ;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list);
		
		listView = (ListView) findViewById(R.id.listview);
		
		showLoadingSpinner();
		retrieveJsonData();
	}
	
	private void showLoadingSpinner() {
		progressDialog = new ProgressDialog(this);
		progressDialog.show();
	}
	
	private void dismissLoadingSpinner() {
		progressDialog.dismiss();
	}
	
	private void retrieveJsonData() {
		new JsonDataLoader(getApplicationContext(), this).loadJsonData();
	}

	@Override
	public void onJsonDataReceived(JsonResponse jsonResponse) {	
		dismissLoadingSpinner();
	}

	@Override
	public void onError() {		
		dismissLoadingSpinner();
	}

	/*@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.image_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}*/
}
