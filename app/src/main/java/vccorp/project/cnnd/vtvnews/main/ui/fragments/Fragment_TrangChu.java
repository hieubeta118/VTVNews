package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 3/23/2016.
 */
public class Fragment_TrangChu extends BaseFragment {
    private WebView webView;
    //    private SwipeRefreshLayout swipeRefreshLayout;
    private static final String TAG = "Main";
    public static final String NORMAL = "normal";
    public static final String MEDIUM = "medium";
    public static final String BIG = "big";
    public static final String WHITE_BG = "white_bg";
    public static final String WHITE_TV = "white_tv";
    public static final String BLACK_BG = "black_bg";
    public static final String BLACK_TV = "black_tv";
    int mCacheMemory = 0;
    private Bundle webViewBundle;
    private View rootView;
    boolean loadingFinished = true;

    public static Fragment_TrangChu newInStance() {
        Fragment_TrangChu fragment_trangChu = new Fragment_TrangChu();
        return fragment_trangChu;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_trangchu, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.webview_trangchu);
//        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout_trangchu);

        webSetting();
        if (!isNetworkAvailable()) {
            webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
      
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");
                System.out.println("your current url when webpage loading.." + url);
                loadingFinished = false;
                ((HomeActivity) getActivity()).pushFragment(Fragment_WebViewDetail.newInstance(url));
//                ((HomeActivity) getActivity()).showFragment(Fragment_WebViewDetail.newInstance(url));
//                showFragment(Fragment_WebViewDetail.newInstance(url));

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
        if (!loadingFinished) {
            return;
        } else {
            webView.loadUrl("http://m.vtv.vn/app.htm");

        }


    }
    //    public void showFragment(Fragment fragment){
//        Log.i("getShow", "show fragment");
////        FragmentManager fm = getSupportFragmentManager();
////        fm.beginTransaction().setCustomAnimations(R.anim.back_slide_in, R.anim.back_slide_out)
////                .show(fragment).commit();
//        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.setCustomAnimations(R.anim.back_slide_in, R.anim.back_slide_out);
//        if(fragment.isHidden()){
//            fragmentTransaction.show(fragment);
//            Log.i("getShowHide", "Show");
//        }else{
//            fragmentTransaction.hide(fragment);
//            Log.i("getShowHide", "Hide");
//        }
//        fragmentTransaction.commit();
//    }


    private void webSetting() {
        WebSettings settings = webView.getSettings();

        settings.setJavaScriptEnabled(true);
        settings.setAppCacheMaxSize(100 * 1024 * 1024);
//        Log.i("getSize", AppPreferences.INSTANCE.getTextSize());
        if (AppPreferences.INSTANCE.getTextSize() != null) {
            if (AppPreferences.INSTANCE.getTextSize().equals(NORMAL)) {
                getTextSizeDefault();
            } else if (AppPreferences.INSTANCE.getTextSize().equals(MEDIUM)) {
                getTextSizeBigger();
            } else if (AppPreferences.INSTANCE.getTextSize().equals(BIG)) {
                getTextSizeBiggest();
            }
        }
//        if (AppPreferences.INSTANCE.getTextviewColor() != null && AppPreferences.INSTANCE.getWebviewBg() != null) {
//            if (AppPreferences.INSTANCE.getTextviewColor().equals(BLACK_TV) &&
//                    AppPreferences.INSTANCE.getWebviewBg().equals(WHITE_BG)) {
//                webView.setBackgroundColor(getResources().getColor(R.color.white));
//                webView.loadUrl("javascript:document.body.style.color=\"black\";");
//            } else if (AppPreferences.INSTANCE.getTextviewColor().equals(WHITE_TV) &&
//                    AppPreferences.INSTANCE.getWebviewBg().equals(BLACK_BG)) {
//                webView.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_500));
//                webView.loadUrl("javascript:document.body.style.color=\"white\";");
//            }
//        }

        settings.setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void onEvent(int cacheMemory) {
        if (cacheMemory != 0) {
            Toast.makeText(getActivity(), "offline" + String.valueOf(mCacheMemory), Toast.LENGTH_SHORT).show();
        } else {
            Log.i("getEvent", String.valueOf(mCacheMemory));
            mCacheMemory = cacheMemory;
        }
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

    private void getTextSizeDefault() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom());
    }

    private void getTextSizeBigger() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom() + 15);
    }

    private void getTextSizeBiggest() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setTextZoom(webSettings.getTextZoom() + 30);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        webView.saveState(outState);
    }

    @Override
    public void onDestroyView() {
        if (rootView != null && rootView.getParent() != null) {
            ((ViewGroup) rootView.getParent()).removeView(rootView);
        }
        super.onDestroyView();

    }
}
