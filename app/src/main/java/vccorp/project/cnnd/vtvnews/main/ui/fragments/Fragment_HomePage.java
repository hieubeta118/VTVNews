package vccorp.project.cnnd.vtvnews.main.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.google.gson.JsonObject;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Stack;

import info.hoang8f.android.segmented.SegmentedGroup;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.adapter.ListTopicAdapter;
import vccorp.project.cnnd.vtvnews.main.model.ListNewsTopic;
import vccorp.project.cnnd.vtvnews.main.service.ServiceManager;
import vccorp.project.cnnd.vtvnews.main.ui.activities.HomeActivity;
import vccorp.project.cnnd.vtvnews.main.view.AutoHighlightImageView;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;
import vccorp.project.cnnd.vtvnews.main.view.DividerItemDecoration;
import vccorp.project.cnnd.vtvnews.main.view.RecyclerItemClickListener;

/**
 * Created by Admin on 3/30/2016.
 */
public class Fragment_HomePage extends BaseFragment {
    /**
     * Home Page Fragment declare
     */
    private RecyclerView recyclerView;
    private SegmentedGroup segmentedGroup;
    private ArrayList<ListNewsTopic> listNewsTopicArrayList;
    private ListTopicAdapter topicAdapter;
    private Stack<Fragment> backStack;
    private AutoHighlightImageView btnMenu;
    private boolean animating;


    public LinearLayout main_content;
    public static final String TAG_TIN_TUC_URL = "new_url";
    public static final String TAG_VIDEO_URL = "video_url";
    public static final String TAG_ARRAY_CATEGORIES = "categories";
    public static final String TAG_TOP_MENU = "top_menu";
    public static final String TAG_MAIN_PAGE = "url";
    public static final String TAG_CATEGORY_URL = "catUrl";
    public static final String TAG_TITLE = "title";
    public static final String TIM_KIEM_URL = "http://m.vtv.vn/app/tim-kiem.htm";
    public static final int CLEAR_RECYCLERVIEW_SELECTION = -1;


    public static Fragment_HomePage newInstance() {
        Fragment_HomePage fragment_homePage = new Fragment_HomePage();
        return fragment_homePage;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_homepage, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (backStack == null) {
            backStack = new Stack<Fragment>();
        }

        /**
         * Fragment Home Page
         */
        //Menu drawer init
        initMenuDrawer(view);
        //init Recyclerview
        initRecyclerview(view);
        loadData();
        initSegmentGroup(view);

    }

    private void initRecyclerview(View view) {
        //main content for fragments
        main_content = (LinearLayout) view.findViewById(R.id.main_content);
        listNewsTopicArrayList = new ArrayList<>();
        topicAdapter = new ListTopicAdapter(getActivity(), listNewsTopicArrayList);
        //recyclerView setup
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview_tab);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        //devider between each item
        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),
                DividerItemDecoration.HORIZONTAL_LIST));
        recyclerView.setAdapter(topicAdapter);
        /**
         * click recyclerView item to display fragments below
         */
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        segmentedGroup.clearCheck();
                        topicAdapter.setSelecteditem(position);
                        final ListNewsTopic listNewsTopic = listNewsTopicArrayList.get(position);
                        Fragment newFragment = new FragmentWebView();

                        Bundle args = new Bundle();
                        args.putString("cateUrl", listNewsTopic.getCategoryUrl());
                        newFragment.setArguments(args);
                        replaceAllFragment(newFragment);
                    }
                }));

    }

    private void initSegmentGroup(View view) {
        RadioButton radioButton = (RadioButton) view.findViewById(R.id.btn_trangchu);
        radioButton.setChecked(true);
        pushFragment(Fragment_TrangChu.newInStance());

        /**
         * Top Menu segment init
         */
        segmentedGroup = (SegmentedGroup) view.findViewById(R.id.segmented);
        segmentedGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                topicAdapter.setSelecteditem(CLEAR_RECYCLERVIEW_SELECTION);
                switch (checkedId) {
                    case R.id.btn_trangchu:
                        replaceAllFragment(Fragment_TrangChu.newInStance());
                        return;
                    case R.id.btn_tintuc:
                        replaceAllFragment(Fragment_TinTuc.newInStance());
                        return;
                    case R.id.btn_video:
                        ((HomeActivity) getActivity()).replaceAllFragment(Fragment_TV.newInStance());

                        return;
                    default:
                }
            }
        });
    }

    private void initMenuDrawer(View view) {
        View customView = View.inflate(getActivity(), R.layout.menu_drawer_layout, null);
        final Drawer result = new DrawerBuilder()
                .withActivity(getActivity())
                .withCustomView(customView)
                .build();
        btnMenu = (AutoHighlightImageView) view.findViewById(R.id.btn_menu);
        btnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.openDrawer();
                result.getDrawerLayout();
            }
        });
        RelativeLayout relativeLayout_search = (RelativeLayout) customView.findViewById(R.id.menu_search);
        relativeLayout_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.closeDrawer();
                segmentedGroup.clearCheck();
                Fragment newFragment = new FragmentWebView();

                Bundle args = new Bundle();
                args.putString("cateUrl", TIM_KIEM_URL);
                newFragment.setArguments(args);
                replaceAllFragment(newFragment);
            }
        });
        RelativeLayout relativeLayout_Offline = (RelativeLayout) customView.findViewById(R.id.menu_offline);
        relativeLayout_Offline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.closeDrawer();
                ((HomeActivity) getActivity()).pushFragment(Fragment_Offline.newInstance());
            }
        });
        RelativeLayout relativeLayout_Font = (RelativeLayout) customView.findViewById(R.id.menu_options);
        relativeLayout_Font.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                result.closeDrawer();
                ((HomeActivity) getActivity()).pushFragment(Fragment_FontSetting.newInstance());
            }
        });

    }


    private void loadData() {
        listNewsTopicArrayList.clear();
        topicAdapter.notifyDataSetChanged();
        ServiceManager.getInstance().getSourceLink(new Callback<JsonObject>() {
            @Override
            public void success(JsonObject jsonObject, Response response) {
                try {
                    JSONObject responseObject = new JSONObject(jsonObject.toString());
                    JSONArray results = responseObject.getJSONArray(TAG_ARRAY_CATEGORIES);
//                    Log.i("getResults", String.valueOf(results));
                    for (int i = 0; i < results.length(); i++) {
                        final ListNewsTopic newsTopicItem = new ListNewsTopic();
                        JSONObject topicObject = results.getJSONObject(i);
                        newsTopicItem.setCateTitle(topicObject.optString(TAG_TITLE));
                        newsTopicItem.setCategoryUrl(topicObject.optString(TAG_CATEGORY_URL));
//                        Log.i("getCateUrl", topicObject.optString(TAG_CATEGORY_URL));
                        listNewsTopicArrayList.add(newsTopicItem);
                    }
                } catch (JSONException e) {
                    e.getMessage();
                    e.printStackTrace();
                } finally {
                    topicAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.i("getError", String.valueOf(error));
            }
        });
    }

    public void pushFragment(Fragment fragment) {
        pushFragment(fragment, true);
    }

    public void replaceTopFragment(Fragment fragment) {
        if (backStack.isEmpty()) {
            pushFragment(fragment);
            return;
        }
        Fragment currentFragment = backStack.pop();
        if (currentFragment instanceof BaseFragment) {
            ((BaseFragment) currentFragment).recycle();
        }
        backStack.add(fragment);
        animating = true;
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.back_slide_in,
                        R.anim.back_slide_out)
                .remove(currentFragment)
                .add(R.id.main_content, fragment, fragment.getClass().getName()).commit();
        getChildFragmentManager().executePendingTransactions();
        animating = false;
    }

    public void pushFragment(Fragment fragment, boolean animate) {
        Fragment currentFragment = backStack.isEmpty() ? null : backStack
                .peek();
        backStack.push(fragment);
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        animating = true;
        if (animate) {
            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        }
        if (currentFragment != null) {
            ft.remove(currentFragment);
        }
        ft.add(R.id.main_content, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
        animating = false;
    }

    public void popFragment() {
        popFragment(true);
    }

    /**
     * Pops a single fragment from backstack.
     *
     * @param animate
     */
    public void popFragment(boolean animate) {
        popFragment(animate, 1);
    }

    public void popFragment(boolean animate, int depth) {
        if (depth < 1) {
            return;
        }
        if (backStack.size() < 2) {
            getActivity().finish();
            return;
        }
        hideKeyboard();
        Fragment currentFragment = backStack.peek();
        while (depth >= 1) {
            Fragment next = backStack.pop();
            if (next instanceof BaseFragment) {
                ((BaseFragment) next).recycle();
            }
            depth--;
            if (backStack.size() < 2) {
                break;
            }
        }
        Fragment fragment = backStack.peek();
        animating = true;
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        if (animate) {
            ft.setCustomAnimations(R.anim.back_slide_in, R.anim.back_slide_out);
        }
        if (currentFragment != null) {
            ft.remove(currentFragment);
            if (currentFragment instanceof BaseFragment) {
                ((BaseFragment) currentFragment).recycle();
            }
        }
        ft.add(R.id.main_content, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        getChildFragmentManager().executePendingTransactions();
        animating = false;
    }


    public void replaceAllFragment(Fragment fragment) {
        if (backStack.isEmpty()) {
            pushFragment(fragment);
            return;
        }
        Fragment currentFragment = backStack.pop();
        if (currentFragment instanceof BaseFragment) {
            ((BaseFragment) currentFragment).recycle();
        }
        backStack.removeAllElements();
        backStack.add(fragment);
        animating = true;
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.back_slide_in,
                        R.anim.back_slide_out)
                .remove(currentFragment)
                .add(R.id.main_content, fragment, fragment.getClass().getName()).commit();
        getChildFragmentManager().executePendingTransactions();
        animating = false;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }


}
