package ph.particledesign;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class PresetListAdapter extends BaseAdapter {
    /*
    private static final int PRESET_EXPLOSION = 0;
    private static final int PRESET_FIRE = PRESET_EXPLOSION + 1;
    private static final int PRESET_FIREWORKS = PRESET_FIRE + 1;
    private static final int PRESET_FLOWER = PRESET_FIREWORKS + 1;
    private static final int PRESET_GALAXY = PRESET_FLOWER + 1;
    private static final int PRESET_METEOR = PRESET_GALAXY + 1;
    private static final int PRESET_RAIN = PRESET_METEOR + 1;
    private static final int PRESET_SMOKE = PRESET_RAIN + 1;
    private static final int PRESET_SNOW = PRESET_SMOKE + 1;
    private static final int PRESET_SPIRAL = PRESET_SNOW + 1;
    private static final int PRESET_SUN = PRESET_SPIRAL + 1;
    */

    private LayoutInflater mInflater;
    private ArrayList<PresetItem> mPresetItems = new ArrayList<PresetItem>();

    public PresetListAdapter(Context context) {
        mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        Resources res = context.getResources();
        String[] presetTitle=res.getStringArray(R.array.preset);
        for (int i = 0; i < presetTitle.length; ++i) {
            mPresetItems.add(new PresetItem(i, presetTitle[i]));
        }
    }

    @Override
    public int getCount() {
        return mPresetItems.size();
    }

    @Override
    public Object getItem(int position) {
        return mPresetItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return mPresetItems.get(position).mId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.listitem_preset, null, false);
            viewHolder = new ViewHolder();
            viewHolder.mTextView = (TextView)convertView.findViewById(R.id.preset_title);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        PresetItem item = mPresetItems.get(position);
        viewHolder.mTextView.setText(item.mTitle);
        return convertView;
    }

    private class PresetItem {
        public PresetItem(int id, String title) {
            mId = id;
            mTitle = title;
        }
        String mTitle;
        int mId;
    }

    private class ViewHolder {
        public TextView mTextView;
    }
}
