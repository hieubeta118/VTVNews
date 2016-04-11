package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.view.AutoHighlightImageView;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 4/1/2016.
 */
public class Fragment_FontSetting extends BaseFragment {
    private static final String TAG = "Font";

    public static Fragment_FontSetting newInstance() {
        Fragment_FontSetting fragment_fontSetting = new Fragment_FontSetting();
        return fragment_fontSetting;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_font_setting, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }
    private void initUI(View view){
        AutoHighlightImageView imgBack = (AutoHighlightImageView) view.findViewById(R.id.imgBack_Font);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = getActivity();
                if (activity != null && activity instanceof HomeActivity && isAdded()) {
                    ((HomeActivity) getActivity()).popFragment();
                }
            }
        });
    }
    @Override
    public boolean canBack() {
        return super.canBack();
    }
}
