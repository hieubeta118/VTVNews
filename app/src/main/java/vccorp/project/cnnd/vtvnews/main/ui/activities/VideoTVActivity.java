//package vccorp.project.cnnd.vtvnews.main.ui.activities;
//
//import android.media.MediaPlayer;
//import android.net.Uri;
//import android.os.Bundle;
//import android.support.v4.view.ViewPager;
//import android.widget.MediaController;
//
//import com.astuetz.PagerSlidingTabStrip;
//
//import java.util.ArrayList;
//
//import vccorp.project.cnnd.vtvnews.R;
//import vccorp.project.cnnd.vtvnews.main.adapter.ListPagerVideoAdapter;
//import vccorp.project.cnnd.vtvnews.main.model.ListLiveVideoModel;
//import vccorp.project.cnnd.vtvnews.main.view.BaseActivity;
//import vccorp.project.cnnd.vtvnews.main.view.CustomVideoView;
//
///**
// * Created by Admin on 3/24/2016.
// */
//public class VideoTVActivity extends BaseActivity {
//    private ViewPager viewpagerLichChieu, viewPager;
//    private ListPagerVideoAdapter pagerVideoAdapter;
//    private ListPagerLichChieuAdapter lichChieuAdapter;
//    private CustomVideoView videoView;
//    private ArrayList<ListLiveVideoModel> listLiveVideoArrayList;
//    PagerSlidingTabStrip pagerSlidingTabStrip, pagerSlidingTabLichChieu;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_video);
//        viewPager = (ViewPager) findViewById(R.id.content_channel);
//        viewpagerLichChieu = (ViewPager) findViewById(R.id.content_channel1);
//        videoView = (CustomVideoView) findViewById(R.id.video_play_activity_video_view);
//
//        pagerVideoAdapter = new ListPagerVideoAdapter(getApplicationContext(), getSupportFragmentManager());
//        lichChieuAdapter = new ListPagerLichChieuAdapter(getApplicationContext(), getSupportFragmentManager());
//        pagerSlidingTabStrip = (PagerSlidingTabStrip) findViewById(R.id.page_tab_video);
//        pagerSlidingTabLichChieu = (PagerSlidingTabStrip) findViewById(R.id.tab_lich_phat);
//        viewPager.setAdapter(pagerVideoAdapter);
//        viewpagerLichChieu.setAdapter(lichChieuAdapter);
//        pagerSlidingTabStrip.setViewPager(viewPager);
//        pagerSlidingTabLichChieu.setViewPager(viewpagerLichChieu);
//
////        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
////            @Override
////            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////
////            }
////
////            @Override
////            public void onPageSelected(int position) {
////                switch (position) {
////                    case 0:
////                        String videoPath = "http://222.255.27.46/vtv1_m.m3u8?token=6a0795415d";
////                        getVideoView(videoPath);
////                        break;
////                    case 1:
////                        Toast.makeText(getApplicationContext(), "VTV2", Toast.LENGTH_SHORT).show();
////                        String videoPath2 = "http://222.255.27.27/vtv2_m.m3u8?token=6a0795415d";
////                        getVideoView(videoPath2);
////                        break;
////                    case 2:
////                        Toast.makeText(getApplicationContext(), "VTV3", Toast.LENGTH_SHORT).show();
////                        String videoPath3 = "http://222.255.27.46/vtv3_m.m3u8?token=6a0795415d";
////                        getVideoView(videoPath3);
////                        break;
////                    case 3:
////                        String videoPath4 = "http://222.255.27.27/vtv2_m.m3u8?token=6a0795415d";
////                        getVideoView(videoPath4);
////                        break;
////                    case 4:
////                }
////            }
////
////            @Override
////            public void onPageScrollStateChanged(int state) {
////
////            }
////        });
////        loadData();
//    }
//
//    public void getVideoView(String path){
//        try{
//            Uri uri = Uri.parse(path);
//            videoView.setVideoURI(uri);
////            View view = findViewById(R.id.controller);
////            MediaController mediaController = new MediaController(this);
////            mediaController.setAnchorView(view);
////            videoView.setMediaController(mediaController);
//            videoView.requestFocus();
//            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                @Override
//                public void onPrepared(MediaPlayer mp) {
//                    mp.setOnVideoSizeChangedListener(new MediaPlayer.OnVideoSizeChangedListener() {
//                        @Override
//                        public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
//                            MediaController mediaController = new MediaController(VideoTVActivity.this);
//                            videoView.setMediaController(mediaController);
//                            mediaController.setAnchorView(videoView);
//
//                        }
//                    });
//                }
//            });
//
//
//            videoView.start();
//
//        }catch (Exception e){
//            e.printStackTrace();
//            e.getMessage();
//        }
//    }
//
////    private void loadData(){
//////        listLiveVideoArrayList.clear();
////        pagerVideoAdapter.notifyDataSetChanged();
////        ServiceManager.getInstance().getListLiveVideoItem(new Callback<JSONArray>() {
////            @Override
////            public void success(JSONArray jsonArray, Response response) {
////                try{
////                    JSONArray responseArray = new JSONArray(jsonArray.toString());
////                    Log.i("getArray", String.valueOf(responseArray));
////
////                }catch (JSONException e){
////                    e.printStackTrace();
////                    e.getMessage();
////                }finally {
////                    pagerVideoAdapter.notifyDataSetChanged();
////                }
////            }
////
////            @Override
////            public void failure(RetrofitError error) {
////                Log.i("getJSONError", error.getMessage());
////            }
////        });
////    }
//
//
//}
