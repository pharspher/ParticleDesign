package ph.particledesign;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;

public class ConfigurationPageManager {

    private static ConfigurationPageManager mInstance;

    private ArrayList<ConfigurationPage> mConfigPageList = new ArrayList<ConfigurationPage>();

    public class ConfigurationPage {
        ListView mListView;
        ConfigurationListAdapter mListAdapter;
        String mPageTitle;
    }

    public static ConfigurationPageManager getInstance(MainActivity activity) {
        if (mInstance == null) {
            mInstance = new ConfigurationPageManager(activity);
        }
        return mInstance;
    }

    private ConfigurationPageManager(MainActivity activity) {
        LayoutInflater inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ConfigurationPage page = new ConfigurationPage();
        page.mPageTitle = activity.getString(R.string.PAGE_TITLE_EMITTER_CONFIG);
        page.mListView = (ListView)inflater.inflate(R.layout.page_attribute, null);
        page.mListAdapter = new ConfigurationListAdapter(activity, ConfigurationListFactory.getEmitterConfigList());
        page.mListView.setAdapter(page.mListAdapter);
        mConfigPageList.add(page);

        page = new ConfigurationPage();
        page.mPageTitle = activity.getString(R.string.PAGE_TITLE_PARTICLE_CONFIG);
        page.mListView = (ListView)inflater.inflate(R.layout.page_attribute, null);
        page.mListAdapter = new ConfigurationListAdapter(activity, ConfigurationListFactory.getParticleConfigList());
        page.mListView.setAdapter(page.mListAdapter);
        mConfigPageList.add(page);

        page = new ConfigurationPage();
        page.mPageTitle = activity.getString(R.string.PAGE_TITLE_GRAVITY_CONFIG);
        page.mListView = (ListView)inflater.inflate(R.layout.page_attribute, null);
        page.mListAdapter = new ConfigurationListAdapter(activity, ConfigurationListFactory.getGravityConfigList());
        page.mListView.setAdapter(page.mListAdapter);
        mConfigPageList.add(page);
    }

    public ConfigurationPage getPageAt(int index) {
        return mConfigPageList.get(index);
    }

    public int getPageCount() {
        return mConfigPageList.size();
    }
}
