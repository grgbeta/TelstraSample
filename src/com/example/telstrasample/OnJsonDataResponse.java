package com.example.telstrasample;

import com.example.telstrasample.data.JsonResponse;

public interface OnJsonDataResponse {
	public void onJsonDataReceived(JsonResponse jsonResponse) ;
	public void onError();
}
