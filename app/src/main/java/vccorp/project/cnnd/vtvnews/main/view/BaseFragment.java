package vccorp.project.cnnd.vtvnews.main.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.StringRes;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * Created by Admin on 3/23/2016.
 */
public class BaseFragment extends Fragment {

    private static String TAG = BaseFragment.class.getName();
    public static final String BUNDLE_IS_TOP_NAVIGATION = "fragment.is.on.top";

    protected boolean firstLoad;

    public BaseFragment() {
        super();
        firstLoad = true;
    }
    public boolean canBack() {
        return true;
    }

    /**
     * Fragment should be free from event bus, before pop from backstack (and ready to be released).
     */
    public void recycle() {
    }
    protected void runOnUiThread(Runnable runnable) {
        if (getActivity() == null || !isAdded()) {
            return;
        }
        getActivity().runOnUiThread(runnable);
    }

    /**
     * Define content alert in fragment
     *
     * @param title
     * @param message
     * @param listener
     */
    protected void showAlert(final String title, final String message, final AlertListener listener) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity()).setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (listener != null)
                                    listener.yesOnClick();
                            }
                        }).create()
                        .show();
            }
        });
    }

    /**
     * Define content alert in fragment
     *
     * @param title
     * @param message
     */
    protected void showAlert(final String title, final String message) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity()).setTitle(title)
                        .setMessage(message)
                        .setNegativeButton(android.R.string.ok, null).create()
                        .show();
            }
        });
    }

    /**
     * Define content alert in fragment
     *
     * @param titleResId
     * @param messageResId
     */
    protected void showAlert(@StringRes final int titleResId, @StringRes final int messageResId) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                new AlertDialog.Builder(getActivity()).setTitle(titleResId)
                        .setMessage(messageResId)
                        .setNegativeButton(android.R.string.ok, null).create()
                        .show();
            }
        });
    }

    /**
     * Show keyboard in fragment
     *
     * @param target
     */
    protected void showKeyboard(final View target) {
        if (target == null || getActivity() == null) {
            return;
        }
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(target,
                InputMethodManager.SHOW_IMPLICIT);
    }

    /**
     * Hide keyboard
     */
    protected void hideKeyboard() {
        if (getActivity() == null) {
            return;
        }
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    public AlertListener listener;

    public interface AlertListener {
        public void yesOnClick();
    }

}
