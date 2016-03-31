package vccorp.project.cnnd.vtvnews.main.ui.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RelativeLayout;

import net.simonvt.menudrawer.MenuDrawer;
import net.simonvt.menudrawer.Position;

import java.util.Stack;

import vccorp.project.cnnd.vtvnews.R;
import vccorp.project.cnnd.vtvnews.main.ui.fragments.Fragment_HomePage;
import vccorp.project.cnnd.vtvnews.main.ui.fragments.Fragment_TinTuc;
import vccorp.project.cnnd.vtvnews.main.ui.fragments.Fragment_TrangChu;
import vccorp.project.cnnd.vtvnews.main.view.BaseActivity;
import vccorp.project.cnnd.vtvnews.main.view.BaseFragment;

/**
 * Created by Admin on 3/23/2016.
 */
public class HomeActivity extends BaseActivity {

    private MenuDrawer menuDrawer;
//    private PagerSlidingTabStrip pagerSlidingTabStrip;
//    private ListPagerAdapter pagerAdapter;
//    private CustomViewpager viewPager;
//    private Fragment fragment;

    /* Backstack */
    private Stack<Fragment> backStack;
    private boolean animating;
    public RelativeLayout homeContent;
    private final String TAG = HomeActivity.class.getName();

    public static final String BUNDLE_KEY_TOP_FRAGMENT = "HomeActivity.TopFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        if (backStack == null) {
            backStack = new Stack<Fragment>();
        }
        //main content for fragments
        homeContent = (RelativeLayout) findViewById(R.id.home_content);
        pushFragment(Fragment_HomePage.newInstance());


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.back_slide_in,
                        R.anim.back_slide_out)
                .remove(currentFragment)
                .add(R.id.home_content, fragment, fragment.getClass().getName()).commit();
        getSupportFragmentManager().executePendingTransactions();
        animating = false;
    }

    public void pushFragment(Fragment fragment, boolean animate) {
        Fragment currentFragment = backStack.isEmpty() ? null : backStack
                .peek();
        backStack.push(fragment);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        animating = true;
        if (animate) {
            ft.setCustomAnimations(R.anim.slide_in, R.anim.slide_out);
        }
        if (currentFragment != null) {
            ft.remove(currentFragment);
        }
        ft.add(R.id.home_content, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
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
            finish();
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
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (animate) {
            ft.setCustomAnimations(R.anim.back_slide_in, R.anim.back_slide_out);
        }
        if (currentFragment != null) {
            ft.remove(currentFragment);
            if (currentFragment instanceof BaseFragment) {
                ((BaseFragment) currentFragment).recycle();
            }
        }
        ft.add(R.id.home_content, fragment, fragment.getClass().getName()).commitAllowingStateLoss();
        getSupportFragmentManager().executePendingTransactions();
//        updateNavigationControl(fragment);
        animating = false;
    }

    @Override
    public void onBackPressed() {
        if (animating) {
            return;
        }
        Fragment fragment = backStack.peek();
        if (fragment instanceof Fragment_TinTuc) {
            replaceAllFragment(Fragment_TrangChu.newInStance());
            return;
        }


        popFragment(true);
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
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.back_slide_in,
                        R.anim.back_slide_out)
                .remove(currentFragment)
                .add(R.id.home_content, fragment, fragment.getClass().getName()).commit();
        getSupportFragmentManager().executePendingTransactions();
        animating = false;
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }


}
