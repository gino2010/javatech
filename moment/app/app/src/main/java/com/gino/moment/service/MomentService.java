package com.gino.moment.service;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.Toast;

import com.gino.moment.R;
import com.gino.moment.SettingsFragment;
import com.gino.moment.adapter.SectionAdapter;
import com.gino.moment.model.GridData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MomentService {
    private boolean status = true;
    private Context context;
    private String address;
    private OkHttpClient client;

    public MomentService(Context context) {
        this.context = context;
        client = new OkHttpClient();

        SharedPreferences sharedPreferences = context.getSharedPreferences(
                context.getString(R.string.shared), Context.MODE_PRIVATE);
        address = sharedPreferences.getString(SettingsFragment.SERVER, null);
        if (address == null || "".equals(address)) {
            Toast.makeText(context, R.string.set_address, Toast.LENGTH_LONG).show();
            status = false;
        }

        address = String.format("http://%s/api/", address);
    }

//    public List<String> getUserIds() {
//        ArrayList<String> users = new ArrayList<>();
//        if (status) {
//            Request request = new Request.Builder().url(address + "user_id").build();
//            try {
//                Response response = client.newCall(request).execute();
//                String jsonString = response.body().string();
//                try {
//                    JSONArray jsonArray = new JSONArray(jsonString);
//                    for (int i = 0; i < jsonArray.length(); i++) {
//                        String userId = jsonArray.getString(i);
//                        users.add(userId);
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        return users;
//    }

    public GridData getUserImages() {
        List<SectionAdapter.Section> sectionList = new ArrayList<>();
        List<Integer> imageList = new ArrayList<>();

        if (status) {
            Request request = new Request.Builder().url(address + "user_image").build();
            try {
                Response response = client.newCall(request).execute();
                String jsonString = response.body().string();
                try {
                    JSONArray jsonArray = new JSONArray(jsonString);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        // get user id
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        // create section item
                        sectionList.add(new SectionAdapter.Section(imageList.size(), jsonObject.getString("userId")));

                        JSONArray imagesIds = jsonObject.getJSONArray("imageIds");
                        for (int j = 0; j < imagesIds.length(); j++) {
                            // get image id
                            imageList.add(imagesIds.getInt(j));
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return new GridData(sectionList, imageList);
    }

    public Bitmap getImageById(Integer id) {
        if (status) {
            Request request = new Request.Builder().url(address + "image/" + id).build();
            try {
                Response response = client.newCall(request).execute();
                String baseStr = response.body().string();
                byte[] decodedString = Base64.decode(baseStr, Base64.DEFAULT);
                return BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public String getImageAddress(Integer id) {
        return address + "image/" + id;
    }
}
