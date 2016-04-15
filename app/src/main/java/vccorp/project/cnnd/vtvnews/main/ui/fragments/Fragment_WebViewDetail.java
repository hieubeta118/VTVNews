package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.model.CommentModel;
import vccorp.project.cnnd.vtvnews.main.service.ServiceManager;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.view.AutoHighlightImageView;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;
import vccorp.project.cnnd.vtvnews.main.view.RoundedImageView;

/**
 * Created by Admin on 3/29/2016.
 */
public class Fragment_WebViewDetail extends BaseFragment {
    private WebView webView;
    private static final String TAG = "Detail";
    public static final String NORMAL = "normal";
    public static final String MEDIUM = "medium";
    public static final String BIG = "big";
    String cateUrl;
    String commentUrl;
    String newsUrl;
    private ArrayList<CommentModel> commentModelArrayList;
    private AutoHighlightImageView imgBack;


    public static Fragment_WebViewDetail newInstance(String mCateUrl) {
        Fragment_WebViewDetail fragment_webViewDetail = new Fragment_WebViewDetail();
        fragment_webViewDetail.cateUrl = mCateUrl;
        return fragment_webViewDetail;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_web_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.webview_detail);
        imgBack = (AutoHighlightImageView) view.findViewById(R.id.nav_back_button);
        RelativeLayout btnComment = (RelativeLayout) view.findViewById(R.id.btn_comment);
        LinearLayout btnShare = (LinearLayout) view.findViewById(R.id.btn_share);
        WebSettings settings = webView.getSettings();
//        Log.i("getSize", AppPreferences.INSTANCE.getTextSize());
        if(AppPreferences.INSTANCE.getTextSize() != null){
            if(AppPreferences.INSTANCE.getTextSize().equals(NORMAL)){
                getTextSizeDefault();
            }else if(AppPreferences.INSTANCE.getTextSize().equals(MEDIUM)){
                getTextSizeBigger();
            }else if(AppPreferences.INSTANCE.getTextSize().equals(BIG)){
                getTextSizeBiggest();
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setAppCacheMaxSize(100 * 1024 * 1024);
        settings.setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if (!isNetworkAvailable()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        commentModelArrayList = new ArrayList<>();


        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                ((HomeActivity) getActivity()).pushFragment(Fragment_WebViewDetail.newInstance(url));
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                Log.i(TAG, "Finished loading URL: " + url);

            }

            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.e(TAG, "Error: " + description);
//                Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
                alertDialog.setTitle("Error");
                alertDialog.setMessage(description);
                alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        return;
                    }
                });
                alertDialog.show();
            }
        });
        webView.loadUrl(cateUrl);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null && activity instanceof HomeActivity && isAdded()) {
                    ((HomeActivity) getActivity()).popFragment();
                }
            }
        });
        btnComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Log.i("getCommentUrl", commentUrl);
                loadData();
                if (commentUrl == null) {
                    Toast.makeText(getActivity(), "Không thể comment đường dẫn này", Toast.LENGTH_LONG).show();
                } else {
                    ((HomeActivity) getActivity()).pushFragment(Fragment_Comment.newInstance(commentUrl));
                }
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
                if (newsUrl == null) {
                    Toast.makeText(getActivity(), "Không thể chia sẻ đường dẫn này", Toast.LENGTH_SHORT).show();
                } else {
                    showSharingDialog();
                }
            }
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public boolean canBack() {
        return super.canBack();
    }

    public void showSharingDialog() {
        FragmentManager fragmentManager = getFragmentManager();
        Fragment_Dialog fragmentDialog = new Fragment_Dialog();
        fragmentDialog.show(fragmentManager, " ");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (commentModelArrayList == null) {
            loadData();
        }
    }

    /**
     * load commentUrl following @param cateUrl
     */
    public void loadData() {
        commentModelArrayList.clear();
        Log.i("getCAte", cateUrl);
        ServiceManager.getInstance().getCommentUrl(cateUrl, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                if (jsonObject != null) {
                    try {

                        final CommentModel commentModel = new CommentModel();
                        JSONObject responseObject = new JSONObject(jsonObject.toString());
                        if (responseObject.equals("")) {
                            return;
                        } else {
                            commentUrl = responseObject.optString("commentUrl");
                            Log.i("comment", commentUrl);
                            newsUrl = responseObject.optString("newsUrl");
                            String newsTitle = responseObject.optString("newsTitleFull");
                            /**
                             * Preference save newsUrl to share
                             */
                            AppPreferences.INSTANCE.setShareUrl(newsUrl);
                            AppPreferences.INSTANCE.setNewsTitles(newsTitle);
                            commentModel.setUrlNews(responseObject.optString("newsUrl"));
                        }


                        Log.i("WEbNews", newsUrl);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        e.getMessage();
                    }
                } else {

                    Toast.makeText(getActivity(), "Không thể parse", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error http", error.toString());
            }
        });
    }
    private void getTextSizeDefault(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom());
    }
    private void getTextSizeBigger(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom() + 15);
    }
    private void getTextSizeBiggest(){
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom() + 30);
    }
}
