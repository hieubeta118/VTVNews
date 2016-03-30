package vccorp.project.cnnd.vtvnews.main.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.view.BaseActivity;

/**
 * Created by Admin on 3/23/2016.
 */
public class SplashActivity extends BaseActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getAction();
            }
        }, 2000);
    }

    private void getAction() {
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }
}
