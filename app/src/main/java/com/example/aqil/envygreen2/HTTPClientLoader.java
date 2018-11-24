package com.example.aqil.envygreen2;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


public class HTTPClientLoader extends AsyncTaskLoader<ArrayList<TrashEntitiy>> {


    private ArrayList<TrashEntitiy> mData = new ArrayList<>();
    private boolean hasResult = false;


    public HTTPClientLoader(Context context) {
        super(context);
        onContentChanged();
        Log.d("TAG", "HTTPClientLoader: ");

    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }
    @Override
    public void deliverResult(ArrayList<TrashEntitiy> data) {
        this.mData = data;
        hasResult = true;
        super.deliverResult(data);
    }
    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources();
            mData = null;
            hasResult = false;
        }
    }
    @Override
    public ArrayList<TrashEntitiy> loadInBackground() {
        String url = null;
        switch (getId()) {
            case 0:
                url = "http://4a2f2ef1.ngrok.io/api/envybank/bank/catalogue";
                break;
            case 1:
                url = "http://4a2f2ef1.ngrok.io/api/envybank/bank/catalogue/botol";
                break;
            case 2:
                url="http://4a2f2ef1.ngrok.io/api/envybank/bank/catalogue/kaleng";
                break;
            case 3:
                url="http://4a2f2ef1.ngrok.io/api/envybank/bank/catalogue/kertas";
                break;
        }
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<TrashEntitiy> trashEntitiyArrayList = new ArrayList<>();
        client.get(url, new AsyncHttpResponseHandler() {

            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    String responses = new String(responseBody);
                    JSONObject jsonObject = new JSONObject(responses);
                    JSONArray result = jsonObject.getJSONArray("results");

                    for (int i = 0; i < result.length(); i++) {
                        int productId = result.getJSONObject(i).getInt("product_id");
                        String productName = result.getJSONObject(i).getString("product_name");
                        Double price = result.getJSONObject(i).getDouble("price");
                        int stock=result.getJSONObject(i).getInt("stock");;
                        String description=result.getJSONObject(i).getString("description");;
                        Double rating=result.getJSONObject(i).getDouble("rating");;
                        String salesBankName=result.getJSONObject(i).getString("bank_name");;
                        String thumbnail_path=result.getJSONObject(i).getString("product_image");;
                        String category=result.getJSONObject(i).getString("category");;


                      TrashEntitiy trashEntitiy = new TrashEntitiy(productId,productName,price,stock,description,rating,salesBankName,thumbnail_path,category);
                      trashEntitiyArrayList.add(trashEntitiy);


                    }

                    Log.d("TAG", "onSuccess: "+trashEntitiyArrayList.size());
                } catch (Exception e) {
                    Log.d("TAG", "onSuccess: ");
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


        return trashEntitiyArrayList;
    }

    private void onReleaseResources() {

    }
}
