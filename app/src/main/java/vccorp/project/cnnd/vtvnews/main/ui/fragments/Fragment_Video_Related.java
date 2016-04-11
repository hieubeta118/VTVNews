package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.Toast;

import com.github.rtoshiro.view.video.FullscreenVideoLayout;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.adapter.VideoRelatedAdapter;
import vccorp.project.cnnd.vtvnews.main.helpers.ParseListVideoRelated;
import vccorp.project.cnnd.vtvnews.main.model.VideoRelatedModel;
import vccorp.project.cnnd.vtvnews.main.service.ServiceManager;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;
import vccorp.project.cnnd.vtvnews.main.view.DividerItemDecoration;
import vccorp.project.cnnd.vtvnews.main.view.EndlessRecyclerOnScrollListener;
import vccorp.project.cnnd.vtvnews.main.view.RecyclerItemClickListener;
import vccorp.project.cnnd.vtvnews.main.view.Updateable;

/**
 * Created by Admin on 3/24/2016.
 */
public class Fragment_Video_Related extends BaseFragment implements Updateable, SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private ArrayList<VideoRelatedModel> videoRelatedModelArrayList;
    private VideoRelatedAdapter videoAdapter;
    public String searchQuery = "sad";
    public String oldQuery = "";


    private boolean loading = true;
    int ival = 0;
    int loadLimit = 5;
    private String mChannelName;
    private Map<String, String> maps;

    public static Fragment_Video_Related newInStance(String channelName) {
        Fragment_Video_Related fragment_video_related = new Fragment_Video_Related();
        fragment_video_related.mChannelName = channelName;
        return fragment_video_related;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_related, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        videoRelatedModelArrayList = new ArrayList<>();
        videoAdapter = new VideoRelatedAdapter(getActivity(), videoRelatedModelArrayList);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_videoRelated);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(videoAdapter);
//        loadData(mChannelName);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreData(mChannelName);
            }
        });
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                VideoRelatedModel videoRelatedModel =
                        videoRelatedModelArrayList.get(position);
                Log.i("getFileName", videoRelatedModel.getFileName());
                AppPreferences.INSTANCE.setVideoURI(videoRelatedModel.getFileName());
//                Fragment_TV.initVideoStreaming(videoRelatedModel.getFileName());
                EventBus.getDefault().post(videoRelatedModel.getFileName());


            }
        }));

        initSearchViewUI(view);
        Button btnSearch = (Button) view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maps = new HashMap<>();
                maps.put("keyword", searchQuery);
                ival =0;
                loadDataSearch(mChannelName);
                final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(linearLayoutManager);
                recyclerView.setHasFixedSize(true);
                recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                        DividerItemDecoration.VERTICAL_LIST));
                recyclerView.setAdapter(videoAdapter);
                recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
                    @Override
                    public void onLoadMore(int current_page) {
                        loadMoreData(mChannelName);
                    }
                });
                hideKeyboard();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (videoRelatedModelArrayList == null) {
            ival = 0;
            loadData(mChannelName);
        }
    }

    private void loadData(String channelName) {
        videoRelatedModelArrayList.clear();
        videoAdapter.notifyDataSetChanged();
        Log.i("updateChannelLoad", channelName);
        String channelNameLower = channelName.toLowerCase();
        ServiceManager.getInstance().getVideoRelated(channelNameLower,
                new Callback<JsonArray>() {
                    @Override
                    public void success(JsonArray jsonElements, Response response) {
                        try {
                            JSONArray responseArray = new JSONArray(jsonElements.toString());
                            Log.i("getRs", String.valueOf(responseArray));
                            for (int i = ival; i < loadLimit; i++) {
                                JSONObject videoObject = responseArray.getJSONObject(i);
                                Log.i("getobject", String.valueOf(videoObject));
                                Log.i("getNumber", String.valueOf(ival));
                                Log.i("getLoadLimit", String.valueOf(loadLimit));
                                final VideoRelatedModel videoRelatedItem = new VideoRelatedModel();
                                videoRelatedModelArrayList
                                        .add(ParseListVideoRelated.parse(videoObject, videoRelatedItem));
                                ival++;
                            }
                        } catch (JSONException e) {
                            e.getMessage();
                            e.printStackTrace();
                        } finally {
                            videoAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("getErrorParse", error.getMessage());
                    }
                });
    }

    private void loadMoreData(String channelName) {
        final int loadLimitAfter = ival + 5;
        Log.i("getLoadLimit", String.valueOf(loadLimitAfter));
        ServiceManager.getInstance().getVideoRelated(channelName,
                new Callback<JsonArray>() {
                    @Override
                    public void success(JsonArray jsonElements, Response response) {
                        try {
                            JSONArray responseArray = new JSONArray(jsonElements.toString());
                            Log.i("getRs", String.valueOf(responseArray));
                            for (int i = ival; i < loadLimitAfter; i++) {
                                JSONObject videoObject = responseArray.getJSONObject(i);

                                final VideoRelatedModel videoRelatedItem = new VideoRelatedModel();
                                videoRelatedModelArrayList
                                        .add(ParseListVideoRelated.parse(videoObject, videoRelatedItem));
                                ival++;
                            }
                        } catch (JSONException e) {
                            e.getMessage();
                            e.printStackTrace();
                        } finally {
                            videoAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.i("getErrorParse", error.getMessage());
                    }
                });
    }

    @Override
    public void update(String channelName) {
        Log.d("updateChannel", "updateChannel(" + channelName + ")");
        mChannelName = channelName;
        ival = 0;

        loadData(mChannelName);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.VERTICAL_LIST));
        recyclerView.setAdapter(videoAdapter);
        Log.d("getChannelNAme", mChannelName);
        recyclerView.addOnScrollListener(new EndlessRecyclerOnScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int current_page) {
                loadMoreData(mChannelName);
            }
        });
    }

    private void initSearchViewUI(View view) {
        SearchView searchView = (SearchView) view.findViewById(R.id.searchVideo);
        searchView.setOnQueryTextListener(this);
//        searchView.performClick();
        searchView.onActionViewExpanded();
        searchView.clearFocus();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        searchQuery = query;
        Log.e("on Sub", searchQuery);
        InputMethodManager imm = (InputMethodManager)
                getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isAcceptingText()) {
            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
        videoAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null && !(newText.equals(""))) {
            searchQuery = newText;
            Log.e("on Text", newText);
        }
        return false;
    }

    public void loadDataSearch(String channelName) {
        videoRelatedModelArrayList.clear();
        videoAdapter.notifyDataSetChanged();
        String channelNameLower = channelName.toLowerCase();
        ServiceManager.getInstance().getSearchVideo(maps, channelNameLower, new Callback<JsonArray>() {
            @Override
            public void success(JsonArray jsonElements, Response response) {
                try {
                    JSONArray responseArray = new JSONArray(jsonElements.toString());
                    Log.i("getRSSearch", String.valueOf(responseArray));
                    for (int i = ival; i < loadLimit; i++) {
                        JSONObject videoObject = responseArray.getJSONObject(i);
                        Log.i("getobject", String.valueOf(videoObject));
                        Log.i("getNumber", String.valueOf(ival));
                        Log.i("getLoadLimit", String.valueOf(loadLimit));
                        final VideoRelatedModel videoRelatedItem = new VideoRelatedModel();
                        videoRelatedModelArrayList
                                .add(ParseListVideoRelated.parse(videoObject, videoRelatedItem));
                        ival++;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    e.getMessage();
                }finally {
                    videoAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                Log.i("getErrorParse", error.getMessage());
            }
        });
    }
}
