package nemupm.imagelist;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.io.InputStream;
import java.util.ArrayList;


public class ListActivity extends Activity{
    private BitmapAndDescriptionModel bdModel = null;
    private CustomListItemAdapter adapter;
    private static final int REQUEST_GALLERY = 0;
;
    private Bitmap imageToRegister = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("logcat","onCreate");
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

        // make data for list_view
        // read bitmap and description
        bdModel = new BitmapAndDescriptionModel(mActivity);
        ArrayList lstBd = bdModel.getBitmapAndDescription();
        ListView listView = (ListView)findViewById(R.id.listView);
        // make adapter
        adapter = new CustomListItemAdapter(mActivity,lstBd);
        listView.setAdapter(adapter);

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

        // when tapping
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // call DetailActivity
                BitmapAndDescription bd = (BitmapAndDescription)parent.getItemAtPosition(position);
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("bitmap",bd.getBitmap());
                intent.putExtra("description",bd.getDescription());
                startActivity(intent);
            }
        });
    }

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
        switch (item.getItemId()){
            case R.id.action_new:
                registerBitmapAndDescription();
                return true;
            case R.id.action_settings:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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

    /**
     * register bitmap and description
     */
    public void registerBitmapAndDescription(){
        // call gallery
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQUEST_GALLERY);

        // input description dialog
        LayoutInflater inflater
                = LayoutInflater.from(this);
        View view = inflater.inflate(R.layout.input_description_dialog,null);
        final EditText editText
                = (EditText)view.findViewById(R.id.editText);

        new AlertDialog.Builder(this)
                .setTitle("Please input image's description")
                .setIcon(R.drawable.ic_launcher)
                .setView(view)
                .setPositiveButton(
                        "Register",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                BitmapAndDescription bd = new BitmapAndDescription();
                                bd.setBitmap(imageToRegister);
                                bd.setDescription(editText.getText().toString());
                                bdModel.insertBitmapAndDescription(
                                        bd.getBitmap(),bd.getDescription());
                                // reflesh
                                adapter.add(bd);
                                adapter.notifyDataSetChanged();
                            }
                        }
                ).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {
            try {
                InputStream in = getContentResolver().openInputStream(data.getData());
                imageToRegister = BitmapFactory.decodeStream(in);
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
