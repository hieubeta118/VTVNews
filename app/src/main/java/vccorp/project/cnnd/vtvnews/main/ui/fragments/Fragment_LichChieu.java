package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.adapter.LichChieuAdapter;
import vccorp.project.cnnd.vtvnews.main.helpers.ParseListLichChieu;
import vccorp.project.cnnd.vtvnews.main.model.LichChieuModel;
import vccorp.project.cnnd.vtvnews.main.service.ServiceManager;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;
import vccorp.project.cnnd.vtvnews.main.view.Updateable;

/**
 * Created by Admin on 4/5/2016.
 */
public class Fragment_LichChieu extends BaseFragment implements Updateable {
    private RecyclerView recyclerView;
    private ArrayList<LichChieuModel> lichChieuArrayList;
    private LichChieuAdapter lichChieuAdapter;
    String mChannelName;

    public static Fragment_LichChieu newInstance(String channelName) {
        Fragment_LichChieu fragment_lichChieu = new Fragment_LichChieu();
        fragment_lichChieu.mChannelName = channelName;
        return fragment_lichChieu;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lichchieu, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        lichChieuArrayList = new ArrayList<>();
        lichChieuAdapter = new LichChieuAdapter(getActivity(), lichChieuArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_lichchieu);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(lichChieuAdapter);
//        loadData(mChannelName);

    }

    @Override
    public void onResume() {
        super.onResume();
        if(lichChieuArrayList == null ){
            loadData(mChannelName);
        }
    }

    private void loadData(String channelName) {
        lichChieuArrayList.clear();
        lichChieuAdapter.notifyDataSetChanged();
        String channeNameLower = channelName.toLowerCase();
        ServiceManager.getInstance().getDemoLich(channeNameLower, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonElements, Response response) {
                try {
                    JSONArray responseArray = new JSONArray(jsonElements.toString());
                    Log.i("getlich", String.valueOf(responseArray));
                    for (int i = 0; i < responseArray.length(); i++) {
                        JSONObject jsonObject = responseArray.getJSONObject(i);
                        final LichChieuModel lichChieuModel = new LichChieuModel();
                        Log.i("getTitle", jsonObject.optString("Title"));
                        lichChieuArrayList.add(ParseListLichChieu.parse(jsonObject, lichChieuModel));

                    }
                    for(int j = 1; j< responseArray.length();j++){
                        JSONObject jsonObject = responseArray.getJSONObject(j);
                        final LichChieuModel lichChieuModel = new LichChieuModel();
                        lichChieuModel.setEndTime(jsonObject.optString("ScheduleTime"));
                        lichChieuArrayList.add(lichChieuModel);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    e.getMessage();
                }finally {
                    lichChieuAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("getJSONError", error.getMessage());
            }
        });
    }

    @Override
    public void update(String channelName) {
        Log.d("updateChannel", "updateChannel(" + channelName + ")");
        mChannelName = channelName;
        loadData(mChannelName);
    }
}
