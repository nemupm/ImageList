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
public class CustomListItemAdapter extends ArrayAdapter<BitmapAndDescription>{
    private LayoutInflater mLayoutInflater;

    public CustomListItemAdapter(Context context, List<BitmapAndDescription> objects){
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

        BitmapAndDescription item = getItem(position);

        ImageView image = (ImageView) view.findViewById(R.id.imageView);
        TextView text = (TextView) view.findViewById(R.id.textView);
        image.setImageBitmap(item.getBitmap());
        text.setText("Description:"+item.getDescription());

        return view;
    }
}
