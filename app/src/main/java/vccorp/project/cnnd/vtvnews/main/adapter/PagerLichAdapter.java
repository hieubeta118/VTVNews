package vccorp.project.cnnd.vtvnews.main.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;

import java.util.ArrayList;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.model.ListLiveVideoModel;
import vccorp.project.cnnd.vtvnews.main.ui.fragments.Fragment_LichChieu;
import vccorp.project.cnnd.vtvnews.main.ui.fragments.Fragment_TV;
import vccorp.project.cnnd.vtvnews.main.ui.fragments.Fragment_Video_Related;
import vccorp.project.cnnd.vtvnews.main.utils.AppPreferences;
import vccorp.project.cnnd.vtvnews.main.view.Updateable;

/**
 * Created by Admin on 4/5/2016.
 */
public class PagerLichAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.CustomTabProvider {
    Context mContext;
    private Fragment fragment = null;
    private ArrayList<ListLiveVideoModel> listLiveVideoArrayList;
    String mChannelName;
    public PagerLichAdapter(Context ctx, FragmentManager fm) {
        super(fm);
        mContext = ctx;
    }
    private final String[] TITLES = {"Lịch chiếu", "Related Video"};

    @Override
    public View getCustomTabView(ViewGroup parent, int position) {
        RelativeLayout customLayout = (RelativeLayout) LayoutInflater.
                from(mContext).inflate(R.layout.custom_tab_lichchieu, parent, false);
        TextView tvTab = (TextView) customLayout.findViewById(R.id.title_tab_lich_chieu);
        tvTab.setText(TITLES[position]);
        return customLayout;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                fragment = Fragment_LichChieu.newInstance(AppPreferences.INSTANCE.getChannelName());
                break;
            case 1:
                fragment = Fragment_Video_Related.newInStance(AppPreferences.INSTANCE.getChannelName());
                default:
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Override
    public int getItemPosition(Object object) {
      if(object instanceof Updateable){
          ((Updateable) object).update(AppPreferences.INSTANCE.getChannelName());
          Log.i("getChenn", AppPreferences.INSTANCE.getChannelName());
      }

        return super.getItemPosition(object);
    }


}
