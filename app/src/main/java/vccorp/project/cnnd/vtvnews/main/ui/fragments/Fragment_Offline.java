package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.JsonArray;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.service.ServiceManager;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.view.AutoHighlightImageView;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 4/1/2016.
 */
public class Fragment_Offline extends BaseFragment {
    private static final String TAG = "Offline";
    private RadioGroup radioGroup;
    private RelativeLayout relativeDownloadCache;
    ArrayList<String> fileNames = new ArrayList<String>();
    ArrayList<String> urls = new ArrayList<String>();
    ArrayList<String> paths = new ArrayList<String>();
    ArrayList<File> files = new ArrayList<File>();

    public static Fragment_Offline newInstance() {
        Fragment_Offline fragment_offline = new Fragment_Offline();
        return fragment_offline;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_setting_cache, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
        AutoHighlightImageView imgBack = (AutoHighlightImageView) view.findViewById(R.id.imv_show_menu);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null && activity instanceof HomeActivity && isAdded()) {
                    ((HomeActivity) getActivity()).popFragment();
                }
            }
        });
        radioGroup = (RadioGroup) view.findViewById(R.id.rag_cache_size);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rab_100:
                        EventBus.getDefault().post(100 * 1024 * 1024);
                        return;
                    case R.id.rab_200:
                        EventBus.getDefault().post(200 * 1024 * 1024);
                        return;
                    case R.id.rab_300:
                        EventBus.getDefault().post(300 * 1024 * 1024);
                        return;
                    default:
                }
            }
        });
        relativeDownloadCache = (RelativeLayout) view.findViewById(R.id.rel_notify_cache);
        relativeDownloadCache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                urls.clear();
                fileNames.clear();
                paths.clear();
                files.clear();
                ServiceManager.getInstance().getCacheUrl(new Callback<JsonArray>() {
                    @Override
                    public void success(JsonArray jsonElements, Response response) {
                        try {
                            JSONArray responseArray = new JSONArray(jsonElements.toString());

                            if (responseArray != null) {
                                for (int i = 0; i < responseArray.length(); i++) {
                                    urls.add(responseArray.get(i).toString());
                                    Log.i("getUrls", String.valueOf(urls));
                                }
                                for (int i = 0; i < urls.size(); i++) {
                                    fileNames.add("file" + i + ".html");
                                }
                                for (int i = 0; i < urls.size(); i++) {
                                    paths.add(getActivity().getFilesDir() + "/" + fileNames.get(i));
                                    Log.i("getPath", String.valueOf(paths));
                                }
                                for (int i = 0; i < urls.size(); i++) {
                                    files.add(new File(paths.get(i)));
                                    Log.i("getfile", String.valueOf(files));
                                }
                                WebView webView = new WebView(getActivity());
                                WebSettings webSettings = webView.getSettings();
                                webSettings.setAppCacheMaxSize(100 * 1024 * 1024);
                                webSettings.setAppCachePath(getActivity().getCacheDir().getAbsolutePath());
                                webSettings.setAllowFileAccess(true);
                                webSettings.setAppCacheEnabled(true);
                                webSettings.setJavaScriptEnabled(true);
                                webSettings.setCacheMode(WebSettings.LOAD_DEFAULT); // load online by default
                                if (!isNetworkAvailable()) {
                                    webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
                                }
                                new downloadContentOfPage().execute();
                            }
                        } catch (JSONException e) {
                            e.getMessage();
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();
                        Log.i("getErrorParse", error.getMessage());
                    }
                });


            }
        });


    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    class downloadContentOfPage extends AsyncTask<String, Void, String> {

        FileOutputStream fos;
        String result = "";
        ProgressDialog progressDialog;
        int position;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            Log.e("pre execute", "yes");

            try {
                fos = getActivity().openFileOutput(fileNames.get(position), Context.MODE_PRIVATE);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            //give your url of html page that you want to download first time.
            HttpGet httpGet = new HttpGet(urls.get(position));
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpGet, localContext);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            BufferedReader reader = null;
            try {
                reader = new BufferedReader(
                        new InputStreamReader(response.getEntity().getContent()
                        )
                );
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    result += line + "\n";
                }
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return result;
        }


        @Override
        protected void onPostExecute(String res) {

            try {
                fos.write(res.getBytes());
                fos.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            Toast.makeText(getActivity().getApplicationContext(), "saved", Toast.LENGTH_LONG).show();
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }

        }


    }

    @Override
    public boolean canBack() {
        return super.canBack();
    }
}
