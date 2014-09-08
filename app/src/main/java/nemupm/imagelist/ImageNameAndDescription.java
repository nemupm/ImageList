package nemupm.imagelist;

import android.provider.BaseColumns;

/**
 * Created by Kiko on 2014/09/07.
 */
public class ImageNameAndDescription implements BaseColumns{
    public static final String TABLE_NAME = "imagename_and_description";

    public static final String COLUMN_NAME_IMAGE_NAME = "image_name";
    public static final String COLUMN_NAME_DESCRIPTION = "description";

    private String mImageName;
    private String mDescription;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmImageName() {
        return mImageName;
    }

    public void setmImageName(String mImageName) {
        this.mImageName = mImageName;
    }
}
