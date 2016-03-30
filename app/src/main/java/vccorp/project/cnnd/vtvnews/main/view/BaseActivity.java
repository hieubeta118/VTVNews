package vccorp.project.cnnd.vtvnews.main.view;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Admin on 3/23/2016.
 */
public abstract class BaseActivity extends FragmentActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

//    protected void showLoading() {
//        showLoading(null);
//    }
//
//    protected void showLoading(@StringRes int resId) {
//        showLoading(getString(resId));
//    }
//
//    protected void showLoading(String loadingMessage) {
//        final View v = findViewById(R.id.loading_view);
//        if (v == null) {
//            return;
//        }
//        TextView message = (TextView) v.findViewById(R.id.loading_title);
//        if (TextUtils.isEmpty(loadingMessage)) {
//            message.setVisibility(View.GONE);
//        } else {
//            message.setVisibility(View.VISIBLE);
//            message.setText(loadingMessage);
//        }
//        if (!isFinishing()) {
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    v.setVisibility(View.VISIBLE);
//                }
//            });
//        }
//    }
//
//    protected void hideLoading() {
//        final View v = findViewById(R.id.loading_view);
//        if (v == null) {
//            return;
//        }
//        if (!isFinishing()) {
//            runOnUiThread(new Runnable() {
//
//                @Override
//                public void run() {
//                    v.setVisibility(View.GONE);
//                }
//            });
//        }
//    }

    protected void showAlert(final String title, final String message) {
        if (!isFinishing()) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    new AlertDialog.Builder(BaseActivity.this).setTitle(title)
                            .setMessage(message)
                            .setNegativeButton(android.R.string.ok, null).create()
                            .show();
                }
            });
        }
    }

    protected void showAlert(@StringRes final int titleResId, @StringRes final int messageResId) {
        if (!isFinishing()) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    new AlertDialog.Builder(BaseActivity.this).setTitle(titleResId)
                            .setMessage(messageResId)
                            .setNegativeButton(android.R.string.ok, null).create()
                            .show();
                }
            });
        }
    }

    protected void showKeyboard(View target) {
        if (target == null) {
            return;
        }
        ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target,
                InputMethodManager.SHOW_IMPLICIT);
    }

    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
