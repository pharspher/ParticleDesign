package ph.particledesign;

public class ConfigurationItem {

    public static final int TYPE_MODE = 0;
    public static final int TYPE_SEEKBAR = TYPE_MODE + 1;

    public static final String TAG_MODE = "mode";
    public static final String TAG_DURATION = "duration";
    public static final String TAG_MAX_PARTICLE = "max_particle";
    public static final String TAG_LIFE = "life";
    public static final String TAG_LIFE_VAR = "life_var";
    public static final String TAG_START_SIZE = "start_size";
    public static final String TAG_START_SIZE_VAR = "start_size_var";
    public static final String TAG_END_SIZE = "end_size";
    public static final String TAG_END_SIZE_VAR = "end_size_var";
    public static final String TAG_SPEED = "speed";

    public static final String TITLE_MODE = "Mode";
    public static final String TITLE_DURATION = "Duration";
    public static final String TITLE_MAX_PARTICLE = "Number of particle";
    public static final String TITLE_LIFE = "Life";
    public static final String TITLE_LIFE_VAR = "Life variance";
    public static final String TITLE_START_SIZE = "Start size";
    public static final String TITLE_START_SIZE_VAR = "Start size variance";
    public static final String TITLE_END_SIZE = "End size";
    public static final String TITLE_END_SIZE_VAR = "End size variance";
    public static final String TITLE_SPEED = "Speed";

    public static final int MODE_GRAVITY = 0;
    public static final int MODE_RADIUS = MODE_GRAVITY + 1;

    public String mTitle;
    public int mType;
    public String mTag;
    public int mode;
}
