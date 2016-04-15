package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 3/23/2016.
 */
public class Fragment_TinTuc extends BaseFragment{
    private WebView webView;
//    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "Main";
    public static final String NORMAL = "normal";
    public static final String MEDIUM = "medium";
    public static final String BIG = "big";

    public static Fragment_TinTuc newInStance(){
        Fragment_TinTuc fragment_tinTuc = new Fragment_TinTuc();
        return fragment_tinTuc;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tintuc, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_tintuc);
        webView = (WebView) view.findViewById(R.id.webview_tintuc);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
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
        settings.setAppCacheMaxSize(100*1024*1024);

        settings.setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        if(!isNetworkAvailable()){
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);

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
                Toast.makeText(getActivity(), "Oh no! " + description, Toast.LENGTH_SHORT).show();
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
        webView.loadUrl("http://m.vtv.vn/app/tin-moi.htm");
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
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
