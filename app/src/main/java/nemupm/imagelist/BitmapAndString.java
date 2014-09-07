package nemupm.imagelist;

import android.graphics.Bitmap;

/**
 * Created by Kiko on 2014/09/07.
 */
public class BitmapAndString {
    private Bitmap image;
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
