package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 3/24/2016.
 */
public class Fragment_Video_Related extends BaseFragment {
    public static Fragment_Video_Related newInStance(){
        Fragment_Video_Related fragment_video_related = new Fragment_Video_Related();
        return fragment_video_related;

    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_video_related, container, false);
    }
    private void loadData(){

    }
}
