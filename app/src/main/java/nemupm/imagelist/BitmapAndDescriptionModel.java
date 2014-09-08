package nemupm.imagelist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kiko on 2014/09/08.
 */
public class BitmapAndDescriptionModel {
    /**
     * Bitmap Model
     * @return
     */
    static public final String DATE_PATTERN ="yyyy-MM-dd'T'HH:mm:ss";
    private ImageNameAndDescriptionOpenHelper sql = null;

    public BitmapAndDescriptionModel(Context context){
        sql = new ImageNameAndDescriptionOpenHelper(context);
    }

    public void insertBitmapAndDescription(Bitmap bitmap,String description){
        Date date = new Date();
        date.getTime();
        String dateString = (new SimpleDateFormat(DATE_PATTERN)).format(date);
        String name = dateString + ".jpg";
        try {
            // save image on internal database
            FileOutputStream out = sql.context.openFileOutput(name, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.close();

            // insert name and description on sql database
            SQLiteDatabase db = sql.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(ImageNameAndDescription.COLUMN_NAME_IMAGE_NAME,name);
            values.put(ImageNameAndDescription.COLUMN_NAME_DESCRIPTION,description);

            long rowId = db.insert(ImageNameAndDescription.TABLE_NAME,null,values);
            if(rowId == -1){
                throw new Exception();
            }
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<BitmapAndDescription> getBitmapAndDescription(){
        ArrayList<BitmapAndDescription> lstBd = new ArrayList<BitmapAndDescription>();
        try {
            SQLiteDatabase db = sql.getReadableDatabase();
            String[] projection = {
                    ImageNameAndDescription._ID,
                    ImageNameAndDescription.COLUMN_NAME_IMAGE_NAME,
                    ImageNameAndDescription.COLUMN_NAME_DESCRIPTION
            };
            Cursor cursor = db.query(
                    ImageNameAndDescription.TABLE_NAME, projection, null, null, null, null, null);
            boolean moveToFirst = cursor.moveToFirst();
            if (moveToFirst == false) {
                return lstBd;
            }
            do {
                String imageName = cursor.getString(
                        cursor.getColumnIndex(ImageNameAndDescription.COLUMN_NAME_IMAGE_NAME));
                String description = cursor.getString(
                        cursor.getColumnIndex(ImageNameAndDescription.COLUMN_NAME_DESCRIPTION));

                BitmapAndDescription bd = new BitmapAndDescription();
                Bitmap bitmap = getBitmap(imageName);

                bd.setBitmap(bitmap);
                bd.setDescription(description);
                lstBd.add(bd);
            }while(cursor.moveToNext());

        }catch(Exception e){
            e.printStackTrace();
        }
        return lstBd;
    }

    public Bitmap getBitmap(String name){
        Bitmap bitmap = null;
        try{
            InputStream in = sql.context.openFileInput(name);
            bitmap = BitmapFactory.decodeStream(in);
        }catch(IOException e){
            e.printStackTrace();
        }
        return bitmap;
    }
}
