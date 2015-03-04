package com.example.telstrasample;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.example.telstrasample.data.JsonResponse;
import com.google.gson.Gson;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

public class ImageListActivity extends Activity {
	/*
	 * Json url to get the data
	 */
	public static String jsonUrl = "https://dl.dropboxusercontent.com/u/746330/facts.json";
	
	/*
	 * This dialog is used during the json data load
	 */
	private ProgressDialog progressDialog = null ;
	
	private ListView listView = null ;
	private JsonResponse jsonResponse = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_image_list);
		
		/*
		 * Setting the title empty as it needs to be filled from the json data
		 */
		setTitle("");
				
		listView = (ListView) findViewById(R.id.listview);
		
		/*
		 * Loading the json data and initializing the list
		 */
		retrieveJsonDataAndLoadList();
	}
	
	private void showLoadingSpinner() {
		progressDialog = new ProgressDialog(this);
		progressDialog.setMessage("Loading ...");;
		progressDialog.show();
	}
	
	private void dismissLoadingSpinner() {
		progressDialog.dismiss();
	}
	
	private void retrieveJsonDataAndLoadList() {		
		new JsonLoaderTask().execute();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		/*
		 * Refresh menu 
		 */
		getMenuInflater().inflate(R.menu.image_list, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_refresh) {
			/*
			 * Refreshing the list content on pressing this action menu
			 */
			retrieveJsonDataAndLoadList();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	/*
	 * This class is used to load the json data from the given url.
	 * Loads the JsonResponse global variable once the data is loaded
	 */
	private class JsonLoaderTask extends AsyncTask<String, Void, Void> {
		private boolean isError = false;

		@Override
		protected Void doInBackground(String... params) {
			
			try {
			    DefaultHttpClient httpClient = new DefaultHttpClient();
			    HttpGet httpGet = new HttpGet(jsonUrl);

			    HttpResponse httpResponse = httpClient.execute(httpGet);
			    HttpEntity httpEntity = httpResponse.getEntity();
			    String output = EntityUtils.toString(httpEntity);
			    			    			    
			    Gson gson = new Gson();
			    
			    jsonResponse = gson.fromJson(output, JsonResponse.class) ;
			    
			} catch(Exception e) {
				e.printStackTrace() ;
				isError = true;
			}
			
			return null;			
		}
		
		protected void onPreExecute() {
			/*
			 * Starting the spinner before loading the data
			 */
			setTitle("");
			listView.setAdapter(null);
			showLoadingSpinner();
		}

		protected void onPostExecute(Void arg) {
			if (isError) {
				Toast.makeText(getApplicationContext(), "Error loading data", Toast.LENGTH_LONG).show();;
				dismissLoadingSpinner();
				return;
			}
			
			setTitle(jsonResponse.title);
			listView.setAdapter(new ImageListAdapter(
								getApplicationContext(), jsonResponse.rows));
			dismissLoadingSpinner();
		}
	}
}
