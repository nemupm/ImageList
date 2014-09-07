package nemupm.imagelist;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class ListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        // attatch fragment
        /*
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }*/
        final ListActivity mActivity = this;

        /// get image's path
        //Uri uri = MediaStore.Images.Media.INTERNAL_CONTENT_URI; // internal
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI; // SD card
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        ContentResolver cr = getContentResolver();
        ArrayList<BitmapAndString> lstBs = new ArrayList<BitmapAndString>();
        cursor.moveToFirst();
        for (int i = 0; i < cursor.getCount(); i++){
            long id = cursor.getLong(cursor.getColumnIndexOrThrow("_id"));
            Bitmap bmp = MediaStore.Images.Thumbnails.getThumbnail(cr, id, MediaStore.Images.Thumbnails.MICRO_KIND, null);
            String str = "hogehoge" + Integer.toString(i);
            BitmapAndString bs = new BitmapAndString();
            bs.setImage(bmp);
            bs.setDescription(str);
            lstBs.add(bs);
            cursor.moveToNext();
        }
        // make data in list_view
        /*
        /// get image's size
        String path = "sample.png";
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        /// make images
        options.inSampleSize = options.outHeight/600;
        options.inJustDecodeBounds = false;
        Bitmap bmp = BitmapFactory.decodeFile(path, options);
        */
        ListView listView = (ListView)findViewById(R.id.listView);
        // make adapter
        CustomListItemAdapter adapter = new CustomListItemAdapter(mActivity,lstBs);
        listView.setAdapter(adapter);
        // when tapping
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = (String) parent.getItemAtPosition(position);
                Toast.makeText(mActivity, str, Toast.LENGTH_SHORT).show();
            }
        });
    }
/*
    // get photo's path
    private void setPhoto() {
        String[] str_items = {getString(R.string.pick_message02), getString(R.string.pick_message03)};
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_menu_more)
                .setTitle(getString(R.string.pick_message01))
                .setItems(str_items, new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0) {
                            //ギャラリーの起動
                            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select picture"), 999);
                        } else {
                            //カメラの起動
                            //大きい画像の取得用
                            String filename = "CIGAR"+System.currentTimeMillis()+".jpg";

                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, filename);
                            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                            currentData.photo = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                            Intent intent = new Intent();
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, currentData.photo);
                            startActivityForResult(intent, 999);
                        }
                    }
                })
                .show();
    }

    //写真選択インテント呼び出しからの戻り
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 999) {
            if (resultCode == RESULT_OK) {
                //画像のセット
                ivPhoto.setImageURI(data.getData());
            }
        }
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_list, container, false);
            return rootView;
        }
    }
}
