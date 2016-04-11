package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.Button;
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
import vccorp.project.cnnd.vtvnews.main.model.ListNewsTopic;
import vccorp.project.cnnd.vtvnews.main.service.ServiceManager;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 4/2/2016.
 */
public class Fragment_Comment extends BaseFragment {
    private WebView webView;
    String newsUrl;
    String commentUrl;
    private ArrayList<CommentModel> commentModelArrayList;
    public static final String TAG = "comment";
    public static final String TAG_COMMENT_URL = "http://m.vtv.vn/app/binh-luan.htm";

    public static Fragment_Comment newInstance(String mNewsUrl) {
        Fragment_Comment fragment_comment = new Fragment_Comment();
        fragment_comment.newsUrl = mNewsUrl;
        return fragment_comment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        CommentModel commentModel = new CommentModel();
        commentModelArrayList = new ArrayList<>();
        webView = (WebView) view.findViewById(R.id.webview_comment);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
//        loadData();
        final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.i(TAG, "Processing webview url click...");

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

        webView.loadUrl(newsUrl);

        Button btnCloseFragment = (Button) view.findViewById(R.id.btn_close_page_comment_tab);
        btnCloseFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((HomeActivity) getActivity()).popFragment();
            }
        });
    }

}
