package nemupm.imagelist;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailActivity extends Activity {
    private BitmapAndDescription bd = new BitmapAndDescription();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // catch data
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        Bitmap bitmap = (Bitmap) b.get("bitmap");
        String description = (String) b.get("description");
        bd.setBitmap(bitmap);
        bd.setDescription(description);

        ImageView image = (ImageView) this.findViewById(R.id.imageView);
        TextView text = (TextView) this.findViewById(R.id.textView);
        image.setImageBitmap(bd.getBitmap());
        text.setText("Description:\n\t" + bd.getDescription());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
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

    @Override
    protected void onDestroy(){
        Log.d("logcat","onDestroy");
        super.onDestroy();
    }
}
