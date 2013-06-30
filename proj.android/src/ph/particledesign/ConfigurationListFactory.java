package ph.particledesign;

import java.util.ArrayList;

public class ConfigurationListFactory {

    public static ArrayList<ConfigurationItem> getParticleConfigList() {
        ArrayList<ConfigurationItem> list = new ArrayList<ConfigurationItem>();

        ConfigurationItem item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_MAX_PARTICLE;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_MAX_PARTICLE;
        list.add(item);

        item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_LIFE;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_LIFE;
        list.add(item);

        item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_LIFE_VAR;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_LIFE_VAR;
        list.add(item);

        item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_START_SIZE;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_START_SIZE;
        list.add(item);

        item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_START_SIZE_VAR;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_LIFE_VAR;
        list.add(item);

        item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_END_SIZE;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_END_SIZE;
        list.add(item);

        item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_END_SIZE_VAR;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_END_SIZE_VAR;
        list.add(item);

        return list;
    }

    public static ArrayList<ConfigurationItem> getEmitterConfigList() {
        ArrayList<ConfigurationItem> list = new ArrayList<ConfigurationItem>();

        ConfigurationItem item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_MODE;
        item.mType = ConfigurationItem.TYPE_MODE;
        item.mTag = ConfigurationItem.TAG_MODE;
        list.add(item);

        item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_DURATION;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_DURATION;
        list.add(item);

        return list;
    }

    public static ArrayList<ConfigurationItem> getGravityConfigList() {
        ArrayList<ConfigurationItem> list = new ArrayList<ConfigurationItem>();

        ConfigurationItem item = new ConfigurationItem();
        item.mTitle = ConfigurationItem.TITLE_SPEED;
        item.mType = ConfigurationItem.TYPE_SEEKBAR;
        item.mTag = ConfigurationItem.TAG_SPEED;
        list.add(item);

        return list;
    }
}
