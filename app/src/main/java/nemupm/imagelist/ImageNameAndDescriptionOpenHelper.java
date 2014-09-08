package nemupm.imagelist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Kiko on 2014/09/08.
 */
public class ImageNameAndDescriptionOpenHelper extends SQLiteOpenHelper{
    // database version
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "database.db";

    private static final String TABLE_CREATE =
            "CREATE TABLE " + ImageNameAndDescription.TABLE_NAME + " (" +
                    ImageNameAndDescription._ID + " INTEGER PRIMARY KEY," +
                    ImageNameAndDescription.COLUMN_NAME_IMAGE_NAME + " TEXT NOT NULL, " +
                    ImageNameAndDescription.COLUMN_NAME_DESCRIPTION + " TEXT);";

    private static final String TABLE_DELETE =
            "DROP TABLE IF EXISTS " + ImageNameAndDescription.TABLE_NAME;

    public Context context = null;

    public ImageNameAndDescriptionOpenHelper(Context con) {
        super(con, DATABASE_NAME, null, DATABASE_VERSION);
        context = con;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DELETE);
        onCreate(db);
    }
}
