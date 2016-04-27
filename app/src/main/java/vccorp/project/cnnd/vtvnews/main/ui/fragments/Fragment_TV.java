package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.Activity;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.gson.JsonArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.adapter.ListLiveTvAdapter;
import vccorp.project.cnnd.vtvnews.main.adapter.PagerLichAdapter;
import vccorp.project.cnnd.vtvnews.main.helpers.ParseListLiveVideo;
import vccorp.project.cnnd.vtvnews.main.model.ListLiveVideoModel;
import vccorp.project.cnnd.vtvnews.main.service.ServiceManager;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;
import vccorp.project.cnnd.vtvnews.main.view.RecyclerItemClickListener;
import vccorp.project.cnnd.vtvnews.main.view.Updateable;

/**
 * Created by Admin on 3/29/2016.
 */
public class Fragment_TV extends BaseFragment {

    private RecyclerView recyclerViewChannel;
    private ArrayList<ListLiveVideoModel> listLiveVideoArrayList;
    private ListLiveTvAdapter channelAdapter;
    public FullscreenVideoLayout videoLayout;
    private ViewPager viewPager;
    private PagerLichAdapter pagerAdapter;
    private PagerSlidingTabStrip pagerSlidingTabStrip;
    private static final String TAG = "TV";
    String fileVideoUri;
    String channelName;


    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    public static Fragment_TV newInStance() {
        Fragment_TV fragment_tv = new Fragment_TV();
        return fragment_tv;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.activity_video, container, false);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        loadData();
        intiRecyclerView(view);
        initLichChieuPager(view);
        videoLayout = (FullscreenVideoLayout)
                view.findViewById(R.id.videoView);
        videoLayout.setActivity(getActivity());
//        Log.i("getVideo", AppPreferences.INSTANCE.getVideoURI());



        Button btnDong = (Button) view.findViewById(R.id.btndong);
        btnDong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null && activity instanceof HomeActivity && isAdded()) {

                    ((HomeActivity) getActivity()).replaceAllFragment(Fragment_HomePage.newInstance());
                }
            }
        });
    }

    private void intiRecyclerView(View view){
        listLiveVideoArrayList = new ArrayList<>();
        channelAdapter = new ListLiveTvAdapter(getActivity(), listLiveVideoArrayList);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerViewChannel = (RecyclerView) view.findViewById(R.id.toolbarVideo);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerViewChannel.setLayoutManager(linearLayoutManager);
        recyclerViewChannel.setHasFixedSize(true);
        recyclerViewChannel.setAdapter(channelAdapter);
        recyclerViewChannel.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        channelAdapter.setSelectedItem(position);
                        final ListLiveVideoModel listLiveVideoModel =
                                listLiveVideoArrayList.get(position);
                        videoLayout.reset();
//                        channelName = listLiveVideoModel.getNameChannel().toLowerCase();
                        AppPreferences.INSTANCE.setChannelName(listLiveVideoModel.getNameChannel());
                        notifyViewPagerDataSetChanged();
                        AppPreferences.INSTANCE.setVideoURI(listLiveVideoModel.getLinkFile());
                        initVideoStreaming(AppPreferences.INSTANCE.getVideoURI());

                    }
                }));


    }
    private void loadData(){
//        listLiveVideoArrayList.clear();
//        channelAdapter.notifyDataSetChanged();
        ServiceManager.getInstance().getListLiveVideoItem(new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonElements, Response response) {
                try {
                    JSONArray responseArray = new JSONArray(jsonElements.toString());
                    Log.i("getArray", String.valueOf(responseArray));
                    for (int i = 0; i < responseArray.length(); i++) {
                        final ListLiveVideoModel liveVideoItem = new ListLiveVideoModel();
                        JSONObject channelObject = responseArray.getJSONObject(i);
                        listLiveVideoArrayList.add(ParseListLiveVideo
                                .parse(channelObject, liveVideoItem));

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    e.getMessage();
                } finally {
                    channelAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("getErrorParse", error.getMessage());
            }
        });
    }

    public void initVideoStreaming(String urlStream){
        try{
            Uri videoUri = Uri.parse(urlStream);
            videoLayout.setVideoURI(videoUri);
            videoLayout.requestFocus();
            videoLayout.start();

        }catch (IOException e){
            e.printStackTrace();
            e.getMessage();
        }
    }
    public void onEvent(String fileName){

        if(fileName == null){
            Toast.makeText(getActivity(), "Live TV or null",Toast.LENGTH_SHORT).show();
        }else{
            Log.i("getEvent", fileName);
            videoLayout.reset();
            initVideoStreaming(fileName);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
    private void initLichChieuPager(View view){
        viewPager = (ViewPager) view.findViewById(R.id.content_channel);
        pagerAdapter = new PagerLichAdapter(getActivity(), getChildFragmentManager());
        pagerSlidingTabStrip = (PagerSlidingTabStrip) view.findViewById(R.id.tab_lich_phat);
        viewPager.setAdapter(pagerAdapter);
        pagerSlidingTabStrip.setViewPager(viewPager);
        pagerSlidingTabStrip.setOnTabReselectedListener(new PagerSlidingTabStrip.OnTabReselectedListener() {
            @Override
            public void onTabReselected(int position) {
                channelAdapter.setSelectedItem(-1);
            }
        });
    }

    private void notifyViewPagerDataSetChanged() {
        Log.d(TAG, "\nnotifyDataSetChanged()");
        pagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }
}
