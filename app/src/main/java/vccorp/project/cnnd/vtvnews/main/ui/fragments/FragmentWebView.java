package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.Toast;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 3/28/2016.
 */
public class FragmentWebView extends BaseFragment implements View.OnTouchListener, Handler.Callback {
    private WebView webView;
    private static final String TAG = "webview";
    String cateUrl;
    private SwipeRefreshLayout refreshLayout;
    private final Handler handler = new Handler(this);
    private static final String DEFAULT_KEY = "http://m.vtv.vn/app.htm";
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;

    public static FragmentWebView newInStance() {
        FragmentWebView fragmentWebView = new FragmentWebView();
        return fragmentWebView;

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Bundle bundle = this.getArguments();
        cateUrl = bundle.getString("cateUrl", DEFAULT_KEY);
        return inflater.inflate(R.layout.fragment_trongnuoc, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        webView = (WebView) view.findViewById(R.id.webview_trongnuoc);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refresh_layout);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                /**
                 * re-fresh webview
                 */
                webView.loadUrl("javascript:window.location.reload( true )");
//                webView.reload();
            }
        });
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");

                ((HomeActivity) getActivity()).pushFragment(Fragment_WebViewDetail.newInstance(url));
//                view.loadUrl(url);
                return true;
            }

            public void onPageFinished(WebView view, String url) {
                refreshLayout.setRefreshing(false);
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
        webView.setOnTouchListener(this);

        webView.loadUrl(cateUrl);
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL) {
            handler.removeMessages(CLICK_ON_WEBVIEW);
            return true;
        }
        if (msg.what == CLICK_ON_WEBVIEW) {
            Toast.makeText(getActivity(), "WebView clicked", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if (v.getId() == R.id.webview_trongnuoc && event.getAction() == MotionEvent.ACTION_DOWN) {
            handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        }
        return false;
    }
}
