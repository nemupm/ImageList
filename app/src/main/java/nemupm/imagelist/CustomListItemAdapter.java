package nemupm.imagelist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kiko on 2014/09/07.
 */
public class CustomListItemAdapter extends ArrayAdapter<BitmapAndString>{
    private LayoutInflater mLayoutInflater;

    public CustomListItemAdapter(Context context, List<BitmapAndString> objects){
        super(context, 0, objects);
        mLayoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View view = null;

        if(convertView == null){
            view = mLayoutInflater.inflate(R.layout.custom_list_item, parent, false);
        }else{
            view = convertView;
        }

        BitmapAndString item = getItem(position);

        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        TextView text = (TextView) view.findViewById(R.id.textView);
        image.setImageBitmap(item.getImage());
        text.setText("Description:"+item.getDescription());

        return view;
    }
}
