//package vccorp.project.cnnd.vtvnews.main.adapter;
//
//import android.content.Context;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentStatePagerAdapter;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.RelativeLayout;
//
//import com.astuetz.PagerSlidingTabStrip;
//
//import vccorp.project.cnnd.vtvnews.R;
//
///**
// * Created by Admin on 3/24/2016.
// */
//public class ListPagerVideoAdapter extends FragmentStatePagerAdapter implements PagerSlidingTabStrip.CustomTabProvider{
//    Context mContext;
//
//    private final int[] ICON = {R.drawable.vtv1, R.drawable.vtv2,
//            R.drawable.vtv3, R.drawable.vtv4,
//            R.drawable.vtv5, R.drawable.vtv6,
//            R.drawable.logo_vtv7, R.drawable.logo_vtv8,
//            R.drawable.logo_vtv9};
//    private Fragment fragment = null;
//    public ListPagerVideoAdapter(Context ctx, FragmentManager fm) {
//        super(fm);
//        mContext = ctx;
//    }
//
//    @Override
//    public View getCustomTabView(ViewGroup parent, int position) {
//        RelativeLayout customLayout = (RelativeLayout) LayoutInflater.
//                from(mContext).inflate(R.layout.custom_tab_video, parent, false);
//        ImageView imgView = (ImageView) customLayout.findViewById(R.id.icon_tab);
//        imgView.setImageResource(ICON[position]);
//
//        return customLayout;
//    }
//
//    @Override
//    public Fragment getItem(int position) {
//        switch (position){
//            case 0:
//                String videoPath = "http://222.255.27.46/vtv1_m.m3u8?token=6a0795415d";
//                fragment = Fragment_Vtv1.newInStance(videoPath);
//                break;
//            case 1:
//                String videoPath2 = "http://222.255.27.27/vtv2_m.m3u8?token=6a0795415d";
//                fragment = Fragment_Vtv2.newInStance(videoPath2);
//                break;
//            case 2:
//                String videoPath3 = "http://222.255.27.46/vtv3_m.m3u8?token=6a0795415d";
//                fragment = Fragment_Vtv3.newInStance(videoPath3);
//                break;
//            case 3:
//                fragment = new Fragment_Vtv1();
//                break;
//            case 4:
//                fragment = new Fragment_Vtv2();
//                break;
//            case 5:
//                fragment = new Fragment_Vtv3();
//                break;
//            case 6:
//                fragment = new Fragment_Vtv1();
//                break;
//            case 7:
//                fragment = new Fragment_Vtv2();
//                break;
//            case 8:
//                fragment = new Fragment_Vtv3();
//                break;
//        }
//        return fragment;
//    }
//
//
//    @Override
//    public int getCount() {
//        return ICON.length;
//    }
//}
