package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.view.AutoHighlightImageView;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 4/1/2016.
 */
public class Fragment_FontSetting extends BaseFragment {
    private static final String TAG = "Font";
    public static final String NORMAL = "normal";
    public static final String MEDIUM = "medium";
    public static final String BIG = "big";
    public static final String WHITE_BG = "white_bg";
    public static final String WHITE_TV = "white_tv";
    public static final String BLACK_BG = "black_bg";
    public static final String BLACK_TV = "black_tv";

    RadioButton radioButtonLight;
    RadioButton radioButtonDark;
    RadioButton radioNormal;
    RadioButton radioMedium;
    RadioButton radioBig;

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

        final TextView tvPreviewFont = (TextView) view.findViewById(R.id.preview_font);
        final View viewBackground = view.findViewById(R.id.preview_background);
        RadioGroup radioGroup = (RadioGroup) view.findViewById(R.id.radio_title_font_size_select);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_small:
                        AppPreferences.INSTANCE.setTextSize(NORMAL);
                        tvPreviewFont.setTextSize(18);
                        return;
                    case R.id.radio_medium:

                        AppPreferences.INSTANCE.setTextSize(MEDIUM);
                        tvPreviewFont.setTextSize(24);
                        return;
                    case R.id.radio_large:

                        AppPreferences.INSTANCE.setTextSize(BIG);
                        tvPreviewFont.setTextSize(28);
                        return;
                    default:
                }

            }
        });
        RadioGroup radioGroupColor = (RadioGroup) view.findViewById(R.id.radio_title_font_color_select);
        radioButtonLight = (RadioButton) view.findViewById(R.id.radio_light);
        radioButtonDark = (RadioButton) view.findViewById(R.id.radio_dark);
        radioNormal = (RadioButton) view.findViewById(R.id.radio_small);
        radioMedium = (RadioButton) view.findViewById(R.id.radio_medium);
        radioBig = (RadioButton) view.findViewById(R.id.radio_large);
//        saveRadioButtonState();
        radioGroupColor.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_light:
                        viewBackground.setBackgroundColor(getResources().getColor(R.color.white));
                        tvPreviewFont.setTextColor(getResources().getColor(R.color.black));
                        AppPreferences.INSTANCE.setWebviewBg(WHITE_BG);
                        AppPreferences.INSTANCE.setTextviewColor(BLACK_TV);
                        return;
                    case R.id.radio_dark:
                        viewBackground.setBackgroundColor(getResources().getColor(R.color.md_blue_grey_700));
                        tvPreviewFont.setTextColor(getResources().getColor(R.color.white));
                        AppPreferences.INSTANCE.setWebviewBg(BLACK_BG);
                        AppPreferences.INSTANCE.setTextviewColor(WHITE_TV);
                }
            }
        });
    }
//    private void saveRadioButtonState(){
////        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Size", 0);
////        SharedPreferences.Editor editor = sharedPreferences.edit();
////        editor.putBoolean(NORMAL, radioNormal.isChecked());
////        editor.putBoolean(MEDIUM, radioMedium.isChecked());
////        editor.putBoolean(BIG, radioBig.isChecked());
////        editor.commit();
//
//    }
    private void loadRadioButtonState(){
//        SharedPreferences settings = getActivity().getSharedPreferences("Size", 0);
//        radioNormal.setChecked(settings.getBoolean(NORMAL, false));
//        radioMedium.setChecked(settings.getBoolean(MEDIUM, false));
//        radioBig.setChecked(settings.getBoolean(BIG, false));
        if (AppPreferences.INSTANCE.getTextSize() != null){
            if(AppPreferences.INSTANCE.getTextSize().equals(NORMAL)){
                radioNormal.setChecked(true);
            } else if(AppPreferences.INSTANCE.getTextSize().equals(MEDIUM)){
                radioMedium.setChecked(true);
            }else if (AppPreferences.INSTANCE.getTextSize().equals(BIG)){
                radioBig.setChecked(true);
            }
        }

    }

    @Override
    public boolean canBack() {
        return super.canBack();
    }

    @Override
    public void onStart() {
        super.onStart();
        loadRadioButtonState();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadRadioButtonState();
    }
}
