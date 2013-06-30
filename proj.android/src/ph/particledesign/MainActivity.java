package ph.particledesign;

import java.util.ArrayList;

import org.cocos2dx.lib.Cocos2dxGLSurfaceView;
import org.cocos2dx.lib.Cocos2dxHelper;
import org.cocos2dx.lib.Cocos2dxHelper.Cocos2dxHelperListener;
import org.cocos2dx.lib.Cocos2dxRenderer;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;

import ph.particledesign.ConfigurationPageManager.ConfigurationPage;

public class MainActivity extends Activity implements Cocos2dxHelperListener{

    // ===========================================================
    // Constants
    // ===========================================================
    private static final String TAG = MainActivity.class.getSimpleName();

    // ===========================================================
    // Fields
    // ===========================================================
    private View mGestureOverlay;
    private GestureDetector mGestureDetector;
    private GestureDetector.SimpleOnGestureListener mGestureListener;

    private Cocos2dxGLSurfaceView mGLSurfaceView;

    private ViewPager mViewPager;
    private LocalPagerAdapter mPagerAdapter;

    private ConfigurationPageManager mConfigPageManager;

    private ListView mPresetListView;
    private PresetListAdapter mPresetListAdapter;

    private TextView mCodeView;

    public static native void onGLViewSizeChanged();
    public static native void applyPreset(int id);

    // ===========================================================
    // Static initialization
    // ===========================================================
    static {
        System.loadLibrary("game");
    }

    // ===========================================================
    // Override methods for superClass/interfaces
    // ===========================================================
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.initView();
        this.setupViewPager();

        Cocos2dxHelper.init(this, this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        Cocos2dxHelper.onResume();
        this.mGLSurfaceView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

        Cocos2dxHelper.onPause();
        this.mGLSurfaceView.onPause();
    }

    @Override
    public void showDialog(final String pTitle, final String pMessage) {
    }

    @Override
    public void showEditTextDialog(final String pTitle, final String pContent, final int pInputMode, final int pInputFlag, final int pReturnType, final int pMaxLength) {
    }

    @Override
    public void runOnGLThread(final Runnable pRunnable) {
        this.mGLSurfaceView.queueEvent(pRunnable);
    }

    // ===========================================================
    // Methods
    // ===========================================================
    public void initView() {
        setContentView(R.layout.activity_main);
        this.mGLSurfaceView = (Cocos2dxGLSurfaceView)findViewById(R.id.gl_view);
        this.mGLSurfaceView.setCocos2dxRenderer(new Cocos2dxRenderer());
        if (isAndroidEmulator()) {
            this.mGLSurfaceView.setEGLConfigChooser(8, 8, 8, 8, 16, 0);
        }

        mViewPager = (ViewPager)findViewById(R.id.pager_view);
        mViewPager.setPageMargin(this.getResources().getDimensionPixelSize(R.dimen.page_margin));
        PagerTabStrip strip = (PagerTabStrip)findViewById(R.id.pager_indicator);
        strip.setDrawFullUnderline(true);

        LayoutInflater inflater = getLayoutInflater();

        // First page
        mPresetListView = (ListView)inflater.inflate(R.layout.page_preset, null);
        mPresetListAdapter = new PresetListAdapter(this);
        mPresetListView.setAdapter(mPresetListAdapter);
        mPresetListView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final long finalId = id;
                runOnGLThread(new Runnable() {
                    @Override
                    public void run() {
                        applyPreset((int)finalId);
                    }
                });
            }
        });

        // Manager for second...(n - 1)'th pages
        mConfigPageManager = ConfigurationPageManager.getInstance(this);

        // The last page
        mCodeView = (TextView)inflater.inflate(R.layout.page_code, null);

        // Detect double-tap event before dispatching to GL surface view
        mGestureListener = new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                if (mViewPager.getVisibility() == View.GONE) {
                    mViewPager.setVisibility(View.VISIBLE);
                    onGLViewSizeChanged();
                } else if (mViewPager.getVisibility() == View.VISIBLE) {
                    mViewPager.setVisibility(View.GONE);
                    onGLViewSizeChanged();
                }
                return true;
            }
        };
        mGestureDetector = new GestureDetector(this, mGestureListener);
        mGestureDetector.setOnDoubleTapListener(mGestureListener);

        mGestureOverlay = (View)findViewById(R.id.gesture_overlay);
        mGestureOverlay.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                mGestureDetector.onTouchEvent(event);
                mGLSurfaceView.onTouchEvent(event);
                return true;
            }
        });
    }

    private void setupViewPager() {
        mPagerAdapter = new LocalPagerAdapter();

        // First page
        mPagerAdapter.addPage(mPresetListView, getString(R.string.PAGE_TITLE_PRESET));

        // Second...(n - 1)'th pages
        for (int i = 0; i < mConfigPageManager.getPageCount(); ++i) {
            ConfigurationPage page = mConfigPageManager.getPageAt(i);
            mPagerAdapter.addPage(page.mListView, page.mPageTitle);
        }

        // The last page
        mPagerAdapter.addPage(mCodeView, getString(R.string.PAGE_TITLE_CODE_VIEW));

        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(1, false);
    }

    private final static boolean isAndroidEmulator() {
        String model = Build.MODEL;
        Log.d(TAG, "model=" + model);
        String product = Build.PRODUCT;
        Log.d(TAG, "product=" + product);
        boolean isEmulator = false;
        if (product != null) {
           isEmulator = product.equals("sdk") || product.contains("_sdk") || product.contains("sdk_");
        }
        Log.d(TAG, "isEmulator=" + isEmulator);
        return isEmulator;
    }

    // ===========================================================
    // Inner and Anonymous classes
    // ===========================================================
    private static class LocalPagerAdapter extends PagerAdapter {

        private ArrayList<Page> mPageViews = new ArrayList<Page>();

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mPageViews.get(position).mPageView;
            if (v.getParent() == null) {
                container.addView(v, 0);
            }
            return v;
        }

        public void addPage(View page, String title) {
            mPageViews.add(new Page(page, title));
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals((View)object);
        }

        @Override
        public int getCount() {
            return mPageViews.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View removedView = (View)object;
            if (removedView.getParent() != null) {
                container.removeView(removedView);
            }
        }

        public CharSequence getPageTitle(int position) {
            return mPageViews.get(position).mPageTitle;
        }

        private class Page {
            private Page(View v, String title) {
                mPageTitle = title;
                mPageView = v;
            }
            String mPageTitle;
            View mPageView;
        }
    }
}
