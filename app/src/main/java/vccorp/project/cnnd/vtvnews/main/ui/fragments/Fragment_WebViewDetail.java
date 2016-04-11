package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
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
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        commentModelArrayList = new ArrayList<>();
        loadData();

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
                ((HomeActivity) getActivity()).pushFragment(Fragment_Comment.newInstance(commentUrl));
            }
        });
        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSharingDialog();
            }
        });
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

    /**
     * load commentUrl following @param cateUrl
     */
    public void loadData() {
        commentModelArrayList.clear();
        ServiceManager.getInstance().getCommentUrl(cateUrl, new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                try {
                    final CommentModel commentModel = new CommentModel();
                    JSONObject responseObject = new JSONObject(jsonObject.toString());
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

                    Log.i("WEbNews", newsUrl);
                } catch (JSONException e) {
                    e.printStackTrace();
                    e.getMessage();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("error http", error.toString());
            }
        });
    }
}
