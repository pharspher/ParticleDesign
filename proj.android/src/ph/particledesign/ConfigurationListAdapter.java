package ph.particledesign;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class ConfigurationListAdapter extends BaseAdapter {
    private MainActivity mActivity;
    private ArrayList<ConfigurationItem> mControlList;

    private static class ViewHolder {
        TextView mTitle;
        SeekBar mSeekbar;
        TextView mSeekbarValue;
    }

    public static native void setProgressValue(String tag, int value, int type);

    public ConfigurationListAdapter(MainActivity activity, ArrayList<ConfigurationItem> itemList) {
        mActivity = activity;
        mControlList = itemList;
    }

    @Override
    public int getCount() {
        return mControlList.size();
    }

    @Override
    public Object getItem(int position) {
        return mControlList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = mActivity.getLayoutInflater();

        View inflatedView = null;

        final ConfigurationItem item = mControlList.get(position);
        switch (item.mType) {
        case ConfigurationItem.TYPE_MODE:
            inflatedView = inflateModeItem(position, convertView, parent, inflater);
            break;
        case ConfigurationItem.TYPE_SEEKBAR:
            inflatedView = inflateSeekbarItem(position, convertView, parent, inflater);
            break;
            
        }
        return inflatedView;
    }

    private View inflateModeItem(final int position, final View convertView, ViewGroup parent, LayoutInflater inflater) {
        final ConfigurationItem item = mControlList.get(position);

        View inflatedView;
        ViewHolder viewHolder;

        if (convertView == null || item.mType != ConfigurationItem.TYPE_MODE) {
            inflatedView = inflater.inflate(R.layout.listitem_mode, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView)inflatedView.findViewById(R.id.control_item_title);
            inflatedView.setTag(viewHolder);
        } else {
            inflatedView = convertView;
            viewHolder = (ViewHolder)convertView.getTag();
        }
        return inflatedView;
    }

    private View inflateSeekbarItem(final int position, final View convertView, ViewGroup parent, LayoutInflater inflater) {
        final ConfigurationItem item = mControlList.get(position);

        View inflatedView;
        ViewHolder viewHolder;

        if (convertView == null || item.mType != ConfigurationItem.TYPE_SEEKBAR) {
            inflatedView = inflater.inflate(R.layout.listitem_seekbar, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.mTitle = (TextView)inflatedView.findViewById(R.id.control_item_title);
            viewHolder.mSeekbar = (SeekBar)inflatedView.findViewById(R.id.control_item_slider);
            viewHolder.mSeekbarValue = (TextView)inflatedView.findViewById(R.id.control_item_slider_value);
            inflatedView.setTag(viewHolder);
        } else {
            inflatedView = convertView;
            viewHolder = (ViewHolder)convertView.getTag();
        }

        viewHolder.mTitle.setText(item.mTitle);
        final TextView finalValueView = viewHolder.mSeekbarValue;
        viewHolder.mSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                final int finalProgress = progress;
                mActivity.runOnGLThread(new Runnable() {
                    @Override
                    public void run() {
                        setProgressValue(item.mTag, finalProgress, item.mType);
                    }
                });
                finalValueView.setText(String.valueOf(progress));
            }
        });
        viewHolder.mSeekbarValue.setText(String.valueOf(viewHolder.mSeekbar.getProgress()));

        return inflatedView;
    }

}
